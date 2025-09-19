package com.shop;

import java.util.*;
import com.product.Product;

public class Shop {
    private int shopId;
    private String shopName;
    private String category;
    private List<Product> products;
    private Map<Integer, Product> productMap; // For efficient lookups
    private Object shopDetails; // Placeholder for shop details

    // File path for storing shop data
    private static final String DATA_DIR = "DB";
    private static final String SHOP_FILE = DATA_DIR + "/shops.txt";

    public Shop(int shopId, String shopName, String category, Object shopDetails) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.category = category;
        this.shopDetails = shopDetails;
        this.products = new ArrayList<>();
    }

    // Save shop data to file
    public void saveShopData() {
        try {
            java.nio.file.Path dirPath = java.nio.file.Paths.get(DATA_DIR);
            if (!java.nio.file.Files.exists(dirPath)) {
                java.nio.file.Files.createDirectories(dirPath);
            }
            java.nio.file.Path filePath = java.nio.file.Paths.get(SHOP_FILE);
            String data = shopId + "," + shopName + "," + category + "\n";
            java.nio.file.Files.write(filePath, data.getBytes(), java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    // Private constructor for loading without saving
    private Shop(int shopId, String shopName, String category, Object shopDetails, boolean load) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.category = category;
        this.shopDetails = shopDetails;
        this.products = new ArrayList<>();
    }

    // Static method to load shops from file
    public static List<Shop> loadShops() {
        List<Shop> shops = new ArrayList<>();
        java.nio.file.Path filePath = java.nio.file.Paths.get(SHOP_FILE);
        if (!java.nio.file.Files.exists(filePath)) {
            return shops;
        }
        try {
            List<String> lines = java.nio.file.Files.readAllLines(filePath);
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String category = parts[2].trim();
                    Shop shop = new Shop(id, name, category, null, true);
                    shops.add(shop);
                }
            }
        } catch (java.io.IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return shops;
    }

    public void addProduct(Product product) {
        if (product != null && !products.contains(product)) {
            products.add(product);
            product.saveProductData();
        }
    }

    public void removeProduct(int productId) {
        products.removeIf(product -> product.getProductId() == productId);
    }

    public Product getProduct(int productId) {
        return products.stream()
                .filter(product -> product.getProductId() == productId)
                .findFirst()
                .orElse(null);
    }

    public List<Product> listAllProducts() {
        return new ArrayList<>(products);
    }

    public int getShopId() { return shopId; }
    public String getShopName() { return shopName; }
    public String getCategory() { return category; }
}
