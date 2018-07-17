///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package newsensimulator.model.fileIO;
//
//import edu.uci.ics.jung.graph.Graph;
//import edu.uci.ics.jung.graph.SparseMultigraph;
//import edu.uci.ics.jung.io.GraphMLMetadata;
//import edu.uci.ics.jung.io.GraphMLReader;
//import edu.uci.ics.jung.io.GraphMLWriter;
//import java.awt.Color;
//import java.awt.geom.Point2D;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.PrintWriter;
//import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.swing.JOptionPane;
//import javax.xml.parsers.ParserConfigurationException;
//import newsensimulator.control.Controller;
//import newsensimulator.model.ElectricalNetwork;
//import newsensimulator.model.networkelements.Line;
//import newsensimulator.model.networkelements.LineFactory;
//import newsensimulator.model.networkelements.Vertex;
//import newsensimulator.model.networkelements.VertexFactory;
//import newsensimulator.view.AreaSimulacion;
//import newsensimulator.view.MainInterface;
//import org.apache.commons.collections15.Transformer;
//import org.xml.sax.SAXException;
//
///**
// *
// * @author hvargas
// */
//public class FileIO {
//
//    private static FileIO fileIO;
//    //private GraphMLReader<Graph<Vertex, Line>, Vertex, Line> gmlReader;
//    //private GraphMLWriter<Vertex, Line> gmlWriter;
//    private Point2D position;
//
//    private File file;
//    private FileOutputStream fos;
//    private FileInputStream fis;
//    private ObjectOutputStream oos;
//    private ObjectInputStream ois;
//
//    private ModelFile mf;
//
//    public static FileIO getFileIO() {
//        if (fileIO == null) {
//            fileIO = new FileIO();
//        }
//        return fileIO;
//    }
//
//    private FileIO() {
//    }
//
//    public boolean openFile(String filename) {
//
//        return false;
//    }
//
//    public boolean saveFile(String filename) {
//        try {
//            mf = new ModelFile();
//            System.out.println(filename);
//
//            for (Vertex v : ElectricalNetwork.getElectricalNetwork().getVertices()) {
//                position = new Point2D.Double();
//                position.setLocation(
//                        AreaSimulacion.getAreaSimulacion().getGraphLayout().transform(v).getX(), 
//                        AreaSimulacion.getAreaSimulacion().getGraphLayout().transform(v).getY());
//                mf.addVertex(position, v);
//            }
//
//            for (Line l : ElectricalNetwork.getElectricalNetwork().getEdges()) {
//                mf.addLine(l);
//            }
//
//            file = new File(filename);
//            if (!file.exists()) {
//                fos = new FileOutputStream(file);
//                oos = new ObjectOutputStream(fos);
//                oos.writeObject(mf);
//                oos.close();
//
//                return true;
//            } else {
//                int seleccion = JOptionPane.showOptionDialog(
//                        null,
//                        "El archivo existe!\n¿Desea sobreescribir?",
//                        "Guardar...",
//                        JOptionPane.YES_NO_OPTION,
//                        JOptionPane.QUESTION_MESSAGE,
//                        null, // null para icono por defecto.
//                        null,
//                        null);
//
//                if (seleccion != -1) {
//                    System.out.println("seleccionada opcion " + seleccion);
//                    if (seleccion == 0) {
//                        fos = new FileOutputStream(file);
//                        oos = new ObjectOutputStream(fos);
//                        oos.writeObject(mf);
//                        oos.close();
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return false;
//    }
//    
//    
//    /*
//     public boolean openFile(String filename) throws ParserConfigurationException, SAXException, IOException {
//
//     gmlReader = new GraphMLReader<Graph<Vertex, Line>, Vertex, Line>(VertexFactory.getInstance(), LineFactory.getInstance());
//     Graph<Vertex, Line> graph = new SparseMultigraph<Vertex, Line>();
//     gmlReader.load(filename, graph);
//
//     Map<String, GraphMLMetadata<Vertex>> vertex_meta = gmlReader.getVertexMetadata(); //Our vertex Metadata is stored in a map.
//     Map<String, GraphMLMetadata<Line>> edge_meta = gmlReader.getEdgeMetadata(); // Our edge Metadata is stored in a map.
//
//     for (Vertex n : graph.getVertices()) {
//
//     n.setName(vertex_meta.get("name").transformer.transform(n)); //Set the value of the node to the vertex_id which was read in from the GraphML Reader.
//     n.setNumberBus(Integer.valueOf(vertex_meta.get("numberBus").transformer.transform(n)));
//     n.setCodeBus(Integer.valueOf(vertex_meta.get("codeBus").transformer.transform(n)));
//     n.setVoltageBus(Double.valueOf(vertex_meta.get("voltageBus").transformer.transform(n)));
//     n.setAngleBus(Double.valueOf(vertex_meta.get("angleBus").transformer.transform(n)));
//     n.setLoadMW(Double.valueOf(vertex_meta.get("loadMW").transformer.transform(n)));
//     n.setLoadMVar(Double.valueOf(vertex_meta.get("loadMVar").transformer.transform(n))); // Set the color, which we get from the Map, vertex_color.
//
//            
//
//     position = new Point2D.Double();
//     position.setLocation(Double.valueOf(vertex_meta.get("posX").transformer.transform(n)), Double.valueOf(vertex_meta.get("posY").transformer.transform(n)));
//            
//     //ElectricalNetwork.getElectricalNetwork().crearNodo(position, n);
//     Controller.getController().crearNodo(position, n);
//            
//     AreaSimulacion.getAreaSimulacion().getGraphLayout().setLocation(n, position);
//     }
//
//     // Just as we added the vertices to the graph, we add the edges as well.
//     for (Line e : graph.getEdges()) {
//     System.out.println(edge_meta.get("lineName").transformer.transform(e));
//     e.setName(edge_meta.get("lineName").transformer.transform(e)); //Set the edge's value.
//     e.setLineNumber(Integer.valueOf(edge_meta.get("lineNumber").transformer.transform(e)));
//     e.setResistance(Double.valueOf(edge_meta.get("lineResistance").transformer.transform(e)));
//     e.setReactance(Double.valueOf(edge_meta.get("lineReactance").transformer.transform(e)));
//     e.setMaxCurrent(Double.valueOf(edge_meta.get("lineMaxCurrent").transformer.transform(e)));
//     e.setColorLinea(Color.BLACK);
//     e.setEstiloLinea(Boolean.valueOf(edge_meta.get("lineStyle").transformer.transform(e)));
//     //e.setStroke(null);
//     e.setOrigen(ElectricalNetwork.getElectricalNetwork().obtenerBus(edge_meta.get("lineSource").transformer.transform(e)));
//     e.setDestino(ElectricalNetwork.getElectricalNetwork().obtenerBus(edge_meta.get("lineTarget").transformer.transform(e)));
//
//     ElectricalNetwork.getElectricalNetwork().crearLinea(e);
//
//     }
//
//     MainInterface.getMainInterface().actualizar();
//
//     //AreaSimulacion.getAreaSimulacion().setGraphLayout(new FRLayout<Node, Line>(graph));
//     return true;
//     }
//
//     public boolean saveFile(String filename) {
//     try {
//     gmlWriter = new GraphMLWriter<Vertex, Line>();
//     PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
//
//     for (final Vertex n : ElectricalNetwork.getElectricalNetwork().getVertices()) {
//     gmlWriter.addVertexData("posX", null, null,
//     new Transformer<Vertex, String>() {
//     @Override
//     public String transform(Vertex v) {
//     return Double.toString(AreaSimulacion.getAreaSimulacion().getGraphLayout().transform(v).getX());
//     }
//     });
//     gmlWriter.addVertexData("posY", null, null,
//     new Transformer<Vertex, String>() {
//     @Override
//     public String transform(Vertex v) {
//     return Double.toString(AreaSimulacion.getAreaSimulacion().getGraphLayout().transform(v).getY());
//     }
//     });
//     gmlWriter.addVertexData("vertex", null, null,
//     new Transformer<Vertex, String>() {
//     @Override
//     public String transform(Vertex v) {
//     return v.toString();
//     }
//     });
//     }
//     for (Line l : ElectricalNetwork.getElectricalNetwork().getEdges()) {
//
//     gmlWriter.addEdgeData("lineName", null, null,
//     new Transformer<Line, String>() {
//     @Override
//     public String transform(Line l) {
//     return l.getName();
//     }
//     });
//
//     gmlWriter.addEdgeData("lineNumber", null, null,
//     new Transformer<Line, String>() {
//     @Override
//     public String transform(Line l) {
//     return Integer.toString(l.getLineNumber());
//     }
//     });
//
//     gmlWriter.addEdgeData("lineResistance", null, null,
//     new Transformer<Line, String>() {
//     @Override
//     public String transform(Line l) {
//     return Double.toString(l.getResistance());
//     }
//     });
//
//     gmlWriter.addEdgeData("lineReactance", null, null,
//     new Transformer<Line, String>() {
//     @Override
//     public String transform(Line l) {
//     return Double.toString(l.getReactance());
//     }
//     });
//
//     gmlWriter.addEdgeData("lineMaxCurrent", null, null,
//     new Transformer<Line, String>() {
//     @Override
//     public String transform(Line l) {
//     return Double.toString(l.getMaxCurrent());
//     }
//     });
//
//     gmlWriter.addEdgeData("lineColor", null, null,
//     new Transformer<Line, String>() {
//     @Override
//     public String transform(Line l) {
//     return l.getColorLinea().toString();
//     }
//     });
//
//     gmlWriter.addEdgeData("lineStyle", null, null,
//     new Transformer<Line, String>() {
//     @Override
//     public String transform(Line l) {
//     return Boolean.toString(l.getEstiloLinea());
//     }
//     });
//
//     gmlWriter.addEdgeData("lineStroke", null, null,
//     new Transformer<Line, String>() {
//     @Override
//     public String transform(Line l) {
//     return l.getEstiloStroke().toString();
//     }
//     });
//
//     gmlWriter.addEdgeData("lineSource", null, null,
//     new Transformer<Line, String>() {
//     @Override
//     public String transform(Line l) {
//     switch (l.getOrigen().getVertexTypeName()) {
//     case "Bus":
//     return l.getOrigen().getVertexAsBus().getName();
//     case "Fault":
//     return l.getOrigen().getVertexAsFault().getName();
//     case "Load":
//     return l.getOrigen().getVertexAsLoad().getName();
//     case "Generator":
//     return l.getOrigen().getVertexAsGenerator().getName();
//     case "ElectricVehicle":
//     return l.getOrigen().getVertexAsElectricVehicle().getName();
//     }
//     return null;
//     }
//     });
//
//     gmlWriter.addEdgeData("lineTarget", null, null,
//     new Transformer<Line, String>() {
//     @Override
//     public String transform(Line l) {
//     switch (l.getDestino().getVertexTypeName()) {
//     case "Bus":
//     return l.getDestino().getVertexAsBus().getName();
//     case "Fault":
//     return l.getDestino().getVertexAsFault().getName();
//     case "Load":
//     return l.getDestino().getVertexAsLoad().getName();
//     case "Generator":
//     return l.getDestino().getVertexAsGenerator().getName();
//     case "ElectricVehicle":
//     return l.getDestino().getVertexAsElectricVehicle().getName();
//     }
//     return null;
//     }
//     });
//
//     }
//
//     gmlWriter.save(ElectricalNetwork.getElectricalNetwork(), out);
//
//     } catch (Exception ex) {
//     ex.printStackTrace();
//     return false;
//     }
//
//     return true;
//     }
//     */
//}
