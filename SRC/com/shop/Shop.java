package com.shop;

import java.util.*;
    import com.product.Product;
import com.employee.Employee;

public class Shop {
    private int shopId;
    private String shopName;
    private String category;
    private List<Product> products = new ArrayList<>();
    private Employee manager;

    // File path for storing shop data
    private static final String DATA_DIR = "DB";
    private static final String SHOP_FILE = DATA_DIR + "/shops.txt";

    public Shop(int shopId, String shopName, String category, Employee manager) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.category = category;
        this.manager = manager;
        saveShopData();
    }

    public void addProduct(Product product) {
        products.add(product);
        saveShopData();
    }

    public void removeProduct(int productId) {
        products.removeIf(p -> p.getProductId() == productId);
        saveShopData();
    }

    public void updateProduct(int productId, Product newDetails) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId() == productId) {
                products.set(i, newDetails);
                return;
            }
        }
    }

    public Product getProduct(int productId) {
        return products.stream().filter(p -> p.getProductId() == productId).findFirst().orElse(null);
    }

    public List<Product> listAllProducts() { return Collections.unmodifiableList(products); }

    public String getShopReport() {
        return "Shop Report :: " + shopName + " | Category: " + category + " | Products: " + products.size();
    }

    public int getShopId() { return shopId; }
    public String getShopName() { return shopName; }
    public String getCategory() { return category; }
    public Employee getManager() { return manager; }

    // Save shop data to file
    private void saveShopData() {
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
}
