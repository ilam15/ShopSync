package com.product;

import java.util.Date;
import com.supplier.Supplier;

public class Product {
    private int productId;
    private String productName;
    private double price;
    private int quantity;
    private Date expiryDate;
    private Supplier supplier;

    public Product(int productId, String productName, double price, int quantity, Date expiryDate, Supplier supplier) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.supplier = supplier;
    }

    public void updateStock(int qty) { this.quantity += qty; }

    public boolean checkAvailability() { return quantity > 0; }

    public String getDetails() {
        return productName + " | Price: " + price + " | Qty: " + quantity + 
               (expiryDate != null ? " | Exp: " + expiryDate : "");
    }

    public void applyDiscount(double percent) {
        if (percent < 0) return;
        this.price -= (price * percent / 100.0);
    }

    public boolean isExpired() { return expiryDate != null && expiryDate.before(new Date()); }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public Supplier getSupplier() { return supplier; }
}
