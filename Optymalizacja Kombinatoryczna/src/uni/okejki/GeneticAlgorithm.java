package uni.okejki;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by jacek on 11/01/16.
 */

public class GeneticAlgorithm {
    private ResultFileCreator creator;
    private ArrayList<ProposedSolution> population;
    private ArrayList<ProposedSolution> reducedPopulation;
    private ArrayList<MaintenanceBreak> breaks;
    String outputFile;
    private double crossover_percentage;
    private double reduce_percentage;
    private double mutation_percentage;

    private final double CROSSOVER_PERCENTAGE = 0.5;
    private final double REDUCE_PERCENTAGE = 0.75;
    private final double MUTATION_PERCENTAGE = 0.05;

    private final int ITERATION_COUNT = 50;

    private final Random RANDOM = new Random();

    public GeneticAlgorithm(ArrayList<MaintenanceBreak> breaks, String outputFile) {
        this.breaks = breaks;
        this.outputFile = outputFile;
        this.crossover_percentage = CROSSOVER_PERCENTAGE;
        this.reduce_percentage = REDUCE_PERCENTAGE;
        this.mutation_percentage = MUTATION_PERCENTAGE;
    }

    public void setCrossover_percentage(double crossover_percentage) {
        this.crossover_percentage = crossover_percentage;
    }

    public void setReduce_percentage(double reduce_percentage) {
        this.reduce_percentage = reduce_percentage;
    }

    public void setMutation_percentage(double mutation_percentage) {
        this.mutation_percentage = mutation_percentage;
    }

    public void initializePopulation(int populationCount, ArrayList<Task>tasks) {
        IndiePOPulationInitializer init = new IndiePOPulationInitializer(tasks, breaks);
        init.call(populationCount);

        ArrayList<Task> tempTasks = new ArrayList<Task>(tasks);
        population = new ArrayList<ProposedSolution>(init.getPopulation());
        creator = new ResultFileCreator(getBestSolution().fitnessFunction());
        reducedPopulation = new ArrayList<>();
        reducePopulation(reduce_percentage);
        run(reducedPopulation);
    }

    private ArrayList<ProposedSolution> tournamentSelection(final ArrayList<ProposedSolution> pop, double percentage) {
        int tournamentSize = new Double(Math.ceil(1.0 / percentage)).intValue();
        if (pop.size() < tournamentSize) {
            return pop;
        }

        ArrayList<ProposedSolution> result = new ArrayList<>();
        for (int i = tournamentSize; i < pop.size(); i += tournamentSize) {
            int randomIndex = i - RANDOM.nextInt(tournamentSize);
            result.add(pop.get(randomIndex));
        }

        return result;
    }

    private void reducePopulation(double percentage) {
        int howMany = new Double(Math.ceil(percentage * population.size())).intValue();
        int randomBeginIndex = RANDOM.nextInt(population.size() - howMany);

        for (int i = randomBeginIndex; i < howMany + randomBeginIndex; i++) {
            reducedPopulation.add(population.get(i));
        }
    }

    public int rouletteSelect(final ArrayList<ProposedSolution> pop, final ArrayList<ProposedSolution> toFilter) {
        ArrayList<ProposedSolution> filtered = pop.stream()
                .filter(solution -> !toFilter.contains(solution))
                .collect(Collectors.toCollection(ArrayList<ProposedSolution>::new));

        float totalWeight = filtered.stream().reduce((float)(0.0), (result, solution) -> result + solution.fitnessWeight(), Float::sum);

        ArrayList<Float> weightCollection = filtered.stream()
                .map(ProposedSolution::fitnessWeight)
                .collect(Collectors.toCollection(ArrayList::new));

        float value = randUniformPositive() * totalWeight;

        for(int i=0; i<weightCollection.size(); i++) {
            value -= weightCollection.get(i);
            if (value <= 0) return i;
        }

        return weightCollection.size() - 1;
    }

    private float randUniformPositive() {
        return new Random().nextFloat();
    }

    private void crossoverPopulation(ArrayList<ProposedSolution> pop, double percentage) {
        int howMany = new Double(Math.ceil(percentage * pop.size())).intValue();
        int randomBeginIndex = RANDOM.nextInt(pop.size() - howMany);

        for (int i = randomBeginIndex; i < randomBeginIndex + howMany; i += 2) {
            pop.get(i).crossOver(pop.get(i + 1));
        }
    }
    
    private void mutateAlt(ArrayList<ProposedSolution> pop) {
        for (ProposedSolution solution : pop) {
            solution.mutate();
        }
    }

    private ArrayList<ProposedSolution> selectElementsWithRoulette(final ArrayList<ProposedSolution> pop, double percentage) {
        ArrayList<ProposedSolution> result = new ArrayList<>();
        int howMany = new Double(Math.ceil(percentage * pop.size())).intValue();
        while (result.size() < howMany) {
            int index = rouletteSelect(pop, result);
            result.add(pop.get(index));
        }

        return result;
    }

    public void run(ArrayList<ProposedSolution> pop) {
        System.out.println("--- " + outputFile + " ---");
        for (int i = 0; i < ITERATION_COUNT; ++i) {
            ArrayList<ProposedSolution> filtered = tournamentSelection(pop, mutation_percentage);
            mutateAlt(filtered);
            crossoverPopulation(population, crossover_percentage);
            currentStatus();
        }

        currentStatus();
        creator.setSolution(getBestSolution());
        creator.writeToFile(outputFile, false);
    }

    private boolean isPopulationValid(final ArrayList<ProposedSolution> pop) {
        for (ProposedSolution solution : pop) {
            if (!solution.areOperationsValid()) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<Integer> fitnesses() {
        return population.stream().map(solution -> solution.fitnessFunction()).collect(Collectors.toCollection(ArrayList::new));
    }

    public int bestFitness() {
        ArrayList<Integer> asd = fitnesses();
        Collections.sort(asd);
        return asd.get(0);
    }

    public int worstFitness() {
        ArrayList<Integer> asd = fitnesses();
        Collections.sort(asd);
        return asd.get(asd.size()-1);
    }

    public double meanFitness() {
        return (fitnesses().stream().reduce(0, (result, fit) -> result + fit, Integer::sum).doubleValue() / (double)(fitnesses().size()));
    }

    public double medianFitness() {
        ArrayList<Integer> asd = fitnesses();
        Collections.sort(asd);
        int middle = asd.size()/2;

        if (asd.size()%2 == 1) {
            return asd.get(middle);
        } else {
            return (asd.get(middle-1) + asd.get(middle)) / 2.0;
        }
    }

    private ProposedSolution getBestSolution() {
        return population.stream()
                .sorted((e1, e2) -> Integer.compare(e1.getFinishTime(), e2.getFinishTime()))
                .findFirst()
                .get();
    }

    public void currentStatus() {
        System.out.print(bestFitness() + " " + worstFitness() + " " + meanFitness() + " " + medianFitness() + "\n");
    }
}
