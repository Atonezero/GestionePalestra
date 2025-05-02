package presenter;

import model.Iscritto;
import model.Abbonamento;
import manager.IscrittiManager;
import manager.AbbonamentiManager;
import java.time.LocalDate;
import java.util.List;

public class MainPresenter {
    private IscrittiManager iscrittiManager;
    private AbbonamentiManager abbonamentiManager;

    public MainPresenter() {
        iscrittiManager = IscrittiManager.getInstance();
        abbonamentiManager = AbbonamentiManager.getInstance();
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
} 