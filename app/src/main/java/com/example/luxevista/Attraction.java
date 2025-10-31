package com.example.luxevista;

public class Attraction {
    int id;
    String name, description, image, type;
    double rating;

    public Attraction(int id, String name, String description, String image, String type, double rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.type = type;
        this.rating = rating;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getImage() { return image; }
    public String getType() { return type; }
    public double getRating() { return rating; }
}
