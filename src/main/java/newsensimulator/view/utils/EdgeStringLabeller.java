/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.view.utils;

import newsensimulator.model.networkelements.Edge;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Jose Muñoz Parra
 */
public class EdgeStringLabeller implements Transformer<Edge, String>{
    private String etiqueta;
    
    public String transform(Edge edge) {
        switch(edge.getEdgeTypeName()){
            case "SimpleLine":
                etiqueta = edge.getEdgeAsSimpleLine().getName();
                break;
            case "TFLocationLine":
                etiqueta = edge.getEdgeAsTFLocationLine().getName();
                break;
            default:
                etiqueta = "error etiqueta";
                break;
        }
        return etiqueta;
    }
    
}
