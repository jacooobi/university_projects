package uni.okejki;

/**
 * Created by jacek on 11/01/16.
 */
public class TimeFrame {
    private int startTime;
    private int duration;
    private int machineNumber;
    private int id;

    public int getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
    }

    public int getMachineNumber() {
        return machineNumber;
    }

    public TimeFrame(int startTime, int duration, int machineNumber, int id) {
        this.startTime = startTime;
        this.duration = duration;
        this.machineNumber = machineNumber;
        this.id = id;
    }

    public int getOperationDuration() {
        return 0;
    }

    public int getFinishTime() {
        return startTime + duration;
    }

    public boolean isOperation() {
        return false;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getTaskId() {
        return id;
    }

    public String toString() { return ""; }
}
