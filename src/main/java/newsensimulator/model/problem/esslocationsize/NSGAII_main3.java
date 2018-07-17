package newsensimulator.model.problem.esslocationsize;

import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.measure.MeasureListener;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.IntegerDoubleSolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;

/**
 * Class to configure and run the NSGA-II algorithm
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class NSGAII_main3 extends AbstractAlgorithmRunner {

    private List population;
    private static int MAX_ALLOWED_EVOLUTIONS;
    private static int POPULATION_SIZE;
    private static int BESS_AMOUNT;
    private static boolean QUANTITY = false;
    private static double period;

    public void setEvolutions(int evol) {
        this.MAX_ALLOWED_EVOLUTIONS = evol;
    }

    public void setPopulationSize(int pop) {
        this.POPULATION_SIZE = pop;
    }

    public void setBESS_AMOUNT(int BESS) {
        this.BESS_AMOUNT = BESS;
    }

    public void setQuantity(boolean cant) {
        this.QUANTITY = cant;
    }
    public boolean getQuantity(){
        return this.QUANTITY;
    }

    public double getPeriod() {
        return period;
    }

    public void setPeriod(double period) {
        this.period = period;
    }
    
    

    /**
     * @param args Command line arguments.
     * @throws JMetalException
     * @throws FileNotFoundException Invoking command: java
     * org.uma.jmetal.runner.multiobjective.NSGAIIRunner problemName
     * [referenceFront]
     */
    public static void main(String[] args) throws JMetalException, FileNotFoundException, InterruptedException {
        NSGAII_main3 asdf = new NSGAII_main3();
        asdf.start();
    }

    public void start() {

        long startMeasure = System.currentTimeMillis();
        Problem problem;
        Algorithm<List<IntegerDoubleSolution>> algorithm;
        CrossoverOperator<IntegerDoubleSolution> crossover;
        MutationOperator mutation;
        SelectionOperator<List<IntegerDoubleSolution>, IntegerDoubleSolution> selection;
        String referenceParetoFront = "";

//        if (args.length == 1) {
//            problemName = args[0];
//        } else if (args.length == 2) {
//            problemName = args[0];
//            referenceParetoFront = args[1];
//        } else {
//            problemName = "org.uma.jmetal.problem.multiobjective.zdt.ZDT1";
//            referenceParetoFront = "jmetal-problem/src/test/resources/pareto_fronts/ZDT1.pf";
//        }
//    problem = new ZDT2(); //ProblemUtils.<DoubleSolution> loadProblem(problemName);
//        problem = ProblemUtils.loadProblem("jmetal.problems.OptimalBESS");
        if (QUANTITY == false) {
            problem = new Optimal(BESS_AMOUNT + 1, 1, period);
            double crossoverProbability = 0.9;
            double crossoverDistributionIndex = 20.0;
            crossover = new IntegerDoubleCrossover(crossoverProbability, crossoverDistributionIndex);
            double mutationProbability = 1.0 / problem.getNumberOfVariables();
            double mutationDistributionIndex = 20.0;
            mutation = new IntegerDoubleMutation(mutationProbability, mutationDistributionIndex);
            selection = new BinaryTournamentSelection<IntegerDoubleSolution>(new RankingAndCrowdingDistanceComparator<IntegerDoubleSolution>());

            algorithm = new NSGAIIBuilder<IntegerDoubleSolution>(problem, crossover, mutation)
                    .setSelectionOperator(selection)
                    .setMaxEvaluations(MAX_ALLOWED_EVOLUTIONS)
                    .setPopulationSize(POPULATION_SIZE).setVariant(NSGAIIBuilder.NSGAIIVariant.Measures)
                    .build();

            AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                    .execute();

//        List<IntegerDoubleSolution> population = algorithm.getResult();
            List<IntegerDoubleSolution> population = algorithm.getResult();

            setPopulation(population);

            System.out.println("---------PARETO FRONT ------------");
            
            for (int i = 0; i < population.size(); i++) {
                System.out.println(population.get(i).getObjective(0) + " " + population.get(i).getObjective(1) + " " + population.get(i).getObjective(2));
            }

            for (int i = 0; i < population.size(); i++) {
//            System.out.println(population.get(i).getVariableValue(0) + " " + population.get(i).getVariableValue(1) + " " + population.get(i).getVariableValue(2) + " " + population.get(i).getVariableValue(3) + " " + population.get(i).getVariableValue(4));
            }

        } else if (QUANTITY == true) {
            List<IntegerDoubleSolution> population = new ArrayList();
            for (int a = 1; a < BESS_AMOUNT; a++) {

                problem = new Optimal(a + 1, 1,period);
                double crossoverProbability = 0.9;
                double crossoverDistributionIndex = 20.0;
                crossover = new IntegerDoubleCrossover(crossoverProbability, crossoverDistributionIndex);
                double mutationProbability = 1.0 / problem.getNumberOfVariables();
                double mutationDistributionIndex = 20.0;
                mutation = new IntegerDoubleMutation(mutationProbability, mutationDistributionIndex);
                selection = new BinaryTournamentSelection<IntegerDoubleSolution>(new RankingAndCrowdingDistanceComparator<IntegerDoubleSolution>());

                algorithm = new NSGAIIBuilder<IntegerDoubleSolution>(problem, crossover, mutation)
                        .setSelectionOperator(selection)
                        .setMaxEvaluations(MAX_ALLOWED_EVOLUTIONS)
                        .setPopulationSize(POPULATION_SIZE).setVariant(NSGAIIBuilder.NSGAIIVariant.Measures)
                        .build();

                AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                        .execute();

//        List<IntegerDoubleSolution> population = algorithm.getResult();
                population.addAll(algorithm.getResult());

                long computingTime = algorithmRunner.getComputingTime();

                System.out.println("---------PARETO FRONT " + a + "------------");
                for (int i = 0; i < population.size(); i++) {
                    System.out.println(population.get(i).getObjective(0) + " " + population.get(i).getObjective(1) + " " + population.get(i).getObjective(2));
                }

                for (int i = 0; i < population.size(); i++) {
//            System.out.println(population.get(i).getVariableValue(0) + " " + population.get(i).getVariableValue(1) + " " + population.get(i).getVariableValue(2) + " " + population.get(i).getVariableValue(3) + " " + population.get(i).getVariableValue(4));
                }

//    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
//            printFinalSolutionSet(population);
//    if (!referenceParetoFront.equals("")) {
//      printQualityIndicators(population, referenceParetoFront) ;
//    }
            }
             setPopulation(population);
        }
        long estimatedTime = System.currentTimeMillis() - startMeasure;
        System.out.println("poblacion " + population);
//        System.out.println("Computing Time: "+ computingTime);
        System.out.println("Simulation Time: " + estimatedTime);
    }

    public void setPopulation(List population) {
        this.population = population;
    }

    public List getPopulation() {
        return this.population;
    }

    private static class Listener implements MeasureListener<List<DoubleSolution>> {

        private int counter = 0;

        @Override
        synchronized public void measureGenerated(List<DoubleSolution> solutions) {
            if ((counter % 10 == 0)) {
                System.out.println("PUSH MEASURE. Counter = " + counter + " First solution: " + solutions.get(0));
            }
            counter++;
        }
    }

}

//        algorithm = new MOCellBuilder<>(problem, crossover, mutation)
//                .setSelectionOperator(selection)
//                .setMaxEvaluations(50000)
//                .setPopulationSize(100)
//                .setArchive(new CrowdingDistanceArchive<>(100))
//                .build();
//         algorithm = new SPEA2Builder<>(problem, crossover, mutation)
//        .setSelectionOperator(selection)
//        .setMaxIterations(250)
//        .setPopulationSize(100)
//        .build() ;
//        algorithm = new NSGAIIIBuilder<>(problem)
//                .setCrossoverOperator(crossover)
//                .setMutationOperator(mutation)
//                .setSelectionOperator(selection)
//                .setMaxIterations(300)
//                .setPopulationSize(1000)
//                .build();
//        MeasureManager measureManager = ((NSGAIIMeasures<IntegerDoubleSolution>) algorithm).getMeasureManager();
//        CountingMeasure currentEvalution
//                = (CountingMeasure) measureManager.<Long>getPullMeasure("currentEvaluation");
//        BasicMeasure<List<DoubleSolution>> solutionListMeasure
//                = (BasicMeasure<List<DoubleSolution>>) measureManager.<List<DoubleSolution>>getPushMeasure("currentPopulation");
//        CountingMeasure iteration2
//                = (CountingMeasure) measureManager.<Long>getPushMeasure("currentEvaluation");
//
//        solutionListMeasure.register(new Listener());
