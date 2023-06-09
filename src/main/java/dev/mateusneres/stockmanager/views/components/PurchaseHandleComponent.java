package dev.mateusneres.stockmanager.views.components;

import dev.mateusneres.stockmanager.controllers.HomeController;
import dev.mateusneres.stockmanager.enums.OperationType;
import dev.mateusneres.stockmanager.models.PurchaseProduct;
import dev.mateusneres.stockmanager.views.MPopUp;
import dev.mateusneres.stockmanager.views.hooks.NumericDocumentFilter;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import java.awt.*;

@Getter
public class PurchaseHandleComponent extends MPopUp {

    private final JLabel titleLabel;
    private final JLabel idLabel;
    private final JLabel productLabel;
    private final JLabel supplierLabel;
    private final JLabel amountLabel;
    private final JLabel totalPriceLabel;
    private final JComboBox productComboBox;
    private final JComboBox supplierComboBox;
    private final JTextField amountField;
    private final JLabel totalPriceResult;
    private final JButton confirmButton;
    private final OperationType operationType;

    @SneakyThrows
    public PurchaseHandleComponent(HomeController homeController, OperationType operationType, String[] products, String[] suppliers, PurchaseProduct purchaseProduct) {
        super("StockManager - " + operationType.getName() + " Purchase", homeController);
        this.operationType = operationType;

        titleLabel = new JLabel(operationType.getName() + " Purchase!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#353b40")), BorderFactory.createEmptyBorder(25, 0, 20, 0)));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Border defaultBorder = BorderFactory.createEmptyBorder(15, 0, 0, 0);
        idLabel = new JLabel("ID: " + (purchaseProduct != null ? String.valueOf(purchaseProduct.getId()) : "..."));
        idLabel.setBorder(defaultBorder);

        productLabel = new JLabel("Select a product: ");
        productLabel.setBorder(defaultBorder);
        productLabel.setHorizontalAlignment(SwingConstants.LEFT);

        supplierLabel = new JLabel("Select a supplier: ");
        supplierLabel.setBorder(defaultBorder);
        supplierLabel.setHorizontalAlignment(SwingConstants.LEFT);

        amountLabel = new JLabel("Product amount: ");
        amountLabel.setBorder(defaultBorder);
        amountLabel.setHorizontalAlignment(SwingConstants.LEFT);

        totalPriceLabel = new JLabel("Total price: ");
        totalPriceLabel.setBorder(defaultBorder);
        totalPriceLabel.setHorizontalAlignment(SwingConstants.LEFT);

        productComboBox = new JComboBox(products);
        productComboBox.setSelectedItem(null);

        supplierComboBox = new JComboBox(suppliers);
        supplierComboBox.setSelectedItem(null);

        amountField = new JTextField();
        amountField.setColumns(10);
        AbstractDocument docPrice = (AbstractDocument) amountField.getDocument();
        docPrice.setDocumentFilter(new NumericDocumentFilter());

        totalPriceResult = new JLabel("R$ 0");

        confirmButton = new JButton(operationType.getName() + " Purchase!");
        confirmButton.setPreferredSize(new Dimension(getWidth(), 50));
        confirmButton.setBackground(Color.decode("#02a864"));
        confirmButton.setForeground(Color.WHITE);

        initEditData(purchaseProduct);
        initComponents();
    }

    private void initComponents() {
        JPanel contentPane = new JPanel(new VerticalLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));

        JPanel separator = new JPanel();
        separator.setPreferredSize(new Dimension(getWidth(), 20));

        contentPane.add(titleLabel);
        if (operationType == OperationType.UPDATE) contentPane.add(idLabel);
        contentPane.add(productLabel);
        contentPane.add(productComboBox);
        contentPane.add(supplierLabel);
        contentPane.add(supplierComboBox);
        contentPane.add(amountLabel);
        contentPane.add(amountField);
        contentPane.add(totalPriceLabel);
        contentPane.add(totalPriceResult);
        contentPane.add(separator);
        contentPane.add(confirmButton);

        setContentPane(contentPane);
    }

    private void initEditData(PurchaseProduct purchaseProduct) {
        if (operationType != OperationType.UPDATE || purchaseProduct == null) return;

        productComboBox.setSelectedItem(purchaseProduct.getProduct().getId() + " - " + purchaseProduct.getProduct().getName() + " - R$ " + purchaseProduct.getProduct().getPrice());
        supplierComboBox.setSelectedItem(purchaseProduct.getSupplier().getId() + " - " + purchaseProduct.getSupplier().getName());
        amountField.setText(String.valueOf(purchaseProduct.getQuantity()));
        totalPriceResult.setText(String.format("R$ %.2f", (purchaseProduct.getQuantity() * purchaseProduct.getProduct().getPrice())));
    }

}