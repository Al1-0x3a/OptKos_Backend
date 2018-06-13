package manager;

import data_loader.data_access_object.AppointmentDao;
import data_loader.data_access_object.ServiceCounterDao;
import data_loader.data_access_object.WorkingWeekDao;
import data_models.Appointment;
import data_models.ServiceCounter;
import data_models.WorkingDay;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticManager {

    public ServiceCounter getAllServiceCounter(String serviceId, String startTime, String endTime) {
        return ServiceCounterDao.getAllServiceCounter(serviceId, LocalDateTime.parse(startTime),
                LocalDateTime.parse(endTime));
    }

    public void getWorktimeStatistics(String start, String end){
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        int[] workingDays = getAllWorkingTimeSumForEachDay();
        int[] appointments = getAverageActualWorkingTime(startDate, endDate);


    }

    public int[] getAllWorkingTimeSumForEachDay(){
        ArrayList<WorkingDay> workingDays;
        workingDays = (ArrayList<WorkingDay>) WorkingWeekDao.getAllWorkingDaysFromDb();

        /*Count employees by getting the count of Mondays(to avoid another db query)*/
        List<WorkingDay> wd = workingDays.stream().filter(w -> w.getDay().equals("Montag"))
                .collect(Collectors.toList());
        int countEmployees = wd.size();

        /*Calculate average Workingtime*/
        int[] workingWeek = new int[7];
        for(int i = 0; i<workingWeek.length; i++){
            for (WorkingDay day :
                    workingDays) {
                if(WorkingWeekDao.getDayIndex(day.getDay())==i)
                    workingWeek[i] += day.getWorkingTimeInMinutes();
            }
            workingWeek[i] /= countEmployees;
        }

        return workingWeek;
    }

    public int[] getAverageActualWorkingTime(LocalDate start, LocalDate end){
        List<Appointment> appointments = AppointmentDao.getAllAppointmentsInTimespan(start, end);

        /*Creating a list for each weekday*/
        ArrayList<ArrayList<Appointment>>appointmentsInWeek = new ArrayList<>();
        for(int i = 0; i<7; i++){
            final int index = i;
            ArrayList<Appointment> app = ((ArrayList) appointments.stream()
                    .filter(a -> a.getStartTime().getDayOfWeek().getValue() == index).collect(Collectors.toList()));
            appointmentsInWeek.add(app);
        }


        return null;
    }

}
