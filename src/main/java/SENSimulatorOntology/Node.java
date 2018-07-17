package SENSimulatorOntology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: Node
* @author ontology bean generator
* @version 2014/07/18, 02:33:21
*/
public class Node implements Concept {

   /**
* Protege name: angle
   */
   private float angle;
   public void setAngle(float value) { 
    this.angle=value;
   }
   public float getAngle() {
     return this.angle;
   }

   /**
* Protege name: inFault
   */
   private boolean inFault;
   public void setInFault(boolean value) { 
    this.inFault=value;
   }
   public boolean getInFault() {
     return this.inFault;
   }

   /**
* Protege name: busName
   */
   private String busName;
   public void setBusName(String value) { 
    this.busName=value;
   }
   public String getBusName() {
     return this.busName;
   }

   /**
* Protege name: voltage
   */
   private float voltage;
   public void setVoltage(float value) { 
    this.voltage=value;
   }
   public float getVoltage() {
     return this.voltage;
   }

}
