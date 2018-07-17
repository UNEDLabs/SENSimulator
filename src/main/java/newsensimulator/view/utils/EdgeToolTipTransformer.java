/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.view.utils;

import newsensimulator.model.networkelements.Edge;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Jose Muñoz Parra
 */
public class EdgeToolTipTransformer implements Transformer<Edge, String> {

    public String transform(Edge i) {
        switch (i.getEdgeTypeName()) {
            case "SimpleLine":
                if (!i.getEdgeAsSimpleLine().getName().equals("")) {
                    String info = i.getEdgeAsSimpleLine().getCurrent().toString();
                    return info;
                } else {
                    return null;
                }
            case "TFLocationLine":
                String info = "info...";//i.getEdgeAsSimpleLine().getCurrent().toString();
                    return info;
            default:
                break;
        }
        return null;
    }

}
