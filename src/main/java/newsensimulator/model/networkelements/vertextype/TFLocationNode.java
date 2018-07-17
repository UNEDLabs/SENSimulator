/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.networkelements.vertextype;

import newsensimulator.control.Controller;

/**
 *
 * @author Jose Muñoz Parra
 */
public class TFLocationNode {

    private String name;
    private int numberTFLocationNode;
    private boolean transformer = false;
    private double va = 1;
    private double vb = 1;
    private double vc = 1;
    private double angleA = 0;
    private double angleB = 0;
    private double angleC = 0;
    private double activePowerA = 0;
    private double activePowerB = 0;
    private double activePowerC = 0;
    private double reactivePowerA = 0;
    private double reactivePowerB = 0;
    private double reactivePowerC = 0;
    private int NCli = 0;
    private boolean MTNode = false;

    public TFLocationNode(String name) {
        this.name = name;
    }
    
    public void setMTNodeStatus(boolean connectionMT){
        this.MTNode = connectionMT;
    }
    
    public boolean getMTNodeStatus(){
        return MTNode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberNode() {
        return numberTFLocationNode;
    }

    public void setNumberNode(int numberBus) {
        this.numberTFLocationNode = numberBus;
    }

    public boolean isTransformerNode() {
        return transformer;
    }

    public void setTransformerNode(boolean transformer) {
        this.transformer = transformer;
    }

    public double[] getThreePhaseVoltage() {
        double[] aux = {va, vb, vc};
        return aux;
    }

    public void setThreePhaseVoltage(double[] threePhaseVoltage) {
        if (threePhaseVoltage.length == 3) {
            va = threePhaseVoltage[0];
            vb = threePhaseVoltage[1];
            vc = threePhaseVoltage[2];
        } else {
            Controller.getController().SENSimulatorConsolePrint("Error!: ThreePhaseVoltage var.");
        }
    }

    public double[] getThreePhaseAngle() {
        double[] aux = {angleA, angleB, angleC};
        return aux;
    }

    public void setThreePhaseAngle(double[] threePhaseAngle) {
        if (threePhaseAngle.length == 3) {
            angleA = threePhaseAngle[0];
            angleB = threePhaseAngle[1];
            angleC = threePhaseAngle[2];
        } else {
            Controller.getController().SENSimulatorConsolePrint("Error!: threePhaseAngle var.");
        }
    }

    public double[] getThreePhaseActivePower() {
        double[] aux = {activePowerA, activePowerB, activePowerC};
        return aux;
    }

    public void getThreePhaseActivePower(double[] threePhaseActivePower) {
        if (threePhaseActivePower.length == 3) {
            activePowerA = threePhaseActivePower[0];
            activePowerB = threePhaseActivePower[1];
            activePowerC = threePhaseActivePower[2];
        } else {
            Controller.getController().SENSimulatorConsolePrint("Error!: threePhaseActivePower var.");
        }
    }

    public double[] getThreePhaseReactivePower() {
        double[] aux = {reactivePowerA, reactivePowerB, reactivePowerC};
        return aux;
    }

    public void getThreePhaseReactivePower(double[] threePhaseReactivePower) {
        if (threePhaseReactivePower.length == 3) {
            reactivePowerA = threePhaseReactivePower[0];
            reactivePowerB = threePhaseReactivePower[1];
            reactivePowerC = threePhaseReactivePower[2];
        } else {
            Controller.getController().SENSimulatorConsolePrint("Error!: threePhaseReactivePower var.");
        }
    }

    public double getVoltagePhase_A() {
        return va;
    }

    public void setVoltagePhase_A(double va) {
        this.va = va;
    }

    public double getVoltagePhase_B() {
        return vb;
    }

    public void setVoltagePhase_B(double vb) {
        this.vb = vb;
    }

    public double getVoltagePhase_C() {
        return vc;
    }

    public void setVoltagePhase_C(double vc) {
        this.vc = vc;
    }

    public double getAnglePhase_A() {
        return angleA;
    }

    public void setAnglePhase_A(double angleA) {
        this.angleA = angleA;
    }

    public double getAnglePhase_B() {
        return angleB;
    }

    public void setAnglePhase_B(double angleB) {
        this.angleB = angleB;
    }

    public double getAnglePhase_C() {
        return angleC;
    }

    public void setAnglePhase_C(double angleC) {
        this.angleC = angleC;
    }

    public double getActivePowerPhase_A() {
        return activePowerA;
    }

    public void setActivePowerPhase_A(double activePowerA) {
        this.activePowerA = activePowerA;
    }

    public double getActivePowerPhase_B() {
        return activePowerB;
    }

    public void setActivePowerPhase_B(double activePowerB) {
        this.activePowerB = activePowerB;
    }

    public double getActivePowerPhase_C() {
        return activePowerC;
    }

    public void setActivePowerPhase_C(double activePowerC) {
        this.activePowerC = activePowerC;
    }
    
    public double getReactivePowerPhase_A() {
        return reactivePowerA;
    }

    public void setReactivePowerPhase_A(double reactivePowerA) {
        this.reactivePowerA = reactivePowerA;
    }

    public double getReactivePowerPhase_B() {
        return reactivePowerB;
    }

    public void setReactivePowerPhase_B(double reactivePowerB) {
        this.reactivePowerB = reactivePowerB;
    }

    public double getReactivePowerPhase_C() {
        return reactivePowerC;
    }

    public void setReactivePowerPhase_C(double reactivePowerC) {
        this.reactivePowerC = reactivePowerC;
    }
    
    public int getNCli(){
        return NCli;
    }
    
    public void setNCli(int ncli){
        this.NCli =ncli;
    }
}
