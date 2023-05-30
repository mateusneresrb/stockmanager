package dev.mateusneres.stockmanager.views;

import dev.mateusneres.stockmanager.enums.ButtonType;
import dev.mateusneres.stockmanager.views.components.MImage;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MPopUp extends JFrame {

    public MPopUp(String name) {
        super(name);

        handleListener();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(400, 500));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setAlwaysOnTop(true);
    }

    private void handleListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                dispose();
            }

            @Override
            public void windowIconified(WindowEvent e) {
                dispose();
            }
        });
    }

    protected class HeaderRenderer extends DefaultTableCellRenderer {
        public HeaderRenderer() {
            setOpaque(true);
            setHorizontalAlignment(SwingConstants.CENTER);
            setBackground(Color.decode("#20262b"));
            setFont(getFont().deriveFont(Font.BOLD, 14f));
        }
    }

    protected class NonEditableTableModel extends DefaultTableModel {
        public NonEditableTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            int editButtonIndex = getColumnCount() - 2;
            int deleteButtonIndex = getColumnCount() - 1;

            return column == editButtonIndex || column == deleteButtonIndex;
        }

    }

    public static class TooltipTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (value != null) {
                setToolTipText(value.toString());
            } else {
                setToolTipText("");
            }

            setHorizontalAlignment(CENTER);

            return cellComponent;
        }
    }

    protected class ButtonRenderer extends JButton implements TableCellRenderer {
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

    protected class ButtonEditor extends DefaultCellEditor {
        private final JButton button;

        public ButtonEditor(JButton button) {
            super(new JCheckBox());
            this.button = button;
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return button;
        }
    }

    public class NumericDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
            String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
            if (isNumeric(newText)) {
                super.insertString(fb, offset, text, attrs);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
            if (isNumeric(newText)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        private boolean isNumeric(String text) {
            try {
                if (text.isEmpty()) {
                    return true;
                }
                double value = Double.parseDouble(text);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

}
