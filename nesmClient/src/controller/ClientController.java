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
import view.ChatGroupGui;
import view.ChatGui;
import view.FriendListJFrame;
import view.MessengerGui;
import view.Notification;
import view.PlayaudioFile;
import view.SelectedChatGroupJFrame;
import view.ServerDown;

public class ClientController {

    ServerInterface serverRef;
    // object from messenger  gui 
    MessengerGui messengerGui;
    User user;
    FriendListJFrame friendListJframe;
    //HashMap represent open Window 
    HashMap<Integer,ChatGui> openWindows = new HashMap<>();
    HashMap<String,ChatGroupGui> openChatGroupWindows = new HashMap<>();
    ClientImpl clientImpl;
    Vector<User>friends;
    
    

// >--Constructor--->
    ClientController() {
       
        try {
            this.clientImpl = new ClientImpl(this);
        } catch (RemoteException ex) {
           
        }
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
            serverRef = (ServerInterface) reg.lookup("Server Object");
            messengerGui = new MessengerGui(this);
            messengerGui.setResizable(false);

        } catch (Exception e) {
            new ServerDown();
        }
    }

    /*
     sign in method
     */
    public boolean signIn(String email, String password) {
         boolean flag=false;
        try {
            user=serverRef.signIn(email,password);
            
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
     * @author atef
     * this method is used to send the message to server .
     * 
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
     * 
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
     * this method is used to check if the client window is open or not.
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
     
     /**
      * @author Atef
      * this method used to check if there is an chat group windows already opened or open a new one.
      * @param chatGroupName
      * @return chat group window
      */
     public ChatGroupGui openChatGroup(String chatGroupName,Vector<User> friendUsers){
     
        ChatGroupGui chatgroup;
        
        if(openChatGroupWindows.containsKey(chatGroupName)){
            
            chatgroup=openChatGroupWindows.get(chatGroupName);
            System.out.println("it is found");
            
        }else{
        
            chatgroup=new ChatGroupGui(chatGroupName, friendUsers, this);
            openChatGroupWindows.put(chatGroupName, chatgroup);
        }
        
        return chatgroup;
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
    * Update status of my friends.
    * @param friendId 
    */
   public void recieveState(int state,int friendId){
       
       for( User friendUser : user.getFriends())
        {
            
           
           
            if(friendUser.getId().equals(friendId))
            {
               
               
               friendUser.setState(state);
               String imageName="";
               switch(state){
                   case User.Available:
                       imageName="available";
                       break;
                    case User.Busy:
                       imageName="busy";
                       break;
                       
                     case User.Away:
                       imageName="away";
                       break;
                       
                     case User.Offline:
                       imageName="offline";
                       break;
               }
               new Notification(friendUser.getUsername(),User.getStringState(friendUser.getState()),imageName,"src\\sounds\\userState.wav");
               
               friendListJframe.paintList();
               //To Update status in chat windows if it is open
               if(openWindows.containsKey(friendId)){
               
                    openWindows.get(friendId).updateState(User.getStringState(friendUser.getState()));
               
               }
               
               
            }
        }
   
   
   }
   
  
   public void updateState(int state){
       
       
       
      
        try {
            serverRef.updateState(state,user);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
   }
   
   
     public boolean addFriendRequest(int sendId,String receiverEmail)
   {
       boolean res ;
        try 
        {
           res = serverRef.addFriendRequest(sendId, receiverEmail);
             return res ;
        } 
        catch (RemoteException ex)
        {
            ex.printStackTrace();
             return false;
        }
        
   }
   
   
   /**
    * Set me offline
    */
   public void setMeOffline(){
       
        try {
            serverRef.setMyState(user.getId(),User.Offline);
            serverRef.updateState(User.Offline, user);
            serverRef.unregister(user.getId());
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
            updateState(state);
        } catch (RemoteException ex) {
           ex.printStackTrace();
        }
   }
    
   
   public void selectChatGroup(){
   
       new SelectedChatGroupJFrame(friends,this);
   
   }
   
   /**
    * this method used to start chat for the first time.
    * @param name
    * @param participants 
    */
   public void startChatGroup(String name,Vector<User> participants){
   
       participants.add(user);
       for (int i = 0; i <participants.size(); i++) {
           System.out.println(participants.get(i).getUsername());
       }
      
       System.out.println("start chat 1");
       sendGroupMessage(user.getUsername()+" has started a group chat",name,participants);
       System.out.println("start chat 2");
   
   }
   /**
    * send a message to a group.
    * @param message
    * @param friendIds 
    */
   public void sendGroupMessage(String message,String name,Vector<User> participants){
   
        try {
            System.out.println("send group 1");
            serverRef.sendGroupMessage(user, participants, message,name);
            System.out.println("send group 2");
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
   }
   
   /**
    * Receive a group message.
    * @param msg
    * @param name
    * @param participants 
    */
   public void receiveGroupChat(User sender,String msg,String name ,Vector<User> participants){
   
       for(int i=0;i<participants.size();i++){
       
           System.out.println("ana gwa el recieve aho w ana esmy "+participants.get(i).getUsername());
       }
       ChatGroupGui chatGroupGui=openChatGroup(name, participants);
       chatGroupGui.receiverMessage(sender, msg);
        
   
   
   }
    
    /*
        Main method 
     */
    public static void main(String[] args) {
        new ClientController();
    }

}
