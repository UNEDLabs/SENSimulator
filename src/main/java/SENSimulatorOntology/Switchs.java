package SENSimulatorOntology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: Switchs
* @author ontology bean generator
* @version 2014/07/18, 02:33:21
*/
public class Switchs implements Concept {

   /**
* Protege name: switchCount
   */
   private int switchCount;
   public void setSwitchCount(int value) { 
    this.switchCount=value;
   }
   public int getSwitchCount() {
     return this.switchCount;
   }

   /**
* Protege name: switchState
   */
   private String switchState;
   public void setSwitchState(String value) { 
    this.switchState=value;
   }
   public String getSwitchState() {
     return this.switchState;
   }

   /**
* Protege name: switchName
   */
   private String switchName;
   public void setSwitchName(String value) { 
    this.switchName=value;
   }
   public String getSwitchName() {
     return this.switchName;
   }

}
