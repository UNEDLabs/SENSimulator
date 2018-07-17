/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.networkelements.vertextype;

import java.io.Serializable;

/**
 *
 * @author Jose Muñoz Parra
 */
public class Generator implements Serializable {

    private String name;
    private boolean generatorState;
    private int generatorType;
    private double MWGen;
    private double MVarGen;
    private double minMVarGen;
    private double maxMVarGen;
    private int generatorNumber;
    private boolean isolateMode = false;

    public Generator(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setGeneratorState(boolean generatorState) {
        this.generatorState = generatorState;
    }

    public boolean getGeneratorState() {
        return generatorState;
    }

    public void setGeneratorType(int type) {
        this.generatorType = type;
    }

    public int getGeneratorType() {
        return generatorType;
    }

    public void setMWGenerator(double MWGen) {
        this.MWGen = MWGen;
    }

    public double getMWGenerator() {
        return MWGen;
    }

    public void setMVarGenerator(double MVarGen) {
        this.MVarGen = MVarGen;
    }

    public double getMVarGenerator() {
        return MVarGen;
    }

    public void setMinMVarGenerator(double minMVarGen) {
        this.minMVarGen = minMVarGen;
    }

    public double getMinMVarGenerator() {
        return minMVarGen;
    }

    public void setMaxMVarGenerator(double maxMVarGen) {
        this.maxMVarGen = maxMVarGen;
    }

    public double getMaxMVarGenerator() {
        return maxMVarGen;
    }

    public int getGeneratorNumber() {
        return generatorNumber;
    }

    public void setGeneratorNumber(int generatorNumber) {
        this.generatorNumber = generatorNumber;
    }

//Agregado por Lisa:
    public void setIsolateState(boolean isolateMode) {
        this.isolateMode = isolateMode;
    }

    public boolean getIsolateState() {
        return isolateMode;
    }

}
