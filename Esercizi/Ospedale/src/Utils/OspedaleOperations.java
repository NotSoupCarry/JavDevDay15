package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Models.Medico;
import Models.Paziente;
import Models.Persona;

public class OspedaleOperations {

    // Funzione per il login
    public static Persona login(String nome, String cognome) {
        String query = "SELECT p.id, p.nome, p.cognome, p.ruolo, p.creato_da FROM Persona p WHERE p.nome = ? AND p.cognome = ?";

        try (Connection conn = DBContext.connessioneDatabase();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nome);
            stmt.setString(2, cognome);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String ruolo = rs.getString("ruolo");
                Enums.Ruolo ruoloEnum = "Medico".equals(ruolo) ? Enums.Ruolo.MEDICO : Enums.Ruolo.PAZIENTE;
                int idCreatoDa = rs.getInt("creato_da"); // Ottieni l'ID della persona che ha creato il record

                return ruoloEnum == Enums.Ruolo.MEDICO
                        ? new Medico(id, nome, cognome, ruoloEnum, idCreatoDa, "specializzazione")
                        : new Paziente(id, nome, cognome, ruoloEnum, idCreatoDa, 12345);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void registraUtenteMedico(String nome, String cognome, String specializzazione) {
        String ruolo = "Medico";
        String insertPersonaQuery = "INSERT INTO Persona (nome, cognome, ruolo) VALUES (?, ?, ?)";
        String insertMedicoQuery = "INSERT INTO Medico (persona_id, specializzazione) VALUES (?, ?)";

        try (Connection conn = DBContext.connessioneDatabase();
                PreparedStatement stmtPersona = conn.prepareStatement(insertPersonaQuery,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Inserimento nella tabella Persona
            stmtPersona.setString(1, nome);
            stmtPersona.setString(2, cognome);
            stmtPersona.setString(3, ruolo);

            stmtPersona.executeUpdate();

            // Ottieni l'ID generato per la persona appena inserita
            ResultSet generatedKeys = stmtPersona.getGeneratedKeys();
            if (generatedKeys.next()) {
                int personaId = generatedKeys.getInt(1); // ID della persona

                // Inserimento nella tabella Medico
                if (ruolo.equals("Medico")) {
                    try (PreparedStatement stmtMedico = conn.prepareStatement(insertMedicoQuery)) {
                        stmtMedico.setInt(1, personaId);
                        stmtMedico.setString(2, specializzazione);
                        stmtMedico.executeUpdate();
                    }
                }
                System.out.println("Medico registrato con successo!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Errore durante la registrazione del medico.");
        }
    }

    public static void registraUtentePaziente(String nome, String cognome, int codicePaziente) {
        String ruolo = "Paziente";
        String insertPersonaQuery = "INSERT INTO Persona (nome, cognome, ruolo) VALUES (?, ?, ?)";
        String insertPazienteQuery = "INSERT INTO Paziente (persona_id, codice_paziente) VALUES (?, ?)";

        try (Connection conn = DBContext.connessioneDatabase();
                PreparedStatement stmtPersona = conn.prepareStatement(insertPersonaQuery,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Inserimento nella tabella Persona
            stmtPersona.setString(1, nome);
            stmtPersona.setString(2, cognome);
            stmtPersona.setString(3, ruolo);

            stmtPersona.executeUpdate();

            // Ottieni l'ID generato per la persona appena inserita
            ResultSet generatedKeys = stmtPersona.getGeneratedKeys();
            if (generatedKeys.next()) {
                int personaId = generatedKeys.getInt(1); // ID della persona

                // Inserimento nella tabella Paziente
                if (ruolo.equals("Paziente")) {
                    try (PreparedStatement stmtPaziente = conn.prepareStatement(insertPazienteQuery)) {
                        stmtPaziente.setInt(1, personaId);
                        stmtPaziente.setInt(2, codicePaziente);
                        stmtPaziente.executeUpdate();
                    }
                }
                System.out.println("Paziente registrato con successo!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Errore durante la registrazione del paziente.");
        }
    }

    // Metodo per stampare tutte le persone
    public static void stampaTutti() {
        String query = "SELECT p.id, p.nome, p.cognome, p.ruolo, p.creato_da, m.specializzazione, paz.codice_paziente "
                + "FROM Persona p "
                + "LEFT JOIN Medico m ON p.id = m.persona_id "
                + "LEFT JOIN Paziente paz ON p.id = paz.persona_id";

        try (Connection conn = DBContext.connessioneDatabase();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            List<Persona> medici = new ArrayList<>();
            List<Persona> pazienti = new ArrayList<>();

            // Elaborazione dei risultati
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String ruolo = rs.getString("ruolo");
                int IDCreatoDa = rs.getInt("creato_da");

                if ("Medico".equalsIgnoreCase(ruolo)) {
                    String specializzazione = rs.getString("specializzazione");
                    Persona medico = new Medico(id, nome, cognome, Enums.Ruolo.MEDICO, IDCreatoDa, specializzazione);
                    medici.add(medico);
                } else if ("Paziente".equalsIgnoreCase(ruolo)) {
                    int codicePaziente = rs.getInt("codice_paziente");
                    Persona paziente = new Paziente(id, nome, cognome, Enums.Ruolo.PAZIENTE, IDCreatoDa,
                            codicePaziente);
                    pazienti.add(paziente);
                }
            }

            // Stampa i dettagli di Medico
            System.out.println("\nElenco dei Medici:");
            if (medici != null && !medici.isEmpty()) {
                for (Persona medico : medici) {
                    medico.stampaDettagli();
                }
            } else {
                System.out.println("Non ci sono medici");
            }

            // Stampa i dettagli di Paziente
            System.out.println("\nElenco dei Pazienti:");
            if (pazienti != null && !pazienti.isEmpty()) {
                for (Persona paziente : pazienti) {
                    paziente.stampaDettagli();
                }
            } else {
                System.out.println("Non ci sono pazienti");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metodo per stampare tutte le persone con il creatore e data
    public static void mostraTutteLePersoneConCreatore() {
        String query = "SELECT p.id, p.nome, p.cognome, p.ruolo, p.data_creazione, p.creato_da, c.nome AS creatore_nome, c.cognome AS creatore_cognome "
                +
                "FROM Persona p " +
                "LEFT JOIN Persona c ON p.creato_da = c.id";

        try (Connection conn = DBContext.connessioneDatabase();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            System.out.println("\n===== Tutte le persone con il loro creatore =====");
            while (rs.next()) {
                int personaId = rs.getInt("id");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                java.sql.Date dataCreazione = rs.getDate("data_creazione");
                String ruolo = rs.getString("ruolo");
                String creatoreNome = rs.getString("creatore_nome");
                String creatoreCognome = rs.getString("creatore_cognome");

                System.out.println("ID Persona: " + personaId);
                System.out.println("Nome: " + nome);
                System.out.println("Cognome: " + cognome);
                System.out.println("Ruolo: " + ruolo);

                if (creatoreNome != null && creatoreCognome != null) {
                    System.out.println("Creato da: " + creatoreNome + " - " + creatoreCognome + " in data " + dataCreazione);
                } else {
                    System.out.println("Creatore sconosciuto/sistema");
                }

                System.out.println("-------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metodo per inserire un medico
    public static void inserisciMedico(Scanner scanner, int creato_da) {
        System.out.println("\n===== Inserimento Medico =====");

        System.out.print("Nome: ");
        String nome = Controlli.controlloInputStringhe(scanner);

        System.out.print("Cognome: ");
        String cognome = Controlli.controlloInputStringhe(scanner);

        System.out.print("Specializzazione: ");
        String specializzazione = Controlli.controlloInputStringhe(scanner);

        String insertPersonaQuery = "INSERT INTO Persona (nome, cognome, ruolo, creato_da) VALUES (?, ?, 'Medico', ?)";
        String insertMedicoQuery = "INSERT INTO Medico (persona_id, specializzazione) VALUES (?, ?)";

        try (Connection conn = DBContext.connessioneDatabase();
                PreparedStatement stmtPersona = conn.prepareStatement(insertPersonaQuery,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Stampa di debug per verificare creato_da
            System.out.println("ID Creato Da: " + creato_da); // Aggiungi questa riga per verificare il valore

            // Inserimento nella tabella Persona
            stmtPersona.setString(1, nome);
            stmtPersona.setString(2, cognome);
            stmtPersona.setInt(3, creato_da); // Verifica che il valore creato_da sia corretto

            stmtPersona.executeUpdate();

            // Ottieni l'id della persona appena inserita
            ResultSet generatedKeys = stmtPersona.getGeneratedKeys();
            if (generatedKeys.next()) {
                int personaId = generatedKeys.getInt(1);

                // Inserimento nella tabella Medico
                try (PreparedStatement stmtMedico = conn.prepareStatement(insertMedicoQuery)) {
                    stmtMedico.setInt(1, personaId);
                    stmtMedico.setString(2, specializzazione);
                    stmtMedico.executeUpdate();
                }
            }

            System.out.println("Medico inserito con successo!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metodo per inserire un paziente
    public static void inserisciPaziente(Scanner scanner, int IDCreatoDa) {
        System.out.println("\n===== Inserimento Paziente =====");

        System.out.print("Nome: ");
        String nome = Controlli.controlloInputStringhe(scanner);

        System.out.print("Cognome: ");
        String cognome = Controlli.controlloInputStringhe(scanner);

        System.out.print("Codice paziente: ");
        int codicePaziente = Controlli.controlloCodicePazienteUnivoco(scanner);

        String insertPersonaQuery = "INSERT INTO Persona (nome, cognome, ruolo, creato_da) VALUES (?, ?, 'Paziente', ?)";
        String insertPazienteQuery = "INSERT INTO Paziente (persona_id, codice_paziente) VALUES (?, ?)";

        try (Connection conn = DBContext.connessioneDatabase();
                PreparedStatement stmtPersona = conn.prepareStatement(insertPersonaQuery,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Inserimento nella tabella Persona
            stmtPersona.setString(1, nome);
            stmtPersona.setString(2, cognome);
            stmtPersona.setInt(3, IDCreatoDa);

            stmtPersona.executeUpdate();

            // Ottieni l'id della persona appena inserita
            ResultSet generatedKeys = stmtPersona.getGeneratedKeys();
            if (generatedKeys.next()) {
                int personaId = generatedKeys.getInt(1);

                // Inserimento nella tabella Paziente
                try (PreparedStatement stmtPaziente = conn.prepareStatement(insertPazienteQuery)) {
                    stmtPaziente.setInt(1, personaId);
                    stmtPaziente.setInt(2, codicePaziente);
                    stmtPaziente.executeUpdate();
                }
            }

            System.out.println("Paziente inserito con successo!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
