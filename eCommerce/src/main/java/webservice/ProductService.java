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

@WebService(serviceName = "product")
public class ProductService {

    @WebMethod(operationName = "createOne")
    public Product createProduct(@WebParam(name = "name") String name,
            @WebParam(name = "description") String description,
            @WebParam(name = "category_id") int category_id,
            @WebParam(name = "price") double price) {
        try {
            Connection connection = PGConnection.getConnection();
            ProductRepo productRepo = new ProductRepo(connection);
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setCategory_id(category_id);
            product.setPrice(price);
            int newProductId = productRepo.create(product);
            if (newProductId == 0) {
                throw new SQLException("Creating product failed");
            } else {
                return productRepo.findById(newProductId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod(operationName = "findAll")
    public List<Product> findAll() {
        try {
            Connection connection = PGConnection.getConnection();
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
            Connection connection = PGConnection.getConnection();
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
            Connection connection = PGConnection.getConnection();
            ProductRepo productRepo = new ProductRepo(connection);
            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setDescription(description);
            product.setCategory_id(category_id);
            product.setPrice(price);

            boolean success = productRepo.updateById(product);

            if (success) {
                return productRepo.findById(id);
            } else {
                throw new SQLException("Updating product failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod(operationName = "deleteById")
    public String deleteById(@WebParam(name = "id") int id) {
        try {
            Connection connection = PGConnection.getConnection();
            ProductRepo productRepo = new ProductRepo(connection);
            productRepo.deleteById(id);
            return "Product deleted successfully!";
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
