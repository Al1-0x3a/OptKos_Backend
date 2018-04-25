package data_models;

public class WorkingWeek {

	private WorkingDay[] workingDays;

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

}