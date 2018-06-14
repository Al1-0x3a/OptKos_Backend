package client_api;

import data_models.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface IStatisticApi {
    @WebMethod
    ServiceCounter getAllServiceCounter(String serviceId, String startTime, String endTime);
   @WebMethod
    List<long[]> getWorkTimeStatistic(String start, String end);
}
