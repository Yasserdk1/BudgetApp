package com.example.budgetapp.models;

import java.io.Serializable;

public class Expense implements Serializable {
    private int id;
    private double amount;
    private int categoryId;
    private String date;  // format: dd/MM/yyyy
    private String description;
    private String paymentMethod;

    // Champ supplémentaire (pas dans DB)
    private String categoryName;

    public Expense() {
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}