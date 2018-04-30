package data_models;

import java.util.UUID;

public class ColourMixture {

	private UUID colourId;
	private UUID customerId;
	private int mixingRatio;

	public ColourMixture(){

	}

	public ColourMixture(UUID colourId, UUID customerId, int mixingRatio) {
		this.colourId = colourId;
		this.customerId = customerId;
		this.mixingRatio = mixingRatio;
	}

	public UUID getColourId() {
		return colourId;
	}

	public void setColourId(UUID colourId) {
		this.colourId = colourId;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}

	public int getMixingRatio() {
		return mixingRatio;
	}

	public void setMixingRatio(int mixingRatio) {
		this.mixingRatio = mixingRatio;
	}
}