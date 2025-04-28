package view;

import model.Iscritto;
import manager.IscrittiManager;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Pannello per la visualizzazione di tutti gli iscritti
 */
public class VisualizzaIscrittiPanel extends JPanel {
    private JTable iscrittiTable;
    private DefaultTableModel tableModel;

    public VisualizzaIscrittiPanel() {
        setLayout(new BorderLayout());

        // Creazione del modello della tabella
        String[] columnNames = {"Nome", "Cognome", "Codice Identificativo"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Creazione della tabella
        iscrittiTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(iscrittiTable);
        add(scrollPane, BorderLayout.CENTER);

        // Pulsante per aggiornare la lista
        JButton aggiornaButton = new JButton("Aggiorna Lista");
        aggiornaButton.addActionListener(e -> aggiornaListaIscritti());
        add(aggiornaButton, BorderLayout.SOUTH);

        // Caricamento iniziale dei dati
        aggiornaListaIscritti();
    }

    private void aggiornaListaIscritti() {
        // Pulisci la tabella
        tableModel.setRowCount(0);

        // Carica gli iscritti
        List<Iscritto> iscritti = IscrittiManager.getInstance().getTuttiIscritti();
        for (Iscritto iscritto : iscritti) {
            Object[] rowData = {
                iscritto.getNome(),
                iscritto.getCognome(),
                iscritto.getCodiceIdentificativo()
            };
            tableModel.addRow(rowData);
        }
    }
} 