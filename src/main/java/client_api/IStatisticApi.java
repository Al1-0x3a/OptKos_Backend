package client_api;

import data_models.*;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface IStatisticApi {
    @WebMethod
    ServiceCounter getAllServiceCounter(String serviceId, String startTime, String endTime);
   @WebMethod
    WorktimeStatisticModel getWorkTimeStatistic(String start, String end);
}
