package data_models;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Employee extends Person {

	private String employeeId;
	private char isDeleted;
	private Position position;
	private String colour;
    private List<WorkingDay> workingDays;

	public Employee(){
	    super();
		this.initWorkingDays();
	}
	public Employee(String personId){
		super(personId);
		this.employeeId = UUID.randomUUID().toString();
		this.initWorkingDays();
		this.position = new Position().setDefaultValues();
	}


	public Employee(String employeeId, char isDeleted, Position position, String displayColor) {
		this.employeeId = employeeId;
		this.isDeleted = isDeleted;
		this.position = position;
		this.colour = displayColor;
		this.initWorkingDays();
	}
	public void setColour(String colour){this.colour =colour;}

	public String getColour(){return this.colour;}
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

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "Employee{" +
				super.toString() +
				"employeeId=" + employeeId +
				", isDeleted=" + isDeleted +
				", positionId=" + position +
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