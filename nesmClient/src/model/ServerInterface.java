
package model;
import java.rmi.*;


public interface ServerInterface extends Remote{
    
    boolean signIn(String email,String Password) throws RemoteException;
    boolean signUp(String username,String email,String password) throws RemoteException;
    boolean checkEmail(String email)throws RemoteException;
    
    
}
