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
         boolean flag=false;
        try {
            user=serverRef.signIn(email,password);
            if (user!=null){
                flag=true;
            } 
        } catch (RemoteException ex) {
           ex.printStackTrace();
        }
        finally{
            return flag;
        }

    }

    public boolean signup(String username, String email, String password) {
        try {
           boolean flag =serverRef.signUp(username, email, password);
           //If sign up went sucessfully then i will sign In for him :D
           
           return flag;
            
        } catch (RemoteException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean checkEmail(String email) {
        try {
            return serverRef.checkEmail(email);
        } catch (RemoteException ex) {
           // ex.printStackTrace();
            return false;
        }
    }

    public String searchEmail(String email)
    {
        String res=null;
        try
        {
             res = serverRef.searchEmail(email);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        finally
        {
            return res;
        }
    }

    
   public User getUser(){
   
        return user;
   }
    
    
    /*
        Main method 
     */
    public static void main(String[] args) {
        new ClientController();
    }

}
