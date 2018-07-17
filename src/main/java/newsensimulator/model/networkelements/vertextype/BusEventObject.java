/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.networkelements.vertextype;

import java.util.EventObject;

/**
 *
 * @author Jose Muñoz Parra
 */
public class BusEventObject extends EventObject {

    public Bus bus;

    public BusEventObject(Object source, Bus bus) {
        super(source);
        this.bus = bus;
    }

    public Bus getBus() {
        return bus;
    }

}
