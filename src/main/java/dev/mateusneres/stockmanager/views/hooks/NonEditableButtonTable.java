package dev.mateusneres.stockmanager.views.hooks;

import javax.swing.table.DefaultTableModel;

public class NonEditableButtonTable extends DefaultTableModel {

    public NonEditableButtonTable(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        int editButtonIndex = getColumnCount() - 2;
        int deleteButtonIndex = getColumnCount() - 1;

        return column == editButtonIndex || column == deleteButtonIndex;
    }

}
