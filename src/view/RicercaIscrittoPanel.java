package view;

import model.Iscritto;
import manager.IscrittiManager;
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

    public RicercaIscrittoPanel() {
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

                Optional<Iscritto> iscritto = IscrittiManager.getInstance().cercaIscritto(codice);
                if (iscritto.isPresent()) {
                    Iscritto i = iscritto.get();
                    StringBuilder sb = new StringBuilder();
                    sb.append("Nome: ").append(i.getNome()).append("\n");
                    sb.append("Cognome: ").append(i.getCognome()).append("\n");
                    sb.append("Codice: ").append(i.getCodiceIdentificativo()).append("\n");
                    sb.append("\nAbbonamenti attivi:\n");
                    for (Abbonamento a : i.getAbbonamentiAttivi()) {
                        sb.append("- ").append(a.toString()).append("\n");
                    }
                    sb.append("\nStorico abbonamenti:\n");
                    for (Abbonamento a : i.getStoricoAbbonamenti()) {
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