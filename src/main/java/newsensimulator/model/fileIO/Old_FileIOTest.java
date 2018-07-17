/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.fileIO;

//import edu.uci.ics.jung.graph.Graph;
//import edu.uci.ics.jung.graph.SparseMultigraph;
//import edu.uci.ics.jung.io.GraphMLMetadata;
//import edu.uci.ics.jung.io.GraphMLReader;
//import edu.uci.ics.jung.io.GraphMLWriter;
//import java.awt.geom.Point2D;
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.xml.parsers.ParserConfigurationException;
//import newsensimulator.control.Controller;
//import newsensimulator.model.ElectricalNetwork;
//import newsensimulator.model.utils.ElementCounter;
//import newsensimulator.view.AreaSimulacion;
//import newsensimulator.view.MainInterface;
//import org.apache.commons.collections15.Transformer;
//import org.xml.sax.SAXException;
//
///**
// *
// * @author hvargas
// */
//public class FileIOTest {
//
//    private static FileIOTest fileIO;
//    private GraphMLReader<Graph<Vertex, Line>, Vertex, Line> gmlReader;
//
//    private GraphMLWriter<Vertex, Line> gmlWriter;
////    private Point2D position;
//
//    public static FileIOTest getFileIO() {
//        if (fileIO == null) {
//            fileIO = new FileIOTest();
//        }
//        return fileIO;
//    }
//
//    private FileIOTest() {
//
//    }
//
//    public boolean openFile(String filename) throws ParserConfigurationException, SAXException, IOException {
//
//        gmlReader = new GraphMLReader<>(VertexFactory.getInstance(), LineFactory.getInstance());
//
//        Graph<Vertex, Line> graph = new SparseMultigraph<>();
//
//        gmlReader.load(filename, graph);/*<- aqui ver q onda por q tira "   Creando vertice con metodo sin argumentos
//         *                                   Vertice sin tipo!!!!...
//         */
//
//
//        Map<String, GraphMLMetadata<Vertex>> vertex_meta = gmlReader.getVertexMetadata(); //Our vertex Metadata is stored in a map.
//        Map<String, GraphMLMetadata<Line>> edge_meta = gmlReader.getEdgeMetadata(); // Our edge Metadata is stored in a map.
//
//        for (Vertex n : graph.getVertices()) {
//
//            String sName = vertex_meta.get("Name").transformer.transform(n);
//            String sType = vertex_meta.get("Type").transformer.transform(n);
//            if (!sType.equals("Fault")) {
//                Point2D.Double position = new Point2D.Double();
//                position.setLocation(Double.valueOf(vertex_meta.get("posX").transformer.transform(n)), Double.valueOf(vertex_meta.get("posY").transformer.transform(n)));
//
//                Vertex v = Controller.getController().crearNodo(position, sType,sName);
//                //vemos para cada caso las respectivas propiedades
//                switch (sType) {
//                    case "Bus":
//
//                        //vertex_meta.get("AngleBeforBlackout").transformer.transform(n);
//                        v.getVertexAsBus().setAngleBus(Double.parseDouble(vertex_meta.get("Angle").transformer.transform(n)));
//                        v.getVertexAsBus().setCodeBus(Integer.parseInt(vertex_meta.get("BusCode").transformer.transform(n)));
//                        v.getVertexAsBus().setNumberBus(Integer.parseInt(vertex_meta.get("NumberBus").transformer.transform(n)));
//                        //vertex_meta.get("VoltageBeforBlackout").transformer.transform(n);
//                        v.getVertexAsBus().setVoltageBus(Double.parseDouble(vertex_meta.get("Voltage").transformer.transform(n)));
//
//                        break;
//
//                    case "Load":
//
//                        v.getVertexAsLoad().setLoadMVar(Double.parseDouble(vertex_meta.get("LoadMVar").transformer.transform(n)));
//                        v.getVertexAsLoad().setLoadMW(Double.parseDouble(vertex_meta.get("LoadMW").transformer.transform(n)));
//
//                        break;
//                    case "Generator":
//
//                        v.getVertexAsGenerator().setGeneratorNumber(Integer.parseInt(vertex_meta.get("GeneratorNumber").transformer.transform(n)));
//                        v.getVertexAsGenerator().setGeneratorState(Boolean.parseBoolean(vertex_meta.get("GeneratorState").transformer.transform(n)));
//                        v.getVertexAsGenerator().setMVarGenerator(Double.parseDouble(vertex_meta.get("MWGenerator").transformer.transform(n)));
//
//                        break;
//                    case "ElectricVehicle":
//
//                        v.getVertexAsElectricVehicle().setElectricVehicleNumber(Integer.parseInt(vertex_meta.get("VehicleNumber").transformer.transform(n)));
//                        v.getVertexAsElectricVehicle().setElectricVehicleState(Boolean.parseBoolean(vertex_meta.get("VehicleState").transformer.transform(n)));
//
//                        break;
//                    default:
//
//                        break;
//                }
//            }
//
//        }
//
//        // Just as we added the vertices to the graph, we add the edges as well.
//        for (Line e : graph.getEdges()) {
//            System.out.println(edge_meta.get("name").transformer.transform(e));
//            Vertex v1 = ElectricalNetwork.getElectricalNetwork().getBusByName(edge_meta.get("source").transformer.transform(e));
//            Vertex v2 = ElectricalNetwork.getElectricalNetwork().getBusByName(edge_meta.get("target").transformer.transform(e));
//
//            Line l = new Line(edge_meta.get("name").transformer.transform(e));
//                        
//            l.setLineNumber(Integer.valueOf(edge_meta.get("lineNo").transformer.transform(e)));
//            l.setResistance(Double.valueOf(edge_meta.get("resistance").transformer.transform(e)));
//            l.setReactance(Double.valueOf(edge_meta.get("reactance").transformer.transform(e)));
//            l.setMaxCurrent(Double.valueOf(edge_meta.get("maxCurrent").transformer.transform(e)));
//            //l.setColorLinea(Color.BLACK);
//            l.setEstiloLinea(Boolean.valueOf(edge_meta.get("estiloLinea").transformer.transform(e)));
//
//            l.setOrigen(v1);
//            l.setDestino(v2);
//
//            ElementCounter.getElementCounter().setLineNumberAvailable(l.getLineNumber());
//            ElectricalNetwork.getElectricalNetwork().createLine(l);
//
//        }
//
//        //ahora se cargan las fallas
//       /* for (Vertex n : graph.getVertices()) {
//
//         String sName = vertex_meta.get("Name").transformer.transform(n);
//         String sType = vertex_meta.get("Type").transformer.transform(n);
//         if (sType.equals("Fault")) {
//         Point2D.Double position = new Point2D.Double();
//         position.setLocation(Double.valueOf(vertex_meta.get("posX").transformer.transform(n)), Double.valueOf(vertex_meta.get("posY").transformer.transform(n)));
//
//         Vertex v = Controller.getController().crearNodo(position, sType);
//
//         v.getVertexAsFault().setFaultNumber(Integer.parseInt(vertex_meta.get("FaultNumber").transformer.transform(n)));
//         v.getVertexAsFault().setFaultType(Integer.parseInt(vertex_meta.get("FaultType").transformer.transform(n)));
//
//         vertex_meta.get("LineFault").transformer.transform(n);
//
//               
//
//         }
//
//         }*/
//        AreaSimulacion.getAreaSimulacion().repaint();
//
//        return true;
//    }
//
//    public boolean saveFile2(String filename) {
//
//        FileWriter fichero = null;
//        PrintWriter pw = null;
//        try {
//            fichero = new FileWriter(filename);
//            pw = new PrintWriter(fichero);
//
//            //ecritura del fichero
//            //encabezado
//            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//            pw.println("    <graphml xmlns=\"http://graphml.graphdrawing.org/xmlns/graphml\"");
//            pw.println("     xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"  ");
//            pw.println("     xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns/graphml\">");
//
//            pw.println("         <key id=\"MWGenerator\" for=\"node\"/>");
//            pw.println("         <key id=\"VehicleState\" for=\"node\"/>");
//            pw.println("         <key id=\"LoadMW\" for=\"node\"/>");
//            pw.println("         <key id=\"FaultType\" for=\"node\"/>");
//            pw.println("         <key id=\"GeneratorState\" for=\"node\"/>");
//            pw.println("         <key id=\"GeneratorNumber\" for=\"node\"/>");
//            pw.println("         <key id=\"Name\" for=\"node\"/>");
//            pw.println("         <key id=\"posX\" for=\"node\"/>");
//            pw.println("         <key id=\"posY\" for=\"node\"/>");
//            pw.println("         <key id=\"Type\" for=\"node\"/>");
//            pw.println("         <key id=\"Angle\" for=\"node\"/>");
//            pw.println("         <key id=\"LineFault\" for=\"node\"/>");
//            pw.println("         <key id=\"Voltage\" for=\"node\"/>");
//            pw.println("         <key id=\"FaultNumber\" for=\"node\"/>");
//            pw.println("         <key id=\"LoadMVar\" for=\"node\"/>");
//            pw.println("         <key id=\"BusCode\" for=\"node\"/>");
//            pw.println("         <key id=\"NumberBus\" for=\"node\"/>");
//            pw.println("         <key id=\"VehicleNumber\" for=\"node\"/>");
//
//            pw.println("         <key id=\"maxCurrent\" for=\"edge\"/>");
//            pw.println("         <key id=\"reactance\" for=\"edge\"/>");
//            pw.println("         <key id=\"lineNo\" for=\"edge\"/>");
//            pw.println("         <key id=\"colorLinea\" for=\"edge\"/>");
//            pw.println("         <key id=\"estiloLinea\" for=\"edge\"/>");
//            pw.println("         <key id=\"estiloStroke\" for=\"edge\"/>");
//            pw.println("         <key id=\"name\" for=\"edge\"/>");
//            pw.println("         <key id=\"source\" for=\"edge\"/>");
//            pw.println("         <key id=\"resistance\" for=\"edge\"/>");
//            pw.println("         <key id=\"target\" for=\"edge\"/>");
//            pw.println("");
//            pw.println("         <graph edgedefault=\"directed\">");
//
//            // insertar los nodos
//            for (Vertex n : ElectricalNetwork.getElectricalNetwork().getVertices()) {
//                pw.println("            <node id=\"" + n.getNameVertex() + "\">");
//
//                    //almacenamos la posicion de vertex
//                    pw.println("                <data key=\"posX\">" + String.valueOf(AreaSimulacion.getAreaSimulacion().getGraphLayout().transform(n).getX()) + "</data>");
//                    pw.println("                <data key=\"posY\">" + String.valueOf(AreaSimulacion.getAreaSimulacion().getGraphLayout().transform(n).getY()) + "</data>");
//                    pw.println("                <data key=\"Type\">" + n.getVertexTypeName() + "</data>");
//                    pw.println("                <data key=\"Name\">" +  n.getNameVertex() + "</data>");
//                    
//                   
//                    switch (n.getVertexTypeName()) {
//
//                        case "Bus":
//                            
//                            pw.println("                <data key=\"Angle\">" + String.valueOf(n.getVertexAsBus().getAngleBus()) + "</data>");
//                            pw.println("                <data key=\"BusCode\">" + Integer.toString(n.getVertexAsBus().getBusCode()) + "</data>");
//                            pw.println("                <data key=\"NumberBus\">" + Integer.toString(n.getVertexAsBus().getNumberBus()) + "</data>");
//                            pw.println("                <data key=\"Voltage\">" + String.valueOf(n.getVertexAsBus().getVoltageBus()) + "</data>");
//
//                            break;
//                        case "Fault":
//                            
//                            pw.println("                <data key=\"FaultNumber\">" + String.valueOf(n.getVertexAsFault().getFaultNumber()) + "</data>");
//                            pw.println("                <data key=\"FaultType\">" + String.valueOf(n.getVertexAsFault().getFaultType()) + "</data>");
//
//                            /**
//                             * *
//                             *
//                             * aca guardar la linea, pero no se cuales son
//                             * necesarions ahora guarda el nimero de linea
//                             *
//                             *
//                             * });
//                             */
//                            pw.println("                <data key=\"LineFault\">" + String.valueOf(n.getVertexAsFault().getLocation().getLineNumber()) + "</data>");
//
//                            break;
//                        case "Load":
//                           
//                            pw.println("                <data key=\"LoadMVar\">" + String.valueOf(n.getVertexAsLoad().getLoadMVar()) + "</data>");
//                            pw.println("                <data key=\"LoadMW\">" + String.valueOf(n.getVertexAsLoad().getLoadMW()) + "</data>");
//
//                            break;
//                        case "Generator":
//                            
//                            pw.println("                <data key=\"GeneratorNumber\">" + Integer.toString(n.getVertexAsGenerator().getGeneratorNumber()) + "</data>");
//                            pw.println("                <data key=\"GeneratorState\" >" + Boolean.toString(n.getVertexAsGenerator().getGeneratorState()) + "</data>");
//                            pw.println("                <data key=\"MWGenerator\">" + String.valueOf(n.getVertexAsGenerator().getMWGenerator()) + "</data>");
//
//                            break;
//                        case "ElectricVehicle":
//                            
//                            pw.println("                <data key=\"VehicleNumber\" >" + String.valueOf(n.getVertexAsElectricVehicle().getElectricVehicleNumber()) + "</data>");
//                            pw.println("                <data key=\"VehicleState\">" + Boolean.toString(n.getVertexAsElectricVehicle().getElectricVehicleState()) + "</data>");
//
//                            break;
//                        default:
//                            
//                    }
//                pw.println("            </node>");
//            }
//            //insertar las lineas
//            for (Line l : ElectricalNetwork.getElectricalNetwork().getEdges()) {
//                pw.println("            <edge directed=\"false\" source=\"" + l.getOrigen().getNameVertex() + "\" target=\"" + l.getDestino().getNameVertex() + "\">");
//
//                        pw.println("                <data key=\"name\">" + l.getName() + "</data>");
//                        pw.println("                <data key=\"lineNo\">" + Integer.toString(l.getLineNumber()) + "</data>");
//                        pw.println("                <data key=\"resistance\">" + String.valueOf(l.getResistance()) + "</data>");
//                        pw.println("                <data key=\"reactance\">" + String.valueOf(l.getReactance()) + "</data>");
//                        pw.println("                <data key=\"maxCurrent\">" + String.valueOf(l.getMaxCurrent()) + "</data>");
//                        pw.println("                <data key=\"colorLinea\">" + l.getColorLinea().toString() + "</data>");
//                        // verdadero=continua false= descontinua
//                        pw.println("                <data key=\"estiloLinea\">" + Boolean.toString(l.getEstiloLinea()) + "</data>");
//                        pw.println("                <data key=\"estiloStroke\">" + l.getEstiloStroke().toString() + "</data>");
//                        pw.println("                <data key=\"source\">" + l.getOrigen().getNameVertex() + "</data>");
//                        pw.println("                <data key=\"target\">" + l.getDestino().getNameVertex() + "</data>");
//                pw.println("            </edge>");
//            }
//            pw.println("         </graph>");
//            pw.println("</graphml>");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                // Nuevamente aprovechamos el finally para 
//                // asegurarnos que se cierra el fichero.
//                if (null != fichero) {
//                    fichero.close();
//                }
//            } catch (Exception e2) {
//                e2.printStackTrace();
//            }
//        }
//
//        return true;
//    }
//
//    public boolean saveFile(String filename) {
//        System.out.println("Estamos guardando para usted");
//
//        try {
//
//            gmlWriter = new GraphMLWriter<Vertex, Line>();
//
//            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
//            Map<String, GraphMLMetadata<Vertex>> mapVertexData = new HashMap<String, GraphMLMetadata<Vertex>>();
//
//            /*
//             atributos de vertices
//             */
//            String[] idDataVertex = {"MWGenerator", "Name", "Type", "AngleBeforBlackout", "Angle", "BusCode", "NumberBus", "VoltageBeforBlackout", "Voltage", "FaultNumber", "FaultType", "LineFault", "LoadMVar", "LoadMW", "GeneratorNumber", "GeneratorState", "VehicleNumber", "VehicleState"
//            };
//            for (int i = 0; i < idDataVertex.length; i++) {
//                mapVertexData.put(idDataVertex[i], new GraphMLMetadata<Vertex>(null, null, new Transformer<Vertex, String>() {
//
//                    @Override
//                    public String transform(Vertex i) {
//                        return null;
//                    }
//                }));
//            }
//
//            gmlWriter.setVertexData(mapVertexData);
//
//            for (Vertex n : ElectricalNetwork.getElectricalNetwork().getVertices()) {
//
//                gmlWriter.setVertexIDs(new Transformer<Vertex, String>() {
//                    @Override
//                    public String transform(Vertex n) {
//                        return n.getNameVertex();
//                    }
//                });
//
//                gmlWriter.addVertexData("Name", null, null,
//                        new Transformer<Vertex, String>() {
//                            @Override
//                            public String transform(Vertex n) {
//                                return n.getNameVertex();
//                            }
//                        });
//
//                gmlWriter.addVertexData("Type", null, null,
//                        new Transformer<Vertex, String>() {
//                            @Override
//                            public String transform(Vertex n) {
//                                return n.getVertexTypeName();
//                            }
//                        });
//                System.out.println(n.getNameVertex() + "->" + n.getVertexTypeName());
//                switch (n.getVertexTypeName()) {
//
//                    case "Bus":
//                        System.out.println("case bus:" + n.getNameVertex() + "->" + n.getVertexTypeName());
//                        /* gmlWriter.addVertexData("AngleBeforBlackout", null, null,
//                         new Transformer<Vertex, String>() {
//                         @Override
//                         public String transform(Vertex n) {
//                         if (n.getVertexAsBus() != null) {
//                         return String.valueOf(n.getVertexAsBus().getAngleBeforeBlackout());
//                         } else {
//                         return " ";
//                         }
//                         }
//                         });
//                         */
//                        gmlWriter.addVertexData("Angle", null, null,
//                                new Transformer<Vertex, String>() {
//                                    @Override
//                                    public String transform(Vertex n) {
//                                        // if (n.getVertexAsBus() != null) {
//                                        return String.valueOf(n.getVertexAsBus().getAngleBus());
//                                        // } else {
//                                        //     return " ";
//                                        //}
//                                    }
//                                });
//
//                        gmlWriter.addVertexData("BusCode", null, null,
//                                new Transformer<Vertex, String>() {
//                                    @Override
//                                    public String transform(Vertex n) {
//                                        //if (n.getVertexAsBus() != null) {
//                                        return Integer.toString(n.getVertexAsBus().getBusCode());
//                                        // } else {
//                                        //    return " ";
//                                        //}
//                                    }
//                                });
//
//                        gmlWriter.addVertexData("NumberBus", null, null,
//                                new Transformer<Vertex, String>() {
//                                    @Override
//                                    public String transform(Vertex n) {
//                                        // if (n.getVertexAsBus() != null) {
//                                        return Integer.toString(n.getVertexAsBus().getNumberBus());
//                                        // } else {
//                                        //    return " ";
//                                        //}
//                                    }
//                                });
//
//                        /* gmlWriter.addVertexData("VoltageBeforBlackout", null, null,
//                         new Transformer<Vertex, String>() {
//                         @Override
//                         public String transform(Vertex n) {
//                         if (n.getVertexAsBus() != null) {
//                         return String.valueOf(n.getVertexAsBus().getVoltageBeforeBlackout());
//                         } else {
//                         return " ";
//                         }
//                         }
//                         });*/
//                        gmlWriter.addVertexData("Voltage", null, null,
//                                new Transformer<Vertex, String>() {
//                                    @Override
//                                    public String transform(Vertex n) {
//                                        //  if (n.getVertexAsBus() != null) {
//                                        return String.valueOf(n.getVertexAsBus().getVoltageBus());
//                                        //} else {
//                                        //    return " ";
//                                        //}
//                                    }
//                                });
//                        break;
//                    case "Fault":
//                        System.out.println("case fault " + n.getNameVertex() + "->" + n.getVertexTypeName());
//                        gmlWriter.addVertexData("FaultNumber", null, null,
//                                new Transformer<Vertex, String>() {
//                                    @Override
//                                    public String transform(Vertex n) {
//                                        // if (n.getVertexAsFault() != null) {
//                                        return String.valueOf(n.getVertexAsFault().getFaultNumber());
//                                        // } else {
//                                        //    return " ";
//                                        //}
//                                    }
//                                });
//
//                        gmlWriter.addVertexData("FaultType", null, null,
//                                new Transformer<Vertex, String>() {
//                                    @Override
//                                    public String transform(Vertex n) {
//                                        //  if (n.getVertexAsFault() != null) {
//                                        return String.valueOf(n.getVertexAsFault().getFaultType());
//                                        // } else {
//                                        //    return " ";
//                                        //}
//                                    }
//                                });
//                        /**
//                         * *
//                         *
//                         * aca guardar la linea, pero no se cuales son
//                         * necesarions ahora guarda el nimero de linea
//                         *
//                         *
//                         * });
//                         */
//                        gmlWriter.addVertexData("LineFault", null, null,
//                                new Transformer<Vertex, String>() {
//                                    @Override
//                                    public String transform(Vertex n) {
//                                        // if (n.getVertexAsFault() != null) {
//                                        return String.valueOf(n.getVertexAsFault().getLocation().getLineNumber());
//                                        // } else {
//                                        //     return " ";
//                                        // }
//                                    }
//                                });
//                        break;
//                    case "Load":
//                        System.out.println("case load: " + n.getNameVertex() + "->" + n.getVertexTypeName());
//                        gmlWriter.addVertexData("LoadMVar", null, null,
//                                new Transformer<Vertex, String>() {
//                                    @Override
//                                    public String transform(Vertex n) {
//                                        //  if (n.getVertexAsLoad() != null) {
//                                        return String.valueOf(n.getVertexAsLoad().getLoadMVar());
//                                        // } else {
//                                        //     return " ";
//                                        // }
//                                    }
//                                });
//                        gmlWriter.addVertexData("LoadMW", null, null,
//                                new Transformer<Vertex, String>() {
//                                    @Override
//                                    public String transform(Vertex n) {
//                                        // if (n.getVertexAsLoad() != null) {
//                                        return String.valueOf(n.getVertexAsLoad().getLoadMW());
//                                        // } else {
//                                        //     return " ";
//                                        //}
//                                    }
//                                });
//                        break;
//                    case "Generator":
//                        System.out.println("case generator: " + n.getNameVertex() + "->" + n.getVertexTypeName());
//                        gmlWriter.addVertexData("GeneratorNumber", null, null,
//                                new Transformer<Vertex, String>() {
//                                    @Override
//                                    public String transform(Vertex n) {
//                                        //  if (n.getVertexAsGenerator() != null) {
//                                        return Integer.toString(n.getVertexAsGenerator().getGeneratorNumber());
//                                        // } else {
//                                        //     return " ";
//                                        // }
//                                    }
//                                });
//
//                        gmlWriter.addVertexData("GeneratorState", null, null,
//                                new Transformer<Vertex, String>() {
//                                    @Override
//                                    public String transform(Vertex n) {
//                                        // if (n.getVertexAsGenerator() != null) {
//                                        return Boolean.toString(n.getVertexAsGenerator().getGeneratorState());
//
//                                        // } else {
//                                        //     return " ";
//                                        // }
//                                    }
//                                });
//
//                        gmlWriter.addVertexData("MWGenerator", null, null,
//                                new Transformer<Vertex, String>() {
//                                    @Override
//                                    public String transform(Vertex b) {
//                                        // if (b.getVertexAsGenerator() != null) {
//                                        return String.valueOf(b.getVertexAsGenerator().getMWGenerator());
//                                        // } else {
//                                        //   return " ";
//                                        //}
//                                    }
//                                });
//                        break;
//                    case "ElectricVehicle":
//                        System.out.println("case vehicle: " + n.getNameVertex() + "->" + n.getVertexTypeName());
//                        gmlWriter.addVertexData("VehicleNumber", null, null,
//                                new Transformer<Vertex, String>() {
//                                    @Override
//                                    public String transform(Vertex n) {
//                                        //  if (n.getVertexAsElectricVehicle() != null) {
//                                        return String.valueOf(n.getVertexAsElectricVehicle().getElectricVehicleNumber());
//                                        // } else {
//                                        //     return " ";
//                                        //}
//                                    }
//                                });
//
//                        gmlWriter.addVertexData("VehicleState", null, null,
//                                new Transformer<Vertex, String>() {
//                                    @Override
//                                    public String transform(Vertex n) {
//                                        if (n.getVertexAsElectricVehicle() != null) {
//
//                                            return Boolean.toString(n.getVertexAsElectricVehicle().getElectricVehicleState());
//                                        } else {
//                                            System.out.println("hola miguel");
//                                            return " ";
//                                        }
//                                    }
//
//                                });
//                        break;
//                    default:
//                        System.out.println("defalut " + n.getNameVertex() + "->" + n.getVertexTypeName());
//                }
//                //almacenamos la posicion de vertex
//                gmlWriter.addVertexData("posX", null, null,
//                        new Transformer<Vertex, String>() {
//                            @Override
//                            public String transform(Vertex n) {
//                                return String.valueOf(AreaSimulacion.getAreaSimulacion().getGraphLayout().transform(n).getX());
//                            }
//                        });
//
//                gmlWriter.addVertexData("posY", null, null,
//                        new Transformer<Vertex, String>() {
//                            @Override
//                            public String transform(Vertex n) {
//                                return String.valueOf(AreaSimulacion.getAreaSimulacion().getGraphLayout().transform(n).getY());
//                            }
//                        });
//
//            }
//            /*
//             lines
//             */
//            for (Line l : ElectricalNetwork.getElectricalNetwork().getEdges()) {
//                gmlWriter.addEdgeData("name", null, null,
//                        new Transformer<Line, String>() {
//                            @Override
//                            public String transform(Line l) {
//                                return l.getName();
//                            }
//                        });
//
//                gmlWriter.addEdgeData("lineNo", null, null,
//                        new Transformer<Line, String>() {
//                            @Override
//                            public String transform(Line l) {
//                                return Integer.toString(l.getLineNumber());
//                            }
//                        });
//
//                gmlWriter.addEdgeData("resistance", null, null,
//                        new Transformer<Line, String>() {
//                            @Override
//                            public String transform(Line l) {
//                                return String.valueOf(l.getResistance());
//                            }
//                        });
//
//                gmlWriter.addEdgeData("reactance", null, null,
//                        new Transformer<Line, String>() {
//                            @Override
//                            public String transform(Line l) {
//                                return String.valueOf(l.getReactance());
//                            }
//                        });
//
//                gmlWriter.addEdgeData("maxCurrent", null, null,
//                        new Transformer<Line, String>() {
//                            @Override
//                            public String transform(Line l) {
//                                return String.valueOf(l.getMaxCurrent());
//                            }
//                        });
//
//                gmlWriter.addEdgeData("colorLinea", null, null,
//                        new Transformer<Line, String>() {
//                            @Override
//                            public String transform(Line l) {
//                                return l.getColorLinea().toString();
//                            }
//                        });
//                // verdadero=continua false= descontinua
//                gmlWriter.addEdgeData("estiloLinea", null, null,
//                        new Transformer<Line, String>() {
//                            @Override
//                            public String transform(Line l) {
//                                return Boolean.toString(l.getEstiloLinea());
//                            }
//                        });
//
//                gmlWriter.addEdgeData("estiloStroke", null, null,
//                        new Transformer<Line, String>() {
//                            @Override
//                            public String transform(Line l) {
//                                return l.getEstiloStroke().toString();
//                            }
//                        });
//
//                gmlWriter.addEdgeData("source", null, null,
//                        new Transformer<Line, String>() {
//                            @Override
//                            public String transform(Line l) {
//                                return l.getOrigen().getNameVertex();
//                            }
//                        });
//
//                gmlWriter.addEdgeData("target", null, null,
//                        new Transformer<Line, String>() {
//                            @Override
//                            public String transform(Line l) {
//                                return l.getDestino().getNameVertex();
//                            }
//                        });
//
//            }
//
//            gmlWriter.save(ElectricalNetwork.getElectricalNetwork(), out);
//            System.out.println("guardado");
//        } catch (IOException ex) {
//            Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
//            return false;
//        }
//
//        return true;
//    }
//
//}
