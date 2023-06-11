package dev.mateusneres.stockmanager.views.hooks;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TableHeaderRenderer extends DefaultTableCellRenderer {
    public TableHeaderRenderer() {
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
        setBackground(Color.decode("#20262b"));
        setFont(getFont().deriveFont(Font.BOLD, 14f));
    }
}
