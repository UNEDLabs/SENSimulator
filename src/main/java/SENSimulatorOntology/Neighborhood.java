package SENSimulatorOntology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: Neighborhood
* @author ontology bean generator
* @version 2014/07/18, 02:33:21
*/
public class Neighborhood implements Predicate {

   /**
* Protege name: NEIGHBORS
   */
   private Neighbors neighborS;
   public void setNEIGHBORS(Neighbors value) { 
    this.neighborS=value;
   }
   public Neighbors getNEIGHBORS() {
     return this.neighborS;
   }

}
