package manager;

import data_loader.data_access_object.ServiceCounterDao;
import data_models.ServiceCounter;

import java.time.LocalDateTime;
import java.util.List;

public class StatisticManager {

    public List<ServiceCounter> getAllServiceCounter(LocalDateTime starttime, LocalDateTime endtime) {
        return ServiceCounterDao.getAllServiceCounter(starttime, endtime);
    }
}
