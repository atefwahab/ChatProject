package model;

import java.sql.*;
import com.mysql.jdbc.Driver;
import controller.ServerController;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * Constructor
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
    static Statement state;
    static String query;
    static ResultSet resultSet;
    
    
    
    
   
        //User reference 
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
	 * This method take two parameters email and password;
	 */
	
	public  User signIn(String email, String password) {
            
             query="SELECT `Password` FROM `users` WHERE `E-mail`= '"+email+"'";
             
            try {
            
                  resultSet= state.executeQuery(query);
                  if(resultSet.next()){
                  
                        if(resultSet.getString(1).equals(password))     
                        {
                         
                            System.out.println("true sign in");
                            User user=new User();
                            user.setEmail(email);
                            int id =getUserId(email);
                            user.setId(getUserId(email));
                            user.setUsername(getUsername(id));
                            user.setFriends(getFriends(id));
                            
                           
                            return user;
               
                        }
                   
                       else{
                            System.out.println("false sign in");
                             return null;
                        }
                  }//end if
                   else{
                         System.out.println("false sign in");
                         return null;
            }
        }//end try
           
        
       	
         catch (SQLException ex) {
           ex.printStackTrace();
           System.out.println("false sign in"); 
           return null;
             
        }
	
        }//end sign in
        
        
        
        
	/**
	 *  SIGN UP take 3 parameters email,password,and username
	 */
	
	public  boolean signUp(String email, String password, String username) {
            
            
		
            query="INSERT INTO `users`(`user_name`, `E-mail`, `Password`) VALUES ('"+username+"','"+email+"','"+password+"')";
        try {
            
            
            
            state.executeUpdate( query);
            
            return true;
        
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
           
	}
        
        
	
	/**
	 * Set UserName 
         * this method takes an user id then change username for this user
	 */
	
	public void setUsername(String username,int userId) {
	query = "INSERT INTO `users`(`user_name`) VALUES ('" + username + "') where user_id="+userId;
        try {

            state.executeUpdate(query);
            
            

        } catch (SQLException ex) {
            ex.printStackTrace();
            
        }
	}
	
	/**
	 * change password and takes userid
	 */
	
	public void setPassword(String password,int userId) {
        
        // TODO implement me
        query = "INSERT INTO `users`(`Password`) VALUES ('" + password + "') where user_id="+userId;
        try {

            state.executeUpdate(query);
           

        } catch (SQLException ex) {
            ex.printStackTrace();
            
        }
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
           * This method used to get Password for a user
	 */
	
	public String getPassword(int userId) {
        String pas;
        query = "SELECT Password FROM users WHERE user-id = " + userId;

        try {
            resultSet = state.executeQuery(query);
            if (resultSet.next()) {
                pas = resultSet.getString(1);

            } //end if 
            else {
                pas = null;
            }
        }//end try
        catch (SQLException ex) {
            ex.printStackTrace();
            pas = null;
        }//end catch

        return pas;
    }//end password
	
	/**
	* Get username
	 */
	
	 public String getUsername(int userId) {

        String st;
        query = "SELECT `user_name` FROM `users` WHERE `user_id`="+userId;
             

        try {
            resultSet = state.executeQuery(query);
            if (resultSet.next()) {
                st = resultSet.getString(1);

            } //end if 
            else {
                st = null;
            }
        }//end try
        catch (SQLException ex) {
            ex.printStackTrace();
            st = null;
        }//end catch

        return st;
    }//end getusername
	
         
         
         /**
            *    GET USER ID
	 */
	
	 public int getUserId(String email) {
        int id;
        // TODO implement me
        query = "SELECT `user_id` FROM `users` WHERE `E-mail`='"+email+"'";
           

        try {

            resultSet = state.executeQuery(query);

            if (resultSet.next()) {

                id = resultSet.getInt(1);

            } //end if
            else {
                id = 0;
            }
        }//end try
        catch (SQLException ex) {
            ex.printStackTrace();
            id = 0;

        }//end catch

        return id;

    }//end getuserid
	
	/**
	* GET EMAIL
	 */
	
	public String getEmail(int userId) {
        // TODO implement me

        query = "SELECT `E-mail` FROM `users` WHERE `user_id`="+userId;

        String em;

        try {

            resultSet = state.executeQuery(query);

            if (resultSet.next()) {
                em = resultSet.getString(1);

            } //end if 
            else {

                em = null;
            }//end else
        }//end try
        catch (SQLException ex) {
            ex.printStackTrace();
            em = null;
        }//end catch

        return em;
    }//end get email
	/**
	 * return vector of friends list
	 */
	
	public  Vector<User> getFriends(int userID) {
        
            
            Vector<User> friends=new Vector<>();
            try {
            // TODO implement me
             query="SELECT `friend_id` FROM `friends` WHERE `user_id`="+userID;
                
                ResultSet resultSet=state.executeQuery(query);
                Vector<Integer> id=new Vector<>();
                User userItem;
                while(resultSet.next()){
                    
                    id.add(resultSet.getInt(1));
                }
                query="SELECT `user_id` FROM `friends` WHERE `friend_id`="+userID;
                resultSet=state.executeQuery(query);
                while(resultSet.next()){
                    
                    id.add(resultSet.getInt(1));
                }
                
                
                   
                //Getting information of Friends 
                for(Integer item:id){
                     
                        userItem =new User();
                        
                        // contructing my first friend data
                        userItem.setId(item);
                        userItem.setEmail(getEmail(item));
                        userItem.setUsername(getUsername(item));
                   
                        //adding the first user to vector
                         friends.add(userItem);
                    
                    
                }
                       
                    
                
                    
        } catch (SQLException ex) {
                 ex.printStackTrace();
        }
        finally{
        
            return friends;
        }
	}
	
	/**
	 * this function take 2 userIDs and insert them into friends table.
	 */
	
	public  boolean addFriends(int SenderId,int receiverID) {
		
            boolean flag=false;
            String query="INSERT INTO `friends`(`user_id`, `friend_id`) VALUES ("+SenderId+","+receiverID+")";
            
        try {
            
            
            if(removeFriendRequest(SenderId, receiverID)){
            
                state.executeUpdate(query);
                flag=true;
            
            
            }
            
        } catch (SQLException ex) {
           
            ex.printStackTrace();
            
        }
		
            
                return flag;	
	}
	
	/**
	 * this method add senderID and recieverID into friendsRequest database.
	 */
	
	public boolean addFriendRequest(int sendId,String receiverEmail) {
		
            int receiverID=getUserId(receiverEmail);
        try {
            query="INSERT INTO `request`( `send_request`, `recevier_id`) VALUES ("+sendId+","+receiverID+")";
            state.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
                    
	}
	
	/**
	 * return a list of people who sent this user a friend requests.
	 */
	
	public Vector<User> getFriendsRequest(int userID) {
		
            Vector<User> friendsRequest=new Vector<>();
            try {
            
            String query="SELECT `recevier_id` FROM `request` WHERE `send_request`="+userID;
                
                ResultSet resultSet=state.executeQuery(query);
                Vector<Integer> id=new Vector<>();
                User userItem;
                while(resultSet.next()){
                    
                    id.add(resultSet.getInt(1));
                }
                   
                //Getting information of Friends 
                for(Integer item:id){
                     
                        userItem =new User();
                        
                        // contructing my first friend data
                        userItem.setId(item);
                        userItem.setEmail(getEmail(item));
                        userItem.setUsername(getUsername(item));
                   
                        //adding the first user to vector
                         friendsRequest.add(userItem);
                    
                    
                }
                       
                    
                
                    
        } catch (SQLException ex) {
                 ex.printStackTrace();
        }
        finally{
        
            return friendsRequest;
        }
	}
	
	/**
	*This method used to remove a friend request from a friend request table
	*
	**/
	
	public boolean removeFriendRequest(int senderId,int receiverID){
		
            boolean flag=false;
            String query="DELETE FROM `request` WHERE `send_request`="+senderId+"and `recevier_id`="+receiverID;
        try {
            state.executeUpdate(query);
            flag=true;
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
            return flag;
		
	}
        
        
    // Check email existance
    public boolean checkEmail(String email){
        boolean flag=false;
        try {
            query="SELECT `E-mail` FROM `users` WHERE `E-mail`='"+email+"'";
            resultSet=state.executeQuery(query);
            if (resultSet.next()) {
                
                System.out.println("true");
                flag=true;
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally{
         return flag;
        }
    }
 
    /**
     * this method used to search for a friend using his email.
     * @param email of friend.
     * @return his username;
     */
    public String searchFriend(String email)
    {
        
        
        
            query="SELECT `user_name` FROM `users` WHERE `E-mail`= '"+email+"'";
            
            
             try {
            resultSet= state.executeQuery(query);
            if(resultSet.next())
            {
                return(resultSet.getString(1));
                        
            } //end if 
       
             else{return null;}}
        catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
               
       
       
    }
    
    /**
     * This method take a user id and a friend id then return the file id for their conversation.
     * @param userId
     * @param FriendId
     * @return FileId
     */
    public String getFileID(int userId,int friendId){
        String fileId=null;
        String query="SELECT `file_id` FROM `friends` WHERE `user_id`="+userId+" AND `friend_id`="+friendId;
        try {
            ResultSet result=state.executeQuery(query);
            if(result.next()){
            
                fileId=result.getString(1);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return fileId;
    }
 
}

