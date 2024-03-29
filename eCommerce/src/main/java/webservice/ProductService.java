/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package webservice;

import jakarta.jws.*;
import java.sql.*;
import models.*;
import DBConnection.MyDBConnection;
import java.util.List;
import repository.*;

/**
 *
 * @author Student
 */ 

@WebService(serviceName = "ProductService")
public class ProductService {

    @WebMethod(operationName = "createOne")
    public String createProduct(@WebParam(name = "name") String name,
            @WebParam(name = "description") String description,
            @WebParam(name = "category_id") int category_id,
            @WebParam(name = "price") double price) {
        try {
            Connection connection = MyDBConnection.getConnection();
            ProductRepo productRepo = new ProductRepo(connection);
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setCategory_id(category_id);
            product.setPrice(price);
            productRepo.create(product);
            return "Product created successfully!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error creating product: " + e.getMessage();
        }
    }

    @WebMethod(operationName = "findAll")
    public List<Product> findAll() {
        try {
            Connection connection = MyDBConnection.getConnection();
            ProductRepo productRepo = new ProductRepo(connection);
            return productRepo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod(operationName = "findById")
    public Product findById(@WebParam(name = "id") int id) {
        try {
            Connection connection = MyDBConnection.getConnection();
            ProductRepo productRepo = new ProductRepo(connection);
            return productRepo.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod(operationName = "updateOne")
    public Product updateProduct(@WebParam(name = "id") int id,
            @WebParam(name = "name") String name,
            @WebParam(name = "description") String description,
            @WebParam(name = "category_id") int category_id,
            @WebParam(name = "price") double price) {
        try {
            Connection connection = MyDBConnection.getConnection();
            ProductRepo productRepo = new ProductRepo(connection);
            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setDescription(description);
            product.setCategory_id(category_id);
            product.setPrice(price);
            productRepo.updateById(product);
            return productRepo.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod(operationName = "deleteById")
    public String deleteById(@WebParam(name = "id") int id) {
        try {
            Connection connection = MyDBConnection.getConnection();
            ProductRepo productRepo = new ProductRepo(connection);
            productRepo.deleteById(id);
            return "Product deleted successfully!";
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
