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
import jakarta.xml.ws.WebServiceException;
import validator.CategoryValidator;

@WebService(serviceName = "category")
public class CategoryService {

    @WebMethod(operationName = "createOne")
    public String createCategory(@WebParam(name = "name") String name,
            @WebParam(name = "description") String description) {
        try {
            Connection connection = PGConnection.getConnection();
            CategoryValidator catValidator = new CategoryValidator(connection);

            if (catValidator.isValidCategoryInfo(name, description)) {
                CategoryRepo categoryRepo = new CategoryRepo(connection);
                Category category = new Category();
                category.setName(name);
                category.setDescription(description);
                categoryRepo.create(category);
                return "Category created successfully!";
            } else {
                throw new WebServiceException("An error occurred while processing the request");
            }
        } catch (SQLException e) {
            throw new WebServiceException("An error occurred while processing the request", e);
        }
    }

    @WebMethod(operationName = "findAll")
    public List<Category> findAll() {
        try {
            Connection connection = PGConnection.getConnection();
            CategoryRepo categoryRepo = new CategoryRepo(connection);
            return categoryRepo.findAll();
        } catch (SQLException e) {
            throw new WebServiceException("An error occurred while processing the request", e);
        }
    }

    @WebMethod(operationName = "findById")
    public Category findById(@WebParam(name = "id") int id) {
        try {
            Connection connection = PGConnection.getConnection();
            CategoryValidator catValidator = new CategoryValidator(connection);
            if (catValidator.isValidId(id)) {
                CategoryRepo categoryRepo = new CategoryRepo(connection);
                return categoryRepo.findById(id);
            } else {
                throw new WebServiceException("An error occurred while processing the request");
            }
        } catch (SQLException e) {
            throw new WebServiceException("An error occurred while processing the request", e);
        }
    }

    @WebMethod(operationName = "updateOne")
    public Category updateCategory(
            @WebParam(name = "id") int id, 
            @WebParam(name = "name") String name,
            @WebParam(name = "description") String description) {
        try {
            Connection connection = PGConnection.getConnection();
            CategoryValidator catValidator = new CategoryValidator(connection);
            if (catValidator.isValidId(id) && catValidator.isValidCategoryInfo(name, description)) {
                CategoryRepo categoryRepo = new CategoryRepo(connection);
                Category category = new Category();
                category.setId(id);
                category.setName(name);
                category.setDescription(description);
                categoryRepo.updateById(category);
                return categoryRepo.findById(id);
            } else {
                throw new WebServiceException("An error occurred while processing the request");
            }
        } catch (SQLException e) {
            throw new WebServiceException("An error occurred while processing the request", e);
        }
    }

    @WebMethod(operationName = "deleteById")
    public String deleteById(@WebParam(name = "id") int id) {
        try {
            Connection connection = PGConnection.getConnection();
            CategoryValidator catValidator = new CategoryValidator(connection);
            if (catValidator.isValidId(id)) {
                CategoryRepo categoryRepo = new CategoryRepo(connection);
                categoryRepo.deleteById(id);
                return "Category deleted successfully!";
            } else {
                throw new WebServiceException("The provided id is not found");
            }

        } catch (SQLException e) {
            throw new WebServiceException("An error occurred while processing the request", e);
        }
    }
}
