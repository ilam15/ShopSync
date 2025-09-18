package com.employee;

import com.customer.Customer;
import com.transaction.Transaction;

public class Cashier extends Employee {
    private String shift;   
    private int counterNo;

    public Cashier(int employeeId, String name, double salary, String shift, int counterNo) {
        super(employeeId, name, "CASHIER", salary);
        this.shift = shift;
        this.counterNo = counterNo;
    }

    public Transaction processBilling(Customer customer, Transaction transaction) {
        // Need to be work 
        return transaction; 
    }

    public String issueReceipt(Transaction tx) {
        return tx != null ? tx.generateInvoice() : "No transaction to print.";
    }

    public String getShift() { return shift; }
    public int getCounterNo() { return counterNo; }
}
