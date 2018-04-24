package data_models;

import java.util.UUID;

public class Position {

	private String description;
	private String name;
	private String note;
	private UUID positionId;

	public Position(){
	    this.positionId = UUID.randomUUID();

	}

	public Position(UUID positionId, String name, String description, String note) {
		this.positionId = positionId;
		this.description = description;
		this.name = name;
		this.note = note;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public UUID getPositionId() {
		return positionId;
	}

}