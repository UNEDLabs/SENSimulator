/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.view.utils;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Context;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import java.awt.Shape;
import newsensimulator.control.Controller;
import newsensimulator.model.networkelements.Edge;
import newsensimulator.model.networkelements.Vertex;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Jose Muñoz Parra
 */
public class EdgeShapeTransformer implements Transformer<Context<Graph<Vertex, Edge>, Edge>, Shape> {

    public Shape transform(Context<Graph<Vertex, Edge>, Edge> i) {
        switch (i.element.getEdgeTypeName()) {
            case "SimpleLine":
                if (!i.element.getEdgeAsSimpleLine().getName().equals("")) {
                    EdgeShape.Line<Vertex, Edge> es = new EdgeShape.Line<Vertex, Edge>();
                    return es.transform(i);
                } else {
                    EdgeShape.QuadCurve<Vertex, Edge> es = new EdgeShape.QuadCurve<Vertex, Edge>();
                    return es.transform(i);
                }
            case "TFLocationLine":
                EdgeShape.Line<Vertex, Edge> es = new EdgeShape.Line<Vertex, Edge>();
                return es.transform(i);
            default:
                Controller.getController().SENSimulatorConsolePrint("EdgeShapeTransformer revisar...");
                break;
        }
        
        return null;
    }

}
