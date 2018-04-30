package data_models;

import java.util.UUID;

public class Hairstyle {

	private UUID customerId;
	private UUID hairstyleId;
	private int length;

	public Hairstyle(){

	}

	public Hairstyle(UUID customerId, int length) {
		this.customerId = customerId;
		this.length = length;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}

	public UUID getHairstyleId() {
		return hairstyleId;
	}

	public void setHairstyleId(UUID hairstyleId) {
		this.hairstyleId = hairstyleId;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}