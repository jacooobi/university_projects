package uni.okejki;

/**
 * Created by jacek on 11/01/16.
 */
public class Operation {

    private int executionStart;
    private int executionTime;
    private int executionMachine;
    private boolean processed;
    private int id;

    public Operation(int executionTime, int executionMachine, int id) {
        this.executionTime = executionTime;
        this.executionMachine = executionMachine;
        this.processed = false;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isFirst() {
        return getId() == 1;
    }

    public boolean isSecond() {
        return getId() == 2;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public int getExecutionTime() { return executionTime; }
    public int getExecutionMachine() { return executionMachine; }
    public int getExecutionStart() { return executionStart; }
    public void setExecutionStart(int executionStart) { this.executionStart = executionStart; }
    public boolean isProcessed() { return processed; }
    public boolean alreadyProcessed() { return isProcessed(); }
    public String toString() { return ""; }
}

