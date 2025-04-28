package model;

import java.time.LocalDate;

/**
 * Implementazione di un abbonamento annuale
 */
public class AbbonamentoAnnuale implements Abbonamento {
    private LocalDate dataInizio;
    private LocalDate dataFine;

    /**
     * Costruttore per creare un nuovo abbonamento annuale
     * @param dataInizio Data di inizio dell'abbonamento
     */
    public AbbonamentoAnnuale(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
        this.dataFine = dataInizio.plusYears(1);
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
    public String toString() {
        return "Abbonamento Annuale - Inizio: " + dataInizio + ", Fine: " + dataFine;
    }
} 