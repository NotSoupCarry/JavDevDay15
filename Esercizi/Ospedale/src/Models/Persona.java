package Models;

import Enums.Ruolo;

public abstract class Persona {
    public int id;
    public static final String Ruolo = null;
    private String nome;
    private String cognome;
    private Ruolo ruolo;
    private int IDCreatoDa;

    public Persona(int id, String nome, String cognome, Ruolo ruolo, int IDCreatoDa) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.ruolo = ruolo;
        this.IDCreatoDa = IDCreatoDa;
    }

    // Getter e Setter
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    public int getCreatoDa(){
        return IDCreatoDa;
    }

    // Metodo astratto
    public abstract void stampaDettagli();
}

