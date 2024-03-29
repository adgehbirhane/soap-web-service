/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package webservice;

import jakarta.jws.*;
import java.sql.*;
import models.*;
import DBConnection.PGConnection;
import java.util.List;
import repository.*;
 
@WebService(serviceName = "order")
public class OrderService {

    @WebMethod(operationName = "createOne")
    public String createOrder(@WebParam(name = "customer_id") int customer_id,
            @WebParam(name = "product_id") int product_id,
            @WebParam(name = "quantity") int quantity,
            @WebParam(name = "order_date") Timestamp order_date,
            @WebParam(name = "status") String status) {
        try {
            Connection connection = PGConnection.getConnection();
            OrderRepo orderRepo = new OrderRepo(connection);
            Order order = new Order();
            order.setCustomer_id(customer_id);
            order.setProduct_id(product_id);
            order.setQuantity(quantity);
            order.setOrder_date(order_date);
            order.setStatus(status);
            orderRepo.create(order);
            return "Order created successfully!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error creating order: " + e.getMessage();
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
            return null;
        }
    }

    @WebMethod(operationName = "findById")
    public Order findById(@WebParam(name = "id") int id) {
        try {
            Connection connection = PGConnection.getConnection();
            OrderRepo orderRepo = new OrderRepo(connection);
            return orderRepo.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod(operationName = "updateOne")
    public Order updateOrder(@WebParam(name = "id") int id,
            @WebParam(name = "customer_id") int customer_id,
            @WebParam(name = "product_id") int product_id,
            @WebParam(name = "quantity") int quantity,
            @WebParam(name = "order_date") Timestamp order_date,
            @WebParam(name = "status") String status) {
        try {
            Connection connection = PGConnection.getConnection();
            OrderRepo orderRepo = new OrderRepo(connection);
            Order order = new Order();
            order.setId(id);
            order.setCustomer_id(customer_id);
            order.setProduct_id(product_id);
            order.setQuantity(quantity);
            order.setOrder_date(order_date);
            order.setStatus(status);
            orderRepo.updateById(order);
            return orderRepo.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod(operationName = "deleteById")
    public String deleteById(@WebParam(name = "id") int id) {
        try {
            Connection connection = PGConnection.getConnection();
            OrderRepo orderRepo = new OrderRepo(connection);
            orderRepo.deleteById(id);
            return "Order deleted successfully!";
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
