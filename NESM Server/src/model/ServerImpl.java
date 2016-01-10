
package model;

import controller.ServerController;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;


public class ServerImpl extends UnicastRemoteObject implements ServerInterface{
    
    ServerController serverController;
    
    
    public ServerImpl(ServerController c)throws RemoteException{
    
    serverController=c;
        
    }

    @Override
    public User signIn(String email, String Password) {
        
       
        return serverController.signIn(email, Password);
    }

    @Override
    public boolean signUp(String username, String email, String pasword) throws RemoteException {
       
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

    @Override
    public User getUser() throws RemoteException {
        return serverController.getUser();
    }

    @Override
    public boolean addFriendRequest(int sendId,String receiverEmail) throws RemoteException {
        
        return serverController.addFriendRequest(sendId,receiverEmail);
    }

    @Override
    public boolean addFriends(int SenderId, int receiverID) throws RemoteException {
      
        return serverController.addFriends(SenderId, receiverID);
    }

    @Override
    public void sendMessage(int senderId, int friendId, String message,Font font,Color color) throws RemoteException {
       serverController.sendMessage(senderId, friendId, message,font,color);
    }
    
     @Override
     
    /*
    *send file
    */
    public void sendFile(int senderId, int friendId, byte[]  file,String fileName) throws RemoteException {
       serverController.sendFile(senderId, friendId, file,fileName);
    }
    
    /*
    *send image
    */
    @Override
    public void sendImage(int senderId, int friendId, byte[]  image,String imageName) throws RemoteException {
       serverController.sendImage(senderId, friendId, image,imageName);
    }
    
    

    @Override
    public void register(int id,ClientInterface clientInterface) throws RemoteException {
        serverController.register(id, clientInterface);
    }

    @Override
    public void unregister(int id) throws RemoteException {
       
        serverController.unregister(id);
    }

    /**
     * called by client to Update State .
     * @param state
     * @throws RemoteException 
     */
    @Override
    public void updateState(int state,User friendUser) throws RemoteException {
       serverController.updateState(state,friendUser);
    }

    @Override
    public void setMyState(int userId,int state) throws RemoteException {
       
        serverController.setMyState(userId, state);
    }

    @Override
    public void sendGroupMessage(User sender, Vector<User> friends, String message,String name) throws RemoteException {
        
        serverController.sendGroupMessage(sender, friends, message,name);
        
    }

    @Override
    public void updateFriendRequest(int userID) throws RemoteException {
    serverController.updateFriendRequest(userID);
    }

    @Override
    public boolean removeFriendRequest(int senderId, int receiverID) throws RemoteException {
        return  serverController.removeFriendRequest(senderId, receiverID);
    }
   
    
}
