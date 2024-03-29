package DBConnection;

import java.sql.*; 

public class PGConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/ecommerce";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    public static Connection getConnection()throws SQLException { 
        try { 
            return DriverManager.getConnection(URL, USER, PASSWORD); 
        } catch (SQLException e) { 
            System.err.println("Error connecting to the database: " + e.getMessage()); 
          return null;
        } 
    }
}
