package com.bank.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Statement;



public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/bank_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Root@123";

    private static Connection connection = null;

  
    private DBConnection() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
            	
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);

                Statement stmt = connection.createStatement();                

                System.out.println("Database Connected Successfully");     
            }
        } catch (ClassNotFoundException e) {
      
        } catch (SQLException e) {
          e.printStackTrace();
        }
        return connection;
    }
    public static void main(String args[]) {
    	getConnection();
    	System.out.println(getConnection());
    }

}


