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
 * @author Liss
 */
public class BatteryFactory implements Factory<Battery> {

    private int genCount = 0;
    private static BatteryFactory instance = new BatteryFactory();
    private int batteryType = 0;

    private BatteryFactory() {
        System.out.println("Creando instancia de Battery...");
    }

    public static BatteryFactory getInstance() {
        return instance;
    }

    public Battery create() {
        int numeroBateria;

        numeroBateria = ElementCounter.getElementCounter().getBatteryNumberAvailable();

        String name = "BT" + numeroBateria;
        Battery bat = new Battery(name);
        bat.setBatteryNumber(numeroBateria);
        bat.setBatteryState(true);
        bat.setBatteryType(batteryType);

        return bat;
    }

    public Battery create(String name) {
        int numeroBateria;

        numeroBateria = Integer.parseInt(name.substring(2));
        ElementCounter.getElementCounter().setBatteryNumberAvailable(numeroBateria);
        //ElementCounter.getElementCounter().getElectricVehicleNumberAvailable();

        //String name = "EV" + numeroVehiculoElectrico;
        Battery bat = new Battery(name);
        bat.setBatteryNumber(numeroBateria);
        bat.setBatteryState(true);
        bat.setBatteryType(batteryType);

        return bat;
    }

}
