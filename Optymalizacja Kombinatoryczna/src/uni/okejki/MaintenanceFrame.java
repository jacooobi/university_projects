package uni.okejki;

/**
 * Created by jacek on 11/01/16.
 */
public class MaintenanceFrame extends TimeFrame {
    private static int counter = 0;
    public MaintenanceFrame(int startTime, int duration, int machineNumber) {
        super(startTime, duration, machineNumber, ++counter);
    }

    @Override
    public String toString() {
        String machineID;
        if (getMachineNumber() == 1) {
            machineID = "M1";
        } else {
            machineID = "M2";
        }

        return "maint" + Integer.toString(getTaskId()) + "_" + machineID + "," + getStartTime() + "," + getDuration() + "; ";
    }

    public void stringify() {
        System.out.print(toString());
    }
}
