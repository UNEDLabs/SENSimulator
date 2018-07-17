package newsensimulator.model.fileIO;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.List;
import newsensimulator.control.Controller;
import newsensimulator.model.ElectricalNetwork;
import newsensimulator.model.networkelements.*;
import newsensimulator.view.AreaSimulacion;
import org.apache.commons.math3.complex.*;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

/**
 *
 * @author Jose Muñoz Parra
 */
public class FileIO {
    
    
    private static FileIO fileIO;
    
     public static FileIO getFileIO() {
        if (fileIO == null) {
            fileIO = new FileIO();
        }
        return fileIO;
    }

    public FileIO() {
        Controller.getController().SENSimulatorConsolePrint("Launching instance: FileIO.java");
    }

    public void writeXMLFile(String filename) {

        try {
            Element model = new Element("NetworkModel");
            Document doc = new Document();
            doc.setRootElement(model);

            Element vertexElement = new Element("Vertex");

            for (Vertex v : ElectricalNetwork.getElectricalNetwork().getVertices()) {

                switch (v.getVertexTypeName()) {
                    case "Bus":
                        Element bus = new Element("Bus");
                        bus.setAttribute(new Attribute("name", v.getVertexAsBus().getName()));
                        bus.setAttribute(new Attribute("vertexType", v.getVertexTypeName()));

                        Point2D busPosition = new Point2D.Double();
                        busPosition.setLocation(
                                AreaSimulacion.getAreaSimulacion().getGraphLayout().transform(v).getX(),
                                AreaSimulacion.getAreaSimulacion().getGraphLayout().transform(v).getY());

                        bus.addContent(new Element("posX").setText(String.valueOf(busPosition.getX())));
                        bus.addContent(new Element("posY").setText(String.valueOf(busPosition.getY())));

                        bus.addContent(new Element("busNumber").setText(String.valueOf(v.getVertexAsBus().getNumberBus())));
                        bus.addContent(new Element("busCode").setText(String.valueOf(v.getVertexAsBus().getBusCode())));
                        bus.addContent(new Element("busVoltage").setText(String.valueOf(v.getVertexAsBus().getVoltageBus())));
                        bus.addContent(new Element("busAngle").setText(String.valueOf(v.getVertexAsBus().getAngleBus())));

                        ComplexFormat cf = new ComplexFormat();
                        String complexVoltage = cf.format(v.getVertexAsBus().getComplexVoltage());

                        bus.addContent(new Element("busComplexVoltageValue").setText(complexVoltage));
                        bus.addContent(new Element("busIsolatedStatus").setText(String.valueOf(v.getVertexAsBus().getIsolatedStatus())));
                        bus.addContent(new Element("busTFPresent").setText(String.valueOf(v.getVertexAsBus().isTFPresent())));
                        bus.addContent(new Element("busIncidentLinesNumber").setText(String.valueOf(v.getVertexAsBus().getIncidentLinesNumber())));

                        vertexElement.addContent(bus);
                        //doc.getRootElement().addContent(node);
                        break;
                    case "Fault":
                        break;
                    case "Load":
                        Element load = new Element("Load");
                        load.setAttribute(new Attribute("name", v.getVertexAsLoad().getName()));
                        load.setAttribute(new Attribute("vertexType", v.getVertexTypeName()));

                        Point2D loadPosition = new Point2D.Double();
                        loadPosition.setLocation(
                                AreaSimulacion.getAreaSimulacion().getGraphLayout().transform(v).getX(),
                                AreaSimulacion.getAreaSimulacion().getGraphLayout().transform(v).getY());

                        load.addContent(new Element("posX").setText(String.valueOf(loadPosition.getX())));
                        load.addContent(new Element("posY").setText(String.valueOf(loadPosition.getY())));

                        load.addContent(new Element("loadNumber").setText(String.valueOf(v.getVertexAsLoad().getLoadNumber())));
                        load.addContent(new Element("loadType").setText(String.valueOf(v.getVertexAsLoad().getLoadType())));
                        load.addContent(new Element("loadPriority").setText(String.valueOf(v.getVertexAsLoad().getLoadPriority())));
                        load.addContent(new Element("loadMW").setText(String.valueOf(v.getVertexAsLoad().getLoadMW())));
                        load.addContent(new Element("loadMVar").setText(String.valueOf(v.getVertexAsLoad().getLoadMVar())));
                        load.addContent(new Element("loadState").setText(String.valueOf(v.getVertexAsLoad().getLoadState())));

                        vertexElement.addContent(load);

                        break;
                    case "Generator":
                        Element generator = new Element("Generator");
                        generator.setAttribute(new Attribute("name", v.getVertexAsGenerator().getName()));
                        generator.setAttribute(new Attribute("vertexType", v.getVertexTypeName()));

                        Point2D generatorPosition = new Point2D.Double();
                        generatorPosition.setLocation(
                                AreaSimulacion.getAreaSimulacion().getGraphLayout().transform(v).getX(),
                                AreaSimulacion.getAreaSimulacion().getGraphLayout().transform(v).getY());

                        generator.addContent(new Element("posX").setText(String.valueOf(generatorPosition.getX())));
                        generator.addContent(new Element("posY").setText(String.valueOf(generatorPosition.getY())));

                        generator.addContent(new Element("generatorNumber").setText(String.valueOf(v.getVertexAsGenerator().getGeneratorNumber())));
                        generator.addContent(new Element("generatorType").setText(String.valueOf(v.getVertexAsGenerator().getGeneratorType())));
                        generator.addContent(new Element("generatorState").setText(String.valueOf(v.getVertexAsGenerator().getGeneratorState())));
                        generator.addContent(new Element("generatorMW").setText(String.valueOf(v.getVertexAsGenerator().getMWGenerator())));
                        generator.addContent(new Element("generatorMVar").setText(String.valueOf(v.getVertexAsGenerator().getMVarGenerator())));
                        generator.addContent(new Element("generatorMinMW").setText(String.valueOf(v.getVertexAsGenerator().getMinMVarGenerator())));
                        generator.addContent(new Element("generatorMaxMW").setText(String.valueOf(v.getVertexAsGenerator().getMaxMVarGenerator())));

                        vertexElement.addContent(generator);

                        break;
                    case "ElectricVehicle":
                        Element electricVehicle = new Element("ElectricVehicle");
                        electricVehicle.setAttribute(new Attribute("name", v.getVertexAsElectricVehicle().getName()));
                        electricVehicle.setAttribute(new Attribute("vertexType", v.getVertexTypeName()));

                        Point2D electricVehiclePosition = new Point2D.Double();
                        electricVehiclePosition.setLocation(
                                AreaSimulacion.getAreaSimulacion().getGraphLayout().transform(v).getX(),
                                AreaSimulacion.getAreaSimulacion().getGraphLayout().transform(v).getY());

                        electricVehicle.addContent(new Element("posX").setText(String.valueOf(electricVehiclePosition.getX())));
                        electricVehicle.addContent(new Element("posY").setText(String.valueOf(electricVehiclePosition.getY())));

                        electricVehicle.addContent(new Element("electricVehicleNumber").setText(String.valueOf(v.getVertexAsElectricVehicle().getElectricVehicleNumber())));
                        electricVehicle.addContent(new Element("electricVehicleType").setText(String.valueOf(v.getVertexAsElectricVehicle().getElectricVehicleType())));
                        electricVehicle.addContent(new Element("electricVehicleState").setText(String.valueOf(v.getVertexAsElectricVehicle().getElectricVehicleState())));

                        vertexElement.addContent(electricVehicle);
                        break;
                    case "TFLocationNode":
                        break;
                    default:
                        System.out.println("Un detalle por aqui!!! :(");
                        break;
                }
            }

            Element edgeElement = new Element("Edge");

            for (Edge e : ElectricalNetwork.getElectricalNetwork().getEdges()) {

                switch (e.getEdgeTypeName()) {
                    case "SimpleLine":
                        Element simpleLine = new Element("SimpleLine");
                        simpleLine.setAttribute(new Attribute("name", e.getEdgeAsSimpleLine().getName()));
                        simpleLine.setAttribute(new Attribute("lineType", e.getEdgeTypeName()));

                        simpleLine.addContent(new Element("source").setText(e.getEdgeAsSimpleLine().getOrigen().getVertexName()));
                        simpleLine.addContent(new Element("target").setText(e.getEdgeAsSimpleLine().getDestino().getVertexName()));
                        simpleLine.addContent(new Element("lineNumber").setText(String.valueOf(e.getEdgeAsSimpleLine().getLineNumber())));

                        simpleLine.addContent(new Element("lineResistance").setText(String.valueOf(e.getEdgeAsSimpleLine().getResistance())));
                        simpleLine.addContent(new Element("lineReactance").setText(String.valueOf(e.getEdgeAsSimpleLine().getReactance())));
                        simpleLine.addContent(new Element("lineMaxCurrent").setText(String.valueOf(e.getEdgeAsSimpleLine().getMaxCurrent())));
                        simpleLine.addContent(new Element("lineMagCurrent").setText(String.valueOf(e.getEdgeAsSimpleLine().getMagCurrent())));
                        simpleLine.addContent(new Element("lineSwitchCode").setText(String.valueOf(e.getEdgeAsSimpleLine().getSwitchCode())));
                        simpleLine.addContent(new Element("lineSwitchStatus").setText(String.valueOf(e.getEdgeAsSimpleLine().getSwitchStatus())));
                        simpleLine.addContent(new Element("lineColor").setText(String.valueOf(e.getEdgeAsSimpleLine().getColorLinea())));
                        simpleLine.addContent(new Element("lineState").setText(String.valueOf(e.getEdgeAsSimpleLine().getEstiloLinea())));
                        simpleLine.addContent(new Element("lineStroke").setText(String.valueOf(e.getEdgeAsSimpleLine().getEstiloStroke())));

                        ComplexFormat cf = new ComplexFormat();
                        String complexCurrent = cf.format(e.getEdgeAsSimpleLine().getCurrent());

                        simpleLine.addContent(new Element("lineComplexCurrent").setText(complexCurrent));
                        simpleLine.addContent(new Element("lineLabelState").setText(String.valueOf(e.getEdgeAsSimpleLine().getLabel())));
                        simpleLine.addContent(new Element("lineInFault").setText(String.valueOf(e.getEdgeAsSimpleLine().getInFault())));
                        

                        edgeElement.addContent(simpleLine);

                        break;
                    case "TFLocationLine":

                        // Artreglar esto a lo que si corresponde
                        Element tfLine = new Element("TFLocationLine");
                        tfLine.setAttribute(new Attribute("name", e.getEdgeAsSimpleLine().getName()));
                        tfLine.setAttribute(new Attribute("lineType", e.getEdgeTypeName()));

                        tfLine.addContent(new Element("source").setText(e.getEdgeAsSimpleLine().getOrigen().getVertexName()));
                        tfLine.addContent(new Element("typeSourceNode").setText(e.getEdgeAsSimpleLine().getOrigen().getVertexTypeName()));

                        tfLine.addContent(new Element("target").setText(e.getEdgeAsSimpleLine().getDestino().getVertexName()));
                        tfLine.addContent(new Element("typeTargetNode").setText(e.getEdgeAsSimpleLine().getDestino().getVertexTypeName()));

                        tfLine.addContent(new Element("lineNumber").setText(String.valueOf(e.getEdgeAsSimpleLine().getLineNumber())));

                        tfLine.addContent(new Element("lineResistance").setText(String.valueOf(e.getEdgeAsSimpleLine().getResistance())));
                        tfLine.addContent(new Element("lineReactance").setText(String.valueOf(e.getEdgeAsSimpleLine().getReactance())));
                        tfLine.addContent(new Element("lineMaxCurrent").setText(String.valueOf(e.getEdgeAsSimpleLine().getMaxCurrent())));
                        tfLine.addContent(new Element("lineMagCurrent").setText(String.valueOf(e.getEdgeAsSimpleLine().getMagCurrent())));
                        tfLine.addContent(new Element("lineSwitchCode").setText(String.valueOf(e.getEdgeAsSimpleLine().getSwitchCode())));
                        tfLine.addContent(new Element("lineSwitchStatus").setText(String.valueOf(e.getEdgeAsSimpleLine().getSwitchStatus())));
                        tfLine.addContent(new Element("lineColor").setText(String.valueOf(e.getEdgeAsSimpleLine().getColorLinea())));
                        tfLine.addContent(new Element("lineStyle").setText(String.valueOf(e.getEdgeAsSimpleLine().getEstiloLinea())));
                        tfLine.addContent(new Element("lineStroke").setText(String.valueOf(e.getEdgeAsSimpleLine().getEstiloStroke())));

                        //ComplexFormat cf = new ComplexFormat();
                        //String complexCurrent = cf.format(e.getEdgeAsSimpleLine().getCurrent());
                        //tfLine.addContent(new Element("lineComplexCurrent").setText(complexCurrent));
                        tfLine.addContent(new Element("lineLabelState").setText(String.valueOf(e.getEdgeAsSimpleLine().getLabel())));
                        tfLine.addContent(new Element("lineInFault").setText(String.valueOf(e.getEdgeAsSimpleLine().getInFault())));

                        edgeElement.addContent(tfLine);

                        break;
                    default:
                        System.out.println("Un detalle por aqui!!! :(");
                        break;
                }
            }

            doc.getRootElement().addContent(vertexElement);
            doc.getRootElement().addContent(edgeElement);

            // new XMLOutputter().output(doc, System.out);
            XMLOutputter xmlOutput = new XMLOutputter();

            // display nice nice
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter(filename));
            //xmlOutput.output(doc, new FileWriter("c:\\xmls\\file.xml"));

            System.out.println("File Saved!");
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }

    public void readXMLFile(String filename) {
        //Se crea un SAXBuilder para poder parsear el archivo
        SAXBuilder builder = new SAXBuilder();
        //File xmlFile = new File("c:\\xmls\\file.xml");
        File xmlFile = new File(filename);
        try {
            //Se crea el documento a traves del archivo
            Document document = builder.build(xmlFile);

            //Se obtiene la raiz 
            Element rootNode = document.getRootElement();

            Element vertexElements = rootNode.getChild("Vertex");
            Element edgeElements = rootNode.getChild("Edge");

            //Se obtiene la lista de hijos
            List listVertex = vertexElements.getChildren();//rootNode.getChildren("Vertex");
            List listEdge = edgeElements.getChildren();

            System.out.println("numero de vertices: " + listVertex.size());

            String vertexName = "";
            String posX = "";
            String posY = "";
            Point2D.Double position;
            Vertex v;

            //Se recorre la lista de hijos 
            for (int i = 0; i < listVertex.size(); i++) {
                Element dataVertex = (Element) listVertex.get(i);

                switch (dataVertex.getName()) {
                    case "Bus":
                        vertexName = dataVertex.getAttributeValue("name");

                        posX = dataVertex.getChildTextTrim("posX");
                        posY = dataVertex.getChildTextTrim("posY");

                        position = new Point2D.Double();
                        position.setLocation(Double.valueOf(posX), Double.valueOf(posY));

                        v = Controller.getController().crearNodo(position, "Bus", vertexName);

                        v.getVertexAsBus().setNumberBus(Integer.parseInt(dataVertex.getChildTextTrim("busNumber")));
                        v.getVertexAsBus().setCodeBus(Integer.parseInt(dataVertex.getChildTextTrim("busCode")));
                        v.getVertexAsBus().setVoltageBus(Double.parseDouble(dataVertex.getChildTextTrim("busVoltage")));
                        v.getVertexAsBus().setAngleBus(Double.parseDouble(dataVertex.getChildTextTrim("busAngle")));

                        ComplexFormat cf = new ComplexFormat();
                        Complex c = cf.parse(dataVertex.getChildTextTrim("busComplexVoltageValue"));

                        v.getVertexAsBus().setComplexVoltage(c);

                        v.getVertexAsBus().setIsolatedStatus(Boolean.parseBoolean(dataVertex.getChildTextTrim("busIsolatedStatus")));
                        v.getVertexAsBus().setTFPresent(Boolean.parseBoolean(dataVertex.getChildTextTrim("busTFPresent")));
                        v.getVertexAsBus().setIncidentLinesNumber(Integer.parseInt(dataVertex.getChildTextTrim("busIncidentLinesNumber")));
                        break;
                    case "Load":
                        vertexName = dataVertex.getAttributeValue("name");

                        posX = dataVertex.getChildTextTrim("posX");
                        posY = dataVertex.getChildTextTrim("posY");

                        position = new Point2D.Double();
                        position.setLocation(Double.valueOf(posX), Double.valueOf(posY));

                        v = Controller.getController().crearNodo(position, "Load", vertexName);

                        v.getVertexAsLoad().setLoadNumber(Integer.parseInt(dataVertex.getChildTextTrim("loadNumber")));
                        v.getVertexAsLoad().setLoadPriority(Integer.parseInt(dataVertex.getChildTextTrim("loadPriority")));
                        v.getVertexAsLoad().setLoadMW(Double.parseDouble(dataVertex.getChildTextTrim("loadMW")));
                        v.getVertexAsLoad().setLoadMVar(Double.parseDouble(dataVertex.getChildTextTrim("loadMVar")));
                        v.getVertexAsLoad().setLoadState(Boolean.parseBoolean(dataVertex.getChildTextTrim("loadState")));
                        v.getVertexAsLoad().setLoadType(Integer.parseInt(dataVertex.getChildTextTrim("loadType")));

                        break;
                    case "Generator":
                        vertexName = dataVertex.getAttributeValue("name");

                        posX = dataVertex.getChildTextTrim("posX");
                        posY = dataVertex.getChildTextTrim("posY");

                        position = new Point2D.Double();
                        position.setLocation(Double.valueOf(posX), Double.valueOf(posY));

                        v = Controller.getController().crearNodo(position, "Generator", vertexName);

                        v.getVertexAsGenerator().setGeneratorNumber(Integer.parseInt(dataVertex.getChildTextTrim("generatorNumber")));
                        v.getVertexAsGenerator().setGeneratorType(Integer.parseInt(dataVertex.getChildTextTrim("generatorType")));
                        v.getVertexAsGenerator().setGeneratorState(Boolean.parseBoolean(dataVertex.getChildTextTrim("generatorState")));
                        v.getVertexAsGenerator().setMWGenerator(Double.parseDouble(dataVertex.getChildTextTrim("generatorMW")));
                        v.getVertexAsGenerator().setMVarGenerator(Double.parseDouble(dataVertex.getChildTextTrim("generatorMVar")));
                        v.getVertexAsGenerator().setMinMVarGenerator(Double.parseDouble(dataVertex.getChildTextTrim("generatorMinMW")));
                        v.getVertexAsGenerator().setMaxMVarGenerator(Double.parseDouble(dataVertex.getChildTextTrim("generatorMaxMW")));

                        break;
                    case "ElectricVehicle":
                        vertexName = dataVertex.getAttributeValue("name");

                        posX = dataVertex.getChildTextTrim("posX");
                        posY = dataVertex.getChildTextTrim("posY");

                        position = new Point2D.Double();
                        position.setLocation(Double.valueOf(posX), Double.valueOf(posY));

                        v = Controller.getController().crearNodo(position, "ElectricVehicle", vertexName);

                        v.getVertexAsElectricVehicle().setElectricVehicleNumber(Integer.parseInt(dataVertex.getChildTextTrim("electricVehicleNumber")));
                        v.getVertexAsElectricVehicle().setElectricVehicleType(Integer.parseInt(dataVertex.getChildTextTrim("electricVehicleType")));
                        v.getVertexAsElectricVehicle().setElectricVehicleState(Boolean.parseBoolean(dataVertex.getChildTextTrim("electricVehicleState")));

                        break;
                    case "TFLocationNode":
                        break;
                    default:
                        throw new AssertionError();
                }

            }

            String lineName = "";
            String lineNumber = "";
            String sourceVertexName = "";
            String targetVertexName = "";
            String typeSourceNode = "";
            String typeTargetNode = "";

            for (int i = 0; i < listEdge.size(); i++) {
                Element dataEdge = (Element) listEdge.get(i);

                switch (dataEdge.getName()) {
                    case "SimpleLine":
                        
                        lineName = dataEdge.getAttributeValue("name");
                        lineNumber = dataEdge.getChildTextTrim("lineNumber");
                        sourceVertexName = dataEdge.getChildTextTrim("source");
                        targetVertexName = dataEdge.getChildTextTrim("target");
                        typeSourceNode = dataEdge.getChildTextTrim("typeSourceNode");
                        typeTargetNode = dataEdge.getChildTextTrim("typeTargetNode");

                        Vertex sourceVertex = ElectricalNetwork.getElectricalNetwork().getBusByName(sourceVertexName);
                        Vertex targetVertex = ElectricalNetwork.getElectricalNetwork().getBusByName(targetVertexName);
                        
//                        Edge e = EdgeFactory.getInstance().create(sourceVertex, targetVertex, Integer.parseInt(lineNumber));
                        Edge e = EdgeFactory.getInstance().create(sourceVertex, targetVertex);

                        e.getEdgeAsSimpleLine().setName(lineName);
                        //e.getEdgeAsSimpleLine().setLineNumber(Integer.parseInt(lineNumber));
                        e.getEdgeAsSimpleLine().setResistance(Double.parseDouble(dataEdge.getChildTextTrim("lineResistance")));
                        e.getEdgeAsSimpleLine().setReactance(Double.parseDouble(dataEdge.getChildTextTrim("lineReactance")));
                        e.getEdgeAsSimpleLine().setMaxCurrent(Double.parseDouble(dataEdge.getChildTextTrim("lineMaxCurrent")));
                        e.getEdgeAsSimpleLine().setMagCurrent(Double.parseDouble(dataEdge.getChildTextTrim("lineMagCurrent")));
                        e.getEdgeAsSimpleLine().setSwitchCode(Integer.parseInt(dataEdge.getChildTextTrim("lineSwitchCode")));
                        e.getEdgeAsSimpleLine().setSwitchCode(Integer.parseInt(dataEdge.getChildTextTrim("lineSwitchCode")));
                        e.getEdgeAsSimpleLine().setSwitchStatus(Integer.parseInt(dataEdge.getChildTextTrim("lineSwitchStatus")));
                        //e.getEdgeAsSimpleLine().setColorLinea(new Color(dataEdge.getChildTextTrim("lineColor")));
                        //e.getEdgeAsSimpleLine().setResistance(Double.parseDouble(dataEdge.getChildTextTrim("lineResistance")));

                        ComplexFormat cf = new ComplexFormat();
                        Complex lcc = cf.parse(dataEdge.getChildTextTrim("lineComplexCurrent"));

                        e.getEdgeAsSimpleLine().setCurrent(lcc);
                        e.getEdgeAsSimpleLine().setLabelState(dataEdge.getChildTextTrim("lineLabelState"));
                        e.getEdgeAsSimpleLine().setInFalut(Boolean.parseBoolean(dataEdge.getChildTextTrim("lineInFault")));
                        e.getEdgeAsSimpleLine().setEstiloLinea(Boolean.parseBoolean(dataEdge.getChildTextTrim("lineState")));
                        
                        ElectricalNetwork.getElectricalNetwork().createLine(e);

                        break;
                    case "TFLocationLine":
                        /*
                         vertexName = dataVertex.getAttributeValue("name");

                         posX = dataVertex.getChildTextTrim("posX");
                         posY = dataVertex.getChildTextTrim("posY");

                         position = new Point2D.Double();
                         position.setLocation(Double.valueOf(posX), Double.valueOf(posY));

                         v = Controller.getController().crearNodo(position, "Load", vertexName);

                         v.getVertexAsLoad().setLoadNumber(Integer.parseInt(dataVertex.getChildTextTrim("loadNumber")));
                         v.getVertexAsLoad().setLoadPriority(Integer.parseInt(dataVertex.getChildTextTrim("loadPriority")));
                         v.getVertexAsLoad().setLoadMW(Double.parseDouble(dataVertex.getChildTextTrim("loadMW")));
                         v.getVertexAsLoad().setLoadMVar(Double.parseDouble(dataVertex.getChildTextTrim("loadMVar")));
                         v.getVertexAsLoad().setLoadState(Boolean.parseBoolean(dataVertex.getChildTextTrim("loadState")));
                         v.getVertexAsLoad().setLoadType(Integer.parseInt(dataVertex.getChildTextTrim("loadType")));
                         */

                        break;

                    default:
                        throw new AssertionError();
                }

            }

        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }
    }

}
