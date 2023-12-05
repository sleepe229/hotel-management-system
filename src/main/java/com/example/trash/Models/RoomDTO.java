package com.example.trash.Models;

public class RoomDTO {
    int id;
    int roomnumber;
    String type;
    String status;

    public RoomDTO(int id, String type, String status, int roomnumber) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.roomnumber = roomnumber;
    }
}
