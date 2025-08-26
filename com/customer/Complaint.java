package com.customer;

public class Complaint {
    private String complaintId;
    private Customer customer;
    private String description;
    private String status; // OPEN, IN_PROGRESS, RESOLVED, REJECTED

    public Complaint(String complaintId, Customer customer, String description, String status) {
        this.complaintId = complaintId;
        this.customer = customer;
        this.description = description;
        this.status = status;
    }

    public void resolveComplaint() { this.status = "RESOLVED"; }

    public String getComplaintDetails() {
        return "Complaint #" + complaintId + " | Customer: " + customer.getCustomerName() +
               " | Status: " + status + " | Desc: " + description;
    }

    public String getComplaintId() { return complaintId; }
    public String getStatus() { return status; }
    public Customer getCustomer() { return customer; }
}
