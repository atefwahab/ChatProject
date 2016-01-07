package model;


import java.util.regex.Pattern;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author safaa
 */
public class Validator {

    /**
     * that email is valid or not.
     *
     * @param email
     * @return the boolean value that represent email is valid or not.
     */
    public static boolean isEmail(String email) {
        
		//^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		//+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$"

        boolean b = Pattern.matches("^[^\\d][_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{3,20})$" ,email);
       // System.out.println();
        return b;

    }

    /**
     * that username is valid or not
     *
     * @param username
     * @return the boolean value that represent username is valid or not.
     */
    public static boolean isUsername(String username) {

        boolean b = Pattern.matches("^[^\\d][a-z0-9_-]{4,20}$",username);

        return b;

    }

    /**
     * that password is valid or not
     *
     * @param password
     * @return the boolean value that represent password is valid or not
     */
    public static boolean isPassword(String password) 
    {
        boolean b = Pattern.matches( "^[^\\d][a-z0-9_-]{6,20}$",password);

        return b;
    }
   
}
