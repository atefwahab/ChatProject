
package controller;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import model.ClientInterface;
import model.DbConnector;
import model.ServerImpl;
import model.User;
import project.ChatType;
import project.MessageType;
import project.ObjectFactory;
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
   
   //Xml variables 
   //1-Create JAXB OBJECT
    JAXBContext context;
    
   
    
      //2-Marshaller
    Marshaller marsh;
    
     //3-Unmarshaller
    Unmarshaller unmarsh;
    
    
    Registry reg;

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
            reg =LocateRegistry.createRegistry(2000);
            
            

        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        
        
        try {
            //1)get JAXBContext
            context=JAXBContext.newInstance("project");
            
            //2)create Marshal to write
            marsh=context.createMarshaller();
            
            //3) to read it well
             marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
            
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
        
    }


    //controller SignIn
    public User signIn(String email, String Password) {
        
        
        User obj=dbConnector.signIn(email, Password);
       
        if(obj!=null){
           
            updateState(obj.getState(),obj);
            
           getFriendsRequest(dbConnector.getUserId(email));
        }
        
        
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
    
        boolean flag= dbConnector.addFriendRequest(sendId, receiverEmail);
        if(flag){
            
            updateFriendRequest(dbConnector.getUserId(receiverEmail));
        }
            
        return flag;
    }
    
    public void updateFriendRequest(int userID){
    
        
      if(onlineUsers.containsKey(userID)){
          
          try {
              
              onlineUsers.get(userID).updateFriendRequest(dbConnector.getFriendsRequest(userID));
          } catch (RemoteException ex) {
              ex.printStackTrace();
          }
      
      }
        
    }
    
    
    /**
     * Add 2 Friends into db Friends table..and Remove these friends from friend request.
     * @param SenderId
     * @param receiverID
     * @return 
     */
    public  boolean addFriends(int SenderId,int receiverID) {
    
        boolean flag= dbConnector.addFriends(SenderId, receiverID);
        //obs
        File file =new File(dbConnector.getFileID(SenderId, receiverID)+".xml");
        writeXmlFirstTime(file);
        updateFriends(SenderId,receiverID);
        
        return flag;
    }
    
    public boolean enableServer(){
        boolean flag;
        try {
            reg.rebind("Server Object",serverImpl);
            flag=true;
        } catch (RemoteException ex) {
            ex.printStackTrace();
            flag=false;
        }
        return flag;
    }
    
    
     public boolean disableServer(){
        boolean flag;
        try {
            reg.unbind("Server Object");
            
            flag=true;
        } catch (Exception ex) {
            ex.printStackTrace();
            flag=false;
        }
        return flag;
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
     public void sendMessage(int senderId,int friendId,String message,Font font,Color color){
     
         ClientInterface receiverClient=onlineUsers.get(friendId);
         // geting the name of file
         String fileName=dbConnector.getFileID(senderId, friendId);
         //obs
         File file = new File(fileName+".xml");
         //call xml method 
         writeXml(file,message ,dbConnector.getUsername(senderId), dbConnector.getUsername(friendId));
         
        try {
            receiverClient.receive(message, senderId,font,color);
        } catch (RemoteException ex) {
           
        }
         
     }
     
       /**
     * This method used by the client to send file for a another client.
     * @param senderId
     * @param friendId
     * @param file
     * @param fileName 
     */
      public void sendFile(int senderId,int friendId,byte[] file,String fileName){
     
         ClientInterface receiverClient=onlineUsers.get(friendId);
        try {
            receiverClient.receiveFile(file, senderId,fileName);
        } catch (RemoteException ex) {
           ex.printStackTrace();
        }
         
     }
      
      /**
     * This method used by the client to send file for a another client.
     * @param senderId
     * @param friendId
     * @param image
     * @param imageName
     */
      public void sendImage(int senderId,int friendId,byte[] image,String imageName){
     
         ClientInterface receiverClient=onlineUsers.get(friendId);
        try {
            receiverClient.receiveImage(image, senderId,imageName);
        } catch (RemoteException ex) {
           ex.printStackTrace();
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
     
     
     
   public void sendAnnoc(byte[] image)
   {
     Iterator<ClientInterface> iterator=onlineUsers.values().iterator();
         
         while(iterator.hasNext()){
         
             try {
                 iterator.next().receiveAnnoc(image);
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
                   
                   onlineUsers.get(participants.get(i).getId()).receiveGroupChat(sender,message,name,participants);
                   
               } catch (RemoteException ex) {
                   ex.printStackTrace();
               }
           }    
         
    }
       
       /**
        * Xml methods .
        */
    
    public void writeXmlFirstTime(File file){
    
        ObjectFactory factory=new ObjectFactory();
        ChatType chatType = factory.createChatType();
        JAXBElement<ChatType> JAXPchat=factory.createChat(chatType);
        try {
            marsh.marshal(JAXPchat,file);
        } catch (JAXBException ex) {
           ex.printStackTrace();
        }
    }
        
    public void writeXml(File file,String msg,String userName,String friendName){
    
    
        
        try { 
            ObjectFactory factory=new ObjectFactory();
            unmarsh=context.createUnmarshaller();
            JAXBElement<ChatType> JAXPchat=(JAXBElement) unmarsh.unmarshal(file);
            ChatType chatType = (ChatType) JAXPchat.getValue();
            
             MessageType messageType=factory.createMessageType();
            messageType.setBody(msg);
            messageType.setFrom(userName);
            messageType.setTo(friendName);
            chatType.getMessage().add(messageType);
            marsh.setProperty("com.sun.xml.internal.bind.xmlHeaders", "<?xml-stylesheet type=\"text/xsl\" href=\"xslpro.xsl\"?>");
            marsh.marshal(JAXPchat, file);
            
        } catch (Exception ex) {
            
         
        }
        
        
        /*ChatType chatType = factory.createChatType();
        
        
        MessageType messageType=factory.createMessageType();
        messageType.setBody(msg);
        messageType.setFrom(userName);
        messageType.setTo(friendName);
       chatType.getMessage().add(messageType);
      // JAXBElement<ChatType> JAXPchat=factory.createChat(chatType);
       
        try {
            JAXBElement<ChatType> JAXPchat=JAXBElement<ChatType>unmarsh.unmarshal(file);
            unmarsh=context.createUnmarshaller();
            marsh.marshal(JAXPchat, file);
        } catch (JAXBException ex) {
           ex.printStackTrace();
        }*/
    
    }

    /**
     * 
     */
    public void getChat(){}
  
    /**
     * return friends requests
     * @param userID
     * @return 
     */ 
    public Vector<User> getFriendsRequest(int userID){
    
        return dbConnector.getFriendsRequest(userID);
    }
    
    /**
     * Remove friend request from db.
     * @param senderId
     * @param receiverID
     * @return 
     */
    public boolean removeFriendRequest(int senderId,int receiverID){
    
        return dbConnector.removeFriendRequest(senderId, receiverID);
    }
    
    
    public void updateFriends(int senderId,int friendId){

       ClientInterface c= onlineUsers.get(senderId);
       if(c!=null){
       
           try {
               c.updateFriends(dbConnector.getFriends(senderId));
           } catch (RemoteException ex) {
               ex.printStackTrace();
           }
       }
       c= onlineUsers.get(friendId);
       if(c!=null){
       
           try {
               c.updateFriends(dbConnector.getFriends(friendId));
           } catch (RemoteException ex) {
               ex.printStackTrace();
           }
       }


}
    
    
 
    
 
 
    public void createChatFolder(){
    
        File chatFolder=new File("Chat");
        if (!chatFolder.exists()) {
            
             chatFolder.mkdir();
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
