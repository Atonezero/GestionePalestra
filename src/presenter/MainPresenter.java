package presenter;

import model.Iscritto;
import model.Abbonamento;
import model.AbbonamentoMensile;
import model.AbbonamentoAnnuale;
import manager.DataManager;
import java.time.LocalDate;
import java.util.List;
import java.io.IOException;

public class MainPresenter {
    private DataManager dataManager;

    public MainPresenter() {
        this.dataManager = new DataManager();
    }

    public void aggiungiIscritto(String nome, String cognome, String codice) {
        Iscritto nuovoIscritto = new Iscritto(nome, cognome, codice);
        dataManager.aggiungiIscritto(nuovoIscritto);
        try {
            dataManager.salvaDati();
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio dei dati: " + e.getMessage());
        }
    }

    public List<Iscritto> getTuttiIscritti() {
        return dataManager.getTuttiIscritti();
    }

    public Iscritto cercaIscritto(String codice) {
        return dataManager.getTuttiIscritti().stream()
            .filter(i -> i.getCodiceIdentificativo().equals(codice))
            .findFirst()
            .orElse(null);
    }

    public void aggiungiAbbonamentoMensile(Iscritto iscritto, LocalDate dataInizio) {
        Abbonamento abbonamento = new AbbonamentoMensile(dataInizio, iscritto.getCodiceIdentificativo());
        dataManager.aggiungiAbbonamento(iscritto, abbonamento);
        try {
            dataManager.salvaDati();
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio dei dati: " + e.getMessage());
        }
    }

    public void aggiungiAbbonamentoAnnuale(Iscritto iscritto, LocalDate dataInizio) {
        Abbonamento abbonamento = new AbbonamentoAnnuale(dataInizio, iscritto.getCodiceIdentificativo());
        dataManager.aggiungiAbbonamento(iscritto, abbonamento);
        try {
            dataManager.salvaDati();
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio dei dati: " + e.getMessage());
        }
    }

    public List<Abbonamento> getAbbonamenti(Iscritto iscritto) {
        return dataManager.getAbbonamenti(iscritto);
    }

    public List<Abbonamento> getAbbonamentiAttivi(Iscritto iscritto) {
        return dataManager.getAbbonamentiAttivi(iscritto);
    }

    public List<Abbonamento> getAbbonamentiFuturi(Iscritto iscritto) {
        return dataManager.getAbbonamentiFuturi(iscritto);
    }

    public List<Abbonamento> getStoricoAbbonamenti(Iscritto iscritto) {
        return dataManager.getStoricoAbbonamenti(iscritto);
    }

    public boolean eliminaIscritto(Iscritto iscritto) {
        dataManager.rimuoviIscritto(iscritto);
        try {
            dataManager.salvaDati();
            return true;
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio dei dati: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminaAbbonamento(Iscritto iscritto, Abbonamento abbonamento) {
        try {
            dataManager.rimuoviAbbonamento(iscritto, abbonamento);
            dataManager.salvaDati();
            return true;
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio dei dati: " + e.getMessage());
            return false;
        }
    }

    public boolean prolungaAbbonamento(Iscritto iscritto, Abbonamento abbonamentoAttuale, Abbonamento nuovoAbbonamento) {
        try {
            if (abbonamentoAttuale != null && nuovoAbbonamento != null) {
                dataManager.terminaAbbonamento(iscritto, abbonamentoAttuale);
                dataManager.aggiungiAbbonamento(iscritto, nuovoAbbonamento);
                dataManager.salvaDati();
                return true;
            }
            return false;
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio dei dati: " + e.getMessage());
            return false;
        }
    }

    public Abbonamento cercaAbbonamento(Iscritto iscritto, String codiceAbbonamento) {
        List<Abbonamento> abbonamenti = dataManager.getAbbonamenti(iscritto);
        for (Abbonamento abbonamento : abbonamenti) {
            if (abbonamento.getCodiceIscritto().equals(codiceAbbonamento)) {
                return abbonamento;
            }
        }
        return null;
    }
} 