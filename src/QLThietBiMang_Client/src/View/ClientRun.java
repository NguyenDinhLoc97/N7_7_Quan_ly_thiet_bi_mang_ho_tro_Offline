/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

/**
 *
 * @author Bv ptop 268
 */
public class ClientRun {

    public static void main(String[] args) {
        ViewDevice viewDevice = new ViewDevice();
        ViewDevice2 viewDevice2 = new ViewDevice2();
        ViewLogin view = new ViewLogin(viewDevice, viewDevice2);
        view.setVisible(true);
        Thread thread = new Thread(viewDevice);
        thread.start();
    }
}
