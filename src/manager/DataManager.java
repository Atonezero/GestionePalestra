package manager;

import model.Abbonamento;
import model.Iscritto;
import model.AbbonamentoMensile;
import model.AbbonamentoAnnuale;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String FILE_ISCRITTI = "iscritti.csv";
    private static final String FILE_ABBONAMENTI = "abbonamenti.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void salvaDati(List<Iscritto> iscritti) throws IOException {
        salvaIscritti(iscritti);
        salvaAbbonamenti(iscritti);
    }

    public List<Iscritto> caricaDati() throws IOException {
        List<Iscritto> iscritti = caricaIscritti();
        caricaAbbonamenti(iscritti);
        return iscritti;
    }

    private void salvaIscritti(List<Iscritto> iscritti) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_ISCRITTI))) {
            writer.println("nome,cognome,codiceIdentificativo");
            for (Iscritto iscritto : iscritti) {
                writer.println(String.format("%s,%s,%s",
                    iscritto.getNome(),
                    iscritto.getCognome(),
                    iscritto.getCodiceIdentificativo()));
            }
        }
    }

    private void salvaAbbonamenti(List<Iscritto> iscritti) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_ABBONAMENTI))) {
            writer.println("codiceIscritto,tipo,dataInizio,dataFine,attivo");
            for (Iscritto iscritto : iscritti) {
                for (Abbonamento abbonamento : iscritto.getAbbonamentiAttivi()) {
                    writer.println(String.format("%s,%s,%s,%s,true",
                        iscritto.getCodiceIdentificativo(),
                        abbonamento.getTipo(),
                        abbonamento.getDataInizio().format(DATE_FORMATTER),
                        abbonamento.getDataFine().format(DATE_FORMATTER)));
                }
                for (Abbonamento abbonamento : iscritto.getStoricoAbbonamenti()) {
                    writer.println(String.format("%s,%s,%s,%s,false",
                        iscritto.getCodiceIdentificativo(),
                        abbonamento.getTipo(),
                        abbonamento.getDataInizio().format(DATE_FORMATTER),
                        abbonamento.getDataFine().format(DATE_FORMATTER)));
                }
            }
        }
    }

    private List<Iscritto> caricaIscritti() throws IOException {
        List<Iscritto> iscritti = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_ISCRITTI))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    iscritti.add(new Iscritto(parts[0], parts[1], parts[2]));
                }
            }
        }
        return iscritti;
    }

    private void caricaAbbonamenti(List<Iscritto> iscritti) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_ABBONAMENTI))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String codiceIscritto = parts[0];
                    String tipo = parts[1];
                    LocalDate dataInizio = LocalDate.parse(parts[2], DATE_FORMATTER);
                    LocalDate dataFine = LocalDate.parse(parts[3], DATE_FORMATTER);
                    boolean attivo = Boolean.parseBoolean(parts[4]);

                    Iscritto iscritto = iscritti.stream()
                        .filter(i -> i.getCodiceIdentificativo().equals(codiceIscritto))
                        .findFirst()
                        .orElse(null);

                    if (iscritto != null) {
                        Abbonamento abbonamento;
                        if (tipo.equals("MENSILE")) {
                            abbonamento = new AbbonamentoMensile(dataInizio, codiceIscritto);
                        } else {
                            abbonamento = new AbbonamentoAnnuale(dataInizio, codiceIscritto);
                        }

                        if (attivo) {
                            iscritto.aggiungiAbbonamentoAttivo(abbonamento);
                        } else {
                            iscritto.getStoricoAbbonamenti().add(abbonamento);
                        }
                    }
                }
            }
        }
    }
} 