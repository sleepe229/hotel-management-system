package com.example.trash.DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    protected static final String DB_URL = "jdbc:postgresql://localhost:5432/Hotel";
    protected static final String DB_USER = "postgres";
    protected static final String DB_PASSWORD = "1111";
    protected static Connection DBConnecting() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
