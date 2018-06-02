package client_api;

import data_models.*;
import manager.StatisticManager;

import javax.jws.WebService;
import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings("ValidExternallyBoundObject")
@WebService(endpointInterface = "client_api.IStatisticApi")
public class StatisticApi implements IStatisticApi {
    private final StatisticManager statisticManager;

    public StatisticApi() {
        statisticManager = new StatisticManager();
    }
    
    @Override
    public List<ServiceCounter> getAllServiceCounter(LocalDateTime startTime, LocalDateTime endTime) {
        return statisticManager.getAllServiceCounter(startTime, endTime);
    }
}
