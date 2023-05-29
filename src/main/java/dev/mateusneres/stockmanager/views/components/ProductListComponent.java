package dev.mateusneres.stockmanager.views.components;

import dev.mateusneres.stockmanager.views.MPopUp;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductListComponent extends MPopUp {

    private final String[] columnNames = {"ID", "Name", "Price", "Available", "Delete?"};
    private final JXTable table;

    public ProductListComponent(String[][] data) {
        super("StockManager - Product List");

        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        JPanel container = new JPanel();

        table = new JXTable(model);
        table.setSelectionForeground(Color.CYAN);
        table.setFocusable(false);

        Highlighter highlighter = HighlighterFactory.createAlternateStriping(Color.decode("#33383e"), Color.decode("#31333b"));
        table.setHighlighters(highlighter);

        table.getTableHeader().setDefaultRenderer(new HeaderRenderer());
        table.getTableHeader().setReorderingAllowed(false);

        table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
        table.setPreferredScrollableViewportSize(new Dimension(getWidth(), 410));

        JScrollPane scrollPane = new JScrollPane(table);
        container.add(scrollPane);
        setContentPane(container);
    }


}
