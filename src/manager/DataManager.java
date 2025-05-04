package manager;

import model.Iscritto;
import model.Abbonamento;
import java.util.*;
import java.io.*;

public class DataManager {
    private Map<Iscritto, List<Abbonamento>> iscrittiAbbonamenti;
    private manager.CSVManager csvManager;

    public DataManager() {
        this.iscrittiAbbonamenti = new HashMap<>();
        this.csvManager = new manager.CSVManager();
        caricaDati();
    }

    public void aggiungiIscritto(Iscritto iscritto) {
        if (!iscrittiAbbonamenti.containsKey(iscritto)) {
            iscrittiAbbonamenti.put(iscritto, new ArrayList<>());
        }
    }

    public void aggiungiAbbonamento(Iscritto iscritto, Abbonamento abbonamento) {
        if (!iscrittiAbbonamenti.containsKey(iscritto)) {
            iscrittiAbbonamenti.put(iscritto, new ArrayList<>());
        }
        iscrittiAbbonamenti.get(iscritto).add(abbonamento);
    }

    public List<Abbonamento> getAbbonamenti(Iscritto iscritto) {
        return iscrittiAbbonamenti.getOrDefault(iscritto, new ArrayList<>());
    }

    public List<Abbonamento> getAbbonamentiAttivi(Iscritto iscritto) {
        List<Abbonamento> abbonamenti = getAbbonamenti(iscritto);
        List<Abbonamento> attivi = new ArrayList<>();
        for (Abbonamento a : abbonamenti) {
            if (a.isAttivo()) {
                attivi.add(a);
            }
        }
        return attivi;
    }

    public List<Abbonamento> getAbbonamentiFuturi(Iscritto iscritto) {
        List<Abbonamento> abbonamenti = getAbbonamenti(iscritto);
        List<Abbonamento> futuri = new ArrayList<>();
        for (Abbonamento a : abbonamenti) {
            if (a.isFuturo()) {
                futuri.add(a);
            }
        }
        return futuri;
    }

    public List<Abbonamento> getStoricoAbbonamenti(Iscritto iscritto) {
        List<Abbonamento> abbonamenti = getAbbonamenti(iscritto);
        List<Abbonamento> storico = new ArrayList<>();
        for (Abbonamento a : abbonamenti) {
            if (a.isScaduto()) {
                storico.add(a);
            }
        }
        return storico;
    }

    public void rimuoviIscritto(Iscritto iscritto) {
        iscrittiAbbonamenti.remove(iscritto);
    }

    public void rimuoviAbbonamento(Iscritto iscritto, Abbonamento abbonamento) {
        if (iscrittiAbbonamenti.containsKey(iscritto)) {
            List<Abbonamento> abbonamenti = iscrittiAbbonamenti.get(iscritto);
            if (abbonamenti.remove(abbonamento)) {
                iscritto.terminaAbbonamento(abbonamento);
            }
        }
    }

    public List<Iscritto> getTuttiIscritti() {
        return new ArrayList<>(iscrittiAbbonamenti.keySet());
    }

    public void salvaDati() throws IOException {
        csvManager.salvaDati(iscrittiAbbonamenti);
    }

    public void caricaDati() {
        try {
            Map<Iscritto, List<Abbonamento>> dati = csvManager.caricaDati();
            iscrittiAbbonamenti.clear();
            iscrittiAbbonamenti.putAll(dati);
        } catch (IOException e) {
            System.err.println("Errore nel caricamento dei dati: " + e.getMessage());
        }
    }

    public void terminaAbbonamento(Iscritto iscritto, Abbonamento abbonamento) {
        if (iscrittiAbbonamenti.containsKey(iscritto)) {
            List<Abbonamento> abbonamenti = iscrittiAbbonamenti.get(iscritto);
            if (abbonamenti.remove(abbonamento)) {
                iscritto.terminaAbbonamento(abbonamento);
            }
        }
    }
} 