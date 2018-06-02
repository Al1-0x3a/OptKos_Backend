package client_api;

import data_models.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.time.LocalDateTime;
import java.util.List;

@WebService
public interface IStatisticApi {
    @WebMethod
    List<ServiceCounter> getAllServiceCounter(LocalDateTime starttime, LocalDateTime endtime);
}
