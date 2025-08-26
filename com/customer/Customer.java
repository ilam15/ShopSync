package com.customer;

import java.util.*;
import com.product.Product;
import com.transaction.Transaction;
import com.shop.Shop;

public class Customer {
    private int customerId;
    private String customerName;
    private String email;
    private int loyaltyPoints;
    private List<Transaction> purchaseHistory = new ArrayList<>();

    public Customer(int customerId, String customerName, String email) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
    }

    public Transaction buyProduct(Product product, int qty) {
        if (product == null || qty <= 0 || product.getQuantity() < qty) return null;
        double total = product.getPrice() * qty;
        Transaction tx = new Transaction(UUID.randomUUID().toString(), product, this, new Date(), qty, total);
        product.updateStock(-qty);
        purchaseHistory.add(tx);
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

    public int getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public String getEmail() { return email; }
    public int getLoyaltyPoints() { return loyaltyPoints; }
}
