package data_models;


public class WorkingWeek {

	private WorkingDay[] workingDays;

	public WorkingWeek(){
		workingDays = new WorkingDay[6];
		for(int i = 0; i<6; i++){
			workingDays[i] = new WorkingDay();
		}
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