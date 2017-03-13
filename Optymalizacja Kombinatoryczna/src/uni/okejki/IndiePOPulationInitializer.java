package uni.okejki;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by jacek on 13/01/16.
 */

public class IndiePOPulationInitializer {
    private ArrayList<ProposedSolution> population;
    private ArrayList<MaintenanceBreak> breaks;
    private ArrayList<Task> tasks;
    private Random rand;

    public IndiePOPulationInitializer(ArrayList<Task> tasks, ArrayList<MaintenanceBreak> breaks) {
        this.tasks = tasks;
        this.breaks = breaks;
        this.population = new ArrayList<ProposedSolution>();
        this.rand = new Random();
    }

    public ArrayList<ProposedSolution> getPopulation() {
        return population;
    }

    public void call(int populationCount) {
        for (int i = 0; i < populationCount; i++) {
            createSolution();
        }
    }

    private void createSolution() {
        ProposedSolution solution = new ProposedSolution();

        ArrayList<Task> tempTasks = new ArrayList<Task>(composedTasks());

        for (Task tempTask : tempTasks) {
            tempTask.getFirstOperation().setExecutionStart(0);
            tempTask.getFirstOperation().setProcessed(false);
            tempTask.getSecondOperation().setProcessed(false);
        }

        for(Task task : tempTasks) {
            while (!task.bothOperationsMounted()) {
                Operation selectedOperation = task.getAwaitingOperation();

                if (selectedOperation.getExecutionMachine() == 1) {
                    int currentPos = solution.getCurrentLocationMachineOne();

                    if (selectedOperation.isFirst() && task.getReadyTime() <= currentPos && doesItFit(selectedOperation,currentPos)) {
                        OperationFrame operation = new OperationFrame(currentPos, selectedOperation.getExecutionTime(), 1, task.getId(), 1);
                        operation.setReadyTime(task.getReadyTime());
                        selectedOperation.setExecutionStart(currentPos);
                        selectedOperation.setProcessed(true);

                        solution.getFirstMachine().add(operation);

                        solution.updateCurrentLocationMachineOne(operation.getDuration());
                    } else if (selectedOperation.isSecond() && task.getFirstOperation().getExecutionStart() + task.getFirstOperation().getExecutionTime() <= solution.getCurrentLocationMachineOne() && doesItFit(selectedOperation,currentPos)) {

                        OperationFrame operation = new OperationFrame(currentPos, selectedOperation.getExecutionTime(), 1, task.getId(), 2);
                        selectedOperation.setProcessed(true);

                        solution.getFirstMachine().add(operation);
                        solution.updateCurrentLocationMachineOne(operation.getDuration());

                    } else {
                        MaintenanceBreak mBreak = getNearestMaintenanceBreak(currentPos);
                        if (getNearestMaintenanceBreak(currentPos) == null) {
                            int randDuration = rand.nextInt(20) % 20;
                            solution.updateCurrentLocationMachineOne(randDuration);
                        } else {
                            int duration = mBreak.getStartTime() - currentPos;
                            solution.updateCurrentLocationMachineOne(duration + mBreak.getDuration());
                        }
                    }

                } else {

                    int currentPos = solution.getCurrentLocationMachineTwo();

                    if (selectedOperation.isFirst() && task.getReadyTime() <= currentPos) {
                        OperationFrame operation = new OperationFrame(currentPos, selectedOperation.getExecutionTime(), 2, task.getId(), 1);
                        operation.setReadyTime(task.getReadyTime());

                        selectedOperation.setExecutionStart(currentPos);
                        selectedOperation.setProcessed(true);

                        solution.getSecondMachine().add(operation);
                        solution.updateCurrentLocationMachineTwo(operation.getDuration());

                    } else if(selectedOperation.isSecond() && task.getFirstOperation().getExecutionStart() + task.getFirstOperation().getExecutionTime() <= solution.getCurrentLocationMachineTwo() ) {
                        OperationFrame operation = new OperationFrame(currentPos, selectedOperation.getExecutionTime(), 2, task.getId(), 2);
                        selectedOperation.setProcessed(true);

                        solution.getSecondMachine().add(operation);
                        solution.updateCurrentLocationMachineTwo(operation.getDuration());
                    } else {
                        int randDuration = rand.nextInt(Integer.SIZE - 1) % 30;

                        solution.updateCurrentLocationMachineTwo(randDuration);
                    }
                }

            }
        }
        solution.setMaintenanceBreaks(breaks);

        population.add(solution);
    }

    private ArrayList<Task> selectTasksWithReadyTime(ArrayList<Task> tasks) {
        return tasks.stream().filter(t -> t.hasReadyTime()).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Task> selectTasksWithoutReadyTime(ArrayList<Task> tasks) {
        return tasks.stream().filter(t -> !t.hasReadyTime()).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Task> composedTasks() {
        ArrayList<Task> tasksWithReadyTime = new ArrayList<>(selectTasksWithReadyTime(tasks));
        ArrayList<Task> tasksWithoutReadyTime = new ArrayList<>(selectTasksWithoutReadyTime(tasks));
        Collections.shuffle(tasksWithReadyTime);
        Collections.shuffle(tasksWithoutReadyTime);

        ArrayList<Task> tempTasks = new ArrayList<Task>();
        tempTasks.addAll(tasksWithoutReadyTime);
        tempTasks.addAll(tasksWithReadyTime);
        return tempTasks;
    }

    private boolean doesItFit(Operation op, int currentLocation) {
        if (getNearestMaintenanceBreak(currentLocation) == null) {
            return true;
        } else {
            return (currentLocation + op.getExecutionTime() < getNearestMaintenanceBreak(currentLocation).getStartTime());
        }
    }

    private MaintenanceBreak getNearestMaintenanceBreak(int currentLocation) {
        if (currentLocation >= breaks.get(breaks.size()-1).getStartTime() + breaks.get(breaks.size()-1).getDuration()) return null;

        MaintenanceBreak mBreak = breaks.get(0);

        for(MaintenanceBreak maintBreak : breaks) {
            mBreak = maintBreak;
            if (maintBreak.getStartTime() > currentLocation) break;
        }

        return mBreak;
    }
}
