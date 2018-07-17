/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.networkelements.vertextype;

import newsensimulator.model.utils.ElementCounter;
import org.apache.commons.collections15.Factory;

/**
 *
 * @author Jose Muñoz Parra
 */
public class ElectricVehicleFactory implements Factory<ElectricVehicle> {

    private int genCount = 0;
    private static ElectricVehicleFactory instance = new ElectricVehicleFactory();
    private int catType = 0;

    private ElectricVehicleFactory() {
        System.out.println("Creando instancia del ElectricVehicle...");
    }

    public static ElectricVehicleFactory getInstance() {
        return instance;
    }

    public ElectricVehicle create() {
        int numeroVehiculoElectrico;

        numeroVehiculoElectrico = ElementCounter.getElementCounter().getElectricVehicleNumberAvailable();

        String name = "EV" + numeroVehiculoElectrico;
        ElectricVehicle ev = new ElectricVehicle(name);
        ev.setElectricVehicleNumber(numeroVehiculoElectrico);
        ev.setElectricVehicleState(true);
        ev.setElectricVehicleType(catType);

        return ev;
    }
    
    
    public ElectricVehicle create(String name) {
        int numeroVehiculoElectrico;

        numeroVehiculoElectrico = Integer.parseInt(name.substring(2));
        ElementCounter.getElementCounter().setElectricVehicleNumberAvailable(numeroVehiculoElectrico);
        //ElementCounter.getElementCounter().getElectricVehicleNumberAvailable();

        //String name = "EV" + numeroVehiculoElectrico;
        ElectricVehicle ev = new ElectricVehicle(name);
        ev.setElectricVehicleNumber(numeroVehiculoElectrico);
        ev.setElectricVehicleState(true);
        ev.setElectricVehicleType(catType);

        return ev;
    }
}
