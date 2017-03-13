package uni.okejki;

/**
 * Created by jacek on 11/01/16.
 */
public class MaintenanceBreak {

    private int machineNumber;
    private int startTime;
    private int duration;

    public int getStartTime() { return startTime; }
    public int getDuration() { return duration; }
    public int getMachineNumber() { return machineNumber; }

    public MaintenanceBreak(int startTime, int duration) {
        this.startTime = startTime;
        this.duration = duration;
        this.machineNumber = 1;
    }
}
