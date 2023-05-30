package dev.mateusneres.stockmanager.repositories;

import dev.mateusneres.stockmanager.database.MySQLManager;
import dev.mateusneres.stockmanager.models.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SupplierRepository {

    public List<Supplier> findAll() {
        String query = "SELECT * FROM suppliers";
        List<Supplier> suppliers = new ArrayList<>();

        try (Connection connection = MySQLManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                suppliers.add(new Supplier(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone")
                ));
            }

        } catch (SQLException e) {
            Logger.getGlobal().severe("Error on findAll suppliers: " + e.getMessage());
        }

        return suppliers;
    }

    public boolean addSupplier(Supplier supplier){
        String query = "INSERT INTO suppliers (name, address, phone) VALUES (?, ?, ?)";

        try (Connection connection = MySQLManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, supplier.getName());
            preparedStatement.setString(2, supplier.getAddress());
            preparedStatement.setString(3, supplier.getPhone());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0)
                return true;

        } catch (SQLException e) {
            Logger.getGlobal().severe("Error on add supplier: " + e.getMessage());
        }

        return false;
    }

    public boolean updateSupplier(Supplier supplier){
        String query = "UPDATE suppliers SET name = ?, address = ?, phone = ? WHERE id = ?";

        try (Connection connection = MySQLManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, supplier.getName());
            preparedStatement.setString(2, supplier.getAddress());
            preparedStatement.setString(3, supplier.getPhone());
            preparedStatement.setInt(4, supplier.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0)
                return true;

        } catch (SQLException e) {
            Logger.getGlobal().severe("Error on update supplier: " + e.getMessage());
        }

        return false;
    }

    public boolean deleteSupplier(int id){
        String query = "DELETE FROM suppliers WHERE id = ?";

        try (Connection connection = MySQLManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0)
                return true;

        } catch (SQLException e) {
            Logger.getGlobal().severe("Error on delete supplier: " + e.getMessage());
        }

        return false;
    }

}
