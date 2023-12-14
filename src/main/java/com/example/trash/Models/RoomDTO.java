package com.example.trash.Models;

public class RoomDTO {
    int hotel_id;
    int roomnumber;
    String type;
    String status;

    public RoomDTO(int hotel_id, String type, String status, int roomnumber) {
        this.hotel_id = hotel_id;
        this.type = type;
        this.status = status;
        this.roomnumber = roomnumber;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public int getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(int roomnumber) {
        this.roomnumber = roomnumber;
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
}
