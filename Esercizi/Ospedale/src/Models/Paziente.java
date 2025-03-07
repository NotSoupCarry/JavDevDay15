package Models;

public class Paziente extends Persona {
    private int codicePaziente;

    public Paziente(String nome, String cognome, Ruolo ruolo, int codicePaziente) {
        super(nome, cognome, ruolo);
        this.codicePaziente = codicePaziente;
    }

    public int getCodicePaziente() {
        return codicePaziente;
    }

    public void setCodicePaziente(int codicePaziente) {
        this.codicePaziente = codicePaziente;
    }

    @Override
    public void stampaDettagli() {
        System.out.println("Ruolo: Paziente");
        System.out.println("Nome: " + getNome());
        System.out.println("Cognome: " + getCognome());
        System.out.println("Codice Paziente: " + codicePaziente + "\n");
    }
}

