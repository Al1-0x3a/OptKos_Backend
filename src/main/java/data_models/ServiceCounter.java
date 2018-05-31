package data_models;

import java.time.LocalDateTime;
import java.util.UUID;

public class ServiceCounter {

    private String serviceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String serviceCounter;
    private String serviceName;

    public ServiceCounter() {}

    public ServiceCounter(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceId(){return serviceId;}
    public void setServiceId(String serviceId) {this.serviceId = serviceId;}

    public LocalDateTime getStartTime(){return startTime;}
    public void setStartTime(LocalDateTime startTime){this.startTime = startTime;}

    public LocalDateTime getEndTime(){return endTime;}
    public void setEndTime(LocalDateTime endTime){this.endTime = endTime;}

    public String getServiceCounter(){return serviceCounter;}
    public void setServiceCounter(String serviceCounter){this.serviceCounter = serviceCounter;}

    public String getServiceName(){return serviceName;}
    public void setServiceName(String serviceName){this.serviceName = serviceName;}
}
