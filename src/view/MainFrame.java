package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Finestra principale dell'applicazione che utilizza CardLayout per gestire le diverse schermate
 */
public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel menuPanel;

    public MainFrame() {
        setTitle("Gestione Palestra");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Inizializzazione del CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Creazione del menu
        menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAggiungiIscritto = new JButton("Aggiungi Iscritto");
        JButton btnRicercaIscritto = new JButton("Ricerca Iscritto");
        JButton btnVisualizzaIscritti = new JButton("Visualizza Iscritti");
        JButton btnVisualizzaAbbonamenti = new JButton("Visualizza Abbonamenti");

        menuPanel.add(btnAggiungiIscritto);
        menuPanel.add(btnRicercaIscritto);
        menuPanel.add(btnVisualizzaIscritti);
        menuPanel.add(btnVisualizzaAbbonamenti);

        // Aggiunta delle schermate al CardLayout
        mainPanel.add(new AggiungiIscrittoPanel(), "AGGIUNGI_ISCRITTO");
        mainPanel.add(new RicercaIscrittoPanel(), "RICERCA_ISCRITTO");
        mainPanel.add(new VisualizzaIscrittiPanel(), "VISUALIZZA_ISCRITTI");
        mainPanel.add(new VisualizzaAbbonamentiPanel(), "VISUALIZZA_ABBONAMENTI");

        // Gestione degli eventi dei pulsanti
        btnAggiungiIscritto.addActionListener(e -> cardLayout.show(mainPanel, "AGGIUNGI_ISCRITTO"));
        btnRicercaIscritto.addActionListener(e -> cardLayout.show(mainPanel, "RICERCA_ISCRITTO"));
        btnVisualizzaIscritti.addActionListener(e -> cardLayout.show(mainPanel, "VISUALIZZA_ISCRITTI"));
        btnVisualizzaAbbonamenti.addActionListener(e -> cardLayout.show(mainPanel, "VISUALIZZA_ABBONAMENTI"));

        // Layout della finestra
        setLayout(new BorderLayout());
        add(menuPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
} 