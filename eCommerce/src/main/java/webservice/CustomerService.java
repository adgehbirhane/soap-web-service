package webservice;

import jakarta.jws.*;
import java.sql.*;
import models.*;
import DBConnection.PGConnection;
import java.util.List; 
import repository.*;

@WebService(serviceName = "customer")
public class CustomerService {

    @WebMethod(operationName = "createOne")
    public String createCustomer(@WebParam(name = "name") String name,
            @WebParam(name = "email") String email,
            @WebParam(name = "phone") String phone) {
        try {
            Connection connection = PGConnection.getConnection();
            CustomerRepo customerRepo = new CustomerRepo(connection);
            Customer customer = new Customer();
            customer.setName(name);
            customer.setEmail(email);
            customer.setPhone(phone);
            customerRepo.create(customer);
            return "Customer created successfully!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error creating customer: " + e.getMessage();
        }
    }

    @WebMethod(operationName = "findAll")
    public List<Customer> findAll() {
        try {
            Connection connection = PGConnection.getConnection();
            CustomerRepo customerRepo = new CustomerRepo(connection);
            return customerRepo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod(operationName = "findById")
    public Customer finById(@WebParam(name = "id") int id) {
        try {
            Connection connection = PGConnection.getConnection();
            CustomerRepo customerRepo = new CustomerRepo(connection);
            return customerRepo.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod(operationName = "updateOne")
    public Customer updateCustomer(@WebParam(name = "id") int id, @WebParam(name = "name") String name,
            @WebParam(name = "email") String email,
            @WebParam(name = "phone") String phone) {
        try {
            Connection connection = PGConnection.getConnection();
            CustomerRepo customerRepo = new CustomerRepo(connection);
            Customer customer = new Customer();
            customer.setId(id);
            customer.setName(name);
            customer.setEmail(email);
            customer.setPhone(phone);
            customerRepo.updateById(customer);
            return customerRepo.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod(operationName = "deleteById")
    public String deleteById(@WebParam(name = "id") int id) {
        try {
            Connection connection = PGConnection.getConnection();
            CustomerRepo customerRepo = new CustomerRepo(connection);
            customerRepo.deleteById(id);
            return "Customer deleted successfully!";
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
