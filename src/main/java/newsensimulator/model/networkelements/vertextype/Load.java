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
public class Load implements Serializable{

    private String name;
    private int loadType = 1;
    private int loadPriority = 0;
    private double loadMW = 0;
    private double loadMVar = 0;
    private boolean loadState = true;
    private int loadNumber = 0;
    private double[] percentPeak;

    public Load(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
/*
    public String toString() {
        return name;
    }
*/
    public void setLoadType(int loadType) {
        this.loadType = loadType;
    }

    public int getLoadType() {
        return loadType;
    }

    public void setLoadPriority(int loadPriority) {
        this.loadPriority = loadPriority;
    }

    public int getLoadPriority() {
        return loadPriority;
    }

    public void setLoadMW(double loadMW) {
        this.loadMW = loadMW;
    }

    public double getLoadMW() {
        return loadMW;
    }

    public void setLoadMVar(double loadMVar) {
        this.loadMVar = loadMVar;
    }

    public double getLoadMVar() {
        return loadMVar;
    }

    public void setLoadState(boolean loadState) {
        this.loadState = loadState;
    }

    public boolean getLoadState() {
        return loadState;
    }

    public int getLoadNumber() {
        return loadNumber;
    }

    public void setLoadNumber(int loadNumber) {
        this.loadNumber = loadNumber;
    }
    
    //Agregado por Lisa
    public void setPercentOfPeakLoad(double[] percentPeak){
        this.percentPeak = percentPeak;        
    }
    
    public double[] getPercentOfPeakLoad(){
        return percentPeak;
    }
}
