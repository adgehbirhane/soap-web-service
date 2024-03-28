package service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.sql.Connection;
import java.sql.SQLException;
import models.*;
import repository.*; 
// import java.util.*; 

@WebService(serviceName = "eCommerceWebService")
public class eCommerceWebService {

    // Method to create a new customer
    @WebMethod(operationName = "createCustomer")
    public String createCustomer(@WebParam(name = "name") String name,
                                 @WebParam(name = "email") String email,
                                 @WebParam(name = "phone") String phone) {
        System.out.println("name: "+name+ "email: "+ email+"phone: "+phone);
//        return "name: "+name+ "email: "+ email+"phone: "+phone;
        try {
            Connection connection = DatabaseConnection.getConnection();
            CustomerRepo customerRepo = new CustomerRepo(connection);
            Customer customer = new Customer();
            customer.setName(name);
            customer.setEmail(email);
            customer.setPhone(phone);
            customerRepo.createCustomer(customer);
            return "Customer created successfully!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error creating customer: " + e.getMessage();
        }
    }
    
        @WebMethod(operationName = "sendMessage")
    public String sendMessage(@WebParam(name = "name") String name,
                                 @WebParam(name = "message") String message) {
        return "Hi, "+name+ " Your message is: "+message;
    } 
}
