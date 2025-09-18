package com.cart;

import java.util.*;

import com.product.Product;

public class Cart {
    private Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        if (product == null || quantity <= 0) return;
        items.put(product, items.getOrDefault(product, 0) + quantity);
    }

    public void removeProduct(Product product) {
        if (product == null) return;
        items.remove(product);
    }

    public void updateQuantity(Product product, int quantity) {
        if (product == null) return;
        if (quantity <= 0) {
            items.remove(product);
        } else {
            items.put(product, quantity);
        }
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
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
