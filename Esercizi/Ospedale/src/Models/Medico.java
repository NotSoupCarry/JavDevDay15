package Models;

import Enums.Ruolo;

public class Medico extends Persona {
    private String specializzazione;

    public Medico(int id, String nome, String cognome, Ruolo ruolo, int IDCreatoDa, String specializzazione) {
        super(id, nome, cognome, ruolo, IDCreatoDa);
        this.specializzazione = specializzazione;
    }

    public String getSpecializzazione() {
        return specializzazione;
    }

    public void setSpecializzazione(String specializzazione) {
        this.specializzazione = specializzazione;
    }

    @Override
    public void stampaDettagli() {
        System.out.println("Ruolo: Medico");
        System.out.println("Nome: " + getNome());
        System.out.println("Cognome: " + getCognome());
        System.out.println("Specializzazione: " + specializzazione + "\n");
    }
}

