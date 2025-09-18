package com.payment;

public class Payment {
    private String paymentId;
    private String type;   
    private double amount;
    private String status; 

    public Payment(String paymentId, String type) {
        this.paymentId = paymentId;
        this.type = type;
        this.status = "INITIATED";
    }

    public boolean processPayment() {
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
