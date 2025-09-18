package com.customer;

import java.util.*;
import com.product.Product;
import com.transaction.Transaction;
import com.shop.Shop;
import com.cart.Cart;

public class Customer {
    private int customerId;
    private String customerName;
    private String email;
    private int loyaltyPoints;
    private List<Transaction> purchaseHistory = new ArrayList<>();

    // File path for storing customer data
    private static final String DATA_DIR = "DB";
    private static final String CUSTOMER_FILE = DATA_DIR + "/customers.txt";

    public Customer(int customerId, String customerName, String email) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
        saveCustomerData();
    }

    public Transaction buyProduct(Product product, int qty) {
        if (product == null || qty <= 0 || product.getQuantity() < qty) return null;
        double total = product.getPrice() * qty;
        Cart cart = new Cart();
        cart.addProduct(product, qty);
        Transaction tx = new Transaction(UUID.randomUUID().toString(), this, new Date(), cart);
        product.updateStock(-qty);
        purchaseHistory.add(tx);
        saveCustomerData();
        // earn loyalty points: 1 point per 100 currency
        loyaltyPoints += (int) Math.floor(total / 100.0);
        return tx;
    }

    public List<Transaction> viewPurchaseHistory() { return Collections.unmodifiableList(purchaseHistory); }

    public Product searchProduct(Shop shop, String productName) {
        if (shop == null || productName == null) return null;
        return shop.listAllProducts().stream()
                .filter(p -> p.getProductName().equalsIgnoreCase(productName))
                .findFirst().orElse(null);
    }

    public boolean redeemPoints(int points) {
        if (points <= 0 || points > loyaltyPoints) return false;
        loyaltyPoints -= points;
        return true;
    }

    public Complaint registerComplaint(String description) {
        return new Complaint(UUID.randomUUID().toString(), this, description, "OPEN");
    }

    public void addToPurchaseHistory(Transaction tx) {
        purchaseHistory.add(tx);
        saveCustomerData();
    }

    public int getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public String getEmail() { return email; }
    public int getLoyaltyPoints() { return loyaltyPoints; }

    // Save customer data to file
    private void saveCustomerData() {
        try {
            java.nio.file.Path dirPath = java.nio.file.Paths.get(DATA_DIR);
            if (!java.nio.file.Files.exists(dirPath)) {
                java.nio.file.Files.createDirectories(dirPath);
            }
            java.nio.file.Path filePath = java.nio.file.Paths.get(CUSTOMER_FILE);
            String data = customerId + "," + customerName + "," + email + "," + loyaltyPoints + "\n";
            java.nio.file.Files.write(filePath, data.getBytes(), java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
