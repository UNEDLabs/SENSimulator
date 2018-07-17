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
public class LoadFactory implements Factory<Load> {

    private static LoadFactory instance = new LoadFactory();

    private LoadFactory() {
        System.out.println("Creando instancia del LoadFactory...");
    }

    public static LoadFactory getInstance() {
        return instance;
    }

    public Load create() {
        int numeroCarga;

        numeroCarga = ElementCounter.getElementCounter().getLoadNumberAvailable();

        String name = "Load" + numeroCarga;
        double[] percent = {0.30, 0.26, 0.24, 0.22, 0.20, 0.30, 0.38, 0.50, 0.55, 0.55, 0.58, 0.60, 0.55, 0.50, 0.48,  0.50, 0.70,  1.00, 0.95,  0.90, 0.82, 0.75, 0.60, 0.40};
        Load l = new Load(name);
        l.setLoadNumber(numeroCarga);
        l.setLoadMVar(2);
        l.setLoadMW(5);
        l.setLoadPriority(0);
        l.setLoadState(true);
        l.setLoadType(1);
        l.setPercentOfPeakLoad(percent);
        return l;
    }
    
    public Load create(String name) {
        int numeroCarga;
        double[] percent = {0.30, 0.26, 0.24, 0.22, 0.20, 0.30, 0.38, 0.50, 0.55, 0.55, 0.58, 0.60, 0.55, 0.50, 0.48,  0.50, 0.70,  1.00, 0.95,  0.90, 0.82, 0.75, 0.60, 0.40};
        
        numeroCarga = Integer.parseInt(name.substring(4));
        ElementCounter.getElementCounter().setLoadNumberAvailable(numeroCarga);
        //ElementCounter.getElementCounter().getLoadNumberAvailable();

       // String name = "Load" + numeroCarga;
        Load l = new Load(name);
        l.setLoadNumber(numeroCarga);
        l.setLoadMVar(2);
        l.setLoadMW(5);
        l.setLoadPriority(0);
        l.setLoadState(true);
        l.setLoadType(1);
        l.setPercentOfPeakLoad(percent);
        return l;
    }
}
