package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import presenter.MainPresenter;
import model.Iscritto;
import model.Abbonamento;


public class VisualizzaAbbonamentiPanel extends JPanel {
    private MainPresenter presenter;
    private JTextField searchField;
    private JTable iscrittiTable;
    private JTable abbonamentiTable;
    private DefaultTableModel iscrittiModel;
    private DefaultTableModel abbonamentiModel;
    private JButton eliminaButton;
    private JButton ricaricaButton;

    public VisualizzaAbbonamentiPanel(MainPresenter presenter) {
        this.presenter = presenter;
        initComponents();
        aggiornaIscrittiTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Cerca");
        searchButton.addActionListener(e -> cercaIscritto());
        searchPanel.add(new JLabel("Codice Iscritto:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        
        JPanel mainPanel = new JPanel(new GridLayout(2, 1));

        
        iscrittiModel = new DefaultTableModel(new String[]{"Nome", "Cognome", "Codice"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        iscrittiTable = new JTable(iscrittiModel);
        iscrittiTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        iscrittiTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = iscrittiTable.getSelectedRow();
                if (row >= 0) {
                    String codice = (String) iscrittiModel.getValueAt(row, 2);
                    aggiornaAbbonamentiTable(codice);
                }
            }
        });
        JScrollPane iscrittiScroll = new JScrollPane(iscrittiTable);
        mainPanel.add(iscrittiScroll);

        
        abbonamentiModel = new DefaultTableModel(new String[]{"Tipo", "Data Inizio", "Data Fine", "Stato"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        abbonamentiTable = new JTable(abbonamentiModel);
        abbonamentiTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane abbonamentiScroll = new JScrollPane(abbonamentiTable);
        mainPanel.add(abbonamentiScroll);

        add(mainPanel, BorderLayout.CENTER);

        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        eliminaButton = new JButton("Elimina");
        ricaricaButton = new JButton("Ricarica");
        eliminaButton.addActionListener(e -> eliminaSelezionato());
        ricaricaButton.addActionListener(e -> ricaricaPagina());
        buttonPanel.add(ricaricaButton);
        buttonPanel.add(eliminaButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void cercaIscritto() {
        String codice = searchField.getText().trim();
        if (!codice.isEmpty()) {
            Iscritto iscritto = presenter.cercaIscritto(codice);
            if (iscritto != null) {
                
                for (int i = 0; i < iscrittiModel.getRowCount(); i++) {
                    if (iscrittiModel.getValueAt(i, 2).equals(codice)) {
                        iscrittiTable.setRowSelectionInterval(i, i);
                        aggiornaAbbonamentiTable(codice);
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Iscritto non trovato", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void aggiornaIscrittiTable() {
        iscrittiModel.setRowCount(0);
        for (Iscritto iscritto : presenter.getTuttiIscritti()) {
            iscrittiModel.addRow(new Object[]{
                iscritto.getNome(),
                iscritto.getCognome(),
                iscritto.getCodiceIdentificativo()
            });
        }
    }

    private void aggiornaAbbonamentiTable(String codiceIscritto) {
        abbonamentiModel.setRowCount(0);
        Iscritto iscritto = presenter.cercaIscritto(codiceIscritto);
        if (iscritto != null) {
            for (Abbonamento abbonamento : presenter.getAbbonamenti(iscritto)) {
                String stato;
                Color colore;
                if (abbonamento.isScaduto()) {
                    stato = "SCADUTO";
                    colore = Color.RED;
                } else if (abbonamento.isAttivo()) {
                    stato = "ATTIVO";
                    colore = Color.GREEN;
                } else {
                    stato = "FUTURO";
                    colore = Color.YELLOW;
                }
                abbonamentiModel.addRow(new Object[]{
                    abbonamento.getTipo(),
                    abbonamento.getDataInizio().format(Abbonamento.DATE_FORMATTER),
                    abbonamento.getDataFine().format(Abbonamento.DATE_FORMATTER),
                    stato
                });
            }
        }
    }

    private void eliminaSelezionato() {
        int selectedIscrittoRow = iscrittiTable.getSelectedRow();
        int selectedAbbonamentoRow = abbonamentiTable.getSelectedRow();

        if (selectedIscrittoRow >= 0 && selectedAbbonamentoRow >= 0) {
            String codiceIscritto = (String) iscrittiModel.getValueAt(selectedIscrittoRow, 2);
            Iscritto iscritto = presenter.cercaIscritto(codiceIscritto);
            if (iscritto != null) {
                List<Abbonamento> abbonamenti = presenter.getAbbonamenti(iscritto);
                if (selectedAbbonamentoRow < abbonamenti.size()) {
                    Abbonamento abbonamento = abbonamenti.get(selectedAbbonamentoRow);
                    if (presenter.eliminaAbbonamento(iscritto, abbonamento)) {
                        aggiornaAbbonamentiTable(codiceIscritto);
                    }
                }
            }
        } else if (selectedIscrittoRow >= 0) {
            String codiceIscritto = (String) iscrittiModel.getValueAt(selectedIscrittoRow, 2);
            Iscritto iscritto = presenter.cercaIscritto(codiceIscritto);
            if (iscritto != null && presenter.eliminaIscritto(iscritto)) {
                aggiornaIscrittiTable();
                abbonamentiModel.setRowCount(0);
            }
        }
    }
    private void ricaricaPagina() {
        aggiornaIscrittiTable();
    }
}

class DettagliIscrittoDialog extends JDialog {
    public DettagliIscrittoDialog(Iscritto iscritto, MainPresenter presenter) {
        setTitle("Dettagli Iscritto");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setModal(true);

        JPanel mainPanel = new JPanel(new BorderLayout());

        
        JPanel infoPanel = new JPanel(new GridLayout(3, 2));
        infoPanel.add(new JLabel("Nome:"));
        infoPanel.add(new JLabel(iscritto.getNome()));
        infoPanel.add(new JLabel("Cognome:"));
        infoPanel.add(new JLabel(iscritto.getCognome()));
        infoPanel.add(new JLabel("Codice:"));
        infoPanel.add(new JLabel(iscritto.getCodiceIdentificativo()));
        mainPanel.add(infoPanel, BorderLayout.NORTH);

        
        DefaultTableModel model = new DefaultTableModel(new String[]{"Tipo", "Data Inizio", "Data Fine", "Stato"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        for (Abbonamento abbonamento : presenter.getAbbonamenti(iscritto)) {
            String stato;
            if (abbonamento.isScaduto()) {
                stato = "SCADUTO";
            } else if (abbonamento.isAttivo()) {
                stato = "ATTIVO";
            } else {
                stato = "FUTURO";
            }
            model.addRow(new Object[]{
                abbonamento.getTipo(),
                abbonamento.getDataInizio(),
                abbonamento.getDataFine(),
                stato
            });
        }
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(mainPanel);
    }
} 