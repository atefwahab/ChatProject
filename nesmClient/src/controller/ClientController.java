package controller;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ClientImpl;
import model.ServerInterface;
import model.User;
import view.ChatGui;
import view.FriendListJFrame;
import view.MessengerGui;

public class ClientController {

    ServerInterface serverRef;
    // object from messenger  gui 
    MessengerGui messengerGui;
    User user;
    FriendListJFrame friendListJframe;
    //HashMap represent open Window 
    HashMap<Integer,ChatGui> openWindows = new HashMap<>();
    ClientImpl clientImpl;
    Vector<User>friends;

// >--Constructor--->
    ClientController() {
        try {
            this.clientImpl = new ClientImpl(this);
        } catch (RemoteException ex) {
           ex.printStackTrace();
        }
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
            System.out.println(user.getState());
            friends=user.getFriends();
            if (user!=null){
                flag=true;
                friendListJframe=new FriendListJFrame(this, user);
                
                serverRef.register(user.getId(),clientImpl);
                
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
    /**
     * this method is used to send the message to server .
     * @param senderId
     * @param friendId
     * @param message 
     */
     public void sendMessage(int friendId,String message)
     {
        try {
            serverRef.sendMessage(user.getId(), friendId, message);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
     }
    /**
     * this method is used to receive a message from server and send it to the client 
     * @param msg
     * @param friendId 
     */
    public void receive(String msg, int friendId)
    {
        ChatGui chatGui= null;
     
        for( User friendUser : user.getFriends())
        {
           
            if(friendUser.getId()== friendId)
            {
                
                chatGui = this.open(friendUser);
            }
        }
        chatGui.recieve(msg);
    }
    
    
    /**
     * 
     * @param friendId
     * @return 
     */
    public ChatGui open(User user) 
    {
        ChatGui friendChatGui ;
        if(!openWindows.containsKey(user.getId()))
        {
            friendChatGui = new ChatGui(user,this);
            openWindows.put(user.getId(),friendChatGui);
            
            return friendChatGui;
        }
        else
        {
           friendChatGui = openWindows.get(user.getId());
           return friendChatGui ;
        }
    }
    
    
    
    
    
     public void close (int friendId)
     {
           openWindows.remove(friendId);
     }

    
     public void receiveAnnouncemt(String msg){
     
         User serverUser=new User();
         serverUser.setUsername("Hi Messenger Server");
         serverUser.setId(0);
         
         ChatGui chatGui=open(serverUser);
         if(chatGui!=null)
         chatGui.recieve(msg);
         
         
         
     }
     
   public User getUser(){
   
        return user;
   }
   
   /**
    * Update status of my friends
    * @param friendId 
    */
   public void recieveState(int state,int friendId){
       System.out.println("controller Recieve State ");
       for( User friendUser : user.getFriends())
        {
            
           System.out.println("controller Recieve State For 1");
            if(friendUser.getId().equals(friendId))
            {
                System.out.println("controller Recieve State for if 2");
               friendUser.setState(state);
               System.out.println("controller Recieve State for if 3");
               friendListJframe.paintList();
               System.out.println("controller Recieve State for if 4");
            }
        }
   
   
   }
   
  
   public void updateState(int state){
       
       Vector<Integer> friendsId=new Vector<>();
       
       //Getting friends ids
       for(User item:user.getFriends()){
       
           friendsId.add(item.getId());
       }
        try {
            serverRef.updateState(state, friendsId);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
   }
   /**
    * Set me offline
    */
   public void setMeOffline(){
       
        try {
            serverRef.setMyState(user.getId(),User.Offline);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
   
       
   }
   /**
    * Changing my state into database.
    * @param state 
    */
   public void changeMyState(int state){
   
        try {
            serverRef.setMyState(user.getId(), state);
        } catch (RemoteException ex) {
           ex.printStackTrace();
        }
   }
    
    
    /*
        Main method 
     */
    public static void main(String[] args) {
        new ClientController();
    }

}
