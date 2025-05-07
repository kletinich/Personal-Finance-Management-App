package com.kletinich.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Database connector
public abstract class DatabaseConnector {
    private static String DB_URL = System.getenv("DB_URL");
    private static String DB_USERNAME = System.getenv("DB_USERNAME");
    private static String DB_PASSWORD = System.getenv("DB_PASSWORD");

    private static Connection connection = null;

    // connect to the database
    public static Connection connect(){
        try{
            if(connection == null || connection.isClosed()){
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                System.out.println("Database connected!");

                return connection;
            }

            return null;

        }catch(SQLException e){
            System.err.println("Failed to connect to database!");
            System.out.println(e);
            return null;
        }
    }

    // disconnect from the database
    public static void Disconnect(){
        try{
            if(connection != null && !connection.isClosed()){
                connection.close();
                System.out.println("Disconnected from databse!");
            }

        }catch(SQLException e){
            System.err.println("Error while closing database!");
        }
    }
}
