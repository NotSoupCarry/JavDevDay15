import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProvaStream2 {
    public static void main(String[] args) {
        List<String> frasi = List.of(
            "Ciao mondo", 
            "Hello world", 
            "Caffè Latte", 
            "Hello world",   // Duplicato
            "Casa in montagna", 
            "Cioccolato amaro"
        );
        
        // 1. Creiamo uno Stream dalle frasi
        // 2. Filtro: prendo solo le stringhe che iniziano con 'C'
        // 3. Distinct: elimino i duplicati
        // 4. FlatMap: splitta ogni frase in parole
        // 5. Map: converto ogni parola in minuscolo
        // 6. Sorted: ordino alfabeticamente
        // 7. Skip(1): salto la prima parola
        // 8. Limit(4): prendo solo le successive 4 parole
        // 9. Collect: raccolgo il risultato in una lista
        List<String> elaborato = frasi.stream()
            .filter(s -> s.startsWith("C"))
            .distinct()
            .flatMap(s -> Arrays.stream(s.split(" ")))
            .map(String::toLowerCase)
            .sorted()
            .skip(1)
            .limit(4)
            .collect(Collectors.toList());

        System.out.println("=== Risultato dopo le operazioni di elaborazione ===");
        // 10. ForEach (qui lo usiamo semplicemente sulla lista, ma è tipico usarlo sullo stream)
        elaborato.forEach(word -> System.out.println("Elemento: " + word));

        // Esempio di reduce (concatenazione di tutte le parole in una stringa)
        String concatenato = elaborato.stream()
            .reduce("", (acc, word) -> acc.isEmpty() ? word : acc + "-" + word);

        System.out.println("\n=== Risultato finale con reduce ===");
        System.out.println("Stringa concatenata: " + concatenato);
    }
}