package manager;

import model.Iscritto;
import java.util.*;
import java.io.IOException;

public class IscrittiManager {
    private static IscrittiManager instance;
    private final CSVManager csvManager;
    private final List<Iscritto> iscritti;

    private IscrittiManager() {
        this.csvManager = new CSVManager();
        this.iscritti = new ArrayList<>();
        caricaDati();
    }

    public static IscrittiManager getInstance() {
        if (instance == null) {
            instance = new IscrittiManager();
        }
        return instance;
    }

    private void caricaDati() {
        try {
            Map<Iscritto, List<model.Abbonamento>> dati = csvManager.caricaDati();
            iscritti.clear();
            iscritti.addAll(dati.keySet());
        } catch (IOException e) {
            System.err.println("Errore nel caricamento dei dati: " + e.getMessage());
        }
    }

    public void aggiungiIscritto(Iscritto iscritto) throws IOException {
        if (iscritti.stream().anyMatch(i -> i.getCodiceIdentificativo().equals(iscritto.getCodiceIdentificativo()))) {
            throw new IllegalArgumentException("Codice identificativo già esistente");
        }
        iscritti.add(iscritto);
        Map<Iscritto, List<model.Abbonamento>> dati = new HashMap<>();
        dati.put(iscritto, new ArrayList<>());
        csvManager.salvaDati(dati);
    }

    public boolean rimuoviIscritto(String codiceIdentificativo) throws IOException {
        boolean removed = iscritti.removeIf(i -> i.getCodiceIdentificativo().equals(codiceIdentificativo));
        if (removed) {
            Map<Iscritto, List<model.Abbonamento>> dati = new HashMap<>();
            iscritti.forEach(i -> dati.put(i, new ArrayList<>()));
            csvManager.salvaDati(dati);
        }
        return removed;
    }

	public Iscritto cercaIscritto(String codiceIdentificativo) {
 	   return iscritti.stream()
  	          .filter(i -> i.getCodiceIdentificativo().equals(codiceIdentificativo))
   	          .findFirst()
    	          .orElse(null);  // restituisce null se non trova nulla
	}

    public List<Iscritto> getTuttiIscritti() {
        return new ArrayList<>(iscritti);
    }

    public boolean aggiornaIscritto(Iscritto iscritto) throws IOException {
        for (int i = 0; i < iscritti.size(); i++) {
            if (iscritti.get(i).getCodiceIdentificativo().equals(iscritto.getCodiceIdentificativo())) {
                iscritti.set(i, iscritto);
                Map<Iscritto, List<model.Abbonamento>> dati = new HashMap<>();
                iscritti.forEach(isc -> dati.put(isc, new ArrayList<>()));
                csvManager.salvaDati(dati);
                return true;
            }
        }
        return false;
    }
}
