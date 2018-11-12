/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.ThreadSocket;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bv ptop 268
 */
public class ServerRun {

    private static int serverProt = 2018;

    public static void main(String[] args) {
        ServerView view = new ServerView();
        ServerSocket ss;
        try {
            ss = new ServerSocket(serverProt);
            view.showMessage("TCP server is running ...");
            while (true) {
                new ThreadSocket(view, ss.accept()).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerRun.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
