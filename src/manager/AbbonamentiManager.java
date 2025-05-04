package manager;

import model.Abbonamento;
import model.AbbonamentoMensile;
import model.AbbonamentoAnnuale;
import model.Iscritto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

public class AbbonamentiManager {
    private static AbbonamentiManager instance;
    private final CSVManager csvManager;
    private final HashMap<Iscritto, List<Abbonamento>> abbonamenti;
    private final IscrittiManager iscrittiManager;

    private AbbonamentiManager() {
        this.csvManager = new CSVManager();
        this.abbonamenti = new HashMap<>();
        iscrittiManager = IscrittiManager.getInstance();
        caricaDati();
    }

    public static AbbonamentiManager getInstance() {
        if (instance == null) {
            instance = new AbbonamentiManager();
        }
        return instance;
    }

    private void caricaDati() {
        try {
            Map<Iscritto, List<Abbonamento>> dati = csvManager.caricaDati();
            abbonamenti.clear();
            abbonamenti.putAll(dati);
        } catch (IOException e) {
            System.err.println("Errore nel caricamento dei dati: " + e.getMessage());
        }
    }

    public void aggiungiAbbonamentoMensile(Iscritto iscritto, LocalDate dataInizio) throws IOException {
        Abbonamento abbonamento = new AbbonamentoMensile(dataInizio, iscritto.getCodiceIdentificativo());
        iscritto.aggiungiAbbonamentoAttivo(abbonamento);
        abbonamenti.computeIfAbsent(iscritto, k -> new ArrayList<>()).add(abbonamento);
        csvManager.salvaDati(abbonamenti);
    }

    public void aggiungiAbbonamentoAnnuale(Iscritto iscritto, LocalDate dataInizio) throws IOException {
        Abbonamento abbonamento = new AbbonamentoAnnuale(dataInizio, iscritto.getCodiceIdentificativo());
        iscritto.aggiungiAbbonamentoAttivo(abbonamento);
        abbonamenti.computeIfAbsent(iscritto, k -> new ArrayList<>()).add(abbonamento);
        csvManager.salvaDati(abbonamenti);
    }

    public void terminaAbbonamento(Iscritto iscritto, Abbonamento abbonamento) throws IOException {
        iscritto.terminaAbbonamento(abbonamento);
        abbonamenti.computeIfPresent(iscritto, (k, v) -> {
            v.remove(abbonamento);
            return v;
        });
        csvManager.salvaDati(abbonamenti);
    }

    public List<Abbonamento> getAbbonamentiAttivi(Iscritto iscritto) {
        return new ArrayList<>(abbonamenti.getOrDefault(iscritto, new ArrayList<>()));
    }

    public List<Abbonamento> getStoricoAbbonamenti(Iscritto iscritto) {
        return new ArrayList<>(iscritto.getStoricoAbbonamenti());
    }

    public List<Abbonamento> getTuttiAbbonamenti() {
        List<Abbonamento> tuttiAbbonamenti = new ArrayList<>();
        for (List<Abbonamento> lista : abbonamenti.values()) {
            tuttiAbbonamenti.addAll(lista);
        }
        return tuttiAbbonamenti;
    }
}
