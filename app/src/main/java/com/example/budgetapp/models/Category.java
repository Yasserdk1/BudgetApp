package com.example.budgetapp.models;

public class Category {
    private int id;
    private String name;
    private String color;
    private String iconName;

    public Category() {
    }

    public Category(int id, String name, String color, String iconName) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.iconName = iconName;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getIconName() { return iconName; }
    public void setIconName(String iconName) { this.iconName = iconName; }

    @Override
    public String toString() {
        return name;  // Pour affichage dans Spinner
    }
}