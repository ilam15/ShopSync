package com.cart;

import java.util.*;
import com.product.Product;

public class Cart {
    private Map<Product, Integer> items;

    public Cart() {
        this.items = new HashMap<>();
    }

    public void addProduct(Product product, int quantity) {
        if (product != null && quantity > 0) {
            items.put(product, items.getOrDefault(product, 0) + quantity);
        }
    }

    public void removeProduct(Product product) {
        if (product != null) {
            items.remove(product);
        }
    }

    public Map<Product, Integer> getItems() {
        return new HashMap<>(items);
    }

    public double calculateSubtotal() {
        double subtotal = 0.0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            subtotal += entry.getKey().getPrice() * entry.getValue();
        }
        return subtotal;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }
}
