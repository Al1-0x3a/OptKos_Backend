package data_models;

import java.util.UUID;

public class Position {

	private String description;
	private String name;
	private String note;
	private String positionId;

	public Position(){
	}

	public Position(String positionId){
		this.positionId = positionId;
	}
	public Position(String positionId, String name, String description, String note) {
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

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
}