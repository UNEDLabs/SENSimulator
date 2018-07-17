/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.problem.restorationservice;

import java.util.*;
import newsensimulator.model.ElectricalNetwork;
import newsensimulator.model.networkelements.*;

/**
 *
 * @author Liss
 */

/*Aislar la falla implica que deben removerse los nodos y líneas
 que fueron encontrados en esta clase. Actualmente se están removiendo
 en el cálculo del flujo, pero no olvidar que para el simulador
 deben removerse del modelo activo de la red */


public class IsolateFault {

    private Edge lineFault;

    public IsolateFault(Edge lineFault) {
        this.lineFault = lineFault;
    }

    public Vertex getInitialNode(Edge line) {
        Vertex origin = line.getEdgeAsSimpleLine().getOrigen();

        return origin;
    }

    public Vertex getFinalNode(Edge line) {
        Vertex destiny = line.getEdgeAsSimpleLine().getDestino();

        return destiny;
    }

    private List searchSwitchDown(Edge line) {
        List openlines = new ArrayList();
        List EPM = new ArrayList();

        do {
            Vertex finalNode = getFinalNode(line);
            List<Edge> edges = finalNode.getVertexAsBus().getIncidentLines();

            EPM.clear();
            for (Edge edge : edges) {
                if (!edge.getEdgeAsSimpleLine().getName().equals(line.getEdgeAsSimpleLine().getName())) {
                    openlines.add(edge);
                    EPM.add(edge.getEdgeAsSimpleLine().getSwitchCode());
                    if (edge.getEdgeAsSimpleLine().getSwitchCode() == 0) {
                        line = edge;
                    }
                }
            }
            
        } while (EPM.contains(0));

        return openlines;
    }

    private List searchSwitchUp(Edge line) {
        List openlines = new ArrayList();
        List EPM = new ArrayList();

        do {
            Vertex initialNode = getInitialNode(line);
            List<Edge> edges = initialNode.getVertexAsBus().getIncidentLines();
            EPM.clear();
            for (Edge edge : edges) {
                if (!edge.getEdgeAsSimpleLine().getName().equals(line.getEdgeAsSimpleLine().getName())){
                    openlines.add(edge);
                    EPM.add(edge.getEdgeAsSimpleLine().getSwitchCode());
                    if (edge.getEdgeAsSimpleLine().getSwitchCode() == 0) {
                        line = edge;
                    }
                }
            }
        } while (EPM.contains(0) == true);
        return openlines;
    }

    public List evaluateSwitch(Edge line) {

        List openLines = new ArrayList();

        switch (line.getEdgeAsSimpleLine().getSwitchCode()) {
            case 111:
            case 222:
            case 333:
                openLines.addAll(openLines);
                break;
            case 11:
            case 22:
            case 33:
                openLines.addAll(searchSwitchUp(line));
                break;
            case 1:
            case 2:
            case 3:
                openLines.addAll(searchSwitchDown(line));
                break;
            case 0:
                openLines.addAll(searchSwitchDown(line));
                openLines.addAll(searchSwitchUp(line));
                break;
        }
        openLines.add(line);
        List openlinesbynumber = toNumberLine(openLines);
        return openlinesbynumber;
    }
    
    public List toNumberLine(List<Edge> openedges){
        List openlines = new ArrayList();
        for (Edge openedge : openedges) {
            String name = openedge.getEdgeAsSimpleLine().getName();
            openlines.add(Integer.valueOf((String) name.substring(1)));
        }
        return openlines;
    }



}
