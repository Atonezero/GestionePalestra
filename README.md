# GestionePalestra

Applicazione desktop Java per la gestione di iscritti e abbonamenti di una palestra.

## Struttura del Progetto

Il progetto è organizzato nei seguenti package:
- `model`: Contiene le classi del modello dati
- `view`: Contiene le interfacce grafiche Swing
- `presenter`: Contiene i presenter per la logica di business
- `manager`: Contiene i singleton per la gestione dei dati

## Requisiti
- Java 8 o superiore
- Nessuna dipendenza esterna

## Compilazione ed Esecuzione

1. Clonare il repository
2. Aprire il progetto in un IDE Java (Eclipse, IntelliJ, ecc.)
3. Compilare il progetto
4. Eseguire la classe `Main` nel package principale

## Funzionalità
- Gestione iscritti (CRUD)
- Gestione abbonamenti (mensili e annuali)
- Visualizzazione abbonamenti attivi e storico
- Persistenza dei dati su file CSV 