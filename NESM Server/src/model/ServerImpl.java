
package model;

import controller.ServerController;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class ServerImpl extends UnicastRemoteObject implements ServerInterface{
    
    ServerController serverController;
    
    public ServerImpl(ServerController c)throws RemoteException{
    
    serverController=c;
        
    }

    @Override
    public boolean signIn(String email, String Password) {
        
        System.out.println("sign in has pressed");
        return serverController.signIn(email, Password);
    }

    @Override
    public boolean signUp(String username, String email, String pasword) throws RemoteException {
        System.out.println("Sign up has pressed");
        return serverController.signUp(username, email, pasword);
    }

    @Override
    // Check email existance
    public boolean checkEmail(String email) throws RemoteException {
       
    return serverController.checkEmail(email);
    }

    @Override
    //Search Email
    public String searchEmail(String email) throws RemoteException {
        
       return serverController.searchEmail(email);
        
    }
    
}
