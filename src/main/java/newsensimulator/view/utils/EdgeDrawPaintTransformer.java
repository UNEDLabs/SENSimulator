/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.view.utils;

import java.awt.Color;
import java.awt.Paint;
import newsensimulator.control.Controller;
import newsensimulator.model.networkelements.Edge;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Jose Muñoz Parra
 */
public class EdgeDrawPaintTransformer implements Transformer<Edge, Paint> {

    public Paint transform(Edge line) {
        switch (line.getEdgeTypeName()) {
            case "SimpleLine":
                if (!line.getEdgeAsSimpleLine().getName().equals("")) {
                    return line.getEdgeAsSimpleLine().getColorLinea();
                } else {
                    return Color.ORANGE;
                }
            case "TFLocationLine":
                if(line.getEdgeAsTFLocationLine().isPresent()){
                    return Color.BLACK;
                }
                else{
                    return Color.GRAY;
                }
            default:
                Controller.getController().SENSimulatorConsolePrint("EdgeDraw revisar...");
                break;
        }
        return null;
    }

}
