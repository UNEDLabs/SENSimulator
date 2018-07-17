package SENSimulatorOntology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: Bus
* @author ontology bean generator
* @version 2014/07/18, 02:33:21
*/
public class Bus implements Concept {

   /**
* Protege name: electricalVehicles
   */
   private ElectricalVehicles electricalVehicles;
   public void setElectricalVehicles(ElectricalVehicles value) { 
    this.electricalVehicles=value;
   }
   public ElectricalVehicles getElectricalVehicles() {
     return this.electricalVehicles;
   }

   /**
* Protege name: generators
   */
   private Generators generators;
   public void setGenerators(Generators value) { 
    this.generators=value;
   }
   public Generators getGenerators() {
     return this.generators;
   }

   /**
* Protege name: loads
   */
   private Loads loads;
   public void setLoads(Loads value) { 
    this.loads=value;
   }
   public Loads getLoads() {
     return this.loads;
   }

   /**
* Protege name: neighbors
   */
   private Neighbors neighbors;
   public void setNeighbors(Neighbors value) { 
    this.neighbors=value;
   }
   public Neighbors getNeighbors() {
     return this.neighbors;
   }

   /**
* Protege name: node
   */
   private Node node;
   public void setNode(Node value) { 
    this.node=value;
   }
   public Node getNode() {
     return this.node;
   }

   /**
* Protege name: switchs
   */
   private Switchs switchs;
   public void setSwitchs(Switchs value) { 
    this.switchs=value;
   }
   public Switchs getSwitchs() {
     return this.switchs;
   }

}
