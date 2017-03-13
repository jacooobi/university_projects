package uni.okejki;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by jacek on 11/01/16.
 */
public class SeedDataGenerator {
    private String filename;
    private int tasksNumber;
    private int maintenanceBreaksNumber;
    private int taskDurationLowerBound;
    private int taskDurationUpperBound;
    private ArrayList<Task> tasks;
    private ArrayList<MaintenanceBreak> breaks;
    private Random rand;

    public SeedDataGenerator(String filename) {
        this.rand = new Random();
        this.filename = filename;
        this.tasksNumber = tasksCount();
        this.taskDurationLowerBound = 2;
        this.taskDurationUpperBound = 20;
        this.maintenanceDurationLowerBound = 5;
        this.maintenanceDurationUpperBound = 15;
        this.maintenanceBreaksNumber = (int)(tasksNumber * 0.2);
        tasks = new ArrayList<Task>();
        breaks = new ArrayList<MaintenanceBreak>();
        this.filename = filename;
    }

    private int maintenanceDurationLowerBound;
    private int maintenanceDurationUpperBound;

    public int readyTime() {
        return (rand.nextInt(Integer.SIZE - 1) % 3 > 1) ? (rand.nextInt(Integer.SIZE - 1) % 100 + 50) : 0;
    }

    public int tasksCount() {
        return (rand.nextInt(Integer.SIZE - 1) % 100) + 25;
    }

    public int taskDuration() {
        return taskDurationLowerBound + (rand.nextInt(Integer.SIZE - 1) % (taskDurationUpperBound - taskDurationLowerBound));
    }

    public int maintenanceDuration() {
        return maintenanceDurationLowerBound + (rand.nextInt(Integer.SIZE - 1) % (maintenanceDurationUpperBound - maintenanceDurationLowerBound));
    }

    public int taskMachine() {
        return (rand.nextInt(Integer.SIZE - 1) % 2) + 1;
    }

    public int complementaryMachine(int machineNumber) {
        return (machineNumber == 1) ? 2 : 1;
    }

    public void call() {
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.println("**** 3 ****");

            generateTasks();
            generateMaintenanceBreaks();

            writer.println(tasksNumber);

            for(int i=0; i<tasksNumber; i++) {
                writer.print(tasks.get(i).getFirstOperation().getExecutionTime() + ";");
                writer.print(tasks.get(i).getSecondOperation().getExecutionTime() + ";");
                writer.print(tasks.get(i).getFirstOperation().getExecutionMachine() + ";");
                writer.print(tasks.get(i).getSecondOperation().getExecutionMachine() + ";");
                writer.println(tasks.get(i).readyTimeStringified());
            }

            for (int i=0; i<maintenanceBreaksNumber; i++) {
                writer.print(i+1 + ";");
                writer.print(1 + ";");
                writer.print(breaks.get(i).getDuration() + ";");
                writer.println(breaks.get(i).getStartTime() + ";");
            }

            writer.close();
        } catch (Exception e) {}
    }

    private void generateTasks() {
        for (int i = 0; i < tasksNumber; i++) {
            int firstTaskMachine = taskMachine();
            int secondTaskMachine = complementaryMachine(firstTaskMachine);

            Operation numeroUno = new Operation(taskDuration(), firstTaskMachine, 1);
            Operation numeroDos = new Operation(taskDuration(), secondTaskMachine, 2);

            tasks.add(new Task(numeroUno, numeroDos, readyTime()));
        }
    }


    private void generateMaintenanceBreaks() {
        int slider = 0;
        for (int i = 0; i < maintenanceBreaksNumber; i++) {
            int duration = maintenanceDuration();
            int startTime = 30 + rand.nextInt(Integer.SIZE - 1) % 60 + slider;
            breaks.add(new MaintenanceBreak(startTime, duration));
            slider = startTime + duration;
        }
    }
}




