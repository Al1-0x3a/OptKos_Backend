package client_api;

import data_models.*;
import manager.StatisticManager;

import javax.jws.WebService;

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
    public WorktimeStatisticModel getWorkTimeStatistic(String start, String end) {
        return statisticManager.getWorktimeStatistics(start, end);
    }
}
