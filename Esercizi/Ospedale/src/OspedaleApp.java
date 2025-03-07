import java.util.Scanner;

import Utils.Controlli;
import Utils.OspedaleOperations;
import Models.Persona;

public class OspedaleApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exitMainMenu = false;
        Persona loggedUser = null;

        // Menu iniziale: Login o Registrazione
        while (loggedUser == null) {
            loggedUser = mainMenu(scanner);
        }

        System.out.println("Login effettuato con successo! Benvenuto, " + loggedUser.getNome() + "!");

        // Menu principale dopo il login
        while (!exitMainMenu) {
            exitMainMenu = optionsMenu(scanner, loggedUser);
        }

        scanner.close();
    }

    // Menu per Login o Registrazione
    private static Persona mainMenu(Scanner scanner) {
        Persona loggedUser = null;
        boolean exitMenu = false;

        while (!exitMenu) {
            System.out.println("\n===== OSPEDALE =====");
            System.out.println("1. Login");
            System.out.println("2. Registrazione");
            System.out.println("3. Esci");
            System.out.print("Scelta: ");
            int scelta = Controlli.controlloInputInteri(scanner);
            scanner.nextLine(); // Clear newline

            switch (scelta) {
                case 1: // Login
                    loggedUser = loginMenu(scanner);
                    if (loggedUser != null) {
                        exitMenu = true; // Exit menu se login è successo
                    }
                    break;
                case 2: // Registrazione
                    registrazioneMenu(scanner);
                    break;
                case 3: // Esci
                    System.out.println("Chiusura del programma.");
                    exitMenu = true;
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        }

        return loggedUser; // Ritorna l'utente loggato (o null se il login non va a buon fine)
    }

    // Metodo per il login
    private static Persona loginMenu(Scanner scanner) {
        System.out.println("\n===== Login =====");
        System.out.print("Nome: ");
        String nome = Controlli.controlloInputStringhe(scanner);
        System.out.print("Cognome: ");
        String cognome = Controlli.controlloInputStringhe(scanner);

        // Chiama la funzione di login e restituisce l'oggetto Persona
        Persona loggedUser = OspedaleOperations.login(nome, cognome);
        if (loggedUser != null) {
            return loggedUser; // Restituisce l'oggetto Persona se il login è valido
        } else {
            System.out.println("Login fallito. Riprova.");
            return null; // Restituisce null se il login fallisce
        }
    }

    // Metodo per la registrazione
    private static void registrazioneMenu(Scanner scanner) {
        System.out.println("\n===== Registrazione =====");
        System.out.print("Nome: ");
        String nome = Controlli.controlloInputStringhe(scanner);
        System.out.print("Cognome: ");
        String cognome = Controlli.controlloInputStringhe(scanner);

        // Richiesta di ruolo
        while (true) {
            System.out.println("Scegli il tuo ruolo:");
            System.out.println("1. Medico");
            System.out.println("2. Paziente");
            System.out.print("Scelta: ");
            int sceltaRuolo = Controlli.controlloInputInteri(scanner);
            if (sceltaRuolo == 1) {
                System.out.print("Specializzazione: ");
                String Specializzazione = Controlli.controlloInputStringhe(scanner);
                OspedaleOperations.registraUtenteMedico(nome, cognome, Specializzazione);
                break;
            } else if (sceltaRuolo == 2) {
                // Registrazione come Paziente
                System.out.print("Codice Paziente: ");
                int codicePaziente = Controlli.controlloCodicePazienteUnivoco(scanner);
                OspedaleOperations.registraUtentePaziente(nome, cognome, codicePaziente);
                break;
            }
            else{
                System.out.println("devi inserire uu numero tra 1 e 2.");
            }
        }

        // Chiamata alla funzione di registrazione
        System.out.println("Registrazione completata con successo! Ora effettua il login.");
    }

    // Menu delle funzionalità (dopo il login)
    private static boolean optionsMenu(Scanner scanner, Persona loggedUser) {
        boolean exitMainMenu = false;
        System.out.println("\n===== OSPEDALE =====");
        System.out.println("1. Mostra Tutti gli utenti");
        System.out.println("2. Mostra Tutti gli utenti con i creatori");
        System.out.println("3. Inserisci un Medico");
        System.out.println("4. Inserisci un Paziente");
        System.out.println("5. Esci");
        System.out.print("Scelta: ");
        int scelta = Controlli.controlloInputInteri(scanner);
        scanner.nextLine(); // Clear newline

        switch (scelta) {
            case 1: // Mostra utenti
                OspedaleOperations.stampaTutti();
                break;
            case 2: // Mostra utenti con creatori
                OspedaleOperations.mostraTutteLePersoneConCreatore();
                break;
            case 3: // Inserisci un medico
                OspedaleOperations.inserisciMedico(scanner, loggedUser.getId());
                break;
            case 4: // Inserisci un paziente
                OspedaleOperations.inserisciPaziente(scanner, loggedUser.getId());
                break;
            case 5: // Esci
                System.out.println("Chiusura del programma.");
                exitMainMenu = true;
                break;
            default:
                System.out.println("Scelta non valida.");
        }

        return exitMainMenu;
    }
}
