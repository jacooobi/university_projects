package uni.okejki;

/**
 * Created by jacek on 11/01/16.
 */
public class OperationFrame extends TimeFrame {
    private int operationId;
    private int orderId;
    private int readyTime;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public OperationFrame(int startTime, int duration, int machineNumber, int taskId, int operationId) {
        super(startTime, duration, machineNumber, taskId);
        this.operationId = operationId;
    }

    public OperationFrame(OperationFrame toCopy) {
        super(toCopy.getStartTime(), toCopy.getDuration(), toCopy.getMachineNumber(), toCopy.getTaskId());
        this.operationId = toCopy.getOperationId();
    }

    public int getOperationDuration() {
        return getDuration();
    }

    public boolean equals(final OperationFrame operation) {
        return operation.getTaskId() == getTaskId() && operation.getOperationId() == getOperationId();
    }

    public boolean isBefore(final OperationFrame operation) {
        return this.getFinishTime() <= operation.getStartTime();
    }

    public boolean overlaps(final OperationFrame operation) {
        return Math.max(getStartTime(), operation.getStartTime()) <= Math.min(getFinishTime(), operation.getFinishTime());
    }

    public int getOperationId() {
        return operationId;
    }

    public boolean isFirst() { return operationId == 1; }

    public boolean isSecond() { return operationId == 2; }

    @Override
    public boolean isOperation() {
        return true;
    }

    public String toString() {
        return "op" + Integer.toString(operationId) + "_" + Integer.toString(getTaskId()) + "," + getStartTime() + "," + getDuration() + "; ";
    }

    public void stringify() {
        System.out.print(toString());
    }

    public void updateTimes(int newStart) {
        setStartTime(newStart);
    }

    public int getReadyTime() {
        return readyTime;
    }

    public void setReadyTime(int readyTime) {
        this.readyTime = readyTime;
    }

    public boolean inRightOrderWith(final OperationFrame sibling) {
        OperationFrame first = sibling.isFirst() ? sibling : this;
        OperationFrame second = sibling.isSecond() ? sibling : this;

        return !first.overlaps(second) && !second.isBefore(first);
    }
}

