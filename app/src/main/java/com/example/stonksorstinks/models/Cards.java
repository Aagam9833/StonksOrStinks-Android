package com.example.stonksorstinks.models;

public class Cards {
    int id,points;
    String name,description;

    public Cards() {
    }

    public Cards(int id, String name, int points, String description) {
        this.id = id;
        this.points = points;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
