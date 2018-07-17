/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import newsensimulator.model.networkelements.Edge;
import newsensimulator.model.networkelements.Vertex;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Jose Muñoz Parra
 */
public class AgentZ extends Agent {

    private Vertex myVertex;
    private MessageTemplate template;

    protected void setup() {
        System.out.println("Partiendo...");
        Object[] args = getArguments();

        myVertex = (Vertex) args[0];
        template = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchLanguage("reque"));
        addBehaviour(new ZSearch(this, myVertex));
    }

    private class ZSearch extends CyclicBehaviour {

        public ZSearch(Agent a, Vertex vertex) {
            super(a);
            myVertex = vertex;
        }

        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                if (msg.getLanguage().equals("test")) {
                    ACLMessage msgReque = new ACLMessage(ACLMessage.REQUEST);
                    msgReque.setSender(getAID());
                    msgReque.setLanguage("impedance");
                    ArrayList<Edge> myNeighboursLines = myVertex.getVertexAsBus().getIncidentLines();
                    for (Edge line : myNeighboursLines) {
                        if (!line.getEdgeAsSimpleLine().getDestino().equals(myVertex)) {
                            AID id = new AID();
                            id.setLocalName(line.getEdgeAsSimpleLine().getDestino().getVertexAsBus().getName());
                            msgReque.addReceiver(id);
                        } else {
                            AID id = new AID();
                            id.setLocalName(line.getEdgeAsSimpleLine().getOrigen().getVertexAsBus().getName());
                            msgReque.addReceiver(id);
                        }

                    }
                    send(msgReque);

                } else if (msg.getLanguage().equals("impedance")) {
                    System.out.println("Z desde " + msg.getSender().getLocalName());
                    ACLMessage msgInfo = msg.createReply();
                    msgInfo.setPerformative(ACLMessage.INFORM);
                    msgInfo.setLanguage("infoVoltage");
                    //msgInfo.setContent(myVertex.getVertexAsBus().getVoltageBus() + "<" + myVertex.getVertexAsBus().getAngleBus());
                    msgInfo.setContent(myVertex.getVertexAsBus().getComplexVoltage().toString());
                    send(msgInfo);
                } else if (msg.getLanguage().equals("infoVoltage")) {
                    double voltageMag = 0;
                    double voltageAngle = 0;

                    double currentMag = 0;
                    double currentAngle = 0;

                    ArrayList<Edge> myLines = myVertex.getVertexAsBus().getIncidentLines();
                    for (Edge line : myLines) {
                        if (line.getEdgeAsSimpleLine().getDestino().getVertexAsBus().getName().equals(msg.getSender().getLocalName())
                                || line.getEdgeAsSimpleLine().getOrigen().getVertexAsBus().getName().equals(msg.getSender().getLocalName())) {

                            //voltageMag = Double.valueOf(msg.getContent().substring(0, msg.getContent().indexOf("<")));
                            //voltageAngle = Double.valueOf(msg.getContent().substring(msg.getContent().indexOf("<") + 1));
                            Complex vNeighbor = Complex.valueOf(Double.valueOf(msg.getContent().substring(msg.getContent().indexOf("(") + 1, msg.getContent().indexOf(","))),
                                    Double.valueOf(msg.getContent().substring(msg.getContent().indexOf(",") + 1, msg.getContent().indexOf(")") - 1)));

                            System.out.println(voltageMag + " ** " + voltageAngle);

                            //currentMag = line.getCurrent().getReal();
                            //currentAngle = line.getCurrent().getImaginary();
                            //System.out.println(currentMag+"   <<<   "+ currentAngle);
                            //double transDegToRad = (Math.PI)/180;
                            //Complex v1 = new Complex(voltageMag*Math.cos(voltageAngle*transDegToRad), voltageMag*Math.sin(voltageAngle*transDegToRad));
                            Complex v2 = myVertex.getVertexAsBus().getComplexVoltage();//new Complex(myVertex.getVertexAsBus().getVoltageBus()*Math.cos(myVertex.getVertexAsBus().getAngleBus()*transDegToRad), myVertex.getVertexAsBus().getVoltageBus()*Math.sin(myVertex.getVertexAsBus().getAngleBus())*transDegToRad);

                            Complex deltaV = vNeighbor.subtract(v2);
                            /*
                             System.out.println("");
                             System.out.println("Complex v1= "+vNeighbor);
                             System.out.println("Complex v2= "+v2);
                             System.out.println("");
                            
                             System.out.println("delta V : "+deltaV.getReal() +"   < < < < "+ deltaV.getImaginary());
                             */
                            //Complex i = new Complex(currentMag*Math.cos(currentAngle), currentMag*Math.sin(currentAngle));
                            //Complex i = new Complex(currentMag, currentAngle);
                            Complex i = line.getEdgeAsSimpleLine().getCurrent();
                            Complex Z = deltaV.divide(i);

                            System.out.println("Impedancia = " + Z.getReal() + "   j*" + Z.getImaginary());
                        }
                    }
                } else {
                    System.out.println("El agente no entiende");
                }
            }

            block();
        }

    }
}
