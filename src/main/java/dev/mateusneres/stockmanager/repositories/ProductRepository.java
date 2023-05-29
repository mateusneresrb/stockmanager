package dev.mateusneres.stockmanager.repositories;

import dev.mateusneres.stockmanager.database.MySQLManager;
import dev.mateusneres.stockmanager.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProductRepository {

    public List<Product> findAll() {
        String query = "SELECT * from products";
        List<Product> productList = new ArrayList<>();

        try (Connection connection = MySQLManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                productList.add(new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("amountAvailable")
                ));
            }
        } catch (SQLException e) {
            Logger.getGlobal().severe("Error on findAll products: " + e.getMessage());
        }

        return productList;
    }

    public boolean addProduct(Product product) {
        String query = "INSERT INTO products (name, price, amountAvailable) VALUES (?, ?, ?)";

        try (Connection connection = MySQLManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getAmountAvailable());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0)
                return true;

        } catch (
                SQLException e) {
            Logger.getGlobal().severe("Error on add product: " + e.getMessage());
        }

        return false;
    }

    public boolean updateProduct(Product product) {
        String query = "UPDATE products SET name = ?, price = ?, amountAvailable = ? WHERE id = ?";

        try (Connection connection = MySQLManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getAmountAvailable());
            preparedStatement.setInt(4, product.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0)
                return true;

        } catch (
                SQLException e) {
            Logger.getGlobal().severe("Error on update product: " + e.getMessage());
        }

        return false;
    }

    public boolean deleteProduct(int id) {
        String query = "DELETE FROM products WHERE id = ?";

        try (Connection connection = MySQLManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0)
                return true;

        } catch (
                SQLException e) {
            Logger.getGlobal().severe("Error on delete product: " + e.getMessage());
        }

        return false;
    }


}
