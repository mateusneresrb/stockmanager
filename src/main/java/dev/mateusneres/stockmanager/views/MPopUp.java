package dev.mateusneres.stockmanager.views;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
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

    protected class CustomTableCellRenderer extends DefaultTableCellRenderer {
        public CustomTableCellRenderer() {
            setHorizontalAlignment(CENTER);
        }
    }

}
