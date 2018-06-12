package manager;

import data_loader.data_access_object.ServiceCounterDao;
import data_loader.data_access_object.WorkingWeekDao;
import data_models.ServiceCounter;
import data_models.WorkingDay;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class StatisticManager {

    public ServiceCounter getAllServiceCounter(String serviceId, String startTime, String endTime) {
        return ServiceCounterDao.getAllServiceCounter(serviceId, LocalDateTime.parse(startTime),
                LocalDateTime.parse(endTime));
    }

    public void getWorktimeStatistics(String start, String end){
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        ArrayList<WorkingDay> workingDays = (ArrayList<WorkingDay>)
                WorkingWeekDao.getAllWorkingDaysInTimespan(startDate, endDate);
    }
}
