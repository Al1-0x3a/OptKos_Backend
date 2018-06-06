package util;

import manager.AppointmentManagerAufCrack;

public class TestBench {
    public static void main(String[] args) {
        AppointmentManagerAufCrack crack = new AppointmentManagerAufCrack();
        crack.generateIntervals("2018-04-12");
    }
}
