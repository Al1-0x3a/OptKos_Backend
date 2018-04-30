package data_models;

import data_loader.data_access_object.PositionDao;

import java.util.UUID;

public class Employee extends Person {

	private UUID employeeId;
	private char isDeleted;
	private UUID positionId;
	private WorkingWeek workingWeek;

	public Employee(){
	    super();
		this.employeeId = UUID.randomUUID();
		this.workingWeek = new WorkingWeek();
	}

	public Employee(UUID employeeId, char isDeleted, UUID positionId) {
		this.employeeId = employeeId;
		this.isDeleted = isDeleted;
		this.positionId = positionId;
		this.workingWeek = new WorkingWeek();
	}

	public WorkingWeek getWorkingWeek() {
		return workingWeek;
	}

	public void setWorkingWeek(WorkingWeek workingWeek) {
		this.workingWeek = workingWeek;
	}

	public UUID getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(UUID employeeId) {
		this.employeeId = employeeId;
	}

	public char getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(char isDeleted) {
		this.isDeleted = isDeleted;
	}

	public UUID getPositionId() {
		return positionId;
	}

	public void setPositionId(UUID positionId) {
		this.positionId = positionId;
	}

	public Position getPosition(){
		return PositionDao.getPositionByPositionId(this.positionId);
	}

	@Override
	public String toString() {
		return "Employee{" +
                super.toString() +
				"employeeId=" + employeeId +
				", isDeleted=" + isDeleted +
				", positionId=" + positionId +
				'}';
	}
}