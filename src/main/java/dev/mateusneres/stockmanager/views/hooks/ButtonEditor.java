package dev.mateusneres.stockmanager.views.hooks;

import javax.swing.*;
import java.awt.*;


public class ButtonEditor extends DefaultCellEditor {
    private final JButton button;

    public ButtonEditor(JButton button) {
        super(new JCheckBox());
        this.button = button;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button;
    }
}
