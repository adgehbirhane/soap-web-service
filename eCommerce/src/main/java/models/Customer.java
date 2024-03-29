/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
 
public class Customer { 
    
    private int id;
    private String name;
    private String email;
    private String phone;
    
    public int getId(){
        return this.id;
    }
    
    public String getName(){
        return this.name;
    }
        
    public String getEmail(){
        return this.email;
    }
        
    public String getPhone(){
        return this.phone;
    } 
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setPhone(String phone){
        this.phone = phone;
    }
}
