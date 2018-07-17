/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.problem.restorationservice;

import java.util.*;
import org.jgap.*;
import org.jgap.impl.*;

/**
 *
 * @author Liss
 */
public class GeneticAlgorithm {

    private static int MAX_ALLOWED_EVOLUTIONS;
    private static int POPULATION_SIZE;
    private static int PERCENTAGE_CROSS;
    private static int PERCENTAGE_MUTATION;

    public void setEvolutions(int evol) {
        this.MAX_ALLOWED_EVOLUTIONS = evol;
    }

    public void setPopulation(int pop) {
        this.POPULATION_SIZE = pop;
    }

    public void setCrossoverPercent(int cross) {
        this.PERCENTAGE_CROSS = cross;
    }

    public void setMutationPercent(int mut) {
        this.PERCENTAGE_MUTATION = mut;
    }

    public List runAG(List faults, List dataNetwork) throws InvalidConfigurationException {
        long startMeasure = System.currentTimeMillis();
        Configuration.reset();
        Configuration conf = new DefaultConfiguration();
        Configuration.resetProperty(Configuration.PROPERTY_FITEVAL_INST);
        conf.setFitnessEvaluator(new DefaultFitnessEvaluator());
        CentralizedRestorationFitnessFunction myFunc = new CentralizedRestorationFitnessFunction(faults, dataNetwork);
        conf.setFitnessFunction(myFunc);
        conf.setPreservFittestIndividual(true);
        List cycles = (List) dataNetwork.get(2);
        Gene[] sampleGenes = new Gene[cycles.size()];
        
        for (int i = 0; i < sampleGenes.length; i++) {
            sampleGenes[i] = new LineGene(conf, (List) cycles.get(i));
        }

        Chromosome sampleChromosome = new Chromosome(conf, sampleGenes);
        conf.setSampleChromosome(sampleChromosome);
        CrossoverOperator cross = new CrossoverOperator(conf, PERCENTAGE_CROSS);
        conf.addGeneticOperator(cross);
        MutationOperator mutation = new MutationOperator(conf, PERCENTAGE_MUTATION);
        conf.addGeneticOperator(mutation);
        NaturalSelector selector = new BestChromosomesSelector(conf) {
        };
        conf.addNaturalSelector(selector, true);
        conf.setPopulationSize(POPULATION_SIZE);
        Genotype population = Genotype.randomInitialGenotype(conf);

        List mejores = new ArrayList();
        //Evolucionar poblacion inicial configurada como aleatoria
        for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
            population.evolve();
            IChromosome best = population.getFittestChromosome();
            double loss = Math.abs(1 / best.getFitnessValue() - 1);
            mejores.add(loss);
        }

//        List losses = myFunc.getLosses();
        //Obtener cromosoma más apto
        IChromosome bestSolution = population.getFittestChromosome();
        long estimatedTime = System.currentTimeMillis() - startMeasure;
        List best = new ArrayList();
        for (int i = 0; i < bestSolution.size(); i++) {
            best.add(bestSolution.getGene(i).getAllele());
        }
        List initialcondition = new ArrayList((List) dataNetwork.get(3));
        List finalsolution = openClose(best, initialcondition, faults);
        //1 lineas a abrir, 2 lineas a cerrar, 3 pérdidas
        System.out.println("Abrir Lineas " + finalsolution.get(0) + " Cerrar Lineas" + finalsolution.get(1));
        System.out.println("Pérdidas: " + Math.abs(1 / bestSolution.getFitnessValue() - 1));
        List open = (List) finalsolution.get(0);
        List close = (List) finalsolution.get(1);
        System.out.println("Operaciones de conmutación " + (open.size() + close.size()));
        System.out.println("Tiempo de Simulación: " + estimatedTime + " milisegundos");

        System.out.println(mejores);

        finalsolution.add(Math.abs(1 / bestSolution.getFitnessValue() - 1));
        finalsolution.add(mejores);
        return finalsolution;
    }

    //método que define que líneas abrir y cuales cerrar según la solución encontrada
    //contando además la cantidad de conmutaciones
    public List openClose(List solution, List initialopen, List faults) {
        Collection intersection = new HashSet(initialopen);
        intersection.retainAll(solution);
        initialopen.removeAll(intersection);
        solution.removeAll(intersection);
        Collection intersection2 = new HashSet(initialopen);
        intersection2.retainAll(faults);
        initialopen.removeAll(intersection2);
        List openclose = new ArrayList();
        openclose.add(solution);
        openclose.add(initialopen);

        return openclose;
    }

}
