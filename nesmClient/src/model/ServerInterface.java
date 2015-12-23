
package model;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ServerInterface extends Remote{
    
    boolean signIn(String email,String Password) throws RemoteException;
    boolean signUp(String username,String email,String pasword)throws RemoteException;
    boolean checkEmail(String email)throws RemoteException;
    //For Searching for an email
    String searchEmail(String email)throws RemoteException;
    
}
