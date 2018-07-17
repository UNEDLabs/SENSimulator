/*
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 |xxxxxxx|             TÉCNICA DE OPTIMIZACION HEURÍSTICA             |xxxxxxx|
 |xxxxxxx|                    ALGORITMO GENÉTICO                      |xxxxxxx|
 |xxxxxxx|                   (OPERADOR  ELITISMO)                     |xxxxxxx| 
 |xxxxxxx|                           2014                             |xxxxxxx|
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 |xxxxxxx|                JMendoza - AMadrid - HVargas                |xxxxxxx|
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 */

package newsensimulator.model.problem.tdslocationforss.Run_Algorithm.GeneticAlgorithm;

import newsensimulator.model.problem.tdslocationforss.Run_Algorithm.TDs_Location.Metodos;
import org.apache.commons.math3.linear.RealMatrix;

/**
 *
 * @author Andreuw Madrid Carreño  "Escuela de Ingeniería Eléctrica - PUCV"
 */
public class Elitismo {
    
   private RealMatrix cromosoma;
   private RealMatrix SelCh;
   private RealMatrix neto;
   private RealMatrix netoSelCh;
   private RealMatrix min;
   private double minCh;
   private double posCh;
   private RealMatrix mejorCh;
   private RealMatrix max;
   private double peorSel;
   private double posSel;
   
public Elitismo(RealMatrix cromosoma,RealMatrix SelCh,RealMatrix neto,RealMatrix netoSelCh){
   
    this.cromosoma=cromosoma;
    this.SelCh=SelCh;
    this.neto=neto;
    this.netoSelCh=netoSelCh;
    
}

public void RunElitismo(){
    
  min = Metodos.minComplete(neto);
    minCh=min.getEntry(0,0);
    posCh = min.getEntry(0,1);
    mejorCh=cromosoma.getRowMatrix((int)posCh);
    max = Metodos.maxComplete(netoSelCh);
    peorSel = max.getEntry(0,0);
    posSel = max.getEntry(0,1);
    cromosoma = SelCh.copy();
    neto = netoSelCh.copy();
    cromosoma.setRowMatrix((int)posSel,mejorCh);
    neto.setEntry((int)posSel,0, minCh);
    this.cromosoma=cromosoma;
    this.neto=neto;  
    
}
    
    
    //MÉTODOS GET():
    
    public RealMatrix getcromosoma(){
        return cromosoma;
    }
    
    public RealMatrix getneto(){
        return neto;
    }
}
