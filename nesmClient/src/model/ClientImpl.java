/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.ClientController;
import java.awt.Color;
import java.awt.Font;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 *
 * @author atef
 */
public class ClientImpl extends UnicastRemoteObject implements ClientInterface{
    
    ClientController clientController ;

    //Constructor
    public ClientImpl(ClientController c)throws RemoteException{
        clientController=c;
        
    }
    @Override
    public void receive(String msg, Integer friendId,Font font,Color color) throws RemoteException {
        clientController.receive(msg,friendId,font,color);
    }

    @Override
    public void receiveAnnoncement(String msg) throws RemoteException {
        clientController.receiveAnnouncemt(msg);
    }

    /**
     * This method used to update the status of friends.
     * @param friendId
     * @throws RemoteException 
     */
    @Override
    public void recieveState(int state,int friendId) throws RemoteException {
        
        clientController.recieveState(state, friendId);
     
                
    }

    @Override
    public void receiveGroupChat(User sender,String msg,String name ,Vector<User> participants) throws RemoteException {
            System.out.println("client impl 1");
        clientController.receiveGroupChat(sender,msg, name, participants);
        System.out.println("client impl 2");
    }
    
    
       @Override
    public void receiveFile(byte[] file, Integer friendId,String fileName) throws RemoteException {
        clientController.receiveFile(file,friendId,fileName);
    }
     @Override
    public void receiveImage(byte[] image, Integer friendId,String imageName) throws RemoteException {
        clientController.receiveImage(image,friendId,imageName);
    }

    @Override
    public void updateFriendRequest(Vector<User> friendRequests) throws RemoteException {
        clientController.updateFriendRequest(friendRequests);
    }

    /**
     * this method used by the server to update the client friends.
     * @param friend
     * @throws RemoteException 
     */
    @Override
    public void updateFriends(Vector<User> friend) throws RemoteException {
       
        clientController.updateFriends(friend);
    }
    @Override
    public void receiveAnnoc(byte[] image) throws RemoteException {
        clientController.receiveAnnoc(image);
    } 
    
    
}
