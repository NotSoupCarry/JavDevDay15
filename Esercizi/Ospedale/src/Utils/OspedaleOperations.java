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
    // Metodo per stampare tutte le persone
    public static void stampaTutti() {
        String query = "SELECT p.id, p.nome, p.cognome, p.ruolo, m.specializzazione, paz.codice_paziente "
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
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String ruolo = rs.getString("ruolo");

                if ("Medico".equalsIgnoreCase(ruolo)) {
                    String specializzazione = rs.getString("specializzazione");
                    Persona medico = new Medico(nome, cognome, Persona.Ruolo.MEDICO, specializzazione);
                    medici.add(medico);
                } else if ("Paziente".equalsIgnoreCase(ruolo)) {
                    int codicePaziente = rs.getInt("codice_paziente");
                    Persona paziente = new Paziente(nome, cognome, Persona.Ruolo.PAZIENTE, codicePaziente);
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

    // Metodo per inserire un medico
    public static void inserisciMedico(Scanner scanner) {
        System.out.println("\n===== Inserimento Medico =====");

        System.out.print("Nome: ");
        String nome = Controlli.controlloInputStringhe(scanner);

        System.out.print("Cognome: ");
        String cognome = Controlli.controlloInputStringhe(scanner);

        System.out.print("Specializzazione: ");
        String specializzazione = Controlli.controlloInputStringhe(scanner);

        String insertPersonaQuery = "INSERT INTO Persona (nome, cognome, ruolo) VALUES (?, ?, 'Medico')";
        String insertMedicoQuery = "INSERT INTO Medico (persona_id, specializzazione) VALUES (?, ?)";

        try (Connection conn = DBContext.connessioneDatabase();
                PreparedStatement stmtPersona = conn.prepareStatement(insertPersonaQuery,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Inserimento nella tabella Persona
            stmtPersona.setString(1, nome);
            stmtPersona.setString(2, cognome);
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
    public static void inserisciPaziente(Scanner scanner) {
        System.out.println("\n===== Inserimento Paziente =====");

        System.out.print("Nome: ");
        String nome = Controlli.controlloInputStringhe(scanner);

        System.out.print("Cognome: ");
        String cognome = Controlli.controlloInputStringhe(scanner);

        System.out.print("Codice Paziente: ");
        int codicePaziente = Controlli.controlloInputInteri(scanner);

        String insertPersonaQuery = "INSERT INTO Persona (nome, cognome, ruolo) VALUES (?, ?, 'Paziente')";
        String insertPazienteQuery = "INSERT INTO Paziente (persona_id, codice_paziente) VALUES (?, ?)";

        try (Connection conn = DBContext.connessioneDatabase();
                PreparedStatement stmtPersona = conn.prepareStatement(insertPersonaQuery,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Inserimento nella tabella Persona
            stmtPersona.setString(1, nome);
            stmtPersona.setString(2, cognome);
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
