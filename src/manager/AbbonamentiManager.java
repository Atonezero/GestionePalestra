package manager;

import model.Abbonamento;
import model.AbbonamentoMensile;
import model.AbbonamentoAnnuale;
import model.Iscritto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Singleton per la gestione degli abbonamenti
 */
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

    /**
     * Aggiunge un nuovo abbonamento mensile
     * @param iscritto L'iscritto a cui aggiungere l'abbonamento
     * @param dataInizio Data di inizio dell'abbonamento
     */
    public void aggiungiAbbonamentoMensile(Iscritto iscritto, LocalDate dataInizio) {
        Abbonamento abbonamento = new AbbonamentoMensile(dataInizio);
        iscritto.aggiungiAbbonamentoAttivo(abbonamento);
        abbonamenti.add(abbonamento);
        csvManager.salvaAbbonamenti(abbonamenti);
    }

    /**
     * Aggiunge un nuovo abbonamento annuale
     * @param iscritto L'iscritto a cui aggiungere l'abbonamento
     * @param dataInizio Data di inizio dell'abbonamento
     */
    public void aggiungiAbbonamentoAnnuale(Iscritto iscritto, LocalDate dataInizio) {
        Abbonamento abbonamento = new AbbonamentoAnnuale(dataInizio);
        iscritto.aggiungiAbbonamentoAttivo(abbonamento);
        abbonamenti.add(abbonamento);
        csvManager.salvaAbbonamenti(abbonamenti);
    }

    /**
     * Termina un abbonamento attivo
     * @param iscritto L'iscritto di cui terminare l'abbonamento
     * @param abbonamento L'abbonamento da terminare
     */
    public void terminaAbbonamento(Iscritto iscritto, Abbonamento abbonamento) {
        iscritto.terminaAbbonamento(abbonamento);
        csvManager.salvaAbbonamenti(abbonamenti);
    }

    /**
     * @param iscritto L'iscritto di cui ottenere gli abbonamenti attivi
     * @return Lista degli abbonamenti attivi dell'iscritto
     */
    public List<Abbonamento> getAbbonamentiAttivi(Iscritto iscritto) {
        return new ArrayList<>(iscritto.getAbbonamentiAttivi());
    }

    /**
     * @param iscritto L'iscritto di cui ottenere lo storico abbonamenti
     * @return Lista degli abbonamenti scaduti dell'iscritto
     */
    public List<Abbonamento> getStoricoAbbonamenti(Iscritto iscritto) {
        return new ArrayList<>(iscritto.getStoricoAbbonamenti());
    }

    /**
     * @return Lista di tutti gli abbonamenti
     */
    public List<Abbonamento> getTuttiAbbonamenti() {
        return new ArrayList<>(abbonamenti);
    }
} 