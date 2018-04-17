package data_models;

import java.util.UUID;

public class Employee extends Person {

	private int employeeId;
	private char isDeleted;
	private int positionId;

	public Employee(){

	}

	public Employee(int employeeId, char isDeleted, int positionId) {
		this.employeeId = employeeId;
		this.isDeleted = isDeleted;
		this.positionId = positionId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public char getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(char isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
}