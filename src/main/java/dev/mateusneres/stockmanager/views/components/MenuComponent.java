package dev.mateusneres.stockmanager.views.components;

import dev.mateusneres.stockmanager.controllers.HomeController;
import dev.mateusneres.stockmanager.views.MPopUp;
import lombok.Getter;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import java.awt.*;

@Getter
public class MenuComponent extends MPopUp {

    private final JButton addPurchaseButton;
    private final JButton addProductButton;
    private final JButton listProductsButton;
    private final JButton addSupplierButton;
    private final JButton listSupplierButton;

    public MenuComponent(HomeController homeController) {
        super("StockManager - Menu", homeController);

        addPurchaseButton = new JButton("Add Purchase!");
        addPurchaseButton.setPreferredSize(new Dimension(getWidth(), 50));

        addProductButton = new JButton("Add Product!");
        addProductButton.setPreferredSize(new Dimension(getWidth(), 50));
        listProductsButton = new JButton("List Products!");
        listProductsButton.setPreferredSize(new Dimension(getWidth(), 50));

        addSupplierButton = new JButton("Add Supplier!");
        addSupplierButton.setPreferredSize(new Dimension(getWidth(), 50));

        listSupplierButton = new JButton("List Suppliers!");
        listSupplierButton.setPreferredSize(new Dimension(getWidth(), 50));

        initComponents();
    }

    private void initComponents() {
        JPanel contentPane = new JPanel(new VerticalLayout(10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(0, 50, 0 , 50));

        JLabel purchaseLabel = new JLabel("Purchase:");
        purchaseLabel.setFont(new Font("Arial", Font.BOLD, 20));
        purchaseLabel.setBorder(BorderFactory.createEmptyBorder(20,0, 0, 0));
        purchaseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(purchaseLabel);
        contentPane.add(addPurchaseButton);

        JLabel productsLabel = new JLabel("Product:");
        productsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        productsLabel.setBorder(BorderFactory.createEmptyBorder(15,0, 0, 0));
        productsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(productsLabel);
        contentPane.add(addProductButton);
        contentPane.add(listProductsButton);

        JLabel suppliersLabel = new JLabel("Supplier:");
        suppliersLabel.setFont(new Font("Arial", Font.BOLD, 20));
        suppliersLabel.setBorder(BorderFactory.createEmptyBorder(15,0, 0, 0));
        suppliersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(suppliersLabel);
        contentPane.add(addSupplierButton);
        contentPane.add(listSupplierButton);

        setContentPane(contentPane);
    }

}
