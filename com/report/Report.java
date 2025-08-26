package com.report;

import java.util.*;
import com.shop.Shop;
import com.customer.Customer;

public class Report {
    private String reportId;
    private String type; // Sales / Stock / Profit / Customer
    private Date date;
    private String content;

    public Report(String reportId, String type) {
        this.reportId = reportId;
        this.type = type;
        this.date = new Date();
    }

    public String generateSalesReport(Shop shop) {
        if (shop == null) return "No shop selected";
        content = "Sales Report for Shop: " + shop.getShopName() + " | Products: " + shop.listAllProducts().size();
        return content;
    }

    public String generateCustomerReport(Customer customer) {
        if (customer == null) return "No customer selected";
        content = "Customer Report: " + customer.getCustomerName() + " | Purchases: " + customer.viewPurchaseHistory().size();
        return content;
    }

    public String generateMallReport() {
        content = "Mall-level aggregated report (placeholder for analytics).";
        return content;
    }

    public String exportReport(String format) {
        return "Exported report " + reportId + " as " + format + " on " + date + ".";
    }

    public String getReportId() { return reportId; }
    public String getType() { return type; }
    public Date getDate() { return date; }
    public String getContent() { return content; }
}
