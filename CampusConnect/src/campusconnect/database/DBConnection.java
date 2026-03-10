package campusconnect.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection
 * Handles MySQL database connectivity using JDBC.
 */
public final class DBConnection {

    // Database URL
    private static final String URL =
            "jdbc:mysql://localhost:3306/campus_connect?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    // Database credentials
    private static final String USER = "root";
    private static final String PASSWORD = "password";   // change if your MySQL password is different

    private DBConnection() {
        // Prevent object creation
    }

    /**
     * Returns a connection to the database
     */
    public static Connection getConnection() throws SQLException {

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found. Add mysql-connector-j to your project.", e);
        }

        // Create and return connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
