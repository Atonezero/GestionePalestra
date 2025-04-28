package model;

import java.time.LocalDate;

/**
 * Interfaccia che definisce i metodi comuni per tutti i tipi di abbonamento
 */
public interface Abbonamento {
    /**
     * @return La data di inizio dell'abbonamento
     */
    LocalDate getDataInizio();

    /**
     * @return La data di fine dell'abbonamento
     */
    LocalDate getDataFine();

    /**
     * @return Il tipo di abbonamento (mensile/annuale)
     */
    String getTipo();

    /**
     * @return true se l'abbonamento è attivo, false altrimenti
     */
    boolean isAttivo();
    
}