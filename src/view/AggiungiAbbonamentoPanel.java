package view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import com.toedter.calendar.JDateChooser;
import presenter.MainPresenter;
import model.Iscritto;

public class AggiungiAbbonamentoPanel extends JPanel {
    private MainPresenter presenter;
    private JTextField codiceField;
    private JComboBox<String> tipoAbbonamento;
    private JDateChooser dataInizioChooser;
    private JLabel resultLabel;

    public AggiungiAbbonamentoPanel(MainPresenter presenter) {
        this.presenter = presenter;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Codice Iscritto:"), gbc);

        gbc.gridx = 1;
        codiceField = new JTextField(20);
        add(codiceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Tipo Abbonamento:"), gbc);

        gbc.gridx = 1;
        tipoAbbonamento = new JComboBox<>(new String[] { "MENSILE", "ANNUALE" });
        add(tipoAbbonamento, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Data Inizio:"), gbc);

        gbc.gridx = 1;
        dataInizioChooser = new JDateChooser();
        dataInizioChooser.setDateFormatString("dd/MM/yyyy");
        add(dataInizioChooser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton aggiungiButton = new JButton("Aggiungi Abbonamento");
        aggiungiButton.addActionListener(e -> aggiungiAbbonamento());
        add(aggiungiButton, gbc);

        gbc.gridy = 4;
        resultLabel = new JLabel("");
        add(resultLabel, gbc);
    }

    private void aggiungiAbbonamento() {
        String codice = codiceField.getText().trim();
        String tipo = (String) tipoAbbonamento.getSelectedItem();
        LocalDate dataInizio = dataInizioChooser.getDate() != null
                ? dataInizioChooser.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                : null;

        if (codice.isEmpty() || dataInizio == null) {
            resultLabel.setText("Tutti i campi sono obbligatori");
            return;
        }

        Iscritto iscritto = presenter.cercaIscritto(codice);
        if (iscritto == null) {
            resultLabel.setText("Iscritto non trovato");
            return;
        }

        try {
            if (tipo.equals("MENSILE")) {
                presenter.aggiungiAbbonamentoMensile(iscritto, dataInizio);
            } else {
                presenter.aggiungiAbbonamentoAnnuale(iscritto, dataInizio);
            }
            resultLabel.setText("Abbonamento aggiunto con successo");
            codiceField.setText("");
            dataInizioChooser.setDate(null);
        } catch (Exception e) {
            resultLabel.setText("Errore: " + e.getMessage());
        }
    }
}