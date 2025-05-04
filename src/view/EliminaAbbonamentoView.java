package view;

import presenter.MainPresenter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EliminaAbbonamentoView extends JFrame {
    private MainPresenter presenter;
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

        // Codice abbonamento
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Codice Abbonamento:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        codiceAbbonamentoField = new JTextField(20);
        mainPanel.add(codiceAbbonamentoField, gbc);

        // Pulsante elimina
        gbc.gridx = 0;
        gbc.gridy = 1;
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
        gbc.gridy = 2;
        risultatoLabel = new JLabel("");
        risultatoLabel.setForeground(Color.RED);
        mainPanel.add(risultatoLabel, gbc);

        add(mainPanel);
    }

    private void eliminaAbbonamento() {
        String codiceAbbonamento = codiceAbbonamentoField.getText().trim();
        if (codiceAbbonamento.isEmpty()) {
            risultatoLabel.setText("Inserire un codice abbonamento valido");
            return;
        }

        if (presenter.eliminaAbbonamento(codiceAbbonamento)) {
            risultatoLabel.setForeground(Color.GREEN);
            risultatoLabel.setText("Abbonamento eliminato con successo");
            codiceAbbonamentoField.setText("");
        } else {
            risultatoLabel.setForeground(Color.RED);
            risultatoLabel.setText("Abbonamento non trovato");
        }
    }
} 