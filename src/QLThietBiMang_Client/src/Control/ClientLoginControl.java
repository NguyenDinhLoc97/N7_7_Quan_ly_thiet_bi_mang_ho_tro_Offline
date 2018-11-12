/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Account;
import Model.Device;
import View.ViewDevice;
import View.ViewDevice2;
import View.ViewEmployee;
import View.ViewLogin;
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
public class ClientLoginControl {

    private ViewLogin viewLogin;
    ViewDevice2 vDevice2;
    ViewDevice vDevice;
    private String serverHost = "localhost";
    private int serverPort = 2018;

    public ClientLoginControl(ViewDevice vDevice, ViewDevice2 vDevice2) {
        this.vDevice = vDevice;
        this.vDevice2 = vDevice2;
    }

    public void loginAction(Account acc) {
        viewLogin = new ViewLogin();
        try {
            Socket mySocket = new Socket(serverHost, serverPort);
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(acc);
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            mySocket.close();
            if (o instanceof String) {
                String str = (String) o;
                if (str.equals("login fail")) {
                    viewLogin.showMessage("Thông tin đăng nhập không chính xác, vui lòng thử lại!");
                } else {    //nếu đăng nhập được, thì yêu cầu server trả về danh sách device để hiển thị
                    Socket socket = new Socket(serverHost, serverPort);
                    ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                    os.writeObject("data");
                    ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                    Object ob = is.readObject();
                    ArrayList<Device> list = (ArrayList<Device>) ob;
                    if (str.equals("1")) {
                        vDevice.loadData(list);
                        vDevice.setVisible(true);
                    } else {
                        vDevice2.loadData(list);
                        vDevice2.setVisible(true);
                    }
                    socket.close();
                }

            }
        } catch (IOException ex) {
            viewLogin.showMessage(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            viewLogin.showMessage(ex.getMessage());
        }
    }

}
