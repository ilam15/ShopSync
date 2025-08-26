package com.mall;

import java.util.*;
import com.shop.Shop;
import com.employee.Admin;

public class Mall {
    private int mallId;
    private String mallName;
    private String location;
    private List<Shop> shops = new ArrayList<>();
    private Admin admin;

    public Mall(int mallId, String mallName, String location, Admin admin) {
        this.mallId = mallId;
        this.mallName = mallName;
        this.location = location;
        this.admin = admin;
    }

    public void addShop(Shop shop) { shops.add(shop); }

    public void removeShop(int shopId) { shops.removeIf(s -> s.getShopId() == shopId); }

    public Shop getShop(int shopId) {
        return shops.stream().filter(s -> s.getShopId() == shopId).findFirst().orElse(null);
    }

    public List<Shop> listAllShops() { return Collections.unmodifiableList(shops); }

    public String getMallReport() {
        return "Mall Report :: " + mallName + " | Shops: " + shops.size() + " | Location: " + location;
    }

    public int getMallId() { return mallId; }
    public String getMallName() { return mallName; }
    public String getLocation() { return location; }
    public Admin getAdmin() { return admin; }
}
