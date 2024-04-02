/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import models.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepo {

    private Connection connection;

    // Constructor
    public OrderRepo(Connection connection) {
        this.connection = connection;
    }

    // Method to create a new order
    public void create(Order order) throws SQLException {
        String query = "INSERT INTO \"public\".\"Order\" (customer_id, product_id, quantity, order_date, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, order.getCustomer_id());
            statement.setInt(2, order.getProduct_id());
            statement.setInt(3, order.getQuantity());
            statement.setTimestamp(4, order.getOrder_date());
            statement.setString(5, order.getStatus());
            statement.executeUpdate();
        }
    }

    // Method to retrieve all orders
    public List<Order> findAll() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM \"public\".\"Order\"";
        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setCustomer_id(resultSet.getInt("customer_id"));
                order.setProduct_id(resultSet.getInt("product_id"));
                order.setQuantity(resultSet.getInt("quantity"));
                order.setOrder_date(resultSet.getTimestamp("order_date"));
                order.setStatus(resultSet.getString("status"));
                orders.add(order);
            }
        }
        return orders;
    }

    // Method to retrieve an order by ID
    public Order findById(int orderId) throws SQLException {
        String query = "SELECT * FROM \"public\".\"Order\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Order order = new Order();
                    order.setId(resultSet.getInt("id"));
                    order.setCustomer_id(resultSet.getInt("customer_id"));
                    order.setProduct_id(resultSet.getInt("product_id"));
                    order.setQuantity(resultSet.getInt("quantity"));
                    order.setOrder_date(resultSet.getTimestamp("order_date"));
                    order.setStatus(resultSet.getString("status"));
                    return order;
                }
            }
        }
        return null; // Order with the given ID not found
    }

    public void updateStatus(Order order) throws SQLException {
        String query = "UPDATE \"public\".\"Order\" SET status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, order.getStatus());
            statement.setInt(2, order.getId());
            statement.executeUpdate();
        }
    }

    public void deleteById(int orderId) throws SQLException {
        String query = "DELETE FROM \"public\".\"Order\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            statement.executeUpdate();
        }
    }
}
