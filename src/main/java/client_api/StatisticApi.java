package client_api;

import data_loader.data_access_object.*;
import data_models.*;
import manager.StatisticManager;

import javax.jws.WebService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("ValidExternallyBoundObject")
@WebService(endpointInterface = "client_api.IAdministrativeApi")
public class StatisticApi implements IStatisticApi {
    private final StatisticManager statisticManager;

    public StatisticApi() {
        statisticManager = new StatisticManager();
    }


    @Override
    public List<ServiceCounter> getAllServiceCounter(LocalDateTime starttime, LocalDateTime endtime) {
        return statisticManager.getAllServiceCounter(starttime, endtime);
    }
}
