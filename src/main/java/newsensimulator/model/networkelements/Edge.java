package newsensimulator.model.networkelements;

import java.io.Serializable;
import newsensimulator.model.networkelements.linetype.SimpleLine;
import newsensimulator.model.networkelements.linetype.SimpleLineFactory;
import newsensimulator.model.networkelements.linetype.TFLocationLine;
import newsensimulator.model.networkelements.linetype.TFLocationLineFactory;

/**
 *
 * @author Jose Muñoz Parra
 */
public class Edge implements Serializable{

    private SimpleLine simpleLine = null;
    private TFLocationLine tfLocationLine = null;

    private int simpleLineCode = 0;
    private int tfLocationLineCode = 1;

    private int edgeType;
    private String edgeTypeName;

    public Edge(String lineType, Vertex source, Vertex target) {
        switch (lineType) {
            case "SimpleLine":
                this.edgeTypeName = lineType;
                this.edgeType = simpleLineCode;
                simpleLine = SimpleLineFactory.getInstance().create(source, target);
                break;
            case "TFLocationLine":
                this.edgeTypeName = lineType;
                this.edgeType = tfLocationLineCode;
                tfLocationLine = TFLocationLineFactory.getInstance().create(source, target);
                break;
            default:
                System.out.println("Linea desconocida");
                break;
        }
    }

    public SimpleLine getEdgeAsSimpleLine() {
        return simpleLine;
    }

    public void setEdgeAsSimpleLine(SimpleLine simpleLine) {
        this.simpleLine = simpleLine;
        this.edgeTypeName = "SimpleLine";
        this.edgeType = simpleLineCode;
    }

    public TFLocationLine getEdgeAsTFLocationLine() {
        return tfLocationLine;
    }

    public void setEdgeAsTFLocationLine(TFLocationLine tfLocationLine) {
        this.tfLocationLine = tfLocationLine;
        this.edgeTypeName = "TFLocationLine";
        this.edgeType = tfLocationLineCode;

    }

    private void setEdgeType(int edgeCode) {
        this.edgeType = edgeCode;
    }

    public int getEdgeType() {
        return edgeType;
    }

    public String getEdgeTypeName() {
        return edgeTypeName;
    }

    public String getEdgeName() {
        switch (edgeTypeName) {
            case "SimpleLine":
                return getEdgeAsSimpleLine().getName();
            case "TFLocationLine":
                return getEdgeAsTFLocationLine().getName();
            default:
                return null;
        }
    }
}
