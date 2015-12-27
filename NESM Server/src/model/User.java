/*
 * This class used for getting user data
 */
package model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 *
 * @author atef
 */
public class User implements Serializable{
    
    private String username;
   private String state="Available"; 
    private String email;
    private Integer id;
    
    private Vector<User> friends;
    private Vector<Integer> friendRequests;

    public User(){}
    
    
    /**
     * @return the username
     */
    public String getUsername() {
        
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

   


    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

   
    /**
     * @return the friendRequests
     */
    public Vector<Integer> getFriendRequests() {
        return friendRequests;
    }

    /**
     * @param friendRequests the friendRequests to set
     */
    public void setFriendRequests(Vector<Integer> friendRequests) {
        this.friendRequests = friendRequests;
    }

    /**
     * @return the friends
     */
    public Vector<User> getFriends() {
        return friends;
    }

    /**
     * @param friends the friends to set
     */
    public void setFriends(Vector<User> friends) {
        this.friends = friends;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }
    
}


