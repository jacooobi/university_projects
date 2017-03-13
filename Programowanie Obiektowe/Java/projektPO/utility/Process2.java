package projektPO.utility;

import java.util.concurrent.Semaphore;
import java.util.Random;

public class Process2 extends Thread {
    private static final Random rand = new Random();

    private String id;

    private Semaphore sem;

    public Process2(String i, Semaphore s) {
        id = i;
        sem = s;
    }

    public Process2(String i) {
        id = i;
    }

    public String getMyId() {
        return id;
    }

    private void busy() {
        try {
            sleep(rand.nextInt(500));
        } catch (InterruptedException e) {}
    }

    private void noncritical() {
        System.out.println("Thread " + id + " is NON critical");
        busy();
    }

    private void critical() {
        System.out.println("Thread " + id + " entering critical section");
        busy();
        System.out.println("Thread " + id + " leaving critical section");
    }
}
