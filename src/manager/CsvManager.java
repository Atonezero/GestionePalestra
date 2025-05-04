package manager;

import model.Iscritto;
import model.Abbonamento;
import java.util.*;
import java.io.*;
import java.nio.file.*;

public class CSVManager {
    private static final String DATA_DIR = "data";
    private static final String FILE_PATH = DATA_DIR + "/iscritti_abbonamenti.csv";

    public CSVManager() {
        creaDirectoryData();
    }

    private void creaDirectoryData() {
        try {
            Path dataPath = Paths.get(DATA_DIR);
            if (!Files.exists(dataPath)) {
                Files.createDirectories(dataPath);
            }
        } catch (IOException e) {
            System.err.println("Errore nella creazione della directory data: " + e.getMessage());
        }
    }

    public void salvaDati(Map<Iscritto, List<Abbonamento>> iscrittiAbbonamenti) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<Iscritto, List<Abbonamento>> entry : iscrittiAbbonamenti.entrySet()) {
                Iscritto iscritto = entry.getKey();
                for (Abbonamento abbonamento : entry.getValue()) {
                    writer.println(String.format("%s,%s,%s,%s,%s,%s",
                        iscritto.getNome(),
                        iscritto.getCognome(),
                        iscritto.getCodiceIdentificativo(),
                        abbonamento.getTipo(),
                        abbonamento.getDataInizio().format(Abbonamento.DATE_FORMATTER),
                        abbonamento.getDataFine().format(Abbonamento.DATE_FORMATTER)));
                }
            }
        }
    }

    public Map<Iscritto, List<Abbonamento>> caricaDati() throws IOException {
        Map<Iscritto, List<Abbonamento>> iscrittiAbbonamenti = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    Iscritto iscritto = new Iscritto(parts[0], parts[1], parts[2]);
                    Abbonamento abbonamento = Abbonamento.creaAbbonamento(
                        parts[3], // tipo
                        parts[2], // codice iscritto
                        parts[4], // data inizio
                        parts[5]  // data fine
                    );
                    
                    if (!iscrittiAbbonamenti.containsKey(iscritto)) {
                        iscrittiAbbonamenti.put(iscritto, new ArrayList<>());
                    }
                    iscrittiAbbonamenti.get(iscritto).add(abbonamento);
                }
            }
        } catch (FileNotFoundException e) {
            // Il file non esiste ancora, verrà creato al primo salvataggio
            System.out.println("File dei dati non trovato. Verrà creato al primo salvataggio.");
        }
        
        return iscrittiAbbonamenti;
    }
}

//ManagerCsv salva sul file CSV abbonamenti e iscritti separatamente, uso del HashMap per aggiungere iscritti
//e abbonamenti contemporaneamente,aggiungere ad ogni nuova iscrizione, caricamento automatico all' avvio.
//Crea una nuova classe che si occupi di gestire un HashMap con Iscritto, e Storico Abbonamenti, ovvero un Array list di abbonamenti