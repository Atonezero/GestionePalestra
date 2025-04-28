package view;

import model.Iscritto;
import model.Abbonamento;
import manager.IscrittiManager;
import manager.AbbonamentiManager;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Optional;

/**
 * Pannello per la visualizzazione degli abbonamenti
 */
public class VisualizzaAbbonamentiPanel extends JPanel {
    private JTextField codiceField;
    private JButton cercaButton;
    private JTable abbonamentiTable;
    private DefaultTableModel tableModel;
    private JTabbedPane tabbedPane;

    public VisualizzaAbbonamentiPanel() {
        setLayout(new BorderLayout());

        // Pannello superiore per la ricerca
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Codice Iscritto:"));
        codiceField = new JTextField(20);
        searchPanel.add(codiceField);
        cercaButton = new JButton("Cerca");
        searchPanel.add(cercaButton);
        add(searchPanel, BorderLayout.NORTH);

        // TabbedPane per abbonamenti attivi e storico
        tabbedPane = new JTabbedPane();
        
        // Tab abbonamenti attivi
        JPanel attiviPanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Tipo", "Data Inizio", "Data Fine"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        abbonamentiTable = new JTable(tableModel);
        attiviPanel.add(new JScrollPane(abbonamentiTable), BorderLayout.CENTER);
        tabbedPane.addTab("Abbonamenti Attivi", attiviPanel);

        // Tab storico abbonamenti
        JPanel storicoPanel = new JPanel(new BorderLayout());
        DefaultTableModel storicoModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable storicoTable = new JTable(storicoModel);
        storicoPanel.add(new JScrollPane(storicoTable), BorderLayout.CENTER);
        tabbedPane.addTab("Storico Abbonamenti", storicoPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Gestione evento cerca
        cercaButton.addActionListener(e -> {
            String codice = codiceField.getText();
            if (codice.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Inserire un codice identificativo",
                        "Errore",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Optional<Iscritto> iscritto = IscrittiManager.getInstance().cercaIscritto(codice);
            if (iscritto.isPresent()) {
                Iscritto i = iscritto.get();
                
                // Aggiorna tabella abbonamenti attivi
                tableModel.setRowCount(0);
                for (Abbonamento a : i.getAbbonamentiAttivi()) {
                    Object[] rowData = {
                        a.getTipo(),
                        a.getDataInizio(),
                        a.getDataFine()
                    };
                    tableModel.addRow(rowData);
                }

                // Aggiorna tabella storico
                storicoModel.setRowCount(0);
                for (Abbonamento a : i.getStoricoAbbonamenti()) {
                    Object[] rowData = {
                        a.getTipo(),
                        a.getDataInizio(),
                        a.getDataFine()
                    };
                    storicoModel.addRow(rowData);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Nessun iscritto trovato con il codice: " + codice,
                        "Errore",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
} 