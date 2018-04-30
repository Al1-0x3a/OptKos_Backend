package data_models;


public class WorkingWeek {

	private WorkingDay[] workingDays;

	public WorkingWeek(){
		workingDays = new WorkingDay[7];
		workingDays[0] = new WorkingDay("Montag");
		workingDays[1] = new WorkingDay("Dienstag");
		workingDays[2] = new WorkingDay("Mittwoch");
		workingDays[3] = new WorkingDay("Donnerstag");
		workingDays[4] = new WorkingDay("Freitag");
		workingDays[5] = new WorkingDay("Samstag");
		workingDays[6] = new WorkingDay("Sonntag");
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