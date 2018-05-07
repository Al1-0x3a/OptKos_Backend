package data_models;


import java.util.ArrayList;
import java.util.List;

public class WorkingWeek {

	private List<WorkingDay> workingDays;

	public WorkingWeek(){
		workingDays = new ArrayList<>();
		workingDays.add(new WorkingDay("Montag"));
		workingDays.add(new WorkingDay("Dienstag"));
		workingDays.add(new WorkingDay("Mittwoch"));
		workingDays.add(new WorkingDay("Donnerstag"));
		workingDays.add(new WorkingDay("Freitag"));
		workingDays.add(new WorkingDay("Samstag"));
		workingDays.add(new WorkingDay("Sonntag"));
	}

	public List<WorkingDay> getWorkingDays() {
		return workingDays;
	}

	public WorkingDay getWorkingDayByIndex(int index){
		return workingDays.get(index);
	}

	public void setWorkingDays(List<WorkingDay> workingDays) {
		this.workingDays = workingDays;
	}

}