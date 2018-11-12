/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import DAO.AccountDAO;
import DAO.DeviceDAO;
import Model.Account;
import Model.Device;
import Model.UpdateDevice;
import View.ServerView;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Bv ptop 268
 */
public class ThreadSocket extends Thread {

    private Socket clientSocket;
    private AccountDAO accountDAO;
    private DeviceDAO deviceDAO;
    private ServerView view;

    public ThreadSocket(ServerView view, Socket s) {
        this.view = view;
        this.clientSocket = s;
        accountDAO = new AccountDAO();
        deviceDAO = new DeviceDAO();
    }

    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            Object o = ois.readObject();
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            if (o instanceof Account) {
                Account acc = (Account) o;
                if (accountDAO.checkAccount(acc) != null) {         //ktra tài khoản có trong csdl hay không, có thì trả về 1 tài khoản k có trả về null
                    Account account = accountDAO.checkAccount(acc);
                    if (account.getLevel() == 1) {
                        oos.writeObject("1");
                    } else {
                        oos.writeObject("0");
                    }
                } else {
                    oos.writeObject("login fail");
                }
            }
            //load du lieu cho bảng
            if(o instanceof String){
                String str = (String) o;
                if(str.equals("data")){
                    oos.writeObject(deviceDAO.getDevice());
                }
            }
            // check add
            if (o instanceof Device) {
                Device device = (Device) o;
                if (deviceDAO.addDevice(device)) {
                    oos.writeObject("add success");
                } else {
                    oos.writeObject("add fail");
                }
            }
            //check update
            if (o instanceof UpdateDevice) {
                UpdateDevice u = (UpdateDevice) o;
                if (deviceDAO.updateDevice(u)) {
                    oos.writeObject("update success");
                } else {
                    oos.writeObject("update fail");
                }
            }
            //check delete
            if(o instanceof Integer){
                Integer id = (Integer) o;
                if(deviceDAO.deleteDevice(id)){
                    oos.writeObject("delete success");
                }
                else oos.writeObject("delete fail");
            }
        } catch (IOException ex) {
            view.showMessage(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            view.showMessage(ex.getMessage());
        }
    }
}
