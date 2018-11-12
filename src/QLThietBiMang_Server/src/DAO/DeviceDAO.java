/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Account;
import Model.Device;
import Model.UpdateDevice;
import View.ServerView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bv ptop 268
 */
public class DeviceDAO {

    private Connection con;
    private ServerView view;

    public DeviceDAO() {
        con = new ConnectDB().getConnect();
        view = new ServerView();
    }

    public ArrayList<Device> getDevice() {
        ArrayList<Device> list = new ArrayList<>();
        String query = "select * from devices";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                list.add(new Device(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(DeviceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean addDevice(Device device) {
        String query = "Select * FROM devices WHERE ID='" + device.getId() + "'";  //ktra trùng id thì k dc thêm
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return false;  // check trung id
            } else {
                String sql = "Insert into devices values(?,?,?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, device.getId());
                ps.setString(2, device.getName());
                ps.setFloat(3, device.getPrice());
                ps.setInt(4, device.getCount());
                ps.setString(5, device.getStartYear());
                ps.setString(6, device.getDeadYear());
                ps.setString(7, device.getManufacturer());
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }

    public boolean updateDevice(UpdateDevice u) {
        boolean checkIdNotRepeat = true;
        String query = "select * from devices where ID = '" + u.getDevice().getId() + "'";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            //khi select theo id chỉ có thể chọn dc 0 or 1 record
            
            if (rs.next()) {    //nếu có bản ghi thì kiểm tra xem trùng với id của n lúc trước khi thay đổi k
                if (rs.getInt(1) != u.getIdPast()) {  
                    checkIdNotRepeat = false;   // nếu id đó không trùng vs id cũ thì k dc update
                } else {
                }
            }

            //k tra trung id roi update
            if (checkIdNotRepeat) {
                String sql = "update devices set ID=?, Name=?, Price=?, Count=?, StartYear=?, DeadYear=?, Manufacturer=? where ID='" + u.getIdPast() + "'";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, u.getDevice().getId());
                ps.setString(2, u.getDevice().getName());
                ps.setFloat(3, u.getDevice().getPrice());
                ps.setInt(4, u.getDevice().getCount());
                ps.setString(5, u.getDevice().getStartYear());
                ps.setString(6, u.getDevice().getDeadYear());
                ps.setString(7, u.getDevice().getManufacturer());
                ps.executeUpdate();
                return true;
            }
            return false;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean deleteDevice(int id) {
        String query = "delete from devices where ID='" + id + "'";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.executeUpdate();
        } catch (Exception e) {
            view.showMessage(e.getMessage());
            return false;
        }
        return true;
    }
}
