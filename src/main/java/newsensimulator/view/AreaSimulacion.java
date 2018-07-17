/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.view;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import newsensimulator.control.Controller;
import newsensimulator.model.ElectricalNetwork;
import newsensimulator.model.networkelements.Edge;
import newsensimulator.model.networkelements.Vertex;
import newsensimulator.view.utils.EdgeDrawPaintTransformer;
import newsensimulator.view.utils.EdgeShapeTransformer;
import newsensimulator.view.utils.EdgeStringLabeller;
import newsensimulator.view.utils.EdgeStrokeTransformer;
import newsensimulator.view.utils.EdgeToolTipTransformer;
import newsensimulator.view.utils.VertexIconTransformer;
import newsensimulator.view.utils.VertexStringLabeller;
import newsensimulator.view.utils.VertexToolTipTransformer;

/**
 *
 * @author Jose Muñoz Parra
 */
public class AreaSimulacion extends VisualizationViewer<Vertex, Edge> {

    private static AreaSimulacion areaSimulacion;
    private CrossoverScalingControl crossoverScalingControl;

    public static AreaSimulacion getAreaSimulacion() {
        if (areaSimulacion == null) {
            areaSimulacion = new AreaSimulacion();
        }
        
        return areaSimulacion;
    }

    private AreaSimulacion() {
        super(new FRLayout<Vertex, Edge>(ElectricalNetwork.getElectricalNetwork()));
        crossoverScalingControl = new CrossoverScalingControl();
        //setBackground(new Color(200, 215, 255));
        setBackground(Color.WHITE);
        
        setDoubleBuffered(true);
        setGraphMouse(Controller.getController());
        Controller.getController().setMode(ModalGraphMouse.Mode.PICKING);
        //renderContext.setEdgeShapeTransformer(new EdgeShape.Line<Vertex,Line>());

        renderContext.setVertexLabelTransformer(new VertexStringLabeller());
        //Con esta linea cambio el icono del nodo
        renderContext.setVertexIconTransformer(new VertexIconTransformer());
        renderContext.setEdgeLabelTransformer(new EdgeStringLabeller());
        // Con esta linea coloco una linea segmentada, si no esat saldra continua
        renderContext.setEdgeStrokeTransformer(new EdgeStrokeTransformer());
        renderContext.setEdgeShapeTransformer(new EdgeShapeTransformer());//.setEdgeShapeTransformer(new EdgeShape.Line<Integer,Number>());
        renderContext.setEdgeDrawPaintTransformer(new EdgeDrawPaintTransformer());

        setVertexToolTipTransformer(new VertexToolTipTransformer());
        setEdgeToolTipTransformer(new EdgeToolTipTransformer());

    }

    public void paint(Graphics g) {
        super.paint(g);
        Controller.getController().pintarLinea((Graphics2D) g);
    }
    
    protected Object clone() throws CloneNotSupportedException {
        // Estamos usando un objeto que usa el patrón Singleton. Como no podemos
        // tener más instancias de la clase, sobrecargamos el método clone()
        // para evitar que se creen copias.
        throw new CloneNotSupportedException();
    }

    public Point2D getVertexPoint(Vertex v){
        final Layout<Vertex, Edge> layout = AreaSimulacion.getAreaSimulacion().getGraphLayout();
        Point2D vp = layout.transform(v);
//        System.out.println("P "+vp.getX()+" ; "+vp.getY());
        
        return vp;
    }
}
