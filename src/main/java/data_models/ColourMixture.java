package data_models;

import java.util.UUID;

public class ColourMixture {

	private UUID colourMixtureId;
	private UUID colourId;
	private UUID customerId;
	private int mixingRatio;

	public ColourMixture(){
		this.colourMixtureId= UUID.randomUUID();
	}

	public ColourMixture(UUID colourMixtureId ,UUID colourId, UUID customerId, int mixingRatio) {
		this.colourMixtureId = colourMixtureId;
		this.colourId = colourId;
		this.customerId = customerId;
		this.mixingRatio = mixingRatio;
	}

	public UUID getColourMixtureId() { return colourMixtureId; }

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