package model;

import java.time.LocalDate;


public class AbbonamentoMensile implements Abbonamento {
    private LocalDate dataInizio;
    private LocalDate dataFine;

    public AbbonamentoMensile(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
        this.dataFine = dataInizio.plusMonths(1);
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
    public String toString() {
        return "Abbonamento Mensile - Inizio: " + dataInizio + ", Fine: " + dataFine;
    }
} 