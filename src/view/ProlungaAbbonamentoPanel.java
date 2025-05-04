package view;

import model.Iscritto;
import model.Abbonamento;
import model.AbbonamentoMensile;
import model.AbbonamentoAnnuale;
import presenter.MainPresenter;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ProlungaAbbonamentoPanel extends JPanel {
    private MainPresenter presenter;
    private JTextField codiceIscrittoField;
    private JComboBox<String> tipoAbbonamentoCombo;
    private JDateChooser dataInizioChooser;
    private JButton prolungaButton;
    private JLabel risultatoLabel;

    public ProlungaAbbonamentoPanel(MainPresenter presenter) {
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
        gbc.fill = GridBagConstraints.HORIZONTAL;
        codiceIscrittoField = new JTextField(20);
        add(codiceIscrittoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Tipo Abbonamento:"), gbc);

        gbc.gridx = 1;
        String[] tipiAbbonamento = { "MENSILE", "ANNUALE" };
        tipoAbbonamentoCombo = new JComboBox<>(tipiAbbonamento);
        add(tipoAbbonamentoCombo, gbc);

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
        gbc.fill = GridBagConstraints.NONE;
        prolungaButton = new JButton("Prolunga Abbonamento");
        prolungaButton.addActionListener(e -> prolungaAbbonamento());
        add(prolungaButton, gbc);

        gbc.gridy = 4;
        risultatoLabel = new JLabel("");
        risultatoLabel.setForeground(Color.RED);
        add(risultatoLabel, gbc);
    }

    private void prolungaAbbonamento() {
        String codiceIscritto = codiceIscrittoField.getText().trim();
        if (codiceIscritto.isEmpty()) {
            risultatoLabel.setText("Inserire un codice iscritto valido");
            return;
        }

        Iscritto iscritto = presenter.cercaIscritto(codiceIscritto);
        if (iscritto == null) {
            risultatoLabel.setText("Iscritto non trovato");
            return;
        }

        if (!iscritto.puoProlungareAbbonamento()) {
            risultatoLabel.setText("L'iscritto non ha abbonamenti attivi da prolungare");
            return;
        }

        Abbonamento abbonamentoAttuale = iscritto.getAbbonamentoAttivoPiuRecente();
        if (abbonamentoAttuale == null) {
            risultatoLabel.setText("Nessun abbonamento attivo trovato");
            return;
        }

        Date dataInizio = dataInizioChooser.getDate();
        if (dataInizio == null) {
            risultatoLabel.setText("Selezionare una data di inizio");
            return;
        }

        LocalDate dataInizioLocal = dataInizio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (!dataInizioLocal.equals(abbonamentoAttuale.getDataFine())) {
            risultatoLabel.setText("La data di inizio deve coincidere con la fine dell'abbonamento attuale");
            return;
        }

        Abbonamento nuovoAbbonamento;
        if (tipoAbbonamentoCombo.getSelectedItem().equals("MENSILE")) {
            nuovoAbbonamento = new AbbonamentoMensile(dataInizioLocal, codiceIscritto);
        } else {
            nuovoAbbonamento = new AbbonamentoAnnuale(dataInizioLocal, codiceIscritto);
        }

        if (presenter.prolungaAbbonamento(iscritto, abbonamentoAttuale, nuovoAbbonamento)) {
            risultatoLabel.setForeground(Color.GREEN);
            risultatoLabel.setText("Abbonamento prolungato con successo");
            codiceIscrittoField.setText("");
            dataInizioChooser.setDate(null);
        } else {
            risultatoLabel.setForeground(Color.RED);
            risultatoLabel.setText("Errore nel prolungamento dell'abbonamento");
        }
    }
}