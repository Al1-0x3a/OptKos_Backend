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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer extends Person {
	private String annotation;
	private String customerId;
	private boolean problemCustomer;
	private double timefactor;
	private CustomerCategory customerCategory;
	private CustomerColour customerColour;
	private List<ColourMixture> colourMixtureList = new ArrayList<>();

	public Customer() {
		super();
		this.customerId = UUID.randomUUID().toString();
	}

	public Customer(String personId) {
	    super(personId);
	    customerId = UUID.randomUUID().toString();
	}

    public Customer(String personId, String customerId) {
        super(personId);
        this.customerId = customerId;
    }
	public Customer( String customerId, double timefactor, String annotation, char problemCustomer, String personId) {
		super(personId);
		this.annotation = annotation;
		this.customerId = customerId;
		if(problemCustomer == '0')
			this.problemCustomer = false;
		if(problemCustomer == '1')
			this.problemCustomer = true;
		this.timefactor = timefactor;
	}


    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

    public boolean isProblemCustomer() {
        return problemCustomer;
    }

    public void setProblemCustomer(boolean problemCustomer) {
        this.problemCustomer = problemCustomer;
    }

    public double getTimefactor() {
        return timefactor;
    }

    public void setTimefactor(double timefactor) {
        this.timefactor = timefactor;
    }

    public CustomerCategory getCustomerCategory() {
        return customerCategory;
    }

    public void setCustomerCategory(CustomerCategory customerCategory) {
        this.customerCategory = customerCategory;
    }

	public CustomerColour getCustomerColour() {
		return customerColour;
	}

	public void setCustomerColour(CustomerColour customerColour) {
		this.customerColour = customerColour;
	}

	public List<ColourMixture> getColourMixtureList() {
		return colourMixtureList;
	}

	public void setColourMixtureList(List<ColourMixture> colourMixtureList) {
		this.colourMixtureList = colourMixtureList;
	}
}
