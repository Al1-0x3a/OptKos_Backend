package client_api;

import data_models.*;
import manager.StatisticManager;

import javax.jws.WebService;
import java.util.List;

@SuppressWarnings("ValidExternallyBoundObject")
@WebService(endpointInterface = "client_api.IStatisticApi")
public class StatisticApi implements IStatisticApi {
    private final StatisticManager statisticManager;

    public StatisticApi() {
        statisticManager = new StatisticManager();
    }
    
    @Override
    public ServiceCounter getAllServiceCounter(String serviceId, String startTime, String endTime) {
        return statisticManager.getAllServiceCounter(serviceId, startTime, endTime);
    }

    @Override
    public List<long[]> getWorkTimeStatistic(String start, String end) {
        return statisticManager.getWorktimeStatistics(start, end);
    }
}
