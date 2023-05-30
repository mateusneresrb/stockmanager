package dev.mateusneres.stockmanager.views.components;

import dev.mateusneres.stockmanager.enums.OperationType;
import dev.mateusneres.stockmanager.models.Product;
import dev.mateusneres.stockmanager.views.MPopUp;
import lombok.Getter;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class ProductHandleComponent extends MPopUp {

    private final JLabel titleLabel;
    @Getter
    private final JLabel idLabel;
    private final JLabel nameLabel;
    private final JLabel priceLabel;
    private final JLabel amountLabel;
    @Getter
    private final JTextField nameField;
    @Getter
    private final JTextField priceField;
    @Getter
    private final JFormattedTextField amountField;
    @Getter
    private final JButton confirmButton;
    @Getter
    private final OperationType operationType;

    public ProductHandleComponent(OperationType operationType, Product product) {
        super("StockManager - " + operationType.getName() + " Product");
        this.operationType = operationType;

        titleLabel = new JLabel(operationType.getName() + " Product!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#353b40")), BorderFactory.createEmptyBorder(30, 0, 20, 0)));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Border defaultBorder = BorderFactory.createEmptyBorder(20, 0, 0, 0);
        idLabel = new JLabel("ID: " + (product != null ? String.valueOf(product.getId()) : "..."));
        idLabel.setBorder(defaultBorder);

        nameLabel = new JLabel("Name: ");
        nameLabel.setBorder(defaultBorder);
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);

        priceLabel = new JLabel("Price: ");
        priceLabel.setBorder(defaultBorder);
        priceLabel.setHorizontalAlignment(SwingConstants.LEFT);

        amountLabel = new JLabel("Amount: ");
        amountLabel.setBorder(defaultBorder);
        amountLabel.setHorizontalAlignment(SwingConstants.LEFT);

        nameField = new JTextField();

        priceField = new JTextField();
        priceField.setColumns(10);
        AbstractDocument docPrice = (AbstractDocument) priceField.getDocument();
        docPrice.setDocumentFilter(new NumericDocumentFilter());

        amountField = new JFormattedTextField();
        amountField.setColumns(10);
        AbstractDocument doc = (AbstractDocument) amountField.getDocument();
        doc.setDocumentFilter(new NumericDocumentFilter());

        confirmButton = new JButton(operationType.getName() + " Product!");
        confirmButton.setPreferredSize(new Dimension(getWidth(), 50));
        confirmButton.setBackground(Color.decode("#02a864"));
        confirmButton.setForeground(Color.WHITE);

        initEditData(product);
        initComponents();
    }

    private void initComponents() {
        JPanel contentPane = new JPanel(new VerticalLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));

        JPanel separator = new JPanel();
        separator.setPreferredSize(new Dimension(getWidth(), 20));

        contentPane.add(titleLabel);
        contentPane.add(idLabel);
        contentPane.add(nameLabel);
        contentPane.add(nameField);
        contentPane.add(priceLabel);
        contentPane.add(priceField);
        contentPane.add(amountLabel);
        contentPane.add(amountField);
        contentPane.add(separator);
        contentPane.add(confirmButton);

        setContentPane(contentPane);
    }

    private void initEditData(Product product) {
        if (operationType != OperationType.UPDATE || product == null) return;

        nameField.setText(product.getName());
        priceField.setText(String.valueOf(product.getPrice()));
        amountField.setText(String.valueOf(product.getAmountAvailable()));
    }

}
