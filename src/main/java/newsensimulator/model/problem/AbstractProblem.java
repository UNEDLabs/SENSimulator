package newsensimulator.model.problem;

import java.util.*;
import java.util.stream.Collectors;
import newsensimulator.model.ElectricalNetwork;
import newsensimulator.model.networkelements.*;

/**
 * Clase abstracta que permite la integración de los problemas al SENSimulator.
 * Se está considerando el implementar un método que entregue las matrices
 * dataBus-dataLine de la red completa y del modelo activo, por lo que sería
 * positivo definir esto en una reunión con el Prof. Vargas y Lisa.
 *
 * @author Héctor Vargas, Rodrigo Martínez
 */
public abstract class AbstractProblem {

    /**
     * Este método entrega una lista ordenada que contiene los vértices de la
     * red.
     *
     * @return vertexList
     */
    public List<Vertex> getVertices() {
        /*Se obtiene el objeto ElectricalNetwork*/
        ElectricalNetwork currentNetwork = ElectricalNetwork.getElectricalNetwork();
        Collection<Vertex> vertices = currentNetwork.getVertices();       
        List<Vertex> vertexList = vertices.stream()
                .sorted((a, b) -> a.getVertexName().compareTo(b.getVertexName()))
                .collect(Collectors.toList());
        /*System.out.println("Vertices: " + vertexList.stream().map(v -> v.getVertexName()).collect(Collectors.toList()));*/
        return vertexList;
    }
    //Ordenados por el nombre del vértice

    /**
     * Este método entrega una lista ordenada que contiene las líneas (abiertas
     * y cerradas) de la red, excluyendo aquellas que van de una barra a una
     * carga, generador, EV u otro elemento de ese tipo.
     *
     * @return edgeList
     */
    public List<Edge> getEdges() {
        /*Se obtiene el objeto ElectricalNetwork*/
        ElectricalNetwork currentNetwork = ElectricalNetwork.getElectricalNetwork();
        Collection<Edge> edges = currentNetwork.getEdges();       
        List<Edge> edgeList = edges.stream()
                .sorted((a, b) -> a.getEdgeName().compareTo(b.getEdgeName()))
                .filter(e -> e.getEdgeName().contains("L"))
                .collect(Collectors.toList());
        /*System.out.println("Edges: " + edgeList.stream().map(v -> v.getEdgeName()).collect(Collectors.toList()));*/
        return edgeList;
    }

    /**
     * Este método entrega el objeto ElectricalNetwork, sólo debe ser utilizado
     * si se deseea acceder a los datos de la red que no estén disponibles por
     * medio de los métodos de esta clase abstracta.
     *
     * @return
     */
    public ElectricalNetwork getElectricalNetwork() {
        return ElectricalNetwork.getElectricalNetwork();
    }

    /**
     * Este método entrega un List<double[][]> que contiene las matrices dataBus
     * y dataLine correspondientes a cada sub red, donde cada sub red es
     * definida como el conjunto de barras que tienen conectividad a un bus
     * slack. De lo anterior se desprende que el list entregado cuenta con
     * tantos pares dataBus-dataLine como buses slack tenga la red.
     *
     * @return
     */
    public List<double[][]> getDataMatrices() {

        return getElectricalNetwork().getDataNetwork();
    }

    /**
     * Este método es llamado cuando se selecciona el modo de operación
     * correspondiente.
     *
     */
    public abstract void runOptimizationMethod();

    /**
     * Este método permite cambiar el estado de una línea de tipo SimpleLine.
     *
     * @param edgeByName corresponde al nombre de la línea. p.e.: "L4"
     * @param switchStatus corresponde al estado que se quiere. Si se quiere
     * cerrar la línea, switchStatus debe ser un true, por el contrario, si se
     * quiere abrir la línea switchStatus debe ser igual a false.
     * @return un true si pudo realizar el cambio de estado y un false si esto
     * no se pudo realizar, dado que ninguna línea tenía el nombre provisto como
     * argumento.
     */
    public static boolean setSimpleLineStatus(String edgeByName, boolean switchStatus) {
        
        Edge edgeToChange = getEdgeByName(edgeByName);

        if (edgeToChange != null) {
            if (edgeToChange.getEdgeAsSimpleLine().getEstiloLinea()) {
                edgeToChange.getEdgeAsSimpleLine().setSwitchStatus(00);
                edgeToChange.getEdgeAsSimpleLine().setEstiloLinea(false);
            } else {
                edgeToChange.getEdgeAsSimpleLine().setSwitchStatus(11);
                edgeToChange.getEdgeAsSimpleLine().setEstiloLinea(true);
            }
            return true;
        } else {
            System.err.println("Edge " + edgeByName + " not found");
            return false;
        }
    }

    /**
     * Este método retorna el objeto Vertex correspondiente al nombre pasado
     * como argumento.
     *
     * @param vertexName es el nombre del Vertex que se quiere obtener
     * @return el objeto de tipo Vertex correspondiente. En caso que no exista
     * ningún vértice con ese nombre, retornará un null.
     */
    public static Vertex getVertexByName(String vertexName) {
        Collection<Vertex> vertices = ElectricalNetwork.getElectricalNetwork().getVertices();
        for (Vertex v : vertices) {
            if (v.getVertexName().equals(vertexName)) {
                return v;
            }
        }
        return null;
    }

    /**
     * Este método retorna el objeto Edge correspondiente al nombre pasado como
     * argumento.
     *
     * @param edgeName es el nombre del Edge que se quiere obtener
     * @return el objeto de tipo Edge correspondiente. En caso que no exista
     * ninguna línea con ese nombre, retornará un null.
     */
    public static Edge getEdgeByName(String edgeName) {
        Collection<Edge> edges = ElectricalNetwork.getElectricalNetwork().getEdges();
        for (Edge e : edges) {
            if (e.getEdgeName().equals(edgeName)) {
                return e;
            }
        }
        return null;
    }

    
    
}
