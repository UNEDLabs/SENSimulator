/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.networkelements.vertextype;

import newsensimulator.model.utils.ElementCounter;
import org.apache.commons.collections15.Factory;

/**
 *
 * @author hvargas
 */
public class BusFactory implements Factory<Bus> {

    private static BusFactory instance = new BusFactory();

    private BusFactory() {
        System.out.println("Creando instancia del BusFactory...");
    }

    public static BusFactory getInstance() {
        return instance;
    }

    public Bus create() {
        int numeroNodo;

        numeroNodo = ElementCounter.getElementCounter().getBusNumberAvailable();

        String name = "B" + numeroNodo;
        Bus b = new Bus(name);
        b.setName(name);
        b.setNumberBus(numeroNodo);
        b.setAngleBus(0);
        b.setVoltageBus(1);
        b.setIsolatedStatus(false);
        b.setTFPresent(false);

        if (numeroNodo == 1) {
            //El nodo 1 será por defecto la barra slack (valor 1 de codeBus)            
            b.setCodeBus(1);
        } else {
            //defaultCodeBus = 0;
            b.setCodeBus(0);
        }

        return b;
    }

    public Bus create(String name) {
        int numeroNodo = Integer.parseInt(name.substring(1));
        ElementCounter.getElementCounter().setBusNumberAvailable(numeroNodo);
        //numeroNodo = ElementCounter.getElementCounter().getBusNumberAvailable();

        //String name = "B" + numeroNodo;
        Bus b = new Bus(name);
        b.setName(name);
        b.setNumberBus(numeroNodo);
        b.setAngleBus(0);
        b.setVoltageBus(1);
        b.setIsolatedStatus(false);
        b.setTFPresent(false);

        if (numeroNodo == 1) {
            //El nodo 1 será por defecto la barra slack (valor 1 de codeBus)            
            b.setCodeBus(1);
        } else {
            //defaultCodeBus = 0;
            b.setCodeBus(0);
        }

        return b;
    }
}
