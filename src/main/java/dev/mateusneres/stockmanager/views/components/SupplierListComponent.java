package dev.mateusneres.stockmanager.views.components;

import dev.mateusneres.stockmanager.enums.ButtonType;
import dev.mateusneres.stockmanager.views.MPopUp;
import lombok.Getter;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import javax.swing.*;
import java.awt.*;

public class SupplierListComponent extends MPopUp {

    private final String[] columnNames = {"ID", "Name", "Address", "Phone", "Edit", "Delete"};
    private final JXTable table;
    @Getter
    private final JButton editButton;
    @Getter
    private final JButton deleteButton;

    public SupplierListComponent(String[][] data) {
        super("StockManager - Supplier List");

        NonEditableTableModel model = new NonEditableTableModel(data, columnNames);
        JPanel container = new JPanel();

        MImage editImage = new MImage(new ImageIcon(getClass().getResource("/edit.png")));
        editButton = new JButton(editImage.getIcon());
        editButton.setBackground(Color.YELLOW);

        MImage deleteImage = new MImage(new ImageIcon(getClass().getResource("/delete.png")));
        deleteButton = new JButton(deleteImage.getIcon());
        deleteButton.setBackground(Color.decode("#410000"));

        table = new JXTable(model);
        table.setSelectionForeground(Color.CYAN);
        table.setFocusable(false);

        Highlighter highlighter = HighlighterFactory.createAlternateStriping(Color.decode("#33383e"), Color.decode("#31333b"));
        table.setHighlighters(highlighter);

        table.getTableHeader().setDefaultRenderer(new HeaderRenderer());
        table.getTableHeader().setReorderingAllowed(false);
        table.setPreferredScrollableViewportSize(new Dimension(getWidth(), 410));
        table.setDefaultRenderer(Object.class, new TooltipTableCellRenderer());

        table.getColumn("Delete").setCellRenderer(new ButtonRenderer(ButtonType.DELETE));
        table.getColumn("Delete").setCellEditor(new ButtonEditor(deleteButton));

        table.getColumn("Edit").setCellRenderer(new ButtonRenderer(ButtonType.EDIT));
        table.getColumn("Edit").setCellEditor(new ButtonEditor(editButton));
        table.packAll();

        JScrollPane scrollPane = new JScrollPane(table);
        container.add(scrollPane);
        setContentPane(container);
    }

}