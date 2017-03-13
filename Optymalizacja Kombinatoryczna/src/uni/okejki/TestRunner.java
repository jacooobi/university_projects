package uni.okejki;

/**
 * Created by jacek on 24/01/16.
 */
public class TestRunner {
    private static final int POPULATION_SIZE = 10000;

    private static final double MUTATION_PERCENTAGE = 0.05;
    private static final double CROSSOVER_PERCENTAGE = 0.5;
    private static final double REDUCTION_PERCENTAGE = 0.75;

    private static final double[] MUTATION_RANGES = {0.05,0.1,0.15,0.2,0.25,0.3,0.35,0.4,0.45,0.5};
    private static final double[] CROSSOVER_RANGES = {0.25,0.3,0.35,0.4,0.45,0.5,0.55,0.6,0.65,0.7,0.75};
    private static final double[] REDUCTION_RANGES = {0.6,0.63,0.66,0.69,0.72,0.75,0.78,0.81,0.84,0.87,0.9};
    private static final int[] POPULATION_RANGES = {1000,2000,3000,4000,5000,6000,7000,8000,9000,10000};

    private static final String[] filenames = {
            "krotkie_czasy_operacji",
//            "srednie_czasy_operacji",
//            "dlugie_czasy_operacji",
//            "krotkie_czasy_maintenance",
//            "dlugie_czasy_maintenance"
    };


    public static void call() {
        for(String filename : filenames) {
            run(filename);
        }
    }

    private static void launch(App app, int size, String outputFile) {
        GeneticAlgorithm alg = new GeneticAlgorithm(app.getMaintenanceBreaks(), outputFile);
        alg.initializePopulation(size, app.getTasks());
    }

    private static void modifiedMutationPercentageLaunch(App app, int size, String outputFile, double mutationPercentage) {
        GeneticAlgorithm alg = new GeneticAlgorithm(app.getMaintenanceBreaks(), outputFile);
        alg.setMutation_percentage(mutationPercentage);
        alg.initializePopulation(size, app.getTasks());
    }

    private static void modifiedCrossoverPercentageLaunch(App app, int size, String outputFile, double crossoverPercentage) {
        GeneticAlgorithm alg = new GeneticAlgorithm(app.getMaintenanceBreaks(), outputFile);
        alg.setCrossover_percentage(crossoverPercentage);
        alg.initializePopulation(size, app.getTasks());
    }

    private static void modifiedReducePercentageLaunch(App app, int size, String outputFile, double reducePercentage) {
        GeneticAlgorithm alg = new GeneticAlgorithm(app.getMaintenanceBreaks(), outputFile);
        alg.setReduce_percentage(reducePercentage);
        alg.initializePopulation(size, app.getTasks());
    }

    private static App readInputFile(String filename) {
        SeedDataReader reader = new SeedDataReader(filename);
        return reader.read();
    }

    private static void run(String inputFile) {
//
//        for(double val: MUTATION_RANGES) {
//            App app = readInputFile(inputFile);
//            String outputFile = inputFile + "_mutation_" + Double.toString(val) + "_wynik";
//            modifiedMutationPercentageLaunch(app, POPULATION_SIZE, outputFile, val);
//        }

        for(double val: CROSSOVER_RANGES) {
            App app = readInputFile(inputFile);
            String outputFile = inputFile + "_crossover_" + Double.toString(val) + "_wynik";
            modifiedCrossoverPercentageLaunch(app, POPULATION_SIZE, outputFile, val);
        }

//        for(double val: REDUCTION_RANGES) {
//            App app = readInputFile(inputFile);
//            String outputFile = inputFile + "_reduction_" + Double.toString(val) + "_wynik";
//            modifiedReducePercentageLaunch(app, POPULATION_SIZE, outputFile, val);
//        }
//
//        for(int val: POPULATION_RANGES) {
//            App app = readInputFile(inputFile);
//            String outputFile = inputFile + "_population_" + Integer.toString(val) + "_wynik";
//            launch(app, val, outputFile);
//        }
    }
}
