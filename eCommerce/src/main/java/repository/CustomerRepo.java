/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;
import models.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Student
 */
// CustomerRepository.java

public class CustomerRepo {
    private Connection connection;

    // Constructor
    public CustomerRepo(Connection connection) {
        this.connection = connection;
    }

    // Method to create a new customer
    public void createCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO Customer (name, email, phone) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());
            statement.executeUpdate();
        }
    }
}

