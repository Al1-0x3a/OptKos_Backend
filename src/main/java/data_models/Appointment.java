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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Appointment {
	private String appointmentId;
	private LocalDateTime endTime;
	private LocalDateTime endTimeActual;
	private LocalDateTime startTime;
	private LocalDateTime startTimeActual;
	private AppointmentType appointmentType;
	private String employeeid;
	private Customer customer;
	private Service service;

	public Appointment(){
	}
	public Appointment (String UUID){
		appointmentId = UUID;
	}
	public Appointment(String appointmentId, LocalDateTime endTime,
					   LocalDateTime startTime, String employeeid) {
		this.appointmentId = appointmentId;
		this.endTime = endTime;
		this.startTime = startTime;
		this.employeeid = employeeid;
	}

	public Appointment(String appointmentId, LocalDateTime endTime, LocalDateTime startTime, String employeeid,
					   LocalDateTime endTimeActual, LocalDateTime startTimeActual) {
		this.appointmentId = appointmentId;
		this.endTime = endTime;
		this.endTimeActual = endTimeActual;
		this.startTime = startTime;
		this.startTimeActual = startTimeActual;
		this.employeeid = employeeid;
	}

	public String getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		if(this.appointmentId == null)
			this.appointmentId = appointmentId;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public LocalDateTime getEndTimeActual() {
		return endTimeActual;
	}

	public void setEndTimeActual(LocalDateTime endTimeActual) {
		this.endTimeActual = endTimeActual;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getStartTimeActual() {
		return startTimeActual;
	}

	public void setStartTimeActual(LocalDateTime startTimeActual) {
		this.startTimeActual = startTimeActual;
	}

	public AppointmentType getAppointmentType() {
		return this.appointmentType;
	}

	public void setAppointmentType(AppointmentType appointmentType) {
		this.appointmentType = appointmentType;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}


	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public long getAppointmentDuration(){
		if(this.startTimeActual != null){
			return this.startTimeActual.until(endTimeActual, ChronoUnit.MINUTES);
		}
		else {
			return this.startTime.until(endTime, ChronoUnit.MINUTES);
		}
	}
}