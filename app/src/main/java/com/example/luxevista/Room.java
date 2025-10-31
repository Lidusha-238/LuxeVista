package com.example.luxevista;

    public class Room {
        private int id;
        private String name;
        private String description;
        private double price, rating;
        private String type;
        private String image;
        private String availability; // new field


        public Room(int id, String name, String description, double price, String type, String image, String availability, double rating) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.type = type;
            this.image = image;
            this.availability = availability;
            this.rating = rating;
        }

        // Existing getters
        public int getId() { return id; }
        public String getName() { return name; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public String getType() { return type; }
        public String getImage() { return image; }
        public String getAvailability() { return availability; }
        public double getRating() { return rating; }
    }

