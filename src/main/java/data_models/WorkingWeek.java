package data_models;

import java.time.LocalTime;
import java.util.List;

public class WorkingWeek {

	public class WorkingDay {

		private LocalTime endBreakTime;
		private LocalTime endWorkingTime;
		private LocalTime startBreakTime;
		private LocalTime startWorkingTime;
		private WeekDayEnum WeekDay;

		public WorkingDay(){

		}

	}

	private List<WorkingDay> WorkingDayList;
	public Employee m_Employee;

	public WorkingWeek(){

	}

}