package uni.okejki;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by tomek on 23.01.2016.
 */
public class ResultFileCreator {
    private ProposedSolution solution;
    private int timeBefore;

    public ResultFileCreator(int timeBefore) {
        this.timeBefore = timeBefore;
    }

    public void setSolution(ProposedSolution solution) {
        this.solution = solution;
    }

    public void writeToFile(String filename, boolean overwrite) {
        try {
            String path = "./results/" + filename;
            if (!overwrite) {
                File f = new File(path);
                if ((f.exists() && !f.isDirectory())) {
                    System.err.println("File already exists");
                    return;
                }
            }

            solution.constructFinalMachineOne();
            solution.constructFinalMachineTwo();

            PrintWriter writer = new PrintWriter(path, "UTF-8");
            writer.println("**** 3 ****");
            writer.println(solution.fitnessFunction() + ", " + timeBefore);
            writer.println(solution.toString());

            writer.close();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
