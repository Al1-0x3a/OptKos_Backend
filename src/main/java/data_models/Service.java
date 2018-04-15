package data_models;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

public class Service {

	private String description;
	private Duration durationCalculated;
	private Duration durationStatic;
	private String name;
	private BigDecimal price;
	private UUID serviceId;
	public ServiceList m_ServiceList;				// has to be generated

	public Service(){

	}

}