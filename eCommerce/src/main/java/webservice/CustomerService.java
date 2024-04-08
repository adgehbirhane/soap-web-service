package webservice;

import jakarta.jws.*;
import java.sql.*;
import models.*;
import DBConnection.PGConnection;
import jakarta.xml.ws.WebServiceException;
import java.util.List;
import repository.*;
import validator.CustomerValidator;

@WebService(serviceName = "customer")
public class CustomerService {

    @WebMethod(operationName = "createOne")
    public String createCustomer(@WebParam(name = "name") String name,
            @WebParam(name = "email") String email,
            @WebParam(name = "phone") String phone) {
        try {
            Connection connection = PGConnection.getConnection();
            CustomerValidator custValidator = new CustomerValidator(connection);

            if (custValidator.isValidCustomerInfo(name, email, phone)) {
                CustomerRepo customerRepo = new CustomerRepo(connection);
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(email);
                customer.setPhone(phone);
                customerRepo.create(customer);
                return "Customer created successfully!";
            } else {
                throw new WebServiceException("An error occurred while processing the request");
            }
        } catch (SQLException e) {
            throw new WebServiceException("An error occurred while processing the request");
        }
    }

    @WebMethod(operationName = "findAll")
    public List<Customer> findAll() {
        try {
            Connection connection = PGConnection.getConnection();
            CustomerRepo customerRepo = new CustomerRepo(connection);
            List<Customer> customers = customerRepo.findAll();
            if (customers.isEmpty()) {
                throw new WebServiceException("No users found in the database");
            }
            return customers;
        } catch (SQLException e) {
            return null;
        }
    }

    @WebMethod(operationName = "findById")
    public Customer finById(@WebParam(name = "id") int id) {
        try {
            Connection connection = PGConnection.getConnection();
            CustomerValidator custValidator = new CustomerValidator(connection);
            if (custValidator.isValidId(id)) {
                CustomerRepo customerRepo = new CustomerRepo(connection);
                return customerRepo.findById(id);
            } else {
                throw new WebServiceException("An error occurred while processing the request");
            }
        } catch (SQLException e) {
            throw new WebServiceException("An error occurred while processing the request");
        }
    }

    @WebMethod(operationName = "updateOne")
    public Customer updateCustomer(@WebParam(name = "id") int id, @WebParam(name = "name") String name,
            @WebParam(name = "email") String email,
            @WebParam(name = "phone") String phone) {
        try {
            Connection connection = PGConnection.getConnection();
            CustomerValidator custValidator = new CustomerValidator(connection);
            if (custValidator.isValidId(id) && custValidator.isValidCustomerInfo(name, email, phone)) {
                CustomerRepo customerRepo = new CustomerRepo(connection);
                Customer customer = new Customer();
                customer.setId(id);
                customer.setName(name);
                customer.setEmail(email);
                customer.setPhone(phone);
                customerRepo.updateById(customer);
                return customerRepo.findById(id);
            } else {
                throw new WebServiceException("An error occurred while processing the request");
            }
        } catch (SQLException e) {
            throw new WebServiceException("An error occurred while processing the request");
        }
    }

    @WebMethod(operationName = "deleteById")
    public String deleteById(@WebParam(name = "id") int id) {
        try {
            Connection connection = PGConnection.getConnection();
            CustomerValidator custValidator = new CustomerValidator(connection);
            if (custValidator.isValidId(id)) {
                CustomerRepo customerRepo = new CustomerRepo(connection);
                customerRepo.deleteById(id);
                return "Customer deleted successfully!";
            } else {
                throw new WebServiceException("An error occurred while processing the request");
            }

        } catch (SQLException e) {
            throw new WebServiceException("An error occurred while processing the request");
        }
    }
}
