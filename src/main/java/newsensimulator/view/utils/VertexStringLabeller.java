/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.view.utils;

import newsensimulator.model.networkelements.Vertex;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Jose Muñoz Parra
 */
public class VertexStringLabeller implements Transformer<Vertex, String> {

    private String etiqueta;

    public String transform(Vertex vertex) {
        switch (vertex.getVertexTypeName()) {
            case "Bus":
                etiqueta = vertex.getVertexAsBus().getName();
                break;
            case "Fault":
                etiqueta = vertex.getVertexAsFault().getName();
                break;
            case "Load":
                etiqueta = vertex.getVertexAsLoad().getName();
                break;
            case "Generator":
                etiqueta = vertex.getVertexAsGenerator().getName();
                break;
            case "ElectricVehicle":
                etiqueta = vertex.getVertexAsElectricVehicle().getName();
                break;
            case "TFLocationNode":
                etiqueta = vertex.getVertexAsTFLocationNode().getName();
                break;

            case "Battery":
                etiqueta = vertex.getVertexAsBattery().getName();
                break;
            default:
                etiqueta = "error etiqueta";
                break;
        }
        return etiqueta;
    }

}
