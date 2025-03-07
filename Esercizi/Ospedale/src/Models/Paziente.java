package Models;

import Enums.Ruolo;

public class Paziente extends Persona {
    private int codicePaziente;

    public Paziente(int id, String nome, String cognome, Ruolo ruolo, int IDCreatoDa, int codicePaziente) {
        super(id, nome, cognome, ruolo, IDCreatoDa);
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

