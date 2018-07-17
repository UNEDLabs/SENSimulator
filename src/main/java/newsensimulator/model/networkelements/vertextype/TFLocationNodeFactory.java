/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.networkelements.vertextype;

import newsensimulator.model.utils.ElementCounter;

/**
 *
 * @author Jose Muñoz Parra
 */
public class TFLocationNodeFactory {

    private static TFLocationNodeFactory instance = new TFLocationNodeFactory();
    
    private final double angleDefault = 0;
    private final double voltageDefault = 1; // [p.u.]
    private final double activePowerDefault = 0.001;
    private final double reactivePowerDefault = 0.0005;
    
    private final boolean transformerDefault = false;
    
    private final int NCliDefault = 1;

    private TFLocationNodeFactory() {
        System.out.println("Creando instancia del TFLocationNodeFactory...");
    }

    public static TFLocationNodeFactory getInstance() {
        return instance;
    }

    public TFLocationNode create() {
        int number;

        number = ElementCounter.getElementCounter().getBusNumberAvailable();

        String name = "B" + number;
        TFLocationNode tfln = new TFLocationNode(name);
        
        tfln.setName(name);
        tfln.setNumberNode(number);
        
        tfln.setAnglePhase_A(angleDefault);
        tfln.setAnglePhase_B(angleDefault);
        tfln.setAnglePhase_C(angleDefault);
        
        tfln.setVoltagePhase_A(voltageDefault);
        tfln.setVoltagePhase_B(voltageDefault);
        tfln.setVoltagePhase_C(voltageDefault);
        
        tfln.setActivePowerPhase_A(activePowerDefault);
        tfln.setActivePowerPhase_B(activePowerDefault);
        tfln.setActivePowerPhase_B(activePowerDefault);
        
        tfln.setReactivePowerPhase_A(reactivePowerDefault);
        tfln.setReactivePowerPhase_B(reactivePowerDefault);
        tfln.setReactivePowerPhase_C(reactivePowerDefault);
        
        tfln.setTransformerNode(transformerDefault);
        
        tfln.setNCli(NCliDefault);
        
        return tfln;
       
    }

    public TFLocationNode create(String name) {
        
        int number=Integer.parseInt(name.substring(1));
        ElementCounter.getElementCounter().setBusNumberAvailable(number);
        
        TFLocationNode tfln = new TFLocationNode(name);
        
        tfln.setName(name);
        tfln.setNumberNode(number);
        
        tfln.setAnglePhase_A(angleDefault);
        tfln.setAnglePhase_B(angleDefault);
        tfln.setAnglePhase_C(angleDefault);
        
        tfln.setVoltagePhase_A(voltageDefault);
        tfln.setVoltagePhase_B(voltageDefault);
        tfln.setVoltagePhase_C(voltageDefault);
        
        tfln.setActivePowerPhase_A(activePowerDefault);
        tfln.setActivePowerPhase_B(activePowerDefault);
        tfln.setActivePowerPhase_B(activePowerDefault);
        
        tfln.setReactivePowerPhase_A(reactivePowerDefault);
        tfln.setReactivePowerPhase_B(reactivePowerDefault);
        tfln.setReactivePowerPhase_C(reactivePowerDefault);
        
        tfln.setTransformerNode(transformerDefault);
        
        tfln.setNCli(NCliDefault);

        return tfln;
    }
}
