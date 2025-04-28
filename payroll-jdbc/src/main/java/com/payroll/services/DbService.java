package com.payroll.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbService {
    private static final String dbUrl = System.getenv("DATABASE_URL");
    private static final String dbUser = System.getenv("DATABASE_USER");
    private static final String dbPassword = System.getenv("DATABASE_PASSWORD");

    public static DbService instance;

    private DbService() {}

    public static DbService getInstance(){
        if(instance == null){
            instance = new DbService();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}