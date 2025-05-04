package model;

import java.util.ArrayList;
import java.util.List;

public class Iscritto {
    private String nome;
    private String cognome;
    private String codiceIdentificativo;
    private List<Abbonamento> abbonamentiAttivi;
    private List<Abbonamento> storicoAbbonamenti;

    public Iscritto(String nome, String cognome, String codiceIdentificativo) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceIdentificativo = codiceIdentificativo;
        this.abbonamentiAttivi = new ArrayList<>();
        this.storicoAbbonamenti = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCodiceIdentificativo() {
        return codiceIdentificativo;
    }

    public void aggiungiAbbonamentoAttivo(Abbonamento abbonamento) {
        abbonamentiAttivi.add(abbonamento);
    }

    public void terminaAbbonamento(Abbonamento abbonamento) {
        if (abbonamentiAttivi.remove(abbonamento)) {
            storicoAbbonamenti.add(abbonamento);
        }
    }

    public List<Abbonamento> getAbbonamentiAttivi() {
        return new ArrayList<>(abbonamentiAttivi);
    }

    public List<Abbonamento> getStoricoAbbonamenti() {
        return new ArrayList<>(storicoAbbonamenti);
    }

    public boolean puoProlungareAbbonamento() {
        return !abbonamentiAttivi.isEmpty();
    }

    public Abbonamento getAbbonamentoAttivoPiuRecente() {
        if (abbonamentiAttivi.isEmpty()) {
            return null;
        }
        return abbonamentiAttivi.get(abbonamentiAttivi.size() - 1);
    }

    @Override
    public String toString() {
        return nome + " " + cognome + " (" + codiceIdentificativo + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Iscritto iscritto = (Iscritto) obj;
        return codiceIdentificativo.equals(iscritto.codiceIdentificativo);
    }

    @Override
    public int hashCode() {
        return codiceIdentificativo.hashCode();
    }
} 