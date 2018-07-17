package newsensimulator.model.networkdata;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import newsensimulator.control.Controller;
import newsensimulator.model.ElectricalNetwork;
import newsensimulator.model.networkelements.Edge;
import newsensimulator.model.networkelements.Vertex;
import newsensimulator.model.networkelements.linetype.Conductor;

/**
 *
 * @author Jose Muñoz Parra
 */
public class NetworkData {

    // Cada fila almacena los datos de nodos o barras. La estructura es de la siguiente manera
    // [N° Nodo][Tipo][Va][Vb][Bc][<a][<b][<c][Ra][Rb][Rc][Xa][Xb][Xc][Pa][Pb][Pc][Qa][Qb][Qc][NCli]
    private ArrayList<double[]> generalBusData;

    private ArrayList<double[]> generalLineData;

    private final int busArrayLenght = 15;
    private final int lineArrayLenght = 17;

    private final int nodoNumberIndex = 0;
    private final int typeIndex = 1;
    private final int vaIndex = 2;
    private final int vbIndex = 3;
    private final int vcIndex = 4;
    private final int angleAIndex = 5;
    private final int angleBIndex = 6;
    private final int angleCIndex = 7;
    private final int paIndex = 8;
    private final int pbIndex = 9;
    private final int pcIndex = 10;
    private final int qaIndex = 11;
    private final int qbIndex = 12;
    private final int qcIndex = 13;
    private final int ncliIndex = 14;

    private final int numLineIndex = 0;
    private final int NSIndex = 1;
    private final int NTIndex = 2;
    private final int rIndex = 3;
    private final int xIndex = 4;
    private final int distIndex = 5;
    private final int rKmIndex = 6;
    private final int xKmIndex = 7;
    private final int iMaxIndex = 8;
    private final int condTypeIndex = 9;
    private final int fIndex = 10;
    private final int nIndex = 11;
    private final int rnIndex = 12;
    private final int xnIndex = 13;
    private final int fTimeIndex = 14;
    private final int rTimeIndex = 15;
    private final int hTimeIndex = 16;

    private static final NetworkData instance = new NetworkData();

    public static NetworkData getInstance() {
        return instance;
    }

    public NetworkData() {
        generalBusData = new ArrayList();
        generalLineData = new ArrayList();
    }

    private void addInfoNode(Vertex vertex) {
        double[] aux = new double[busArrayLenght];
        boolean flag = false;

        for (int i = 0; i < busArrayLenght; i++) {
            aux[i] = 0;
        }

        switch (vertex.getVertexTypeName()) {
            case "Bus":
                flag = true;
                aux[nodoNumberIndex] = vertex.getVertexAsBus().getNumberBus();
                aux[typeIndex] = vertex.getVertexAsBus().getBusCode();
                aux[vaIndex] = vertex.getVertexAsBus().getVoltageBus();
                aux[angleAIndex] = vertex.getVertexAsBus().getAngleBus();

                for (Vertex v : ElectricalNetwork.getElectricalNetwork().getNeighbors(vertex)) {
                    if (!v.getVertexTypeName().equals("Bus") && !v.getVertexTypeName().equals("TFLocationNode")) {
                        switch (v.getVertexTypeName()) {
                            case "Load":
                                aux[paIndex] = aux[paIndex] + v.getVertexAsLoad().getLoadMW();
                                aux[qaIndex] = aux[qaIndex] + v.getVertexAsLoad().getLoadMVar();
                                break;
                            case "Generator":
                                aux[paIndex] = aux[paIndex] - v.getVertexAsGenerator().getMWGenerator();
                                aux[qaIndex] = aux[qaIndex] - v.getVertexAsGenerator().getMVarGenerator();
                                break;
                            case "ElectricVehicle":
                                Controller.getController().SENSimulatorConsolePrint("para el furuto...");
                                break;
                            default:
                                Controller.getController().SENSimulatorConsolePrint("No era carga,gen o ve...");
                                break;
                        }
                    }
                }
                break;
            case "TFLocationNode":
                flag = true;
                aux[nodoNumberIndex] = vertex.getVertexAsTFLocationNode().getNumberNode();
                //aux[typeIndex] = vertex.getVertexAsTFLocationNode()...;
                aux[vaIndex] = vertex.getVertexAsTFLocationNode().getVoltagePhase_A();
                aux[vbIndex] = vertex.getVertexAsTFLocationNode().getVoltagePhase_B();
                aux[vcIndex] = vertex.getVertexAsTFLocationNode().getVoltagePhase_C();

                aux[angleAIndex] = vertex.getVertexAsTFLocationNode().getAnglePhase_A();
                aux[angleBIndex] = vertex.getVertexAsTFLocationNode().getAnglePhase_B();
                aux[angleCIndex] = vertex.getVertexAsTFLocationNode().getAnglePhase_C();

                aux[paIndex] = vertex.getVertexAsTFLocationNode().getActivePowerPhase_A();
                aux[pbIndex] = vertex.getVertexAsTFLocationNode().getActivePowerPhase_B();
                aux[pcIndex] = vertex.getVertexAsTFLocationNode().getActivePowerPhase_C();

                aux[qaIndex] = vertex.getVertexAsTFLocationNode().getReactivePowerPhase_A();
                aux[qbIndex] = vertex.getVertexAsTFLocationNode().getReactivePowerPhase_B();
                aux[qcIndex] = vertex.getVertexAsTFLocationNode().getReactivePowerPhase_C();

                aux[ncliIndex] = vertex.getVertexAsTFLocationNode().getNCli();

                break;
        }

        if (flag) {
            generalBusData.add(aux);
        }
    }

    private void addInfoEdge(Edge edge) {
        double[] aux = new double[lineArrayLenght];
        boolean flag = false;

        for (int i = 0; i < busArrayLenght; i++) {
            aux[i] = 0;
        }

        switch (edge.getEdgeTypeName()) {
            case "SimpleLine":
                flag = true;
                aux[numLineIndex] = edge.getEdgeAsSimpleLine().getLineNumber();
                aux[NSIndex] = edge.getEdgeAsSimpleLine().getOrigen().getVertexAsBus().getNumberBus();
                aux[NTIndex] = edge.getEdgeAsSimpleLine().getDestino().getVertexAsBus().getNumberBus();
                aux[rIndex] = edge.getEdgeAsSimpleLine().getResistance();
                aux[xIndex] = edge.getEdgeAsSimpleLine().getReactance();
                aux[iMaxIndex] = edge.getEdgeAsSimpleLine().getMaxCurrent();
                break;

            case "TFLocationLine":
                flag = true;
                aux[numLineIndex] = edge.getEdgeAsTFLocationLine().getNumberLine();
                aux[NSIndex] = edge.getEdgeAsTFLocationLine().getSourceVertex().getVertexAsTFLocationNode().getNumberNode();
                aux[NTIndex] = edge.getEdgeAsTFLocationLine().getTargetVertex().getVertexAsTFLocationNode().getNumberNode();
                aux[distIndex] = edge.getEdgeAsTFLocationLine().getConductor().getElectricalConductorDistanceInKilometers();
                aux[rKmIndex] = edge.getEdgeAsTFLocationLine().getConductor().getResistance();
                aux[xKmIndex] = edge.getEdgeAsTFLocationLine().getConductor().getReactance();
                aux[iMaxIndex] = edge.getEdgeAsTFLocationLine().getConductor().getMaxCurrent();
                aux[condTypeIndex] = edge.getEdgeAsTFLocationLine().getConductor().getCondutorType();
                aux[fIndex] = edge.getEdgeAsTFLocationLine().getConductor().getF();
                aux[nIndex] = edge.getEdgeAsTFLocationLine().getConductor().getN();
                aux[rnIndex] = edge.getEdgeAsTFLocationLine().getConductor().getRn();
                aux[xnIndex] = edge.getEdgeAsTFLocationLine().getConductor().getXn();
                aux[fTimeIndex] = edge.getEdgeAsTFLocationLine().getFaultTime();
                aux[rTimeIndex] = edge.getEdgeAsTFLocationLine().getRecoveryTime();
                aux[hTimeIndex] = edge.getEdgeAsTFLocationLine().getHandlingTime();
                break;
        }

        if (flag) {
            generalLineData.add(aux);
        }
    }

    public ArrayList<double[]> getGeneralBusData() {
        generalBusData.clear();

        Collection<Vertex> vertices = ElectricalNetwork.getElectricalNetwork().getVertices();

        for (Vertex v : vertices) {
            if (v.getVertexTypeName().equals("Bus") || v.getVertexTypeName().equals("TFLocationNode")) {
                addInfoNode(v);
            }
        }
        Collections.sort(generalBusData, new Comparator<double[]>() {
            public int compare(double[] d1, double[] d2) {
                return new Double(d1[0]).compareTo(new Double(d2[0]));
            }
        });

        return generalBusData;
    }

    public ArrayList<double[]> getGeneralLineData() {
        generalLineData.clear();

        Collection<Edge> edge = ElectricalNetwork.getElectricalNetwork().getEdges();

        for (Edge e : edge) {
            if (e.getEdgeTypeName().equals("SimpleLine") || e.getEdgeTypeName().equals("TFLocationLine")) {
                if (e.getEdgeTypeName().equals("SimpleLine")) {
                    if (!e.getEdgeAsSimpleLine().getName().equals("")) {
                        addInfoEdge(e);
                    }
                }
                if (e.getEdgeTypeName().equals("TFLocationLine")) {
                    addInfoEdge(e);
                }

            }
        }

        Collections.sort(generalLineData, new Comparator<double[]>() {
            public int compare(double[] d1, double[] d2) {
                return new Double(d1[0]).compareTo(new Double(d2[0]));
            }

        });

        return generalLineData;
    }

    public double[][] getDataLineForTFLocation() {
        ArrayList<double[]> data = getGeneralLineData();
        //             NL   Nombre   n1     n2    Cond     L(m)   Tfalla   Trep   Tman
        double[][] dataLine = new double[data.size()][9];

        for (int i = 0; i < data.size(); i++) {
            //NL   Nombre   n1     n2    Cond     L(m)   Tfalla   Trep   Tman
            for (int j = 0; j < dataLine[i].length; j++) {
                dataLine[i][0] = data.get(i)[numLineIndex];
                dataLine[i][1] = 0;
                dataLine[i][2] = data.get(i)[NSIndex];
                dataLine[i][3] = data.get(i)[NTIndex];
                dataLine[i][4] = data.get(i)[condTypeIndex];

                Edge e = ElectricalNetwork.getElectricalNetwork().getEdgeByName("L" + (int) dataLine[i][0]);
                Point2D p1 = Controller.getController().getVertexPoint(e.getEdgeAsTFLocationLine().getSourceVertex());
                Point2D p2 = Controller.getController().getVertexPoint(e.getEdgeAsTFLocationLine().getTargetVertex());

                double distance = Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));

                dataLine[i][5] = distance;

                System.out.println("distancia nodos :"+e.getEdgeAsTFLocationLine().getSourceVertex().getVertexAsTFLocationNode().getNumberNode()+" - "+ e.getEdgeAsTFLocationLine().getTargetVertex().getVertexAsTFLocationNode().getNumberNode()+":   "+dataLine[i][5]);
                
                dataLine[i][6] = data.get(i)[fTimeIndex];
                dataLine[i][7] = data.get(i)[rTimeIndex];
                dataLine[i][8] = data.get(i)[hTimeIndex];
            }
        }
        
        return dataLine;
    }

    public double[][] getDataConductorForTFLocation() {
        Conductor c = new Conductor(1);
        return c.getDataConductor();
    }

    public double[][] getDataDistanceForTFLocation() {
        Conductor c = new Conductor(1);
        return c.getDistancesBetweenConductors();
    }

    public double[][] getDataLoadForTFLocation() {
        ArrayList<double[]> data = getGeneralBusData();
        
        double[][] dataLoad = new double[data.size()][10];

        for (int i = 0; i < data.size(); i++) {
            //  NB  Pa(pu)   Pb(pu)   Pc(pu)   Qa(pu)   Qb(pu)   Qc(pu)  NCli  Coord X  Coord Y 
            for (int j = 0; j < dataLoad[i].length; j++) {
                dataLoad[i][0] = data.get(i)[nodoNumberIndex];
                dataLoad[i][1] = data.get(i)[paIndex];
                dataLoad[i][2] = data.get(i)[pbIndex];
                dataLoad[i][3] = data.get(i)[pcIndex];
                dataLoad[i][4] = data.get(i)[qaIndex];
                dataLoad[i][5] = data.get(i)[qbIndex];
                dataLoad[i][6] = data.get(i)[qcIndex];
                dataLoad[i][7] = data.get(i)[ncliIndex];
                
                Vertex v = ElectricalNetwork.getElectricalNetwork().getBusByName("B"+(int)dataLoad[i][0]);
                Point2D p1 = Controller.getController().getVertexPoint(v);
                
                dataLoad[i][8] = p1.getX();
                dataLoad[i][9] = p1.getY();
            }
        }
        return dataLoad;
    }
}