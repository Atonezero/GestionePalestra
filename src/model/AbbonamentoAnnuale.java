package model;

import java.time.LocalDate;

public class AbbonamentoAnnuale extends Abbonamento {
    
    public AbbonamentoAnnuale(LocalDate dataInizio, String codiceIscritto) {
        super(dataInizio, codiceIscritto);
        this.dataFine = dataInizio.plusYears(1);
    }

    @Override
    public String getTipo() {
        return "ANNUALE";
    }

    @Override
    public boolean isAttivo() {
        LocalDate oggi = LocalDate.now();
        return !oggi.isBefore(dataInizio) && !oggi.isAfter(dataFine);
    }

    @Override
    public String toString() {
        return "Abbonamento Annuale - Inizio: " + dataInizio + ", Fine: " + dataFine;
    }
} 