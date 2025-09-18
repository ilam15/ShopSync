package com.cart;

import java.util.*;

import com.product.Product;

public class Cart {
    private Map<Product, Integer> items = new HashMap<>();

    // File path for storing cart data
    private static final String DATA_DIR = "DB";
    private static final String CART_FILE = DATA_DIR + "/cart.txt";

    public void addProduct(Product product, int quantity) {
        if (product == null || quantity <= 0) return;
        items.put(product, items.getOrDefault(product, 0) + quantity);
        saveCartData();
    }

    public void removeProduct(Product product) {
        if (product == null) return;
        items.remove(product);
        saveCartData();
    }

    public void updateQuantity(Product product, int quantity) {
        if (product == null) return;
        if (quantity <= 0) {
            items.remove(product);
        } else {
            items.put(product, quantity);
        }
        saveCartData();
    }

    public Map<Product, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public double calculateSubtotal() {
        double subtotal = 0.0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            subtotal += entry.getKey().getPrice() * entry.getValue();
        }
        return subtotal;
    }

    public void clear() {
        items.clear();
        saveCartData();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    // Save cart data to file
    private void saveCartData() {
        try {
            java.nio.file.Path dirPath = java.nio.file.Paths.get(DATA_DIR);
            if (!java.nio.file.Files.exists(dirPath)) {
                java.nio.file.Files.createDirectories(dirPath);
            }
            java.nio.file.Path filePath = java.nio.file.Paths.get(CART_FILE);
            StringBuilder data = new StringBuilder();
            for (Map.Entry<Product, Integer> entry : items.entrySet()) {
                data.append(entry.getKey().getProductName()).append(",").append(entry.getValue()).append("\n");
            }
            java.nio.file.Files.write(filePath, data.toString().getBytes(), java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.WRITE);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
