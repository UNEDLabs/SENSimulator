/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.networkelements;

//import newsensimulator.view.MainInterface;
import org.apache.commons.collections15.Factory;

/**
 *
 * @author Jose Muñoz Parra
 */
public class VertexFactory implements Factory<Vertex>{

    private static final VertexFactory instance = new VertexFactory();

    private VertexFactory() {
        System.out.println("Creando instancia del VertexFactory...");
        //MainInterface.getMainInterface().EscribirConsola("esto es hermoso");
    }

    public static VertexFactory getInstance() {
        return instance;
    }

    public Vertex create(String typeVertex) {
        Vertex vertex = new Vertex(typeVertex);
        return vertex;
    }
    public Vertex create(String typeVertex,String sName) {
        Vertex vertex = new Vertex(typeVertex,sName);
        return vertex;
    }

    @Override
    public Vertex create() {
        System.out.println("Creando vertice con metodo sin argumentos");
        Vertex v = new Vertex("");
        return v;
    }
}
