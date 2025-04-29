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

    // Getters e Setters
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

    public String getCodiceIdentificativo() {
        return codiceIdentificativo;
    }

    public List<Abbonamento> getAbbonamentiAttivi() {
        return abbonamentiAttivi;
    }

    public List<Abbonamento> getStoricoAbbonamenti() {
        return storicoAbbonamenti;
    }

    /**
     * Aggiunge un nuovo abbonamento attivo
     * @param abbonamento L'abbonamento da aggiungere
     */
    public void aggiungiAbbonamentoAttivo(Abbonamento abbonamento) {
        abbonamentiAttivi.add(abbonamento);
    }

    /**
     * Rimuove un abbonamento dalla lista degli attivi e lo aggiunge allo storico
     * @param abbonamento L'abbonamento da rimuovere
     */
    public void terminaAbbonamento(Abbonamento abbonamento) {
        if (abbonamentiAttivi.remove(abbonamento)) {
            storicoAbbonamenti.add(abbonamento);
        }
    }

    @Override
    public String toString() {
        return nome + " " + cognome + " (" + codiceIdentificativo + ")";
    }
} 