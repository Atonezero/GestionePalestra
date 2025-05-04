package manager;

import model.Iscritto;
import model.Abbonamento;
import model.AbbonamentoMensile;
import model.AbbonamentoAnnuale;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.io.IOException;

public class IscrittiAbbonamentiManager {
    private static IscrittiAbbonamentiManager instance;
    private final CSVManager csvManager;
    private final Map<Iscritto, List<Abbonamento>> iscrittiAbbonamenti;
    private final IscrittiManager iscrittiManager;
    private final AbbonamentiManager abbonamentiManager;

    private IscrittiAbbonamentiManager() {
        this.csvManager = new CSVManager();
        this.iscrittiAbbonamenti = new HashMap<>();
        this.iscrittiManager = IscrittiManager.getInstance();
        this.abbonamentiManager = AbbonamentiManager.getInstance();
        caricaDati();
    }

    public static IscrittiAbbonamentiManager getInstance() {
        if (instance == null) {
            instance = new IscrittiAbbonamentiManager();
        }
        return instance;
    }

    private void caricaDati() {
        try {
            Map<Iscritto, List<Abbonamento>> dati = csvManager.caricaDati();
            iscrittiAbbonamenti.clear();
            iscrittiAbbonamenti.putAll(dati);
        } catch (IOException e) {
            System.err.println("Errore nel caricamento dei dati: " + e.getMessage());
        }
    }

    public void aggiungiIscrittoConAbbonamento(Iscritto iscritto, String tipoAbbonamento, LocalDate dataInizio) throws IOException {
        iscrittiManager.aggiungiIscritto(iscritto);
    
        if (tipoAbbonamento.equals("Mensile")) {
            abbonamentiManager.aggiungiAbbonamentoMensile(iscritto, dataInizio);
        } else {
            abbonamentiManager.aggiungiAbbonamentoAnnuale(iscritto, dataInizio);
        }
    
        aggiornaMappa();
    }
    

    public List<Abbonamento> getAbbonamentiPerIscritto(Iscritto iscritto) {
        return iscrittiAbbonamenti.getOrDefault(iscritto, List.of());
    }

    public Map<Iscritto, List<Abbonamento>> getTuttiIscrittiConAbbonamenti() {
        return new HashMap<>(iscrittiAbbonamenti);
    }

    public void aggiornaMappa() {
        iscrittiAbbonamenti.clear();
        caricaDati();
    }
}
