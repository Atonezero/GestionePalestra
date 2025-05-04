package model;

import java.time.LocalDate;

public class AbbonamentoMensile extends Abbonamento {
    
    public AbbonamentoMensile(LocalDate dataInizio, String codiceIscritto) {
        super(dataInizio, codiceIscritto);
        this.dataFine = dataInizio.plusMonths(1);
    }

    @Override
    public String getTipo() {
        return "MENSILE";
    }
} 