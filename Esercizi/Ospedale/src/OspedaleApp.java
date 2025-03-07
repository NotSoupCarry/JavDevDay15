import java.util.Scanner;

import Utils.Controlli;
import Utils.OspedaleOperations;

public class OspedaleApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exitMainMenu = false;
        while (!exitMainMenu) {
            System.out.println("\n===== OSPEDALE =====");
            System.out.println("1. Mostra Tutti gli utenti");
            System.out.println("2. Inserisci un Medico");
            System.out.println("3. Inserisci un Paziente");
            System.out.println("4. Esci");
            System.out.print("Scelta: ");
            int scelta = Controlli.controlloInputInteri(scanner);
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    OspedaleOperations.stampaTutti();
                    break;
                case 2:
                    OspedaleOperations.inserisciMedico(scanner);
                    break;
                case 3:
                    OspedaleOperations.inserisciPaziente(scanner);
                    break;
                case 4:
                    System.out.println("Chiusura del programma.");
                    exitMainMenu = true;
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        }
        scanner.close();
    }
}
