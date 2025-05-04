package view;

import javax.swing.*;
import java.awt.*;
import presenter.MainPresenter;

public class MainFrame extends JFrame {
    private MainPresenter presenter;
    private JPanel mainPanel;
    private JPanel menuPanel;
    private InserisciIscrittoPanel inserisciIscrittoPanel;
    private AggiungiAbbonamentoPanel aggiungiAbbonamentoPanel;
    private VisualizzaAbbonamentiPanel visualizzaAbbonamentiPanel;

    public MainFrame() {
        presenter = new MainPresenter();
        initComponents();
    }

    private void initComponents() {
        setTitle("Gestione Palestra");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout());

        JButton btnInserisciIscritto = new JButton("Inserisci Iscritto");
        JButton btnAggiungiAbbonamento = new JButton("Aggiungi Abbonamento");
        JButton btnVisualizzaAbbonamenti = new JButton("Visualizza Abbonamenti");

        menuPanel.add(btnInserisciIscritto);
        menuPanel.add(btnAggiungiAbbonamento);
        menuPanel.add(btnVisualizzaAbbonamenti);

        mainPanel = new JPanel(new CardLayout());
        inserisciIscrittoPanel = new InserisciIscrittoPanel(presenter);
        aggiungiAbbonamentoPanel = new AggiungiAbbonamentoPanel(presenter);
        visualizzaAbbonamentiPanel = new VisualizzaAbbonamentiPanel(presenter);

        mainPanel.add(inserisciIscrittoPanel, "InserisciIscritto");
        mainPanel.add(aggiungiAbbonamentoPanel, "AggiungiAbbonamento");
        mainPanel.add(visualizzaAbbonamentiPanel, "VisualizzaAbbonamenti");

        btnInserisciIscritto.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "InserisciIscritto");
        });

        btnAggiungiAbbonamento.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "AggiungiAbbonamento");
        });

        btnVisualizzaAbbonamenti.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "VisualizzaAbbonamenti");
        });

        setLayout(new BorderLayout());
        add(menuPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
