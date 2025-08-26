package com.transaction;

import java.util.Date;
    import com.product.Product;
import com.customer.Customer;
import com.payment.Payment;

public class Transaction {
    private String transactionId;
    private Product product;
    private Customer customer;
    private Date date;
    private int quantity;
    private double totalAmount;

    public Transaction(String transactionId, Product product, Customer customer, Date date, int quantity, double totalAmount) {
        this.transactionId = transactionId;
        this.product = product;
        this.customer = customer;
        this.date = date;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public String getTransactionDetails() {
        return "TX " + transactionId + " | " + date + " | Customer: " + customer.getCustomerName() +
               " | Product: " + product.getProductName() + " x" + quantity + " | Total: " + totalAmount;
    }

    public double calculateTotal() { return product.getPrice() * quantity; }

    public String generateInvoice() {
        return """
               ===== INVOICE =====
               Txn ID: %s
               Date: %s
               Customer: %s
               Product: %s
               Qty: %d
               Total: %.2f
               ===================
               """.formatted(transactionId, date, customer.getCustomerName(),
                             product.getProductName(), quantity, totalAmount);
    }

    public boolean processPayment(Payment payment) {
        if (payment == null) return false;
        payment.setAmount(totalAmount);
        return payment.processPayment();
    }

    public String getTransactionId() { return transactionId; }
    public double getTotalAmount() { return totalAmount; }
    public int getQuantity() { return quantity; }
    public Date getDate() { return date; }
    public Product getProduct() { return product; }
    public Customer getCustomer() { return customer; }
}
