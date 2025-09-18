package com.transaction;

import java.util.Date;
import com.customer.Customer;
import com.cart.Cart;
import com.payment.Payment;

public class Transaction {
    private String transactionId;
    private Customer customer;
    private Date date;
    private Cart cart;
    private double totalAmount;
    private String status;

    public Transaction(String transactionId, Customer customer, Date date, Cart cart) {
        this.transactionId = transactionId;
        this.customer = customer;
        this.date = date;
        this.cart = cart;
        this.totalAmount = cart.calculateSubtotal() * 1.1; // Including 10% tax
        this.status = "PENDING";
    }

    public boolean processPayment(Payment payment) {
        if (payment != null) {
            this.status = "COMPLETED";
            return true;
        }
        return false;
    }

    public String generateInvoice() {
        StringBuilder invoice = new StringBuilder();
        invoice.append("Transaction ID: ").append(transactionId).append("\n");
        invoice.append("Customer: ").append(customer.getCustomerName()).append("\n");
        invoice.append("Date: ").append(date).append("\n");
        invoice.append("Items:\n");
        for (var entry : cart.getItems().entrySet()) {
            invoice.append("- ").append(entry.getKey().getProductName())
                   .append(" x").append(entry.getValue())
                   .append(" @ $").append(entry.getKey().getPrice()).append("\n");
        }
        invoice.append("Subtotal: $").append(String.format("%.2f", cart.calculateSubtotal())).append("\n");
        invoice.append("Tax (10%): $").append(String.format("%.2f", cart.calculateSubtotal() * 0.1)).append("\n");
        invoice.append("Total: $").append(String.format("%.2f", totalAmount)).append("\n");
        invoice.append("Status: ").append(status).append("\n");
        return invoice.toString();
    }

    public String getTransactionId() { return transactionId; }
    public Customer getCustomer() { return customer; }
    public Date getDate() { return date; }
    public Cart getCart() { return cart; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
}
