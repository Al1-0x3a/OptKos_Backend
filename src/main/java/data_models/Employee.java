package data_models;

import data_loader.data_access_object.PositionDao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Employee extends Person {

	private String employeeId;
	private char isDeleted;
	private String positionId;
    private List<WorkingDay> workingDays;

	public Employee(){
	    super();
		this.initWorkingDays();
	}
	public Employee(String personId){
	    super(personId);
	    // wtf???
		this.employeeId = UUID.randomUUID().toString();
		this.initWorkingDays();
	}


	public Employee(String employeeId, char isDeleted, String positionId) {
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

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public char getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(char isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
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