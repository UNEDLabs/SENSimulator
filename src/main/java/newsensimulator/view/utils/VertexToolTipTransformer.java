/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.view.utils;

import newsensimulator.model.networkelements.Vertex;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Jose Muñoz Parra
 */
public class VertexToolTipTransformer implements Transformer<Vertex, String> {

    public String transform(Vertex i) {
        if (i.getVertexType() == 0) {
            String info = "Voltaje: " + String.valueOf(i.getVertexAsBus().getVoltageBus()) + " < " + i.getVertexAsBus().getAngleBus() + "°";
            return info;
        } else {
            return null;
        }
    }
}
