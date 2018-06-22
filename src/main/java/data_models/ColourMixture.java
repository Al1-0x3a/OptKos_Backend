package data_models;

import java.util.UUID;

public class ColourMixture {

	private String colourMixtureId;
	private String customerId;
	private int mixingRatio;
	private Colour colour;

	public ColourMixture(){
		this.colourMixtureId= UUID.randomUUID().toString();
	}

	public ColourMixture(String colourMixtureId ,String colourId, String customerId, int mixingRatio, Colour colour) {
		this.colourMixtureId = colourMixtureId;
		this.customerId = customerId;
		this.mixingRatio = mixingRatio;
		this.colour = colour;
	}

	public String getColourMixtureId() { return colourMixtureId; }

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
	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}

}