package manager;

import model.Iscritto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Singleton per la gestione degli iscritti
 */
public class IscrittiManager {
    private static IscrittiManager instance;
    private List<Iscritto> iscritti;
    private final CsvManager csvManager;

    private IscrittiManager() {
        csvManager = CsvManager.getInstance();
        iscritti = csvManager.caricaIscritti();
    }

    public static IscrittiManager getInstance() {
        if (instance == null) {
            instance = new IscrittiManager();
        }
        return instance;
    }

    /**
     * Aggiunge un nuovo iscritto
     * @param iscritto L'iscritto da aggiungere
     */
    public void aggiungiIscritto(Iscritto iscritto) {
        iscritti.add(iscritto);
        csvManager.salvaIscritti(iscritti);
    }

    /**
     * Rimuove un iscritto
     * @param codiceIdentificativo Il codice identificativo dell'iscritto da rimuovere
     * @return true se l'iscritto è stato rimosso, false altrimenti
     */
    public boolean rimuoviIscritto(String codiceIdentificativo) {
        boolean removed = iscritti.removeIf(i -> i.getCodiceIdentificativo().equals(codiceIdentificativo));
        if (removed) {
            csvManager.salvaIscritti(iscritti);
        }
        return removed;
    }

    /**
     * Cerca un iscritto per codice identificativo
     * @param codiceIdentificativo Il codice identificativo da cercare
     * @return L'iscritto trovato, se presente
     */
    public Optional<Iscritto> cercaIscritto(String codiceIdentificativo) {
        return iscritti.stream()
                .filter(i -> i.getCodiceIdentificativo().equals(codiceIdentificativo))
                .findFirst();
    }

    /**
     * @return La lista di tutti gli iscritti
     */
    public List<Iscritto> getTuttiIscritti() {
        return new ArrayList<>(iscritti);
    }

    /**
     * Aggiorna i dati di un iscritto
     * @param iscritto L'iscritto con i dati aggiornati
     * @return true se l'iscritto è stato aggiornato, false altrimenti
     */
    public boolean aggiornaIscritto(Iscritto iscritto) {
        for (int i = 0; i < iscritti.size(); i++) {
            if (iscritti.get(i).getCodiceIdentificativo().equals(iscritto.getCodiceIdentificativo())) {
                iscritti.set(i, iscritto);
                csvManager.salvaIscritti(iscritti);
                return true;
            }
        }
        return false;
    }
} 