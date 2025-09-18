package com.payment;

public class Payment {
    private String paymentId;
    private String type;   
    private double amount;
    private String status;

    // File path for storing payment data
    private static final String DATA_DIR = "DB";
    private static final String PAYMENT_FILE = DATA_DIR + "/payments.txt";

    public Payment(String paymentId, String type) {
        this.paymentId = paymentId;
        this.type = type;
        this.status = "INITIATED";
        savePaymentData();
    }

    public boolean processPayment() {
        if (amount > 0) {
            status = "SUCCESS";
            savePaymentData();
            return true;
        }
        status = "FAILED";
        savePaymentData();
        return false;
    }

    public boolean refundPayment() {
        if ("SUCCESS".equals(status)) {
            status = "REFUNDED";
            savePaymentData();
            return true;
        }
        return false;
    }

    public String getPaymentDetails() {
        return "Payment #" + paymentId + " | Type: " + type + " | Amount: " + amount + " | Status: " + status;
    }

    public void setAmount(double amount) {
        this.amount = amount;
        savePaymentData();
    }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
    public String getType() { return type; }
    public String getPaymentId() { return paymentId; }

    // Save payment data to file
    private void savePaymentData() {
        try {
            java.nio.file.Path dirPath = java.nio.file.Paths.get(DATA_DIR);
            if (!java.nio.file.Files.exists(dirPath)) {
                java.nio.file.Files.createDirectories(dirPath);
            }
            java.nio.file.Path filePath = java.nio.file.Paths.get(PAYMENT_FILE);
            String data = paymentId + "," + type + "," + amount + "," + status + "\n";
            java.nio.file.Files.write(filePath, data.getBytes(), java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
