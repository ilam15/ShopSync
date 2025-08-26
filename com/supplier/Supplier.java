package com.supplier;

import java.util.*;
    import com.product.Product;

public class Supplier {
    private int supplierId;
    private String supplierName;
    private String contact;
    private List<Product> productsSupplied = new ArrayList<>();

    public Supplier(int supplierId, String supplierName, String contact) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.contact = contact;
    }

    public void supplyProduct(Product product, int qty) {
        product.updateStock(qty);
        if (!productsSupplied.contains(product)) productsSupplied.add(product);
    }

    public String getSupplierDetails() {
        return "Supplier :: " + supplierName + " | Contact: " + contact + " | ID: " + supplierId;
    }

    public void updateSupplierInfo(String newContact) { this.contact = newContact; }

    public int getSupplierId() { return supplierId; }
    public String getSupplierName() { return supplierName; }
    public String getContact() { return contact; }
    public List<Product> getProductsSupplied() { return Collections.unmodifiableList(productsSupplied); }
}
