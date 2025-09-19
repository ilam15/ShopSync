package com.product;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Product {
    private int productId;
    private String productName;
    private double price;
    private int quantity;
    private Date expiryDate;
    private Object productDetails; // Placeholder for product details
    private int shopId;

    // File path for storing product data
    private static final String DATA_DIR = "DB";
    private static final String PRODUCT_FILE = DATA_DIR + "/products.txt";

    public Product(int productId, String productName, double price, int quantity, Date expiryDate, Object productDetails) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.productDetails = productDetails;
        this.shopId = 0; // default
    }

    // Constructor with shopId
    public Product(int productId, String productName, double price, int quantity, Date expiryDate, Object productDetails, int shopId) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.productDetails = productDetails;
        this.shopId = shopId;
    }

    // Save product data to file
    public void saveProductData() {
        try {
            java.nio.file.Path dirPath = java.nio.file.Paths.get(DATA_DIR);
            if (!java.nio.file.Files.exists(dirPath)) {
                java.nio.file.Files.createDirectories(dirPath);
            }
            java.nio.file.Path filePath = java.nio.file.Paths.get(PRODUCT_FILE);
            String data = productId + "," + productName + "," + price + "," + quantity + "," + shopId + "\n";
            java.nio.file.Files.write(filePath, data.getBytes(), java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    // Private constructor for loading without saving
    private Product(int productId, String productName, double price, int quantity, Date expiryDate, Object productDetails, int shopId, boolean load) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.productDetails = productDetails;
        this.shopId = shopId;
    }

    // Static method to load products from file
    public static List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();
        java.nio.file.Path filePath = java.nio.file.Paths.get(PRODUCT_FILE);
        if (!java.nio.file.Files.exists(filePath)) {
            return products;
        }
        try {
            List<String> lines = java.nio.file.Files.readAllLines(filePath);
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    int qty = Integer.parseInt(parts[3].trim());
                    int shopId = Integer.parseInt(parts[4].trim());
                    Product product = new Product(id, name, price, qty, null, null, shopId, true);
                    products.add(product);
                }
            }
        } catch (java.io.IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void updateStock(int quantityChange) {
        this.quantity += quantityChange;
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public Date getExpiryDate() { return expiryDate; }
    public int getShopId() { return shopId; }
}
