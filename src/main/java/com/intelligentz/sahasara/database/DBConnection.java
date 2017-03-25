/*
<<<<<<< HEAD
 * To change this template, choose Tools | Templates
=======
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
>>>>>>> d47ea1485a67123ed8e10344b20bfa672a7bc0f1
 * and open the template in the editor.
 */
package com.intelligentz.sahasara.database;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;

/**
 *
 * @author Heshan
 * 
 */
//public class DBConnection {
//    private static DBConnection dbConnection;
//    private Connection connection;
//
//    private DBConnection() throws ClassNotFoundException, SQLException{
//        Class.forName("com.mysql.jdbc.Driver");
//        connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/sahasara_db","root","12345");
//    }
//
//    private Connection getConnection(){
//        return connection;
//    }
//
//    private static DBConnection getDBConnection() throws ClassNotFoundException, SQLException{
//        if(dbConnection==null){
//            dbConnection=new DBConnection();
//        }
//        return dbConnection;
//    }
//
//    public static Connection getConnectionToDB() throws ClassNotFoundException, SQLException{
//        DBConnection dbConnect=DBConnection.getDBConnection();
//        return dbConnect.getConnection();
//
//    }
//=======
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import com.intelligentz.sahasara.constants.DatabaseConstants;

/**
 *
 * @author DINETH
 */
public class DBConnection {
    private static DBConnection dbConnection;
    private final BasicDataSource ds;
    private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());

    private DBConnection()throws IOException, SQLException, PropertyVetoException {
        LOGGER.log(Level.ALL, "Connecting to database...");
        System.out.println("Connecting to database...");
        /*try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        ds = new BasicDataSource();
        ds.setDriverClassName(DatabaseConstants.DRIVER);
        ds.setUsername(DatabaseConstants.USERNAME);
        ds.setPassword(DatabaseConstants.PASSWORD);
        ds.setUrl(DatabaseConstants.DB_URL+DatabaseConstants.DB_NAME);
        // the settings below are optional -- dbcp can work with defaults
        //        ds.setMinIdle(5);
        //        ds.setMaxIdle(20);
        //        ds.setMaxOpenPreparedStatements(180);

    }
        
    public static synchronized DBConnection getDBConnection() throws IOException, SQLException, PropertyVetoException {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
            return dbConnection;
        } else {
            return dbConnection;
        }
    }

    public Connection getConnection() throws SQLException {
        return this.ds.getConnection();
    }
    
    
}
