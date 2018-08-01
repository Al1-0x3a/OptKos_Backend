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

package manager;

import data_loader.data_access_object.AppointmentDao;
import data_loader.data_access_object.ServiceCounterDao;
import data_loader.data_access_object.WorkingWeekDao;
import data_models.Appointment;
import data_models.ServiceCounter;
import data_models.WorkingDay;
import data_models.WorktimeStatisticModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticManager {

    public ServiceCounter getAllServiceCounter(String serviceId, String startTime, String endTime) {
        return ServiceCounterDao.getAllServiceCounter(serviceId, LocalDateTime.parse(startTime),
                LocalDateTime.parse(endTime));
    }

    public WorktimeStatisticModel getWorktimeStatistics(String start, String end){
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        long[] workingDays = getAllWorkingTimeSumForEachDay();
        return getAverageActualWorkingTime(startDate, endDate, workingDays);
    }

    public long[] getAllWorkingTimeSumForEachDay(){
        int countEmployees = 0;
        ArrayList<WorkingDay> workingDays;
        workingDays = (ArrayList<WorkingDay>) WorkingWeekDao.getAllWorkingDaysFromDb();

        /*Count employees by getting the count of Mondays(to avoid another db query)*/
        List<WorkingDay> wd = workingDays.stream().filter(w -> w.getDay().equals("Montag"))
                .collect(Collectors.toList());
        countEmployees = wd.size();

        /*Calculate average Workingtime*/
        long[] workingWeek = new long[7];
        for(int i = 0; i<workingWeek.length; i++){
            for (WorkingDay day :
                    workingDays) {
                if(WorkingWeekDao.getDayIndex(day.getDay())==i)
                    workingWeek[i] += day.getWorkingTimeInMinutes();
            }
        }
        return workingWeek;
    }

    public WorktimeStatisticModel getAverageActualWorkingTime(LocalDate start, LocalDate end, long[] workingDays){
        List<Appointment> appointments = AppointmentDao.getAllAppointmentsInTimespan(start, end);
        long[][] result = new long[7][2];

        /*Creating a list for each weekday*/
        ArrayList<ArrayList<Appointment>>appointmentsInWeek = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            final int index = i+1;

            ArrayList<Appointment> app = ((ArrayList) appointments.stream()
                    .filter(a -> a.getStartTime().getDayOfWeek().getValue() == index).collect(Collectors.toList()));

            if(index == 7){
for (Appointment a: app)
                System.out.println(a.getStartTime().getDayOfWeek().getValue());
            }
            appointmentsInWeek.add(app);
            appointments.removeAll(app);
        }

        short index = 0;
        for (ArrayList<Appointment> app :
                appointmentsInWeek) {

            while (!app.isEmpty()) {
                ArrayList<Appointment> sameDate = (ArrayList) app.stream().filter(a ->
                        a.getStartTime().getDayOfYear() == app.get(0).getStartTime().getDayOfYear())
                        .collect(Collectors.toList());
                for (Appointment a :
                        sameDate) {
                    result[index][1] += a.getAppointmentDuration();
                }
                result[index][0] += workingDays[index];
                app.removeAll(sameDate);
            }
            index++;

        }

        return new WorktimeStatisticModel(result);
    }
}
