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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Student
 */
public class CustomerValidator {

    private Connection connection;

    // Constructor
    public CustomerValidator(Connection connection) {
        this.connection = connection;
    }

    public boolean isValidEmail(String email) throws SQLException {
        // Regular expression for email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            // Email format is invalid
            return false;
        }

        // Email format is valid, check if it already exists in the database
        String query = "SELECT COUNT(*) FROM public.customer WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                int count = resultSet.getInt(1);
                if (count > 0) {
                    // Email already exists
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new WebServiceException("An error occurred while processing the request: Please check the email.", e);
        }

        // Email format is valid and doesn't exist in the database
        return true;
    }

    private boolean isValidPhone(String phone) {
        // Check if the phone starts with "09" or "07" and is 10 characters long
        return phone != null && (phone.startsWith("09") || phone.startsWith("07")) && phone.length() == 10;
    }

    public boolean isValidCustomerInfo(String name, String email, String phone, String password) throws SQLException {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        if (email == null || email.length() == 0 || !matcher.matches()) {
            // Email format is invalid
            throw new WebServiceException("Please enter a valid email!");
        }

        // Email format is valid, check if it already exists in the database
        String query = "SELECT 1 FROM \"public\".\"customer\" WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Email already exists
                    throw new WebServiceException("The provided email is already registered!");
                }
            }
        } catch (SQLException e) {
            // Handle other SQLExceptions
            throw new WebServiceException("An error occurred while processing the request: ");
        }

        if (name == null || name.length() == 0) {
            throw new WebServiceException("Customer name is required!");
        } else if (phone == null || !isValidPhone(phone)) {
            throw new WebServiceException("Please enter a valid phone number. Hint: phone starts with \"09\" or \"07\" and is 10 characters long!");
        } else if (password == null || password.length() < 4) {
            throw new WebServiceException("Password must be at least 4 characters long!");
        }

        return true;
    }

    public boolean isValidMainCustomerInfo(String name, String email, String phone) throws SQLException {
        if (name == null || name.length() == 0) {
            throw new WebServiceException("Customer name is required!");
        } else if (email == null || email.length() == 0 || !isValidEmail(email)) {
            throw new WebServiceException("Please enter valid email!");
        } else if (phone == null || !isValidPhone(phone)) {
            throw new WebServiceException("please enter valid phone. Hint: phone starts with \"09\" or \"07\" and is 10 characters long!");
        }
        return true;
    }

    public boolean isValidId(int id) throws SQLException {
        if (id <= 0) {
            throw new WebServiceException("Customer id must be an integer greater than zero!");
        }
        String query = "SELECT * FROM \"public\".\"customer\" WHERE id = ?";
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
                    throw new WebServiceException("The provided customer id is not found");
                }
            }
        } catch (SQLException e) {
            throw new WebServiceException("An error occurred while processing the request");
        }
    }
}
