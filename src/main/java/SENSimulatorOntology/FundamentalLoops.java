package SENSimulatorOntology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: FundamentalLoops
* @author ontology bean generator
* @version 2014/07/18, 02:33:21
*/
public class FundamentalLoops implements Concept {

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

   /**
* Protege name: LINEState
   */
   private String lineState;
   public void setLINEState(String value) { 
    this.lineState=value;
   }
   public String getLINEState() {
     return this.lineState;
   }

   /**
* Protege name: NodeState
   */
   private String nodeState;
   public void setNodeState(String value) { 
    this.nodeState=value;
   }
   public String getNodeState() {
     return this.nodeState;
   }

   /**
* Protege name: Loop
   */
   private String loop;
   public void setLoop(String value) { 
    this.loop=value;
   }
   public String getLoop() {
     return this.loop;
   }

   /**
* Protege name: LineLoop
   */
   private String lineLoop;
   public void setLineLoop(String value) { 
    this.lineLoop=value;
   }
   public String getLineLoop() {
     return this.lineLoop;
   }

   /**
* Protege name: NodeName
   */
   private String nodeName;
   public void setNodeName(String value) { 
    this.nodeName=value;
   }
   public String getNodeName() {
     return this.nodeName;
   }

   /**
* Protege name: LineStateLoop
   */
   private String lineStateLoop;
   public void setLineStateLoop(String value) { 
    this.lineStateLoop=value;
   }
   public String getLineStateLoop() {
     return this.lineStateLoop;
   }

}
