package uni.okejki;

import java.util.ArrayList;

/**
 * Created by jacek on 11/01/16.
 */
public class App {
    private ArrayList<Task> tasks;
    private ArrayList<MaintenanceBreak> maintenanceBreaks;

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addBreak(MaintenanceBreak mBreak) {
        maintenanceBreaks.add(mBreak);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public ArrayList<MaintenanceBreak> getMaintenanceBreaks() {
        return maintenanceBreaks;
    }

    public App() {
        tasks = new ArrayList<Task>();
        maintenanceBreaks = new ArrayList<MaintenanceBreak>();
    }

    public void read() {
        System.out.println("Reading app state ...");

        for (Task task : tasks) {
            System.out.print(task.getFirstOperation().getExecutionTime() + ";");
            System.out.print(task.getSecondOperation().getExecutionTime() + ";");
            System.out.print(task.getFirstOperation().getExecutionMachine() + ";");
            System.out.print(task.getSecondOperation().getExecutionMachine() + ";");
            System.out.println(task.readyTimeStringified());
        }

        for (MaintenanceBreak mBreak : maintenanceBreaks) {
            System.out.print(mBreak.getMachineNumber() + ";");
            System.out.print(mBreak.getDuration() + ";");
            System.out.println(mBreak.getStartTime() + ";");
        }
    }
}
