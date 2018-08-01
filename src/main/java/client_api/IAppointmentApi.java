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

import data_models.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface IAppointmentApi {
    @WebMethod
    Appointment getNewAppointment();
    @WebMethod
    Appointment getAppointmentById(String uuid);
    @WebMethod
    Appointment getPreviousAppointment(String customerID);
    @WebMethod
    Appointment getNextAppointment(String customerID);
    @WebMethod
    List<AppointmentListItem> getAppointmentsByCalendarWeek(String ldt);
    @WebMethod
    boolean createAppointment(Appointment appointment);
    @WebMethod
    boolean updateAppointment(Appointment appointment);
    @WebMethod
    boolean deleteAppointment(String uuid);
    @WebMethod
    boolean isSlotFree(Appointment appointment, Employee employee);
    @WebMethod
    List<AppointmentSuggestion> findSuggestions(AppointmentSuggestion.Strategy strategy,
                                                AppointmentSuggestion suggestion);
    @WebMethod
    List<AppointmentType> getAllAppointmentTypes();
    @WebMethod
    void appointmentStart(Appointment appointment);
    @WebMethod
    void appointmentEnd(Appointment appointment);
    @WebMethod
    void calculateAverage();
}