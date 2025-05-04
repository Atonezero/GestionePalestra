package view;

import model.Iscritto;
import manager.IscrittiManager;
import presenter.MainPresenter;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;


public class VisualizzaIscrittiPanel extends JPanel {
    private JTable iscrittiTable;
    private DefaultTableModel tableModel;
    private MainPresenter presenter;

    public VisualizzaIscrittiPanel(MainPresenter presenter) {
        this.presenter = presenter;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        
        String[] columnNames = {"Nome", "Cognome", "Codice Identificativo"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        
        iscrittiTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(iscrittiTable);
        add(scrollPane, BorderLayout.CENTER);

        
        JButton aggiornaButton = new JButton("Aggiorna Lista");
        aggiornaButton.addActionListener(e -> aggiornaListaIscritti());
        add(aggiornaButton, BorderLayout.SOUTH);

        
        aggiornaListaIscritti();
    }

    private void aggiornaListaIscritti() {
        
        tableModel.setRowCount(0);

        
        List<Iscritto> iscritti = presenter.getTuttiIscritti();
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