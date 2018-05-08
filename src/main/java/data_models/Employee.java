package data_models;

import data_loader.data_access_object.PositionDao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Employee extends Person {

	private UUID employeeId;
	private char isDeleted;
	private UUID positionId;
    private List<WorkingDay> workingDays;

	public Employee(){
	    super();
		this.employeeId = UUID.randomUUID();
		this.initWorkingDays();
	}
	public Employee(UUID personId){
	    super(personId);
		this.initWorkingDays();
	}


	public Employee(UUID employeeId, char isDeleted, UUID positionId) {
		this.employeeId = employeeId;
		this.isDeleted = isDeleted;
		this.positionId = positionId;
		this.initWorkingDays();
	}

	public List<WorkingDay> getWorkingDays() {
		return this.workingDays;
	}

	public void setWorkingDays(List<WorkingDay> workingDays) {
		this.workingDays = workingDays;
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

	private void initWorkingDays(){
		workingDays = new ArrayList<>();
		workingDays.add(new WorkingDay("Montag"));
		workingDays.add(new WorkingDay("Dienstag"));
		workingDays.add(new WorkingDay("Mittwoch"));
		workingDays.add(new WorkingDay("Donnerstag"));
		workingDays.add(new WorkingDay("Freitag"));
		workingDays.add(new WorkingDay("Samstag"));
		workingDays.add(new WorkingDay("Sonntag"));
	}
}