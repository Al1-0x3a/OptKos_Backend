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

import java.time.Duration;

public class ServiceEmployeeDuration {

	private Duration durationPlanned;
	private Duration durationAverage;
	private String employeeId;
	private String serviceId;
	private String firstName, lastName;

	public ServiceEmployeeDuration(){

	}

	public ServiceEmployeeDuration(Duration durationPlanned, Duration durationAverage, String employeeId,
								   String serviceId, String lastName, String firstName) {
		this.durationPlanned = durationPlanned;
		this.durationAverage = durationAverage;
		this.employeeId = employeeId;
		this.serviceId = serviceId;
		this.firstName = firstName;
		this.lastName = lastName;
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

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}