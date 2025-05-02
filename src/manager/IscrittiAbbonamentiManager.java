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

public class IscrittiAbbonamentiManager {
    private static IscrittiAbbonamentiManager instance;
    private final Map<Iscritto, List<Abbonamento>> iscrittiAbbonamentiMap;
    private final CsvManager csvManager;
    private final IscrittiManager iscrittiManager;
    private final AbbonamentiManager abbonamentiManager;

    private IscrittiAbbonamentiManager() {
        csvManager = CsvManager.getInstance();
        iscrittiManager = IscrittiManager.getInstance();
        abbonamentiManager = AbbonamentiManager.getInstance();
        iscrittiAbbonamentiMap = new HashMap<>();
        initializeMap();
    }

    public static IscrittiAbbonamentiManager getInstance() {
        if (instance == null) {
            instance = new IscrittiAbbonamentiManager();
        }
        return instance;
    }

    private void initializeMap() {
        List<Iscritto> iscritti = iscrittiManager.getTuttiIscritti();
        List<Abbonamento> abbonamenti = abbonamentiManager.getTuttiAbbonamenti();
        
        for (Iscritto iscritto : iscritti) {
            List<Abbonamento> abbonamentiIscritto = abbonamenti.stream()
                .filter(a -> a.getCodiceIscritto().equals(iscritto.getCodiceIdentificativo()))
                .collect(Collectors.toList());
            iscrittiAbbonamentiMap.put(iscritto, abbonamentiIscritto);
        }
    }

    public void aggiungiIscrittoConAbbonamento(Iscritto iscritto, String tipoAbbonamento, LocalDate dataInizio) {
        iscrittiManager.aggiungiIscritto(iscritto);
        
        if (tipoAbbonamento.equals("Mensile")) {
            abbonamentiManager.aggiungiAbbonamentoMensile(iscritto, dataInizio);
        } else {
            abbonamentiManager.aggiungiAbbonamentoAnnuale(iscritto, dataInizio);
        }
        
        aggiornaMappa();
    }

    public List<Abbonamento> getAbbonamentiPerIscritto(Iscritto iscritto) {
        return iscrittiAbbonamentiMap.getOrDefault(iscritto, List.of());
    }

    public Map<Iscritto, List<Abbonamento>> getTuttiIscrittiConAbbonamenti() {
        return new HashMap<>(iscrittiAbbonamentiMap);
    }

    public void aggiornaMappa() {
        iscrittiAbbonamentiMap.clear();
        initializeMap();
    }
}
