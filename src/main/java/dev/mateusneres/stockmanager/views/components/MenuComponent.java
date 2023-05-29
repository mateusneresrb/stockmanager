package dev.mateusneres.stockmanager.views.components;

import dev.mateusneres.stockmanager.views.MPopUp;
import lombok.Getter;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import java.awt.*;

@Getter
public class MenuComponent extends MPopUp {

    private final JButton addProductButton;
    private final JButton listProductsButton;
    private final JButton addSupplierButton;
    private final JButton listSupplierButton;

    public MenuComponent() {
        super("StockManager - Menu");

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

        JLabel productsLabel = new JLabel("Product:");
        productsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        productsLabel.setBorder(BorderFactory.createEmptyBorder(55,0, 0, 0));
        productsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(productsLabel);
        contentPane.add(addProductButton);
        contentPane.add(listProductsButton);

        JLabel suppliersLabel = new JLabel("Supplier:");
        suppliersLabel.setFont(new Font("Arial", Font.BOLD, 20));
        suppliersLabel.setBorder(BorderFactory.createEmptyBorder(30,0, 0, 0));
        suppliersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(suppliersLabel);
        contentPane.add(addSupplierButton);
        contentPane.add(listSupplierButton);

        setContentPane(contentPane);
    }

}
