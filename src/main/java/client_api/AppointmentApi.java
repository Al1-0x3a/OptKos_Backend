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

package client_api;

import data_loader.data_access_object.AppointmentDao;
import data_loader.data_access_object.AppointmentTypeDao;
import data_models.*;
import manager.AppointmentManager;

import javax.jws.WebService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("ValidExternallyBoundObject")
@WebService(endpointInterface = "client_api.IAppointmentApi")
public class AppointmentApi implements IAppointmentApi {
    long start;

    private final AppointmentManager appointmentManager;

    public AppointmentApi() {
        appointmentManager = new AppointmentManager();
    }

    @Override
    public Appointment getNewAppointment() {
        return new Appointment(UUID.randomUUID().toString());
    }

    @Override
    public Appointment getAppointmentById(String uuid) {
        return AppointmentDao.getAppointmentById(uuid);
    }

    @Override
    public Appointment getPreviousAppointment(String customerID) {
        return AppointmentDao.getPreviousAppointmentByCustomerID(customerID);
    }

    @Override
    public Appointment getNextAppointment(String customerID) {
        return AppointmentDao.getNextAppointmentByCustomerID(customerID);
    }

    @Override
    public List<AppointmentListItem> getAppointmentsByCalendarWeek(String ldt) {
        String text = "Get all Apoointments by Calenderweek from Api";
        this.time(true, text);
        List<AppointmentListItem> appointmentListItems = AppointmentDao.getAppointmentsByCalendarWeek(ldt);
        this.time(false, text);
        return appointmentListItems;
    }

    @Override
    public boolean createAppointment(Appointment appointment) {
        return AppointmentDao.createAppointment(appointment);
    }

    @Override
    public boolean updateAppointment(Appointment appointment) {
        return AppointmentDao.updateAppointment(appointment);
    }

    @Override
    public boolean deleteAppointment(String uuid) {
        return AppointmentDao.deleteAppointment(uuid);
    }

    @Override
    public boolean isSlotFree(Appointment appointment, Employee employee) {
        return appointmentManager.isFree(appointment, employee);
    }

    @Override
    public List<AppointmentSuggestion> findSuggestions(AppointmentSuggestion.Strategy strategy,
                                                       AppointmentSuggestion suggestion) {
        String text = "Get Appointment Suggestions from Api";
        this.time(true, text);
        List<AppointmentSuggestion> appointmentSuggestions = appointmentManager.findSuggestions(strategy, suggestion);
        this.time(false, text);
        return appointmentSuggestions;
    }

    @Override
    public List<AppointmentType> getAllAppointmentTypes() {
        return AppointmentTypeDao.getAllAppointmentTypesFromDb();
    }

    @Override
    public void appointmentStart(Appointment appointment) {
        appointment.setStartTimeActual(LocalDateTime.now());
        AppointmentDao.updateAppointment(appointment);
    }

    @Override
    public void appointmentEnd(Appointment appointment) {
        appointment.setEndTimeActual(LocalDateTime.now());
        AppointmentDao.updateAppointment(appointment);
    }

    public void time(boolean isStart, String text) {
        if (isStart) {
            this.start = System.nanoTime();
        } else {
            System.out.println(text +": " + (System.nanoTime() - this.start) / 1e6 + " ms");
        }
    }

    @Override
    public void calculateAverage() {
        appointmentManager.calculateAverage();
    }
}
