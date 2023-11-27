package com.example.trash;

public class Room {
    int id;
    String type;
    String status;
    int number;

    public Room(int id, String type, String status, int number) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.number = number;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
