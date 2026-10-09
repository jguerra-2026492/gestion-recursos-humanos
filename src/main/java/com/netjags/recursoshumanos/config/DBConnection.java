package main.java.com.netjags.recursoshumanos.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DBConnection {
    
    private static Connection conn;
    
    private DBConnection(){
        
    }
    
    public static Connection getConnection()throws SQLException{
        if( conn == null || conn.isClosed()){
            conn = DriverManager.getConnection(Credentials.URL_DATA_BASE, Credentials.USER_DATA_BASE, Credentials.PASS_DATA_BASE);
        }
        return conn;
    }
    
}
