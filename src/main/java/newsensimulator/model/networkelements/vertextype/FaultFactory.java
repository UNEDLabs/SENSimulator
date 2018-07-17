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
public class FaultFactory implements Factory<Fault>{

    private int nodeCount = 0;
    private static FaultFactory instance = new FaultFactory();

    private FaultFactory() {
        System.out.println("Creando instancia del NodeFactory...");
    }

    public static FaultFactory getInstance() {
        return instance;
    }
    
    public Fault create() {
        int numeroFalla = ElementCounter.getElementCounter().getFautNumberAvailable();

        String name = "F" + numeroFalla;
        Fault f = new Fault(name);
        f.setFaultNumber(numeroFalla);

        return f;
    }
    
    public Fault create(String name) {
        int numeroFalla = Integer.parseInt(name.substring(1));
        ElementCounter.getElementCounter().setFautNumberAvailable(numeroFalla);
      //  ElementCounter.getElementCounter().getFautNumberAvailable();

        //String name = "F" + numeroFalla;
        Fault f = new Fault(name);
        f.setFaultNumber(numeroFalla);

        return f;
    }
}
