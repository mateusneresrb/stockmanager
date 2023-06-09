package dev.mateusneres.stockmanager.database;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * This class is responsible for managing the MySQL connection.
 */
public class MySQLManager {

    private static MySQLManager instance;
    private Connection connection;

    private MySQLManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/stockmanager";
            String username = "admin";
            String password = "stockmanager1234";
            this.connection = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException ex) {
            Logger.getGlobal().severe("Something is wrong with the DB connection String: " + ex.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "There was a problem with the database connection: " + e.getMessage(), "Error on MySQL connection!", JOptionPane.ERROR_MESSAGE);
            Logger.getGlobal().severe("There was a problem with the database connection: " + e.getMessage());
        }
    }

    /**
     * This method is responsible for init the tables in the database.
     */
    public void initTables() {
        try (Connection conn = getConnection(); Statement statement = conn.createStatement()) {
            statement.addBatch("CREATE TABLE IF NOT EXISTS `users` ( `userID` VARCHAR(36) NOT NULL, `name` VARCHAR(45) NOT NULL, `email` VARCHAR(45) UNIQUE NOT NULL, `password` VARCHAR(60) NOT NULL, `nonce` VARCHAR(20) NOT NULL, PRIMARY KEY (`userID`))");
            statement.addBatch("CREATE TABLE IF NOT EXISTS `suppliers` ( `id` INT NOT NULL AUTO_INCREMENT, `name` VARCHAR(155) NOT NULL, `address` VARCHAR(255) NOT NULL, `phone` VARCHAR(18) NOT NULL, PRIMARY KEY (`id`))");
            statement.addBatch("CREATE TABLE IF NOT EXISTS `products` ( `id` INT NOT NULL AUTO_INCREMENT, `name` VARCHAR(155) NOT NULL, `price` FLOAT NOT NULL, `amountAvailable` INT(5) NOT NULL, PRIMARY KEY (`id`))");
            statement.addBatch("CREATE TABLE IF NOT EXISTS `purchases` ( `id` INT NOT NULL AUTO_INCREMENT, `date` BIGINT(19) NOT NULL, `total` FLOAT NOT NULL, `supplier_id` INT NOT NULL, PRIMARY KEY (`id`, `supplier_id`), CONSTRAINT `fk_purchases_suppliers` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION)");
            statement.addBatch("CREATE TABLE IF NOT EXISTS `purchases_product` ( `id` INT NOT NULL AUTO_INCREMENT, `product_id` INT NOT NULL, `purchase_id` INT NOT NULL, `supplier_id` INT NOT NULL, `quantity` INT NOT NULL, PRIMARY KEY (`id`, `product_id`, `purchase_id`, `supplier_id`), CONSTRAINT `fk_products_has_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE, CONSTRAINT `fk_products_has_purchases` FOREIGN KEY (`purchase_id` , `supplier_id`) REFERENCES `purchases` (`id` , `supplier_id`) ON DELETE CASCADE ON UPDATE NO ACTION)");

            statement.executeBatch();
        } catch (SQLException e) {
            Logger.getGlobal().severe("There was a problem with the database connection in InitTables: " + e.getMessage());
        }
    }

    /**
     * This method is responsible for returning the instance of the MySQLManager.
     * @return MySQLManager
     */
    public static synchronized MySQLManager getInstance() {
        try {
            if (instance == null || instance.getConnection() == null || instance.getConnection().isClosed()) {
                instance = new MySQLManager();
            }
        } catch (SQLException e) {
            Logger.getGlobal().severe("There was a problem with the database connection: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "There was a problem with the database connection: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return instance;
    }

    /**
     * This method is responsible for returning the connection.
     * @return Connection
     */
    public Connection getConnection() {
        return connection;
    }

}
