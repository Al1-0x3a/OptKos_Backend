package data_models;

import java.util.UUID;

public class Colour {

	private String brightness;
	private UUID colourId;
	private String hue;
	private String manufacturer;
	private String targetColor;

	public Colour(){

	}

	public Colour(String brightness, String hue, String manufacturer, String targetColor) {
		this.brightness = brightness;
		this.hue = hue;
		this.manufacturer = manufacturer;
		this.targetColor = targetColor;
	}

	public String getBrightness() {
		return brightness;
	}

	public void setBrightness(String brightness) {
		this.brightness = brightness;
	}

	public UUID getColourId() {
		return colourId;
	}

	public void setColourId(UUID colourId) {
		this.colourId = colourId;
	}

	public String getHue() {
		return hue;
	}

	public void setHue(String hue) {
		this.hue = hue;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getTargetColor() {
		return targetColor;
	}

	public void setTargetColor(String targetColor) {
		this.targetColor = targetColor;
	}
}