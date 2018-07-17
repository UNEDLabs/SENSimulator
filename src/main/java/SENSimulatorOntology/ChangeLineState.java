package SENSimulatorOntology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: ChangeLineState
* @author ontology bean generator
* @version 2014/07/18, 02:33:21
*/
public class ChangeLineState implements AgentAction {

   /**
* Protege name: LineState
   */
   private boolean lineState;
   public void setLineState(boolean value) { 
    this.lineState=value;
   }
   public boolean getLineState() {
     return this.lineState;
   }

   /**
* Protege name: LINEName
   */
   private String lineName;
   public void setLINEName(String value) { 
    this.lineName=value;
   }
   public String getLINEName() {
     return this.lineName;
   }

}
