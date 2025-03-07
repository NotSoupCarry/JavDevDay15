import java.util.List;
import java.util.stream.Collectors;

public class ProvaStream {
    public static void main(String[] args) {
        List<String> nomi = List.of("Alice", "Bob", "Charlie", "David");

        // Creiamo uno stream e applichiamo diverse operazioni a cascata
        List<String> nomiFiltrati = nomi.stream()
            .filter(nome -> nome.startsWith("C"))  // Prende solo i nomi che iniziano per 'C'
            .map(String::toUpperCase)             // Li converte in maiuscolo
            .sorted()                             // Ordina i risultati
            .collect(Collectors.toList());        // Raccoglie l'output in una nuova lista

        // Stampiamo i nomi filtrati
        System.out.println(nomiFiltrati);  // [CHARLIE]
    }
}