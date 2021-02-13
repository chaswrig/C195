/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author chase
 * This entire class is needed to connect to a java database
 * I think it can be used for other programs, too.
 * This entire class was discussed in the WGU Database lecture w/ Malcom Wabbara.
 * I don't think there is another way to do it.
*/
public class database 
{
    private static final String PROTOCOL = "jdbc"; //This is the type of protocol we are using
    private static final String VENDOR_NAME = ":mysql:"; //this is who houses the database
    private static final String IP_ADDRESS = Password.getIPAddress(); //Pulls IP address given by WGU. Method used for privacy.
    
    //JDBC URL
    private static final String JDBC_URL = PROTOCOL + VENDOR_NAME + IP_ADDRESS;
    
    //Driver Interface reference
    private static final String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static Connection conn = null; //This is the variable that lets you actually connect to the database
    private static final String USERNAME = Password.getUsername(); //Pulls username given by WGU. Method used for privacy.
    private static final String PASSWORD = Password.getPassword(); //Pulls the password given by WGU. Method used for privacy.
    
    //method for connecting
    public static Connection startConnection()
    {
        try 
        {
            Class.forName(MYSQL_JDBC_DRIVER);
            conn = (Connection)DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("Connection Successful!");
        } 
        catch (ClassNotFoundException | SQLException e) 
        {
            System.out.println(e.getMessage());
        }
        
        return conn;
    }
    
    //method for disconnecting
    public static void closeConnection()
    {
        try {
            conn.close();
            System.out.println("Connection Closed.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static Connection getConnection()
    {
        return conn;
    }
}