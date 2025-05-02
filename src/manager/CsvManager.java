package manager;

import model.Iscritto;
import model.Abbonamento;
import model.AbbonamentoMensile;
import model.AbbonamentoAnnuale;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


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


    public List<Iscritto> caricaIscritti() {
        List<Iscritto> iscritti = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ISCRITTI_FILE))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    iscritti.add(new Iscritto(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
        }
        return iscritti;
    }

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
                        abbonamento = new AbbonamentoMensile(dataInizio, codiceIscritto);
                    } else {
                        abbonamento = new AbbonamentoAnnuale(dataInizio, codiceIscritto);
                    }
                    abbonamenti.add(abbonamento);
                }
            }
        } catch (IOException e) {
        }
        return abbonamenti;
    }
}

//ManagerCsv salva sul file CSV abbonamenti e iscritti separatamente, uso del HashMap per aggiungere iscritti
//e abbonamenti contemporaneamente,aggiungere ad ogni nuova iscrizione, caricamento automatico all' avvio.
//Crea una nuova classe che si occupi di gestire un HashMap con Iscritto, e Storico Abbonamenti, ovvero un Array list di abbonamenti