package com.product;

import java.util.Date;

public class Product {
    private int productId;
    private String productName;
    private double price;
    private int quantity;
    private Date expiryDate;
    private Object productDetails; // Placeholder for product details

    public Product(int productId, String productName, double price, int quantity, Date expiryDate, Object productDetails) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.productDetails = productDetails;
    }

    public void updateStock(int quantityChange) {
        this.quantity += quantityChange;
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public Date getExpiryDate() { return expiryDate; }
}
