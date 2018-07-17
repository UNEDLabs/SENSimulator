package SENSimulatorOntology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: Generators
* @author ontology bean generator
* @version 2014/07/18, 02:33:21
*/
public class Generators implements Concept {

   /**
* Protege name: totalGeneration
   */
   private float totalGeneration;
   public void setTotalGeneration(float value) { 
    this.totalGeneration=value;
   }
   public float getTotalGeneration() {
     return this.totalGeneration;
   }

   /**
* Protege name: generatorCount
   */
   private int generatorCount;
   public void setGeneratorCount(int value) { 
    this.generatorCount=value;
   }
   public int getGeneratorCount() {
     return this.generatorCount;
   }

   /**
* Protege name: generatorName
   */
   private String generatorName;
   public void setGeneratorName(String value) { 
    this.generatorName=value;
   }
   public String getGeneratorName() {
     return this.generatorName;
   }

}
