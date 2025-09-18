package com.discount;

import java.util.*;
import com.product.Product;

public class Discount {
    private String discountId;
    private String description;
    private double percentage;
    private Date startDate;
    private Date endDate;

    // Keep original prices so we can remove discount safely
    private Map<Integer, Double> originalPriceMap = new HashMap<>();

    public Discount(String discountId, String description, double percentage, Date startDate, Date endDate) {
        this.discountId = discountId;
        this.description = description;
        this.percentage = percentage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private boolean isActive() {
        Date now = new Date();
        return (startDate == null || !now.before(startDate)) &&
               (endDate == null || !now.after(endDate));
    }

    public boolean applyDiscount(Product product) {
        if (product == null || percentage <= 0 || !isActive()) return false;
        if (!originalPriceMap.containsKey(product.getProductId())) {
            originalPriceMap.put(product.getProductId(), product.getPrice());
        }
        product.applyDiscount(percentage);
        return true;
    }

    public boolean removeDiscount(Product product) {
        if (product == null) return false;
        Double original = originalPriceMap.remove(product.getProductId());
        if (original != null) {
            product.setPrice(original);
            return true;
        }
        return false;
    }

    public String getDiscountDetails() {
        return "Discount #" + discountId + " | " + description + " | " + percentage + "%";
    }

    public String getDiscountId() { return discountId; }
    public double getPercentage() { return percentage; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
}
