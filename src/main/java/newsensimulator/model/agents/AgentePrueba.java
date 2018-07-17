/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SubscriptionInitiator;
import java.util.ArrayList;
import newsensimulator.model.networkelements.Edge;
import newsensimulator.model.networkelements.Vertex;
import newsensimulator.model.networkelements.vertextype.BusEventListener;
import newsensimulator.model.networkelements.vertextype.BusEventObject;

public class AgentePrueba extends Agent {

    // Direccion de memoria apuntando al nodo al cual esta a cargo el Agente
    private Vertex myVertex;

    // Array para ir almacenando los servicios que hemos ido publicando, en caso de querer agregar uno
    // que ya fue agregado previamente, se realizara una modificacion
    private ArrayList<ServiceDescription> registeredService;

    // Array con la lista de los vecinos actuales
    private ArrayList<AID> neighbor;

    protected void setup() {
        System.out.println("Agente: " + getAID().getLocalName() + " iniciando ejecución");

        registeredService = new ArrayList();
        neighbor = new ArrayList();

        Object[] args = getArguments();

        myVertex = (Vertex) args[0];

        addBehaviour(new CyclicTest(this, myVertex));

        myVertex.getVertexAsBus().addBusEventListener(new BusEventListener() {

            @Override
            public void changeIncidentLines(BusEventObject args) {

            }

            @Override
            public void withoutEnergy(BusEventObject args) {
            }

            @Override
            public void changeBusType(BusEventObject args) {
            }

            @Override
            public void startSimulation(BusEventObject args) {
                PublishingLines();
            }
        });

    }

    private void addServiceInDF(String serviceName, String serviceType) {
        // Descripción del agente
        DFAgentDescription descripcion = new DFAgentDescription();
        descripcion.setName(getAID());

        // Descripcion de un servicio se proporciona
        ServiceDescription servicio = new ServiceDescription();
        servicio.setType(serviceType);
        servicio.setName(serviceName);

        try {
            int count = 0;
            boolean flagService = true;
            boolean flagUpdate = false;

            for (ServiceDescription serv : registeredService) {
                if (!serv.getName().equals(servicio.getName()) && !serv.getType().equals(servicio.getType())) {
                    count++;
                } else if (serv.getName().equals(servicio.getName()) && !serv.getType().equals(servicio.getType())) {
                    serv.setType(serviceType);
                    flagUpdate = true;
                }
            }
            if (count == registeredService.size()) {
                registeredService.add(servicio);
            } else {
                System.out.println("Servicio repetido o actualizado...");
                flagService = false;
            }

            for (ServiceDescription serv : registeredService) {
                // Añade dicho servicio a la lista de servicios de la descripción del agente
                descripcion.addServices(serv);
            }

            if (flagService || flagUpdate) {
                if (registeredService.size() == 1 && !flagUpdate) {
                    DFService.register(this, descripcion);
                } else {
                    DFService.modify(this, descripcion);
                }
            }

        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

    private void deleteServiceInDF(String serviceName, String serviceType) {
        // Descripción del agente
        DFAgentDescription descripcion = new DFAgentDescription();
        descripcion.setName(getAID());

        // Descripcion de un servicio se proporciona
        ServiceDescription servicio = new ServiceDescription();
        servicio.setType(serviceType);
        servicio.setName(serviceName);

        try {
            int index = 0;
            boolean flagService = true;
            for (ServiceDescription serv : registeredService) {
                if (serv.getName().equals(servicio.getName()) && serv.getType().equals(servicio.getType())) {
                    break;
                }
                index++;

            }
            if (index != registeredService.size()) {
                registeredService.remove(index);
            } else {
                flagService = false;
            }

            for (ServiceDescription serv : registeredService) {
                descripcion.addServices(serv);
            }
            if (flagService) {
                if (registeredService.size() == 0) {
                    DFService.deregister(this);
                } else {
                    DFService.modify(this, descripcion);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void PublishingLines() {
        ArrayList<Edge> lines = myVertex.getVertexAsBus().getIncidentLines();
        for (Edge l : lines) {
            // Origen publica, destino me encuentra...
            if (l.getEdgeAsSimpleLine().getOrigen().equals(myVertex)) {
                // Agrego al DF que tengo una linea nueva... espero que otro agente se cominique conmigo
                addServiceInDF(l.getEdgeAsSimpleLine().getName(), "AvailableLine");
            } else {
                // Como no soy origen, soy destino... buscare el origen de la linea
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription serv = new ServiceDescription();
                serv.setName(l.getEdgeAsSimpleLine().getName());
                serv.setType("AvailableLine");
                template.addServices(serv);
                addSubscrition(template);
            }
        }
    }

    private class CyclicTest extends CyclicBehaviour {

        public CyclicTest(Agent a, Vertex vertex) {
            super(a);
            myVertex = vertex;
        }

        public void action() {
            ACLMessage msg = null;
            if (msg != null && false) {
                System.out.println("mensaje entrante: " + msg.getEncoding());
                if (msg.getEncoding().equals("a")) {
                    System.out.println("registraré...");
                    String content1 = msg.getContent();
                    String content2 = msg.getLanguage();
                    addServiceInDF(content1, content2);
                } else {
                    System.out.println("NO registraré...");
                    String content1 = msg.getContent();
                    String content2 = msg.getLanguage();
                    deleteServiceInDF(content1, content2);
                }
            }
            block();
        }
    }    
    
    private void addSubscrition(DFAgentDescription template) {
        addBehaviour(new SubscriptionInitiator(this, DFService.createSubscriptionMessage(this, getDefaultDF(), template, null)) {
            protected void handleInform(ACLMessage inform) {
                try {
                    DFAgentDescription[] dfds = DFService.decodeNotification(inform.getContent());
                    for (int i = 0; i < dfds.length; i++) {
                        System.out.println(getAID().getLocalName() + ": El Agente " + dfds[i].getName().getName() + " ha publicado un servicio que me interesa");
                    }
                    // do something with dfds
                } catch (FIPAException fe) {
                    fe.printStackTrace();
                }
            }
        });
    }
}
