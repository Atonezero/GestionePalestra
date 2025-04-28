package manager;

import model.Iscritto;
import model.Abbonamento;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton per la gestione della lettura e scrittura dei file CSV
 */
public class CsvManager {
    private static CsvManager instance;
    private static final String ISCRITTI_FILE = "iscritti.csv";
    private static final String ABBONAMENTI_FILE = "abbonamenti.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private CsvManager() {}

    public static CsvManager getInstance() {
        if (instance == null) {
            instance = new CsvManager();
        }
        return instance;
    }

    /**
     * Salva la lista degli iscritti su file CSV
     * @param iscritti Lista degli iscritti da salvare
     */
    public void salvaIscritti(List<Iscritto> iscritti) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ISCRITTI_FILE))) {
            writer.println("nome,cognome,codiceIdentificativo");
            for (Iscritto iscritto : iscritti) {
                writer.println(String.format("%s,%s,%s",
                    iscritto.getNome(),
                    iscritto.getCognome(),
                    iscritto.getCodiceIdentificativo()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carica la lista degli iscritti dal file CSV
     * @return Lista degli iscritti caricati
     */
    public List<Iscritto> caricaIscritti() {
        List<Iscritto> iscritti = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ISCRITTI_FILE))) {
            // Salta l'header
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    iscritti.add(new Iscritto(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            // Se il file non esiste, restituisce una lista vuota
        }
        return iscritti;
    }

    /**
     * Salva la lista degli abbonamenti su file CSV
     * @param abbonamenti Lista degli abbonamenti da salvare
     */
    public void salvaAbbonamenti(List<Abbonamento> abbonamenti) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ABBONAMENTI_FILE))) {
            writer.println("tipo,dataInizio,dataFine,codiceIscritto");
            for (Abbonamento abbonamento : abbonamenti) {
                writer.println(String.format("%s,%s,%s,%s",
                    abbonamento.getTipo(),
                    abbonamento.getDataInizio().format(DATE_FORMATTER),
                    abbonamento.getDataFine().format(DATE_FORMATTER),
                    abbonamento.getCodiceIscritto()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carica la lista degli abbonamenti dal file CSV
     * @return Lista degli abbonamenti caricati
     */
    public List<Abbonamento> caricaAbbonamenti() {
        List<Abbonamento> abbonamenti = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ABBONAMENTI_FILE))) {
            // Salta l'header
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    LocalDate dataInizio = LocalDate.parse(parts[1], DATE_FORMATTER);
                    LocalDate dataFine = LocalDate.parse(parts[2], DATE_FORMATTER);
                    String codiceIscritto = parts[3];
                    
                    Abbonamento abbonamento;
                    if (parts[0].equals("Mensile")) {
                        abbonamento = new AbbonamentoMensile(dataInizio);
                    } else {
                        abbonamento = new AbbonamentoAnnuale(dataInizio);
                    }
                    abbonamenti.add(abbonamento);
                }
            }
        } catch (IOException e) {
            // Se il file non esiste, restituisce una lista vuota
        }
        return abbonamenti;
    }
} 