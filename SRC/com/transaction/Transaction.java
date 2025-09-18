package com.transaction;

import java.util.Date;
import java.util.Map;
import com.product.Product;
import com.customer.Customer;
import com.payment.Payment;
import com.cart.Cart;

public class Transaction {
    private String transactionId;
    private Customer customer;
    private Date date;
    private Cart cart;
    private double totalAmount;

    // File path for storing transaction data
    private static final String DATA_DIR = "DB";
    private static final String TRANSACTION_FILE = DATA_DIR + "/transactions.txt";

    public Transaction(String transactionId, Customer customer, Date date, Cart cart) {
        this.transactionId = transactionId;
        this.customer = customer;
        this.date = date;
        this.cart = cart;
        this.totalAmount = cart.calculateSubtotal();
        saveTransactionData();
    }

    public String getTransactionDetails() {
        StringBuilder details = new StringBuilder();
        details.append("TX ").append(transactionId).append(" | ").append(date)
               .append(" | Customer: ").append(customer.getCustomerName()).append(" | Items: ");
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            details.append(entry.getKey().getProductName()).append(" x").append(entry.getValue()).append(", ");
        }
        details.append("Total: ").append(totalAmount);
        return details.toString();
    }

    public double calculateTotal() {
        return totalAmount;
    }

    public String generateInvoice() {
        StringBuilder invoice = new StringBuilder();
        invoice.append("===== INVOICE =====\n");
        invoice.append("Txn ID: ").append(transactionId).append("\n");
        invoice.append("Date: ").append(date).append("\n");
        invoice.append("Customer: ").append(customer.getCustomerName()).append("\n");
        invoice.append("Items:\n");
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            invoice.append(" - ").append(entry.getKey().getProductName())
                   .append(" x").append(entry.getValue())
                   .append(" @ ").append(entry.getKey().getPrice())
                   .append(" each\n");
        }
        invoice.append("Total: ").append(String.format("%.2f", totalAmount)).append("\n");
        invoice.append("===================\n");
        return invoice.toString();
    }

    public boolean processPayment(Payment payment) {
        if (payment == null) return false;
        payment.setAmount(totalAmount);
        return payment.processPayment();
    }

    public String getTransactionId() { return transactionId; }
    public double getTotalAmount() { return totalAmount; }
    public Date getDate() { return date; }
    public Cart getCart() { return cart; }
    public Customer getCustomer() { return customer; }

    // Save transaction data to file
    private void saveTransactionData() {
        try {
            java.nio.file.Path dirPath = java.nio.file.Paths.get(DATA_DIR);
            if (!java.nio.file.Files.exists(dirPath)) {
                java.nio.file.Files.createDirectories(dirPath);
            }
            java.nio.file.Path filePath = java.nio.file.Paths.get(TRANSACTION_FILE);
            String data = transactionId + "," + customer.getCustomerName() + "," + date + "," + totalAmount + "\n";
            java.nio.file.Files.write(filePath, data.getBytes(), java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
