package com.example.trash.Models;

public class Room {
    int id;
    String type;
    String status;
    int roomnumber;
    String clientlogin;
    String fullname;
    String phonenumber;
    String email;


    public Room(int id, int roomnumber, String fullname, String clientlogin, String phonenumber, String email, String status) {
        this.id = id;
        this.status = status;
        this.roomnumber = roomnumber;
        this.fullname = fullname;
        this.clientlogin = clientlogin;
        this.phonenumber = phonenumber;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClientlogin() {
        return clientlogin;
    }

    public void setClientlogin(String clientlogin) {
        this.clientlogin = clientlogin;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(int number) {
        this.roomnumber = number;
    }
}
