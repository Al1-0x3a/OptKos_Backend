/*
 * MIT License
 *
 * Copyright (c) 2018 Michael Szostak , Ali Kaya , Johannes Börmann, Nina Leveringhaus , Andre` Rehle , Felix Eisenmann , Patrick Handreck
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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