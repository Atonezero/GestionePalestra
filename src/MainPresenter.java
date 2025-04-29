package presenter;

import manager.AbbonamentiManager;
import manager.IscrittiManager;
import model.Iscritto;
import model.Abbonamento;
import java.time.LocalDate;

public class MainPresenter {
    private final IscrittiManager iscrittiManager;
    private final AbbonamentiManager abbonamentiManager;

    public MainPresenter() {
        this.iscrittiManager = IscrittiManager.getInstance();
        this.abbonamentiManager = AbbonamentiManager.getInstance();
    }

    public void aggiungiIscritto(Iscritto iscritto) {
        iscrittiManager.aggiungiIscritto(iscritto);
    }

    public boolean rimuoviIscritto(String codiceIdentificativo) {
        return iscrittiManager.rimuoviIscritto(codiceIdentificativo);
    }

    public Iscritto cercaIscritto(String codiceIdentificativo) {
        return iscrittiManager.cercaIscritto(codiceIdentificativo).orElse(null);
    }

    public void aggiungiAbbonamentoMensile(Iscritto iscritto, LocalDate dataInizio) {
        abbonamentiManager.aggiungiAbbonamentoMensile(iscritto, dataInizio);
    }

    public void aggiungiAbbonamentoAnnuale(Iscritto iscritto, LocalDate dataInizio) {
        abbonamentiManager.aggiungiAbbonamentoAnnuale(iscritto, dataInizio);
    }

    public void terminaAbbonamento(Iscritto iscritto, Abbonamento abbonamento) {
        abbonamentiManager.terminaAbbonamento(iscritto, abbonamento);
    }
}
