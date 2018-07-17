package newsensimulator.model.networkelements.linetype;

import java.awt.BasicStroke;
import newsensimulator.model.networkelements.*;
import newsensimulator.model.utils.ElementCounter;
import org.apache.commons.collections15.Factory;

public class SimpleLineFactory implements Factory<SimpleLine> {

    private int lineCount = 0;
    private static SimpleLineFactory instance = new SimpleLineFactory();
    private int[] sw = {0, 0};

    private SimpleLineFactory() {
        System.out.println("Creando instancia del LineFactory...");
    }

    public static SimpleLineFactory getInstance() {
        return instance;
    }

    @Override
    public SimpleLine create() {
        //int numeroLinea = ElementCounter.getElementCounter().getLineNumberAvailable();
        int numeroLinea = 0;
        String name = "L" + numeroLinea;
        SimpleLine line = new SimpleLine(name);

        line.setName(name);
        line.setLineNumber(numeroLinea);
        //Codigo switch:
        //      1: Interruptor
        //      2: Interruptor Fusible
        //      3: Interruptor Fusible Controlado
        //
        // Presencia de elementos de conmutacion en la linea
        //        x: Presente       o: No presente
        // -x------o-   -x------x-   -o------o- 
        //      C           CCC          CC
        //
        // Estado de el o los elementos
        //  0: abierto   1: cerrado
        //   00   01   10   11

        //Por defecto seteo en ambos extremos un IntFus
        line.setSwitchCode(222);
        line.setSwDest(sw);
        line.setSwSource(sw);
        return line;
    }

    public SimpleLine create(Vertex origen, Vertex destino) {
        if (origen.getVertexType() == 0 && destino.getVertexType() == 0) {
            int numeroLinea = ElementCounter.getElementCounter().getLineNumberAvailable();

            String name = "L" + numeroLinea;
            SimpleLine line = new SimpleLine(name);
            line.setName(name);
            line.setLineNumber(numeroLinea);
            line.setSwitchCode(222);
            line.setSwDest(sw);
            line.setSwSource(sw);            
            line.setOrigen(origen);
            line.setDestino(destino);

            return line;
        } else {
            int numeroLinea = AuxLineNumber();

            String name = "L" + numeroLinea;
            SimpleLine line = new SimpleLine(name);
            line.setName(name);
            line.setLineNumber(numeroLinea);
            line.setSwitchCode(222);
            line.setSwDest(sw);
            line.setSwSource(sw);           
            line.setOrigen(origen);
            line.setDestino(destino);

            return line;
        }
    }

    public SimpleLine create(Vertex origen, Vertex destino, int numberLine) {
        if (origen.getVertexType() == 0 && destino.getVertexType() == 0) {
            int numeroLinea = numberLine;
            String name = "L" + numeroLinea;

            SimpleLine line = new SimpleLine(name);
            line.setName(name);
            line.setLineNumber(numeroLinea);
            line.setSwitchCode(222);
            line.setSwDest(sw);
            line.setSwSource(sw);            
            line.setOrigen(origen);
            line.setDestino(destino);

            return line;
        } else {
            int numeroLinea = AuxLineNumber();
            String name = "L" + numeroLinea;

            SimpleLine line = new SimpleLine(name);
            line.setName(name);
            line.setLineNumber(numeroLinea);
            line.setSwitchCode(222);
            line.setSwDest(sw);
            line.setSwSource(sw);            
            line.setOrigen(origen);
            line.setDestino(destino);

            return line;
        }
    }

    private int AuxLineNumber() {
        return lineCount--;
    }
}
