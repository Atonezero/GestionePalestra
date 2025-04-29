package manager;

import model.Iscritto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void aggiungiIscritto(Iscritto iscritto) {
        if (iscritti.stream().anyMatch(i -> i.getCodiceIdentificativo().equals(iscritto.getCodiceIdentificativo()))) {
            throw new IllegalArgumentException("Codice identificativo già esistente");
        }
        iscritti.add(iscritto);
        csvManager.salvaIscritti(iscritti);
    }

    public boolean rimuoviIscritto(String codiceIdentificativo) {
        boolean removed = iscritti.removeIf(i -> i.getCodiceIdentificativo().equals(codiceIdentificativo));
        if (removed) {
            csvManager.salvaIscritti(iscritti);
        }
        return removed;
    }

    public Optional<Iscritto> cercaIscritto(String codiceIdentificativo) {
        return iscritti.stream()
                .filter(i -> i.getCodiceIdentificativo().equals(codiceIdentificativo))
                .findFirst();
    }

    public List<Iscritto> getTuttiIscritti() {
        return new ArrayList<>(iscritti);
    }

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
