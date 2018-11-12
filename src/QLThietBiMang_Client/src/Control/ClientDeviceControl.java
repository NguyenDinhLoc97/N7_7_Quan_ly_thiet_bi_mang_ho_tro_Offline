/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Account;
import Model.Device;
import Model.UpdateDevice;
import View.ViewDevice;
import View.ViewEmployee;
import View.ViewLogin;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bv ptop 268
 */
public class ClientDeviceControl extends Thread {

    private ViewDevice viewDevice;
    private String serverHost = "localhost";
    private int serverPort = 2018;

    public ClientDeviceControl(ViewDevice viewDevice) {
        this.viewDevice = viewDevice;
    }

    public boolean addAction(Device device) {
        try {
            Socket mySocket = new Socket(serverHost, serverPort);
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(device);
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            if (o instanceof String) {
                String str = (String) o;
                if (str.equals("add success")) {
                    return true;
                }
            }
            mySocket.close();
        } catch (IOException ex) {
            viewDevice.showMessage(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            viewDevice.showMessage(ex.getMessage());
        }
        return false;
    }

    public boolean updateAction(UpdateDevice u) {
        try {
            Socket mySocket = new Socket(serverHost, serverPort);
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(u);
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            if (o instanceof String) {
                String str = (String) o;
                if (str.equals("update success")) {
                    return true;
                    //viewDevice.showMessage("Update thành công!");
                }
            }
            mySocket.close();
        } catch (IOException ex) {
            viewDevice.showMessage(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            viewDevice.showMessage(ex.getMessage());
        }
        return false;
    }

    public boolean deleteAction(int id) {
        try {
            Socket mysocket = new Socket(serverHost, serverPort);
            ObjectOutputStream oos = new ObjectOutputStream(mysocket.getOutputStream());
            oos.writeObject(id);
            ObjectInputStream ois = new ObjectInputStream(mysocket.getInputStream());
            Object o = ois.readObject();
            if (o instanceof String) {
                String result = (String) o;
                if (result.equals("delete success")) {
                    return true;
                }
            }
            mysocket.close();
        } catch (IOException ex) {
            viewDevice.showMessage(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            viewDevice.showMessage(ex.getMessage());
        }
        return false;
    }

    public void load() {
        try {
            Socket socket = new Socket(serverHost, serverPort);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject("data");
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            Object ob = is.readObject();
            ArrayList<Device> list = (ArrayList<Device>) ob;
            viewDevice.loadData(list);
            socket.close();
        } catch (Exception e) {
        }

    }
}
