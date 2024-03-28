package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*; 

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/ecommerce";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    public static Connection getConnection()throws SQLException { 
        try {
//            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
//            System.out.println("Database connection established successfully.");
        } catch (SQLException e) { 
            System.err.println("Error connecting to the database: " + e.getMessage()); 
          return null;
        } 
    }
}
