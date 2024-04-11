/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validator;

import DBConnection.PGConnection;
import jakarta.xml.ws.WebServiceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import validator.*;

/**
 *
 * @author Student
 */
public class OrderValidator {

    private Connection connection;

    // Constructor
    public OrderValidator(Connection connection) {
        this.connection = connection;
    }

    public boolean isValidOrderInfo(int customer_id, int product_id, int quantity) throws SQLException {
        if (customer_id <= 0) {
            throw new WebServiceException("The provided customer id is not valid!");
        } else if (product_id <= 0) {
            throw new WebServiceException("The provided product id is not valid!");
        } else if (quantity <= 0) {
            throw new WebServiceException("The provided quantity should be an integer greater than 0!");
        }

        Connection connection = PGConnection.getConnection();

        CustomerValidator custValidator = new CustomerValidator(connection);
        ProductValidator proValidator = new ProductValidator(connection);

        if (custValidator.isValidId(customer_id) && proValidator.isValidId(product_id)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isValidId(int id) throws SQLException {
        if (id <= 0) {
            throw new WebServiceException("Order id must be an integer greater than zero!");
        }
        String query = "SELECT * FROM \"public\".\"Order\" WHERE id = ?";
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

    public boolean isValidStatus(String status) {
        if (status.equalsIgnoreCase("active") || status.equalsIgnoreCase("inactive")) {
            return true;
        } else {
            throw new WebServiceException("Status must be one of 'ACTIVE' OR 'INACTIVE'. case insensitive.");
        }
    }
}
