package dev.mateusneres.stockmanager.repositories;

import dev.mateusneres.stockmanager.database.MySQLManager;
import dev.mateusneres.stockmanager.models.User;
import dev.mateusneres.stockmanager.utils.EmailValidator;
import dev.mateusneres.stockmanager.utils.PasswordHasher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Logger;

public class UserRepository {

    public User loginAndGet(String email, char[] password) {
        String query = "SELECT * FROM users WHERE email = ?";

        try (Connection connection = MySQLManager.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    UUID userID = UUID.fromString(resultSet.getString("userID"));
                    String storedHash = resultSet.getString("password");
                    String storedNonce = resultSet.getString("nonce");

                    String passwordWithNonce = new String(password) + storedNonce;

                    if (PasswordHasher.checkPassword(passwordWithNonce.toCharArray(), storedHash)) {
                        return new User(
                                userID, resultSet.getString("name"),
                                email, storedHash, storedNonce);
                    }

                }
            }
        } catch (SQLException e) {
            Logger.getGlobal().severe("Error on login: " + e.getMessage());
        }

        return null;
    }

    public User findByEmail(String email) {
        String query = "SELECT userID, name, email, nonce FROM users WHERE email = ?";

        try (Connection connection = MySQLManager.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            UUID.fromString(resultSet.getString("userID")),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("nonce")
                    );
                }
            }
        } catch (SQLException e) {
            Logger.getGlobal().severe("Error on find user by email: " + e.getMessage());
        }

        return null;
    }

    private boolean isAccountExists(String email) {
        String query = "SELECT email FROM users WHERE email = ?";

        try (Connection connection = MySQLManager.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) return true;
            }

        } catch (SQLException e) {
            Logger.getGlobal().severe("Error on verify account is exists: " + e.getMessage());
        }

        return false;
    }

    public boolean register(String name, String email, char[] password) {
        String query = "INSERT INTO users (userID, name, email, password, nonce) VALUES (?, ?, ?, ?, ?)";

        if (!EmailValidator.validateEmail(email)) return false;
        if (isAccountExists(email)) return false;
        try (Connection connection = MySQLManager.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            String nonce = PasswordHasher.generateNonce();

            String passwordWithNonce = new String(password) + nonce;
            String hashedPassword = PasswordHasher.encodePassword(passwordWithNonce.toCharArray());

            preparedStatement.setString(1, String.valueOf(UUID.randomUUID()));
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, hashedPassword);
            preparedStatement.setString(5, nonce);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getGlobal().severe("Error while registering user: " + e.getMessage());
        }

        return true;
    }


}
