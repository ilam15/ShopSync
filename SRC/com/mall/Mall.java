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

    // File path for storing mall data
    private static final String DATA_DIR = "DB";
    private static final String MALL_FILE = DATA_DIR + "/mall.txt";

    public Mall(int mallId, String mallName, String location, Admin admin) {
        this.mallId = mallId;
        this.mallName = mallName;
        this.location = location;
        this.admin = admin;
        saveMallData();
    }

    public void addShop(Shop shop) {
        shops.add(shop);
        saveMallData();
    }

    public void removeShop(int shopId) {
        shops.removeIf(s -> s.getShopId() == shopId);
        saveMallData();
    }

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

    // Save mall data to file
    private void saveMallData() {
        try {
            java.nio.file.Path dirPath = java.nio.file.Paths.get(DATA_DIR);
            if (!java.nio.file.Files.exists(dirPath)) {
                java.nio.file.Files.createDirectories(dirPath);
            }
            java.nio.file.Path filePath = java.nio.file.Paths.get(MALL_FILE);
            String data = mallId + "," + mallName + "," + location + "\n";
            java.nio.file.Files.write(filePath, data.getBytes(), java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.WRITE);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
