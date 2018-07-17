/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.networkelements.vertextype;

import java.io.Serializable;

/**
 *
 * @author Liss
 */
public class Battery implements Serializable{

    private String name;
    private boolean batteryState;
    private int batteryType;
    private int batteryNumber;
    private double batteryMW;
    private double batteryMWh;
    private double efficiency;
    private int cycles;

    public Battery(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public void setBatteryMW(double batteryMW){
        this.batteryMW=batteryMW;
    }
    
    public void setBatteryState(boolean batteryState) {
        this.batteryState = batteryState;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBatteryMWh(double batteryMWh) {
        this.batteryMWh = batteryMWh;
    }

    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
    }

    public void setCycles(int cycles) {
        this.cycles = cycles;
    }

    public double getBatteryMW() {
        return batteryMW;
    }

    public double getBatteryMWh() {
        return batteryMWh;
    }

    public double getEfficiency() {
        return efficiency;
    }

    public int getCycles() {
        return cycles;
    }


    public boolean getBatteryState() {
        return batteryState;
    }
    
    
        //0 descarga,  1 carga
    public int getBatteryType() {
        return batteryType;
    }

    public void setBatteryType(int batteryType) {
        this.batteryType = batteryType;
    }

    public int getBatteryNumber() {
        return batteryNumber;
    }

    public void setBatteryNumber(int batteryNumber) {
        this.batteryNumber = batteryNumber;
    }
}

    
    
    

