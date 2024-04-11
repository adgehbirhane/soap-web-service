package models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author SW-GC
 */
public class AuthModel { 
    private String email; 
    private String password;
    private String token;
 
    public String getEmail() {
        return this.email;
    }
 
    public String getPassword() {
        return this.password;
    }
    
    public String getToken(){
        return this.token;
    }
  
    public void setEmail(String email) {
        this.email = email;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setToken(String token){
        this.token = token;
    }
}
