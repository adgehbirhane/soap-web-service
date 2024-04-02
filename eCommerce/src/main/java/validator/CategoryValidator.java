/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validator;

import jakarta.xml.ws.WebServiceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author Student
 */
public class CategoryValidator {

    private Connection connection;

    // Constructor
    public CategoryValidator(Connection connection) {
        this.connection = connection;
    }

    public boolean isValidCategoryInfo(String name, String description) {
        if (name == null || name.length() == 0) {
            throw new WebServiceException("Category name is required!");
        } else if (description == null || description.length() == 0) {
            throw new WebServiceException("Category description is required!");
        }
        return true;
    }

    public boolean isValidId(int id) throws SQLException {
        if (id <= 0) {
            throw new WebServiceException("Category id must be an integer greater than zero!");
        }
        String query = "SELECT * FROM \"public\".\"Category\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                int rowCount = 0;
                while (resultSet.next()) {
                    rowCount++;
                } 
                if(rowCount>= 1){
                   return true;
                }else{
                    throw new WebServiceException("The provided id is not found");
                }
            }
        } catch (SQLException e) {
            throw new WebServiceException("An error occurred while processing the request", e);
        }
    }
}
