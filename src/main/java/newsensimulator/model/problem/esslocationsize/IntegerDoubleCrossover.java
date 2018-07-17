package newsensimulator.model.problem.esslocationsize;


import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.util.RepairDoubleSolution;
import org.uma.jmetal.solution.util.RepairDoubleSolutionAtBounds;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.pseudorandom.RandomGenerator;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.problem.IntegerDoubleProblem;
import org.uma.jmetal.solution.IntegerDoubleSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerDoubleSolution;

/**
 * This class allows to apply a SBX crossover operator using two parent
 * solutions (Double encoding). A {@link RepairDoubleSolution} object is used to
 * decide the strategy to apply when a value is out of range.
 *
 * The implementation is based on the NSGA-II code available in
 * <a href="http://www.iitk.ac.in/kangal/codes.shtml">http://www.iitk.ac.in/kangal/codes.shtml</a>
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 * @author Juan J. Durillo
 */
@SuppressWarnings("serial")
public class IntegerDoubleCrossover implements CrossoverOperator<IntegerDoubleSolution> {

    /**
     * EPS defines the minimum difference allowed between real values
     */
    private static final double EPS = 1.0e-14;

    private double distributionIndex;
    private double crossoverProbability;
    private RepairDoubleSolution solutionRepair;

    private RandomGenerator<Double> randomGenerator;

    /**
     * Constructor
     */
    public IntegerDoubleCrossover(double crossoverProbability, double distributionIndex) {
        this(crossoverProbability, distributionIndex, new RepairDoubleSolutionAtBounds());
    }

    /**
     * Constructor
     */
    public IntegerDoubleCrossover(double crossoverProbability, double distributionIndex, RandomGenerator<Double> randomGenerator) {
        this(crossoverProbability, distributionIndex, new RepairDoubleSolutionAtBounds(), randomGenerator);
    }

    /**
     * Constructor
     */
    public IntegerDoubleCrossover(double crossoverProbability, double distributionIndex, RepairDoubleSolution solutionRepair) {
        this(crossoverProbability, distributionIndex, solutionRepair, () -> JMetalRandom.getInstance().nextDouble());
    }

    /**
     * Constructor
     */
    public IntegerDoubleCrossover(double crossoverProbability, double distributionIndex, RepairDoubleSolution solutionRepair, RandomGenerator<Double> randomGenerator) {
        if (crossoverProbability < 0) {
            throw new JMetalException("Crossover probability is negative: " + crossoverProbability);
        } else if (distributionIndex < 0) {
            throw new JMetalException("Distribution index is negative: " + distributionIndex);
        }

        this.crossoverProbability = crossoverProbability;
        this.distributionIndex = distributionIndex;
        this.solutionRepair = solutionRepair;

        this.randomGenerator = randomGenerator;
    }

    /* Getters */
    public double getCrossoverProbability() {
        return crossoverProbability;
    }

    public double getDistributionIndex() {
        return distributionIndex;
    }

    /* Setters */
    public void setCrossoverProbability(double probability) {
        this.crossoverProbability = probability;
    }

    public void setDistributionIndex(double distributionIndex) {
        this.distributionIndex = distributionIndex;
    }

    /**
     * Execute() method
     */
    public List<IntegerDoubleSolution> execute(List<IntegerDoubleSolution> solutions) {
        if (null == solutions) {
            throw new JMetalException("Null parameter");
        } else if (solutions.size() != 2) {
            throw new JMetalException("There must be two parents instead of " + solutions.size());
        }
        return doCrossover(crossoverProbability, solutions.get(0), solutions.get(1));
    }

    /**
     * doCrossover method
     */
    public List doCrossover(
            double probability, IntegerDoubleSolution pa1, IntegerDoubleSolution pa2) {
        List<DefaultIntegerDoubleSolution> offspring = new ArrayList<DefaultIntegerDoubleSolution>(2);      
        
//        System.out.println(pa1);
//        System.out.println(pa2);
        
        DefaultIntegerDoubleSolution parent1 = (DefaultIntegerDoubleSolution) pa1.copy();
        DefaultIntegerDoubleSolution parent2 = (DefaultIntegerDoubleSolution) pa2.copy();

        for (int i = 0; i < pa1.getNumberOfVariables(); i++) {
            parent1.setVariableValue(i, pa1.getVariableValue(i));
            
        }

        for (int i = 0; i < pa2.getNumberOfVariables(); i++) {
            parent2.setVariableValue(i, pa2.getVariableValue(i));
        }
        
        offspring.add((DefaultIntegerDoubleSolution) parent1);
        offspring.add((DefaultIntegerDoubleSolution) parent2);
        
 //
//        offspring.add((IntegerDoubleSolution) parent1);
//        offspring.add((IntegerDoubleSolution) parent2);
        int i;
        double rand;
        double y1, y2, yL, yu, lowerBound, upperBound;
        double c1, c2;
        double alpha, beta, betaq;
        int valueX1, valueX2;

        if (randomGenerator.getRandomValue() <= probability) {
            
            for (i = 0; i < Optimal.getInstance().getNumberOfIntegerVariables(); i++) {
//      for (i = 0; i < parent1.getNumberOfVariables(); i++) {

                    valueX1 = parent1.getVariableValue(i).intValue();
                    valueX2 = parent2.getVariableValue(i).intValue();
                    if (randomGenerator.getRandomValue() <= 0.5) {
                        if (Math.abs(valueX1 - valueX2) > EPS) {

                            if (valueX1 < valueX2) {
                                y1 = valueX1;
                                y2 = valueX2;
                            } else {
                                y1 = valueX2;
                                y2 = valueX1;
                            }

                            yL = parent1.getLowerBound(i).intValue();
                            yu = parent1.getUpperBound(i).intValue();
                            rand = randomGenerator.getRandomValue();
                            beta = 1.0 + (2.0 * (y1 - yL) / (y2 - y1));
                            alpha = 2.0 - Math.pow(beta, -(distributionIndex + 1.0));

                            if (rand <= (1.0 / alpha)) {
                                betaq = Math.pow((rand * alpha), (1.0 / (distributionIndex + 1.0)));
                            } else {
                                betaq = Math
                                        .pow(1.0 / (2.0 - rand * alpha), 1.0 / (distributionIndex + 1.0));
                            }

                            c1 = 0.5 * ((y1 + y2) - betaq * (y2 - y1));
                            beta = 1.0 + (2.0 * (yu - y2) / (y2 - y1));
                            alpha = 2.0 - Math.pow(beta, -(distributionIndex + 1.0));

                            if (rand <= (1.0 / alpha)) {
                                betaq = Math.pow((rand * alpha), (1.0 / (distributionIndex + 1.0)));
                            } else {
                                betaq = Math
                                        .pow(1.0 / (2.0 - rand * alpha), 1.0 / (distributionIndex + 1.0));
                            }

                            c2 = 0.5 * (y1 + y2 + betaq * (y2 - y1));

                            if (c1 < yL) {
                                c1 = yL;
                            }

                            if (c2 < yL) {
                                c2 = yL;
                            }

                            if (c1 > yu) {
                                c1 = yu;
                            }

                            if (c2 > yu) {
                                c2 = yu;
                            }

                            if (randomGenerator.getRandomValue() <= 0.5) {
                                offspring.get(0).setVariableValue(i, (int) c2);
                                offspring.get(1).setVariableValue(i, (int) c1);
                            } else {
                                offspring.get(0).setVariableValue(i, (int) c1);
                                offspring.get(1).setVariableValue(i, (int) c2);
                            }
                        } else {
                            offspring.get(0).setVariableValue(i, (int) valueX1);
                            offspring.get(1).setVariableValue(i, (int) valueX2);
                        }
                    } else {
                        offspring.get(0).setVariableValue(i, (int) valueX2);
                        offspring.get(1).setVariableValue(i, (int) valueX1);
                    }
                

            }

            double value1, value2;
            
            for (i = Optimal.getInstance().getNumberOfIntegerVariables(); i < Optimal.getInstance().getNumberOfDoubleVariables(); i++) {
//      for (i = 0; i < parent1.getNumberOfVariables(); i++) {
                value1 = parent1.getVariableValue(i).doubleValue();
                value2 = parent2.getVariableValue(i).doubleValue();
                if (randomGenerator.getRandomValue() <= 0.5) {
                    if (Math.abs(value1 - value2) > EPS) {

                        if (value1 < value2) {
                            y1 = value1;
                            y2 = value2;
                        } else {
                            y1 = value2;
                            y2 = value1;
                        }

                        lowerBound = parent1.getLowerBound(i).doubleValue();
                        upperBound = parent1.getUpperBound(i).doubleValue();

                        rand = randomGenerator.getRandomValue();
                        beta = 1.0 + (2.0 * (y1 - lowerBound) / (y2 - y1));
                        alpha = 2.0 - Math.pow(beta, -(distributionIndex + 1.0));

                        if (rand <= (1.0 / alpha)) {
                            betaq = Math.pow(rand * alpha, (1.0 / (distributionIndex + 1.0)));
                        } else {
                            betaq = Math
                                    .pow(1.0 / (2.0 - rand * alpha), 1.0 / (distributionIndex + 1.0));
                        }
                        c1 = 0.5 * (y1 + y2 - betaq * (y2 - y1));

                        beta = 1.0 + (2.0 * (upperBound - y2) / (y2 - y1));
                        alpha = 2.0 - Math.pow(beta, -(distributionIndex + 1.0));

                        if (rand <= (1.0 / alpha)) {
                            betaq = Math.pow((rand * alpha), (1.0 / (distributionIndex + 1.0)));
                        } else {
                            betaq = Math
                                    .pow(1.0 / (2.0 - rand * alpha), 1.0 / (distributionIndex + 1.0));
                        }
                        c2 = 0.5 * (y1 + y2 + betaq * (y2 - y1));

                        c1 = solutionRepair.repairSolutionVariableValue(c1, lowerBound, upperBound);
                        c2 = solutionRepair.repairSolutionVariableValue(c2, lowerBound, upperBound);

                        if (randomGenerator.getRandomValue() <= 0.5) {
                            offspring.get(0).setVariableValue(i, c2);
                            offspring.get(1).setVariableValue(i, c1);
                        } else {
                            offspring.get(0).setVariableValue(i, c1);
                            offspring.get(1).setVariableValue(i, c2);
                        }
                    } else {
                        offspring.get(0).setVariableValue(i, value1);
                        offspring.get(1).setVariableValue(i, value2);
                    }
                } else {
                    offspring.get(0).setVariableValue(i, value1);
                    offspring.get(1).setVariableValue(i, value2);
                }
            }
        }

//        System.out.println("OFFSPRING OUT" + offspring);
        return offspring;

    }

    @Override
    public int getNumberOfRequiredParents() {
        return 2;
    }

    @Override
    public int getNumberOfGeneratedChildren() {
        return 2;
    }
}
