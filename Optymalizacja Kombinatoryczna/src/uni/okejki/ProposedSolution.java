package uni.okejki;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProposedSolution {
    private final CrossoverController crossoverController;
    private final MutationController mutationController;
    private int currentLocationMachineOne;
    private int currentLocationMachineTwo;
    private ArrayList<OperationFrame> firstMachine;
    private ArrayList<OperationFrame> secondMachine;

    public void setMaintenanceBreaks(ArrayList<MaintenanceBreak> maintenanceBreaks) {
        this.maintenanceBreaks = maintenanceBreaks;
    }

    private ArrayList<TimeFrame> finalFirstMachineRepresentation;
    private ArrayList<TimeFrame> finalSecondMachineRepresentation;
    private ArrayList<MaintenanceBreak> maintenanceBreaks;

    public ArrayList<TimeFrame> getfinalFirstMachineRepresentation() {
        return finalFirstMachineRepresentation;
    }

    public ArrayList<TimeFrame> getfinalSecondMachineRepresentation() {
        return finalSecondMachineRepresentation;
    }

    public CrossoverController getCrossoverController() {
        return crossoverController;
    }


    public ProposedSolution() {

        this.firstMachine = new ArrayList<OperationFrame>();
        this.secondMachine = new ArrayList<OperationFrame>();
        this.maintenanceBreaks = new ArrayList<MaintenanceBreak>();
        this.finalFirstMachineRepresentation = new ArrayList<TimeFrame>();
        this.finalSecondMachineRepresentation = new ArrayList<TimeFrame>();
        this.crossoverController = new CrossoverController();
        this.mutationController = new MutationController();
        this.currentLocationMachineOne = 0;
        this.currentLocationMachineTwo = 0;
    }

    public ProposedSolution(ProposedSolution toCopy) {
        this.firstMachine = new ArrayList<OperationFrame>(toCopy.getFirstMachine());
        this.secondMachine = new ArrayList<OperationFrame>(toCopy.getSecondMachine());
        this.maintenanceBreaks = new ArrayList<MaintenanceBreak>(toCopy.getMaintenanceBreaks());
        this.currentLocationMachineOne = toCopy.getCurrentLocationMachineOne();
        this.currentLocationMachineTwo = toCopy.getCurrentLocationMachineTwo();
        this.crossoverController = toCopy.getCrossoverController();
        this.mutationController = toCopy.getMutationController();
        this.finalFirstMachineRepresentation = new ArrayList<TimeFrame>();
        this.finalSecondMachineRepresentation = new ArrayList<TimeFrame>();
    }

    public int getHackyCurrentLocation() {
        if (firstMachine.size() == 0) return 0;

        if (firstMachine.get(firstMachine.size()-1).getFinishTime() > 0)
            return firstMachine.get(firstMachine.size()-1).getFinishTime();
        else {
            return 0;
        }
    }

    public MutationController getMutationController() {
        return mutationController;
    }

    public int getCurrentLocationMachineOne() {
        return currentLocationMachineOne;
    }

    public int getMachineOneFinishTime() {
        return firstMachine.get(firstMachine.size()-1).getFinishTime();
    }

    public int getMachineTwoFinishTime() {
        return secondMachine.get(secondMachine.size()-1).getFinishTime();
    }

    public int getFinishTime() {
        return Math.max(getMachineOneFinishTime(), getMachineTwoFinishTime());
    }

    public int getCurrentLocationMachineTwo() {
        return currentLocationMachineTwo;
    }

    public void updateCurrentLocationMachineOne(int distance) {
        this.currentLocationMachineOne += distance;
    }

    public void updateCurrentLocationMachineTwo(int distance) {
        this.currentLocationMachineTwo += distance;
    }

    void addTimeFrameToFirstMachine(OperationFrame frame) {
        firstMachine.add(frame);
    }

    void addTimeFrameToSecondMachine(OperationFrame frame) {
        secondMachine.add(frame);
    }

    public ArrayList<OperationFrame> getFirstMachine() {
        return firstMachine;
    }

    public ArrayList<OperationFrame> getSecondMachine() {
        return secondMachine;
    }

    public ArrayList<MaintenanceBreak> getMaintenanceBreaks() {
        return maintenanceBreaks;
    }

    public int getMachineMaintenanceBreaksCount(int machineNumber) {
        return (int) maintenanceBreaks.stream()
                .filter(t -> t.getMachineNumber() == machineNumber)
                .count();
    }

    private int getMachineIdleFrameCount(final ArrayList<TimeFrame> machine) {
        return (int) machine.stream()
                .filter(t -> t instanceof IdleFrame)
                .count();
    }

    private ArrayList<TimeFrame> getMachineOneIdleFrames() {
        return finalFirstMachineRepresentation.stream().filter(t -> t instanceof IdleFrame).collect(Collectors.toCollection(ArrayList<TimeFrame>::new));
    }

    private ArrayList<TimeFrame> getMachineTwoIdleFrames() {
        return finalSecondMachineRepresentation.stream().filter(t -> t instanceof IdleFrame).collect(Collectors.toCollection(ArrayList<TimeFrame>::new));
    }

    public int getMachineIdleFrameCount(int machineNumber) {
        if (machineNumber == 1) {
            return getMachineIdleFrameCount(finalFirstMachineRepresentation);
        }

        if (machineNumber == 2) {
            return getMachineIdleFrameCount(finalSecondMachineRepresentation);
        }

        return 0;
    }

    public int getMachineIdleFramesLength(int machineNumber) {
        if (machineNumber == 1) {
            return getMachineIdleFramesLength(getMachineOneIdleFrames());
        } else if (machineNumber == 2) {
            return getMachineIdleFramesLength(getMachineTwoIdleFrames());
        }

        return 0;
    }

    private int getMachineIdleFramesLength(final ArrayList<TimeFrame> frames) {
        return frames.stream().reduce(0, (result, idle) -> result + idle.getDuration(), Integer::sum);
    }

    public int getMaintenanceBreaksLength() {
        return maintenanceBreaks.stream().reduce(0, (result, mBreak) -> result + mBreak.getDuration(), Integer::sum);
    }

    private int calculateDistance(ArrayList<OperationFrame> machine) {
        return machine.stream().reduce(0, (result, frame) -> result + frame.getDuration(), Integer::sum);
    }

    public void crossOver(ProposedSolution other) {
        ArrayList<ArrayList<OperationFrame>> firstSolution = crossoverController.crossOver(this, other);
        ArrayList<ArrayList<OperationFrame>> secondSolution = crossoverController.crossOver(other, this);

        setFirstMachine(firstSolution.get(0));
        setSecondMachine(firstSolution.get(1));
        other.setFirstMachine(secondSolution.get(0));
        other.setSecondMachine(secondSolution.get(1));
        reduceIdleTimes(this);
        reduceIdleTimes(other);

//        if (!crossoverController.validate(firstMachine, secondMachine)) {
//            display();
//            System.out.println("");
//        }
//
//        if (!crossoverController.validate(other.getFirstMachine(), other.getSecondMachine())) {
//            other.display();
//            System.out.println("");
//        }
    }

    public void mutate() {
        mutationController.mutate(this);
    }

    private void reduceIdleTimes(ArrayList<OperationFrame> machine, final ArrayList<OperationFrame> otherMachine) {
        for (int i = 1; i < machine.size(); ++i) {
            OperationFrame op = machine.get(i);
            OperationFrame before = machine.get(i - 1);
            int diff = op.getStartTime() - before.getFinishTime();
            if (op.isSecond()) {
                OperationFrame otherOp = null;

                for (OperationFrame o : otherMachine) {
                    if (op.getTaskId() == o.getTaskId()) {
                        otherOp = o;
                        break;
                    }
                }

                if (otherOp != null) {
                    int otherDiff = op.getStartTime() - otherOp.getFinishTime();
                    if (otherDiff < diff) {
                        diff = otherDiff;
                    }
                }
            }

            if (diff > 0) {
                op.setStartTime(op.getStartTime() - diff);
            }
        }
    }

    private void reduceIdleTimes(ProposedSolution machine) {
        ArrayList<OperationFrame> firstMachine = machine.getFirstMachine();
        ArrayList<OperationFrame> secondMachine = machine.getSecondMachine();
        reduceIdleTimes(firstMachine, secondMachine);
        reduceIdleTimes(secondMachine, firstMachine);
    }

    public void reduceIdleTimes() {
        reduceIdleTimes(this);
    }

    public int fitnessFunction() {
        int firstMachine = getFirstMachine().stream().reduce(0, (result, frame) -> result + frame.getFinishTime(), Integer::sum);
        int secondMachine = getSecondMachine().stream().reduce(0, (result, frame) -> result + frame.getFinishTime(), Integer::sum);

        return firstMachine+secondMachine;
    }

    public float fitnessWeight() {
        return 1/(float)(fitnessFunction());
    }

    public void setFirstMachine(ArrayList<OperationFrame> firstMachine) {
        this.firstMachine = firstMachine;
    }

    public void setSecondMachine(ArrayList<OperationFrame> secondMachine) {
        this.secondMachine = secondMachine;
    }

    public void constructFinalMachineOne() {
        for(int i=0; i<firstMachine.size()-1; i++) {
            if(i ==0 && firstMachine.get(i).getStartTime() != 0){
                finalFirstMachineRepresentation.add(new IdleFrame(0, firstMachine.get(i).getStartTime(), 1));
            }

            if (i+1 == firstMachine.size()) {
                int duration = getMachineOneFinishTime() - firstMachine.get(i).getFinishTime();
                if (duration != 0) {
                    finalFirstMachineRepresentation.add(new IdleFrame(firstMachine.get(i).getFinishTime(), duration, 1));
                }
                finalFirstMachineRepresentation.add(firstMachine.get(i));
            }

            finalFirstMachineRepresentation.add(firstMachine.get(i));
            if(firstMachine.get(i).getFinishTime() != firstMachine.get(i+1).getStartTime()) {
                int start = firstMachine.get(i).getFinishTime();

                MaintenanceBreak mBreak = getNearestBreak(firstMachine.get(i).getFinishTime());

                if (mBreak == null) {
                    int duration = firstMachine.get(i + 1).getStartTime() - firstMachine.get(i).getFinishTime();

                    finalFirstMachineRepresentation.add(new IdleFrame(start, duration, 1));
                } else {
                    int duration = mBreak.getStartTime() - start;

                    finalFirstMachineRepresentation.add(new IdleFrame(start, duration, 1));
                    finalFirstMachineRepresentation.add(new MaintenanceFrame(mBreak.getStartTime(), mBreak.getDuration(), 1));
                }

            }

        }

    }

    public void constructFinalMachineTwo() {
        for(int i=0; i<secondMachine.size()-1; i++) {
            if(i ==0 && secondMachine.get(i).getStartTime() != 0){
                finalSecondMachineRepresentation.add(new IdleFrame(0, secondMachine.get(i).getStartTime(), 2));
            }

            if (i+1 == secondMachine.size()) {
                int duration = getMachineTwoFinishTime() - secondMachine.get(i).getFinishTime();
                if (duration != 0) {
                    finalSecondMachineRepresentation.add(new IdleFrame(secondMachine.get(i).getFinishTime(), duration, 2));
                }
                finalSecondMachineRepresentation.add(secondMachine.get(i));
            }

            finalSecondMachineRepresentation.add(secondMachine.get(i));
            if(secondMachine.get(i).getFinishTime() != secondMachine.get(i+1).getStartTime()) {
                int start = secondMachine.get(i).getFinishTime();
                int duration = secondMachine.get(i+1).getStartTime() - secondMachine.get(i).getFinishTime();

                finalSecondMachineRepresentation.add(new IdleFrame(start, duration, 2));
//                finalSecondMachineRepresentation.add(firstMachine.get(i+1));
            }
        }
    }

    private MaintenanceBreak getNearestBreak(int currentLocation) {
        if (currentLocation >= maintenanceBreaks.get(maintenanceBreaks.size()-1).getStartTime() + maintenanceBreaks.get(maintenanceBreaks.size()-1).getDuration()) return null;

        MaintenanceBreak mBreak = maintenanceBreaks.get(0);

        for(MaintenanceBreak maintBreak : maintenanceBreaks) {
            mBreak = maintBreak;
            if (maintBreak.getStartTime() > currentLocation) break;
        }

        return mBreak;
    }

    public OperationFrame getFrameFromFirstMachine(int taskID) {
        for(OperationFrame frame : firstMachine) {
            if (frame.getTaskId() == taskID) return frame;
        }
        return null;
    }

    public OperationFrame getFrameFromSecondMachine(int taskID) {
        for (OperationFrame frame : secondMachine) {
            if (frame.getTaskId() == taskID) return frame;
        }
        return null;
    }

    private boolean validateMachines() {
        return areMachineTasksValid(firstMachine) && areMachineTimeFramesValid(firstMachine) &&
                areMachineTasksValid(secondMachine) && areMachineTimeFramesValid(secondMachine);
    }

    private boolean areMachineTasksValid(ArrayList<OperationFrame> machine) {
        for (final OperationFrame frame: machine) {
            boolean valid = !machine.stream()
                    .filter(o -> !frame.equals(o))
                    .anyMatch(o -> frame.getTaskId() == o.getTaskId());

            if (!valid) return false;
        }

        return true;
    }

    public boolean areMachineTimeFramesValid(ArrayList<OperationFrame> machine) {
        for (int i = 1; i < machine.size(); ++i) {
            if (machine.get(i - 1).getFinishTime() > machine.get(i).getStartTime()) {
                return false;
            }
        }

        return true;
    }

    private boolean areAllTasksComplementary() {
        return firstMachine.stream()
                .allMatch(firstFrame ->
                        secondMachine.stream()
                                .anyMatch(secondFrame ->
                                        (firstFrame.getTaskId() == secondFrame.getTaskId()) &&
                                                (firstFrame.getOperationId() != secondFrame.getOperationId())
                                )
                );
    }

    public boolean areFirstOperationsBeforeSecond() {
        for (final OperationFrame f : firstMachine) {
            for (final OperationFrame s : secondMachine) {
                if (f.getTaskId() == s.getTaskId()) {
                    if (f.isFirst()) {
                        if (!f.isBefore(s)) {
                            return false;
                        }
                    } else {
                        if (!s.isBefore(f)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    private boolean machineReadyTimesValid(ArrayList<OperationFrame> machine) {
        for (final OperationFrame frame : machine) {
            if (frame.getStartTime() < frame.getReadyTime()) {
                return false;
            }
        }
        return true;
    }

    public boolean areReadyTimesValid() {
        return machineReadyTimesValid(firstMachine) && machineReadyTimesValid(secondMachine);
    }

    public boolean areOperationsValid() {
        return validateMachines() &&
                areAllTasksComplementary() &&
                areFirstOperationsBeforeSecond() &&
                areReadyTimesValid();
    }

    @Override
    public String toString() {
        String result = "M1: ";
        for(TimeFrame frame: finalFirstMachineRepresentation) {
            result += frame.toString();
        }
        result += "\n";


        result += "M2: ";
        for(TimeFrame frame: finalSecondMachineRepresentation) {
            result += frame.toString();
        }
        result += "\n";
        result += getMachineMaintenanceBreaksCount(1) + "," + getMaintenanceBreaksLength() + "\n";
        result += getMachineMaintenanceBreaksCount(2) + "," + 0 + "\n";
        result += getMachineIdleFrameCount(1) + "," + getMachineIdleFramesLength(1) + "\n";
        result += getMachineIdleFrameCount(2) + "," + getMachineIdleFramesLength(2) + "\n";

        return result;
    }

    public void display() {
        constructFinalMachineOne();
        constructFinalMachineTwo();

        System.out.println(toString());
    }
}
