package util;

import data_models.Employee;
import manager.AppointmentManagerAufCrack;

import java.util.List;
import java.util.Map;

public class TestBench {
    public static void main(String[] args) {
        AppointmentManagerAufCrack crack = new AppointmentManagerAufCrack();
        Map<Employee, List<AppointmentManagerAufCrack.Interval>> ohboi = crack.generateIntervals("2018-04-12");
        Map<Employee, List<AppointmentManagerAufCrack.Interval>> iobho = crack.invert(ohboi);

        System.out.println("Finito");
    }
}
