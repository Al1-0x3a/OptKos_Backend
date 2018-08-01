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

public class CustomerColour {

	private String customerColourId;
	private int contentWhite;
	private String customerId;
	private int exposureTime;
	private String natural;
	private double oxidation;
	private String result;

	public CustomerColour(){
		this.customerColourId = UUID.randomUUID().toString();
	}

	public CustomerColour(String customerColourId, int contentWhite, String customerId, int exposureTime,
	String natural, Double oxidation, String result) {
		this.customerColourId = customerColourId;
		this.contentWhite = contentWhite;
		this.customerId = customerId;
		this.exposureTime = exposureTime;
		this.natural = natural;
		this.oxidation = oxidation;
		this.result = result;
	}

	public String getCustomerColourId() {
		return customerColourId;
	}

	public void setCustomerColourId(String customerColourId) {
		this.customerColourId = customerColourId; }

	public int getContentWhite() {
		return contentWhite;
	}

	public void setContentWhite(int contentWhite) {
		this.contentWhite = contentWhite;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public int getExposureTime() {
		return exposureTime;
	}

	public void setExposureTime(int exposureTime) {
		this.exposureTime = exposureTime;
	}

	public String getNatural() {
		return natural;
	}

	public void setNatural(String natural) {
		this.natural = natural;
	}

	public Double getOxidation() {
		return oxidation;
	}

	public void setOxidation(Double oxidation) {
		this.oxidation = oxidation;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}