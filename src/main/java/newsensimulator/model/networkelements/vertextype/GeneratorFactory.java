/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package newsensimulator.model.networkelements.vertextype;

import newsensimulator.model.utils.ElementCounter;
import org.apache.commons.collections15.Factory;

/**
 *
 * @author Jose Muñoz Parra
 */
public class GeneratorFactory implements Factory<Generator> {

    private int genCount = 0;
    private static GeneratorFactory instance = new GeneratorFactory();

    private GeneratorFactory() {
        System.out.println("Creando instancia del GeneratorFactory...");
    }

    public static GeneratorFactory getInstance() {
        return instance;
    }

    public Generator create() {
        int numeroGenerador;

        numeroGenerador = ElementCounter.getElementCounter().getGeneratorNumberAvailable();

        String name = "G" + numeroGenerador;
        Generator g = new Generator(name);
        g.setGeneratorNumber(numeroGenerador);
        g.setGeneratorState(true);
        g.setGeneratorType(0);
        g.setMVarGenerator(0);
        g.setMWGenerator(0);
        g.setMinMVarGenerator(0);
        g.setMaxMVarGenerator(0);

        return g;
    }
    
    
    public Generator create(String name) {
        int numeroGenerador;

        numeroGenerador =Integer.parseInt(name.substring(1));
        ElementCounter.getElementCounter().setGeneratorNumberAvailable(numeroGenerador);
        //ElementCounter.getElementCounter().getGeneratorNumberAvailable();

        //String name = "G" + numeroGenerador;
        Generator g = new Generator(name);
        g.setGeneratorNumber(numeroGenerador);
        g.setGeneratorState(true);
        g.setGeneratorType(0);
        g.setMVarGenerator(0);
        g.setMWGenerator(0);
        g.setMinMVarGenerator(0);
        g.setMaxMVarGenerator(0);

        return g;
    }
}
