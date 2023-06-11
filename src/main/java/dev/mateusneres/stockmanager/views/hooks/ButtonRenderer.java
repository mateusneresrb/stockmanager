package dev.mateusneres.stockmanager.views.hooks;

import dev.mateusneres.stockmanager.enums.ButtonType;
import dev.mateusneres.stockmanager.views.components.MImage;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer extends JButton implements TableCellRenderer {
    private final ButtonType buttonType;

    public ButtonRenderer(ButtonType buttonType) {
        this.buttonType = buttonType;
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        String icon = buttonType == ButtonType.DELETE ? "/delete.png" : "/edit.png";

        MImage buttonImage = new MImage(new ImageIcon(getClass().getResource(icon)));
        setIcon(buttonImage.getIcon());
        setName(buttonType.name());
        return this;
    }
}