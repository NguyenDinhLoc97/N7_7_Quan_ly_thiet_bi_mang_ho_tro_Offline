/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Account;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bv ptop 268
 */
public class AccountDAO {
    private Connection con ;
    public Account checkAccount(Account acc){
        con= new ConnectDB().getConnect();
        String query = "Select * FROM users WHERE userName='" + acc.getUserName() + "' AND password='" + acc.getPassword() + "'";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                return new Account(rs.getString(1), rs.getString(2), rs.getInt(3));              
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }       
        return null;
    }
}
