package data_models;

public class Employee extends Person {

	private UUID employeeId;
	private char isDeleted;
	private UUID positionId;

	public Employee(){

	}

	public Employee(UUID employeeId, char isDeleted, UUID positionId) {
		this.employeeId = employeeId;
		this.isDeleted = isDeleted;
		this.positionId = positionId;
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