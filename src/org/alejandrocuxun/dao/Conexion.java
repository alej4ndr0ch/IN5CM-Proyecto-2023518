/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.alejandrocuxun.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author aleja
 */
public class Conexion {
    private static Conexion instance;
    
<<<<<<< HEAD
    private String url = "jdbc:mysql://localhost:3306/superkinaldb?serverTimezone=GMT-6&&useSSL=false";
=======
    private String jdbcurl = "jdbc:mysql://localhost:3306/superkinalDB?serverTimezone=GMT-6&useSSL=false";
>>>>>>> e2186e805825bd595c799466183545218cd6968b
    private String user = "root";
    private String password = "admin";
    
    private Conexion(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static Conexion getInstance(){
        if(instance == null){
            instance = new Conexion();
        }
        return instance;
    }
    
    public Connection obtenerConexion() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }
}
