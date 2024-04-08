/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validator;

import jakarta.xml.ws.WebServiceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Student
 */
public class ProductValidator {

    private Connection connection;

    // Constructor
    public ProductValidator(Connection connection) {
        this.connection = connection;
    }

    private boolean isCategoryExist(int id) throws SQLException {
        if (id <= 0) {
            throw new WebServiceException("Category id must be an integer greater than zero!");
        }
        String query = "SELECT * FROM \"public\".\"category\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                int rowCount = 0;
                while (resultSet.next()) {
                    rowCount++;
                }
                if (rowCount >= 1) {
                    return true;
                } else {
                    throw new WebServiceException("The provided category id is not found");
                }
            }
        } catch (SQLException e) {
            throw new WebServiceException("An error occurred while processing the request", e);
        }
    }

    public boolean isValidProductInfo(int categoryId, double price, String name, String description)
            throws SQLException {
        if (!isCategoryExist(categoryId)) {
            throw new WebServiceException("Category Id should be valid. Hint: Category should be preregisted!");
        } else if (price <= 0) {
            throw new WebServiceException("Price of the product should be decimal number!");
        } else if (name == null || name.length() == 0) {
            throw new WebServiceException("Product name is required!");
        } else if (description == null || description.length() == 0) {
            throw new WebServiceException("Product description is required!");
        }
        return true;
    }

    public boolean isValidId(int id) throws SQLException {
        if (id <= 0) {
            throw new WebServiceException("Product id must be an integer greater than zero!");
        }
        String query = "SELECT * FROM \"public\".\"product\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                int rowCount = 0;
                while (resultSet.next()) {
                    rowCount++;
                }
                if (rowCount >= 1) {
                    return true;
                } else {
                    throw new WebServiceException("The provided id is not found");
                }
            }
        } catch (SQLException e) {
            throw new WebServiceException("An error occurred while processing the request", e);
        }
    }
}
