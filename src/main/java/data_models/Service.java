package data_models;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

public class Service {
	private UUID serviceId;
	private String name;
	private String description;
	private BigDecimal price;
	private Duration durationPlanned;
	private Duration durationAverage;

	public Service(){

	}

}