package com.payment;

public class Payment {
    private String paymentId;
    private String paymentType;
    private double amount;
    private String status;

    public Payment(String paymentId, String paymentType) {
        this.paymentId = paymentId;
        this.paymentType = paymentType;
        this.status = "PENDING";
    }

    public boolean processPayment(double amount) {
        this.amount = amount;
        this.status = "COMPLETED";
        return true;
    }

    public String getPaymentId() { return paymentId; }
    public String getPaymentType() { return paymentType; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
}
