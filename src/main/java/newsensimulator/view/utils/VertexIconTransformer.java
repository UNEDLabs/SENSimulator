/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.view.utils;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import newsensimulator.control.Controller;
import newsensimulator.model.ElectricalNetwork;
import newsensimulator.model.networkelements.Edge;
import newsensimulator.model.networkelements.Vertex;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Jose Muñoz Parra
 */
public class VertexIconTransformer implements Transformer<Vertex, Icon> {

    private static final ImageIcon busIn = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/posteMadera2.png"));
    private static final ImageIcon busOut = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/posteMadera2Gris.png"));
    private static final ImageIcon busSB = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/posteMaderaSlack2.png"));
    private static final ImageIcon faultIn = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/fault.png"));
    private static final ImageIcon faultOut = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/noFault.png"));

    private static final ImageIcon loadIn = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/load.png"));
    private static final ImageIcon loadOut = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/loadOut.png"));
    private static final ImageIcon residentialLoadIn = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/loadHouse.png"));
    private static final ImageIcon residentialLoadOut = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/loadHouseOut.png"));
    private static final ImageIcon commercialLoadIn = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/shopping.png"));
    private static final ImageIcon commercialLoadOut = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/shoppingOut.png"));
    private static final ImageIcon industrialLoadIn = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/Industry.png"));
    private static final ImageIcon industrialLoadOut = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/IndustryOut.png"));

    private static final ImageIcon generatorIn = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/windpower-icon.png"));
    private static final ImageIcon generatorIn2 = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/solarpanel-icon.png"));
    private static final ImageIcon generatorOut = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/batteryOut.png"));
    private static final ImageIcon vehicleIn = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/car.png"));
    private static final ImageIcon vehicleOut = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/carOut.png"));
    private static final ImageIcon error = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/attention.png"));
    private final ImageIcon batteryIn = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/battery.png"));
    private final ImageIcon batteryOut = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/batteryOut.png"));
    private static ImageIcon tfIcon = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/electric.png"));

    private static ImageIcon MTNode = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/MTNode.png"));
    private static ImageIcon MTNodeWithTF = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/MTNodeWithTF.png"));

    private static ImageIcon icon;

    private ArrayList slackBus;

    public Icon transform(Vertex vertex) {
        //System.out.println(":(");
        switch (vertex.getVertexTypeName()) {
            case "Bus":
                //if (Controller.getController().getOperationMode() == 1) {
                slackBus = new ArrayList();
                slackBus = ElectricalNetwork.getElectricalNetwork().getSlackBusNodes();
                int count = 0;

                DijkstraShortestPath<Vertex, Edge> alg = new DijkstraShortestPath(ElectricalNetwork.getElectricalNetwork().getActiveModel());

                for (Object n : slackBus) {
                    Vertex bus = (Vertex) n;

                    List<Edge> path = alg.getPath(vertex, bus);

                    if (path.isEmpty()) {
                        count++;
                    }
                }

                if (count == slackBus.size() && vertex.getVertexAsBus().getBusCode() != 1) {
                    icon = busOut;
                    if (vertex.getVertexAsBus().getVoltageBus() != 0.0) {
                        //icon = busOut;
                        vertex.getVertexAsBus().setVoltageBus(0.0);
                        vertex.getVertexAsBus().setAngleBus(0);
                        vertex.getVertexAsBus().setIsolatedStatus(false);
                    }
                } else if (vertex.getVertexAsBus().getBusCode() == 1) {
                    icon = busSB;
                } else {
                    icon = busIn;
                    if (vertex.getVertexAsBus().getVoltageBus() == 0 && vertex.getVertexAsBus().getAngleBus() == 0 && vertex.getVertexAsBus().getIsolatedStatus() == false) {
                        vertex.getVertexAsBus().setVoltageBus(1.0);
                        vertex.getVertexAsBus().setAngleBus(0);
                        vertex.getVertexAsBus().setIsolatedStatus(true);
                    }

                }
                //} 
                /*
                 else if (Controller.getController().getOperationMode() == 2) {
                 if (vertex.getVertexAsBus().isTFPresent()) {
                 icon = tfIcon;
                 } else {
                 icon = busIn;
                 }
                 }
                 */
                break;
            case "Fault":
                icon = faultIn;
                break;
            case "Load":
                Collection<Vertex> vs = ElectricalNetwork.getElectricalNetwork().getNeighbors(vertex);
                int k = 0;
                for (Object att : vs) {
                    Vertex vertexAtt = (Vertex) att;
                    if (vertexAtt.getVertexType() == 0) {
                        if (!vertexAtt.getVertexAsBus().getIsolatedStatus()) {
                            k = 1;
                        }
                    }
                }

                Collection<Edge> edge = ElectricalNetwork.getElectricalNetwork().getInEdges(vertex);
                if (edge.isEmpty()) {
                    k = 1;
                }

                switch (vertex.getVertexAsLoad().getLoadType()) {
                    case 0:
                        if (vertex.getVertexAsLoad().getLoadState() && k == 0) {
                            icon = loadIn;
                        } else {
                            icon = loadOut;
                        }
                        break;
                    case 1:
                        if (vertex.getVertexAsLoad().getLoadState() && k == 0) {
                            icon = residentialLoadIn;
                        } else {
                            icon = residentialLoadOut;
                        }
                        break;
                    case 2:
                        if (vertex.getVertexAsLoad().getLoadState() && k == 0) {
                            icon = commercialLoadIn;
                        } else {
                            icon = commercialLoadOut;
                        }
                        break;
                    case 3:
                        if (vertex.getVertexAsLoad().getLoadState() && k == 0) {
                            icon = industrialLoadIn;
                        } else {
                            icon = industrialLoadOut;
                        }
                        break;
                }
                break;
            case "Generator":
                //Modificacion de Lisa
                if (vertex.getVertexAsGenerator().getGeneratorType() == 0 && vertex.getVertexAsGenerator().getGeneratorState()) {
                    icon = generatorIn2;
                }
                if (vertex.getVertexAsGenerator().getGeneratorType() == 1 && vertex.getVertexAsGenerator().getGeneratorState()) {
                    icon = generatorIn;
                }

//                if (vertex.getVertexAsGenerator().getGeneratorState()) {
//                    icon = generatorIn;
//                } else {
//                    icon = generatorOut;
//                }
                break;

            case "Battery": //Modificacion de Lisa
                
                  Collection<Vertex> vk = ElectricalNetwork.getElectricalNetwork().getNeighbors(vertex);
                int j = 0;
                for (Object att : vk) {
                    Vertex vertexAtt = (Vertex) att;
                    if (vertexAtt.getVertexType() == 0) {
                        if (!vertexAtt.getVertexAsBus().getIsolatedStatus()) {
                            j = 1;
                        }
                    }
                }

                Collection<Edge> ed = ElectricalNetwork.getElectricalNetwork().getInEdges(vertex);
                if (ed.isEmpty()) {
                    j = 1;
                }
                
                
                 if (vertex.getVertexAsBattery().getBatteryState() && j == 0) {
                    icon = batteryIn;
                } else {
                    icon = batteryOut;
                }

                break;

            case "ElectricVehicle":
                Collection<Vertex> v = ElectricalNetwork.getElectricalNetwork().getNeighbors(vertex);
                int n = 0;
                for (Object att : v) {
                    Vertex vertexAtt = (Vertex) att;
                    if (vertexAtt.getVertexType() == 0) {
                        if (!vertexAtt.getVertexAsBus().getIsolatedStatus()) {
                            n = 1;
                        }
                    }
                }

                Collection<Edge> g = ElectricalNetwork.getElectricalNetwork().getInEdges(vertex);
                if (g.isEmpty()) {
                    n = 1;
                }
                if (vertex.getVertexAsElectricVehicle().getElectricVehicleState() && n == 0) {
                    icon = vehicleIn;
                } else {
                    icon = vehicleOut;
                }
                break;

            case "TFLocationNode":
                if (!vertex.getVertexAsTFLocationNode().isTransformerNode() && !vertex.getVertexAsTFLocationNode().getMTNodeStatus()) {
                    icon = busIn;
                } else if (!vertex.getVertexAsTFLocationNode().isTransformerNode() && vertex.getVertexAsTFLocationNode().getMTNodeStatus()) {
                    icon = MTNode;
                } else if (vertex.getVertexAsTFLocationNode().isTransformerNode() && vertex.getVertexAsTFLocationNode().getMTNodeStatus()) {
                    icon = MTNodeWithTF;
                } else if (vertex.getVertexAsTFLocationNode().isTransformerNode() && !vertex.getVertexAsTFLocationNode().getMTNodeStatus()) {
                    icon = busSB;
                } else {
                    icon = error;
                }
                break;

            default:
                icon = error;
                break;
        }

        return icon;
    }
}
