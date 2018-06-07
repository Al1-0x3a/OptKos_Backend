package util;

import data_models.Employee;
import manager.AppointmentManager;

import java.util.List;
import java.util.Map;

public class TestBench {
    public static void main(String[] args) {
        AppointmentManager crack = new AppointmentManager();
        Map<Employee, List<AppointmentManager.Interval>> ohboi = crack.generateIntervals("2018-04-12");
        Map<Employee, List<AppointmentManager.Interval>> iobho = crack.invert(ohboi);

        System.out.println("Finito");
    }
}
