/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.ClientController;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
    public void receive(String msg, Integer friendId) throws RemoteException {
        clientController.receive(msg,friendId);
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
        System.out.println("client implementation 1");
        clientController.recieveState(state, friendId);
        System.out.println("client implementation 2");
                
    }
    
    
    
}
