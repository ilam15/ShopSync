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

    // File path for storing product data
    private static final String DATA_DIR = "DB";
    private static final String PRODUCT_FILE = DATA_DIR + "/products.txt";

    public Product(int productId, String productName, double price, int quantity, Date expiryDate, Supplier supplier) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.supplier = supplier;
        saveProductData();
    }

    public void updateStock(int qty) { 
        this.quantity += qty; 
        saveProductData();
    }

    public boolean checkAvailability() { return quantity > 0; }

    public String getDetails() {
        return productName + " | Price: " + price + " | Qty: " + quantity + 
               (expiryDate != null ? " | Exp: " + expiryDate : "");
    }

    public void applyDiscount(double percent) {
        if (percent < 0) return;
        this.price -= (price * percent / 100.0);
        saveProductData();
    }

    public boolean isExpired() { return expiryDate != null && expiryDate.before(new Date()); }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public void setPrice(double price) { 
        this.price = price; 
        saveProductData();
    }
    public int getQuantity() { return quantity; }
    public Supplier getSupplier() { return supplier; }

    // Save product data to file
    private void saveProductData() {
        try {
            java.nio.file.Path dirPath = java.nio.file.Paths.get(DATA_DIR);
            if (!java.nio.file.Files.exists(dirPath)) {
                java.nio.file.Files.createDirectories(dirPath);
            }
            java.nio.file.Path filePath = java.nio.file.Paths.get(PRODUCT_FILE);
            String data = productId + "," + productName + "," + price + "," + quantity + "," + expiryDate + "," + (supplier != null ? supplier.getSupplierName() : "") + "\n";
            java.nio.file.Files.write(filePath, data.getBytes(), java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
