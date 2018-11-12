/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Bv ptop 268
 */
public class ConnectDB {
    private String dbClass="com.mysql.jdbc.Driver";
    private String dbUrl="jdbc:mysql://localhost:3306/devicemanager";
    private String userName="root";
    private String password="";
    public Connection getConnect(){
        Connection conn =null;
        try {
            Class.forName(dbClass);
            conn = DriverManager.getConnection(dbUrl, userName, password);
            return conn;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
