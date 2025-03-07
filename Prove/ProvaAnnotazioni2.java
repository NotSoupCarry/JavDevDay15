import java.util.List;
import java.util.stream.Stream;

// 1. Definiamo un’interfaccia funzionale con @FunctionalInterface
@FunctionalInterface
interface StreamProcessor {
    // Un solo metodo astratto che elabora uno Stream e ne ritorna un altro
    Stream<String> process(Stream<String> input);
}

// 2. Classe con un metodo deprecato
class OldStreamProcessor {

    @Deprecated
    public Stream<String> oldMethod(Stream<String> input) {
        System.out.println("[DEPRECATED] oldMethod in OldStreamProcessor");
        // Simuliamo una trasformazione “vecchio stile”: convertiamo tutto in maiuscolo
        return input.map(String::toUpperCase);
    }
}

// 3. Classe che estende OldStreamProcessor e implementa StreamProcessor
class MyStreamProcessor extends OldStreamProcessor implements StreamProcessor {

    // @Override per indicare che implementiamo il metodo dell’interfaccia
    @Override
    public Stream<String> process(Stream<String> input) {
        System.out.println("[OVERRIDE] process in MyStreamProcessor");
        // Esempio di elaborazione: filtriamo le stringhe che iniziano con “C”
        return input
                .filter(s -> s.startsWith("C"))
                .map(String::toLowerCase);
    }

    // @SafeVarargs + @SuppressWarnings per gestire parametri varargs di tipo generico
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public final Stream<String> combineAll(Stream<String>... streams) {
        System.out.println("[SAFEVARARGS] combineAll in MyStreamProcessor");
        // Concateniamo tutti gli Stream passati come parametri
        Stream<String> result = Stream.empty();
        for (Stream<String> s : streams) {
            result = Stream.concat(result, s);
        }
        return result;
    }
}

// 4. Classe principale con il main che dimostra l’uso delle annotazioni
public class ProvaAnnotazioni2 {
    public static void main(String[] args) {
        MyStreamProcessor processor = new MyStreamProcessor();

        // Esempio di lista di stringhe
        List<String> items = List.of("Chocolate", "Coffee", "Tea", "Cookie");

        // @Override - viene chiamato il metodo process
        Stream<String> processedStream = processor.process(items.stream());
        processedStream.forEach(System.out::println);

        // @Deprecated - se usiamo oldMethod, riceviamo un avviso di deprecazione
        Stream<String> oldProcessed = processor.oldMethod(items.stream());
        oldProcessed.forEach(s -> System.out.println("Deprecated output: " + s));

        // @SafeVarargs e @SuppressWarnings - metodo che combina più Stream
        Stream<String> combined = processor.combineAll(
                Stream.of("Cappuccino", "Cortado"),
                Stream.of("Espresso", "Latte")
        );
        combined.forEach(System.out::println);
    }
}