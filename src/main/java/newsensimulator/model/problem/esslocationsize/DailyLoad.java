/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.problem.esslocationsize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import newsensimulator.model.ElectricalNetwork;
import newsensimulator.model.networkelements.Edge;
import newsensimulator.model.networkelements.Vertex;
import newsensimulator.model.problem.powerflow.PowerFlow;

//import newsensimulator.model.ElectricalNetwork;
//import newsensimulator.model.networkelements.Edge;
//import newsensimulator.model.networkelements.Vertex;
//import newsensimulator.model.problem.powerflow.PowerFlow;
/**
 *
 * @author Liss
 */
public class DailyLoad {

    private static DailyLoad instance;
    private List<double[]> dailyLoad;

    private DailyLoad() {
        //Residencial
        double[] percent = {0.30, 0.26, 0.24, 0.22, 0.20, 0.30, 0.38, 0.50, 0.55, 0.55, 0.58, 0.60, 0.55, 0.50, 0.48, 0.50, 0.70, 1.00, 0.95, 0.90, 0.82, 0.75, 0.60, 0.40};
        //Comercial
        double[] percent2 = {0.20, 0.20, 0.19, 0.18, 0.20, 0.22, 0.25, 0.4, 0.7, 0.85, 0.91, 0.93, 0.89, 0.93, 0.94, 0.95, 1.00, 0.90, 0.75, 0.70, 0.65, 0.55, 0.30, 0.20};
        //Industrial
        double[] percent3 = {0.55, 0.53, 0.51, 0.50, 0.55, 0.59, 0.70, 0.80, 0.92, 1.00, 0.98, 0.95, 0.94, 0.98, 0.90, 0.85, 0.80, 0.73, 0.73, 0.71, 0.70, 0.70, 0.65, 0.60};
        dailyLoad = new ArrayList();
        dailyLoad.add(percent);
        dailyLoad.add(percent2);
        dailyLoad.add(percent3);

    }

    public static DailyLoad getInstance() {
        if (instance == null) {
            instance = new DailyLoad();
        }
        return instance;
    }

    public void setDailyLoad(List<double[]> dailyLoad) {
        this.dailyLoad = dailyLoad;
    }

    public List<double[]> getDailyLoad() {
        return dailyLoad;
    }

    //@parm lista con todos los elementos de la red
    //@return lista con pérdidas totales por hora (podría retornar magnitudes de tensión tambien)
    // corre flujo de potencia en cada hora
    public List dailyPowerFlow(List dataNetwork) {

        List totalLosses = new ArrayList(); //Lista con pérdidas totales por hora
        List magVoltBus = new ArrayList(); //Lista con listas de magnitudes de tensión pu
        List totalLoadbility = new ArrayList();
        List powerFlowResult = new ArrayList();

        List dataBuses = (List) dataNetwork.get(0);
        int[] openlines = (int[]) dataNetwork.get(3);
        totalLosses.clear();
        double[][] dataLine = (double[][]) dataNetwork.get(1);
        for (Object databus : dataBuses) {
            PowerFlow flow = new PowerFlow(dataLine, (double[][]) databus, openlines);
            flow.runLoadFlow();
            totalLosses.add(flow.getMWTotalLoss());
            List<Double> magBus = flow.getMagVoltBusAsList();
            double magTotal = magBus.stream().mapToDouble(o -> o.doubleValue()).sum();
            magVoltBus.add(magTotal);
            List magCu = flow.getMagCurrentAsList();

            List loadbi = new ArrayList();
            for (int i = 0; i < magCu.size(); i++) {
                loadbi.add((double) magCu.get(i) / (double) dataLine[i][7]);
            }
            totalLoadbility.add(loadbi);
        }
        powerFlowResult.add(totalLosses); //0 todas las pérdidas
        powerFlowResult.add(magVoltBus); //1 todas las tensiones sumadas por hora
        powerFlowResult.add(totalLoadbility); //2 lista con listas con la cargabilidad en cada linea
//        List dataLoad = (List) dataNetwork.get(2);
//        return dataLoad;
        return powerFlowResult;
//        return totalLosses;
    }

    public List powerFlowPerHour() {

        List totalLosses = new ArrayList(); //Lista con pérdidas totales por hora
        List magVoltBus = new ArrayList(); //Lista con listas de magnitudes de tensión pu
        List dataNetwork = getData();
//        Networks net = new Networks();
//        List dataNetwork = net.perHour();

        totalLosses.clear();
        List dataBuses = (List) dataNetwork.get(0);
        int[] openlines = (int[]) dataNetwork.get(3);

        for (Object databus : dataBuses) {
            PowerFlow flow = new PowerFlow((double[][]) dataNetwork.get(1), (double[][]) databus, openlines);
            flow.runLoadFlow();
            totalLosses.add(flow.getMWTotalLoss());
            List<Double> magBus = flow.getMagVoltBusAsList();
            double magTotal = magBus.stream().mapToDouble(o -> o.doubleValue()).sum();
            magVoltBus.add(magTotal);
        }


        List dataLoad = (List) dataNetwork.get(2);
        List allData = new ArrayList();
        allData.add(dataNetwork);
        allData.add(totalLosses);
        allData.add(magVoltBus);
        //retorna lista con datanetwork, totallosses y total magBus
        return allData;
//        return dataLoad;
    }

    public List powerFlowWithBattery(int[] nodes, double power) {
//        Networks net = new Networks();
//        List data = net.perHour();

        List data = getData();
        List losses = new ArrayList();
        List voltage = new ArrayList();
        List loadability = new ArrayList();
        List results = new ArrayList();
        int cantBess = nodes.length;

        List databuses = (List) data.get(0);
//        double[] max = {0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2,0.2,0.2,0.2,0.2,0.2};
        for (Object databuse : databuses) {
            PowerFlow flow = new PowerFlow((double[][]) data.get(1), (double[][]) databuse, (int[]) data.get(3));
            flow.runLoadFlow();
            voltage.add(flow.getMagVoltBusAsList());
            losses.add(flow.getMWTotalLoss());
//            loadability.add(Arrays.asList(flow.getLoadability(max)));
        }

        results.add(losses);
        results.add(data.get(2));

//        System.out.println(losses);
//        System.out.println(data.get(2));
//        for (int i = 0; i < voltage.size(); i++) {
//            System.out.println(voltage.get(i));
//        }
//        for (int i = 0; i < loadability.size(); i++) {
//            System.out.println(loadability.get(i));
//        }
        double scale = (Math.pow(10, 4));
        power = power / cantBess;
        power = (double) (Math.round(power * scale) / scale);

        double chargePower = (power * 4 / 7) / 0.7;
        List load = new ArrayList();
        load.addAll((List) data.get(2));

        double l = 0;
        for (int i = 0; i < 7; i++) {
            double[][] b = (double[][]) databuses.get(i);
            for (int j = 0; j < nodes.length; j++) {
                b[nodes[j] - 1][4] = b[nodes[j] - 1][4] + chargePower;
            }
            l = (double) load.get(i) + chargePower * cantBess;
            load.set(i, l);
        }
        //descarga de bess
        l = 0;
        for (int i = 17; i < 20; i++) {
            double[][] b = (double[][]) databuses.get(i);
            for (int j = 0; j < nodes.length; j++) {
                b[nodes[j] - 1][6] = b[nodes[j] - 1][6] + power;
            }
            l = (double) load.get(i) - power * cantBess;
            load.set(i, l);
        }

        List lossesBess = new ArrayList();
        for (Object databuse : databuses) {
            PowerFlow flow = new PowerFlow((double[][]) data.get(1), (double[][]) databuse, (int[]) data.get(3));
            flow.runLoadFlow();
            voltage.add(flow.getMagVoltBusAsList());
            lossesBess.add(flow.getMWTotalLoss());
//            loadability.add(Arrays.asList(flow.getLoadability(max)));

        }
        results.add(lossesBess);
        results.add(load);

//        System.out.println(losses);
//        System.out.println(load);
//        for (int i = 0; i < voltage.size(); i++) {
//            System.out.println(voltage.get(i));
//        }
//        for (int i = 0; i < loadability.size(); i++) {
//            System.out.println(loadability.get(i));
//        }
        //perdidas originales, demanda original, perdidas bess, demanda con bess
        return results;
    }

//Retornará lista con 4 cosas: (0)Una lista con cada DataBus (arreglo) por Hora, 
//(1) un arreglo con el DataLine, (2)una lista con la demanda total por hora, (3) y un array con las lineas abiertas    
    private List getData() {
        List data = new ArrayList();
        List dataBuses = new ArrayList();
        List totalLoad = new ArrayList();

        List<double[]> daily = getDailyLoad();
//        System.out.println(daily.get(0)[0]);
        ElectricalNetwork network = ElectricalNetwork.getElectricalNetwork();
        Collection<Vertex> vertices = network.getVertices();
        List<Vertex> vertexList = new ArrayList();
        vertexList.addAll(vertices);
        Iterator<Vertex> it = vertexList.iterator();
        while (it.hasNext()) {
            Vertex v = it.next();
            if (v.getVertexTypeName() != "Bus") {
                it.remove();
            }
            if (v.getVertexType() == 2 && daily.size() != 0) {

                switch (v.getVertexAsLoad().getLoadType()) {

                    case 1:
                        double[] residential = daily.get(0);
                        v.getVertexAsLoad().setPercentOfPeakLoad(residential);

                        break;
                    case 2:
                        double[] commercial = daily.get(1);
                        v.getVertexAsLoad().setPercentOfPeakLoad(commercial);

                        break;
                    case 3:
                        double[] industrial = daily.get(2);
                        v.getVertexAsLoad().setPercentOfPeakLoad(industrial);

                        break;
                }
            }
        }

        Collections.sort(vertexList, (Object o1, Object o2) -> {
            Vertex v1 = (Vertex) o1;
            Vertex v2 = (Vertex) o2;
            int a = Integer.parseInt(v1.getVertexName().substring(1));
            int b = Integer.parseInt(v2.getVertexName().substring(1));
            return a - b;
        });

        for (int j = 0; j < 24; j++) {
            int i = 0;
            double[][] dataBus = new double[vertexList.size()][11];
            double totalLoadMW = 0;

            for (Vertex v : vertexList) {
                dataBus[i][0] = Integer.valueOf(v.getVertexAsBus().getName().substring(1));
                dataBus[i][1] = (double) v.getVertexAsBus().getBusCode();
                dataBus[i][2] = 1;
                dataBus[i][3] = v.getVertexAsBus().getAngleBus();
                double loadMW = 0;
                double loadMVar = 0;
                double genMW = 0;
                double genMVar = 0;
                double minGenMVar = 0;
                double maxGenMVar = 0;
                double shuntCap = 0;
                for (Vertex vrtx : network.getNeighbors(v)) {
                    switch (vrtx.getVertexType()) {
                        // Vertex de carga
                        case 2:
                            loadMW = loadMW + vrtx.getVertexAsLoad().getLoadMW() * vrtx.getVertexAsLoad().getPercentOfPeakLoad()[j];
                            loadMVar = loadMVar + vrtx.getVertexAsLoad().getLoadMVar();
                            totalLoadMW = totalLoadMW + loadMW;

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
                    }
                }
                dataBus[i][4] = loadMW;
                dataBus[i][5] = loadMVar;
                dataBus[i][6] = genMW;
                dataBus[i][7] = genMVar;
                dataBus[i][8] = minGenMVar;
                dataBus[i][9] = maxGenMVar;
                dataBus[i][10] = shuntCap;
                i++;
            }
            
            totalLoad.add(totalLoadMW);
            dataBuses.add(dataBus);
        }

        data.add(dataBuses);

        Collection<Edge> edges = network.getEdges();
        List<Edge> edgeList = new ArrayList();
        edgeList.addAll(edges);
        Iterator<Edge> it2 = edgeList.iterator();
        while (it2.hasNext()) {
            Edge ed = it2.next();
            if (ed.getEdgeAsSimpleLine().getOrigen().getVertexTypeName() != "Bus" || ed.getEdgeAsSimpleLine().getDestino().getVertexTypeName() != "Bus") {
                it2.remove();
            }
        }
        Collections.sort(edgeList, (Object o1, Object o2) -> {
            Edge e1 = (Edge) o1;
            Edge e2 = (Edge) o2;
            int a = Integer.parseInt(e1.getEdgeAsSimpleLine().getName().substring(1));
            int b = Integer.parseInt(e2.getEdgeAsSimpleLine().getName().substring(1));
            return a - b;
        });

        List<Integer> initialCondition = new ArrayList();
        for (Edge edgeList1 : edgeList) {
            String name = edgeList1.getEdgeAsSimpleLine().getName();
            int linenumber = Integer.valueOf((String) name.substring(1));
            if (edgeList1.getEdgeAsSimpleLine().getEstiloLinea() == false && edgeList1.getEdgeAsSimpleLine().getInFault() == false) {
                initialCondition.add(linenumber);
//                System.out.println(Integer.valueOf((String) name.substring(1)));
            }
        }
        int[] openlines = initialCondition.stream().mapToInt(i -> i).toArray();

        double[][] dataLine = new double[edgeList.size()][8];
        int i = 0;
        for (Edge line : edgeList) {
            dataLine[i][0] = (double) line.getEdgeAsSimpleLine().getOrigen().getVertexAsBus().getNumberBus();
            dataLine[i][1] = (double) line.getEdgeAsSimpleLine().getDestino().getVertexAsBus().getNumberBus();
            dataLine[i][2] = line.getEdgeAsSimpleLine().getResistance();
            dataLine[i][3] = line.getEdgeAsSimpleLine().getReactance();
            dataLine[i][4] = 0.0;
            dataLine[i][5] = 1.0;
            dataLine[i][6] = 0.0; //agregado
            dataLine[i][7] = 0.2; //agregado

            i++;
        }

        data.add(dataLine);
        data.add(totalLoad);
        data.add(openlines);
        //Retornará lista con 4 cosas: Una lista con cada DataBus (arreglo) por Hora, un arreglo con el DataLine, una lista con la demanda total por hora, y un array con las lineas abiertas
        return data;
    }
}
