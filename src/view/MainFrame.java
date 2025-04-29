package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import presenter.MainPresenter;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel menuPanel;
    private MainPresenter presenter;

    public MainFrame() {
        setTitle("Gestione Palestra");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Initialize presenter
        presenter = new MainPresenter();

        // Initialize CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create menu
        menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAggiungiIscritto = new JButton("Aggiungi Iscritto");
        JButton btnRicercaIscritto = new JButton("Ricerca Iscritto");
        JButton btnVisualizzaIscritti = new JButton("Visualizza Iscritti");
        JButton btnVisualizzaAbbonamenti = new JButton("Visualizza Abbonamenti");

        menuPanel.add(btnAggiungiIscritto);
        menuPanel.add(btnRicercaIscritto);
        menuPanel.add(btnVisualizzaIscritti);
        menuPanel.add(btnVisualizzaAbbonamenti);

        // Add panels to CardLayout
        mainPanel.add(new AggiungiIscrittoPanel(presenter), "AGGIUNGI_ISCRITTO");
        mainPanel.add(new RicercaIscrittoPanel(presenter), "RICERCA_ISCRITTO");
        mainPanel.add(new VisualizzaIscrittiPanel(presenter), "VISUALIZZA_ISCRITTI");
        mainPanel.add(new VisualizzaAbbonamentiPanel(presenter), "VISUALIZZA_ABBONAMENTI");

        // Button event handling
        btnAggiungiIscritto.addActionListener(e -> cardLayout.show(mainPanel, "AGGIUNGI_ISCRITTO"));
        btnRicercaIscritto.addActionListener(e -> cardLayout.show(mainPanel, "RICERCA_ISCRITTO"));
        btnVisualizzaIscritti.addActionListener(e -> cardLayout.show(mainPanel, "VISUALIZZA_ISCRITTI"));
        btnVisualizzaAbbonamenti.addActionListener(e -> cardLayout.show(mainPanel, "VISUALIZZA_ABBONAMENTI"));

        // Window layout
        setLayout(new BorderLayout());
        add(menuPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
}
