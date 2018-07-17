package newsensimulator.view.utils;

import java.awt.BasicStroke;
import java.awt.Stroke;
import newsensimulator.model.networkelements.Edge;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Jose Muñoz Parra
 */
public class EdgeStrokeTransformer implements Transformer<Edge, Stroke> {

    public Stroke transform(Edge line) {
        switch (line.getEdgeTypeName()) {
            case "SimpleLine":
                if (!line.getEdgeAsSimpleLine().getEstiloLinea()) {
                    return line.getEdgeAsSimpleLine().getEstiloStroke();
                } else {
                    return null;
                }
            case "TFLocationLine":
                if(line.getEdgeAsTFLocationLine().isPresent()){
                    return null;
                }
                else{
                    float dash[] = {5.0f};
                    return new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 1.0f);
                }
            default:
                break;
        }
        return null;
    }
}
