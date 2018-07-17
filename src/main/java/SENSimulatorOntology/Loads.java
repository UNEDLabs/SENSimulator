package SENSimulatorOntology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: Loads
* @author ontology bean generator
* @version 2014/07/18, 02:33:21
*/
public class Loads implements Concept {

   /**
* Protege name: loadCount
   */
   private int loadCount;
   public void setLoadCount(int value) { 
    this.loadCount=value;
   }
   public int getLoadCount() {
     return this.loadCount;
   }

   /**
* Protege name: loadName
   */
   private String loadName;
   public void setLoadName(String value) { 
    this.loadName=value;
   }
   public String getLoadName() {
     return this.loadName;
   }

   /**
* Protege name: totalConsumption
   */
   private float totalConsumption;
   public void setTotalConsumption(float value) { 
    this.totalConsumption=value;
   }
   public float getTotalConsumption() {
     return this.totalConsumption;
   }

}
