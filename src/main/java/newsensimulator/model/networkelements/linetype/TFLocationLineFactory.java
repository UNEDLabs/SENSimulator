/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.networkelements.linetype;

import newsensimulator.model.networkelements.Vertex;
import newsensimulator.model.networkelements.vertextype.TFLocationNode;
import newsensimulator.model.utils.ElementCounter;
import org.apache.commons.collections15.Factory;

/**
 *
 * @author Jose Muñoz Parra
 */
public class TFLocationLineFactory implements Factory<TFLocationLine> {

    private static TFLocationLineFactory instance = new TFLocationLineFactory();

    private boolean lineBuiltDefault = true;
    private Conductor defaultConductor;

    public static TFLocationLineFactory getInstance() {
        return instance;
    }

    public TFLocationLine create() {
        //int numeroLinea = ElementCounter.getElementCounter().getLineNumberAvailable();
        int number = 0;
        String name = "L" + number;
        TFLocationLine tfll = new TFLocationLine(name);

        tfll.setNumberLine(number);
        tfll.setLineBuilt(lineBuiltDefault);

        defaultConductor = new Conductor(1);
        tfll.setConductor(defaultConductor);
        
        return tfll;
    }

    public TFLocationLine create(Vertex origen, Vertex destino) {
        int number = ElementCounter.getElementCounter().getLineNumberAvailable();

        String name = "L" + number;
        TFLocationLine tfll = new TFLocationLine(name);

        tfll.setNumberLine(number);
        tfll.setLineBuilt(lineBuiltDefault);

        tfll.setSourceVertex(origen);
        tfll.setTargetVertex(destino);
        
        defaultConductor = new Conductor(1);
        tfll.setConductor(defaultConductor);

        return tfll;
    }

    public TFLocationLine create(Vertex origen, Vertex destino, int numberLine) {
        String name = "L" + numberLine;

        TFLocationLine tfll = new TFLocationLine(name);
        
        tfll.setNumberLine(numberLine);
        tfll.setLineBuilt(lineBuiltDefault);

        tfll.setSourceVertex(origen);
        tfll.setTargetVertex(destino);
        
        defaultConductor = new Conductor(1);
        tfll.setConductor(defaultConductor);

        return tfll;

    }

}
