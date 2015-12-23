/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ClientInterface extends Remote{
    
    void receive(String msg,Integer friendId)throws RemoteException;
    void receiveAnnoncement(String msg)throws RemoteException;
}
