package com.example.trash;

public class Room {
    int id;
    String type;
    String status;
    int number;
    String clientlogin;
    String fullname;
    String phonenumber;
    String email;

    public Room(int id, String type, String status, int number) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.number = number;
    }

    public Room(int id, int number, String fullname, String clientlogin, String phonenumber, String email, String status) {
        this.id = id;
        this.status = status;
        this.number = number;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
