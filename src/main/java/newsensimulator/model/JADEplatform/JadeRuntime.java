package newsensimulator.model.JADEplatform;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.*;

/**
 *
 * @author Jose Muñoz Parra
 */
public class JadeRuntime {

    private AgentContainer mc = null;
    private AgentContainer ac = null;
    private AgentController aControl;
    private static JadeRuntime jadeRuntime;

    public static JadeRuntime getJadeRuntime() {
        if (jadeRuntime == null) {
            jadeRuntime = new JadeRuntime();
        }
        return jadeRuntime;
    }
    
    public static boolean isActive(){
        if(jadeRuntime != null){
            return true;
        }
        else{
            return false;
        }
    }
            
    public JadeRuntime() {
        try {
            // Get a hold on JADE runtime
            Runtime rt = Runtime.instance();

            // Exit the JVM when there are no more containers around
            rt.setCloseVM(true);

            // Launch a complete platform on the 8888 port
            // create a default Profile
            Profile pMain = new ProfileImpl(null, 8888, null);

            System.out.println("Launching a whole in-process platform..." + pMain);
            mc = rt.createMainContainer(pMain);

            //System.out.println("Launching the rma agent on the main container ...");
            //crearAgente("rma", "jade.tools.rma.rma");
            //creando un nuevo container, donde se almacenaran nuestros agentes
            Profile pContainer = new ProfileImpl();
            pContainer.setParameter(Profile.LOCAL_HOST, null);
            pContainer.setParameter(Profile.LOCAL_PORT, "8888");
            pContainer.setParameter(Profile.CONTAINER_NAME, "Agent-Container");

            ac = rt.createAgentContainer(pContainer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startRMA() {
        startAgentSystem("rma", "jade.tools.rma.rma");
    }

    public void startDummyAgent() {
        startAgentSystem("dummy", "jade.tools.DummyAgent.DummyAgent");
    }

    public void startSnifferAgent() {
        startAgentSystem("sniffer", "jade.tools.sniffer.Sniffer");
    }

    public void startInstrospectorAgent() {
        startAgentSystem("instrospector", "jade.tools.introspector.Introspector");
    }
    
    public void killRMA() {
        killAgentSystem("rma");
    }

    public void killDummyAgent() {
        killAgentSystem("dummy");
    }

    public void killSnifferAgent() {
        killAgentSystem("sniffer");
    }

    public void killInstrospectorAgent() {
        killAgentSystem("instrospector");
    }

    public void startAgent(String agentName, String agentClass, Object[] args) {
        try {
            aControl = ac.createNewAgent(agentName, agentClass, args);
            aControl.start();
            System.out.println("Agente propio creado...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void killAgent(String localAgentName) {
        try {
            aControl = ac.getAgent(localAgentName);
            //aControl.suspend();
            System.out.println("El que morira: "+aControl.getName());
            aControl.kill();
            System.out.println("Muerto el agente: " + localAgentName);
        } catch (ControllerException e) {
            System.out.println(e.getMessage());
        }
    }

    // Esta clase se utiliza para cambiar de un tipo de barra, cambiando 
    // la clase del agente
    /*
    public void changeAgentType(String nameOldAgent, String typeAgent, Vertex vertex) {
        //String name = oldAgent.getAID().getName();
        //name = name.substring(0, name.indexOf("@"));
        System.out.println("Nombre del que va a morir: " + nameOldAgent);
        //String type = oldAgent.getClass().getSimpleName();
        this.killAgent(nameOldAgent);
        Object[] args = new Object[1];
        args[0] = vertex;
        System.out.println("agente muerto...");

        try {
            Thread.sleep(300);
        } catch (InterruptedException ex) {
        }
        
        switch (typeAgent) {
            case "ControllerAgent":
                startAgent(vertex.getVertexAsBus().getName(), "newsensimulator.model.agents.ControllerAgent", args);
                break;
            case "DeviceAgent":
                startAgent(vertex.getVertexAsBus().getName(), "newsensimulator.model.agents.DeviceAgent", args);
                break;
            default:
                break;
        }
    }
*/
    private void killAgentSystem(String agentSystem) {

        try {
            aControl = mc.getAgent(agentSystem);
            aControl.kill();
        } catch (ControllerException e) {
            System.out.println(e.getMessage());
        }

    }

    private void startAgentSystem(String agentName, String agentClass) {
        try {
            aControl = mc.createNewAgent(agentName, agentClass, new Object[0]);
            aControl.start();

            System.out.println("Agente del sistema creado...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
