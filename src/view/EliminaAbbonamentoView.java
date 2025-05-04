package view;

import presenter.MainPresenter;
import model.Iscritto;
import model.Abbonamento;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EliminaAbbonamentoView extends JFrame {
    private MainPresenter presenter;
    private JTextField codiceIscrittoField;
    private JTextField codiceAbbonamentoField;
    private JButton eliminaButton;
    private JLabel risultatoLabel;

    public EliminaAbbonamentoView(MainPresenter presenter) {
        this.presenter = presenter;
        setTitle("Elimina Abbonamento");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Codice iscritto
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Codice Iscritto:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        codiceIscrittoField = new JTextField(20);
        mainPanel.add(codiceIscrittoField, gbc);

        // Codice abbonamento
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Codice Abbonamento:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        codiceAbbonamentoField = new JTextField(20);
        mainPanel.add(codiceAbbonamentoField, gbc);

        // Pulsante elimina
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        eliminaButton = new JButton("Elimina");
        eliminaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminaAbbonamento();
            }
        });
        mainPanel.add(eliminaButton, gbc);

        // Label risultato
        gbc.gridy = 3;
        risultatoLabel = new JLabel("");
        risultatoLabel.setForeground(Color.RED);
        mainPanel.add(risultatoLabel, gbc);

        add(mainPanel);
    }

    private void eliminaAbbonamento() {
        String codiceIscritto = codiceIscrittoField.getText().trim();
        String codiceAbbonamento = codiceAbbonamentoField.getText().trim();

        if (codiceIscritto.isEmpty() || codiceAbbonamento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Inserisci sia il codice iscritto che il codice abbonamento", 
                "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Iscritto iscritto = presenter.cercaIscritto(codiceIscritto);
        if (iscritto == null) {
            JOptionPane.showMessageDialog(this, "Iscritto non trovato", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Abbonamento abbonamento = presenter.cercaAbbonamento(iscritto, codiceAbbonamento);
        if (abbonamento == null) {
            JOptionPane.showMessageDialog(this, "Abbonamento non trovato", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (presenter.eliminaAbbonamento(iscritto, abbonamento)) {
            JOptionPane.showMessageDialog(this, "Abbonamento eliminato con successo", 
                "Successo", JOptionPane.INFORMATION_MESSAGE);
            codiceIscrittoField.setText("");
            codiceAbbonamentoField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Errore durante l'eliminazione dell'abbonamento", 
                "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
} 