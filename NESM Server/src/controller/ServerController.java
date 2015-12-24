
package controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DbConnector;
import model.ServerImpl;

/**
 *
 * @author atef
 */
public class ServerController {
    
    ServerImpl serverImpl;
    DbConnector dbConnector;
    
    // >-- Constructor -->
    public ServerController() {
        
        //create object from db
        dbConnector =new DbConnector(this);
        try {
            //create object from ServerImplementation which will be sent to client
            serverImpl =new ServerImpl(this);
            Registry reg =LocateRegistry.createRegistry(5000);
            reg.rebind("Server Object",serverImpl);

        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        
    }


    //controller SignIn
    public boolean signIn(String email, String Password) {
        
        return dbConnector.signIn(email, Password);
        
    }

    //Controller Signup
    public boolean signUp(String username, String email, String pasword){
        if(dbConnector.signUp(email, pasword, username)){
            System.out.println("true signup");
            return true;
            
        }
        else{
            System.out.println("false signup");
            return false;
        }
    }

    
    // Check email existance
    public boolean checkEmail(String email){
        
        if(dbConnector.checkEmail(email)){
            return true;
        }else{
        
            return false;
        }
    }
    
    //search email
    public String searchEmail(String email){
    
           return dbConnector.searchFriend(email);
    
    }
    
    
    
    public static void main(String[] args) {
        
        new ServerController();
    }
    
}
