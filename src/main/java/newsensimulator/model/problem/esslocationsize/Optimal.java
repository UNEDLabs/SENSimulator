package newsensimulator.model.problem.esslocationsize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.uma.jmetal.problem.impl.AbstractIntegerDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.IntegerDoubleSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerDoubleSolution;
import org.uma.jmetal.util.solutionattribute.impl.OverallConstraintViolation;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Liss
 */
public class Optimal extends AbstractIntegerDoubleProblem<IntegerDoubleSolution> {

    public List dataNetwork;
    public List totalLoss;
    public List totalMagVolt;
    public List posibleCosts;
    public double investmentCost;
    public OverallConstraintViolation<IntegerDoubleSolution> overallConstraintViolationDegree;
    public double maintenanceCost = 30 * 1000;
    public double lossesCost;
    public double depthDischarge = 0.8;
    public double energyCost = 90.3;
    public double efficiency = 0.70;
    public double operationDays = 200;
//    public double peakLoadCost=5922.55;
    public double peakLoadCost = 9970.5;
    public double months = 7;
    double scale = (Math.pow(10, 4));
    public double distributionCost;
    public double[] minData;
    private double peakLoad;
    public int[] advancedLA = {330 * 1000, 400 * 1000, 4500};
    public int[] znbFlow = {400 * 1000, 400 * 1000, 10000};
    public int[] vanadiumRedox = {600 * 1000, 400 * 1000, 10000};
    public int[] fecrFlow = {330 * 1000, 400 * 1000, 10000};
    public int[] liIon = {600 * 1000, 400 * 1000, 4500};
    public int intVariables;
    public int realVariables;
    private double n;
    public int[] NaS = {450, 2000, 4500};
    public int[] VRB = {600, 600, 10000};
    public int[] ZnBR = {700, 500, 3000};
    public int[] liion = {900, 600, 10000};
    public int[] leadAcid = {600, 400, 2000};
    public int[] feCrflow = {300, 1200, 10000};

    /**
     * Constructor. Creates a default instance of the IntRealProblem problem.
     */
    public Optimal() {
        this(2, 1, 15);
    } // IntRealProblem
    private static Optimal instance;

    public static Optimal getInstance() {
        if (instance == null) {
            instance = new Optimal();
        }
        return instance;
    }
    
    

    /**
     * Constructor. Creates a new instance of the IntRealProblem problem.
     *
     * @param intVariables Number of integer variables of the problem
     * @param realVariables Number of real variables of the problem
     */
    public Optimal(int intVariables, int realVariables, double period) {
        this.intVariables = intVariables;
        this.realVariables = realVariables;
        this.n = period;
        setNumberOfIntegerVariables(intVariables);
        setNumberOfDoubleVariables(realVariables);
        setNumberOfVariables(realVariables + intVariables);
        List allData = DailyLoad.getInstance().powerFlowPerHour();
        this.dataNetwork = new ArrayList((List) allData.get(0));
        this.totalLoss = new ArrayList((List) allData.get(1));
        this.totalMagVolt = new ArrayList((List) allData.get(2));
        
        int nodes = ((double[][]) ((List) this.dataNetwork.get(0)).get(0)).length;
        double maxLoad = Collections.max((List<Double>) this.dataNetwork.get(2));
        int numberOfVariables_ = getNumberOfVariables();
//        System.out.println("VARIABLES: " + numberOfVariables_);
        setNumberOfObjectives(3);
        setNumberOfConstraints(1);

        List upperLimit_ = new ArrayList();
        List lowerLimit_ = new ArrayList();

        //cantidad de nodos
        for (int i = 0; i < intVariables - 1; i++) {
            lowerLimit_.add(2);
            upperLimit_.add(nodes);
        }
        // for
        //tipo de BESS
        lowerLimit_.add(1);
        upperLimit_.add(6);
        //tamaño de bess power
        lowerLimit_.add(0.0);
        upperLimit_.add(maxLoad * 0.2);
        setLowerLimit(lowerLimit_);
        setUpperLimit(upperLimit_);

//        this.posibleCosts = new ArrayList(Arrays.asList(advancedLA, znbFlow, vanadiumRedox, fecrFlow, liIon));
        this.posibleCosts = new ArrayList(Arrays.asList(NaS, VRB, ZnBR, liion, leadAcid, feCrflow));

        overallConstraintViolationDegree = new OverallConstraintViolation<IntegerDoubleSolution>();
    } // constructor

    @Override
    public void evaluate(IntegerDoubleSolution solution) {
        int[] varInt = new int[this.intVariables];
        double[] varDo = new double[this.realVariables];

        for (int i = 0; i < varInt.length; i++) {
            varInt[i] = solution.getVariableValue(i).intValue();
//            System.out.println(varInt[i]);
        }

        for (int i = varInt.length; i < solution.getNumberOfVariables(); i++) {
            varDo[i - varInt.length] = solution.getVariableValue(i).doubleValue();
//            System.out.println("double: " + varDo[i-solution.getNumberOfIntegerVariables()]);
        }
//        System.out.println("----------");

        double[] fx = new double[2]; // function values     
        fx[0] = 0.0;
        fx[1] = 0.0;

        int[] nodes = new int[varInt.length - 1];
//        int[] nodes = new int[];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = varInt[i];

        }

        int indexCost = solution.getVariableValue(nodes.length).intValue();
        int[] posibleCost = (int[]) this.posibleCosts.get(indexCost - 1); //valor de costos -1


        double power = (double) solution.getVariableValue(solution.getNumberOfVariables() - 1);


        
        power = (double) (Math.round(power * scale) / scale);
        double energy = power * 4 / 0.8;

        List result = new ArrayList();
        result = evaluateBESS(power, nodes);

        List<Double> ev_losses = new ArrayList();
        ev_losses.clear();
        ev_losses = (List) result.get(0);
        double ev_lossestotal = 0;
        ev_lossestotal = ev_losses.stream().mapToDouble(o -> o.doubleValue()).sum();
//        for (Object ev_losse : ev_losses) {
//            ev_lossestotal += (double) ev_losse;
//        }
        List<Double> ev_volt = new ArrayList();
        ev_volt = (List) result.get(1);
        double ev_magvolt = 0;
        ev_magvolt = ev_volt.stream().mapToDouble(o -> o.doubleValue()).sum();

        double or_magvolt = 0;
        for (Object or : this.totalMagVolt) {
            or_magvolt += (double) or;
        }

        double or_losses = 0;
        for (Object or : this.totalLoss) {
            or_losses += (double) or;
        }

        List load = new ArrayList();
        load.addAll((List) this.dataNetwork.get(2));

//EVALUACIÓN DE RESTRICCIÓN
        List<List> loadbility = (List) result.get(2);
        double overallConstraintViolation = 0.0;
        for (List loadbi : loadbility) {
            double maxLoad = (double) Collections.max(loadbi);
            if (maxLoad >= 1) {
                overallConstraintViolation += maxLoad;
            }
        }
        overallConstraintViolationDegree.setAttribute(solution, overallConstraintViolation);

        //----       
        for (int i = 16; i < 20; i++) {
            double d = (double) load.get(i);
            load.set(i, d - power);
        }

        peakLoad = (double) Collections.max(load);

        double totalCost = 0;

//        double n = posibleCost[2] / operationDays;
//        double n = 15;
        double FAC = (16.67) * (1 - Math.pow(0.94545, n));
        double FAU = 10 * (1 - Math.pow(0.909, n));
        investmentCost = (posibleCost[0] * energy) + (posibleCost[1] * power);
        lossesCost = energy * depthDischarge * energyCost * efficiency * operationDays;
        distributionCost = (or_losses * energyCost * operationDays + peakLoadCost * peakLoad * months) * n * FAC;
        totalCost = investmentCost + (maintenanceCost * power * n + lossesCost * n) * FAU + ((peakLoadCost * (peakLoad - power) * months * n) + (ev_lossestotal * energyCost * operationDays * n)) * FAC;

//        if (totalCost < distributionCost && ev_lossestotal < or_losses) {
//            System.out.println("total: " + totalCost + " distr: " + distributionCost);
//            System.out.println("perdidas eval:" + ev_lossestotal + " perdidas orig " + or_losses);
//            System.out.println("perdidas orig " + or_losses);
//        totalCosts.add(totalCost);
        double best1 = (totalCost / distributionCost);
        best1 = (Math.round(best1 * scale) / scale);
        double best2 = (ev_lossestotal / or_losses);
        best2 = (Math.round(best2 * scale) / scale);
        double best3 = (ev_magvolt / or_magvolt);
        best3 = (Math.round(best3 * scale) / scale);

        fx[0] = best1;
        fx[1] = best2;
//        System.out.println(best1 + " " + best2+ " "+best3);
        solution.setObjective(0, best1);
        solution.setObjective(1, best2);
        solution.setObjective(2, best3);

    } // evaluate

    public List evaluateBESS(double power, int[] nodes) {
        List dataBuses = (List) this.dataNetwork.get(0);
        List testBus = new ArrayList();
        testBus.clear();

        power = power / nodes.length;
        double chargepower = (power * 4 / efficiency) / 7;
//        testBus.addAll(dataBuses);
//        System.out.println(chargepower);

        for (Object dataBus : dataBuses) {
            double[][] bus = (double[][]) dataBus;
            double[][] dataTest = new double[bus.length][bus[0].length];
            for (int i = 0; i < bus.length; i++) {
                System.arraycopy(bus[i], 0, dataTest[i], 0, bus[0].length);
            }
            testBus.add(dataTest);
        }
        //carga BESS
        for (int i = 0; i < 7; i++) {
            double[][] b = (double[][]) testBus.get(i);
            for (int j = 0; j < nodes.length; j++) {
//                System.out.println(b[nodes[j] - 1][4]);
                b[nodes[j] - 1][4] = b[nodes[j] - 1][4] + chargepower;

            }
        }
        //descarga de bess

        for (int i = 16; i < 20; i++) {
            double[][] b = (double[][]) testBus.get(i);
            for (int j = 0; j < nodes.length; j++) {
                b[nodes[j] - 1][6] = b[nodes[j] - 1][6] + power;
            }
        }

        List datas = new ArrayList();
        datas.add(testBus);
        datas.add(this.dataNetwork.get(1));
        datas.add(this.dataNetwork.get(2));
        datas.add(this.dataNetwork.get(3));

        List losses = new ArrayList();
        List powerFlowResult = DailyLoad.getInstance().dailyPowerFlow(datas);

//        losses.clear();
//        losses.addAll((List) powerFlowResult.get(0));
//        System.out.println(losses);
        return powerFlowResult;
//        return losses;
    }

    @Override
    public IntegerDoubleSolution createSolution() {
        return new DefaultIntegerDoubleSolution(this);
    }

}
