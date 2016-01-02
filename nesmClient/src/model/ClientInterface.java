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
}
