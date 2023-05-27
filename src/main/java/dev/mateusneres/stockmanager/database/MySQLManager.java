package dev.mateusneres.stockmanager.database;

import java.sql.*;

public class MySQLManager {

    private static MySQLManager instance;
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/jdbc";
    private String username = "username";
    private String password = "password";

    private MySQLManager() {
        try {
            Class.forName("org.mysql.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
            initTables();
        } catch (ClassNotFoundException ex) {
            System.out.println("Something is wrong with the DB connection String: " + ex.getMessage());
        } catch (SQLException e) {
            System.out.println("There was a problem with the database connection: " + e.getMessage());
        }
    }

    private void initTables() {
        try (Connection conn = getConnection(); Statement statement = conn.createStatement()) {
            statement.addBatch("CREATE TABLE IF NOT EXISTS `users` ( `userID` VARCHAR(36) NOT NULL, `name` VARCHAR(45) NOT NULL, `email` VARCHAR(45) NOT NULL, `password` VARCHAR(60) NOT NULL, `nounce` VARCHAR(20) NOT NULL, PRIMARY KEY (`userID`))");
            statement.executeBatch();
        } catch (SQLException e) {
            System.out.println("There was a problem with the database connection: " + e.getMessage());
        }
    }

    public static synchronized MySQLManager getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new MySQLManager();
            }
        } catch (SQLException e) {
            System.out.println("There was a problem with the database connection: " + e.getMessage());
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

}
