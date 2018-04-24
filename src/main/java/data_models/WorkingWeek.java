package data_models;

import java.time.LocalTime;

public class WorkingWeek {




	private WorkingDay[] workingDays;
	public Employee Employee;

	public WorkingWeek(){

	}

	public WorkingDay[] getWorkingDays() {
		return workingDays;
	}

	public WorkingDay getWorkingDayByIndex(int index){
		return workingDays[index];
	}

	public void setWorkingDays(WorkingDay[] workingDays) {
		this.workingDays = workingDays;
	}

	public data_models.Employee getEmployee() {
		return Employee;
	}

	public void setEmployee(data_models.Employee employee) {
		Employee = employee;
	}
}