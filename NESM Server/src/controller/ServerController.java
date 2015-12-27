
package controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import model.DbConnector;
import model.ServerImpl;
import model.User;

/**
 *
 * @author atef
 */
public class ServerController {
    
    ServerImpl serverImpl;
    DbConnector dbConnector;
    
    User user;
    
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
    public User signIn(String email, String Password) {
        
        
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
    
    
    public User getUser(){
    
        User obj = new User();
        obj.setUsername("Ayad");
        return obj;
    }
    
    /**
     * Add Friend Request
     * @param  sendId
     * @param receiverID 
     * return boolean
     */
    public boolean addFriendRequest(int sendId,String receiverEmail){
    
        return dbConnector.addFriendRequest(sendId, receiverEmail);
    }
    
    
    /**
     * Add 2 Friends into db Friends table..and Remove these friends from friend request.
     * @param SenderId
     * @param receiverID
     * @return 
     */
    public  boolean addFriends(int SenderId,int receiverID) {
    
        return dbConnector.addFriends(SenderId, receiverID);
    }
    
    
    
    
    public static void main(String[] args) {
        
        new ServerController();
    }
    
}
