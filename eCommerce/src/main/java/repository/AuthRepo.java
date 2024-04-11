package repository;
 
import DBConnection.PGConnection;
import jakarta.xml.ws.WebServiceException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import models.AuthModel;
import models.Token;
import utils.PasswordHasher;

public class AuthRepo {

    private Connection connection;

    // Constructor
    public AuthRepo(Connection connection) {
        this.connection = connection;
    }
                          
    public Token signIn(AuthModel auth) {
        try {
            Connection connection = PGConnection.getConnection();

            // Retrieve the customer's hashed password from the database
            String query = "SELECT password FROM \"public\".\"customer\" WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, auth.getEmail());
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String hashedPassword = resultSet.getString("password");
                        try {
                            // Verify the provided password against the hashed password
                            if (PasswordHasher.verifyPassword(auth.getPassword(), hashedPassword)) {
                                 Token userToken = new Token();
                                 // Generate a new token
                                String token = generateToken(); 
                                // Update the token in the database
                                updateToken(auth.getEmail(), token); 
                                userToken.setEmail(auth.getEmail());
                                userToken.setToken(token); 
                                return userToken;
                            } else {
                                throw new WebServiceException("Invalid email or password!.");
                            }
                        } catch (NoSuchAlgorithmException e) {
                            // Handle the exception here
                            System.err.println("An error occurred: " + e.getMessage());
                            throw new WebServiceException("No such algorithm exception");
                            // You can also log the exception or throw a more specific exception
                        }
                    } else {
                        throw new WebServiceException("Invalid email or password!..");
                    }
                }
            }
        } catch (SQLException e) {
            throw new WebServiceException("An error occurred while processing the request");
        }
    }

    private String generateToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private void updateToken(String email, String token) throws SQLException {
        String query = "UPDATE \"public\".\"customer\" SET token = ? WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, token);
            statement.setString(2, email);
            statement.executeUpdate();
        }
    }
}