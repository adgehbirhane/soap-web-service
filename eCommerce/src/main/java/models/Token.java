/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author SW-GC
 */
public class Token {

    private String email;  
    private String token;
 
    public String getEmail() {
        return this.email;
    }
  
    
    public String getToken(){
        return this.token;
    }
  
    public void setEmail(String email) {
        this.email = email;
    }
  
    
    public void setToken(String token){
        this.token = token;
    }
}
