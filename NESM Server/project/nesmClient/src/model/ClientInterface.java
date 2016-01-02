/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ClientInterface extends Remote{
    /**
     * Server use this method to send a client message
     * @param msg
     * @param friendId
     * @throws RemoteException 
     */
    void receive(String msg,Integer friendId)throws RemoteException;
    
    /**
     * this method used by Server to broadcast an announcement
     * @param msg
     * @throws RemoteException 
     */
    void receiveAnnoncement(String msg)throws RemoteException;
    
    void recieveState(int state,int friendId)throws RemoteException;
    
  //  void updateFriendRequest() throws RemoteException;
    
}
