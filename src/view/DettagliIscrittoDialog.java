package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import model.Iscritto;
import model.Abbonamento;
import presenter.MainPresenter;

public class DettagliIscrittoDialog extends JDialog {
    public DettagliIscrittoDialog(Iscritto iscritto, MainPresenter presenter) {
        setTitle("Dettagli Iscritto");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setModal(true);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Pannello informazioni
        JPanel infoPanel = new JPanel(new GridLayout(3, 2));
        infoPanel.add(new JLabel("Nome:"));
        infoPanel.add(new JLabel(iscritto.getNome()));
        infoPanel.add(new JLabel("Cognome:"));
        infoPanel.add(new JLabel(iscritto.getCognome()));
        infoPanel.add(new JLabel("Codice:"));
        infoPanel.add(new JLabel(iscritto.getCodiceIdentificativo()));
        mainPanel.add(infoPanel, BorderLayout.NORTH);

        // Tabella abbonamenti
        DefaultTableModel model = new DefaultTableModel(new String[]{"Tipo", "Data Inizio", "Data Fine", "Stato"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.setDefaultRenderer(Object.class, new StatoAbbonamentoRenderer());

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
                    abbonamento.getDataInizio().format(Abbonamento.DATE_FORMATTER),
                    abbonamento.getDataFine().format(Abbonamento.DATE_FORMATTER),
                    stato
            });
        }
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(mainPanel);
    }
}