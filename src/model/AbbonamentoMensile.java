package model;

import java.time.LocalDate;


public class AbbonamentoMensile implements Abbonamento {
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private String codiceIscritto;

    public AbbonamentoMensile(LocalDate dataInizio, String codiceIscritto) {
        this.dataInizio = dataInizio;
        this.dataFine = dataInizio.plusMonths(1);
        this.codiceIscritto = codiceIscritto;
    }

    @Override
    public LocalDate getDataInizio() {
        return dataInizio;
    }

    @Override
    public LocalDate getDataFine() {
        return dataFine;
    }

    @Override
    public String getTipo() {
        return "Mensile";
    }

    @Override
    public boolean isAttivo() {
        LocalDate oggi = LocalDate.now();
        return !oggi.isBefore(dataInizio) && !oggi.isAfter(dataFine);
    }

    @Override
    public String getCodiceIscritto() {
        return codiceIscritto;
    }

    @Override
    public String toString() {
        return "Abbonamento Mensile - Inizio: " + dataInizio + ", Fine: " + dataFine;
    }
} 