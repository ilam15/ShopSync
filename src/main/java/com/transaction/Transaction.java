package com.transaction;

import java.util.Date;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import com.customer.Customer;
import com.cart.Cart;
import com.payment.Payment;

public class Transaction {
    private String transactionId;
    private String billId;
    private Customer customer;
    private Date date;
    private Cart cart;
    private double totalAmount;
    private String status;

    public Transaction(String transactionId, Customer customer, Date date, Cart cart) {
        this.transactionId = transactionId;
        this.billId = generateBillId();
        this.customer = customer;
        this.date = date;
        this.cart = cart;
        this.totalAmount = cart.calculateSubtotal() * 1.1; // Including 10% tax
        this.status = "PENDING";
    }

    private String generateBillId() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999) + 1);
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
        invoice.append("==========================================\n");
        invoice.append("              SHOPSYNC MALL\n");
        invoice.append("         Mall Management System\n");
        invoice.append("==========================================\n");
        invoice.append("BILL ID: ").append(billId).append("\n");
        invoice.append("Transaction ID: ").append(transactionId).append("\n");
        invoice.append("Date: ").append(date).append("\n");
        invoice.append("Customer: ").append(customer.getCustomerName()).append("\n");
        invoice.append("Email: ").append(customer.getEmail()).append("\n");
        invoice.append("==========================================\n");
        invoice.append("ITEMS PURCHASED:\n");
        invoice.append("-----------------------------------------\n");
        for (var entry : cart.getItems().entrySet()) {
            double itemTotal = entry.getKey().getPrice() * entry.getValue();
            invoice.append(String.format("%-20s x%-3d $%-8.2f $%.2f%n",
                entry.getKey().getProductName(),
                entry.getValue(),
                entry.getKey().getPrice(),
                itemTotal));
        }
        invoice.append("==========================================\n");
        invoice.append(String.format("SUBTOTAL:                    $%.2f%n", cart.calculateSubtotal()));
        invoice.append(String.format("TAX (10%%):                   $%.2f%n", cart.calculateSubtotal() * 0.1));
        invoice.append("==========================================\n");
        invoice.append(String.format("GRAND TOTAL:                 $%.2f%n", totalAmount));
        invoice.append("==========================================\n");
        invoice.append("Payment Status: ").append(status).append("\n");
        invoice.append("Thank you for shopping with ShopSync!\n");
        invoice.append("==========================================\n");
        return invoice.toString();
    }

    public void downloadBillAsText() {
        try {
            String filename = "bill_" + billId + ".txt";
            FileWriter writer = new FileWriter(filename);
            writer.write(generateInvoice());
            writer.close();
            System.out.println("Bill downloaded successfully as: " + filename);
        } catch (IOException e) {
            System.err.println("Error downloading bill: " + e.getMessage());
        }
    }

    public String getBillId() { return billId; }

    public String getTransactionId() { return transactionId; }
    public Customer getCustomer() { return customer; }
    public Date getDate() { return date; }
    public Cart getCart() { return cart; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
}
