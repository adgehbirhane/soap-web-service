/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

/**
 *
 * @author Student
 */ 

import models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepo {

    private Connection connection;

    // Constructor
    public ProductRepo(Connection connection) {
        this.connection = connection;
    }

    // Method to create a new product
    public void create(Product product) throws SQLException {
        String query = "INSERT INTO \"public\".\"Product\" (name, description, category_id, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setInt(3, product.getCategory_id());
            statement.setDouble(4, product.getPrice());
            statement.executeUpdate();
        }
    }

    // Method to retrieve all products
    public List<Product> findAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM \"public\".\"Product\"";
        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setCategory_id(resultSet.getInt("category_id"));
                product.setPrice(resultSet.getDouble("price"));
                products.add(product);
            }
        }
        return products;
    }

    // Method to retrieve a product by ID
    public Product findById(int productId) throws SQLException {
        String query = "SELECT * FROM \"public\".\"Product\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Product product = new Product();
                    product.setId(resultSet.getInt("id"));
                    product.setName(resultSet.getString("name"));
                    product.setDescription(resultSet.getString("description"));
                    product.setCategory_id(resultSet.getInt("category_id"));
                    product.setPrice(resultSet.getDouble("price"));
                    return product;
                }
            }
        }
        return null; // Product with the given ID not found
    }

    // Method to update a product by ID
    public void updateById(Product product) throws SQLException {
        String query = "UPDATE \"public\".\"Product\" SET name = ?, description = ?, category_id = ?, price = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setInt(3, product.getCategory_id());
            statement.setDouble(4, product.getPrice());
            statement.setInt(5, product.getId());
            statement.executeUpdate();
        }
    }

    // Method to delete a product by ID
    public void deleteById(int productId) throws SQLException {
        String query = "DELETE FROM \"public\".\"Product\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productId);
            statement.executeUpdate();
        }
    }
}

