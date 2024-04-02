/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validator;

import jakarta.xml.ws.WebServiceException;

/**
 *
 * @author Student
 */
public class NotFoundException extends WebServiceException {

    public NotFoundException() {
        super("id not found");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
