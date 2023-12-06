package com.example.trash.DBUtils;

public class DBPasswordHashing {

    public static String hashed(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}