package data_models;

import java.util.UUID;

public class CustomerCategory {

    private String customerCategoryId;
    private String description;
    private String name;
    private int timeBonus;
    private double timeFactor;

    public CustomerCategory() {
        this.customerCategoryId = UUID.randomUUID().toString();
    }

    public CustomerCategory(String customerCategoryId, String name, String description, int timeBonus, double timeFactor) {
        this.customerCategoryId = customerCategoryId;
        this.description = description;
        this.name = name;
        this.timeBonus = timeBonus;
        this.timeFactor = timeFactor;
    }

    public String getCustomerCategoryId() {
        return customerCategoryId;
    }

    public void setCustomerCategoryId(String customerCategoryId) {
        this.customerCategoryId = customerCategoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeBonus() {
        return timeBonus;
    }

    public void setTimeBonus(int timeBonus) {
        this.timeBonus = timeBonus;
    }

    public double getTimeFactor() {
        return timeFactor;
    }

    public void setTimeFactor(double timeFactor) {
        this.timeFactor = timeFactor;
    }
}