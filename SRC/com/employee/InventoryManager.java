package com.employee;

import java.util.*;
import com.product.Product;
import com.supplier.Supplier;

public class InventoryManager extends Employee {
    private String section;        
    private String responsibility; 

    public InventoryManager(int employeeId, String name, double salary, String section, String responsibility) {
        super(employeeId, name, "INVENTORY_MANAGER", salary);
        this.section = section;
        this.responsibility = responsibility;
    }

    public List<Product> trackLowStock(List<Product> products, int threshold) {
        if (products == null) return List.of();
        List<Product> low = new ArrayList<>();
        for (Product p : products) if (p.getQuantity() <= threshold) low.add(p);
        return low;
    }

    public void orderFromSupplier(Product product, Supplier supplier, int qty) {
        if (product != null && supplier != null && qty > 0) supplier.supplyProduct(product, qty);
    }

    public List<Product> updateExpiryCheck(List<Product> products) {
        if (products == null) return List.of();
        List<Product> expired = new ArrayList<>();
        for (Product p : products) if (p.isExpired()) expired.add(p);
        return expired;
    }

    public String getSection() { return section; }
    public String getResponsibility() { return responsibility; }
}
