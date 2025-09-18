package com.shop;

import java.util.*;
import com.product.Product;

public class Shop {
    private int shopId;
    private String shopName;
    private String category;
    private List<Product> products;
    private Object shopDetails; // Placeholder for shop details

    public Shop(int shopId, String shopName, String category, Object shopDetails) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.category = category;
        this.shopDetails = shopDetails;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
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
