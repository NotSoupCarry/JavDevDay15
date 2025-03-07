package Models;

public abstract class Persona {
    private String nome;
    private String cognome;
    private Ruolo ruolo;

    public enum Ruolo {
        MEDICO, PAZIENTE;
    }

    public Persona(String nome, String cognome, Ruolo ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.ruolo = ruolo;
    }

    // Getter e Setter
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

    // Metodo astratto
    public abstract void stampaRuolo();
}

