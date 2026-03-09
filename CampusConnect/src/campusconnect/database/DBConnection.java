package campusconnect.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Central JDBC connection utility.
 */
public final class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/campus_connect?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found. Add Connector/J to classpath.", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
