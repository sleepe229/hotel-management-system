package com.example.trash.Models;

public class Hotel {
    int id;
    String name;
    int stars;
    String location;

    public Hotel(int id, String name, int stars, String location) {
        this.id = id;
        this.name = name;
        this.stars = stars;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
