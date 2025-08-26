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

    public Shop(int shopId, String shopName, String category, Employee manager) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.category = category;
        this.manager = manager;
    }

    public void addProduct(Product product) { products.add(product); }

    public void removeProduct(int productId) { products.removeIf(p -> p.getProductId() == productId); }

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
}
