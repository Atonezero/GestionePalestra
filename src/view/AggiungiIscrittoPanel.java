package view;

import model.Iscritto;
import model.Abbonamento;
import manager.IscrittiManager;
import presenter.MainPresenter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

public class AggiungiIscrittoPanel extends JPanel {
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField codiceField;
    private JComboBox<String> tipoAbbonamentoCombo;
    private JDateChooser dataInizioChooser;
    private JButton salvaButton;
    private MainPresenter presenter;

    public AggiungiIscrittoPanel(MainPresenter presenter) {
        this.presenter = presenter;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        nomeField = new JTextField(20);
        add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Cognome:"), gbc);

        gbc.gridx = 1;
        cognomeField = new JTextField(20);
        add(cognomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Codice Identificativo:"), gbc);

        gbc.gridx = 1;
        codiceField = new JTextField(20);
        add(codiceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Tipo Abbonamento:"), gbc);

        gbc.gridx = 1;
        String[] tipiAbbonamento = { "Mensile", "Annuale" };
        tipoAbbonamentoCombo = new JComboBox<>(tipiAbbonamento);
        add(tipoAbbonamentoCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Data Inizio:"), gbc);

        gbc.gridx = 1;
        dataInizioChooser = new JDateChooser();
        dataInizioChooser.setDateFormatString("yyyy-MM-dd");
        dataInizioChooser.setPreferredSize(new Dimension(200, 20));
        add(dataInizioChooser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        salvaButton = new JButton("Salva");
        add(salvaButton, gbc);

        salvaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String cognome = cognomeField.getText();
                String codice = codiceField.getText();
                String tipoAbbonamento = (String) tipoAbbonamentoCombo.getSelectedItem();
                Date dataInizioDate = dataInizioChooser.getDate();

                if (nome.isEmpty() || cognome.isEmpty() || codice.isEmpty() || dataInizioDate == null) {
                    JOptionPane.showMessageDialog(AggiungiIscrittoPanel.this,
                            "Tutti i campi sono obbligatori",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    LocalDate dataInizio = dataInizioDate.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();

                    Iscritto nuovoIscritto = new Iscritto(nome, cognome, codice);

                    IscrittiManager.getInstance().aggiungiIscritto(nuovoIscritto);

                    if (tipoAbbonamento.equals("Mensile")) {
                        presenter.aggiungiAbbonamentoMensile(nuovoIscritto, dataInizio);
                    } else {
                        presenter.aggiungiAbbonamentoAnnuale(nuovoIscritto, dataInizio);
                    }

                    JOptionPane.showMessageDialog(AggiungiIscrittoPanel.this,
                            "Iscritto e abbonamento aggiunti con successo",
                            "Successo",
                            JOptionPane.INFORMATION_MESSAGE);

                    nomeField.setText("");
                    cognomeField.setText("");
                    codiceField.setText("");
                    dataInizioChooser.setDate(null);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AggiungiIscrittoPanel.this,
                            "Errore durante l'aggiunta dell'iscritto: " + ex.getMessage(),
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}