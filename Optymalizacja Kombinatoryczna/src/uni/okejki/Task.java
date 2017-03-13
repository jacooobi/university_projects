package uni.okejki;

/**
 * Created by jacek on 11/01/16.
 */
public class Task {
    private static int counter = 0;

    private Operation firstOperation;
    private Operation secondOperation;
    private int readyTime;
    private int id;

    public Task(Operation firstOperation, Operation secondOperation, int readyTime) {
        this.firstOperation = firstOperation;
        this.secondOperation = secondOperation;
        this.readyTime = readyTime;
        this.id = ++counter;

    }

    public int getId() {
        return id;
    }

    public boolean hasReadyTime() {
        return readyTime > 0;
    }

    public boolean bothOperationsMounted() {
        return (firstOperation.alreadyProcessed() && secondOperation.alreadyProcessed());
    }

    public int getReadyTime() { return readyTime; }

    public Operation getFirstOperation() { return firstOperation; }
    public Operation getSecondOperation() { return secondOperation; }

    public String readyTimeStringified() {
        return (readyTime == 0) ? "" : (Integer.toString(readyTime) + ";");
    }

    public Operation startOperation() {
        return (firstOperation.getExecutionMachine() == 1) ? firstOperation : secondOperation;

    }
    public Operation getAwaitingOperation() {
        return firstOperation.alreadyProcessed() ? secondOperation : firstOperation;
    }

    public Operation getComplementaryOperation() {
        return firstOperation.alreadyProcessed() ? firstOperation : secondOperation;
    }
}



