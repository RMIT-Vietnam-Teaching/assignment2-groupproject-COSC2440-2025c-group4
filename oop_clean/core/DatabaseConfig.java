package oop_clean.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class that manages the database connection.
 * Ensures only one connection instance exists throughout the application.
 * Loads database credentials from environment variables for security.
 * 
 * @author Group04
 */
public class DatabaseConfig {
    private static DatabaseConfig instance;
    private Connection connection;

    private DatabaseConfig() throws SQLException {
        try {
            String url = System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/eventdb");
            String user = System.getenv().getOrDefault("DB_USER", "postgres");
            String pass = System.getenv().getOrDefault("DB_PASSWORD", "password");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw e;
        }
    }

    public static synchronized DatabaseConfig getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
