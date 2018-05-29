package util;

public class StopWatch {

    private static long start = 0;
    private static long end = 0;

    public static void start() {
        start = System.currentTimeMillis();
        end = start;
    }

    public static long stop() {
        end = System.currentTimeMillis();
        return end - start;
    }
}
