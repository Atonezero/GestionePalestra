package view;

import javax.swing.*;
import java.awt.*;
import presenter.MainPresenter;

public class InserisciIscrittoPanel extends JPanel {
    private MainPresenter presenter;
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField codiceField;
    private JLabel resultLabel;

    public InserisciIscrittoPanel(MainPresenter presenter) {
        this.presenter = presenter;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Nome
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        nomeField = new JTextField(20);
        add(nomeField, gbc);

        // Cognome
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Cognome:"), gbc);

        gbc.gridx = 1;
        cognomeField = new JTextField(20);
        add(cognomeField, gbc);

        // Codice
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Codice Identificativo:"), gbc);

        gbc.gridx = 1;
        codiceField = new JTextField(20);
        add(codiceField, gbc);

        // Pulsante Inserisci
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton inserisciButton = new JButton("Inserisci");
        inserisciButton.addActionListener(e -> inserisciIscritto());
        add(inserisciButton, gbc);

        // Label risultato
        gbc.gridy = 4;
        resultLabel = new JLabel("");
        add(resultLabel, gbc);
    }

    private void inserisciIscritto() {
        String nome = nomeField.getText().trim();
        String cognome = cognomeField.getText().trim();
        String codice = codiceField.getText().trim();

        if (nome.isEmpty() || cognome.isEmpty() || codice.isEmpty()) {
            resultLabel.setText("Tutti i campi sono obbligatori");
            return;
        }

        try {
            presenter.aggiungiIscritto(nome, cognome, codice);
            resultLabel.setText("Iscritto aggiunto con successo");
            nomeField.setText("");
            cognomeField.setText("");
            codiceField.setText("");
        } catch (Exception e) {
            resultLabel.setText("Errore: " + e.getMessage());
        }
    }
} 