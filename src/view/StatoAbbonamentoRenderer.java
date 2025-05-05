package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class StatoAbbonamentoRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Controlla solo la colonna "Stato" (indice 3)
        if (column == 3 && value != null) {
            String stato = value.toString();
            switch (stato) {
                case "SCADUTO":
                    c.setBackground(new Color(255, 150, 150)); // Rosso chiaro
                    c.setForeground(Color.BLACK);
                    break;
                case "ATTIVO":
                    c.setBackground(new Color(150, 255, 150)); // Verde chiaro
                    c.setForeground(Color.BLACK);
                    break;
                case "FUTURO":
                    c.setBackground(new Color(255, 255, 150)); // Giallo chiaro
                    c.setForeground(Color.BLACK);
                    break;
                default:
                    c.setBackground(table.getBackground());
                    c.setForeground(table.getForeground());
            }
        } else {
            c.setBackground(table.getBackground());
            c.setForeground(table.getForeground());
        }

        // Mantieni il colore di selezione
        if (isSelected) {
            c.setBackground(table.getSelectionBackground());
            c.setForeground(table.getSelectionForeground());
        }

        return c;
    }
}