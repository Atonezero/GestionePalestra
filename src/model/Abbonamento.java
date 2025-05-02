package model;

import java.time.LocalDate;

public interface Abbonamento {

    LocalDate getDataInizio();

    LocalDate getDataFine();

    String getTipo();

    boolean isAttivo();
    
    String getCodiceIscritto();
}