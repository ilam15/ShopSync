package com.mall;

import java.util.*;
import com.shop.Shop;

public class Mall {
    private int mallId;
    private String mallName;
    private String location;
    private List<Shop> shops;
    private Object mallDetails; // Placeholder for mall details

    public Mall(int mallId, String mallName, String location, Object mallDetails) {
        this.mallId = mallId;
        this.mallName = mallName;
        this.location = location;
        this.mallDetails = mallDetails;
        this.shops = new ArrayList<>();
    }

    public void addShop(Shop shop) {
        shops.add(shop);
    }

    public void removeShop(int shopId) {
        shops.removeIf(shop -> shop.getShopId() == shopId);
    }

    public Shop getShop(int shopId) {
        return shops.stream()
                .filter(shop -> shop.getShopId() == shopId)
                .findFirst()
                .orElse(null);
    }

    public List<Shop> listAllShops() {
        return new ArrayList<>(shops);
    }

    public String getMallReport() {
        StringBuilder report = new StringBuilder();
        report.append("Mall: ").append(mallName).append("\n");
        report.append("Location: ").append(location).append("\n");
        report.append("Total Shops: ").append(shops.size()).append("\n");
        for (Shop shop : shops) {
            report.append("- ").append(shop.getShopName()).append(" (").append(shop.getCategory()).append(")\n");
        }
        return report.toString();
    }

    public int getMallId() { return mallId; }
    public String getMallName() { return mallName; }
    public String getLocation() { return location; }
}
