/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.networkelements.linetype;

import java.util.ArrayList;
import newsensimulator.control.Controller;

/**
 *
 * @author Jose Muñoz Parra
 */
public class Conductor {

    private double metersDistance;
    private double kilometersDistance;
    private int conductorType;
    private double maxCurrent;
    private double resistance;
    private double reactance;
    //revisar que es esto (Andrew)
    private int F;
    private int N;
    private double Rn;
    private double Xn;
    //Conductores por defecto
    //                               Resistance, Reactance, MaxCurrent, Distance[Km],  F   ,  N  ,  Rn  ,  Xn   
    private double[] defaultType1 = {1.01,        0.095,      0.096,      0.1,         3,     1,  0.744, 0.091};
    private double[] defaultType2 = {0.744,      0.0931,      0.117,      0.1,         3,     1,  0.744, 0.091};
    private double[] defaultType3 = {0.101,       0.328,      1.042,      0.1,         3,     1,  0.744, 0.091};
    
    private double[][] dataConductor = {
        // Cond    Imax     rl      xl      F       N       rn      xn           
        {1.0000, 0.0960, 1.0100, 0.0950, 3.0000, 1.0000, 0.7440, 0.0910},
        {2.0000, 0.1170, 0.7440, 0.0931, 3.0000, 1.0000, 0.7440, 0.0910},
        {3.0000, 1.0420, 0.1010, 0.3280, 3.0000, 1.0000, 0.7440, 0.0910}};

    private ArrayList<double[]> defaultConductors;

    private double[][] distancesBetweenConductors = {
        //  N       A       B       C  
        {0.0000, 0.2000, 0.4000, 0.6000}, // N
        {0.2000, 0.0000, 0.2000, 0.4000}, // A
        {0.4000, 0.2000, 0.0000, 0.2000}, // B
        {0.6000, 0.4000, 0.2000, 0.0000}};// C

    public Conductor(int conductorType) {

        defaultConductors = new ArrayList();
        defaultConductors.add(defaultType1);
        defaultConductors.add(defaultType2);
        defaultConductors.add(defaultType3);

        setCondutorType(conductorType);
        switch (conductorType) {
            case 1:
                setResistance(defaultType1[0]);
                setReactance(defaultType1[1]);
                setMaxCurrent(defaultType1[2]);
                setElectricalConductorDistanceInKilometers(defaultType1[3]);
                setF((int) defaultType1[4]);
                setN((int) defaultType1[5]);
                setRn(defaultType1[6]);
                setXn(defaultType1[7]);
                break;
            case 2:
                setResistance(defaultType2[0]);
                setReactance(defaultType2[1]);
                setMaxCurrent(defaultType2[2]);
                setElectricalConductorDistanceInKilometers(defaultType2[3]);
                setF((int) defaultType2[4]);
                setN((int) defaultType2[5]);
                setRn(defaultType2[6]);
                setXn(defaultType2[7]);
                break;
            case 3:
                setResistance(defaultType3[0]);
                setReactance(defaultType3[1]);
                setMaxCurrent(defaultType3[2]);
                setElectricalConductorDistanceInKilometers(defaultType3[3]);
                setF((int) defaultType3[4]);
                setN((int) defaultType3[5]);
                setRn(defaultType3[6]);
                setXn(defaultType3[7]);
                break;
            //default:
            //    System.out.println("Conductor vacio...");
            //    break;
        }
    }

    public double[][] getDataConductor(){
        return dataConductor;
    }
    
    public double[][] getDistancesBetweenConductors() {
        return distancesBetweenConductors;
    }

    public void setDistanceBetweenConductor(String phase1, String phase2, double distancesBetweenConductors) {
        int p1 = -1;
        int p2 = -1;

        switch (phase1) {
            case "N":
                p1 = 0;
                break;
            case "A":
                p1 = 1;
                break;
            case "B":
                p1 = 2;
                break;
            case "C":
                p1 = 3;
                break;
            default:
                Controller.getController().SENSimulatorConsolePrint("Formato de fase 1 erroneo. Se acepta A, B, C y N");
                break;
        }
        switch (phase2) {
            case "N":
                p2 = 0;
                break;
            case "A":
                p2 = 1;
                break;
            case "B":
                p2 = 2;
                break;
            case "C":
                p2 = 3;
                break;
            default:
                Controller.getController().SENSimulatorConsolePrint("Formato de fase 2 erroneo. Se acepta A, B, C y N");
                break;
        }
        this.distancesBetweenConductors[p1][p2] = distancesBetweenConductors;
        this.distancesBetweenConductors[p2][p1] = distancesBetweenConductors;
    }

    public double getDistanceBetweenConductor(String phase1, String phase2){
        int p1 = -1;
        int p2 = -1;

        switch (phase1) {
            case "N":
                p1 = 0;
                break;
            case "A":
                p1 = 1;
                break;
            case "B":
                p1 = 2;
                break;
            case "C":
                p1 = 3;
                break;
            default:
                Controller.getController().SENSimulatorConsolePrint("Formato de fase 1 erroneo. Se acepta A, B, C y N");
                break;
        }
        switch (phase2) {
            case "N":
                p2 = 0;
                break;
            case "A":
                p2 = 1;
                break;
            case "B":
                p2 = 2;
                break;
            case "C":
                p2 = 3;
                break;
            default:
                Controller.getController().SENSimulatorConsolePrint("Formato de fase 2 erroneo. Se acepta A, B, C y N");
                break;
        }
        return distancesBetweenConductors[p1][p2];
    }
    
    public ArrayList getDefaultConductorList() {
        return defaultConductors;
    }

    public void setCondutorType(int conductorType) {
        this.conductorType = conductorType;
    }

    public int getCondutorType() {
        return conductorType;
    }

    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    public double getResistance() {
        return resistance;
    }

    public void setReactance(double reactance) {
        this.reactance = reactance;
    }

    public double getReactance() {
        return reactance;
    }

    public void setMaxCurrent(double maxCurrent_kA) {
        this.maxCurrent = maxCurrent_kA;
    }

    public double getMaxCurrent() {
        return maxCurrent;
    }

    public void setElectricalConductorDistanceInMeters(double metersDistance) {
        this.metersDistance = metersDistance;
        this.kilometersDistance = metersDistance / 1000;
    }

    public double getElectricalConductorDistanceInMeters() {
        return metersDistance;
    }

    public void setElectricalConductorDistanceInKilometers(double kilometersDistance) {
        this.kilometersDistance = kilometersDistance;
        this.metersDistance = kilometersDistance * 1000;
    }

    public double getElectricalConductorDistanceInKilometers() {
        return kilometersDistance;
    }

    public void setF(int F) {
        this.F = F;
    }

    public int getF() {
        return F;
    }

    public void setN(int N) {
        this.N = N;
    }

    public int getN() {
        return N;
    }

    public void setRn(double Rn) {
        this.Rn = Rn;
    }

    public double getRn() {
        return Rn;
    }

    public void setXn(double Xn) {
        this.Xn = Xn;
    }

    public double getXn() {
        return Xn;
    }

}
