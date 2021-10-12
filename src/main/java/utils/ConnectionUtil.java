package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    public static Connection getConnection() throws SQLException {
        //for many frameworks using JDBC, it's necessary to "register" the driver we're using to make the framework aware of it.

        try{
            Class.forName("org.postgresql.Driver");
//
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        String  url = "jdbc:postgresql://javafs-210927.cngqgibuqdom.us-east-1.rds.amazonaws.com:5432/javafs210927";
        String username = "postgres", pwd = "abcd1234";
        //it's possible to use System.getenv("var-name") to hide this login info.
        
        return DriverManager.getConnection(url, username, pwd);
        }



}
