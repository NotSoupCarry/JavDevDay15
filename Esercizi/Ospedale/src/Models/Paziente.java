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
    public void stampaRuolo() {
        System.out.println("Ruolo: Paziente");
    }
}
