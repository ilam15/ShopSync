package com.customer;

public class Complaint {
    private String complaintId;
    private Customer customer;
    private String description;
    private String status;

    public Complaint(String complaintId, Customer customer, String description, String status) {
        this.complaintId = complaintId;
        this.customer = customer;
        this.description = description;
        this.status = status;
    }

    public String getComplaintId() { return complaintId; }
    public Customer getCustomer() { return customer; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
