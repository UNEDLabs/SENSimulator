package newsensimulator.control;

import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.picking.PickedState;
import java.awt.*;
import java.awt.event.*;
import java.awt.KeyEventDispatcher;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import javax.swing.*;
import newsensimulator.model.ElectricalNetwork;
import newsensimulator.model.networkelements.*;
import newsensimulator.model.problem.esslocationsize.DailyLoad;
import newsensimulator.model.problem.powerflow.PowerFlow;
import newsensimulator.model.problem.restorationservice.CentralizedServiceRestoration;
import newsensimulator.view.*;
import newsensimulator.view.dialogwindows.*;

/**
 *
 * @author Jose Muñoz Parra
 */
public final class Controller extends DefaultModalGraphMouse implements KeyEventDispatcher, VisualizationViewer.GraphMouse {

    private Properties properties;
    private TFLocationNodeProperties tflnProperties;

    private TFLocationLineProperties tfllProperties;

    private static Controller controlador;
    protected Vertex vertice;
    protected Edge edge;
    protected Point punto, punto2;
    private Rectangle areaSeleccion;
    private PowerFlow flujo;
    private final Timer temporizadorSimulacion;
    //final DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
    //private Timer visualizationChecker;
    private boolean runningSimulation = false;

    public static Controller getController() {

        if (controlador == null) {
            controlador = new Controller();
        }
        return controlador;

    }

    private Controller() {

        vertice = null;
        punto = null;
        punto2 = null;
        //this.setMode(ModalGraphMouse.Mode.PICKING);
        temporizadorSimulacion = new Timer(1000, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // Cada vez que corra el flujo de potencia iniciare este elemento, por 
                //si existen actualizaciones
                if (MainInterface.getMainInterface().getMethodologyName() == "Restoration Service (MAS)") {
                    ElectricalNetwork.getElectricalNetwork().powerFlowInfo();
                }
                if (MainInterface.getMainInterface().getMethodologyName() == "Restoration Service (AG)") {
//                    DailyLoad.getInstance().start();
//                    CentralizedServiceRestoration.getInstance().runOptimizationMethod();
                    
                    
                    MainInterface.getMainInterface().changeStateButtons();
                    stopSimulacion();

                }

            }
        });

    }

    public void borrarNodo(Vertex vertex) {
        /*
         if (!ElectricalNetwork.getElectricalNetwork().getIncidentEdges(vertex).isEmpty()) {
            
         for (Object edge : ElectricalNetwork.getElectricalNetwork().getIncidentEdges(vertex)) {
         Edge line = (Edge) edge;
         }
            
         }
         */
        ElectricalNetwork.getElectricalNetwork().deleteVertex(vertex);
    }

    public void borrarNodos() {
        ElectricalNetwork.getElectricalNetwork().borrarVertices();
    }

    public void borrarLinea(Edge linea) {
        ElectricalNetwork.getElectricalNetwork().deleteLine(linea);
    }

    public void borrarLineas() {
        ElectricalNetwork.getElectricalNetwork().deleteAllLines();
    }

    public void limpiar() {
        ElectricalNetwork.getElectricalNetwork().limpiar();
        MainInterface.getMainInterface().actualizar();
    }

    public int cantidadNodos() {
        return ElectricalNetwork.getElectricalNetwork().cantidadNodos();
    }

    public int cantidadLineas() {
        return ElectricalNetwork.getElectricalNetwork().cantidadLineas();
    }

    public Vertex crearNodo(Point2D coordenadas, String type) {
        Vertex v = VertexFactory.getInstance().create(type);
        ElectricalNetwork.getElectricalNetwork().createElement(v);
        AreaSimulacion.getAreaSimulacion().getGraphLayout().setLocation(v, coordenadas);

        return v;
    }

    public Vertex crearNodo(Point2D coordenadas, String type, boolean MTNodeStatus) {
        Vertex v = VertexFactory.getInstance().create(type);
        v.getVertexAsTFLocationNode().setMTNodeStatus(MTNodeStatus);
        ElectricalNetwork.getElectricalNetwork().createElement(v);
        AreaSimulacion.getAreaSimulacion().getGraphLayout().setLocation(v, coordenadas);

        return v;
    }

    public Vertex crearNodo(Point2D.Double position, String sType, String sName) {

        Vertex v = VertexFactory.getInstance().create(sType, sName);
        ElectricalNetwork.getElectricalNetwork().createElement(v);
        AreaSimulacion.getAreaSimulacion().getGraphLayout().setLocation(v, position);

        return v;
    }

    public Vertex crearFalla(Point2D coordenadas, Edge line) {
        Vertex vertexFault = VertexFactory.getInstance().create("Fault");
        vertexFault.getVertexAsFault().setLocation(line);
        ElectricalNetwork.getElectricalNetwork().crearFalla(vertexFault);
        AreaSimulacion.getAreaSimulacion().getGraphLayout().setLocation(vertexFault, coordenadas);

        return vertexFault;
    }

    public Edge crearLinea(Vertex origen, Vertex destino, int numberLine) {
        if (origen != destino) {
            Edge l = EdgeFactory.getInstance().create(origen, destino, numberLine);
            /*
             if (origen.getVertexType() != 0 || destino.getVertexType() != 0) {
             l.setName("");
             } 
             // setear propiedades de la linea antes de agregarlo al modelo de red
             if (isRunningSimulation() && (origen.getVertexType() == 0 && destino.getVertexType() == 0)) {
             //Codigo switch:
             //      1: Interruptor
             //      2: Interruptor Fusible
             //      3: Interruptor Fusible Controlado
             //
             // Presencia de elementos de conmutacion en la linea
             //        x: Presente       o: No presente
             // -x------o-   -x------x-   -o------o- 
             //      C           CCC          CC
             //
             // Estado de el o los elementos
             //  0: abierto   1: cerrado
             //   00   01   10   11
             l.setSwitchStatus(01);
             l.setEstiloLinea(false);
             }
             if (origen.getVertexType() == 0 && destino.getVertexType() == 0) {
             if (origen.getVertexAsBus().getBusCode() == 1 && destino.getVertexAsBus().getBusCode() == 1) {
             l.setSwitchStatus(01);
             l.setEstiloLinea(false);
             } else {
             l.setSwitchStatus(11);
             l.setEstiloLinea(true);
             }
             }
             */
            float dash[] = {5.0f};
            l.getEdgeAsSimpleLine().setEstiloStroke(new BasicStroke(5.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 1.0f));
            return l;
        }
        AreaSimulacion.getAreaSimulacion().repaint();
        return null;
    }

    public void crearLinea(Vertex origen, Vertex destino) {
        if (origen != destino) {
            Edge l = EdgeFactory.getInstance().create(origen, destino);
            if (l != null) {
                switch (l.getEdgeTypeName()) {
                    case "SimpleLine":
                        if (origen.getVertexType() != 0 || destino.getVertexType() != 0) {
                            l.getEdgeAsSimpleLine().setName("");

                        }
                        if (isRunningSimulation() && (origen.getVertexType() == 0 && destino.getVertexType() == 0)) {
                            //Codigo switch:
                            //      1: Interruptor
                            //      2: Interruptor Fusible
                            //      3: Interruptor Fusible Controlado
                            //
                            // Presencia de elementos de conmutacion en la linea
                            //        x: Presente       o: No presente
                            // -x------o-   -x------x-   -o------o- 
                            //      C           CCC          CC
                            //
                            // Estado de el o los elementos
                            //  0: abierto   1: cerrado
                            //   00   01   10   11

                            l.getEdgeAsSimpleLine().setSwitchStatus(01);
                            l.getEdgeAsSimpleLine().setEstiloLinea(false);
                        }
                        // setear propiedades de la linea antes de agregarlo al modelo de red
                        if (origen.getVertexType() == 0 && destino.getVertexType() == 0) {
                            if (origen.getVertexAsBus().getBusCode() == 1 && destino.getVertexAsBus().getBusCode() == 1) {
                                l.getEdgeAsSimpleLine().setSwitchStatus(01);
                                l.getEdgeAsSimpleLine().setEstiloLinea(false);
                            } else {
                                l.getEdgeAsSimpleLine().setSwitchStatus(11);
                                l.getEdgeAsSimpleLine().setEstiloLinea(true);
                            }
                        }
                        break;
                    case "TFLocationLine":
                        //l.getEdgeAsTFLocationLine().setName("");
                        l.getEdgeAsTFLocationLine().setLineBuilt(true);
                        break;
                    default:
                        System.out.println("Problema en crearLine 345");
                        break;
                }
            }
            ElectricalNetwork.getElectricalNetwork().createLine(l);
        }
        AreaSimulacion.getAreaSimulacion().repaint();
    }

    public void crearLinea(Vertex origen, Vertex destino, boolean initialState) {
        if (origen != destino) {
            Edge l = EdgeFactory.getInstance().create(origen, destino);
            if (origen.getVertexType() != 0 || destino.getVertexType() != 0) {
                switch (l.getEdgeTypeName()) {
                    case "SimpleLine":
                        l.getEdgeAsSimpleLine().setName("");
                        break;
                    case "TFLocationLine":
                        System.out.println("aca poner algo de tflocation...asdfg..");
                        break;
                    default:
                        System.out.println("Problema en crearLine asdadsrgfrdsfewqgaasdf");
                        break;
                }

            }
            // setear propiedades de la linea antes de agregarlo al modelo de red
            if (isRunningSimulation() && (origen.getVertexType() == 0 && destino.getVertexType() == 0)) {
                //Codigo switch:
                //      1: Interruptor
                //      2: Interruptor Fusible
                //      3: Interruptor Fusible Controlado
                //
                // Presencia de elementos de conmutacion en la linea
                //        x: Presente       o: No presente
                // -x------o-   -x------x-   -o------o- 
                //      C           CCC          CC
                //
                // Estado de el o los elementos
                //  0: abierto   1: cerrado
                //   00   01   10   11
                switch (l.getEdgeTypeName()) {
                    case "SimpleLine":
                        l.getEdgeAsSimpleLine().setSwitchStatus(01);
                        l.getEdgeAsSimpleLine().setEstiloLinea(false);
                        break;
                    case "TFLocationLine":
                        System.out.println("aca poner algo de tflocation...asdfwsaregg..");
                        break;
                    default:
                        System.out.println("Problema en crearLine asdadsrgfa rgrdsfewqgaasdf");
                        break;
                }
            }
            if (origen.getVertexType() == 0 && destino.getVertexType() == 0) {
                switch (l.getEdgeTypeName()) {
                    case "SimpleLine":
                        if (origen.getVertexAsBus().getBusCode() == 1 && destino.getVertexAsBus().getBusCode() == 1) {
                            l.getEdgeAsSimpleLine().setSwitchStatus(01);
                            l.getEdgeAsSimpleLine().setEstiloLinea(false);
                        } else {
                            l.getEdgeAsSimpleLine().setSwitchStatus(11);
                            l.getEdgeAsSimpleLine().setEstiloLinea(true);
                        }
                        break;
                    case "TFLocationLine":
                        System.out.println("aca poner algo de tflocation...aregg..");
                        break;
                    default:
                        System.out.println("Problema en crearLine asgrdsfewqgaasdf");
                        break;
                }
            }
            if (!initialState) {
                switch (l.getEdgeTypeName()) {
                    case "SimpleLine":
                        l.getEdgeAsSimpleLine().setSwitchStatus(01);
                        l.getEdgeAsSimpleLine().setEstiloLinea(false);
                        break;
                    case "TFLocationLine":
                        System.out.println("aca poner algo de tflocation...aregg..");
                        break;
                    default:
                        System.out.println("Problema en crearLine asgrdsfewqgaasdf");
                        break;
                }
            }
            ElectricalNetwork.getElectricalNetwork().createLine(l);
        }
        AreaSimulacion.getAreaSimulacion().repaint();
    }

    @SuppressWarnings("serial")
    protected void handlePopup(final MouseEvent event) {
        final Layout<Vertex, Edge> layout = AreaSimulacion.getAreaSimulacion().getGraphLayout();
        punto = event.getPoint();

        GraphElementAccessor<Vertex, Edge> gea = AreaSimulacion.getAreaSimulacion().getPickSupport();
        if (gea != null) {
            vertice = gea.getVertex(layout, punto.getX(), punto.getY());
            edge = gea.getEdge(layout, punto.getX(), punto.getY());
            final PickedState<Vertex> estadoVertice = AreaSimulacion.getAreaSimulacion().getPickedVertexState();
            
            JPopupMenu popup = new JPopupMenu();
            if (vertice != null) {//propiedades para un vertice
                String name = "";
                switch (vertice.getVertexTypeName()) {
                    case "Bus":
                        name = vertice.getVertexAsBus().getName();
                        
                        break;
                    case "Fault":
                        name = vertice.getVertexAsFault().getName();
                        
                        break;
                    case "Load":
                        name = vertice.getVertexAsLoad().getName();
                        
                        break;
                    case "Generador":
                        name = vertice.getVertexAsGenerator().getName();
                        break;
                    case "ElectricVehicle":
                        name = vertice.getVertexAsElectricVehicle().getName();
                        break;
                    case "TFLocationNode":
                        name = vertice.getVertexAsTFLocationNode().getName();
                        break;
                    case "Battery":
                        name = vertice.getVertexAsBattery().getName();
                        break;
                    default:
                        break;
                }
                popup.add(new AbstractAction("Delete " + vertice.getVertexTypeName() + " " + name) {

                    public void actionPerformed(ActionEvent e) {
                        String name = vertice.getVertexTypeName();
                        //System.out.println("=D");
                        estadoVertice.pick(vertice, false);
                        borrarNodo(vertice);
                        MainInterface.getMainInterface().actualizar();
                        if (CentralizedServiceRestoration.getInstance().getState() == true && name == "Fault") {
                            CentralizedServiceRestoration.getInstance().returnInitialState();
                        }
                        //agregado por Lisa
                    }
                });
                popup.addSeparator();
                popup.add(vertice.getVertexTypeName() + " selected");
                //popup.add("Voltaje " + vertice.getVertexAsBus().getVoltageBus() + " @ " + vertice.getVertexAsBus().getAngleBus() + "°");
                popup.add("Info 1");
                popup.add("Info 2");
                popup.add("...");
                popup.addSeparator();
                popup.add(new AbstractAction("Properties") {

                    public void actionPerformed(ActionEvent e) {
                        switch (vertice.getVertexTypeName()) {
                            case "Bus":
                                // Debo separar las ventanas a futuro
                                properties = new Properties(vertice);
                                break;
                            case "Fault":
                                // Debo separar las ventanas a futuro
                                properties = new Properties(vertice);
                                break;
                            case "Load":
                                // Debo separar las ventanas a futuro
                                properties = new Properties(vertice);
                                break;
                            case "Generator":
                                // Debo separar las ventanas a futuro
                                properties = new Properties(vertice);
                                break;
                            case "ElectricVehicle":
                                // Debo separar las ventanas a futuro
                                properties = new Properties(vertice);
                                break;
                            case "TFLocationNode":
                                tflnProperties = new TFLocationNodeProperties(vertice);
                                tflnProperties.setVisible(true);
                                break;
                            case "Battery":
                                // Debo separar las ventanas a futuro
                                properties = new Properties(vertice);
                                break;
                            default:
                                Controller.getController().SENSimulatorConsolePrint("Error en propiedades de NODOS... Cod. 988");
                                break;
                        }
                    }
                });
            } else if (edge != null) {
                // Propiedades para una linea
                switch (edge.getEdgeTypeName()) {
                    case "SimpleLine":
                        popup.add(new AbstractAction("Delete line " + edge.getEdgeAsSimpleLine().getName()) {

                            public void actionPerformed(ActionEvent e) {
                                //System.out.println("=D =D");
                                borrarLinea(edge);
                                MainInterface.getMainInterface().actualizar();
                            }
                        });
                        //linea.getName();
                        popup.addSeparator();
                        popup.add(new AbstractAction("On/Off") {

                            public void actionPerformed(ActionEvent e) {
                                if (edge.getEdgeAsSimpleLine().getEstiloLinea()) {
                                    //linea.setColorLinea(Color.yellow);
                                    edge.getEdgeAsSimpleLine().setEstiloLinea(!edge.getEdgeAsSimpleLine().getEstiloLinea());
                                    edge.getEdgeAsSimpleLine().setSwitchStatus(01);
                                    edge.getEdgeAsSimpleLine().setLabelState("(0---X)");
                                } else {
                                    //linea.setColorLinea(Color.black);
                                    edge.getEdgeAsSimpleLine().setEstiloLinea(!edge.getEdgeAsSimpleLine().getEstiloLinea());
                                    edge.getEdgeAsSimpleLine().setSwitchStatus(11);
                                    edge.getEdgeAsSimpleLine().setLabelState("(X---X)");

                                }
                                MainInterface.getMainInterface().actualizar();
                                AreaSimulacion.getAreaSimulacion().repaint();
                            }
                        });
                        popup.add("I = " + edge.getEdgeAsSimpleLine().getCurrent().getReal() + "+" + edge.getEdgeAsSimpleLine().getCurrent().getImaginary() + "j");
                        popup.add("Im = " + edge.getEdgeAsSimpleLine().getMagCurrent());
                        popup.add("NS: " + edge.getEdgeAsSimpleLine().getOrigen().getVertexName() + " NF: " + edge.getEdgeAsSimpleLine().getDestino().getVertexName());
                        popup.add("...");
                        popup.addSeparator();
                        popup.add(new AbstractAction("Create fault") {
                            public void actionPerformed(ActionEvent e) {
                                //System.out.println("click fault");
                                Vertex v = crearNodo(AreaSimulacion.getAreaSimulacion().getRenderContext().getMultiLayerTransformer().inverseTransform(punto), "Fault");
                                v.getVertexAsFault().setLocation(edge);
                                MainInterface.getMainInterface().actualizar();
                                properties = new Properties(v, true);
                                //properties.setLocationRelativeTo(MainInterface.getMainInterface());
                                MainInterface.getMainInterface().actualizar();
                            }
                        });
                        popup.addSeparator();
                        popup.add(new AbstractAction("Properties") {

                            public void actionPerformed(ActionEvent e) {
                                properties = new Properties(edge);
                            }
                        });
                        break;
                    case "TFLocationLine":

                        popup.add(new AbstractAction("Delete line " + edge.getEdgeAsTFLocationLine().getName()) {

                            public void actionPerformed(ActionEvent e) {
                                //System.out.println("=D =D");
                                borrarLinea(edge);
                                MainInterface.getMainInterface().actualizar();
                            }
                        });

                        popup.addSeparator();

                        popup.add("...");
                        popup.add("...");
                        popup.add("...");
                        popup.add("...");

                        popup.addSeparator();
                        popup.add(new AbstractAction("Properties") {

                            public void actionPerformed(ActionEvent e) {
                                System.out.println("propiedades tfline........");
                                tfllProperties = new TFLocationLineProperties(edge);
                                tfllProperties.setVisible(true);
                            }
                        });

                        break;
                    default:
                        break;
                }
            } else {//Propiedades al crear un bus
                JMenu options = new JMenu("New vertex...");
                JMenuItem menuItemBus = new JMenuItem(new AbstractAction("Create Bus") {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("click bus");
                        Vertex v = crearNodo(AreaSimulacion.getAreaSimulacion().getRenderContext().getMultiLayerTransformer().inverseTransform(punto), "Bus");
                        
                        MainInterface.getMainInterface().actualizar();
                        properties = new Properties(v, true);
                        //properties.setLocationRelativeTo(MainInterface.getMainInterface());
                        MainInterface.getMainInterface().actualizar();
                    }
                });
                options.add(menuItemBus);

                JMenuItem menuItemLoad = new JMenuItem(new AbstractAction("Create Load") {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("click load");
                        Vertex v = crearNodo(AreaSimulacion.getAreaSimulacion().getRenderContext().getMultiLayerTransformer().inverseTransform(punto), "Load");
                        MainInterface.getMainInterface().actualizar();
                        properties = new Properties(v, true);
                   
                        //properties.setLocationRelativeTo(MainInterface.getMainInterface());
                        MainInterface.getMainInterface().actualizar();
                    }
                });
                options.add(menuItemLoad);
                /*
                 JMenuItem menuItemFault = new JMenuItem(new AbstractAction("Create fault") {
                 public void actionPerformed(ActionEvent e) {
                 System.out.println("click fault");
                 Vertex v = crearNodo(AreaSimulacion.getAreaSimulacion().getRenderContext().getMultiLayerTransformer().inverseTransform(punto), "Fault");
                 MainInterface.getMainInterface().actualizar();
                 properties = new Properties(v, true);
                 //properties.setLocationRelativeTo(MainInterface.getMainInterface());
                 MainInterface.getMainInterface().actualizar();
                 }
                 });
                 options.add(menuItemFault);
                 */
                JMenuItem menuItemGen = new JMenuItem(new AbstractAction("Create Generator") {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("click gen");
                        Vertex v = crearNodo(AreaSimulacion.getAreaSimulacion().getRenderContext().getMultiLayerTransformer().inverseTransform(punto), "Generator");
                        MainInterface.getMainInterface().actualizar();
                        properties = new Properties(v, true);
                        //properties.setLocationRelativeTo(MainInterface.getMainInterface());
                        MainInterface.getMainInterface().actualizar();
                    }
                });
                options.add(menuItemGen);
                
                JMenuItem menuItemBat = new JMenuItem(new AbstractAction("Create Battery") {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("click bat");
                        Vertex v = crearNodo(AreaSimulacion.getAreaSimulacion().getRenderContext().getMultiLayerTransformer().inverseTransform(punto), "Battery");
                        MainInterface.getMainInterface().actualizar();
                        properties = new Properties(v, true);
                        //properties.setLocationRelativeTo(MainInterface.getMainInterface());
                        MainInterface.getMainInterface().actualizar();
                    }
                });
                options.add(menuItemBat);
                
                JMenuItem menuItemEV = new JMenuItem(new AbstractAction("Create ElectricVehicle") {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("click ev");
                        Vertex v = crearNodo(AreaSimulacion.getAreaSimulacion().getRenderContext().getMultiLayerTransformer().inverseTransform(punto), "ElectricVehicle");
                        MainInterface.getMainInterface().actualizar();
                        properties = new Properties(v, true);
                        //properties.setLocationRelativeTo(MainInterface.getMainInterface());
                        MainInterface.getMainInterface().actualizar();
                    }
                });
                options.add(menuItemEV);

                popup.add(options);

                /*
                 popup.add(new AbstractAction("New vertex...") {

                 public void actionPerformed(ActionEvent e) {
                 System.out.println("=D =D =D");
                 busCreateDialog = new BusCreate(punto);
                 //Establecemos valores por defecto
                 busCreateDialog.jTextFieldMW.setText("0.0");
                 busCreateDialog.jTextFieldMVar.setText("0.0");
                 //
                 busCreateDialog.setLocationRelativeTo(MainInterface.getMainInterface());
                 busCreateDialog.setVisible(true);

                 }
                 });
                
                 */
                popup.addSeparator();
                popup.add("Accion General 1");
                popup.add("Accion General 2");
                popup.add("Accion General 3");
            }

            popup.show(AreaSimulacion.getAreaSimulacion(), event.getX(), event.getY());
        }
        MainInterface.getMainInterface().actualizar();
        AreaSimulacion.getAreaSimulacion().repaint();
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            System.out.println("click");

            SENSimulatorConsolePrint("Click");
        }
    }

    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            handlePopup(e);
            e.consume();
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            punto = e.getPoint();
            vertice = AreaSimulacion.getAreaSimulacion().getPickSupport().getVertex(AreaSimulacion.getAreaSimulacion().getModel().getGraphLayout(), punto.getX(), punto.getY());

            if (vertice == null) {
                //System.out.println("pasando por aqui" + punto);
                // Si no hay ningún nodo...
                if (!e.isControlDown()) {
                    // ...y no estamos pulsando Ctrl, limpiamos la selección
                    AreaSimulacion.getAreaSimulacion().getPickedVertexState().clear();
                }
                if (e.isAltDown()) {
                    switch (this.getProblemName()) {
                        case "Operation":
                            System.out.println("Punto nodal: " + punto.x + " ; " + punto.y);
                            crearNodo(AreaSimulacion.getAreaSimulacion().getRenderContext().getMultiLayerTransformer().inverseTransform(punto), "Bus");
                            break;
                        case "Planning":
                            if (e.isShiftDown()) {
                                crearNodo(AreaSimulacion.getAreaSimulacion().getRenderContext().getMultiLayerTransformer().inverseTransform(punto), "TFLocationNode", true);
                                //System.out.println("funciona shift y alt");
                            } else {
                                crearNodo(AreaSimulacion.getAreaSimulacion().getRenderContext().getMultiLayerTransformer().inverseTransform(punto), "TFLocationNode");
                            }
                            break;
                        default:
                            break;
                    }
                    punto = null;
                    MainInterface.getMainInterface().actualizar();

                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            handlePopup(e);
            e.consume();
        } else {
            AreaSimulacion.getAreaSimulacion().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            punto = e.getPoint();
            Vertex nodoDestino = AreaSimulacion.getAreaSimulacion().getPickSupport().getVertex(AreaSimulacion.getAreaSimulacion().getModel().getGraphLayout(), punto.getX(), punto.getY());
            PickedState<Vertex> pVS = AreaSimulacion.getAreaSimulacion().getPickedVertexState();

            switch (this.getProblemName()) {
                case "Operation":
                    if (vertice != null && nodoDestino != null) {
                        if (vertice != nodoDestino) {
                            if (vertice.getVertexType() == 0 || nodoDestino.getVertexType() == 0) {
                                //crearLinea(vertice, nodoDestino);
                                if (vertice.getVertexType() != 0 && nodoDestino.getVertexType() == 0 && ElectricalNetwork.getElectricalNetwork().getNeighborCount(vertice) == 0) {
                                    if (e.isShiftDown()) {
                                        crearLinea(vertice, nodoDestino, false);
                                    } else {
                                        crearLinea(vertice, nodoDestino);
                                    }
                                } else if (vertice.getVertexType() == 0 && nodoDestino.getVertexType() != 0 && ElectricalNetwork.getElectricalNetwork().getNeighborCount(nodoDestino) == 0) {
                                    //crearLinea(vertice, nodoDestino);
                                    if (e.isShiftDown()) {
                                        crearLinea(vertice, nodoDestino, false);
                                    } else {
                                        crearLinea(vertice, nodoDestino);
                                    }
                                } else if (vertice.getVertexType() == 0 && nodoDestino.getVertexType() == 0) {
                                    //crearLinea(vertice, nodoDestino);
                                    if (e.isShiftDown()) {
                                        crearLinea(vertice, nodoDestino, false);
                                    } else {
                                        crearLinea(vertice, nodoDestino);
                                    }
                                } else {
                                    System.out.println("Solo un enlace...");
                                }
                            } else {
                                System.out.println("No se permite la conexion entre nodos atributo");
                            }
                        } else {
                            if (e.isControlDown()) {
                                pVS.pick(vertice, !pVS.isPicked(vertice));
                            } else {
                                pVS.clear();
                                pVS.pick(vertice, true);
                            }
                        }
                    }
                    break;
                case "Planning":

                    if (vertice != null && nodoDestino != null) {
                        if (vertice != nodoDestino) {
                            //crearLinea(vertice, nodoDestino);
                            crearLinea(vertice, nodoDestino);

                        } else {
                            if (e.isControlDown()) {
                                pVS.pick(vertice, !pVS.isPicked(vertice));
                            } else {
                                pVS.clear();
                                pVS.pick(vertice, true);
                            }
                        }
                    }

                    break;
                default:
                    System.out.println("accion default 2562...");
                    break;
            }
            if (areaSeleccion != null) {
                // hemos hecho un área de selección, seleccionar esos nodos
                // cogemos todos los nodos que están dentro del área y...
                Collection<Vertex> nodosDentro = AreaSimulacion.getAreaSimulacion().getPickSupport().getVertices(AreaSimulacion.getAreaSimulacion().getModel().getGraphLayout(), areaSeleccion);
                if (e.isControlDown()) {
                    // si control está pulsado, cambiamos su estado
                    for (Vertex n : nodosDentro) {
                        pVS.pick(n, !pVS.isPicked(n));
                    }
                } else {
                    // si no lo está, simplemente los seleccionamos
                    for (Vertex n : nodosDentro) {
                        pVS.pick(n, true);
                    }
                }
                // y nos olvidamos del área de selección
                areaSeleccion = null;
            }
            punto = null;
            punto2 = null;
            MainInterface.getMainInterface().actualizar();
            //AreaSimulacion.getAreaSimulacion().repaint();
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        if (punto != null) {
            if (vertice == null) {
                // Si no habíamos pinchado en un nodo...
                if (e.isShiftDown()) {

                    AreaSimulacion.getAreaSimulacion().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                    Point2D q = AreaSimulacion.getAreaSimulacion().getRenderContext().getMultiLayerTransformer().inverseTransform(punto);
                    Point2D p = AreaSimulacion.getAreaSimulacion().getRenderContext().getMultiLayerTransformer().inverseTransform(e.getPoint());
                    float dx = (float) (p.getX() - q.getX());
                    float dy = (float) (p.getY() - q.getY());
                    AreaSimulacion.getAreaSimulacion().getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).translate(dx, dy);
                    punto.x = e.getX();
                    punto.y = e.getY();
                    /**
                     * ****************
                     */

                } else {
                    // volvemos a poner el cursor normal (por si teníamos otro)
                    AreaSimulacion.getAreaSimulacion().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    // cogemos el punto en el que está el ratón
                    punto2 = e.getPoint();
                    // creamos un rectángulo entre ese punto y el de antes
                    areaSeleccion = new Rectangle(Math.min(punto.x, punto2.x), Math.min(punto.y, punto2.y), Math.abs(punto.x - punto2.x), Math.abs(punto.y - punto2.y));

                }
            } else {
                if (AreaSimulacion.getAreaSimulacion().getPickedVertexState().isPicked(vertice)) {
                    //System.out.println("Hice click en el nodo, pero esta como seleccionado...");
                    Point2D q = AreaSimulacion.getAreaSimulacion().getRenderContext().getMultiLayerTransformer().inverseTransform(punto);
                    Point2D p = AreaSimulacion.getAreaSimulacion().getRenderContext().getMultiLayerTransformer().inverseTransform(e.getPoint());
                    float dx = (float) (p.getX() - q.getX());
                    float dy = (float) (p.getY() - q.getY());
                    for (Vertex n : AreaSimulacion.getAreaSimulacion().getPickedVertexState().getPicked()) {
                        Point2D pn = AreaSimulacion.getAreaSimulacion().getModel().getGraphLayout().transform(n);
                        pn.setLocation(pn.getX() + dx, pn.getY() + dy);
                        AreaSimulacion.getAreaSimulacion().getModel().getGraphLayout().setLocation(n, pn);
                    }
                    punto.x = e.getX();
                    punto.y = e.getY();

                } else {
                    //System.out.println("aqui deberia aparecer la linea...");
                    AreaSimulacion.getAreaSimulacion().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                    punto2 = e.getPoint();
                }
            }
            AreaSimulacion.getAreaSimulacion().repaint();
        }
        e.consume();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        //JadeRuntime.getJadeRuntime().startRMA();
    }

    public void SENSimulatorConsolePrint(String msg) {
        MainInterface.getMainInterface().SENSimulatorConsolePrint(msg);
    }

    public void pintarLinea(Graphics2D graph) {
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (areaSeleccion != null) {
            // Si el área de selección no es nula, la pintamos
            // azul para el borde
            graph.setColor(Color.blue);
            graph.draw(areaSeleccion);
            // azul algo transparente para el relleno 
            graph.setColor(new Color(0x440000FF, true));
            graph.fill(areaSeleccion);
        } else if (punto2 != null) {
            graph.setColor(Color.black);
            graph.drawLine(punto.x, punto.y, punto2.x, punto2.y);
        }
        AreaSimulacion.getAreaSimulacion().repaint();
    }

    public void startSimulacion() {
        temporizadorSimulacion.setInitialDelay(10);
        temporizadorSimulacion.start();
        runningSimulation = temporizadorSimulacion.isRunning();
        System.out.println(runningSimulation);

    }

    public void stopSimulacion() {
        temporizadorSimulacion.stop();
        runningSimulation = temporizadorSimulacion.isRunning();
        //MainInterface.getMainInterface().
        System.out.println("Estado simulacion: " + runningSimulation);
    }

    public boolean isRunningSimulation() {
        return runningSimulation;
    }

    public void setSimulationParameters(int baseMWA, double accuracy, double accel, int maxIter) {
        ElectricalNetwork.getElectricalNetwork().setSimulationParameters(baseMWA, accuracy, accel, maxIter);
    }

    public Vertex getVertexByName(String nameVertex) {
        return ElectricalNetwork.getElectricalNetwork().getBusByName(nameVertex);
    }

    public double getOperationMode() {
        /*
         0: Power Flow
         1: Restoration Service
         2: TF Location
         */

 /*
         Debo arreglar esto!!!!!!!!!!
        
         */
        return MainInterface.getMainInterface().getOperationMode();
    }

    public String getProblemName() {
        return MainInterface.getMainInterface().getProblemName();
    }

    public boolean dispatchKeyEvent(KeyEvent e) {
        // Si la interfaz está activa y el evento es de tipo KEY_PRESSED,
        System.out.println("el hector se putea siempre ahora :D");
        if (MainInterface.getMainInterface().isActive() && e.getID() == KeyEvent.KEY_PRESSED) {
            // lo tratamos.
            keyPressed(e);
            return true;
        }
        return false;
    }

    private void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_0:
            case KeyEvent.VK_NUMPAD0:
                //AreaSimulacion.getAreaSimulacion().verTodo();
                AreaSimulacion.getAreaSimulacion().repaint();
                break;
            // La combinación Ctrl. + A selecciona todos los nodos de la red
            case KeyEvent.VK_A:
                if (e.isControlDown()) {
                    for (Vertex n : ElectricalNetwork.getElectricalNetwork().getVertices()) {
                        AreaSimulacion.getAreaSimulacion().getPickedVertexState().pick(n, true);
                    }
                }
                MainInterface.getMainInterface().actualizar();
                break;
            // La tecla Supr. borra todos los nodos seleccionados
            case KeyEvent.VK_DELETE:
                Set<Vertex> seleccion = AreaSimulacion.getAreaSimulacion().getPickedVertexState().getPicked();
                for (Vertex n : seleccion) {
                    borrarNodo(n);
                }
                MainInterface.getMainInterface().actualizar();
                break;
        }
    }

    public ArrayList<double[]> getBusDataSystem() {
        return ElectricalNetwork.getElectricalNetwork().getBusDataSystem();
    }

    public ArrayList<double[]> getLineDataSystem() {
        return ElectricalNetwork.getElectricalNetwork().getLineDataSystem();
    }

    public void setNodeActiveInSimulation(String bus) {
        ElectricalNetwork.getElectricalNetwork().setNodeActiveInSimulation(bus);
    }

    public String getNodeActiveInSimulation() {
        return ElectricalNetwork.getElectricalNetwork().getNodeActiveInSimulation();
    }

    public void runTFLocationAGAlgorithm() {
        ElectricalNetwork.getElectricalNetwork().runTFLocationAGAlgorithm();
    }

    public Point2D getVertexPoint(Vertex v) {
        return AreaSimulacion.getAreaSimulacion().getVertexPoint(v);
    }
}
