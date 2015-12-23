package controller;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ServerInterface;
import model.User;
import view.MessengerGui;

public class ClientController {

    ServerInterface serverRef;
    // object from messenger  gui 
    MessengerGui messengerGui;
    User user;

    // >--Constructor--->
    ClientController() {
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
            serverRef = (ServerInterface) reg.lookup("Server Object");
            messengerGui = new MessengerGui(this);
            messengerGui.setResizable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     sign in method
     */
    public boolean signIn(String email, String password) {
        try {
            return serverRef.signIn(email, password);
        } catch (RemoteException e) {
            return false;
        }

    }

    public boolean signup(String username, String email, String password) {
        try {
            System.out.println(serverRef.signUp(username, email, password));
            //return serverRef.signUp(username, email, password);
            return true;
        } catch (RemoteException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean checkEmail(String email) {
        try {
            return serverRef.checkEmail(email);
        } catch (RemoteException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /*
        Main method 
     */
    public static void main(String[] args) {
        new ClientController();
    }

}
