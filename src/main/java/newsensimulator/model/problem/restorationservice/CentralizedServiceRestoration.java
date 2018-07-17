package newsensimulator.model.problem.restorationservice;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import newsensimulator.control.Controller;
import newsensimulator.model.ElectricalNetwork;
import newsensimulator.model.networkelements.*;
import newsensimulator.model.problem.AbstractProblem;
import org.jgap.InvalidConfigurationException;

/**
 *
 * @author Héctor Vargas, Rodrigo Martínez, Lisa Soto
 */
public class CentralizedServiceRestoration extends AbstractProblem implements ActionListener {

    private static CentralizedServiceRestoration instance;
    private List dataNetwork;
    public boolean state = false;
    List results;
    JFrame frame;
    JButton button1;
    JTextField text1;
    JTextField text2;
    JTextField text3;
    JTextField text4;
    JFrame frame1;
    List<Edge> faults;
    long startMeasure;
    long estimatedTime;
    List<Edge> fictEd;

    public static CentralizedServiceRestoration getInstance() {
        if (instance == null) {
            instance = new CentralizedServiceRestoration();
        }
        return instance;
    }

    @Override
    public void runOptimizationMethod() {
        setState(true);
        start();
//        buildFrame();
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean st) {
        state = st;
    }

    public void setResults(List r) {
        results = r;
    }

    public List getResults() {
        return results;
    }

    public void start() {
        startMeasure = System.currentTimeMillis();

        List<Vertex> vertexList = instance.getVertices();
        List<Vertex> vertexFault = new ArrayList();
        List<Vertex> vertexGenerator = new ArrayList();
        Vertex vertexSlack = vertexList.get(0);
        //buscar existencia de falla, barra generadora y existencia de GD
        for (Vertex vertexList1 : vertexList) {
            if (vertexList1.getVertexTypeName() == "Fault") {
                vertexFault.add(vertexList1);
            }
            if (vertexList1.getVertexTypeName() == "Generator") {
                vertexGenerator.add(vertexList1);
            }
            if (vertexList1.getVertexTypeName() == "Bus") {
                if (vertexList1.getVertexAsBus().getBusCode() == 1) {
                    vertexSlack = vertexList1;
                }
            }
        }

        if (vertexFault.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Debe ingresar al menos una falla, intente nuevamente :C");
            return;
        }

        //MALLAS FUNDAMENTALES
        // se agregan a datanetwork
//        List<Integer> MF1 = new ArrayList(Arrays.asList(1, 2, 4, 5, 17, 11, 9, 8)); //MF1 
//        List<Integer> MF2 = new ArrayList(Arrays.asList(2, 3, 8, 10, 13, 14, 18)); //MF2 
//        List<Integer> MF3 = new ArrayList(Arrays.asList(1, 3, 4, 6, 7, 19, 15, 16, 13)); //Malla externa
//        List cycles = Arrays.asList(MF1, MF2, MF3);
        List cycles = fundamentalLoops();

        //Si hay generadores se crean líneas ficticias y se agregan nuevas MF
        fictEd = new ArrayList();
        List<List> mfisolatemode = new ArrayList();
        if (vertexGenerator.size() != 0) {
            ElectricalNetwork net = instance.getElectricalNetwork();
            for (Vertex vertex : vertexGenerator) {
                if (vertex.getVertexAsGenerator().getIsolateState() == true) {
                    Collection n = net.getNeighbors(vertex);
                    List<Vertex> nodes = new ArrayList(n);
                    DijkstraShortestPath camino = new DijkstraShortestPath(net);
                    List newmf = camino.getPath(vertexSlack, nodes.get(0));
                    mfisolatemode.add(newmf);
                }
            }
           
            for (Vertex vertex : vertexGenerator) {
                if (vertex.getVertexAsGenerator().getIsolateState() == true) {
                    Collection n = net.getNeighbors(vertex);
                    List<Vertex> nodes = new ArrayList(n);
                    Edge ed = new Edge("SimpleLine", vertexSlack, nodes.get(0));
                    fictEd.add(ed);
                    Color color = new Color(1, 0, 0, 0.0f);
                    ed.getEdgeAsSimpleLine().setColorLinea(color);
                    ed.getEdgeAsSimpleLine().setResistance(0);
                    net.addEdge(ed, vertexSlack, nodes.get(0));
                }
            }
        }
        for (int j = 0; j < mfisolatemode.size(); j++) {
            List mf = mfisolatemode.get(j);
            for (int i = 0; i < mf.size(); i++) {
                Edge edg = (Edge) mf.get(i);
                int linenumber = Integer.valueOf((String) edg.getEdgeAsSimpleLine().getName().substring(1));
                mf.set(i, linenumber);
            }
            Edge ed2 = fictEd.get(j);
            int line = Integer.valueOf((String) ed2.getEdgeAsSimpleLine().getName().substring(1));
            mf.add(line);
        }
        cycles.addAll(mfisolatemode);

        dataNetwork = instance.getData();
        dataNetwork.add(cycles);
        List<Edge> edgeList = instance.getEdges();
        //set switches para civanlar
        List<Integer> switchs = new ArrayList(Arrays.asList(3, 3, 3, 22, 333, 3, 3, 222, 2, 2, 222, 1, 3, 3, 3, 2, 222, 222, 222, 222, 222));

        //set switches para baran
//        List<Integer> switchs = new ArrayList(Arrays.asList(1, 333, 2, 0, 1, 3, 3, 3, 333, 2, 2, 2, 222, 2, 333, 2, 2, 3, 2, 2, 2, 333, 2, 0, 3, 0, 2, 333, 333, 2, 2, 2, 333, 333, 33, 333, 2,222,222,222));
//        List<Integer> switchs = new ArrayList(Arrays.asList(1, 333, 2, 0, 0, 3, 3, 3, 333, 2, 2, 2, 222, 2, 333, 2, 2, 3, 2, 2, 2, 333, 2, 0, 3, 0, 2, 333, 333, 2, 2, 2, 333, 333, 33, 333, 2, 222, 222, 222));

        //IDENTIFICACIÓN DE CONDICIONES INICIALES
        //se agregan a datanetwork
        List initialCondition = new ArrayList();
        for (Edge edgeList1 : edgeList) {
            String name = edgeList1.getEdgeAsSimpleLine().getName();
            int linenumber = Integer.valueOf((String) name.substring(1));
            edgeList1.getEdgeAsSimpleLine().setSwitchCode(switchs.get(linenumber - 1));
            if (edgeList1.getEdgeAsSimpleLine().getEstiloLinea() == false && edgeList1.getEdgeAsSimpleLine().getInFault() == false) {
                initialCondition.add(linenumber);
                System.out.println(Integer.valueOf((String) name.substring(1)));
            }
        }

//        int mf = edgeList.size() - vertexList.size() + vertexFault.size() + 1;
//        if (initialCondition.size() == mf) {
        dataNetwork.add(initialCondition);
//        JOptionPane.showMessageDialog(frame, "TODO OK");
//        } else {
//            JOptionPane.showMessageDialog(frame, "Debe abrir al menos " + mf + " lineas");        
//        }

        //IDENTIFICACION DE UBICACIÓN DE FALLA E INICIO DE ALGORITMO
        faults = new ArrayList();
        if (!vertexFault.isEmpty()) {
//            JOptionPane.showMessageDialog(frame, "Hay al menos una falla :)");
            for (Vertex vertexFault1 : vertexFault) {
                faults.add(vertexFault1.getVertexAsFault().getLocation());
            }
            List openlines = runIsolate(faults, dataNetwork);
            try {
                runGeneticAlgorithm(openlines, dataNetwork);
            } catch (InvalidConfigurationException ex) {
                Logger.getLogger(CentralizedServiceRestoration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List runIsolate(List faults, List dataNetwork) {
        long startMeasure = System.nanoTime();
        List noSwitch = noSwitch();
        List openLines = new ArrayList();
        for (int i = 0; i < faults.size(); i++) {
            Edge line = (Edge) faults.get(i);
            IsolateFault fault = new IsolateFault(line);
            openLines.addAll(fault.evaluateSwitch(line));
            NewFundamentalLoops fl = new NewFundamentalLoops((List) dataNetwork.get(2), openLines, noSwitch);
            dataNetwork.set(2, fl.runFL());
        }

        long estimatedTime = System.nanoTime() - startMeasure;
        System.out.println("Para aislar la falla es necesario quitar las líneas: " + openLines);
        List openswitch = new ArrayList(openLines);
        openswitch.removeAll(noSwitch);
        System.out.println("Para aislar la falla es necesario abrir los EPM ubicados en las líneas: " + openswitch);
        System.out.println("Tiempo: " + estimatedTime + " nanosegundos");
        List initialopen = new ArrayList((List) dataNetwork.get(3));

        initialopen.retainAll(openswitch);
        openswitch.removeAll(initialopen);

        for (Object openLine : openswitch) {
            boolean flag = true;
            String linea = "L" + String.valueOf((int) openLine);
            for (Object fail : faults) {
                Edge line = (Edge) fail;
                flag = flag && !linea.equals(line.getEdgeAsSimpleLine().getName());
            }
            if (flag == true) {
                CentralizedServiceRestoration.setSimpleLineStatus(linea, false);
            }
        }

        return openLines;
    }

    public void runGeneticAlgorithm(List openLines, List dataNetwork) throws InvalidConfigurationException {
        System.out.println("Iniciando algoritmo genético...");
        GeneticAlgorithm ag = new GeneticAlgorithm();
        List parametros = saveParameter();
        ag.setEvolutions((int) parametros.get(0));
        ag.setPopulation((int) parametros.get(1));
        ag.setCrossoverPercent((int) parametros.get(2));
        ag.setMutationPercent((int) parametros.get(3));

        List results = ag.runAG(openLines, dataNetwork);
        results.add(openLines);
        results.add(faults.get(0).getEdgeAsSimpleLine().getName());
        results.add(parametros.get(0));
        results.add(parametros.get(1));
        results.add(parametros.get(2));
        results.add(parametros.get(3));

        //0 lineas a abrir, 1 lineas a cerrar, 2 perdida óptimo, 3 lista con todas las pérdidas, 4 epms, 5 linea falla, 6 evoluciones, 7 poblacion     
        List open = (List) results.get(0);
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            
//            return;
//        }

        for (int j = 0; j < open.size(); j++) {
            CentralizedServiceRestoration.setSimpleLineStatus("L" + String.valueOf((int) open.get(j)), false);
            Edge edge = CentralizedServiceRestoration.getEdgeByName("L" + String.valueOf((int) open.get(j)));
            if (!fictEd.contains(edge)) {
                float dash[] = {5.0f};
                edge.getEdgeAsSimpleLine().setColorLinea(Color.cyan);
                edge.getEdgeAsSimpleLine().setEstiloStroke(new BasicStroke(4.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 1.0f));
            } else {
                edge.getEdgeAsSimpleLine().setColorLinea(Color.white);
            }
        }
        List close = (List) results.get(1);
        for (int i = 0; i < close.size(); i++) {
            float dash[] = {5.0f};
            CentralizedServiceRestoration.setSimpleLineStatus("L" + String.valueOf((int) close.get(i)), true);
            Edge edge = CentralizedServiceRestoration.getEdgeByName("L" + String.valueOf((int) close.get(i)));
            edge.getEdgeAsSimpleLine().setColorLinea(Color.green);
            edge.getEdgeAsSimpleLine().setEstiloStroke(new BasicStroke(7.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 1.0f));
        }

        estimatedTime = System.currentTimeMillis() - startMeasure;
        System.out.println("Tiempo Total de Simulación: " + estimatedTime + " milisegundos");

        String loss = String.format("%.4f", (double) results.get(2));
//        Object[] options = {"Ok", "Ver más"};
//        int n = JOptionPane.showOptionDialog(frame, "Se ha restaurado el servicio obteniendo pérdidas de: " + loss + " [MW]", "Solución", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        String msg = "Se ha restaurado el servicio obteniendo pérdidas de: " + loss + " [MW]";
        Controller.getController().SENSimulatorConsolePrint(msg);
        setResults(results);
//        if (n == JOptionPane.NO_OPTION) {
//            ResultsPanel showresults = new ResultsPanel(results);
//            showresults.runresults();
//        }

    }

    public void returnInitialState() {
        ElectricalNetwork currentNetwork = ElectricalNetwork.getElectricalNetwork();
        if (fictEd.size() != 0) {
            for (Edge e : fictEd) {
                currentNetwork.deleteLine(e);
            }
        }
        Collection<Vertex> vertices = currentNetwork.getVertices();
        boolean hayfalla = false;
        for (Vertex vertexList1 : vertices) {
            if (vertexList1.getVertexTypeName() == "Fault") {
                hayfalla = true;
            }
        }
        if (!hayfalla) {
            Collection<Edge> edgeList = currentNetwork.getEdges();
            for (Edge edgeList1 : edgeList) {
                if (edgeList1.getEdgeAsSimpleLine().getEstiloLinea() == false) {
                    CentralizedServiceRestoration.setSimpleLineStatus(edgeList1.getEdgeName(), true);
                }
            }
            List initialcondition = (List) dataNetwork.get(3);
            for (int j = 0; j < initialcondition.size(); j++) {
                CentralizedServiceRestoration.setSimpleLineStatus("L" + String.valueOf((int) initialcondition.get(j)), false);
            }
        }
    }

    public List fundamentalLoops() {

        List datas = getData();
        double[][] databus = (double[][]) datas.get(0);
        double[][] dataline = (double[][]) datas.get(1);
        String nodes[] = new String[databus.length];
        boolean adjMatrix[][] = new boolean[databus.length][databus.length];
        for (int i = 0; i < databus.length; i++) {
            nodes[i] = "Node " + i;
        }
        for (int j = 0; j < dataline.length; j++) {
            int a = (int) dataline[j][0];
            int b = (int) dataline[j][1];
            adjMatrix[a - 1][b - 1] = true;
            adjMatrix[b - 1][a - 1] = true;
        }
        ElementaryCyclesSearch ecs = new ElementaryCyclesSearch(adjMatrix, nodes);
        List cycles = ecs.getElementaryCycles();
//        System.out.println(cycles);
        List<List> loops = new ArrayList();
        for (int i = 0; i < cycles.size(); i++) {
            List cycle = (List) cycles.get(i);
            List list = new ArrayList();
            for (Object obj : cycle) {
                if (cycle.size() > 2) {
                    list.add(Integer.valueOf((String) obj.toString().substring(5)));
                }
            }
            loops.add(list);
        }
        Iterator<List> it = loops.iterator();
        while (it.hasNext()) {
            int size = it.next().size();
            if (size == 0) {
                it.remove();
            }
        }
        List poligonos = getPolygons(loops);
        List<List> deleteLoops = deletecontains(poligonos, loops);
        loops.removeAll(deleteLoops);
//hasta aqui los loops estan en función de los nodos
        ElectricalNetwork network = instance.getElectricalNetwork();
        List fundamentalloops = new ArrayList();
        for (int j = 0; j < loops.size(); j++) {
            List target = (List) loops.get(j);
            List lines = new ArrayList();
            for (int i = 0; i < target.size(); i++) {
                if (i < target.size() - 1) {
                    int vert1 = (int) target.get(i) + 1;
                    int vert2 = (int) target.get(i + 1) + 1;
                    String bus1 = "B" + vert1;
                    String bus2 = "B" + vert2;
                    Vertex origin = instance.getVertexByName(bus1);
                    Vertex destin = instance.getVertexByName(bus2);
                    Edge ed = network.findEdge(origin, destin);
                    String name = ed.getEdgeAsSimpleLine().getName();
                    lines.add(Integer.valueOf((String) name.substring(1)));
                } else {
                    int vert1 = (int) target.get(i) + 1;
                    int vert2 = (int) target.get(0) + 1;
                    String bus1 = "B" + vert1;
                    String bus2 = "B" + vert2;
                    Vertex origin = instance.getVertexByName(bus1);
                    Vertex destin = instance.getVertexByName(bus2);
                    Edge ed = network.findEdge(origin, destin);
                    String name = ed.getEdgeAsSimpleLine().getName();
                    lines.add(Integer.valueOf((String) name.substring(1)));
                }
            }
            fundamentalloops.add(lines);
        }
        System.out.println(fundamentalloops);
        return fundamentalloops;
    }

    public List getPolygons(List<List> loops) {
        Controller control = Controller.getController();
        List poligonos = new ArrayList();
        for (List loop : loops) {
            Polygon poli = new Polygon();
            for (int i = 0; i < loop.size(); i++) {
//                Point2D punto = (Point2D) points.get((int) loop.get(i));
//                poli.addPoint((int) punto.getX(), (int) punto.getY());
                int vert = (int) loop.get(i) + 1;
                String bus = "B" + vert;
                Vertex vertice = instance.getVertexByName(bus);
                Point2D point = control.getVertexPoint(vertice);
                poli.addPoint((int) point.getX(), (int) point.getY());
            }
            poligonos.add(poli);
        }

        return poligonos;
    }

    public List deletecontains(List poligonos, List loops) {
        List deleteloops = new ArrayList();
        for (int k = 0; k < poligonos.size(); k++) {
            Area pol1 = new Area((Polygon) poligonos.get(k));
            for (int i = k + 1; i < poligonos.size(); i++) {
                Area pol2 = new Area((Polygon) poligonos.get(i));
                Area test = (Area) pol2.clone();
                test.intersect(pol1);
                if (!test.isEmpty()) {

                    if (test.equals(pol2)) {
                        deleteloops.add(loops.get(k));
//                        poligonos.remove(k);
//                        System.out.println("poligono " + k + " se elimina");
                    } else if (test.equals(pol1)) {
                        deleteloops.add(loops.get(i));
//                        poligonos.remove(i);
//                        System.out.println("poligon" + i + "se elimina");
                    }
                }
            }
        }

        return deleteloops;
    }

    public List noSwitch() {
        List<Edge> edges = instance.getEdges();
        List noSwitch = new ArrayList();
        for (Edge edge : edges) {
            if (edge.getEdgeAsSimpleLine().getSwitchCode() == 0) {
                int number = Integer.valueOf((String) edge.getEdgeAsSimpleLine().getName().substring(1));
                noSwitch.add(number);
            }
        }
        return noSwitch;
    }

    public void buildFrame() {
        frame1 = new JFrame("Configuración");
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.setSize(300, 270);
        frame1.setResizable(false);
        button1 = new JButton("OK");
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        TitledBorder title = BorderFactory.createTitledBorder(loweredetched, "Parámetros Algoritmo Genético");
        JPanel mainPanel = new JPanel();
        JPanel mainPanel1 = new JPanel();
        text1 = new JTextField();
        text2 = new JTextField();
        text3 = new JTextField();
        text4 = new JTextField();
        text1.setBounds(new Rectangle(160, 30, 70, 28));
        text2.setBounds(new Rectangle(160, 60, 70, 28));
        text3.setBounds(new Rectangle(160, 90, 70, 28));
        text4.setBounds(new Rectangle(160, 120, 70, 28));
        JLabel area1 = new JLabel("Cantidad de Evoluciones:");
        JLabel area2 = new JLabel("Tamaño de la Población:");
        JLabel area3 = new JLabel("Porcentaje de Cruza:");
        JLabel area4 = new JLabel("Porcentaje de Mutación:");
        area1.setBounds(10, 30, 150, 30);
        area2.setBounds(10, 60, 150, 30);
        area3.setBounds(10, 90, 150, 30);
        area4.setBounds(10, 120, 150, 30);
        button1.setBounds(100, 180, 100, 40);
        mainPanel.setLayout(null);
        mainPanel1.setLayout(null);
        button1.addActionListener(this);
        mainPanel1.setBorder(title);
        mainPanel1.setBounds(20, 20, 260, 180);
        mainPanel1.setBorder(title);
        mainPanel1.add(area1);
        mainPanel1.add(area2);
        mainPanel1.add(area3);
        mainPanel1.add(area4);
        mainPanel.add(button1);
        mainPanel1.add(text1);
        mainPanel1.add(text2);
        mainPanel1.add(text3);
        mainPanel1.add(text4);
        frame1.add(mainPanel1);
        frame1.add(mainPanel);
        frame1.setVisible(true);
        frame1.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent event) {
        frame1.dispose();
    }

    public List saveParameter() {
        int evol = Integer.parseInt(text1.getText());
        int pop = Integer.parseInt(text2.getText());
        int cross = Integer.parseInt(text3.getText());
        int mutation = Integer.parseInt(text4.getText());
        List parameter = new ArrayList();
        parameter.add(evol);
        parameter.add(pop);
        parameter.add(cross);
        parameter.add(mutation);
        return parameter;
    }

    private List getData() {
        List data = new ArrayList();
        ElectricalNetwork network = instance.getElectricalNetwork();
        Collection<Vertex> vertices = network.getVertices();
        List<Vertex> vertexList = new ArrayList();
        vertexList.addAll(vertices);
        Iterator<Vertex> it = vertexList.iterator();
        while (it.hasNext()) {
            Vertex v = it.next();
            if (v.getVertexTypeName() != "Bus") {
                it.remove();
            }
        }
        Collections.sort(vertexList, (Object o1, Object o2) -> {
            Vertex v1 = (Vertex) o1;
            Vertex v2 = (Vertex) o2;
            int a = Integer.parseInt(v1.getVertexName().substring(1));
            int b = Integer.parseInt(v2.getVertexName().substring(1));
            return a - b;
        });

        double[][] dataBus = new double[vertexList.size()][11];
        int i = 0;
        for (Vertex v : vertexList) {
            dataBus[i][0] = Integer.valueOf(v.getVertexAsBus().getName().substring(1));
            dataBus[i][1] = (double) v.getVertexAsBus().getBusCode();
            dataBus[i][2] = 1;
            dataBus[i][3] = v.getVertexAsBus().getAngleBus();
            double loadMW = 0;
            double loadMVar = 0;
            double genMW = 0;
            double genMVar = 0;
            double minGenMVar = 0;
            double maxGenMVar = 0;
            double shuntCap = 0;

            for (Vertex vrtx : network.getNeighbors(v)) {
                switch (vrtx.getVertexType()) {
                    // Vertex de carga
                    case 2:
                        loadMW = loadMW + vrtx.getVertexAsLoad().getLoadMW();
                        loadMVar = loadMVar + vrtx.getVertexAsLoad().getLoadMVar();
                        break;
                    // Vertex de generacion
                    case 3:
                        genMW = genMW + vrtx.getVertexAsGenerator().getMWGenerator();
                        genMVar = genMVar + vrtx.getVertexAsGenerator().getMVarGenerator();
                        minGenMVar = minGenMVar + vrtx.getVertexAsGenerator().getMinMVarGenerator();
                        maxGenMVar = maxGenMVar + vrtx.getVertexAsGenerator().getMaxMVarGenerator();
                        break;
                    // Vertex de EV
                    case 4:
                        break;
                }
            }
            dataBus[i][4] = loadMW;
            dataBus[i][5] = loadMVar;
            dataBus[i][6] = genMW;
            dataBus[i][7] = genMVar;
            dataBus[i][8] = minGenMVar;
            dataBus[i][9] = maxGenMVar;
            dataBus[i][10] = shuntCap;
            i++;
        }
//        for (int j = 0; j < dataBus[0].length; j++) {
//            for (int k = 0; k < dataBus.length; k++) {
//                System.out.print(dataBus[k][j] + " ");
//            }
//            System.out.println("++");
//        }

        data.add(dataBus);

        Collection<Edge> edges = network.getEdges();
        List<Edge> edgeList = new ArrayList();
        edgeList.addAll(edges);
        Iterator<Edge> it2 = edgeList.iterator();
        while (it2.hasNext()) {
            Edge ed = it2.next();
            if (ed.getEdgeAsSimpleLine().getOrigen().getVertexTypeName() != "Bus" || ed.getEdgeAsSimpleLine().getDestino().getVertexTypeName() != "Bus") {
                it2.remove();
            }
        }
        Collections.sort(edgeList, (Object o1, Object o2) -> {
            Edge e1 = (Edge) o1;
            Edge e2 = (Edge) o2;
            int a = Integer.parseInt(e1.getEdgeAsSimpleLine().getName().substring(1));
            int b = Integer.parseInt(e2.getEdgeAsSimpleLine().getName().substring(1));
            return a - b;
        });

        double[][] dataLine = new double[edgeList.size()][6];
        i = 0;
        for (Edge line : edgeList) {
            dataLine[i][0] = (double) line.getEdgeAsSimpleLine().getOrigen().getVertexAsBus().getNumberBus();
            dataLine[i][1] = (double) line.getEdgeAsSimpleLine().getDestino().getVertexAsBus().getNumberBus();
            dataLine[i][2] = line.getEdgeAsSimpleLine().getResistance();
            dataLine[i][3] = line.getEdgeAsSimpleLine().getReactance();
            dataLine[i][4] = 0.0;
            dataLine[i][5] = 1.0;
            i++;
        }

//        for (int j = 0; j < dataLine[0].length; j++) {
//            for (int k = 0; k < dataLine.length; k++) {
//                System.out.print(dataLine[k][j]);
//            }
//            System.out.println(" ");
//        }
        data.add(dataLine);

        return data;
    }

}
