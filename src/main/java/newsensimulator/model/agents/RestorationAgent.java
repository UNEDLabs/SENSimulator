package newsensimulator.model.agents;

import SENSimulatorOntology.ChangeLineState;
import SENSimulatorOntology.FundamentalLoops;
import SENSimulatorOntology.Lines;
import SENSimulatorOntology.LoopManager;
import SENSimulatorOntology.LoopRequest;
import SENSimulatorOntology.Node;
import SENSimulatorOntology.ReportAloneAgentState;
import SENSimulatorOntology.SearchingNeighbors;
import SENSimulatorOntology.SpreadingMessage;
import SENSimulatorOntology.StatusNode;
import SENSimulatorOntology.VoltageBeforeBlackout;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREInitiator;
import jade.proto.AchieveREResponder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import newsensimulator.model.networkelements.Edge;
import newsensimulator.model.networkelements.Vertex;
import newsensimulator.model.networkelements.vertextype.BusEventListener;
import newsensimulator.model.networkelements.vertextype.BusEventObject;

/**
 *
 * @author Jose Muñoz Parra
 */
public class RestorationAgent extends Agent {

    private Vertex myVertex;

    private static final String ONE_STATE = "UNO";
    private static final String TWO_STATE = "DOS";
    private static final String THREE_STATE = "TRES";
    private static final String FOUR_STATE = "CUATRO";
    private static final String FIVE_STATE = "CINCO";
    private static final String SIX_STATE = "SEIS";
    private static final String SEVEN_STATE = "SIETE";
    private static final String ERROR_STATE = "ZERO";

    private int nextState;

    private ArrayList<AID> neighborAIDList;
    private ArrayList<Edge> neighborLineList;
    private ArrayList<Edge> incidentLines;
    private ArrayList initialLineState;
    private ArrayList<String> aloneAgents;

    private ArrayList<ArrayList> informBlackOut;
    private ArrayList<ArrayList> informOkZone;

    //private ArrayList<String[]> fundamentalLoops;
    private ArrayList<ArrayList> fundamentalLoops;
    //private ArrayList<String[]> loopsRestoration;
    private ArrayList<ArrayList> loopsRestoration;
    //private ArrayList<String[]> finalsLoops;
    private ArrayList<ArrayList> finalsLoops;

    private boolean availableLinesService = false;

    private Edge lineInFault;

    private MessageTemplate templateQueryIf;
    private MessageTemplate templateRequest;
    private MessageTemplate templateDebug;
    private MessageTemplate templatePropagate;
    private MessageTemplate templateAlone;

    private Codec codec = new SLCodec();
    private Ontology ontology = SENSimulatorOntology.SENSOntology.getInstance();
    private long registerALTime = 0;

    private int agentAsController = 0;
    private int agentAsDevice = 1;

    private DeviceAgent deviceAgent;
    private ControllerAgent controllerAgent;

    private RestorationProcess restorationProcess;

    private DebugBehaviour debugBehaviour;

    private Agent agent;
    private AID agentInFault;

    private ArrayList<String> targetAgents;
    private ArrayList<String[]> okAgents;
    private ArrayList<String[]> linesLink;

    private boolean flagContactedPreviewVoltage = false;
    private boolean flagSearchingNeighbors = false;
    private boolean flagStatusNode = false;
    private boolean flagVoltageBeforeBlackout = false;
    private boolean agentInProblem = false;
    private boolean flagAlone = false;
    private boolean publishService = false;

    private boolean flagAllReply = false;

    private int countReplyMsg = 0;
    private int edges = 0;
    private int requestsSent = 0;
    private int requestCount = 0;
    private int publishNumber = 0;

    private int countBO = 0;
    private int countOK = 0;
    private int msgBO = 0;
    private int msgOK = 0;

    private boolean iAmFaultAgent = false;

    private String agentNeighborOk;

    private QueryInitiator queryInitiator;
    private RequestInitiator requestInitiator;

    @Override
    protected void setup() {
        agent = this;
        System.out.println("Partiendo...");
        Object[] args = getArguments();
        neighborAIDList = new ArrayList();
        neighborLineList = new ArrayList();
        incidentLines = new ArrayList();
        fundamentalLoops = new ArrayList();
        loopsRestoration = new ArrayList();
        aloneAgents = new ArrayList();
        finalsLoops = new ArrayList();

        informBlackOut = new ArrayList();
        informOkZone = new ArrayList();

        targetAgents = new ArrayList();
        okAgents = new ArrayList();
        linesLink = new ArrayList();
        //checkingAvailableLinesService = new CheckingAvailableLinesService(this, 1000);

        myVertex = (Vertex) args[0];
        myVertex.getVertexAsBus().addBusEventListener(new BusEventListener() {
            @Override
            public void changeIncidentLines(BusEventObject args) {
                //System.out.println("Linea conectada al agente: " + getAID().getLocalName());
                if (neighborAIDList.size() < args.getBus().getIncidentLinesNumber()) {
                    if (!availableLinesService) {
                        registerAvailableLines();
                        //neighborList.add(":D");
                        //searchServiceAvailableLines();
                        addBehaviour(new searchServiceAvailableLinesBehaviour());
                    }
                } else if (neighborAIDList.size() > 0 && neighborAIDList.size() > args.getBus().getIncidentLinesNumber()) {
                    if (availableLinesService) {
                        deregisterAvailableLines();
                        //neighborList.remove(0);
                    }
                }
            }

            @Override
            public void withoutEnergy(BusEventObject args) {
                /*
                 if (false) {
                 // Publicar servicio de existencia de problemas
                 registerBlackOutPoint();

                 if (!flagContactedPreviewVoltage) {
                 //System.out.println("             Llegue primero: " + getLocalName());
                 //searchWithoutEnergyService();
                 }
                 reportBusInFault();
                 }
                 */
                removeNormalBehaviour();
                agentInProblem = true;
                restorationProcess = new RestorationProcess(agent, myVertex);
                addBehaviour(restorationProcess);
            }

            public void changeBusType(BusEventObject args) {
                switch (myVertex.getVertexAsBus().getBusCodeText()) {
                    case "Load Bus":
                        removeBehaviour(controllerAgent);
                        deviceAgent = new DeviceAgent(agent, myVertex);
                        addBehaviour(deviceAgent);
                        break;
                    case "Slack Bus":
                        removeBehaviour(deviceAgent);
                        controllerAgent = new ControllerAgent(agent, myVertex);
                        addBehaviour(controllerAgent);
                        break;
                    case "Regulated Bus":
                        break;
                    default:
                        break;
                }
            }

            public void startSimulation(BusEventObject args) {

            }
        });

        templateQueryIf = AchieveREResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_QUERY);
        templateRequest = MessageTemplate.and(MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST), MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

        templateAlone = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchEncoding("alone"));

        templateDebug = MessageTemplate.and(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.DISCONFIRM), MessageTemplate.MatchLanguage("debug")), MessageTemplate.MatchOntology("debug"));

        templatePropagate = MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE);

        System.out.println("Soy " + getName() + " y soy " + getClass().getSimpleName());

        addBehaviour(new QueryResponder(this, templateQueryIf));
        addBehaviour(new RequestResponder(this, templateRequest));

        /*
         if (controllerAgentFSM == null) {
         System.out.println("controller igual a null");
         }

         if (deviceAgentFSM == null) {
         System.out.println("device igual a null");
         }
         */
        switch (myVertex.getVertexAsBus().getBusCodeText()) {
            case "Load Bus":
                deviceAgent = new DeviceAgent(this, myVertex);
                addBehaviour(deviceAgent);
                break;
            case "Slack Bus":
                controllerAgent = new ControllerAgent(this, myVertex);
                addBehaviour(controllerAgent);
                break;
            case "Regulated Bus":
                break;
            default:
                break;
        }
        debugBehaviour = new DebugBehaviour(this, myVertex);
        addBehaviour(debugBehaviour);

        addBehaviour(new InformAlone(this, myVertex));

        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);
    }

    private void removeNormalBehaviour() {
        switch (myVertex.getVertexAsBus().getBusCodeText()) {
            case "Load Bus":
                removeBehaviour(deviceAgent);
                break;
            case "Slack Bus":
                removeBehaviour(controllerAgent);
                break;
            case "Regulated Bus":
                break;
            default:
                break;
        }
    }

    private class DebugBehaviour extends CyclicBehaviour {

        public DebugBehaviour(Agent a, Vertex vertex) {
            super(a);
            myVertex = vertex;
        }

        public void action() {

            ACLMessage msgDebug = receive(templateDebug);
            if (msgDebug != null) {
                printFundamentalLoops();
            }

            block();
        }
    }

    private class DeviceAgent extends CyclicBehaviour {

        public DeviceAgent(Agent a, Vertex vertex) {
            super(a);
            myVertex = vertex;
        }

        public void action() {
            ACLMessage msg = myAgent.receive(templatePropagate);
            if (msg != null) {
                try {
                    ContentElement ce = getContentManager().extractContent(msg);
                    if (ce instanceof SpreadingMessage) {
                        SpreadingMessage sm = (SpreadingMessage) ce;
                        FundamentalLoops fl = sm.getFundamentalLoops();
                        int edges = 0;

                        for (AID aid : neighborAIDList) {
                            if (!aid.equals(msg.getSender())) {
                                ACLMessage msgReply = new ACLMessage(ACLMessage.PROPAGATE);
                                msgReply.setSender(getAID());
                                msgReply.addReceiver(aid);
                                msgReply.setLanguage(codec.getName());
                                msgReply.setOntology(ontology.getName());

                                SpreadingMessage smReply = new SpreadingMessage();
                                FundamentalLoops flReply = new FundamentalLoops();

                                flReply.setNodeName(fl.getNodeName() + myVertex.getVertexAsBus().getName() + ";");
                                // StateNode:
                                // "ok" : no problem
                                // "blackout" : with problems
                                if (myVertex.getVertexAsBus().getVoltageBus() > 0) {
                                    flReply.setNodeState(fl.getNodeState() + "ok;");
                                } else {
                                    flReply.setNodeState(fl.getNodeState() + "blackout;");
                                }
                                //Line l = incidentLines.get(neighborAIDList.indexOf(aid));
                                Edge l = neighborLineList.get(neighborAIDList.indexOf(aid));
                                flReply.setLINEName(fl.getLINEName() + l.getEdgeAsSimpleLine().getName() + ";");

                                if (l.getEdgeAsSimpleLine().getSwitchStatus() != 11) {
                                    flReply.setLINEState(fl.getLINEState() + "opened;");

                                } else {
                                    flReply.setLINEState(fl.getLINEState() + "closed;");
                                }

                                smReply.setFundamentalLoops(flReply);

                                //String[] loop = findingLoop(flReply);
                                ArrayList<String[]> loop = findingLoop(flReply);

                                if (loop == null && myVertex.getVertexAsBus().getCountFundamentalLoopsSystem() >= fundamentalLoops.size()) {
                                    getContentManager().registerLanguage(codec);
                                    getContentManager().registerOntology(ontology);
                                    getContentManager().fillContent(msgReply, smReply);

                                    if (myVertex.getVertexAsBus().getTotalBuses() >= countReplyMsg) {
                                        send(msgReply);
                                        countReplyMsg++;
                                    } else {
                                        msgReply.reset();
                                    }

                                } else {
                                    addLoop(loop);
                                }
                                /*
                                 if (myVertex.getVertexAsBus().getCountFundamentalLoopsSystem() >= fundamentalLoops.size() && fundamentalLoops.size() > 0) {
                                 System.out.println(myAgent.getLocalName() + ": tengo aca " + fundamentalLoops.size() + " loops");
                                 for (int k = 0; k < fundamentalLoops.size(); k++) {
                                 for (int j = 0; j < fundamentalLoops.get(k).length; j++) {
                                 System.out.print(fundamentalLoops.get(k)[j] + "  ");
                                 }
                                 System.out.println("");
                                 }
                                 }
                                 */
                                edges++;
                            }
                        }
                        if (edges == 0) {
                            System.out.println(myAgent.getLocalName() + " : Soy ultimo en la red");
                        }
                    }
                } catch (Codec.CodecException ex) {
                    Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                } catch (OntologyException ex) {
                    Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (fundamentalLoops.size() > 0) {
                    registerLoopService();
                }
                restorationProcess = new RestorationProcess(agent, myVertex);
                addBehaviour(restorationProcess);
                removeBehaviour(this);
            }
            block();
        }
    }

    private class ControllerAgent extends CyclicBehaviour {

        public ControllerAgent(Agent a, Vertex vertex) {
            super(a);
            myVertex = vertex;
        }

        public void action() {
            ACLMessage msg = myAgent.receive(templatePropagate);
            if (msg != null) {
                try {
                    ContentElement ce = getContentManager().extractContent(msg);
                    if (ce instanceof SpreadingMessage) {
                        SpreadingMessage sm = (SpreadingMessage) ce;
                        FundamentalLoops fl = sm.getFundamentalLoops();
                        int edges = 0;

                        for (AID aid : neighborAIDList) {
                            if (!aid.equals(msg.getSender())) {
                                ACLMessage msgReply = new ACLMessage(ACLMessage.PROPAGATE);
                                msgReply.setSender(getAID());
                                msgReply.addReceiver(aid);
                                msgReply.setLanguage(codec.getName());
                                msgReply.setOntology(ontology.getName());

                                SpreadingMessage smReply = new SpreadingMessage();
                                FundamentalLoops flReply = new FundamentalLoops();

                                flReply.setNodeName(fl.getNodeName() + myVertex.getVertexAsBus().getName() + ";");
                                // StateNode:
                                // "ok" : no problem
                                // "blackout" : with problems
                                if (myVertex.getVertexAsBus().getVoltageBus() > 0) {
                                    flReply.setNodeState(fl.getNodeState() + "ok;");
                                } else {
                                    flReply.setNodeState(fl.getNodeState() + "blackout;");
                                }
                                //Line l = incidentLines.get(neighborAIDList.indexOf(aid));
                                Edge l = neighborLineList.get(neighborAIDList.indexOf(aid));

                                flReply.setLINEName(fl.getLINEName() + l.getEdgeAsSimpleLine().getName() + ";");
                                if (l.getEdgeAsSimpleLine().getSwitchStatus() != 11) {
                                    flReply.setLINEState(fl.getLINEState() + "opened;");
                                } else {
                                    flReply.setLINEState(fl.getLINEState() + "closed;");
                                }

                                smReply.setFundamentalLoops(flReply);

                                //String[] loop = findingLoop(flReply);
                                ArrayList<String[]> loop = findingLoop(flReply);

                                if (loop == null && myVertex.getVertexAsBus().getCountFundamentalLoopsSystem() >= fundamentalLoops.size()) {
                                    getContentManager().registerLanguage(codec);
                                    getContentManager().registerOntology(ontology);
                                    getContentManager().fillContent(msgReply, smReply);

                                    if (myVertex.getVertexAsBus().getTotalBuses() >= countReplyMsg) {
                                        send(msgReply);
                                        countReplyMsg++;
                                    } else {
                                        msgReply.reset();
                                    }

                                } else {
                                    addLoop(loop);
                                }
                                /*
                                 if (myVertex.getVertexAsBus().getCountFundamentalLoopsSystem() >= fundamentalLoops.size() && fundamentalLoops.size() > 0) {
                                 System.out.println(myAgent.getLocalName() + ": tengo aca " + fundamentalLoops.size() + " loops");
                                 for (int k = 0; k < fundamentalLoops.size(); k++) {
                                 for (int j = 0; j < fundamentalLoops.get(k).length; j++) {
                                 System.out.print(fundamentalLoops.get(k)[j] + "  ");
                                 }
                                 System.out.println("");
                                 }
                                 }
                                 */
                                edges++;
                            }
                        }
                        if (edges == 0) {
                            System.out.println(myAgent.getLocalName() + " : Soy ultimo en la red");
                        }
                    }
                } catch (Codec.CodecException ex) {
                    Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                } catch (OntologyException ex) {
                    Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (fundamentalLoops.size() > 0) {
                    registerLoopService();
                }
                restorationProcess = new RestorationProcess(agent, myVertex);
                addBehaviour(restorationProcess);
                removeBehaviour(this);
            }
            block();
        }
    }

    private class InformAlone extends CyclicBehaviour {

        public InformAlone(Agent a, Vertex vertex) {
            super(a);
            myVertex = vertex;
        }

        public void action() {
            ACLMessage msgAlone = receive(templateAlone);
            if (msgAlone != null) {
                try {
                    ContentElement ce = getContentManager().extractContent(msgAlone);
                    if (ce instanceof ReportAloneAgentState) {
                        System.out.println(getLocalName() + ": El agente " + msgAlone.getSender().getLocalName() + " informa estado solitario");

                        ReportAloneAgentState ras = (ReportAloneAgentState) ce;
                        Node n = ras.getReportAloneAgentState();

                        aloneAgents.add(n.getBusName());
                    }
                } catch (Codec.CodecException ex) {
                    Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                } catch (OntologyException ex) {
                    Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            block();
        }
    }

    private class RestorationProcess extends FSMBehaviour {

        public RestorationProcess(Agent a, Vertex vertex) {
            super(a);
            myVertex = vertex;
        }

        public void onStart() {
            registerFirstState(new OneBehaviour(), ONE_STATE);
            registerState(new TwoBehaviour(), TWO_STATE);
            registerState(new ThreeBehaviour(), THREE_STATE);
            registerState(new FourBehaviour(), FOUR_STATE);
            registerState(new FiveBehaviour(), FIVE_STATE);
            registerState(new SixBehaviour(), SIX_STATE);
            registerState(new SevenBehaviour(), SEVEN_STATE);

            registerLastState(new ErrorBehaviour(), ERROR_STATE);

            registerTransition(ONE_STATE, ONE_STATE, 1);
            registerTransition(TWO_STATE, TWO_STATE, 2);
            registerTransition(THREE_STATE, THREE_STATE, 3);
            registerTransition(FOUR_STATE, FOUR_STATE, 4);
            registerTransition(FIVE_STATE, FIVE_STATE, 5);
            registerTransition(SIX_STATE, SIX_STATE, 6);
            registerTransition(SEVEN_STATE, SEVEN_STATE, 7);

            registerTransition(ONE_STATE, TWO_STATE, 2);
            registerTransition(ONE_STATE, THREE_STATE, 3);
            registerTransition(TWO_STATE, THREE_STATE, 3);
            registerTransition(THREE_STATE, FOUR_STATE, 4);
            registerTransition(FOUR_STATE, FIVE_STATE, 5);
            registerTransition(FIVE_STATE, SIX_STATE, 6);
            registerTransition(SIX_STATE, SEVEN_STATE, 7);
            registerTransition(SEVEN_STATE, ONE_STATE, 1);

            registerDefaultTransition(ONE_STATE, ERROR_STATE);
            registerDefaultTransition(TWO_STATE, ERROR_STATE);
            registerDefaultTransition(THREE_STATE, ERROR_STATE);
            registerDefaultTransition(FOUR_STATE, ERROR_STATE);
            registerDefaultTransition(FIVE_STATE, ERROR_STATE);
            registerDefaultTransition(SIX_STATE, ERROR_STATE);
        }

        private class OneBehaviour extends OneShotBehaviour {

            public void action() {
                if (!flagAllReply) {
                    System.out.println(getAID().getLocalName() + ": Restoration process...");
                    lineInFault = null;
                    if (agentInProblem) {
                        for (Edge l : incidentLines) {
                            if (l.getEdgeAsSimpleLine().getInFault()) {

                                System.out.println(myAgent.getLocalName() + ": estoy cercano a la falla");
                                registerBlackOutPoint();
                                iAmFaultAgent = true;
                                if (myVertex.equals(l.getEdgeAsSimpleLine().getOrigen())) {
                                    agentNeighborOk = l.getEdgeAsSimpleLine().getDestino().getVertexAsBus().getName();
                                } else {
                                    agentNeighborOk = l.getEdgeAsSimpleLine().getOrigen().getVertexAsBus().getName();
                                }
                                System.out.println("Al otro lado de la falla: " + agentNeighborOk);
                                lineInFault = l;
                                break;
                            }
                        }
                    }
                    if (lineInFault != null) {
                        nextState = 2;
                    } else {
                        nextState = 3;
                    }
                } else {
                    nextState = 1;
                    block();
                }
            }

            public int onEnd() {
                return nextState;
            }
        }

        private class TwoBehaviour extends OneShotBehaviour {

            public void action() {
                System.out.println(getAID().getLocalName() + "pase al segundo estado...");
                if (lineInFault != null) {
                    System.out.println("COMIENZO A PROPAGAR EL MENSAJE");
                    System.out.println(getAID().getName());

                    for (AID aid : neighborAIDList) {
                        try {
                            ACLMessage msg = new ACLMessage(ACLMessage.PROPAGATE);
                            msg.setSender(getAID());
                            msg.addReceiver(aid);
                            msg.setLanguage(codec.getName());
                            msg.setOntology(ontology.getName());

                            SpreadingMessage sm = new SpreadingMessage();
                            FundamentalLoops fl = new FundamentalLoops();

                            fl.setNodeName(myVertex.getVertexAsBus().getName() + ";");
                            // StateNode:
                            // "ok" : no problem
                            // "blackout" : with problems
                            if (myVertex.getVertexAsBus().getVoltageBus() > 0) {
                                fl.setNodeState("ok;");
                            } else {
                                fl.setNodeState("blackout;");
                            }
                            //Line l = incidentLines.get(neighborAIDList.indexOf(aid));
                            Edge l = neighborLineList.get(neighborAIDList.indexOf(aid));
                            fl.setLINEName(l.getEdgeAsSimpleLine().getName() + ";");
                            if (l.getEdgeAsSimpleLine().getSwitchStatus() != 11) {
                                fl.setLINEState("opened;");
                            } else {
                                fl.setLINEState("closed;");
                            }

                            sm.setFundamentalLoops(fl);

                            getContentManager().registerLanguage(codec);
                            getContentManager().registerOntology(ontology);

                            getContentManager().fillContent(msg, sm);

                            send(msg);
                        } catch (Codec.CodecException ex) {
                            Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (OntologyException ex) {
                            Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                nextState = 3;
            }

            public int onEnd() {
                return nextState;
            }
        }

        private class ThreeBehaviour extends OneShotBehaviour {

            public void action() {
                ACLMessage msg = myAgent.receive(templatePropagate);
                if (msg != null) {
                    try {
                        ContentElement ce = getContentManager().extractContent(msg);
                        if (ce instanceof SpreadingMessage) {
                            SpreadingMessage sm = (SpreadingMessage) ce;
                            FundamentalLoops fl = sm.getFundamentalLoops();

                            for (AID aid : neighborAIDList) {
                                if (!aid.equals(msg.getSender())) {
                                    ACLMessage msgReply = new ACLMessage(ACLMessage.PROPAGATE);
                                    msgReply.setSender(getAID());
                                    msgReply.addReceiver(aid);
                                    msgReply.setLanguage(codec.getName());
                                    msgReply.setOntology(ontology.getName());

                                    SpreadingMessage smReply = new SpreadingMessage();
                                    FundamentalLoops flReply = new FundamentalLoops();

                                    flReply.setNodeName(fl.getNodeName() + myVertex.getVertexAsBus().getName() + ";");
                                    // StateNode:
                                    // "ok" : no problem
                                    // "blackout" : with problems
                                    if (myVertex.getVertexAsBus().getVoltageBus() > 0) {
                                        flReply.setNodeState(fl.getNodeState() + "ok;");
                                    } else {
                                        flReply.setNodeState(fl.getNodeState() + "blackout;");
                                    }
                                    //Line l = incidentLines.get(neighborAIDList.indexOf(aid));
                                    Edge l = neighborLineList.get(neighborAIDList.indexOf(aid));
                                    flReply.setLINEName(fl.getLINEName() + l.getEdgeAsSimpleLine().getName() + ";");
                                    if (l.getEdgeAsSimpleLine().getSwitchStatus() != 11) {
                                        flReply.setLINEState(fl.getLINEState() + "opened;");
                                    } else {
                                        flReply.setLINEState(fl.getLINEState() + "closed;");
                                    }

                                    smReply.setFundamentalLoops(flReply);

                                    //String[] loop = findingLoop(flReply);
                                    ArrayList<String[]> loop = findingLoop(flReply);
                                    if (loop == null && myVertex.getVertexAsBus().getCountFundamentalLoopsSystem() >= fundamentalLoops.size()) {
                                        getContentManager().registerLanguage(codec);
                                        getContentManager().registerOntology(ontology);
                                        getContentManager().fillContent(msgReply, smReply);

                                        if (myVertex.getVertexAsBus().getTotalBuses() >= countReplyMsg) {
                                            send(msgReply);
                                            countReplyMsg++;
                                        } else {
                                            msgReply.reset();
                                        }

                                    } else {
                                        addLoop(loop);

                                    }
                                    edges++;
                                }
                            }
                            if (edges == 0 && !flagAlone) {
                                System.out.println(myAgent.getLocalName() + " : Soy ultimo en la red");
                                flagAlone = true;
                                reportAloneState();
                            }
                        }
                    } catch (Codec.CodecException ex) {
                        Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (OntologyException ex) {
                        Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                if (fundamentalLoops.size() > 0) {
                    registerLoopService();
                }

                System.out.println(getLocalName() + ": TAMAÑO ----> " + fundamentalLoops.size());
                if (myVertex.getVertexAsBus().getCountFundamentalLoopsSystem() <= fundamentalLoops.size() && iAmFaultAgent) {
                    System.out.println(getLocalName() + ": ALGUNA VEZ LO HACE ACA....");
                    nextState = 4;
                } else {
                    nextState = 3;
                }

                System.out.println(getLocalName() + ": contador en ---> " + countReplyMsg);
            }

            public int onEnd() {
                if (nextState != 4) {
                    block();
                }
                return nextState;
            }
        }

        private class FourBehaviour extends OneShotBehaviour {

            // El unico agente que pasa a este estado es el agente en falla
            public void action() {
                System.out.println("AGENTE: " + getLocalName() + " PASO AL ESTADO 4");

                try {
                    Thread.sleep((int) Math.random() * 1000 + 1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                DFAgentDescription[] result = searchingLoopService();

                try {
                    ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                    msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
                    msg.setLanguage(codec.getName());
                    msg.setOntology(ontology.getName());
                    msg.setSender(getAID());

                    for (int i = 0; i < result.length; i++) {
                        if (!result[i].getName().equals(getAID())) {
                            msg.addReceiver(result[i].getName());
                            requestsSent++;
                        }
                    }

                    LoopRequest lr = new LoopRequest();
                    FundamentalLoops fl = new FundamentalLoops();

                    lr.setLoopRequest(fl);

                    getContentManager().registerLanguage(codec);
                    getContentManager().registerOntology(ontology);

                    getContentManager().fillContent(msg, lr);

                    //addBehaviour(new RequestInitiator(myAgent, msg));
                    requestInitiator = new RequestInitiator(myAgent, msg);
                    addBehaviour(requestInitiator);

                } catch (Codec.CodecException ex) {
                    Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                } catch (OntologyException ex) {
                    Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                }
                nextState = 5;
            }

            public int onEnd() {
                return nextState;
            }
        }

        private class FiveBehaviour extends OneShotBehaviour {

            public void action() {
                if (requestCount == requestsSent) {
                    for (ArrayList<String[]> aStr : fundamentalLoops) {
                        loopsRestoration.add(aStr);
                    }
                    int max = 0;
                    //for (String[] aStr : loopsRestoration) {
                    for (int m = 0; m < loopsRestoration.size(); m++) {
                        String[] aStr = (String[]) loopsRestoration.get(m).get(0);
                        for (int i = 0; i < aStr.length; i++) {
                            try {
                                if (Integer.parseInt(aStr[i].substring(1)) > max) {
                                    max = Integer.parseInt(aStr[i].substring(1));
                                }
                            } catch (Exception ex) {
                                System.out.println("PROBLEMAS CON EL ENTERO");
                                ex.printStackTrace();
                            }
                        }
                    }
                    System.out.println("EL NODO DE NUMERO MAYOR ES: " + max);

                    finalsLoops = searchFinalsLoop(loopsRestoration, aloneAgents, max);

                    printFinalsLoops(finalsLoops);

                    nextState = 6;

                } else {
                    nextState = 5;
                }

            }

            public int onEnd() {
                return nextState;
            }
        }

        private class SixBehaviour extends OneShotBehaviour {

            // El estado definitivo (final :D)...
            public void action() {
                try {
                    ArrayList<String> nodeBlackOut = new ArrayList();
                    ArrayList<String[]> neighborBlackOut = new ArrayList();
                    ArrayList<String[]> lineNeighborBlackOut = new ArrayList();

                    for (ArrayList<String[]> loop : finalsLoops) {
                        String[] nodes = loop.get(0);
                        String[] nState = loop.get(3);

                        for (int i = 0; i < nodes.length; i++) {
                            if (nState[i].equals("blackout")) {
                                nodeBlackOut.add(nodes[i]);
                            }
                        }
                    }
                    /*
                     HashSet<String> hashSet = new HashSet<String>(nodeBlackOut);
                     nodeBlackOut.clear();
                     nodeBlackOut.addAll(hashSet);
                     */

                    Set<String> linkedHashSet = new LinkedHashSet<String>();
                    linkedHashSet.addAll(nodeBlackOut);
                    nodeBlackOut.clear();
                    nodeBlackOut.addAll(linkedHashSet);

                    System.out.println("NODOS BLACKOUT:");
                    for (String n : nodeBlackOut) {
                        System.out.print(n + "  ");
                    }
                    System.out.println("");

                    for (String nbo : nodeBlackOut) {
                        String aux = "";
                        String lineAux = "";
                        for (ArrayList<String[]> loop : finalsLoops) {
                            String[] l = loop.get(0);
                            String[] line = loop.get(1);
                            String[] ls = loop.get(3);

                            for (int i = 0; i < l.length; i++) {
                                if (nbo.equals(l[i])) {
                                    if (i == 0) {
                                        if (ls[i + 1].equals("ok")) {
                                            aux = aux + l[i + 1] + ";";
                                            lineAux = lineAux + line[i] + ";";
                                        }
                                    } else if (i == l.length - 1) {
                                        if (ls[i - 1].equals("ok")) {
                                            aux = aux + l[i - 1] + ";";
                                            lineAux = lineAux + line[i - 1] + ";";
                                        }
                                    } else {
                                        if (ls[i + 1].equals("ok")) {
                                            aux = aux + l[i + 1] + ";";
                                            lineAux = lineAux + line[i] + ";";
                                        }
                                        if (ls[i - 1].equals("ok")) {
                                            aux = aux + l[i - 1] + ";";
                                            lineAux = lineAux + line[i - 1] + ";";
                                        }
                                    }
                                }
                            }
                        }
                        String[] neighbor = stringToArrayString(aux);
                        String[] lineNeighbor = stringToArrayString(lineAux);

                        ArrayList<String> aLAux = new ArrayList();
                        ArrayList<String> aLLAux = new ArrayList();

                        for (int i = 0; i < neighbor.length; i++) {
                            aLAux.add(neighbor[i]);
                            aLLAux.add(lineNeighbor[i]);
                        }
                        /*
                         hashSet = new HashSet<String>(aLAux);
                         aLAux.clear();
                         aLAux.addAll(hashSet);
                         */
                        linkedHashSet = new LinkedHashSet<String>();
                        linkedHashSet.addAll(aLAux);
                        aLAux.clear();
                        aLAux.addAll(linkedHashSet);

                        linkedHashSet = new LinkedHashSet<String>();
                        linkedHashSet.addAll(aLLAux);
                        aLLAux.clear();
                        aLLAux.addAll(linkedHashSet);

                        neighbor = new String[aLAux.size()];
                        lineNeighbor = new String[aLLAux.size()];

                        for (int i = 0; i < neighbor.length; i++) {
                            neighbor[i] = aLAux.get(i);
                            lineNeighbor[i] = aLLAux.get(i);
                        }

                        neighborBlackOut.add(neighbor);
                        lineNeighborBlackOut.add(lineNeighbor);
                    }

                    //for (String str :nodeBlackOut ) {
                    for (int i = 0; i < nodeBlackOut.size(); i++) {
                        System.out.println("VECINOS OK DE: " + nodeBlackOut.get(i));
                        for (int j = 0; j < neighborBlackOut.get(i).length; j++) {
                            System.out.print("    " + neighborBlackOut.get(i)[j]);
                        }
                        System.out.println("");
                        for (int j = 0; j < lineNeighborBlackOut.get(i).length; j++) {
                            System.out.print("    " + lineNeighborBlackOut.get(i)[j]);
                        }
                        System.out.println("");
                    }
                    //Array con los nodos BO
                    targetAgents = nodeBlackOut;
                    // Array con vecinos y lineas de los nodos BO
                    okAgents = neighborBlackOut;
                    linesLink = lineNeighborBlackOut;

                    ACLMessage msg = new ACLMessage(ACLMessage.QUERY_REF);
                    msg.setProtocol(FIPANames.InteractionProtocol.FIPA_QUERY);
                    msg.setLanguage(codec.getName());
                    msg.setOntology(ontology.getName());
                    msg.setSender(getAID());

                    VoltageBeforeBlackout vbb = new VoltageBeforeBlackout();
                    Node n = new Node();
                    n.setBusName("nada");
                    n.setVoltage(-1);
                    n.setAngle(-1);
                    vbb.setVBeforeBlackout(n);
                    msgBO = 0;
                    for (int i = 0; i < nodeBlackOut.size(); i++) {
                        AID id = new AID();
                        id.setLocalName(nodeBlackOut.get(i));
                        if (!id.getName().equals(getAID()) && !getLocalName().equals(nodeBlackOut.get(i))) {
                            msg.addReceiver(id);
                            msgBO++;
                        }
                    }

                    getContentManager().registerLanguage(codec);
                    getContentManager().registerOntology(ontology);
                    getContentManager().fillContent(msg, vbb);

                    //this.addBehaviour(new QueryInitiator(this, msg));
                    queryInitiator = new QueryInitiator(myAgent, msg);
                    addBehaviour(queryInitiator);

                    msg = new ACLMessage(ACLMessage.QUERY_REF);
                    msg.setProtocol(FIPANames.InteractionProtocol.FIPA_QUERY);
                    msg.setLanguage(codec.getName());
                    msg.setOntology(ontology.getName());
                    msg.setSender(getAID());

                    StatusNode sn = new StatusNode();
                    n = new Node();
                    n.setBusName("nada");
                    n.setVoltage(-1);
                    n.setAngle(-1);
                    sn.setNODE(n);

                    ArrayList<String> nb = new ArrayList();
                    for (String[] nei : neighborBlackOut) {
                        for (int i = 0; i < nei.length; i++) {
                            nb.add(nei[i]);
                        }
                    }

                    HashSet<String> hashSet = new HashSet<String>(nb);
                    nb.clear();
                    nb.addAll(hashSet);

                    msgOK = 0;
                    for (int i = 0; i < nb.size(); i++) {
                        AID id = new AID();
                        id.setLocalName(nb.get(i));
                        if (!id.getName().equals(getAID()) && nodeBlackOut.contains(getLocalName())) {// !getLocalName().equals(nodeBlackOut.get(i))) {
                            msg.addReceiver(id);
                            msgOK++;
                        }
                    }

                    getContentManager().registerLanguage(codec);
                    getContentManager().registerOntology(ontology);
                    getContentManager().fillContent(msg, sn);

                    queryInitiator = new QueryInitiator(myAgent, msg);
                    addBehaviour(queryInitiator);

                    nextState = 7;

                } catch (Codec.CodecException ex) {
                    Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                } catch (OntologyException ex) {
                    Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            public int onEnd() {
                return nextState;
            }
        }

        private class SevenBehaviour extends OneShotBehaviour {

            // El estado definitivo (final :D)...
            public void action() {
                System.out.println("ESTADO 7");
                System.out.println("msg enviado BO: " + msgBO);
                System.out.println("msg enviado OK: " + msgOK);
                System.out.println("msg recibido BO: " + informBlackOut.size());
                System.out.println("msg recibido OK: " + informOkZone.size());

                if (informBlackOut.size() == msgBO && informOkZone.size() == msgOK) {
                    System.out.println("TENGO TODAS LAS RESPUESTAS");

                    flagAllReply = true;
                    float voltageCandidate = 0;
                    String agentCandidate = "";
                    for (ArrayList inform : informOkZone) {
                        String agent = (String) inform.get(0);
                        Node n = (Node) inform.get(1);

                        System.out.println("cand " + agent + " y voltaje :" + n.getVoltage());
                        if (voltageCandidate < n.getVoltage() && !agentNeighborOk.equals(agent)) {
                            agentCandidate = agent;
                            voltageCandidate = n.getVoltage();
                            System.out.println("en el if " + agentCandidate + " y v -> " + voltageCandidate);
                        }
                    }

                    System.out.println("Quien sera el punto de restauracion: " + agentCandidate);

                    try {
                        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                        msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
                        msg.addReceiver(new AID(agentCandidate, AID.ISLOCALNAME));
                        msg.setLanguage(codec.getName());
                        msg.setOntology(ontology.getName());

                        ChangeLineState cls = new ChangeLineState();
                        Lines li = new Lines();

                        /*
                         okAgents = neighborBlackOut;
                         linesLink = lineNeighborBlackOut;
                         */
                        ArrayList<String> index = new ArrayList();
                        ArrayList<ArrayList> option = new ArrayList();

                        //for(String[] nei : okAgents){
                        for (int j = 0; j < okAgents.size(); j++) {
                            String[] nei = okAgents.get(j);
                            for (int i = 0; i < nei.length; i++) {
                                if (nei[i].equals(agentCandidate)) {
                                    index.add(targetAgents.get(j));
                                    //index.add(nei);
                                    index.add(linesLink.get(j)[i]);
                                }
                            }
                            option.add(index);
                        }
                        String test = "";
                        for (ArrayList aux : option) {
                            System.out.println("------->>>> Enlace: " + (String) aux.get(0) + " linea " + (String) aux.get(1) + " PTO enlace " + agentCandidate);
                            test = (String) aux.get(1);
                        }

                        cls.setLINEName(test);
                        cls.setLineState(true);

                        getContentManager().registerLanguage(codec);
                        getContentManager().registerOntology(ontology);
                        Action a = new Action(getAID(), cls);
                        getContentManager().fillContent(msg, a);

                        requestInitiator = new RequestInitiator(myAgent, msg);
                        addBehaviour(requestInitiator);
                        block();
                    } catch (Codec.CodecException ex) {
                        Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (OntologyException ex) {
                        Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                if (flagAllReply) {
                    nextState = 1;
                } else {
                    nextState = 7;
                }
            }

            public int onEnd() {
                return nextState;
            }
        }

        private class ErrorBehaviour extends OneShotBehaviour {

            public void action() {
                //System.out.println("Error!: Al estado " + ONE_STATE);
            }
        }
    }

    private ArrayList searchFinalsLoop(ArrayList<ArrayList> candidates, ArrayList<String> aloneAgents, int lastBus) {
        boolean alone = false;
        boolean foundInFirstLoop = false;
        boolean foundInSecondLoop = false;

        for (int i = 1; i <= lastBus; i++) {
            foundInFirstLoop = false;
            foundInSecondLoop = false;
            String bus = "B" + i;
            for (String aA : aloneAgents) {
                if (aA.equals(bus)) {
                    alone = true;
                    break;
                }
            }
            if (!alone) {
                //for (String[] loop : finalsLoops) {
                for (int m = 0; m < finalsLoops.size(); m++) {
                    String[] loop = (String[]) finalsLoops.get(m).get(0);
                    for (int j = 0; j < loop.length; j++) {
                        if (loop[j].equals(bus)) {
                            foundInFirstLoop = true;
                            break;
                        }
                    }
                    if (foundInFirstLoop) {
                        break;
                    }
                }
                if (!foundInFirstLoop) {
                    //for (String[] loop : candidates) {
                    for (int m = 0; m < candidates.size(); m++) {
                        String[] loop = (String[]) candidates.get(m).get(0);
                        for (int j = 0; j < loop.length; j++) {
                            if (loop[j].equals(bus)) {
                                //finalsLoops.add(loop);
                                finalsLoops.add(candidates.get(m));
                                //candidates.remove(loop);
                                foundInSecondLoop = true;
                                break;
                            }
                        }
                        if (foundInSecondLoop) {
                            break;
                        }
                    }
                }
            }
        }

        if (finalsLoops.size() < myVertex.getVertexAsBus().getCountFundamentalLoopsSystem()) {
            System.out.println("NECESITO " + (myVertex.getVertexAsBus().getCountFundamentalLoopsSystem() - finalsLoops.size()) + " LOOPS");
            //int count = 0;
            //for (String[] addLoop : candidates) {
            for (int m = 0; m < candidates.size(); m++) {
                String[] addLoop = (String[]) candidates.get(m).get(0);
                //for (String[] loops : finalsLoops) {
                for (int n = 0; n < finalsLoops.size(); n++) {
                    String[] loops = (String[]) finalsLoops.get(n).get(0);
                    if (!compareArrayByElements(addLoop, loops)) {//!Arrays.equals(addLoop, loops)) {
                        //count++;
                        finalsLoops.add(candidates.get(m));
                        break;
                        //System.out.println("LA SUMA VA EN " + count);
                    }
                }
                if ((myVertex.getVertexAsBus().getCountFundamentalLoopsSystem() - finalsLoops.size()) == 0) {
                    break;
                }
                /*
                 if (count == finalsLoops.size()) {
                 System.out.println("SUMO ALGO?");
                 //finalsLoops.add(addLoop);
                 finalsLoops.add(candidates.get(m));
                 count = 0;
                 }
                 */
            }
        }
        System.out.println("ENCONTRE " + finalsLoops.size() + " LOOPS");
        //for (String[] prt : finalsLoops) {
        for (int m = 0; m < finalsLoops.size(); m++) {
            String[] prt = (String[]) finalsLoops.get(m).get(0);
            for (int i = 0; i < prt.length; i++) {
                System.out.print(prt[i] + "   ");
            }
            System.out.println("");
        }
        return finalsLoops;
    }

    private boolean compareArrayByElements(String[] a, String[] b) {
        boolean result = false;
        System.out.println("");
        if (a.length == b.length) {
            for (int i = 0; i < a.length; i++) {
                System.out.println("--------> Comparando: " + a[i] + " con " + b[i]);
                if (!a[i].equals(b[i])) {
                    System.out.println("");
                    System.out.println("");
                    return result;
                }
            }
            result = true;
        }
        return result;
    }

    private void reportAloneState() {
        try {
            DFAgentDescription description = new DFAgentDescription();
            ServiceDescription service = new ServiceDescription();
            service.setType("BlackOutPoint");
            service.setName("WithoutEnergy");
            description.addServices(service);

            DFAgentDescription[] resultados = DFService.search(this, description);

            if (resultados.length == 0) {
                System.out.println("Ningun agente ofrece el servicio deseado");
            } else {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.setLanguage(codec.getName());
                msg.setOntology(ontology.getName());
                msg.setSender(getAID());
                msg.setEncoding("alone");

                ReportAloneAgentState ras = new ReportAloneAgentState();
                Node n = new Node();

                n.setBusName(myVertex.getVertexAsBus().getName());
                ras.setReportAloneAgentState(n);

                for (int i = 0; i < resultados.length; i++) {
                    if (!resultados[i].getName().equals(getAID())) {
                        msg.addReceiver(resultados[i].getName());
                    }
                }

                getContentManager().registerLanguage(codec);
                getContentManager().registerOntology(ontology);
                getContentManager().fillContent(msg, ras);

                send(msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //private String[] findingLoop(FundamentalLoops fl) {
    private ArrayList findingLoop(FundamentalLoops fl) {
        ArrayList<String[]> dataLoop = null;

        String[] loop = null;
        String[] loopL = null;
        String[] loopLS = null;
        String[] loopS = null;

        String possibleLoop = fl.getNodeName();
        String linesLoop = fl.getLINEName();
        String linesStateLoop = fl.getLINEState();
        String possibleLoopS = fl.getNodeState();

        ArrayList<String> nodes = new ArrayList();
        ArrayList<String> lLoop = new ArrayList();
        ArrayList<String> lSLoop = new ArrayList();
        ArrayList<String> nodesS = new ArrayList();

        while (!possibleLoop.isEmpty()) {
            String aux = possibleLoop.substring(0, possibleLoop.indexOf(";"));
            possibleLoop = possibleLoop.substring(possibleLoop.indexOf(";") + 1);
            nodes.add(aux);

            String auxL = linesLoop.substring(0, linesLoop.indexOf(";"));
            linesLoop = linesLoop.substring(linesLoop.indexOf(";") + 1);
            lLoop.add(auxL);

            String auxLS = linesStateLoop.substring(0, linesStateLoop.indexOf(";"));
            linesStateLoop = linesStateLoop.substring(linesStateLoop.indexOf(";") + 1);
            lSLoop.add(auxLS);

            String auxNS = possibleLoopS.substring(0, possibleLoopS.indexOf(";"));
            possibleLoopS = possibleLoopS.substring(possibleLoopS.indexOf(";") + 1);
            nodesS.add(auxNS);
        }

        String[] infoNodes = new String[nodes.size()];
        String[] infoL = new String[lLoop.size()];
        String[] infoLS = new String[lSLoop.size()];
        String[] infoNodesS = new String[nodesS.size()];

        for (int i = 0; i < nodes.size(); i++) {
            infoNodes[i] = nodes.get(i);
            infoL[i] = lLoop.get(i);
            infoLS[i] = lSLoop.get(i);
            infoNodesS[i] = nodesS.get(i);
        }

        for (int i = 0; i < infoNodes.length; i++) {
            for (int j = 0; j < infoNodes.length; j++) {
                if (infoNodes[i].equals(infoNodes[j]) && i != j) {
                    String[] loopAux = new String[Math.abs(j - i + 1)];
                    String[] loopLAux = new String[Math.abs(j - i + 1)];
                    String[] loopLSAux = new String[Math.abs(j - i + 1)];
                    String[] loopSAux = new String[Math.abs(j - i + 1)];

                    if (i > j) {
                        for (int k = 0; k < loopAux.length; k++) {
                            loopAux[k] = nodes.get(j + k);
                            loopLAux[k] = lLoop.get(j + k);
                            loopLSAux[k] = lSLoop.get(j + k);
                            loopSAux[k] = nodesS.get(j + k);
                        }
                    } else {
                        for (int k = 0; k < loopAux.length; k++) {
                            loopAux[k] = nodes.get(i + k);
                            loopLAux[k] = lLoop.get(i + k);
                            loopLSAux[k] = lSLoop.get(i + k);
                            loopSAux[k] = nodesS.get(i + k);
                        }
                    }
                    loop = loopAux;
                    loopL = loopLAux;
                    loopLS = loopLSAux;
                    loopS = loopSAux;

                    dataLoop = new ArrayList();
                    dataLoop.add(loop);
                    dataLoop.add(loopL);
                    dataLoop.add(loopLS);
                    dataLoop.add(loopS);

                    //return loop;
                    return dataLoop;
                }
            }
        }
        //return loop;
        return dataLoop;
    }
    /*
     private void addLoop(String[] newLoop) {

     if (fundamentalLoops.isEmpty()) {
     fundamentalLoops.add(newLoop);
     } else {
     String[] loop1 = new String[newLoop.length];
     String[] loop2 = new String[newLoop.length];
     loop1 = newLoop;

     for (int j = 0; j < newLoop.length; j++) {
     loop2[j] = newLoop[newLoop.length - 1 - j];
     }
     boolean llave1 = true;
     boolean llave2 = true;
     for (String[] loop : fundamentalLoops) {
     if (loop.length == newLoop.length) {
     int count = 0;
     for (int i = 0; i < loop1.length; i++) {
     if (loop[i].equals(loop1[i])) {
     count++;
     }
     }
     if (count == loop.length) {
     llave1 = false;
     }

     count = 0;
     for (int i = 0; i < loop2.length; i++) {
     if (loop[i].equals(loop2[i])) {
     count++;
     }
     }
     if (count == loop.length) {
     llave2 = false;
     }
     }
     }
     if (llave1 && llave2) {
     fundamentalLoops.add(newLoop);
     }
     }
     }
     */

    private void addLoop(ArrayList<String[]> newArrayListLoop) {

        String[] newLoop = newArrayListLoop.get(0);

        if (fundamentalLoops.isEmpty()) {
            //fundamentalLoops.add(newLoop);
            fundamentalLoops.add(newArrayListLoop);
        } else {
            String[] loop1 = new String[newLoop.length];
            String[] loop2 = new String[newLoop.length];
            loop1 = newLoop;

            for (int j = 0; j < newLoop.length; j++) {
                loop2[j] = newLoop[newLoop.length - 1 - j];
            }
            boolean llave1 = true;
            boolean llave2 = true;
            //for (String[] loop : fundamentalLoops) {
            for (int m = 0; m < fundamentalLoops.size(); m++) {
                String[] loop = (String[]) fundamentalLoops.get(m).get(0);
                if (loop.length == newLoop.length) {
                    int count = 0;
                    for (int i = 0; i < loop1.length; i++) {
                        if (loop[i].equals(loop1[i])) {
                            count++;
                        }
                    }
                    if (count == loop.length) {
                        llave1 = false;
                    }

                    count = 0;
                    for (int i = 0; i < loop2.length; i++) {
                        if (loop[i].equals(loop2[i])) {
                            count++;
                        }
                    }
                    if (count == loop.length) {
                        llave2 = false;
                    }
                }
            }
            if (llave1 && llave2) {
                //fundamentalLoops.add(newLoop);
                fundamentalLoops.add(newArrayListLoop);
            }
        }
    }

    private void registerLoopService() {
        // Agregando loops del agente al DF
        // Objetivo: definir los "jefes" la red 

        DFAgentDescription description = new DFAgentDescription();
        description.setName(getAID());
        ServiceDescription service = new ServiceDescription();
        service.setType("LoopService");

        String nS = "Level " + fundamentalLoops.size();

        service.setName(nS);
        description.addServices(service);
        try {
            if (fundamentalLoops.size() == 1 && !publishService) {
                publishService = true;
                if (iAmFaultAgent) {
                    service = new ServiceDescription();
                    service.setType("BlackOutPoint");
                    service.setName("WithoutEnergy");
                    description.addServices(service);
                    DFService.modify(this, description);
                } else {
                    DFService.register(this, description);
                }
            } else if (publishService && publishNumber != fundamentalLoops.size()) {
                publishNumber = fundamentalLoops.size();
                if (iAmFaultAgent) {
                    service = new ServiceDescription();
                    service.setType("BlackOutPoint");
                    service.setName("WithoutEnergy");
                    description.addServices(service);
                    DFService.modify(this, description);
                } else {
                    DFService.modify(this, description);
                }
            }
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }

    }

    private DFAgentDescription[] searchingLoopService() {
        DFAgentDescription[] resultados = null;
        DFAgentDescription description = new DFAgentDescription();
        ServiceDescription service = new ServiceDescription();
        service.setType("LoopService");
        description.addServices(service);

        try {
            // Todas las descripciones que encajan con la plantilla proporcionada en el DF
            resultados = DFService.search(this, description);

            if (resultados.length == 0) {
                System.out.println("Ningun agente ofrece el servicio deseado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultados;

    }

    private class searchServiceAvailableLinesBehaviour extends OneShotBehaviour {

        public void action() {
            try {
                Thread.sleep((int) Math.random() * 1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            searchAvailableLinesService();
            removeBehaviour(this);
        }
    }

    private void registerAvailableLines() {
        // Agregando presencia del agente al DF
        // Objetivo: formar la red de conexiones
        DFAgentDescription description = new DFAgentDescription();
        description.setName(getAID());
        ServiceDescription service = new ServiceDescription();
        service.setType("Presence");
        service.setName("Available Lines");
        description.addServices(service);

        try {
            DFService.register(this, description);
            availableLinesService = true;
        } catch (FIPAException ex) {
            availableLinesService = false;
            ex.printStackTrace();
        }
    }

    private void deregisterAvailableLines() {
        DFAgentDescription description = new DFAgentDescription();
        description.setName(getAID());
        ServiceDescription service = new ServiceDescription();
        service.setType("Presence");
        service.setName("Available Lines");
        description.addServices(service);
        try {
            DFService.deregister(this, description);
            availableLinesService = false;
            //removeBehaviour(checkingAvailableLinesService);
        } catch (FIPAException ex) {
            availableLinesService = true;
            ex.printStackTrace();
        }
    }

    private void registerBlackOutPoint() {
        // Agregando Zona de apagon al DF
        // Objetivo: encontrar agente cercano a la falla
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        DFAgentDescription description = new DFAgentDescription();
        description.setName(getAID());
        ServiceDescription service = new ServiceDescription();
        service.setType("BlackOutPoint");
        service.setName("WithoutEnergy");
        description.addServices(service);

        try {
            DFService.register(this, description);
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
    }

    private void deregisterBlackOutPoint() {
        DFAgentDescription description = new DFAgentDescription();
        description.setName(getAID());
        ServiceDescription service = new ServiceDescription();
        service.setType("BlackOutPoint");
        service.setName("WithoutEnergy");
        description.addServices(service);
        try {
            DFService.deregister(this, description);
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
    }

    private void searchAvailableLinesService() {
        DFAgentDescription description = new DFAgentDescription();
        ServiceDescription service = new ServiceDescription();
        service.setType("Presence");
        service.setName("Available Lines");
        description.addServices(service);

        try {
            // Todas las descripciones que encajan con la plantilla proporcionada en el DF
            DFAgentDescription[] resultados = DFService.search(this, description);

            if (resultados.length == 0) {
                System.out.println("Ningun agente ofrece el servicio deseado");
            }
            searchNeighbors(resultados);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DFAgentDescription[] searchWithoutEnergyService() {
        DFAgentDescription[] resultados = null;
        DFAgentDescription description = new DFAgentDescription();
        ServiceDescription service = new ServiceDescription();
        service.setType("BlackOutPoint");
        service.setName("WithoutEnergy");
        description.addServices(service);

        try {
            // Todas las descripciones que encajan con la plantilla proporcionada en el DF
            resultados = DFService.search(this, description);

            //System.out.println("Numero de resultados: " + resultados.length);
            if (resultados.length == 0) {
                System.out.println("Ningun agente ofrece el servicio deseado");
            }
            /*
             for (int i = 0; i < resultados.length; ++i) {
             //System.out.println("El agente " + resultados[i].getName() + " ofrece los siguientes servicios:");
             Iterator services = resultados[i].getAllServices();
             while (services.hasNext()) {
             service = (ServiceDescription) services.next();
             }
             }
             */
            //searchingAgentInFaultZone(resultados);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultados;
    }

    private void registerNewNeighbor(AID agentAID, String link) {
        if (!neighborAIDList.contains(agentAID)) {
            neighborAIDList.add(0, agentAID);
            for (Edge l : incidentLines) {
                if (l.getEdgeAsSimpleLine().getName().equals(link)) {
                    neighborLineList.add(0, l);
                }
            }
        }
    }

    private void deregisterNeighbor(AID agentAID) {
        int index = neighborAIDList.indexOf(agentAID);
        if (index > -1) {
            neighborAIDList.remove(index);
            neighborLineList.remove(index);
        } else {
            System.out.println("NO FUE POSIBLE REMOVER DATOS...");
        }
    }

    private void deregisterNeighbor(Edge link) {
        int index = neighborLineList.indexOf(link);
        if (index > -1) {
            neighborAIDList.remove(index);
            neighborLineList.remove(index);
        } else {
            System.out.println("NO FUE POSIBLE REMOVER DATOS...");
        }
    }

    private AID getNeighborUsingLink(Edge link) {
        int index = neighborLineList.indexOf(link);
        if (index > -1) {
            return neighborAIDList.get(index);
        } else {
            System.out.println("NO FUE POSIBLE ENCONTRAR DATOS...");
            return null;
        }
    }

    private Edge getLineUsingAID(AID aid) {
        int index = neighborAIDList.indexOf(aid);
        if (index > -1) {
            return neighborLineList.get(index);
        } else {
            System.out.println("NO FUE POSIBLE ENCONTRAR DATOS...");
            return null;
        }
    }

    private void printNeighborList() {
        System.out.println("");
        System.out.println("Listado de vecinos: ");
        System.out.print("       ");
        for (AID aid : this.neighborAIDList) {
            System.out.print(aid.getLocalName());
            System.out.print("       ");
        }
        System.out.println("");
        System.out.print("       ");
        for (Edge edge : this.neighborLineList) {
            System.out.print(edge.getEdgeAsSimpleLine().getName());
            System.out.print("       ");
        }
    }

    private void printFundamentalLoops() {
        System.out.println(getLocalName() + ": Listado de FL: ");
        System.out.print("       ");
        //for (String[] str : this.fundamentalLoops) {
        for (int m = 0; m < fundamentalLoops.size(); m++) {
            String[] str = (String[]) fundamentalLoops.get(m).get(0);
            for (int i = 0; i < str.length; i++) {
                System.out.print(str[i]);
                System.out.print("     ");
            }
            System.out.println("");
        }
    }

    private void printFinalsLoops(ArrayList<ArrayList> fLoops) {
        System.out.println(getLocalName() + ": LISTADO DE LOOPS FINALES: ");
        int count = 1;
        for (ArrayList info : fLoops) {
            System.out.println("loop " + count);
            for (String[] finalsL : (ArrayList<String[]>) info) {
                for (int i = 0; i < finalsL.length; i++) {
                    System.out.print("     " + finalsL[i] + "  ");
                }
                System.out.println("");
            }
            count++;
            System.out.println("");
        }
    }

    private void searchNeighbors(DFAgentDescription[] resultados) {
        try {
            ACLMessage msg = new ACLMessage(ACLMessage.QUERY_IF);
            msg.setProtocol(FIPANames.InteractionProtocol.FIPA_QUERY);
            msg.setLanguage(codec.getName());
            msg.setOntology(ontology.getName());
            msg.setSender(getAID());

            SearchingNeighbors sn = new SearchingNeighbors();
            Lines l = new Lines();

            incidentLines = myVertex.getVertexAsBus().getIncidentLines();
            String n = "";
            for (Edge line : incidentLines) {
                n = n + line.getEdgeAsSimpleLine().getName() + ";";
            }
            l.setLineName(n);

            sn.setLine(l);

            for (int i = 0; i < resultados.length; i++) {
                if (!resultados[i].getName().equals(getAID())) {
                    msg.addReceiver(resultados[i].getName());
                }
            }

            getContentManager().registerLanguage(codec);
            getContentManager().registerOntology(ontology);
            getContentManager().fillContent(msg, sn);

            //this.addBehaviour(new QueryInitiator(this, msg));
            queryInitiator = new QueryInitiator(this, msg);
            this.addBehaviour(queryInitiator);

        } catch (Codec.CodecException ex) {
            Logger.getLogger(RestorationAgent.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (OntologyException ex) {
            Logger.getLogger(RestorationAgent.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void reportBusInFault() {
        try {
            DFAgentDescription[] search = searchWithoutEnergyService();

            StatusNode sn = new StatusNode();
            Node n = new Node();
            sn.setNODE(n);
            ACLMessage msg = new ACLMessage(ACLMessage.QUERY_IF);
            msg.setProtocol(FIPANames.InteractionProtocol.FIPA_QUERY);
            msg.setLanguage(codec.getName());
            msg.setOntology(ontology.getName());
            msg.setSender(getAID());
            for (int i = 0; i < search.length; i++) {
                if (!search[i].getName().equals(getAID())) {
                    msg.addReceiver(search[i].getName());
                }
            }
            getContentManager().registerLanguage(codec);
            getContentManager().registerOntology(ontology);
            getContentManager().fillContent(msg, sn);

            //this.addBehaviour(new QueryInitiator(this, msg));
            queryInitiator = new QueryInitiator(this, msg);
            this.addBehaviour(queryInitiator);

        } catch (Codec.CodecException ex) {
            Logger.getLogger(RestorationAgent.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (OntologyException ex) {
            Logger.getLogger(RestorationAgent.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void searchingAgentInFaultZone(DFAgentDescription[] resultados) {
        try {
            ACLMessage msg = new ACLMessage(ACLMessage.QUERY_REF);
            msg.setProtocol(FIPANames.InteractionProtocol.FIPA_QUERY);
            msg.setLanguage(codec.getName());
            msg.setOntology(ontology.getName());
            msg.setSender(getAID());

            VoltageBeforeBlackout vbb = new VoltageBeforeBlackout();
            Node n = new Node();
            n.setBusName("nada");
            n.setVoltage(-1);
            n.setAngle(-1);
            vbb.setVBeforeBlackout(n);

            for (int i = 0; i < resultados.length; i++) {
                if (!resultados[i].getName().equals(getAID())) {
                    msg.addReceiver(resultados[i].getName());
                }
            }

            getContentManager().registerLanguage(codec);
            getContentManager().registerOntology(ontology);
            getContentManager().fillContent(msg, vbb);

            //this.addBehaviour(new QueryInitiator(this, msg));
            queryInitiator = new QueryInitiator(this, msg);
            this.addBehaviour(queryInitiator);

        } catch (Codec.CodecException ex) {
            Logger.getLogger(RestorationAgent.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (OntologyException ex) {
            Logger.getLogger(RestorationAgent.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    private class QueryInitiator extends AchieveREInitiator {

        public QueryInitiator(Agent a, ACLMessage msg) {
            super(a, msg);
        }

        protected void handleAgree(ACLMessage agree) {
            try {
                ContentElement ce = getContentManager().extractContent(agree);
                if (ce instanceof SearchingNeighbors) {
                    //System.out.println(myAgent.getAID().getName() + " : agregando como vecino a: " + agree.getSender().getName());
                    SearchingNeighbors sn = (SearchingNeighbors) ce;
                    Lines lines = sn.getLine();
                    String l = lines.getLineName();

                    registerNewNeighbor(agree.getSender(), l);

                    /*
                     System.out.println(myAgent.getAID().getLocalName() + " Mi lista es vecionos es");
                     for (AID aid : neighborAIDList) {
                     System.out.println("         " + aid.getName());
                     }
                     */
                    if (neighborAIDList.size() > 0 && neighborAIDList.size() == myVertex.getVertexAsBus().getIncidentLinesNumber()) {
                        if (availableLinesService) {
                            deregisterAvailableLines();
                            //neighborList.remove(0);
                        }
                    }
                } else if (ce instanceof StatusNode) {

                }
            } catch (Codec.CodecException ex) {
                Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
            } catch (OntologyException ex) {
                Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        protected void handleRefuse(ACLMessage refuse) {
            System.out.println(myAgent.getAID().getName() + " : el agente " + refuse.getSender().getName() + " no es mi vecino");
        }

        protected void handleNotUnderstood(ACLMessage notUnderstood) {
            //System.out.printf("%s: La operadora no entiende el mensaje.\n", this.myAgent.getLocalName(), notUnderstood.getSender().getLocalName());
        }

        protected void handleInform(ACLMessage inform) {
            System.out.println("/////////////////////////////////Informe de agente de falla");
            try {
                ContentElement ce = getContentManager().extractContent(inform);
                if (ce instanceof SearchingNeighbors) {

                } else if (ce instanceof VoltageBeforeBlackout) { //Inform-T/F
                    VoltageBeforeBlackout sn = (VoltageBeforeBlackout) ce;
                    Node n = sn.getVBeforeBlackout();
                    /*
                     if (n.getInFault()) {
                     agentInFault = inform.getSender();
                     System.out.println(myAgent.getAID().getLocalName() + "********************** El agente en falla es: " + agentInFault);
                     }
                     */
                    ArrayList reply = new ArrayList();
                    reply.add(inform.getSender().getLocalName());
                    reply.add(n);

                    informBlackOut.add(reply);

                } else if (ce instanceof StatusNode) {
                    StatusNode sn = (StatusNode) ce;
                    Node n = sn.getNODE();
                    /*
                     if (n.getInFault()) {
                     agentInFault = inform.getSender();
                     System.out.println(myAgent.getAID().getLocalName() + "********************** El agente en falla es: " + agentInFault);
                     }
                     */
                    ArrayList reply = new ArrayList();
                    reply.add(inform.getSender().getLocalName());
                    reply.add(n);

                    informOkZone.add(reply);

                }
            } catch (Codec.CodecException ex) {
                Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
            } catch (OntologyException ex) {
                Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        protected void handleFailure(ACLMessage fallo) {
            System.out.println(this.myAgent.getLocalName() + ": Se ha producido un fallo.");
        }
    }

    private class QueryResponder extends AchieveREResponder {

        public QueryResponder(Agent agente, MessageTemplate template) {
            super(agente, template);
        }

        protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
            System.out.println("Recibida la consulta...");
            System.out.println("Procesando...");
            if (request != null) {
                try {
                    ContentElement ce = getContentManager().extractContent(request);
                    if (ce instanceof SearchingNeighbors) {
                        flagSearchingNeighbors = true;
                        SearchingNeighbors sn = (SearchingNeighbors) ce;
                        Lines lines = sn.getLine();
                        String l = lines.getLineName();
                        while (!l.isEmpty()) {
                            String target = l.substring(0, l.indexOf(";"));
                            l = l.substring(l.indexOf(";") + 1);
                            //System.out.println(getLocalName() + " VECINO " + request.getSender().getLocalName() + " Y LA LINEA QUE NOS UNE " + target);

                            ArrayList<Edge> myLines = myVertex.getVertexAsBus().getIncidentLines();
                            for (Edge myLine : myLines) {
                                if (myLine.getEdgeAsSimpleLine().getName().equals(target)) {
                                    //System.out.println("Yo " + myAgent.getName() + " he encontrado a mi vecino " + request.getSender().getName());
                                    //neighborAIDList.add(request.getSender());
                                    registerNewNeighbor(request.getSender(), target);
                                    ACLMessage agree = request.createReply();
                                    agree.setPerformative(ACLMessage.AGREE);

                                    SearchingNeighbors snReply = new SearchingNeighbors();
                                    Lines linesReply = new Lines();

                                    linesReply.setLineName(target);
                                    linesReply.setIsMyLine(true);

                                    snReply.setLine(linesReply);

                                    getContentManager().registerLanguage(codec);
                                    getContentManager().registerOntology(ontology);
                                    getContentManager().fillContent(agree, snReply);

                                    /*
                                     System.out.println(myAgent.getAID().getLocalName() + " Mi lista es vecionos es");
                                     for (AID aid : neighborAIDList) {
                                     System.out.println("         " + aid.getName());
                                     }
                                     */
                                    if (neighborAIDList.size() > 0 && neighborAIDList.size() == myVertex.getVertexAsBus().getIncidentLinesNumber()) {
                                        if (availableLinesService) {
                                            deregisterAvailableLines();
                                            //neighborList.remove(0);
                                        }
                                    }
                                    return agree;
                                }
                            }
                        }
                        ACLMessage refuse = request.createReply();
                        refuse.setPerformative(ACLMessage.REFUSE);
                        return refuse;
                    } else if (ce instanceof VoltageBeforeBlackout) {
                        flagVoltageBeforeBlackout = true;
                        flagContactedPreviewVoltage = true;

                        ACLMessage agree = request.createReply();
                        agree.setPerformative(ACLMessage.AGREE);

                        VoltageBeforeBlackout vbb = new VoltageBeforeBlackout();
                        Node n = new Node();
                        n.setBusName("");
                        n.setVoltage(-1);
                        n.setAngle(-1);
                        vbb.setVBeforeBlackout(n);

                        getContentManager().registerLanguage(codec);
                        getContentManager().registerOntology(ontology);
                        getContentManager().fillContent(agree, vbb);

                        return agree;
                    } else if (ce instanceof StatusNode) {
                        flagStatusNode = true;
                        flagContactedPreviewVoltage = true;

                        ACLMessage agree = request.createReply();
                        agree.setPerformative(ACLMessage.AGREE);

                        StatusNode sn = new StatusNode();
                        Node n = new Node();
                        n.setBusName("");
                        n.setVoltage(-1);
                        n.setAngle(-1);
                        sn.setNODE(n);

                        getContentManager().registerLanguage(codec);
                        getContentManager().registerOntology(ontology);
                        getContentManager().fillContent(agree, sn);

                        return agree;
                    } else {
                        ACLMessage failure = request.createReply();
                        failure.setPerformative(ACLMessage.FAILURE);
                        return failure;
                    }
                } catch (Codec.CodecException ex) {
                    Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                } catch (OntologyException ex) {
                    Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ACLMessage refuse = request.createReply();
            refuse.setPerformative(ACLMessage.REFUSE);
            return refuse;
        }

        protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
            if (flagSearchingNeighbors) {
                ACLMessage informIf = request.createReply();
                informIf.setPerformative(ACLMessage.INFORM_IF);
                //inform.setPerformative(ACLMessage.INFORM);
                String retorno = "Agregado...";
                informIf.setContent(retorno);
                flagSearchingNeighbors = false;
                return informIf;

            } else if (flagVoltageBeforeBlackout) {
                try {
                    ACLMessage informRef = request.createReply();
                    informRef.setPerformative(ACLMessage.INFORM);
                    VoltageBeforeBlackout vbbReply = new VoltageBeforeBlackout();
                    Node n = new Node();
                    n.setBusName(myVertex.getVertexAsBus().getName());
                    n.setVoltage((float) myVertex.getVertexAsBus().getVoltageBeforeBlackout());
                    n.setAngle((float) myVertex.getVertexAsBus().getAngleBeforeBlackout());

                    flagVoltageBeforeBlackout = false;
                    vbbReply.setVBeforeBlackout(n);

                    getContentManager().registerLanguage(codec);
                    getContentManager().registerOntology(ontology);

                    getContentManager().fillContent(informRef, vbbReply);

                    countBO++;
                    return informRef;

                } catch (Codec.CodecException ex) {
                    Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                } catch (OntologyException ex) {
                    Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (flagStatusNode) {
                try {
                    ACLMessage informRef = request.createReply();
                    informRef.setPerformative(ACLMessage.INFORM);
                    StatusNode snReply = new StatusNode();
                    Node n = new Node();
                    n.setBusName(myVertex.getVertexAsBus().getName());
                    n.setVoltage((float) myVertex.getVertexAsBus().getVoltageBus());
                    n.setAngle((float) myVertex.getVertexAsBus().getAngleBus());

                    flagStatusNode = false;
                    snReply.setNODE(n);

                    getContentManager().registerLanguage(codec);
                    getContentManager().registerOntology(ontology);

                    getContentManager().fillContent(informRef, snReply);
                    countOK++;
                    return informRef;

                } catch (Codec.CodecException ex) {
                    Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                } catch (OntologyException ex) {
                    Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return null;
        }
    }

    private class RequestInitiator extends AchieveREInitiator {

        public RequestInitiator(Agent a, ACLMessage msg) {
            super(a, msg);
        }

        protected void handleAgree(ACLMessage agree) {
            System.out.println("Manejando un REQUEST por FIPA-REQUEST...");

            if (agree != null) {
                try {
                    ContentElement ce = getContentManager().extractContent(agree);
                    if (ce instanceof LoopRequest) {
                        requestCount++;
                        System.out.println(getLocalName() + ": El agente " + agree.getSender().getLocalName() + " me envia sus loops...");

                        LoopRequest lr = (LoopRequest) ce;
                        FundamentalLoops fl = lr.getLoopRequest();

                        String loopsStr = fl.getLoop();
                        String loopsLineStr = fl.getLineLoop();
                        String loopsLineStateStr = fl.getLineStateLoop();
                        String loopsStateStr = fl.getNodeState();

                        while (!loopsStr.isEmpty()) {
                            String loopAux = loopsStr.substring(0, loopsStr.indexOf("*"));
                            String loopLineAux = loopsLineStr.substring(0, loopsLineStr.indexOf("*"));
                            String loopLineStateAux = loopsLineStateStr.substring(0, loopsLineStateStr.indexOf("*"));
                            String loopStateAux = loopsStateStr.substring(0, loopsStateStr.indexOf("*"));

                            ArrayList<String[]> arrayListAux = new ArrayList();

                            arrayListAux.add(stringToArrayString(loopAux));
                            arrayListAux.add(stringToArrayString(loopLineAux));
                            arrayListAux.add(stringToArrayString(loopLineStateAux));
                            arrayListAux.add(stringToArrayString(loopStateAux));

                            //loopsRestoration.add(stringToArrayString(loopAux));
                            loopsRestoration.add(arrayListAux);

                            loopsStr = loopsStr.substring(loopsStr.indexOf("*") + 1);
                            loopsLineStr = loopsLineStr.substring(loopsLineStr.indexOf("*") + 1);
                            loopsLineStateStr = loopsLineStateStr.substring(loopsLineStateStr.indexOf("*") + 1);
                            loopsStateStr = loopsStateStr.substring(loopsStateStr.indexOf("*") + 1);
                        }

                        System.out.println(getLocalName() + ": Los loops que tengo hasta el momento son: " + loopsRestoration.size());

                    } else if (ce instanceof LoopManager) {
                    }
                } catch (Codec.CodecException ex) {
                    Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                } catch (OntologyException ex) {
                    Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        protected void handleRefuse(ACLMessage refuse) {
            System.out.println("Hospital " + refuse.getSender().getName()
                    + " responde que el accidente esta fuera de su radio de accion. No llegaremos a tiempo!!!");
        }

        protected void handleNotUnderstood(ACLMessage notUnderstood) {
            System.out.println("Hospital " + notUnderstood.getSender().getName()
                    + " no puede entender el mensaje.");
        }

        protected void handleInform(ACLMessage inform) {
            System.out.println("Hospital " + inform.getSender().getName()
                    + " informa que han atendido el accidente.");
        }

        protected void handleFailure(ACLMessage fallo) {
            if (fallo.getSender().equals(myAgent.getAMS())) {
                System.out.println("Alguna de los hospitales no existe");
            } else {
                System.out.println("Fallo en el hospital " + fallo.getSender().getName()
                        + ": " + fallo.getContent().substring(1, fallo.getContent().length() - 1));
            }
        }
    }

    private class RequestResponder extends AchieveREResponder {

        public RequestResponder(Agent a, MessageTemplate mt) {
            super(a, mt);
        }

        protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
            System.out.println("Manejando un REQUEST por FIPA-REQUEST...");

            if (request != null) {
                try {
                    ContentElement ce = getContentManager().extractContent(request);
                    if (ce instanceof LoopRequest) {
                        System.out.println(getLocalName() + ": El agente " + request.getSender().getLocalName() + " me solicita darle a conocer mis loops...");
                        ACLMessage agree = request.createReply();
                        agree.setPerformative(ACLMessage.AGREE);

                        LoopRequest lrReply = new LoopRequest();
                        FundamentalLoops flReply = new FundamentalLoops();

                        String l = "";
                        String ll = "";
                        String lls = "";
                        String ls = "";

                        //for (String[] aStr : fundamentalLoops) {
                        for (ArrayList<String[]> aStr : fundamentalLoops) {

                            l = l + arrayStringtoString(aStr.get(0)) + "*";
                            ll = ll + arrayStringtoString(aStr.get(1)) + "*";
                            lls = lls + arrayStringtoString(aStr.get(2)) + "*";
                            ls = ls + arrayStringtoString(aStr.get(3)) + "*";
                        }
                        flReply.setLoop(l);
                        flReply.setLineLoop(ll);
                        flReply.setLineStateLoop(lls);
                        flReply.setNodeState(ls);

                        lrReply.setLoopRequest(flReply);

                        getContentManager().registerLanguage(codec);
                        getContentManager().registerOntology(ontology);
                        getContentManager().fillContent(agree, lrReply);

                        return agree;

                    } else if (ce instanceof LoopManager) {
                    }

                    try {
                        Action a = (Action) myAgent.getContentManager().extractContent(request);
                        ChangeLineState cls = (ChangeLineState) a.getAction();

                        String lineToChange = cls.getLINEName();

                        for (Edge iL : incidentLines) {
                            if (iL.getEdgeAsSimpleLine().getName().equals(lineToChange)) {
                                if (cls.getLineState()) {
                                    iL.getEdgeAsSimpleLine().setSwitchStatus(11);
                                    iL.getEdgeAsSimpleLine().setEstiloLinea(true);
                                } else {
                                    iL.getEdgeAsSimpleLine().setSwitchStatus(00);
                                    iL.getEdgeAsSimpleLine().setEstiloLinea(false);
                                }
                                break;
                            }
                        }

                        ACLMessage agree = request.createReply();
                        agree.setPerformative(ACLMessage.AGREE);

                        LoopManager vbb = new LoopManager();
                        vbb.setLoopManager(null);

                        getContentManager().registerLanguage(codec);
                        getContentManager().registerOntology(ontology);

                        getContentManager().fillContent(agree, vbb);

                        return agree;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                } catch (Codec.CodecException ex) {
                    Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                } catch (OntologyException ex) {
                    Logger.getLogger(RestorationAgent.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return null;
        }

        @Override
        protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
            if (Math.random() > 0.2) {
                System.out.println("Hospital " + getLocalName() + ": Han vuelto de atender el accidente.");
                ACLMessage inform = request.createReply();
                inform.setPerformative(ACLMessage.INFORM);
                return inform;
            } else {
                System.out.println("Hospital " + getLocalName() + ": Han hecho todo lo posible, lo sentimos.");
                throw new FailureException("Han hecho todo lo posible");
            }
        }
    }

    private String[] stringToArrayString(String str) {
        ArrayList<String> al = new ArrayList();
        String auxStr = "";
        while (!str.isEmpty()) {
            auxStr = str.substring(0, str.indexOf(";"));
            al.add(auxStr);
            str = str.substring(str.indexOf(";") + 1);
        }
        String[] result = new String[al.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = al.get(i);
        }
        return result;
    }

    private String arrayStringtoString(String[] arrayStr) {
        String result = "";
        for (int i = 0; i < arrayStr.length; i++) {
            result = result + arrayStr[i] + ";";
        }
        return result;
    }

    protected void takeDown() {
        //JadeRuntime.getJadeRuntime().changeAgentType(this, "sad", myVertex);
        if (availableLinesService) {
            try {
                DFService.deregister(this);
            } catch (FIPAException fe) {
                fe.printStackTrace();
            }
        }
        System.out.println("Adios -> " + getAID().getName());
    }

    /*    
     private class DeviceAgentFSM extends FSMBehaviour {

     public DeviceAgentFSM(Agent a, Vertex vertex) {
     super(a);
     myVertex = vertex;
     }

     public void onStart() {
     registerFirstState(new OneBehaviour(), ONE_STATE);
     registerState(new TwoBehaviour(), TWO_STATE);
     registerState(new ThreeBehaviour(), THREE_STATE);
     registerState(new FourBehaviour(), FOUR_STATE);
     registerState(new FiveBehaviour(), FIVE_STATE);
     registerLastState(new ErrorBehaviour(), ERROR_STATE);

     registerTransition(ONE_STATE, ONE_STATE, 1);
     registerTransition(TWO_STATE, TWO_STATE, 2);
     registerTransition(THREE_STATE, THREE_STATE, 3);
     registerTransition(FOUR_STATE, FOUR_STATE, 4);
     registerTransition(FIVE_STATE, FIVE_STATE, 5);

     registerTransition(ONE_STATE, TWO_STATE, 2);
     registerTransition(TWO_STATE, THREE_STATE, 3);
     registerTransition(THREE_STATE, FOUR_STATE, 4);
     registerTransition(FOUR_STATE, FIVE_STATE, 5);
     registerTransition(FIVE_STATE, ONE_STATE, 1);

     registerDefaultTransition(ONE_STATE, ERROR_STATE);
     registerDefaultTransition(TWO_STATE, ERROR_STATE);
     registerDefaultTransition(THREE_STATE, ERROR_STATE);
     registerDefaultTransition(FOUR_STATE, ERROR_STATE);
     registerDefaultTransition(FIVE_STATE, ERROR_STATE);
     }

     private class OneBehaviour extends OneShotBehaviour {

     public void action() {
     System.out.println(myAgent.getAID().getLocalName() + ": Device ESTOY BLOQUEADO...");

     ACLMessage msg = myAgent.receive(templateDebug);
     if (msg != null) {
     printNeighborList();
     }

     nextState = 1;
     }

     public int onEnd() {
     block();
     return nextState;
     }
     }

     private class TwoBehaviour extends OneShotBehaviour {

     public void action() {
     //System.out.println("Al estado " + THREE_STATE);
     nextState = 3;
     }

     public int onEnd() {
     return nextState;
     }
     }

     private class ThreeBehaviour extends OneShotBehaviour {

     public void action() {
     //System.out.println("Al estado " + FOUR_STATE);
     nextState = 4;
     }

     public int onEnd() {
     return nextState;
     }
     }

     private class FourBehaviour extends OneShotBehaviour {

     public void action() {
     //System.out.println("Al estado " + FIVE_STATE);
     nextState = 5;
     }

     public int onEnd() {
     return nextState;
     }
     }

     private class FiveBehaviour extends OneShotBehaviour {

     public void action() {
     //System.out.println("Al estado " + ONE_STATE);
     nextState = 1;
     }

     public int onEnd() {
     return nextState;
     }
     }

     private class ErrorBehaviour extends OneShotBehaviour {

     public void action() {
     //System.out.println("Error!: Al estado " + ONE_STATE);
     }
     }
     }

     private class ControllerAgentFSM extends FSMBehaviour {

     public ControllerAgentFSM(Agent a, Vertex vertex) {
     super(a);
     myVertex = vertex;
     }

     public void onStart() {
     registerFirstState(new OneBehaviour(), ONE_STATE);
     registerState(new TwoBehaviour(), TWO_STATE);
     registerState(new ThreeBehaviour(), THREE_STATE);
     registerState(new FourBehaviour(), FOUR_STATE);
     registerState(new FiveBehaviour(), FIVE_STATE);
     registerLastState(new ErrorBehaviour(), ERROR_STATE);

     registerTransition(ONE_STATE, ONE_STATE, 1);
     registerTransition(TWO_STATE, TWO_STATE, 2);
     registerTransition(THREE_STATE, THREE_STATE, 3);
     registerTransition(FOUR_STATE, FOUR_STATE, 4);
     registerTransition(FIVE_STATE, FIVE_STATE, 5);

     registerTransition(ONE_STATE, TWO_STATE, 2);
     registerTransition(TWO_STATE, THREE_STATE, 3);
     registerTransition(THREE_STATE, FOUR_STATE, 4);
     registerTransition(FOUR_STATE, FIVE_STATE, 5);
     registerTransition(FIVE_STATE, ONE_STATE, 1);

     registerDefaultTransition(ONE_STATE, ERROR_STATE);
     registerDefaultTransition(TWO_STATE, ERROR_STATE);
     registerDefaultTransition(THREE_STATE, ERROR_STATE);
     registerDefaultTransition(FOUR_STATE, ERROR_STATE);
     registerDefaultTransition(FIVE_STATE, ERROR_STATE);
     }

     private class OneBehaviour extends OneShotBehaviour {

     public void action() {
     System.out.println(myAgent.getAID().getLocalName() + ": Controller ESTOY BLOQUEADO...");

     nextState = 1;
     }

     public int onEnd() {
     block();
     return nextState;
     }
     }

     private class TwoBehaviour extends OneShotBehaviour {

     public void action() {

     }

     public int onEnd() {
     block();
     return nextState;
     }
     }

     private class ThreeBehaviour extends OneShotBehaviour {

     public void action() {

     }

     public int onEnd() {
     block();
     return nextState;
     }
     }

     private class FourBehaviour extends OneShotBehaviour {

     public void action() {

     }

     public int onEnd() {
     block();
     return nextState;
     }
     }

     private class FiveBehaviour extends OneShotBehaviour {

     public void action() {

     }

     public int onEnd() {
     block();
     return nextState;
     }
     }

     private class ErrorBehaviour extends OneShotBehaviour {

     public void action() {
     //System.out.println("Error!: Al estado " + ONE_STATE);
     }
     }
     }
     */
    /*
     private class CheckingAvailableLinesService extends TickerBehaviour {

     public CheckingAvailableLinesService(Agent a, long lapse) {
     super(a, lapse);
     }

     protected void onTick() {
     if (neighborAIDList.size() > 0 && neighborAIDList.size() != myVertex.getVertexAsBus().getIncidentLinesNumber()) {
     searchServiceAvailableLines();
     }
     }
     }
     */
}
