/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package newsensimulator.model.networkelements.vertextype;

import java.util.EventListener;

/**
 *
 * @author Jose Muñoz Parra
 */
public interface BusEventListener extends EventListener{
    
    public void changeIncidentLines(BusEventObject args);
    
    public void withoutEnergy(BusEventObject args);
    
    public void changeBusType(BusEventObject args);
    
    public void startSimulation(BusEventObject args);
    
}
