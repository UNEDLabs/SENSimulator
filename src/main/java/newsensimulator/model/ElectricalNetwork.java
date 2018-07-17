/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import newsensimulator.model.JADEplatform.JadeRuntime;
import newsensimulator.model.networkdata.NetworkData;
import newsensimulator.model.networkelements.Edge;
import newsensimulator.model.networkelements.Vertex;
import newsensimulator.model.problem.powerflow.PowerFlow;
import newsensimulator.model.problem.tdslocationforss.Run_Algorithm.TDs_Location.TDs_Location;
import newsensimulator.model.utils.ElementCounter;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Jose Muñoz Parra
 */
public class ElectricalNetwork extends SparseMultigraph<Vertex, Edge> implements Serializable {

    private static ElectricalNetwork modeloRed;
    private Collection<Vertex> allNodes;
    private static Collection<Edge> allLines;
    private ArrayList<Vertex> slackBusNodes;

    private PowerFlow pf;

    //VARIABLES FLUJO POTENCIA
    private int basemva = 100;
    private double accuracy = 0.001;
    private double accel = 1.8;
    private int maxIter = 100;

    //[ N° / code / mag / degree / LoadMW / LoadMVar / G-MW / G-MVar / G-Qmin / G-Qmax / shunt cap MVar ]
    private final int nIndex = 0;
    private final int codeIndex = 1;
    private final int magIndex = 2;
    private final int degreeIndex = 3;
    private final int loadMWIndex = 4;
    private final int loadMVarIndex = 5;
    private final int gMWIndex = 6;
    private final int gMVarIndex = 7;
    private final int gQMinIndex = 8;
    private final int gQMaxIndex = 9;
    private final int shuntCapIndex = 10;

    private final int startNodeIndex = 0;
    private final int endNodeIndex = 1;
    private final int ZRIndex = 2;
    private final int ZXIndex = 3;
    private final int bIndex = 4;
    private final int tapIndex = 5;
    private final int numberLineIndex = 6;
    private String nodeActiveInSimulation = "Bus";

    public static ElectricalNetwork getElectricalNetwork() {
        if (modeloRed == null) {
            modeloRed = new ElectricalNetwork();
        }
        return modeloRed;
    }

    public void setElectricalNetwork(ElectricalNetwork en) {
        if (en != null) {
            modeloRed = en;
        } else {
            System.out.println("nulo el  modelo");
        }
    }

    public void createElement(Vertex vertex) {
        switch (vertex.getVertexTypeName()) {
            case "Bus":
                System.out.println("creando bus: " + vertex.getVertexAsBus().getName());
                addVertex(vertex);
                Object[] args = new Object[1];
                args[0] = vertex;
                // creamos los agentes aqui mejor para que los nombre de BUS en NetworkModel y JADE sean los mismos
                if (vertex.getVertexAsBus().getBusCode() == 1) {
                    JadeRuntime.getJadeRuntime().startAgent(vertex.getVertexAsBus().getName(), "newsensimulator.model.agents.RestorationAgent", args);
                } else {
                    //JadeRuntime.getJadeRuntime().startAgent(vertex.getVertexAsBus().getName(), "newsensimulator.model.agents.DeviceAgent", args);
                    JadeRuntime.getJadeRuntime().startAgent(vertex.getVertexAsBus().getName(), "newsensimulator.model.agents.RestorationAgent", args);
                }

                System.out.println("Bus creado");
                break;
            case "Fault":
                System.out.println("creando falla: " + vertex.getVertexAsFault().getName());
                addVertex(vertex);
                System.out.println("falla creada");
                break;
            case "Load":
                System.out.println("creando carga: " + vertex.getVertexAsLoad().getName());
                addVertex(vertex);
                System.out.println("carga creada");
                break;
            case "Generator":
                System.out.println("creando generador: " + vertex.getVertexAsGenerator().getName());
                addVertex(vertex);
                System.out.println("generador creado");
                break;
            case "ElectricVehicle":
                System.out.println("creando vehiculo electrico: " + vertex.getVertexAsElectricVehicle().getName());
                addVertex(vertex);
                System.out.println("vehiculo electrico creado");
                break;
            case "TFLocationNode":
                System.out.println("creando Nodo TF: " + vertex.getVertexAsTFLocationNode().getName());
                addVertex(vertex);
                System.out.println("Nodo TF creado");
                break;
            case "Battery":
                System.out.println("creando Nodo Battery: " + vertex.getVertexAsBattery().getName());
                addVertex(vertex);
                System.out.println("Nodo battery creado");
                break;

            default:
                System.out.println("creando otro tipo de nodo. Cod 857");
                break;
        }
    }

    public void crearFalla(Vertex vertex) {
        System.out.println("Generando falla en linea: " + vertex.getVertexAsFault().getLocation().getEdgeAsSimpleLine().getName());
        addVertex(vertex);
    }

    public Vertex obtenerBus(String nameNode) {
        for (Vertex o : getVertices()) {
            if (o.getVertexType() == 0) {
                if (o.getVertexAsBus().getName().equals(nameNode)) {
                    return o;
                }
            }
        }
        return null;
    }

    public Vertex getBusByName(String name) {
        //System.out.println("NOMBRE: " + name);
        for (Vertex o : getVertices()) {
            if (o.getVertexType() == 0) {
                if (o.getVertexAsBus().getName().equals(name)) {
                    return o;
                }
            } else if (o.getVertexType() == 1) {
                if (o.getVertexAsFault().getName().equals(name)) {
                    return o;
                }
            } else if (o.getVertexType() == 2) {
                if (o.getVertexAsLoad().getName().equals(name)) {
                    return o;
                }
            } else if (o.getVertexType() == 3) {
                if (o.getVertexAsGenerator().getName().equals(name)) {
                    return o;
                }
            } else if (o.getVertexType() == 4) {
                if (o.getVertexAsElectricVehicle().getName().equals(name)) {
                    return o;
                }

            } else if (o.getVertexType() == 5) {
                if (o.getVertexAsTFLocationNode().getName().equals(name)) {
                    return o;
                }
            } else if (o.getVertexType() == 6) {
                if (o.getVertexAsBattery().getName().equals(name)) {
                    return o;
                }
            }
        }
        return null;
    }

    public void deleteVertex(Vertex vertex) {
        switch (vertex.getVertexTypeName()) {
            case "Bus":
                if (JadeRuntime.isActive()) {
                    JadeRuntime.getJadeRuntime().killAgent(vertex.getVertexAsBus().getName());
                    //System.out.print("Vertice " + vertex.getVertexTypeName() + "removido");
                }
                ElementCounter.getElementCounter().removeBusNumber(vertex.getVertexAsBus().getNumberBus());
                break;
            case "Fault":
                System.out.println("se deben poner las acciones a realizar antes de eliminar el vertice");
                ElementCounter.getElementCounter().removeFaultNumber(vertex.getVertexAsFault().getFaultNumber());
                vertex.getVertexAsFault().getLocation().getEdgeAsSimpleLine().setInFalut(false);
                break;
            case "Load":
                System.out.println("se deben poner las acciones a realizar antes de eliminar el vertice");
                ElementCounter.getElementCounter().removeLoadNumber(vertex.getVertexAsLoad().getLoadNumber());
                break;
            case "Generator":
                System.out.println("se deben poner las acciones a realizar antes de eliminar el vertice");
                ElementCounter.getElementCounter().removeGeneratorNumber(vertex.getVertexAsGenerator().getGeneratorNumber());
                break;
            case "ElectricVehicle":
                System.out.println("se deben poner las acciones a realizar antes de eliminar el vertice");
                ElementCounter.getElementCounter().removeElectricVehicleNumber(vertex.getVertexAsElectricVehicle().getElectricVehicleNumber());
                break;
            case "TFLocationNode":
                System.out.println("se deben poner las acciones a realizar antes de eliminar el vertice");
                ElementCounter.getElementCounter().removeBusNumber(vertex.getVertexAsBus().getNumberBus());
                System.out.println("Si esto sucede hay algo raro...");
                break;
            case "Battery":
                System.out.println("se deben poner las acciones a realizar antes de eliminar el vertice");
                ElementCounter.getElementCounter().removeBatteryNumber(vertex.getVertexAsBattery().getBatteryNumber());
                
                break;
            default:
                System.out.println("default delete vertex. Cod. 885");
                break;
        }
        removeVertex(vertex);
    }

    public void borrarVertices() {
        ArrayList<Vertex> alist = new ArrayList<Vertex>();
        for (Vertex v : getVertices()) {
            //System.out.print(n.getName());
            alist.add(v);
        }
        for (int i = 0; i < alist.size(); i++) {
            deleteVertex((Vertex) alist.get(i));
        }
    }

    public void createLine(Edge line) {
        if (line != null) {
            switch (line.getEdgeTypeName()) {
                case "SimpleLine":
                    if (findEdge(line.getEdgeAsSimpleLine().getOrigen(), line.getEdgeAsSimpleLine().getDestino()) == null) {

                        addEdge(line, line.getEdgeAsSimpleLine().getOrigen(), line.getEdgeAsSimpleLine().getDestino(), EdgeType.UNDIRECTED);

                        Vertex origen = line.getEdgeAsSimpleLine().getOrigen();
                        Vertex destino = line.getEdgeAsSimpleLine().getDestino();
                        if (origen.getVertexTypeName().equals("Bus") && destino.getVertexTypeName().equals("Bus")) {
                            origen.getVertexAsBus().setIncidentLinesNumber(this.degree(origen));
                            origen.getVertexAsBus().addIncidentLine(line);
                            destino.getVertexAsBus().setIncidentLinesNumber(this.degree(destino));
                            destino.getVertexAsBus().addIncidentLine(line);
                        }
                        /*
                         if (destino.getVertexTypeName().equals("Bus") && destino.getVertexTypeName().equals("Bus")) {
                         destino.getVertexAsBus().setIncidentLinesNumber(this.degree(destino));
                         destino.getVertexAsBus().addIncidentLine(linea);
                         }
                         */
                    }
                    break;
                case "TFLocationLine":
                    if (findEdge(line.getEdgeAsTFLocationLine().getSourceVertex(), line.getEdgeAsTFLocationLine().getTargetVertex()) == null) {

                        addEdge(line, line.getEdgeAsTFLocationLine().getSourceVertex(), line.getEdgeAsTFLocationLine().getTargetVertex(), EdgeType.UNDIRECTED);
                    }
                    break;
            }
        }
    }

    public Edge getEdgeByName(String nameLine) {
        for (Edge e : getEdges()) {
            if (e.getEdgeName().equals(nameLine)) {
                return e;
            }
        }
        return null;
    }

    public void deleteLine(Edge line) {
        switch (line.getEdgeTypeName()) {
            case "SimpleLine":
                if (line.getEdgeAsSimpleLine().getOrigen().getVertexTypeName().equals("Bus") && line.getEdgeAsSimpleLine().getDestino().getVertexTypeName().equals("Bus")) {
                    System.out.println("linea a eliminar.... " + line.getEdgeAsSimpleLine().getLineNumber());
                    ElementCounter.getElementCounter().removeLineNumber(line.getEdgeAsSimpleLine().getLineNumber());
                }
                removeEdge(line);

                Vertex origen = line.getEdgeAsSimpleLine().getOrigen();
                Vertex destino = line.getEdgeAsSimpleLine().getDestino();

                if (origen.getVertexTypeName().equals("Bus") && destino.getVertexTypeName().equals("Bus")) {
                    origen.getVertexAsBus().setIncidentLinesNumber(this.degree(origen));
                    origen.getVertexAsBus().removeIncidentLine(line);
                    destino.getVertexAsBus().setIncidentLinesNumber(this.degree(destino));
                    destino.getVertexAsBus().removeIncidentLine(line);
                }
                break;
            case "TFLocationLine":
                //pendiente
                removeEdge(line);
                break;
            default:
                break;
        }

    }

    public void deleteAllLines() {
        ArrayList<String> alist = new ArrayList<>();
        for (Edge e : getEdges()) {
            //System.out.print(n.getName());
            alist.add(e.getEdgeName());
        }
        for (String lineName : alist) {
            deleteLine(getEdgeByName(lineName));
        }
    }

    public int cantidadNodos() {
        return getVertexCount();
    }

    public int cantidadLineas() {
        return getEdgeCount();
    }

    // Borra todos los nodos y lineas de la red
    public void limpiar() {
        //ElementCounter.getElementCounter().resetCounters();

        deleteAllLines();
        borrarVertices();

        System.out.println("Finalizado el proceso de limpieza");
        System.out.println("nodos finales: " + cantidadNodos());
        System.out.println("lineas finales: " + cantidadLineas());
    }

    public ElectricalNetwork getActiveModel() {
        ElectricalNetwork activeModel = new ElectricalNetwork();

        Edge linea = null;
        Vertex nodo = null;

        Collection<Vertex> n = ElectricalNetwork.getElectricalNetwork().getVertices();
        Collection<Edge> l = ElectricalNetwork.getElectricalNetwork().getEdges();

        //System.out.println(allLines.toString());
        for (Iterator iter = n.iterator(); iter.hasNext();) {
            activeModel.addVertex((Vertex) iter.next());
        }

        for (Iterator iter = l.iterator(); iter.hasNext();) {
            linea = (Edge) iter.next();
            if (linea.getEdgeAsSimpleLine().getEstiloLinea()) {
                activeModel.addEdge(linea, linea.getEdgeAsSimpleLine().getOrigen(), linea.getEdgeAsSimpleLine().getDestino());
            }
        }
        return activeModel;
    }

    /**
     * El modelo esta compuesto por elementos variados para que el aspecto sea
     * mas amigable. En el caso del modelo, solo interesan los buses y los
     * elementos adyacentes, distintos a otros buses, seran atributos y
     * propiedades del bus.
     */
    public ElectricalNetwork getBusModel() {
        //ArrayList subRedes = new ArrayList();
        ElectricalNetwork busModel = new ElectricalNetwork();

        Collection<Vertex> vertex = ElectricalNetwork.getElectricalNetwork().getVertices();
        Collection<Edge> lines = ElectricalNetwork.getElectricalNetwork().getEdges();

        for (Iterator iter = vertex.iterator(); iter.hasNext();) {
            Vertex bus = (Vertex) iter.next();
            if (bus.getVertexType() == 0) {
                busModel.addVertex(bus);
            }
        }

        for (Iterator iter = lines.iterator(); iter.hasNext();) {
            Edge linea = (Edge) iter.next();
            if (linea.getEdgeAsSimpleLine().getEstiloLinea() && linea.getEdgeAsSimpleLine().getOrigen().getVertexType() == 0 && linea.getEdgeAsSimpleLine().getDestino().getVertexType() == 0) {
                busModel.addEdge(linea, linea.getEdgeAsSimpleLine().getOrigen(), linea.getEdgeAsSimpleLine().getDestino());
            }
        }
        return busModel;
    }

    public ArrayList getSubNetworks() {
        //En este arreglo guardo los subModelos
        ArrayList subNetworks = new ArrayList();
        //Obtengo todos los nodos activos (conectados a una barra slack directa 
        //o indirectamente)
        Collection allActiveVertex = this.getActiveModel().getVertices();

        //Este arreglo lo uso para determinar que barras no estan conectadas al sistema
        //ArrayList lostVertex = new ArrayList();
        //int[][] matrizAislados;
        //A partir de cada Barra Slack se forma un modelo
        for (Object v : getSlackBusNodes()) {
            Vertex vertice = (Vertex) v;

            //Se creo un objeto tipo grafo para generar los subModelos
            SparseMultigraph<Vertex, Edge> subNet = new SparseMultigraph();
            //Y agrego como primer nodo la barra slack
            subNet.addVertex(vertice);
            // Ejecuto el algoritmo para ver si un determinado nodo pertenece 
            //al subsistema de la barra slack en analisis
            DijkstraShortestPath<Vertex, Edge> alg = new DijkstraShortestPath(getActiveModel());
            //Un arreglo dinamico para las lineas de nuestro subsistema
            ArrayList<Edge> linesSubSystem = new ArrayList();
            // Comienza la busquedas de nodos de mi subsistema
            for (Object n : allActiveVertex) {
                Vertex nFin = (Vertex) n;
                if (nFin.getVertexType() == 0) {
                    List<Edge> path = alg.getPath(vertice, nFin);
                    if (!path.isEmpty()) {
                        subNet.addVertex(nFin);
                        for (Object l : path) {
                            Edge line = (Edge) l;
                            linesSubSystem.add(line);
                        }
                    } else {
                        if (nFin.getVertexAsBus().getBusCode() != 1) {
                            //lostVertex.add(nFin);
                        }
                    }
                }
            }
            //Ahora que se agregaron todos los nodos, se podece a agregar las lineas
            //Pero antes eliminaremos los duplicados
            HashSet<Edge> hashSet = new HashSet<Edge>(linesSubSystem);
            linesSubSystem.clear();
            linesSubSystem.addAll(hashSet);
            //Ahora si la agregamos
            for (Object l : linesSubSystem) {
                Edge line = (Edge) l;
                subNet.addEdge(line, line.getEdgeAsSimpleLine().getOrigen(), line.getEdgeAsSimpleLine().getDestino());
            }
            //Y con el modelo listo, al Arreglo dinamico que devolvemos
            subNetworks.add(subNet);
        }
        return subNetworks;
    }

    public ArrayList getDataNetwork() {
        ArrayList data = new ArrayList();

        ArrayList model = getSubNetworks();
        for (Object m : model) {
            SparseMultigraph<Vertex, Edge> subNet = (SparseMultigraph<Vertex, Edge>) m;
            //Datos para buses
            //[ N° / code / mag / degree / LoadMW / LoadMVar / G-MW / G-MVar / G-Qmin / G-Qmax / shunt cap MVar ] 

            double[][] dataBus = new double[subNet.getVertexCount()][11];
            int i = 0;
            for (Object v : subNet.getVertices()) {
                Vertex vertex = (Vertex) v;
                //Numero del bus (despues debo adaptarlo a una lista secuencia partiendo desde 1)
                dataBus[i][nIndex] = Double.valueOf(vertex.getVertexAsBus().getName().substring(1));
                // Codigo (0: slack, 1: load, 2: regulated)
                dataBus[i][codeIndex] = (double) vertex.getVertexAsBus().getBusCode();
                // Magnitud de la tension en la barra
                dataBus[i][magIndex] = vertex.getVertexAsBus().getVoltageBus();
                // Angulo de la barra
                dataBus[i][degreeIndex] = vertex.getVertexAsBus().getAngleBus();
                // Vemos las cargas unidas al bus
                double loadMW = 0;
                double loadMVar = 0;
                double genMW = 0;
                double genMVar = 0;
                double minGenMVar = 0;
                double maxGenMVar = 0;
                double shuntCap = 0;

                for (Object vert : this.getNeighbors(vertex)) {
                    Vertex vrtx = (Vertex) vert;
                    switch (vrtx.getVertexType()) {
                        // Vertex de carga
                        case 2:
                            loadMW = loadMW + vrtx.getVertexAsLoad().getLoadMW();
                            loadMVar = loadMVar + vrtx.getVertexAsLoad().getLoadMVar();
                            break;
                        // Vertex de generacion
                        case 3:
                            genMW = genMW + vrtx.getVertexAsGenerator().getMWGenerator();
                            genMVar = genMVar + vrtx.getVertexAsGenerator().getMVarGenerator();
                            minGenMVar = minGenMVar + vrtx.getVertexAsGenerator().getMinMVarGenerator();
                            maxGenMVar = maxGenMVar + vrtx.getVertexAsGenerator().getMaxMVarGenerator();
                            break;
                        // Vertex de EV
                        case 4:
                            break;

                        case 6:
                            if (vrtx.getVertexAsBattery().getBatteryType()==0) {
                                genMW = genMW + vrtx.getVertexAsBattery().getBatteryMW();
                            }
                            if (vrtx.getVertexAsBattery().getBatteryType()==1) {
                                loadMW = loadMW + vrtx.getVertexAsBattery().getBatteryMW();
                            }

                            break;
                    }
                }
                dataBus[i][loadMWIndex] = loadMW;
                dataBus[i][loadMVarIndex] = loadMVar;
                dataBus[i][gMWIndex] = genMW;
                dataBus[i][gMVarIndex] = genMVar;
                dataBus[i][gQMinIndex] = minGenMVar;
                dataBus[i][gQMaxIndex] = maxGenMVar;
                dataBus[i][shuntCapIndex] = shuntCap;

                i++;
            }

            data.add(dataBus);

            /*
             for (int c = 0; c < subNet.getVertexCount(); c++) {
             for (int d = 0; d < 11; d++) {
             //System.out.print(dataBus[c][d] + "    ");
             if (d == 10) {
             //System.out.println("");
             }
             }
             }
            
             for (Object l : subNet.getEdges()) {
             Edge li = (Edge) l;
             System.out.println(li.getName() + "  ");
             }
             */
            //Ahora recolecto los datos de las lineas
            double[][] dataLine = new double[subNet.getEdgeCount()][7];
            Collection<Edge> edges = subNet.getEdges();

            i = 0;
            for (Object l : edges) {
                Edge line = (Edge) l;
                //System.out.println(line.getName() + "  ");
                dataLine[i][startNodeIndex] = (double) line.getEdgeAsSimpleLine().getOrigen().getVertexAsBus().getNumberBus();
                dataLine[i][endNodeIndex] = (double) line.getEdgeAsSimpleLine().getDestino().getVertexAsBus().getNumberBus();
                dataLine[i][ZRIndex] = line.getEdgeAsSimpleLine().getResistance();
                dataLine[i][ZXIndex] = line.getEdgeAsSimpleLine().getReactance();
                dataLine[i][bIndex] = 0.0;
                dataLine[i][tapIndex] = 1.0;
                dataLine[i][numberLineIndex] = line.getEdgeAsSimpleLine().getLineNumber();

                i++;
            }

            data.add(dataLine);

        }
        //System.out.println("largo data:" + data.size());

        return data;
    }

    public void powerFlowInfo() {
        ArrayList data = this.getDataNetwork();
        ArrayList orden = new ArrayList();
        double[][] dataBus = null;
        double[][] dataLine = null;

        int j = 0;
        for (Object d : data) {
            if (j % 2 == 0) {
                dataBus = (double[][]) d;
                //System.out.println("paso al databus... ");
                //int filas = dataBus.length / 11;
                //System.out.println("filas bus");
                for (int i = 0; i < dataBus.length; i++) {
                    orden.add(dataBus[i][0]);
                    dataBus[i][0] = (double) i + 1;
                }

                for (int c = 0; c < dataBus.length; c++) {
                    for (int e = 0; e < 11; e++) {
                        //System.out.print(dataBus[c][e] + "    ");
                        if (e == 10) {
                            //System.out.println("");
                        }
                    }
                }

            } else {
                //System.out.println("luego al dataline");
                dataLine = (double[][]) d;
                //int filas = dataLine.length / 6;
                for (int i = 0; i < dataLine.length; i++) {
                    double nI = dataLine[i][0];
                    double nF = dataLine[i][1];
                    for (int k = 0; k < orden.size(); k++) {
                        if (nI == (double) orden.get(k)) {
                            dataLine[i][0] = (double) k + 1;
                        } else if (nF == (double) orden.get(k)) {
                            dataLine[i][1] = (double) k + 1;
                        }
                    }
                }

                for (int c = 0; c < dataLine.length; c++) {
                    for (int e = 0; e < 7; e++) {
                        //System.out.print(dataLine[c][e] + "    ");
                        if (e == 6) {
                            //System.out.println("");
                        }
                    }
                }

                //System.out.println("");
                for (int i = 0; i < orden.size(); i++) {
                    //System.out.print(orden.get(i) + "  ");
                }

                if (dataLine.length > 0) {
                    pf = new PowerFlow(dataLine, dataBus, new int[0]);
                    pf.runLoadFlow();
                    pf.getTime();

                    pf.getMWTotalLoss();

                    double[] magV = pf.getMagVoltBus();
                    double[] angle = pf.getAngleBus();

                    for (int i = 0; i < magV.length; i++) {
                        double nBus = (double) orden.get(i);
                        Vertex bus = getBusByName("B" + (int) nBus);
                        if (bus != null) {
                            bus.getVertexAsBus().setVoltageBus(magV[i]);
                            bus.getVertexAsBus().setAngleBus(angle[i]);
                        }
                    }

                    double[] magC = pf.getMagCurrent();
                    Complex[] current = pf.getLineCurrent();
                    //System.out.println("current largo" + current.length);
                    //System.out.println("largo magC " + magC.length);

                    for (int i = 0; i < magC.length; i++) {
                        //System.out.println("L" + (int) dataLine[i][6]);
                        Edge line = getEdgeByName("L" + (int) dataLine[i][6]);
                        if (line != null) {
                            line.getEdgeAsSimpleLine().setCurrent(current[i]);
                            line.getEdgeAsSimpleLine().setMagCurrent(magC[i]);
                        }
                    }
                }
                orden.clear();
            }
            j++;
        }
    }

    public ArrayList getSlackBusNodes() {
        slackBusNodes = new ArrayList();
        allNodes = this.getVertices();

        for (Iterator iter = allNodes.iterator(); iter.hasNext();) {
            Vertex vertex = (Vertex) iter.next();
            if (vertex.getVertexType() == 0) {
                if (vertex.getVertexAsBus().getBusCode() == 1) {
                    slackBusNodes.add(vertex);
                }
            }
        }

        return slackBusNodes;
    }

    public void setSimulationParameters(int baseMVA, double accuracy, double accel, int maxIter) {
        this.basemva = baseMVA;
        this.accuracy = accuracy;
        this.accel = accel;
        this.maxIter = maxIter;
    }

    public void setBaseMVA(int basemva) {
        this.basemva = basemva;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public void setAccel(double accel) {
        this.accel = accel;
    }

    public void setMaxIter(int maxIter) {
        this.maxIter = maxIter;
    }

    public int getBaseMVA() {
        return basemva;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double getAccel() {
        return accel;
    }

    public int getMaxIter() {
        return maxIter;
    }

    public int getCountFundamentalLoops() {
        return ElementCounter.getElementCounter().getCountFundamentalLoops();
    }

    public int getTotalBuses() {
        return ElementCounter.getElementCounter().getTotalBuses();
    }

    /**
     * Sobrecarga del método clone() para evitar que haya más de una instancia
     * de la Red.
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        // Estamos usando un objeto que usa el patrón Singleton. Como no podemos
        // tener más instancias de la clase, sobrecargamos el método clone()
        // para evitar que se creen copias.
        throw new CloneNotSupportedException();
    }

    /*
     public boolean checkerIsolatedNodes() {
     // Variable para saber si existen nodos aislados en la red
     boolean check = false;
     Vertex nodoSlack = null;
     ArrayList<Integer> bSlack = new ArrayList<>();
     ArrayList<Vertex> nodosAislados;

     /*
     * Para saber si existe un nodo aislado, utilizaré el algoritmo
     * integrado de Dijkstra en JUNG, tomando como referencia las barras
     * Slack (nunca interconectadas), y buscando los caminos hacia cada uno
     * de los nodos. En caso de que el nodo este aislado, no existirá tal
     * camino.
     *
     for (int i = 0; i < nBus.length; i++) {
     if (cBus[i] == 1) {
     bSlack.add((int) nBus[i]);
     }
     }
     int[][] matrizAislados;
     if (bSlack.size() > 0) {
     //System.out.println("bSlack.size(): " +bSlack.size());
     //System.out.println("nBus.length: " +nBus.length);
     matrizAislados = new int[bSlack.size()][nBus.length];

     //System.out.println("Arraylist con: " +bSlack.toString());
     int fila = 0;
     for (Iterator iter = bSlack.iterator(); iter.hasNext();) {
     //Barra Slack es unica en la red, pero puede no ser asi en la simulacion...
     //No debe haber 2 de ellas interconectadas
     nodoSlack = ElectricalNetwork.getElectricalNetwork().obtenerBus("B" + (int) iter.next());

     for (int i = 0; i < nBus.length; i++) {
     //System.out.println("estoy aca...");
     if (nodoSlack.getVertexAsBus().getNumberBus() != nBus[i]) {
     DijkstraShortestPath<Vertex, Line> alg = new DijkstraShortestPath(ElectricalNetwork.getElectricalNetwork().getActiveModel());
     //System.out.println("Error aca?");
     int aux = (int) nBus[i];
     Vertex nodoEnBusqueda = ElectricalNetwork.getElectricalNetwork().obtenerBus("B" + aux);
     //System.out.println("me pasaron este nodo " +nodoEnBusqueda.getName());
     List<Line> path = alg.getPath(nodoSlack, nodoEnBusqueda);
     System.out.println(path.toString());
     if (path.isEmpty()) {
     //System.out.println("Nodo aislado: "+ nodoEnBusqueda.getName());
     //nodosAislados.add(nodoEnBusqueda);
     //System.out.println("Arraylist con: " +nodosAislados.toString());
     matrizAislados[fila][i] = 1;
     //System.out.println(1);
     } else {
     matrizAislados[fila][i] = 0;
     //System.out.println(0);
     }
     } else {
     matrizAislados[fila][i] = 0;
     }
     }
     fila++;
     }

     for (int i = 0; i < nBus.length; i++) {
     int suma = 0;
     for (int j = 0; j < bSlack.size(); j++) {
     suma = suma + matrizAislados[j][i];
     }
     if (suma == bSlack.size()) {
     int indice = i + 1;
     //System.out.println("Nodo totalmente aislado: B"+indice);
     Vertex nAislado = ElectricalNetwork.getElectricalNetwork().obtenerBus("B" + indice);
     nAislado.getVertexAsBus().setVoltageBus(0);
     nAislado.getVertexAsBus().setAngleBus(0);
     nAislado.getVertexAsBus().setIsolatedStatus(false);
     nodosAislados.add(nAislado);
     AreaSimulacion.getAreaSimulacion().repaint();
     //System.out.println("Arraylist con: " + nodosAislados.toString()+".....");
     } else {
     int indice = i + 1;
     Vertex n = ElectricalNetwork.getElectricalNetwork().obtenerBus("B" + indice);
     n.getVertexAsBus().setIsolatedStatus(true);
     if (n.getVertexAsBus().getVoltageBus() == 0) {
     n.getVertexAsBus().setVoltageBus(1);
     }
     AreaSimulacion.getAreaSimulacion().repaint();
     }

     }
     }

     if (nodosAislados.isEmpty()) {
     //Si esta vacia, devuelvo false porque no hay nodos completa% aislados
     check = false;
     } else {
     // Por el contrario, digo con true que si existen nodos completa% aislados
     check = true;
     }
     return check;
     }
     */

 /*
     public double[] getMetodoAndreaw() {
     // Ejemplo para cambio de icono segun si existe o no TF en el Bus

     // Obtengo todos los nodos del sistema
     Collection<Vertex> vertices = getVertices();

     // Consulto si se encuenta habilidato el modo TF Location
     if (Controller.getController().isTFLocationMode()) {
     // Aleatoriamente cambio la varible que determina si existe un TF
     // en el bus o no
     for (Vertex v : vertices) {
     // Con el X% de probabilidad se realiza el cambio
     if (Math.random() >= 0.7) {
     v.getVertexAsBus().setTFPresent(true);
     } else {
     v.getVertexAsBus().setTFPresent(false);
     }
     }

     }
     // No retorno nada por el momento
     return null;
     }
     */
    public ArrayList<double[]> getBusDataSystem() {
        return NetworkData.getInstance().getGeneralBusData();
    }

    public ArrayList<double[]> getLineDataSystem() {
        return NetworkData.getInstance().getGeneralLineData();
    }

    public void setNodeActiveInSimulation(String bus) {
        nodeActiveInSimulation = bus;
    }

    public String getNodeActiveInSimulation() {
        return nodeActiveInSimulation;
    }

    public void runTFLocationAGAlgorithm() {
        int N_Source = 1; // No se de donde sale esto??????
        double Vb = 0.2193931;                    // (kV) de la red de B.T....... esto es parametro
        double Vbm = 12;                             // (kV) de la red de M.T...... a un parametro tambien
        double MVAb = 1;                             // (MVA)... parametro....
        double fe = 1;                               // Factor de energía otro parametro...
        double InT = 0.15;                        // Tasa de interes...parametro para variar ¬¬
        double InF = 0.18;                        // Tasa de descuento. una ventanita como minimo para este parametro
        double FC = 0.5;                          // Factor de carga... idem
        double fp = 0.325;                        // Factor de pérdida ¬¬ idem
        int años = 5;
        double cre_dem = 4;
        double r = 0.18;
        double FL = 0.5;
        double FOL = 1.2;
        double pp = 63.1;
        double pMT = 17733;
        double pLLV = 8000;
        double vmcc = 0.28;
        int hora = 25;
        int NIND = 20;
        int MAXGEN = 150;
        double Pcruza = 0.9;
        double Pmutacion = 0.4;

        double[][] matrizLineas = NetworkData.getInstance().getDataLineForTFLocation();
        double[][] matrizConductores = NetworkData.getInstance().getDataConductorForTFLocation();
        double[][] matrizDistancias = NetworkData.getInstance().getDataDistanceForTFLocation();
        double[][] matrizCargas = NetworkData.getInstance().getDataLoadForTFLocation();

        ArrayList<Integer> nodosMTAux = new ArrayList();

        for (Vertex v : this.getVertices()) {
            if (v.getVertexTypeName().equals("TFLocationNode")) {
                if (v.getVertexAsTFLocationNode().getMTNodeStatus() == true) {
                    nodosMTAux.add(v.getVertexAsTFLocationNode().getNumberNode());
                }
            }
        }
        double[][] matrizNodosMT;
        matrizNodosMT = new double[1][nodosMTAux.size()];// = {{1, 2, 3, 4, 5}}; // Ni idea que es eso

        for (int i = 0; i < nodosMTAux.size(); i++) {
            matrizNodosMT[0][i] = nodosMTAux.get(i);
        }

        TDs_Location TDs = new TDs_Location(N_Source, Vb, Vbm, MVAb, fe, InT, InF, FC, fp,
                matrizLineas, matrizConductores, matrizDistancias, matrizCargas, matrizNodosMT,
                años, cre_dem, r, FL, FOL, pp, pMT, pLLV, vmcc, hora, NIND,
                MAXGEN, Pcruza, Pmutacion);

        //TDs_Location TDs = new TDs_Location();
        TDs.Run_TDs_Location();                 //Ejecutar Algoritmo

        double[] openLines = TDs.getLineasAbiertas();

        for (int i = 0; i < openLines.length; i++) {
            this.getEdgeByName("L" + ((int) openLines[i])).getEdgeAsTFLocationLine().setLineBuilt(false);
        }

        double[][] aux = TDs.getTDs().getData();

        for (int i = 0; i < aux[0].length; i++) {
            int index = (int) aux[0][i] + 1;
            System.out.println("TDs: " + index);
            this.getBusByName("B" + index).getVertexAsTFLocationNode().setTransformerNode(true);
        }
    }

}
