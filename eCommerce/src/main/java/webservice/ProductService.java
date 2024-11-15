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
import java.util.List;
import repository.*;
import validator.ProductValidator;

@WebService(serviceName = "product")
public class ProductService {

    @WebMethod(operationName = "createOne")
    public String createProduct(@WebParam(name = "name") String name,
            @WebParam(name = "description") String description,
            @WebParam(name = "category_id") int category_id,
            @WebParam(name = "price") double price) {
        try {
            Connection connection = PGConnection.getConnection();
            ProductValidator proValidator = new ProductValidator(connection);

            if (proValidator.isValidProductInfo(category_id, price, name, description)) {
                ProductRepo productRepo = new ProductRepo(connection);
                Product product = new Product();
                product.setName(name);
                product.setDescription(description);
                product.setCategory_id(category_id);
                product.setPrice(price);
                return  productRepo.create(product); 
            } else {
                throw new WebServiceException("An error occurred while processing the request.");
            }
        } catch (SQLException e) {
            throw new WebServiceException( e.getMessage());
        }
    }

    @WebMethod(operationName = "findAll")
    public List<Product> findAll() {
        try {
            Connection connection = PGConnection.getConnection();
            ProductRepo productRepo = new ProductRepo(connection);
            return productRepo.findAll();
        } catch (SQLException e) {
            return null;
        }
    }

    @WebMethod(operationName = "findById")
    public Product findById(@WebParam(name = "id") int id) {
        try {
            Connection connection = PGConnection.getConnection();

            ProductValidator proValidator = new ProductValidator(connection);
            if (proValidator.isValidId(id)) {
                ProductRepo productRepo = new ProductRepo(connection);
                return productRepo.findById(id);
            } else {
                throw new WebServiceException("An error occurred while processing the request");
            }
        } catch (SQLException e) {
            throw new WebServiceException("An error occurred while processing the request");
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
            ProductValidator proValidator = new ProductValidator(connection);
            if (proValidator.isValidId(id) && proValidator.isValidProductInfo(category_id, price, name, description)) {
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
            ProductValidator proValidator = new ProductValidator(connection);
            if (proValidator.isValidId(id)) {
                ProductRepo productRepo = new ProductRepo(connection);
                productRepo.deleteById(id);
                return "Product deleted successfully!";
            } else {
                throw new WebServiceException("An error occurred while processing the request");
            }
        } catch (SQLException e) {
            throw new WebServiceException("An error occurred while processing the request");
        }
    }
}
