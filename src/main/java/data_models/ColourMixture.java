package data_models;

import java.util.UUID;

public class ColourMixture {

	private String colourMixtureId;
	private String colourId;
	private String customerId;
	private int mixingRatio;

	public ColourMixture(){
		this.colourMixtureId= UUID.randomUUID().toString();
	}

	public ColourMixture(String colourMixtureId ,String colourId, String customerId, int mixingRatio) {
		this.colourMixtureId = colourMixtureId;
		this.colourId = colourId;
		this.customerId = customerId;
		this.mixingRatio = mixingRatio;
	}

	public String getColourMixtureId() { return colourMixtureId; }

	public String getColourId() {
		return colourId;
	}

	public void setColourId(String colourId) {
		this.colourId = colourId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public int getMixingRatio() {
		return mixingRatio;
	}

	public void setMixingRatio(int mixingRatio) {
		this.mixingRatio = mixingRatio;
	}
}