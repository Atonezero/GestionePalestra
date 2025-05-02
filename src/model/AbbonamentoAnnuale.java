package model;

import java.time.LocalDate;


public class AbbonamentoAnnuale implements Abbonamento {
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private String codiceIscritto;

    public AbbonamentoAnnuale(LocalDate dataInizio, String codiceIscritto) {
        this.dataInizio = dataInizio;
        this.dataFine = dataInizio.plusYears(1);
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
        return "Annuale";
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
        return "Abbonamento Annuale - Inizio: " + dataInizio + ", Fine: " + dataFine;
    }
} 