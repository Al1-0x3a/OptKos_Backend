package manager;

import data_loader.data_access_object.AppointmentDao;
import data_loader.data_access_object.ServiceCounterDao;
import data_loader.data_access_object.WorkingWeekDao;
import data_models.Appointment;
import data_models.ServiceCounter;
import data_models.WorkingDay;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticManager {
private int countEmployees = 0;

    public ServiceCounter getAllServiceCounter(String serviceId, String startTime, String endTime) {
        return ServiceCounterDao.getAllServiceCounter(serviceId, LocalDateTime.parse(startTime),
                LocalDateTime.parse(endTime));
    }

    public ArrayList<long[]> getWorktimeStatistics(String start, String end){
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        ArrayList<long[]> result = new ArrayList<>();
        long[] workingDays = getAllWorkingTimeSumForEachDay();
       result.add(workingDays);
        long[] appointments = getAverageActualWorkingTime(startDate, endDate);
        result.add(appointments);

        return result;
    }

    public long[] getAllWorkingTimeSumForEachDay(){
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
            workingWeek[i] /= countEmployees;
        }
        return workingWeek;
    }

    public long[] getAverageActualWorkingTime(LocalDate start, LocalDate end){
        List<Appointment> appointments = AppointmentDao.getAllAppointmentsInTimespan(start, end);

        /*Creating a list for each weekday*/
        ArrayList<ArrayList<Appointment>>appointmentsInWeek = new ArrayList<>();
        for(int i = 0; i<7; i++){
            final int index = i+1;
            ArrayList<Appointment> app = ((ArrayList) appointments.stream()
                    .filter(a -> a.getStartTime().getDayOfWeek().getValue() == index).collect(Collectors.toList()));

           ArrayList<Integer> sumOfWorkingtimeForEachDay = new ArrayList<>();



            appointmentsInWeek.add(app);
           appointments.removeAll(app);
        }

/*        *//*Calculate average Appointment occupied time*//*
        long[] actualWorkingTime = new long[7];
        int index = 0;
        for (ArrayList<Appointment> appointmentListDay :
                appointmentsInWeek) {
            for (Appointment appointment :
                    appointmentListDay) {
                actualWorkingTime[index] += appointment.getAppointmentDuration();
            }
            actualWorkingTime[index] /= 4;
            index++;
        }*/


        ArrayList<Long> averageList = new ArrayList<>();
        ArrayList<Long> magic = new ArrayList<>();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        for (ArrayList<Appointment> app :
                appointmentsInWeek) {

            while (!app.isEmpty()) {
                ArrayList<Appointment> sameDate = (ArrayList) app.stream().filter(a ->
                        a.getStartTime().getDayOfYear() == app.get(0).getStartTime().getDayOfYear())
                        .collect(Collectors.toList());
                long tmp = 0;
                for (Appointment a :
                        sameDate) {
                    tmp += a.getAppointmentDuration();
                }
                magic.add(tmp);
                app.removeAll(sameDate);
            }

            long tmp = 0;
            for (long l :
                    magic) {
                tmp += l;
            }
            averageList.add(tmp/magic.size());
        }

        /*make List to Array*/
        long[] averageArray = new long[averageList.size()];
        for(int i = 0; i<averageArray.length; i++){
            averageArray[i] = averageList.get(i);
        }
        return averageArray;
    }
}
