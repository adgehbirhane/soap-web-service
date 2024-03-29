/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import models.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.*;

public class CustomerRepo {

    private Connection connection;

    // Constructor
    public CustomerRepo(Connection connection) {
        this.connection = connection;
    }

    // Method to create a new customer
    public void create(Customer customer) throws SQLException {
        String query = "INSERT INTO \"public\".\"Customer\" (name, email, phone) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());
            statement.executeUpdate();
        }
    }

    // Method to retrieve all customers
    public List<Customer> findAll() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM \"public\".\"Customer\"";
        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("id"));
                customer.setName(resultSet.getString("name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPhone(resultSet.getString("phone"));
                customers.add(customer);
            }
        }
        return customers;
    }

    // Method to retrieve a customer by ID
    public Customer findById(int customerId) throws SQLException {
        String query = "SELECT * FROM \"public\".\"Customer\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, customerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Customer customer = new Customer();
                    customer.setId(resultSet.getInt("id"));
                    customer.setName(resultSet.getString("name"));
                    customer.setEmail(resultSet.getString("email"));
                    customer.setPhone(resultSet.getString("phone"));
                    return customer;
                }
            }
        }
        return null; // Customer with the given ID not found
    }
 
    public void updateById(Customer customer) throws SQLException {
        String query = "UPDATE \"public\".\"Customer\" SET name = ?, email = ?, phone = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());
            statement.setInt(4, customer.getId());
            statement.executeUpdate();
        }
    }
 
    public void deleteById(int customerId) throws SQLException {
        String query = "DELETE FROM \"public\".\"Customer\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, customerId);
            statement.executeUpdate();
        }
    }
}
