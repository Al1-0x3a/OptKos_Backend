package util;

import manager.AppointmentManager;

public class TestBench {
    public static void main(String[] args) {
        AppointmentManager crack = new AppointmentManager();
        crack.generateIntervals("2018-04-13");
    }
}
