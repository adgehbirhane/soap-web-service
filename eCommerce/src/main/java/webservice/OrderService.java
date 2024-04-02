/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package webservice;

import jakarta.jws.*;
import java.sql.*;
import models.*;
import DBConnection.PGConnection;
import jakarta.xml.ws.WebServiceException;
import java.time.Instant;
import java.util.List;
import repository.*;
import validator.OrderValidator;

@WebService(serviceName = "order")
public class OrderService {

    @WebMethod(operationName = "createOne")
    public String createOrder(
            @WebParam(name = "customer_id") int customer_id,
            @WebParam(name = "product_id") int product_id,
            @WebParam(name = "quantity") int quantity) {
        try {
             Connection connection = PGConnection.getConnection();

            OrderValidator orderValidator = new OrderValidator(connection);
            if (orderValidator.isValidOrderInfo(customer_id, product_id, quantity)) {
                OrderRepo orderRepo = new OrderRepo(connection);
                Order order = new Order();
                order.setCustomer_id(customer_id);
                order.setProduct_id(product_id);
                order.setQuantity(quantity);
               order.setOrder_date(Timestamp.from(Instant.now()));
                order.setStatus("ACTIVE");
                orderRepo.create(order);
                return "Order created successfully!";
            } else {
                throw new WebServiceException("An error occurred while processing the request. m");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new WebServiceException("An error occurred while processing the request, ...", e);
        }
    }

    @WebMethod(operationName = "findAll")
    public List<Order> findAll() {
        try {
            Connection connection = PGConnection.getConnection();
            OrderRepo orderRepo = new OrderRepo(connection);
            return orderRepo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new WebServiceException("An error occurred while processing the request", e);
        }
    }

    @WebMethod(operationName = "findById")
    public Order findById(@WebParam(name = "id") int id) {
        try {
            Connection connection = PGConnection.getConnection();
            OrderValidator orderValidator = new OrderValidator(connection);
            if (orderValidator.isValidId(id)) {
                OrderRepo orderRepo = new OrderRepo(connection);
                return orderRepo.findById(id);
            } else {
                throw new WebServiceException("An error occurred while processing the request");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new WebServiceException("An error occurred while processing the request", e);
        }
    }

    @WebMethod(operationName = "updateStatus")
    public Order updateStatus(
            @WebParam(name = "id") int id,
            @WebParam(name = "status") String status) {
        try {
            Connection connection = PGConnection.getConnection();

            OrderValidator orderValidator = new OrderValidator(connection);
            if (orderValidator.isValidId(id) && orderValidator.isValidStatus(status)) {
                OrderRepo orderRepo = new OrderRepo(connection);
                Order order = new Order();
                order.setId(id);
                order.setStatus(status);
                orderRepo.updateStatus(order);
                return orderRepo.findById(id);
            } else {
                throw new WebServiceException("An error occurred while processing the request");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new WebServiceException("An error occurred while processing the request", e);
        }
    }

    @WebMethod(operationName = "deleteById")
    public String deleteById(@WebParam(name = "id") int id) {
        try {
            Connection connection = PGConnection.getConnection();
            OrderValidator orderValidator = new OrderValidator(connection);
            if (orderValidator.isValidId(id)) {
                OrderRepo orderRepo = new OrderRepo(connection);
                orderRepo.deleteById(id);
                return "Order deleted successfully!";
            } else {
                throw new WebServiceException("An error occurred while processing the request");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new WebServiceException("An error occurred while processing the request", e);
        }
    }
}
