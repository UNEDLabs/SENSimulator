/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.networkelements;

import java.awt.BasicStroke;
import newsensimulator.control.Controller;
import newsensimulator.model.ElectricalNetwork;
import newsensimulator.view.MainInterface;
import org.apache.commons.collections15.Factory;

/**
 *
 * @author Jose Muñoz Parra
 */
public class EdgeFactory implements Factory<Edge> {

    private static final EdgeFactory instance = new EdgeFactory();

    private EdgeFactory() {
        System.out.println("Creando instancia del EdgeFactory...");
        MainInterface.getMainInterface().SENSimulatorConsolePrint("Instancia EdgeFactory");
    }

    public static EdgeFactory getInstance() {
        return instance;
    }

    public Edge create(Vertex origen, Vertex destino, int numberLine) {
        switch (Controller.getController().getProblemName()) {
            // Operacion con SimpleLine
            case "Planning":
                Edge edge1 = new Edge("SimpleLine", origen, destino);
                edge1.getEdgeAsSimpleLine().setLineNumber(numberLine);                
                return edge1;
            // Operacion con TFLocationLine
            case "Operation":
                Edge edge2 = new Edge("TFLocationLine", origen, destino);
                edge2.getEdgeAsTFLocationLine().setNumberLine(numberLine);
                return edge2;
            case "Other":
                System.out.println("EdgeFactory other");
                return null;
            default:
                System.out.println("EdgeFactory desconocido");
                return null;
        }
    }

    
    //Debería revisarse esto, por el case de Planning y Operation ya que uno crea una línea de tipo TFLocationLine
    // dependiendo del problema y esto quizás no debería ser así. Además, está al revés respecto al método create anterior.
      
    public Edge create(Vertex origen, Vertex destino) {
        if (ElectricalNetwork.getElectricalNetwork().findEdge(origen, destino) == null) {
            
            switch (Controller.getController().getProblemName()) {
                // Operacion con SimpleLine
                case "Operation":
                    Edge edge1 = new Edge("SimpleLine", origen, destino);
                    return edge1;
                // Operacion con TFLocationLine
                case "Planning":
                    Edge edge2 = new Edge("TFLocationLine", origen, destino);
                    return edge2;
                default:
                    System.out.println("EdgeFactory desconocido");
                    return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Edge create() {
        System.out.println("Creando edge con metodo sin argumentos");
        Edge e = new Edge(null, null, null);
        return e;
    }

}
