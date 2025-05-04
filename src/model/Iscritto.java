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

    /**
     * Prolunga un abbonamento esistente aggiungendo un nuovo periodo
     * @param abbonamentoAttuale L'abbonamento attualmente attivo
     * @param nuovoAbbonamento Il nuovo abbonamento da aggiungere
     */
    public void prolungaAbbonamento(Abbonamento abbonamentoAttuale, Abbonamento nuovoAbbonamento) {
        if (abbonamentiAttivi.contains(abbonamentoAttuale)) {
            // Verifica che la data di inizio del nuovo abbonamento coincida con la fine dell'abbonamento attuale
            if (nuovoAbbonamento.getDataInizio().equals(abbonamentoAttuale.getDataFine())) {
                abbonamentiAttivi.add(nuovoAbbonamento);
            }
        }
    }

    /**
     * Rimuove un abbonamento tramite il suo codice identificativo
     * @param codiceAbbonamento Il codice dell'abbonamento da rimuovere
     * @return true se l'abbonamento è stato rimosso con successo, false altrimenti
     */
    public boolean rimuoviAbbonamentoPerCodice(String codiceAbbonamento) {
        for (Abbonamento abbonamento : abbonamentiAttivi) {
            if (abbonamento.getCodiceIscritto().equals(codiceAbbonamento)) {
                return abbonamentiAttivi.remove(abbonamento);
            }
        }
        return false;
    }

    /**
     * Verifica se un iscritto ha un abbonamento attivo
     * @return true se ha almeno un abbonamento attivo, false altrimenti
     */
    public boolean haAbbonamentoAttivo() {
        return !abbonamentiAttivi.isEmpty();
    }

    /**
     * Restituisce l'abbonamento attivo più recente
     * @return L'abbonamento attivo più recente o null se non ci sono abbonamenti attivi
     */
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
} 