package com.payment;

public class Payment {
    private String paymentId;
    private String type;   // Cash / Card / UPI
    private double amount;
    private String status; // INITIATED / SUCCESS / FAILED / REFUNDED

    public Payment(String paymentId, String type) {
        this.paymentId = paymentId;
        this.type = type;
        this.status = "INITIATED";
    }

    public boolean processPayment() {
        // In real life: integrate with a gateway. Here: accept if amount > 0.
        if (amount > 0) {
            status = "SUCCESS";
            return true;
        }
        status = "FAILED";
        return false;
    }

    public boolean refundPayment() {
        if ("SUCCESS".equals(status)) {
            status = "REFUNDED";
            return true;
        }
        return false;
    }

    public String getPaymentDetails() {
        return "Payment #" + paymentId + " | Type: " + type + " | Amount: " + amount + " | Status: " + status;
    }

    public void setAmount(double amount) { this.amount = amount; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
    public String getType() { return type; }
    public String getPaymentId() { return paymentId; }
}
