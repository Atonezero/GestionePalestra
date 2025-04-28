package view;

import model.Iscritto;
import manager.IscrittiManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Pannello per l'aggiunta di un nuovo iscritto
 */
public class AggiungiIscrittoPanel extends JPanel {
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField codiceField;
    private JButton salvaButton;

    public AggiungiIscrittoPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Campo nome
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        nomeField = new JTextField(20);
        add(nomeField, gbc);

        // Campo cognome
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Cognome:"), gbc);

        gbc.gridx = 1;
        cognomeField = new JTextField(20);
        add(cognomeField, gbc);

        // Campo codice identificativo
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Codice Identificativo:"), gbc);

        gbc.gridx = 1;
        codiceField = new JTextField(20);
        add(codiceField, gbc);

        // Pulsante salva
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        salvaButton = new JButton("Salva");
        add(salvaButton, gbc);

        // Gestione evento salva
        salvaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String cognome = cognomeField.getText();
                String codice = codiceField.getText();

                if (nome.isEmpty() || cognome.isEmpty() || codice.isEmpty()) {
                    JOptionPane.showMessageDialog(AggiungiIscrittoPanel.this,
                            "Tutti i campi sono obbligatori",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Iscritto nuovoIscritto = new Iscritto(nome, cognome, codice);
                IscrittiManager.getInstance().aggiungiIscritto(nuovoIscritto);

                JOptionPane.showMessageDialog(AggiungiIscrittoPanel.this,
                        "Iscritto aggiunto con successo",
                        "Successo",
                        JOptionPane.INFORMATION_MESSAGE);

                // Reset dei campi
                nomeField.setText("");
                cognomeField.setText("");
                codiceField.setText("");
            }
        });
    }
} 