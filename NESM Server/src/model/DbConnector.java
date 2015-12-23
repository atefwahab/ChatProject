package model;

import java.sql.*;
import com.mysql.jdbc.Driver;
import controller.ServerController;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class DbConnector
{
	
    
    /*
        Database Connection variables
    */
    Connection con;
    String driver="com.mysql.jdbc.Driver";
    String dbUrl="jdbc:mysql://localhost:3306/chatproject";
    String dbUserName="root";
    String pass="";
    Statement state;
    String query;
    ResultSet resultSet;
    
    
    
    
    /**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private String email;
	
	private String password;
	
	
	private String userName;
	
	
	
	private int userId;
	
	
	private Vector<Integer> friendsId;
	private Vector<Integer> friendRequests;
        
        User user;
        
        
        //refernce
        ServerController serverController;
	

	/**
         *    Constructor 
	 */
	
	public DbConnector(ServerController c) {
		
            serverController=c;
            connect();
                
	}
	
	/**
	 * This method take two parameters email and password&nbsp;
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public boolean signIn(String email, String password) {
		// TODO implement me
		return false;	
	}
	
	/**
	 * take 3 parameters email,password,and username
	 */
	
	public boolean signUp(String email, String password, String username) {
            
            
		
            query="INSERT INTO `users`(`user_name`, `E-mail`, `Password`) VALUES ('"+username+"','"+email+"','"+password+"')";
        try {
            
            
            
            state.executeUpdate( query);
            System.out.println(query);
            return true;
        
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
           
	}
	
	/**
	 * this method takes an userid then change username for this user
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public boolean setUsername(int userid) {
		// TODO implement me
		return false;	
	}
	
	/**
	 * change password and takes userid
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public void setPassword(int userID) {
		// TODO implement me	
	}
	
	/**
	 * this method is used to establish connection with database
	 */
	
	public void connect() {
		// TODO implement me
                 //getConnection
        try{
            
            DriverManager.registerDriver(new Driver());
            con=DriverManager.getConnection(dbUrl,dbUserName,pass);
            
            //create Statement
            state=con.createStatement();
            
            
        }catch(SQLException ex){ 
            
            ex.printStackTrace();
        }
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public String getPassword() {
		// TODO implement me
		return "";	
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public String getUsername() {
		// TODO implement me
		return "";	
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public int getUserId() {
		// TODO implement me
		return 0;	
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public String getEmail() {
		// TODO implement me
		return "";	
	}
	
	/**
	 * return vector of friends list
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public Vector<Integer> getFriends() {
		// TODO implement me
		return null;	
	}
	
	/**
	 * this function take 2 userIDs and insert them into friends table
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public boolean addFriends(int friendID) {
		// TODO implement me
		return false;	
	}
	
	/**
	 * this method add senderID and recieverID into friendsRequest database
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public boolean addFriendRequest(int receiverID) {
		// TODO implement me
		return false;	
	}
	
	/**
	 * return a list of people who sent this user a friend requests
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public Vector<Integer> getFriendsRequest() {
		// TODO implement me
		return null;	
	}
	
	/**
	*This method used to remove a friend request from a friend request table
	*
	**/
	
	public boolean removeFriendRequest(int friendID){
		
		//To implement me
		return false;
	}
        
        
    // Check email existance
    public boolean checkEmail(String email){
        
        try {
           
            query="SELECT `E-mail` FROM `users` WHERE `E-mail`= "+email;
            resultSet= state.executeQuery(query); 
            if(resultSet.next()){
                return true;
                
            }
            else{
                 return false;
            }
          
        } catch (SQLException ex) {
            return false;  
        }
    }
    
	
}

