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

@WebService(serviceName = "category")
public class CategoryService {

    @WebMethod(operationName = "createOne")
    public String createCategory(@WebParam(name = "name") String name,
            @WebParam(name = "description") String description) {
        try {
            Connection connection = MyDBConnection.getConnection();
            CategoryRepo categoryRepo = new CategoryRepo(connection);
            Category category = new Category();
            category.setName(name);
            category.setDescription(description);
            categoryRepo.create(category);
            return "Category created successfully!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error creating category: " + e.getMessage();
        }
    }

    @WebMethod(operationName = "findAll")
    public List<Category> findAll() {
        try {
            Connection connection = MyDBConnection.getConnection();
            CategoryRepo categoryRepo = new CategoryRepo(connection);
            return categoryRepo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod(operationName = "findById")
    public Category findById(@WebParam(name = "id") int id) {
        try {
            Connection connection = MyDBConnection.getConnection();
            CategoryRepo categoryRepo = new CategoryRepo(connection);
            return categoryRepo.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod(operationName = "updateOne")
    public Category updateCategory(@WebParam(name = "id") int id, @WebParam(name = "name") String name,
            @WebParam(name = "description") String description) {
        try {
            Connection connection = MyDBConnection.getConnection();
            CategoryRepo categoryRepo = new CategoryRepo(connection);
            Category category = new Category();
            category.setId(id);
            category.setName(name);
            category.setDescription(description);
            categoryRepo.updateById(category);
            return categoryRepo.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod(operationName = "deleteById")
    public String deleteById(@WebParam(name = "id") int id) {
        try {
            Connection connection = MyDBConnection.getConnection();
            CategoryRepo categoryRepo = new CategoryRepo(connection);
            categoryRepo.deleteById(id);
            return "Category deleted successfully!";
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
