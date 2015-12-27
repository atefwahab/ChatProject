
package model;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ServerInterface extends Remote{
    
    User signIn(String email,String Password) throws RemoteException;
    boolean signUp(String username,String email,String pasword)throws RemoteException;
    boolean checkEmail(String email)throws RemoteException;
    //For Searching for an email
    String searchEmail(String email)throws RemoteException;
    User getUser()throws RemoteException;
    
    public boolean addFriendRequest(int sendId,String receiverEmail)throws RemoteException;
    
    public  boolean addFriends(int SenderId,int receiverID)throws RemoteException;
    
}
