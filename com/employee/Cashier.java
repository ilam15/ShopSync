package com.employee;

import com.customer.Customer;
import com.transaction.Transaction;

public class Cashier extends Employee {
    private String shift;   // MORNING / EVENING / NIGHT
    private int counterNo;

    public Cashier(int employeeId, String name, double salary, String shift, int counterNo) {
        super(employeeId, name, "CASHIER", salary);
        this.shift = shift;
        this.counterNo = counterNo;
    }

    public Transaction processBilling(Customer customer, Transaction transaction) {
        // In a real system, apply promos, taxes, etc.
        return transaction; // here we just return the same transaction as "processed"
    }

    public String issueReceipt(Transaction tx) {
        return tx != null ? tx.generateInvoice() : "No transaction to print.";
    }

    public String getShift() { return shift; }
    public int getCounterNo() { return counterNo; }
}
