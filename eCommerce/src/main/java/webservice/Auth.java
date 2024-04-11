package webservice;

import DBConnection.PGConnection;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import models.AuthModel;
import models.Customer;
import models.Token;
import repository.AuthRepo;
import repository.CustomerRepo;
import utils.PasswordHasher;
import validator.CustomerValidator;

@WebService(serviceName = "auth")
public class Auth {

    @WebMethod(operationName = "signup")
    public String createCustomer(
            @WebParam(name = "name") String name,
            @WebParam(name = "email") String email,
            @WebParam(name = "phone") String phone,
            @WebParam(name = "password") String password) {
        try {
            Connection connection = PGConnection.getConnection();
            CustomerValidator custValidator = new CustomerValidator(connection);

            String hashedPassword;

            try {
                hashedPassword = PasswordHasher.hashPassword(password);
            } catch (NoSuchAlgorithmException e) {
                throw new WebServiceException("Error hashing password: " + e.getMessage());
            }

            if (custValidator.isValidCustomerInfo(name, email, phone, password)) {
                CustomerRepo customerRepo = new CustomerRepo(connection);
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(email);
                customer.setPhone(phone);
                customer.setPassword(hashedPassword);
                customerRepo.create(customer);
                return "Customer created successfully!";
            } else {
                throw new WebServiceException("An error occurred while processing the request: Please check the email.");
            }
        } catch (SQLException e) {
            throw new WebServiceException("An error occurred while processing the request");
        }
    }

    @WebMethod(operationName = "signin")
    public Token signIn(@WebParam(name = "email") String email,
            @WebParam(name = "password") String password) {
        try {
            Connection connection = PGConnection.getConnection();

            if (password == null || password.length() < 4) {
                throw new WebServiceException("Password must be at least 4 characters long!");
            }

            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(email);

            if (email == null || email.length() == 0 || !matcher.matches()) { 
                throw new WebServiceException("Please enter a valid email!");
            }
            AuthRepo authRepo = new AuthRepo(connection);
            AuthModel authModel = new AuthModel();
            authModel.setEmail(email);
            authModel.setPassword(password);
                return authRepo.signIn(authModel); 
        } catch (SQLException e) {
            throw new WebServiceException("An error occurred while processing the request");
        }
    } 
}
