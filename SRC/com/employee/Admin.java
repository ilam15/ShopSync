package com.employee;

import com.mall.Mall;
import com.shop.Shop;

public class Admin extends Employee {
    private String username;
    private String password;

    public Admin(int employeeId, String name, double salary, String username, String password) {
        super(employeeId, name, "ADMIN", salary);
        this.username = username;
        this.password = password;
    }

    public void addShop(Mall mall, Shop shop) {
        if (mall != null && shop != null) mall.addShop(shop);
    }

    public void removeShop(Mall mall, int shopId) {
        if (mall != null) mall.removeShop(shopId);
    }

    public String generateMallReport(Mall mall) {
        if (mall == null) return "No mall selected";
        return mall.getMallReport();
    }

    public String manageProducts(Shop shop) {
        if (shop == null) return "No shop selected";
        return "Managing products for shop: " + shop.getShopName() + " | Count: " + shop.listAllProducts().size();
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }
}
