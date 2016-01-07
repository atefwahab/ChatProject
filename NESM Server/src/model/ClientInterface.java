/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;


public interface ClientInterface extends Remote{
    /**
     * Server use this method to send a client message
     * @param msg
     * @param friendId
     * @throws RemoteException 
     */
    void receive(String msg,Integer friendId)throws RemoteException;
    
    /**
     * this method used by server to send a client message from group chat
     * @param msg
     * @param friendIds
     * @throws RemoteException 
     */
     void receiveGroupChat(User sender,String msg,String name,Vector<User> friendIds)throws RemoteException;
    /**
     * this method used by Server to broadcast an announcement
     * @param msg
     * @throws RemoteException 
     */
    void receiveAnnoncement(String msg)throws RemoteException;
    
    void recieveState(int state,int friendId)throws RemoteException;

    void receiveFile(byte[] file,Integer friendId,String fileName)throws RemoteException;
    
   /**
     * Server use this method to send a client image
     * @param image
     * @param friendId
     * @param imageName
     * @throws RemoteException 
     */ 
    
    void receiveImage(byte[] image,Integer friendId,String imageName)throws RemoteException; 

    public void updateFriendRequest(Vector<User> friendRequests)throws RemoteException;
    
    /**
     * this method used by server to update friends after a new friend is added.
     * @param friend
     * @throws RemoteException 
     */
    public void updateFriends(Vector<User> friend)throws RemoteException;
}
