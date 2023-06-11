package dev.mateusneres.stockmanager.views.components;

import dev.mateusneres.stockmanager.controllers.HomeController;
import dev.mateusneres.stockmanager.enums.OperationType;
import dev.mateusneres.stockmanager.models.Supplier;
import dev.mateusneres.stockmanager.views.MPopUp;
import dev.mateusneres.stockmanager.views.hooks.NumericDocumentFilter;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.MaskFormatter;
import java.awt.*;

@Getter
public class SupplierHandleComponent extends MPopUp {

    private final JLabel titleLabel;
    private final JLabel idLabel;
    private final JLabel nameLabel;
    private final JLabel addressLabel;
    private final JLabel phoneLabel;
    private final JTextField nameField;
    private final JTextField addressField;
    private final JFormattedTextField phoneField;
    private final JButton confirmButton;
    private final OperationType operationType;

    @SneakyThrows
    public SupplierHandleComponent(HomeController homeController, OperationType operationType, Supplier supplier) {
        super("StockManager - " + operationType.getName() + " Supplier", homeController);
        this.operationType = operationType;

        titleLabel = new JLabel(operationType.getName() + " Supplier!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#353b40")), BorderFactory.createEmptyBorder(30, 0, 20, 0)));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Border defaultBorder = BorderFactory.createEmptyBorder(20, 0, 0, 0);
        idLabel = new JLabel("ID: " + (supplier != null ? String.valueOf(supplier.getId()) : "..."));
        idLabel.setBorder(defaultBorder);

        nameLabel = new JLabel("Name: ");
        nameLabel.setBorder(defaultBorder);
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);

        addressLabel = new JLabel("Address: ");
        addressLabel.setBorder(defaultBorder);
        addressLabel.setHorizontalAlignment(SwingConstants.LEFT);

        phoneLabel = new JLabel("Phone: ");
        phoneLabel.setBorder(defaultBorder);
        phoneLabel.setHorizontalAlignment(SwingConstants.LEFT);

        nameField = new JTextField();

        addressField = new JTextField();

        MaskFormatter maskFormatter = new MaskFormatter("(##) # ####-####");
        maskFormatter.setPlaceholderCharacter('_');

        phoneField = new JFormattedTextField(maskFormatter);
        phoneField.setColumns(15);

        AbstractDocument doc = (AbstractDocument) phoneField.getDocument();
        doc.setDocumentFilter(new NumericDocumentFilter());

        confirmButton = new JButton(operationType.getName() + " Supplier!");
        confirmButton.setPreferredSize(new Dimension(getWidth(), 50));
        confirmButton.setBackground(Color.decode("#02a864"));
        confirmButton.setForeground(Color.WHITE);

        initEditData(supplier);
        initComponents();
    }

    private void initComponents() {
        JPanel contentPane = new JPanel(new VerticalLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));

        JPanel separator = new JPanel();
        separator.setPreferredSize(new Dimension(getWidth(), 20));

        contentPane.add(titleLabel);
        if (operationType == OperationType.UPDATE) contentPane.add(idLabel);
        contentPane.add(nameLabel);
        contentPane.add(nameField);
        contentPane.add(addressLabel);
        contentPane.add(addressField);
        contentPane.add(phoneLabel);
        contentPane.add(phoneField);
        contentPane.add(separator);
        contentPane.add(confirmButton);

        setContentPane(contentPane);
    }

    private void initEditData(Supplier supplier) {
        if (operationType != OperationType.UPDATE || supplier == null) return;

        nameField.setText(supplier.getName());
        addressField.setText(supplier.getAddress());
        phoneField.setValue(supplier.getPhone());
    }

}

