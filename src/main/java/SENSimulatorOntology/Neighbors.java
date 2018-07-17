package SENSimulatorOntology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: Neighbors
* @author ontology bean generator
* @version 2014/07/18, 02:33:21
*/
public class Neighbors implements Concept {

   /**
* Protege name: neighborCount
   */
   private int neighborCount;
   public void setNeighborCount(int value) { 
    this.neighborCount=value;
   }
   public int getNeighborCount() {
     return this.neighborCount;
   }

   /**
* Protege name: neighborName
   */
   private String neighborName;
   public void setNeighborName(String value) { 
    this.neighborName=value;
   }
   public String getNeighborName() {
     return this.neighborName;
   }

}
