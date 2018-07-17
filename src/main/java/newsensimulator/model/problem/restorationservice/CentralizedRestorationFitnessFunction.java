/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.problem.restorationservice;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.Pair;
import java.util.*;
import newsensimulator.model.ElectricalNetwork;
import newsensimulator.model.networkelements.Edge;
import newsensimulator.model.problem.powerflow.PowerFlow;
import org.jgap.*;

/**
 *
 * @author Liss
 */
public class CentralizedRestorationFitnessFunction extends FitnessFunction {

    private final List faults;
    public List dataNetwork;
    public List losses;

    public CentralizedRestorationFitnessFunction(List _faults, List _data) {
        this.faults = _faults;
        this.dataNetwork = _data;
        this.losses = new ArrayList();
    }

    public List getLosses() {
        return this.losses;
    }

    public void setLosses(double loss) {
        this.losses.add(loss);
    }

    @Override
    public double evaluate(IChromosome line_subject) {

        double fitness = 0;
        Collection openlines = new HashSet();

        for (int i = 0; i < line_subject.size(); i++) {
            openlines.add(line_subject.getGene(i).getAllele());
        }

        if (openlines.size() != line_subject.size()) {
            return fitness; //hay elementos repetidos, se retorna menor valor de fitness            
        }
        List<Integer> openlineslist = new ArrayList(openlines);
//        System.out.println(openlineslist);

        boolean conect = conectivity(openlineslist);
        if (conect == false) {
            return fitness;
        }

        if (this.faults.isEmpty() == false) {
            openlineslist.addAll(this.faults);
        }

        int[] openlinesarr = new int[openlineslist.size()];
        openlinesarr = openlineslist.stream().mapToInt((Integer i) -> i.intValue()).toArray();

//      if (radiality == true) 
//      Collection switchingoperation = switchingOperations(openlineslist, (int[]) data.get(2));
        PowerFlow flow = new PowerFlow((double[][]) this.dataNetwork.get(1), (double[][]) this.dataNetwork.get(0), openlinesarr);
        flow.runLoadFlow();
        List ceros = new ArrayList();
        ceros.add(0.0);
        List<Double> volts = new ArrayList(flow.getMagVoltBusAsList());
        volts.removeAll(ceros);
        double min = Collections.min(volts);
//        if (min > 0.94 && Collections.max(volts) < 1.06) {

//            boolean radiality = radialityConstraints((double[][]) this.dataNetwork.get(1), (double[][]) this.dataNetwork.get(0), openlineslist.size(), Collections.frequency(flow.getMagVoltBusAsList(), 0.0));
//            if (radiality == false) {
//                return fitness;
//            } else {
                double loss = flow.getMWTotalLoss();
//        setLosses(loss);
                fitness += 1 / (1 + Math.abs(loss));
                return fitness;
//            }
//        } else return fitness;
    }

    //Metodo que compara las líneas abiertas inicialmente con las del chromosoma
    //retorna la cantidad de conmutaciones asociadas a la posible solución
    public Collection switchingOperations(List openlines, int[] initialcondition) {

        List initialopen = new ArrayList();
        for (int i = 0; i < initialcondition.length; i++) {
            initialopen.add(initialcondition[i]);
        }
        Collection intersection = new HashSet(initialopen);
        intersection.retainAll(openlines);
        int intersect = intersection.size();
//        return ((openlines.size() - intersection.size()) * 2)-1;
        return intersection;
    }

    //método que evalúa si las líneas solucion dejan nodos aislados
    //retorna true si existe conectividad (no se aislan nodos)
    //retorna false si no existe conectividad
    public boolean conectivity(List chromosomes) {
        SparseMultigraph graphtest = testgraph();
        List<Integer> vertices = new ArrayList();
        chromosomes.stream().forEach((ch) -> {
            vertices.add((int) graphtest.getEndpoints((int) ch).getFirst());
            vertices.add((int) graphtest.getEndpoints((int) ch).getSecond());
            graphtest.removeEdge((int) ch);
        });
        DijkstraShortestPath<Integer, Integer> alg = new DijkstraShortestPath(graphtest);
        for (Integer vertice : vertices) {
            if (alg.getDistance(1, vertice) == null) {
                return false;
            }
        }
        return true;

    }

    public SparseMultigraph testgraph() {
        ElectricalNetwork currentNetwork = ElectricalNetwork.getElectricalNetwork();
        Collection<Edge> edges = currentNetwork.getEdges();
        List<Edge> edgeList = new ArrayList();
        edgeList.addAll(edges);
        Iterator<Edge> it2 = edgeList.iterator();
        while (it2.hasNext()) {
            Edge ed = it2.next();
            if (ed.getEdgeAsSimpleLine().getOrigen().getVertexTypeName() != "Bus" || ed.getEdgeAsSimpleLine().getDestino().getVertexTypeName() != "Bus") {
                it2.remove();
            }
        }
//        Collections.sort(edgeList, (Object o1, Object o2) -> {
//            Edge e1 = (Edge) o1;
//            Edge e2 = (Edge) o2;
//            int a = Integer.parseInt(e1.getEdgeAsSimpleLine().getName().substring(1));
//            int b = Integer.parseInt(e2.getEdgeAsSimpleLine().getName().substring(1));
//            return a - b;
//        });          

        SparseMultigraph<Integer, Integer> graph = new SparseMultigraph();
        for (Edge edge : edgeList) {
            String name = edge.getEdgeAsSimpleLine().getName();
            int or = edge.getEdgeAsSimpleLine().getOrigen().getVertexAsBus().getNumberBus();
            int dest = edge.getEdgeAsSimpleLine().getDestino().getVertexAsBus().getNumberBus();
            graph.addEdge(Integer.valueOf((String) name.substring(1)), new Pair(or, dest));
        }
        return graph;
    }

    //condición de radialidad
    public boolean radialityConstraints(double[][] dataLine, double[][] dataBus, int numOpenLines, int busOut) {
        int radiality = (dataLine.length - numOpenLines) - (dataBus.length - busOut) + 1;
        if (radiality == 0) {
            return true;
        } else {
            return false;
        }
    }

}
