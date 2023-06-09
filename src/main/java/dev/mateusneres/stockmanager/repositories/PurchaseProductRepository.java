package dev.mateusneres.stockmanager.repositories;

import dev.mateusneres.stockmanager.database.MySQLManager;
import dev.mateusneres.stockmanager.models.Product;
import dev.mateusneres.stockmanager.models.Purchase;
import dev.mateusneres.stockmanager.models.PurchaseProduct;
import dev.mateusneres.stockmanager.models.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PurchaseProductRepository {

    public List<PurchaseProduct> findAll() {
        String query = "SELECT * FROM purchases_product JOIN products ON purchases_product.product_id = products.id " +
                "JOIN purchases ON purchases_product.purchase_id = purchases.id " +
                "JOIN suppliers ON purchases_product.supplier_id = suppliers.id";

        List<PurchaseProduct> purchaseProducts = new ArrayList<>();

        try (Connection connection = MySQLManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Supplier supplier = new Supplier(resultSet.getInt("suppliers.id"),
                        resultSet.getString("suppliers.name"),
                        resultSet.getString("suppliers.address"),
                        resultSet.getString("suppliers.phone")
                );

                purchaseProducts.add(new PurchaseProduct(
                        resultSet.getInt("id"),

                        new Product(resultSet.getInt("products.id"),
                                resultSet.getString("products.name"),
                                resultSet.getDouble("products.price"),
                                resultSet.getInt("products.amountAvailable")
                        ),

                        new Purchase(resultSet.getInt("purchases.id"),
                                Instant.ofEpochMilli(resultSet.getLong("purchases.date")),
                                resultSet.getDouble("purchases.total"),
                                supplier
                        ),

                        supplier, resultSet.getInt("quantity")
                ));
            }

        } catch (Exception e) {
            Logger.getGlobal().severe("Error on findAll purchaseProduct: " + e.getMessage());
        }
        return purchaseProducts;
    }

    public Purchase createPurchase(Purchase purchase) {
        String query = "INSERT INTO purchases (date, total, supplier_id) VALUES (?, ?, ?)";

        try (Connection connection = MySQLManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, purchase.getPurchaseDate().toEpochMilli());
            preparedStatement.setDouble(2, purchase.getTotalValue());
            preparedStatement.setInt(3, purchase.getSupplier().getId());
            preparedStatement.execute();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return new Purchase(
                            resultSet.getInt(1),
                            purchase.getPurchaseDate(),
                            purchase.getTotalValue(),
                            purchase.getSupplier()
                    );
                }
            }

        } catch (Exception e) {
            Logger.getGlobal().severe("Error on add purchase: " + e.getMessage());
        }
        return null;
    }

    public boolean updatePurchase(Purchase purchase) {
        String query = "UPDATE purchases SET date = ?, total = ?, supplier_id = ? WHERE id = ?";

        try (Connection connection = MySQLManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, purchase.getPurchaseDate().toEpochMilli());
            preparedStatement.setDouble(2, purchase.getTotalValue());
            preparedStatement.setInt(3, purchase.getSupplier().getId());
            preparedStatement.setInt(4, purchase.getId());
            preparedStatement.execute();

            return true;

        } catch (Exception e) {
            Logger.getGlobal().severe("Error on update purchase: " + e.getMessage());
        }
        return false;
    }

    public PurchaseProduct createPurchaseProduct(Product product, Purchase purchase, Supplier supplier, int quantity) {
        String query = "INSERT INTO purchases_product (product_id, purchase_id, supplier_id, quantity) VALUES (?, ?, ?, ?)";

        try (Connection connection = MySQLManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, product.getId());
            preparedStatement.setInt(2, purchase.getId());
            preparedStatement.setInt(3, supplier.getId());
            preparedStatement.setInt(4, quantity);
            preparedStatement.execute();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return new PurchaseProduct(
                            resultSet.getInt(1),
                            product,
                            purchase,
                            supplier,
                            quantity
                    );
                }
            }

        } catch (Exception e) {
            Logger.getGlobal().severe("Error on add purchaseProduct: " + e.getMessage());
        }

        return null;
    }

    public boolean updatePurchaseProduct(PurchaseProduct purchaseProduct) {
        String query = "UPDATE purchases_product SET product_id = ?, purchase_id = ?, supplier_id = ?, quantity = ? WHERE id = ?";

        try (Connection connection = MySQLManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, purchaseProduct.getProduct().getId());
            preparedStatement.setInt(2, purchaseProduct.getPurchase().getId());
            preparedStatement.setInt(3, purchaseProduct.getSupplier().getId());
            preparedStatement.setInt(4, purchaseProduct.getQuantity());
            preparedStatement.setInt(5, purchaseProduct.getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            Logger.getGlobal().severe("Error on update purchaseProduct: " + e.getMessage());
        }

        return false;
    }

    public boolean deletePurchaseProduct(int id) {
        String query = "DELETE FROM purchases_product WHERE id = ?";

        try (Connection connection = MySQLManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            Logger.getGlobal().severe("Error on delete purchaseProduct: " + e.getMessage());
        }

        return false;
    }

}
