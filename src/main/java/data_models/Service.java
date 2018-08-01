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

import data_loader.data_access_object.ServiceEmployeeDurationDao;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

public class Service {
	private String serviceId;
	private String name;
	private String description;
	private BigDecimal price;
	private Duration durationPlanned;
	private Duration durationAverage;
	private String isDeleted;
	private List<ServiceEmployeeDuration> sedList;

	public Service(){
		this.serviceId = UUID.randomUUID().toString();
		sedList = ServiceEmployeeDurationDao.getSedListWithOnlyEmployees(this.serviceId);
		isDeleted="";
	}

	public Service(String serviceId, String name, String description, BigDecimal price,
	Duration durationPlanned, Duration durationAverage, String isDeleted) {
		this.serviceId = serviceId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.durationPlanned = durationPlanned;
		this.durationAverage = durationAverage;
		this.isDeleted = isDeleted;
	}


	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Duration getDurationPlanned() {
		return durationPlanned;
	}

	public void setDurationPlanned(Duration durationPlanned) {
		this.durationPlanned = durationPlanned;
	}

	public Duration getDurationAverage() {
		return durationAverage;
	}

	public void setDurationAverage(Duration durationAverage) {
		this.durationAverage = durationAverage;
	}

	public String getIsDeleted(){ return isDeleted; }

	public void setIsDeleted(String isDeleted) { this.isDeleted = isDeleted; }

	public List<ServiceEmployeeDuration> getSedList() {
		return sedList;
	}

	public void setSedList(List<ServiceEmployeeDuration> sedList) {
		this.sedList = sedList;
	}
}