package SENSimulatorOntology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: Lines
* @author ontology bean generator
* @version 2014/07/18, 02:33:21
*/
public class Lines implements Concept {

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
* Protege name: LineName
   */
   private String lineName;
   public void setLineName(String value) { 
    this.lineName=value;
   }
   public String getLineName() {
     return this.lineName;
   }

   /**
* Protege name: isMyLine
   */
   private boolean isMyLine;
   public void setIsMyLine(boolean value) { 
    this.isMyLine=value;
   }
   public boolean getIsMyLine() {
     return this.isMyLine;
   }

}
