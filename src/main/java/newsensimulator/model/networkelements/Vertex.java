package newsensimulator.model.networkelements;

import java.awt.Color;
import java.io.Serializable;
import newsensimulator.model.networkelements.vertextype.Battery;
import newsensimulator.model.networkelements.vertextype.BatteryFactory;
import newsensimulator.model.networkelements.vertextype.Bus;
import newsensimulator.model.networkelements.vertextype.BusFactory;
import newsensimulator.model.networkelements.vertextype.ElectricVehicle;
import newsensimulator.model.networkelements.vertextype.ElectricVehicleFactory;
import newsensimulator.model.networkelements.vertextype.Fault;
import newsensimulator.model.networkelements.vertextype.FaultFactory;
import newsensimulator.model.networkelements.vertextype.Generator;
import newsensimulator.model.networkelements.vertextype.GeneratorFactory;
import newsensimulator.model.networkelements.vertextype.Load;
import newsensimulator.model.networkelements.vertextype.LoadFactory;
import newsensimulator.model.networkelements.vertextype.TFLocationNode;
import newsensimulator.model.networkelements.vertextype.TFLocationNodeFactory;

/**
 *
 * @author Jose Muñoz Parra
 */
public class Vertex implements Serializable {

    private static final long serialVersionUID = 1L;

    private int busCode = 0;
    private int faultCode = 1;
    private int loadCode = 2;
    private int generatorCode = 3;
    private int electricVehicleCode = 4;
    private int tfLocationNodeCode = 5;
    private int batteryCode = 6;

    private Bus barra = null;
    private Fault falla = null;
    private Load carga = null;
    private Generator generador = null;
    private ElectricVehicle autoElectrico = null;
    private TFLocationNode tfLocationNode = null;
    private Battery bateria = null;

    private int vertexType;
    private String vertexTypeName;

    public Vertex(String vertexType) {
        vertexTypeName = vertexType;
        switch (vertexType) {
            case "Bus":
                this.setVertexType(busCode);
                System.out.println("Vertice: " + busCode);
                //MainInterface.getMainInterface().Consola.logMessage("hermoso::vertice:" + busCode);
                barra = BusFactory.getInstance().create();
                break;
            case "Fault":
                this.setVertexType(faultCode);
                falla = FaultFactory.getInstance().create();
                break;
            case "Load":
                this.setVertexType(loadCode);
                carga = LoadFactory.getInstance().create();
                break;
            case "Generator":
                this.setVertexType(generatorCode);
                generador = GeneratorFactory.getInstance().create();
                break;
            case "ElectricVehicle":
                this.setVertexType(electricVehicleCode);
                autoElectrico = ElectricVehicleFactory.getInstance().create();
                break;
            case "TFLocationNode":
                this.setVertexType(tfLocationNodeCode);
                tfLocationNode = TFLocationNodeFactory.getInstance().create();
                break;

            case "Battery":
                this.setVertexType(batteryCode);
                bateria = BatteryFactory.getInstance().create();
                break;
            default:

                // System.out.println("Vertice sin tipo-barra asignada por defecto!!!!...");
                break;
        }
    }

    public Vertex(String vertexType, String name) {
        vertexTypeName = vertexType;
        switch (vertexType) {
            case "Bus":
                this.setVertexType(busCode);
                //System.out.println("Vertice: " + busCode);
                barra = BusFactory.getInstance().create(name);
                break;
            case "Fault":
                this.setVertexType(faultCode);
                falla = FaultFactory.getInstance().create(name);
                break;
            case "Load":
                this.setVertexType(loadCode);
                carga = LoadFactory.getInstance().create(name);
                break;
            case "Generator":
                this.setVertexType(generatorCode);
                generador = GeneratorFactory.getInstance().create(name);
                break;
            case "ElectricVehicle":
                this.setVertexType(electricVehicleCode);
                autoElectrico = ElectricVehicleFactory.getInstance().create(name);
                break;
            case "TFLocationNode":
                this.setVertexType(tfLocationNodeCode);
                tfLocationNode = TFLocationNodeFactory.getInstance().create(name);
                break;
            case "Battery":
                this.setVertexType(batteryCode);
                bateria = BatteryFactory.getInstance().create(name);
                break;

            default:

                // System.out.println("Vertice sin tipo-barra asignada por defecto!!!!...");
                break;
        }
    }

    public Bus getVertexAsBus() {
        return barra;
    }

    public Fault getVertexAsFault() {
        return falla;
    }

    public Load getVertexAsLoad() {
        return carga;
    }

    public Generator getVertexAsGenerator() {
        return generador;
    }

    public ElectricVehicle getVertexAsElectricVehicle() {
        return autoElectrico;
    }

    public Battery getVertexAsBattery() {
        return bateria;
    }

    public TFLocationNode getVertexAsTFLocationNode() {
        return tfLocationNode;
    }

    private void setVertexType(int code) {
        this.vertexType = code;
    }

    public int getVertexType() {
        return vertexType;
    }

    public String getVertexTypeName() {
        return vertexTypeName;
    }

    public String getVertexName() {
        switch (vertexTypeName) {
            case "Bus":
                return getVertexAsBus().getName();
            case "Fault":
                return getVertexAsFault().getName();
            case "Load":
                return getVertexAsLoad().getName();
            case "Generator":
                return getVertexAsGenerator().getName();
            case "ElectricVehicle":
                return getVertexAsElectricVehicle().getName();
            case "TFLocationNode":
                return getVertexAsTFLocationNode().getName();
            case "Battery":
                return getVertexAsBattery().getName();
            default:
                return null;
        }
    }

    public void setVertexAsBus(Bus bus) {
        this.barra = bus;
    }

    public void setVertexAsFault(Fault fault) {
        this.falla = fault;
    }

    public void setVertexAsLoad(Load load) {
        this.carga = load;
    }

    public void setVertexAsGenerator(Generator generator) {
        this.generador = generator;
    }

    public void setVertexAsElectricVehicle(ElectricVehicle electricVehicle) {
        this.autoElectrico = electricVehicle;
    }

    public void setVertexAsTFLocationNode(TFLocationNode tfLocationNode) {
        this.tfLocationNode = tfLocationNode;
    }
    
    public void setVertexAsBattery(Battery bateria){
        this.bateria = bateria;
    }

    public Color getColor() {
        return Color.BLUE;
    }

    public Object getPoligono() {
        return null;
    }

}
