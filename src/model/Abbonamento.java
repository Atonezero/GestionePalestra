package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Abbonamento {
    protected LocalDate dataInizio;
    protected LocalDate dataFine;
    protected String codiceIscritto;
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    protected Abbonamento(LocalDate dataInizio, String codiceIscritto) {
        this.dataInizio = dataInizio;
        this.codiceIscritto = codiceIscritto;
    }

    public String getTipo() {
        return "";
    };

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public String getCodiceIscritto() {
        return codiceIscritto;
    }

    public boolean isAttivo() {
        LocalDate oggi = LocalDate.now();
        return !oggi.isBefore(dataInizio) && !oggi.isAfter(dataFine);
    }

    public boolean isFuturo() {
        return LocalDate.now().isBefore(dataInizio);
    }

    public boolean isScaduto() {
        return LocalDate.now().isAfter(dataFine);
    }

    public static Abbonamento creaAbbonamento(String tipo, String codiceIscritto, String dataInizioStr, String dataFineStr) {
        LocalDate dataInizio = LocalDate.parse(dataInizioStr, DATE_FORMATTER);
        LocalDate dataFine = LocalDate.parse(dataFineStr, DATE_FORMATTER);
        
        if (tipo.equals("MENSILE")) {
            return new AbbonamentoMensile(dataInizio, codiceIscritto);
        } else {
            return new AbbonamentoAnnuale(dataInizio, codiceIscritto);
        }
    }

    @Override
    public String toString() {
        return String.format("%s - Dal %s al %s", 
            getTipo(),
            dataInizio.format(DATE_FORMATTER),
            dataFine.format(DATE_FORMATTER));
    }
}