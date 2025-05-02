package view;

import model.Iscritto;
import model.Abbonamento;
import manager.IscrittiManager;
import presenter.MainPresenter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

/**
 * Pannello per la ricerca di un iscritto
 */
public class RicercaIscrittoPanel extends JPanel {
    private JTextField codiceField;
    private JButton cercaButton;
    private JTextArea risultatoArea;
    private MainPresenter presenter;

    public RicercaIscrittoPanel(MainPresenter presenter) {
        this.presenter = presenter;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Campo codice identificativo
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Codice Identificativo:"), gbc);

        gbc.gridx = 1;
        codiceField = new JTextField(20);
        add(codiceField, gbc);

        // Pulsante cerca
        gbc.gridx = 2;
        cercaButton = new JButton("Cerca");
        add(cercaButton, gbc);

        // Area risultato
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        risultatoArea = new JTextArea();
        risultatoArea.setEditable(false);
        add(new JScrollPane(risultatoArea), gbc);

        // Gestione evento cerca
        cercaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codice = codiceField.getText();

                if (codice.isEmpty()) {
                    JOptionPane.showMessageDialog(RicercaIscrittoPanel.this,
                            "Inserire un codice identificativo",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Iscritto iscritto = presenter.cercaIscritto(codice);
                if (iscritto != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Nome: ").append(iscritto.getNome()).append("\n");
                    sb.append("Cognome: ").append(iscritto.getCognome()).append("\n");
                    sb.append("Codice: ").append(iscritto.getCodiceIdentificativo()).append("\n");
                    sb.append("\nAbbonamenti attivi:\n");
                    for (Abbonamento a : presenter.getAbbonamentiAttivi(iscritto)) {
                        sb.append("- ").append(a.toString()).append("\n");
                    }
                    sb.append("\nStorico abbonamenti:\n");
                    for (Abbonamento a : presenter.getStoricoAbbonamenti(iscritto)) {
                        sb.append("- ").append(a.toString()).append("\n");
                    }
                    risultatoArea.setText(sb.toString());
                } else {
                    risultatoArea.setText("Nessun iscritto trovato con il codice: " + codice);
                }
            }
        });
    }
} 