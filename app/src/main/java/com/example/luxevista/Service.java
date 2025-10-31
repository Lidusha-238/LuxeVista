package com.example.luxevista;

public class Service {
    private int id;
    private String name, desc, image;
    private double price, rating;

    public Service(int id, String name, String desc, double price, String image, double rating) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.image = image;
        this.rating = rating;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDesc() { return desc; }
    public double getPrice() { return price; }
    public String getImage() { return image; }
    public double getRating() { return rating; }
}

