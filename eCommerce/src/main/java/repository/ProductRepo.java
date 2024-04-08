/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepo {

    private Connection connection;

    public ProductRepo(Connection connection) {
        this.connection = connection;
    }

    public String create(Product product) throws SQLException {
        String insertQuery = "INSERT INTO \"public\".\"product\" (name, description, category_id, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

            insertStatement.setString(1, product.getName());
            insertStatement.setString(2, product.getDescription());
            insertStatement.setInt(3, product.getCategory_id());
            insertStatement.setDouble(4, product.getPrice());

            int affectedRows = insertStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating product failed, no rows affected.");
            }

           return "Product registered Succeed";
        }
    } 

    public List<Product> findAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM \"public\".\"product\"";
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

    public Product findById(int productId) throws SQLException {
        String query = "SELECT * FROM \"public\".\"product\" WHERE id = ?";
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
        return null;
    }

    public boolean updateById(Product product) throws SQLException {
        String query = "UPDATE \"public\".\"product\" SET name = ?, description = ?, category_id = ?, price = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setInt(3, product.getCategory_id());
            statement.setDouble(4, product.getPrice());
            statement.setInt(5, product.getId());
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }

    public void deleteById(int productId) throws SQLException {
        String query = "DELETE FROM \"public\".\"product\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productId);
            statement.executeUpdate();
        }
    }
}
