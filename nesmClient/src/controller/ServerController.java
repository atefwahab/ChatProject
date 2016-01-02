
package controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ClientInterface;
import model.DbConnector;
import model.ServerImpl;
import model.User;
import view.ServerGui;

/**
 *
 * @author atef
 */
public class ServerController {
    
    ServerImpl serverImpl;
    DbConnector dbConnector;
    ServerGui serverGui;
    
    User user;
    
    //Online Users
   HashMap<Integer,ClientInterface>onlineUsers=new HashMap<>();
    
   public static int existedUsers=0;
   public static int totalUsers=0;

    // >-- Constructor -->
    public ServerController() {
        
        //create object from db
        dbConnector =new DbConnector(this);
        ServerController.totalUsers=dbConnector.getTotalUsers();
        serverGui=new ServerGui(this);
        //sending status to server gui
        serverGui.setUsers(ServerController.existedUsers, ServerController.totalUsers);
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
        
        
        User obj=dbConnector.signIn(email, Password);
       
        
        updateState(obj.getState(),obj);
        
        
        
        return obj; 
        
    }

    //Controller Signup
    public boolean signUp(String username, String email, String pasword){
        if(dbConnector.signUp(email, pasword, username)){
         
            return true;
            
        }
        else{
            
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
    
    /**
     * Register new object
     */
    public void register(int id,ClientInterface client){
        
         onlineUsers.put(id, client);
        
         ServerController.existedUsers++;
        
        //sending status to server gui
        serverGui.setUsers(ServerController.existedUsers, ServerController.totalUsers);
    
           
    
    }
    /**
     * Unregister a user from server
     * @param id 
     */
    public void unregister(int id){
    
       
        onlineUsers.remove(id);
        ServerController.existedUsers--;
        
        //sending status to server gui
        serverGui.setUsers(ServerController.existedUsers, ServerController.totalUsers);
    }
    
    
    /**
     * This method used by the client to send a message for a another client.
     * @param senderId
     * @param friendId
     * @param message 
     */
     public void sendMessage(int senderId,int friendId,String message){
     
         ClientInterface receiverClient=onlineUsers.get(friendId);
        try {
            receiverClient.receive(message, senderId);
        } catch (RemoteException ex) {
           
        }
         
     }
     
     
     /**
      * Sending an Announcement to Clients.
      * @param msg 
      */
     public void sendAnnouncement(String msg){
         Iterator<ClientInterface> iterator=onlineUsers.values().iterator();
         
         while(iterator.hasNext()){
         
             try {
                 iterator.next().receiveAnnoncement(msg);
             } catch (RemoteException ex) {
                ex.printStackTrace();
             }
         
         }
         
         
         
  
     
     }
     
     
     /**
      * this method called by client to update its state
      * @param state 
      */
     public void updateState(int state,User friendUser){
         
         ClientInterface friend;
         Vector<Integer> friendsId=friendUser.getFriendsId();
         
   
         
         for(Integer friendId:friendsId){
             
               
                friend=onlineUsers.get(friendId);
                
                
             if(friend!=null){
                 
                try {
                  
                    friend.recieveState(state,friendUser.getId());
                   
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
             
             }
         
         }
             
     }
     /**
      * this method user to set users offline
      * @param userId 
      */
     public void setMyState(int userId,int state){
     
         dbConnector.setState(state, userId);
     }

     /**
      * this method used to send a message from client to group of clients.
      * @param senderId
      * @param friendIds
      * @param message 
      */
       public void sendGroupMessage(User sender, Vector<User> participants, String message,String name){
        
           for(int i=0;i<participants.size();i++){
           
               try {
                   System.out.println(participants.get(i).getUsername());
                   onlineUsers.get(participants.get(i).getId()).receiveGroupChat(sender,message,name,participants);
                   System.out.println("server control 2");
               } catch (RemoteException ex) {
                   ex.printStackTrace();
               }
           }    
         
    }
     
     
     /**
      * Main method
      * @param args 
      */
    public static void main(String[] args) {
        
        new ServerController();
    }
    
}
