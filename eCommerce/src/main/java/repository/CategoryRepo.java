/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import models.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
 
public class CategoryRepo {

    private Connection connection;

    // Constructor
    public CategoryRepo(Connection connection) {
        this.connection = connection;
    }

    // Method to create a new category
    public void create(Category category) throws SQLException {
        String query = "INSERT INTO \"public\".\"category\" (name, description) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.executeUpdate();
        }
    }

    // Method to retrieve all categories
    public List<Category> findAll() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM \"public\".\"category\"";
        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));
                categories.add(category);
            }
        }
        return categories;
    }

    // Method to retrieve a category by ID
    public Category findById(int categoryId) throws SQLException {
        String query = "SELECT * FROM \"public\".\"category\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Category category = new Category();
                    category.setId(resultSet.getInt("id"));
                    category.setName(resultSet.getString("name"));
                    category.setDescription(resultSet.getString("description"));
                    return category;
                }
            }
        }
        return null; // Category with the given ID not found
    }

    // Method to update a category by ID
    public void updateById(Category category) throws SQLException {
        String query = "UPDATE \"public\".\"category\" SET name = ?, description = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.setInt(3, category.getId());
            statement.executeUpdate();
        }
    }

    // Method to delete a category by ID
    public void deleteById(int categoryId) throws SQLException {
        String query = "DELETE FROM \"public\".\"category\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, categoryId);
            statement.executeUpdate();
        }
    }
}

