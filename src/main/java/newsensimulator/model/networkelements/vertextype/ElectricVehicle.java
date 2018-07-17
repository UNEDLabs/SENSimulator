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
public class ElectricVehicle implements Serializable{

    private String name;
    private boolean carState;
    private int carType;
    private int EVNumber;

    public ElectricVehicle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setElectricVehicleState(boolean carState) {
        this.carState = carState;
    }

    public boolean getElectricVehicleState() {
        return carState;
    }

    public int getElectricVehicleType() {
        return carType;
    }

    public void setElectricVehicleType(int carType) {
        this.carType = carType;
    }

    public int getElectricVehicleNumber() {
        return EVNumber;
    }

    public void setElectricVehicleNumber(int EVNumber) {
        this.EVNumber = EVNumber;
    }
}
