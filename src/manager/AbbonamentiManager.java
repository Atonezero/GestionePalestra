package manager;

import model.Abbonamento;
import model.AbbonamentoMensile;
import model.AbbonamentoAnnuale;
import model.Iscritto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AbbonamentiManager {
    private static AbbonamentiManager instance;
    private List<Abbonamento> abbonamenti;
    private final CsvManager csvManager;
    private final IscrittiManager iscrittiManager;

    private AbbonamentiManager() {
        csvManager = CsvManager.getInstance();
        iscrittiManager = IscrittiManager.getInstance();
        abbonamenti = csvManager.caricaAbbonamenti();
    }

    public static AbbonamentiManager getInstance() {
        if (instance == null) {
            instance = new AbbonamentiManager();
        }
        return instance;
    }


    public void aggiungiAbbonamentoMensile(Iscritto iscritto, LocalDate dataInizio) {
        Abbonamento abbonamento = new AbbonamentoMensile(dataInizio, iscritto.getCodiceIdentificativo());
        iscritto.aggiungiAbbonamentoAttivo(abbonamento);
        abbonamenti.add(abbonamento);
        csvManager.salvaAbbonamenti(abbonamenti);
    }


    public void aggiungiAbbonamentoAnnuale(Iscritto iscritto, LocalDate dataInizio) {
        Abbonamento abbonamento = new AbbonamentoAnnuale(dataInizio, iscritto.getCodiceIdentificativo());
        iscritto.aggiungiAbbonamentoAttivo(abbonamento);
        abbonamenti.add(abbonamento);
        csvManager.salvaAbbonamenti(abbonamenti);
    }


    public void terminaAbbonamento(Iscritto iscritto, Abbonamento abbonamento) {
        iscritto.terminaAbbonamento(abbonamento);
        csvManager.salvaAbbonamenti(abbonamenti);
    }


    public List<Abbonamento> getAbbonamentiAttivi(Iscritto iscritto) {
        return new ArrayList<>(iscritto.getAbbonamentiAttivi());
    }


    public List<Abbonamento> getStoricoAbbonamenti(Iscritto iscritto) {
        return new ArrayList<>(iscritto.getStoricoAbbonamenti());
    }

    public List<Abbonamento> getTuttiAbbonamenti() {
        return new ArrayList<>(abbonamenti);
    }
} 