package dev.mateusneres.stockmanager.views;

import dev.mateusneres.stockmanager.enums.ButtonType;
import dev.mateusneres.stockmanager.views.components.MImage;
import dev.mateusneres.stockmanager.views.hooks.ButtonEditor;
import dev.mateusneres.stockmanager.views.hooks.ButtonRenderer;
import dev.mateusneres.stockmanager.views.hooks.TableHeaderRenderer;
import dev.mateusneres.stockmanager.views.hooks.TooltipTableCellRenderer;
import lombok.Getter;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.VerticalLayout;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class HomeScreen extends JFrame {

    private final String[] columnNames = {"ID", "Produto", "Supplier", "Quantity", "Price", "Date", "Edit", "Delete"};
    @Getter
    private final JLabel logoutLabel;
    @Getter
    private final JTextField searchField;
    @Getter
    private final JButton addButton;
    @Getter
    private final transient TableRowSorter<DefaultTableModel> rowSorter;
    private final JLabel appLogoLabel;
    private final JXTable table;
    @Getter
    private final JButton editButton;
    @Getter
    private final JButton deleteButton;

    private final Point frameLocation;

    public HomeScreen(Point frameLocation, String[][] data) {
        super("StockManager - Home");
        this.frameLocation = frameLocation;

        appLogoLabel = new JLabel("StockManager");
        appLogoLabel.setFont(new Font("Arial", Font.BOLD, 25));
        appLogoLabel.setBorder(BorderFactory.createEmptyBorder(30, 70, 20, 0));
        appLogoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        logoutLabel = new JLabel("Logout");
        logoutLabel.setBackground(Color.decode("#8e8e8e"));
        logoutLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0,10));

        searchField = new JTextField(25);
        PromptSupport.setPrompt("Search purchases here...", searchField);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIDE_PROMPT, searchField);
        PromptSupport.setFontStyle(Font.ITALIC, searchField);

        MImage editImage = new MImage(new ImageIcon(getClass().getResource("/edit.png")));
        editButton = new JButton(editImage.getIcon());
        editButton.setBackground(Color.YELLOW);

        MImage deleteImage = new MImage(new ImageIcon(getClass().getResource("/delete.png")));
        deleteButton = new JButton(deleteImage.getIcon());
        deleteButton.setBackground(Color.decode("#410000"));

        addButton = new JButton("+");
        addButton.setBackground(Color.decode("#724b1d"));

        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        table = new JXTable(model);
        Highlighter highlighter = HighlighterFactory.createAlternateStriping(Color.decode("#33383e"), Color.decode("#31333b"));
        table.setFocusable(false);
        table.setHighlighters(highlighter);

        rowSorter = new TableRowSorter<>(model);
        table.setRowSorter(rowSorter);

        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setDefaultRenderer(new TableHeaderRenderer());
        table.setDefaultRenderer(Object.class, new TooltipTableCellRenderer());

        table.getColumn("Delete").setCellRenderer(new ButtonRenderer(ButtonType.DELETE));
        table.getColumn("Delete").setCellEditor(new ButtonEditor(deleteButton));

        table.getColumn("Edit").setCellRenderer(new ButtonRenderer(ButtonType.EDIT));
        table.getColumn("Edit").setCellEditor(new ButtonEditor(editButton));
        table.packAll();

        table.setCellSelectionEnabled(true);
        table.setDefaultEditor(Object.class, null);
        table.setPreferredScrollableViewportSize(new Dimension(getWidth(), 550));

        initComponents();
    }

    private void initComponents(){
        JPanel contentPane = new JPanel(new VerticalLayout());

        JPanel headerPane = new JPanel(new BorderLayout());
        headerPane.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#353b40")));
        headerPane.setBackground(Color.decode("#20262b"));
        headerPane.add(appLogoLabel, BorderLayout.CENTER);
        headerPane.add(logoutLabel, BorderLayout.LINE_END);

        JPanel searchPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPane.setBorder(BorderFactory.createEmptyBorder(10, 0 ,0 ,0));
        searchPane.add(searchField);
        searchPane.add(addButton);

        JLabel titlePanelLabel = new JLabel("Purchases:");
        titlePanelLabel.setFont(titlePanelLabel.getFont().deriveFont(Font.BOLD, 14f));
        titlePanelLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tablePanel.add(titlePanelLabel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        contentPane.add(headerPane);
        contentPane.add(searchPane);
        contentPane.add(tablePanel);

        setContentPane(contentPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1200, 800));
        setFrameLocation(frameLocation);
        setResizable(false);
        setVisible(true);
        setAlwaysOnTop(true);
    }

    public void updateTableData(String[][] newData) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setDataVector(newData, columnNames);
        table.getColumn("Delete").setCellRenderer(new ButtonRenderer(ButtonType.DELETE));
        table.getColumn("Delete").setCellEditor(new ButtonEditor(deleteButton));

        table.getColumn("Edit").setCellRenderer(new ButtonRenderer(ButtonType.EDIT));
        table.getColumn("Edit").setCellEditor(new ButtonEditor(editButton));
        table.packAll();
    }

    private void setFrameLocation(Point location) {
        if (location != null) {
            setLocation(location);
            return;
        }
        setLocationRelativeTo(null);
    }

}
