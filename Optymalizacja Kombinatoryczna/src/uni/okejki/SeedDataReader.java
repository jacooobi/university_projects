package uni.okejki;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by jacek on 11/01/16.
 */
public class SeedDataReader {
    private String filename;
    private int tasksCount;

    public SeedDataReader(String filename) {
        this.filename = filename;
    }

    private int getTasksCount() {
        return tasksCount;
    }

    public App read() {
        App app = new App();

        try {
            String strLine;
            int i=0;
            Scanner scan = new Scanner(new FileInputStream(filename));

            scan.nextLine();
            tasksCount = Integer.parseInt(scan.nextLine());
            scan.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));

            while (i < getTasksCount()+2 && (strLine = br.readLine()) != null) {
                if (i<2) { i+=1; continue; }
                app.addTask(createTaskFromString(strLine));
                i+=1;
            }

            while ((strLine = br.readLine()) != null)   {
                app.addBreak(createMaintenanceBreakFromString(strLine));
            }

            br.close();
        } catch (Exception e) {
        }

        return app;
    }


    public Task createTaskFromString(String str) {
        String[] elements = str.split(";");

        Operation firstOperation = new Operation(Integer.parseInt(elements[0]), Integer.parseInt(elements[2]), 1);
        Operation secondOperation = new Operation(Integer.parseInt(elements[1]), Integer.parseInt(elements[3]), 2);

        int readyTime = elements.length == 5 ?Integer.parseInt(elements[4]) : 0;

        return new Task(firstOperation, secondOperation, readyTime);
    }

    public MaintenanceBreak createMaintenanceBreakFromString(String str) {
        String[] elements = str.split(";");

        return new MaintenanceBreak(Integer.parseInt(elements[3]), Integer.parseInt(elements[2]));
    }
}




