
package model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;


public interface ServerInterface extends Remote{
    
    User signIn(String email,String Password) throws RemoteException;
    boolean signUp(String username,String email,String pasword)throws RemoteException;
    boolean checkEmail(String email)throws RemoteException;
    //For Searching for an email
    String searchEmail(String email)throws RemoteException;
    User getUser()throws RemoteException;
    
    public boolean addFriendRequest(int sendId,String receiverEmail)throws RemoteException;
    
    public  boolean addFriends(int SenderId,int receiverID)throws RemoteException;
    
    public void sendMessage(int senderId,int friendId,String message)throws RemoteException;
    
    public void register(int id,ClientInterface clientInterface)throws RemoteException;
    
    public void unregister(int id)throws RemoteException;
    
    public void updateState(int state,Vector<Integer> friendsId)throws RemoteException;
    public void setMyState(int userId,int state)throws RemoteException;
}
