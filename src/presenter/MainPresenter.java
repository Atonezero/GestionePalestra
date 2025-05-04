package presenter;

import model.Iscritto;
import model.Abbonamento;
import manager.IscrittiManager;
import manager.AbbonamentiManager;
import manager.DataManager;
import java.time.LocalDate;
import java.util.List;
import java.io.IOException;

public class MainPresenter {
    private IscrittiManager iscrittiManager;
    private AbbonamentiManager abbonamentiManager;
    private DataManager dataManager;

    public MainPresenter() {
        iscrittiManager = IscrittiManager.getInstance();
        abbonamentiManager = AbbonamentiManager.getInstance();
        dataManager = new DataManager();
        caricaDati();
    }

    private void caricaDati() {
        try {
            List<Iscritto> iscritti = dataManager.caricaDati();
            for (Iscritto iscritto : iscritti) {
                iscrittiManager.aggiungiIscritto(iscritto);
            }
        } catch (IOException e) {
            System.err.println("Errore nel caricamento dei dati: " + e.getMessage());
        }
    }

    public void salvaDati() {
        try {
            dataManager.salvaDati(iscrittiManager.getTuttiIscritti());
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio dei dati: " + e.getMessage());
        }
    }

    public void aggiungiIscritto(String nome, String cognome, String codice) {
        Iscritto nuovoIscritto = new Iscritto(nome, cognome, codice);
        iscrittiManager.aggiungiIscritto(nuovoIscritto);
    }

    public List<Iscritto> getTuttiIscritti() {
        return iscrittiManager.getTuttiIscritti();
    }

    public Iscritto cercaIscritto(String codice) {
        return iscrittiManager.cercaIscritto(codice).orElse(null);
    }

    public void aggiungiAbbonamentoMensile(Iscritto iscritto, LocalDate dataInizio) {
        abbonamentiManager.aggiungiAbbonamentoMensile(iscritto, dataInizio);
    }

    public void aggiungiAbbonamentoAnnuale(Iscritto iscritto, LocalDate dataInizio) {
        abbonamentiManager.aggiungiAbbonamentoAnnuale(iscritto, dataInizio);
    }

    public List<Abbonamento> getTuttiAbbonamenti() {
        return abbonamentiManager.getTuttiAbbonamenti();
    }

    public List<Abbonamento> getAbbonamentiAttivi(Iscritto iscritto) {
        return abbonamentiManager.getAbbonamentiAttivi(iscritto);
    }

    public List<Abbonamento> getStoricoAbbonamenti(Iscritto iscritto) {
        return abbonamentiManager.getStoricoAbbonamenti(iscritto);
    }

    public boolean eliminaAbbonamento(String codiceAbbonamento) {
        for (Iscritto iscritto : iscrittiManager.getTuttiIscritti()) {
            if (iscritto.rimuoviAbbonamentoPerCodice(codiceAbbonamento)) {
                salvaDati();
                return true;
            }
        }
        return false;
    }

    public void prolungaAbbonamento(Iscritto iscritto, Abbonamento abbonamentoAttuale, Abbonamento nuovoAbbonamento) {
        iscritto.prolungaAbbonamento(abbonamentoAttuale, nuovoAbbonamento);
        salvaDati();
    }

    public Abbonamento getAbbonamentoAttivoPiuRecente(Iscritto iscritto) {
        return iscritto.getAbbonamentoAttivoPiuRecente();
    }

    public boolean haAbbonamentoAttivo(Iscritto iscritto) {
        return iscritto.haAbbonamentoAttivo();
    }
} 