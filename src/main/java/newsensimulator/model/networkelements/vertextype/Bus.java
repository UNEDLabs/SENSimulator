/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.networkelements.vertextype;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ListIterator;
import newsensimulator.control.Controller;
import newsensimulator.model.ElectricalNetwork;
import newsensimulator.model.networkelements.Edge;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Jose Muñoz Parra (HVO modified)
 */
public class Bus implements BusEventListener {

    private String name;
    private int numberBus = 0;
    private int codeBus = 0;
    private double voltageBus = 1;
    private double angleBus = 0.0;
    private boolean isolatedStatus = true;
    private boolean tfPresente = false;
    private int incidentLinesNumber = 0;

    private ArrayList listeners;
    private ArrayList<Edge> incidentLines;

    private boolean flag = false;
    private double voltageBeforeBlackout = -1;
    private double angleBeforeBlackout = -1;
    private Complex complexVoltaje = new Complex(0, 0);

    public Bus(String name) {
        this.name = name;

        listeners = new ArrayList();
        incidentLines = new ArrayList();
        listeners.add(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String MyName) {
        this.name = MyName;
    }

    public int getNumberBus() {
        return numberBus;
    }

    public void setNumberBus(int numberBus) {
        this.numberBus = numberBus;
    }

    public int getBusCode() {
        return codeBus;
    }

    public void setCodeBus(int codeBus) {
        if (codeBus != this.codeBus) {
            this.codeBus = codeBus;
            ListIterator li = listeners.listIterator();
            while (li.hasNext()) {
                ((BusEventListener) li.next()).changeBusType(new BusEventObject(this, this));
            }
        } else {
            this.codeBus = codeBus;
        }
    }

    public double getVoltageBus() {
        return voltageBus;
    }

    public void setVoltageBus(double voltageBus) {
        double vbb = getVoltageBus();
        double abb = getAngleBus();
        this.voltageBus = voltageBus;

        if (voltageBus == 0.0 && flag == false) {
            flag = true;
            setVoltageBeforeBlackout(vbb);
            setAngleBeforeBlackout(abb);
        }

        if (flag && voltageBus > 0.0) {
            flag = false;
        }

        if (flag  && Controller.getController().isRunningSimulation()) {
            ListIterator li = listeners.listIterator();
            while (li.hasNext()) {
                ((BusEventListener) li.next()).withoutEnergy(new BusEventObject(this, this));
            }
        }
    }

    public void setComplexVoltage(Complex complexVoltage) {
        this.complexVoltaje = complexVoltage;
    }

    public Complex getComplexVoltage() {
        return complexVoltaje;
    }

    public double getVoltageBeforeBlackout() {
        return voltageBeforeBlackout;
    }

    public double getAngleBeforeBlackout() {
        return angleBeforeBlackout;
    }

    private void setVoltageBeforeBlackout(double voltageBeforeBlackout) {
        this.voltageBeforeBlackout = voltageBeforeBlackout;
    }

    private void setAngleBeforeBlackout(double angleBeforeBlackout) {
        this.angleBeforeBlackout = angleBeforeBlackout;
    }

    public double getAngleBus() {
        return angleBus;
    }

    public void setAngleBus(double angleBus) {
        this.angleBus = angleBus;
    }
    /*
     public double getLoadMW() {
     return loadMW;
     }

     public void setLoadMW(double loadMW) {
     this.loadMW = loadMW;
     }

     public double getLoadMVar() {
     return loadMVar;
     }

     public void setLoadMVar(double loadMVar) {
     this.loadMVar = loadMVar;
     }
     */

    public boolean getIsolatedStatus() {
        return isolatedStatus;
    }

    public void setIsolatedStatus(boolean isolatedStatus) {
        this.isolatedStatus = isolatedStatus;
    }

    public void setTFPresent(boolean tfPresente) {
        this.tfPresente = tfPresente;
    }

    public boolean isTFPresent() {
        return tfPresente;
    }
    /*
     public String toString() {
     return name;
     }
     */

    public String getBusCodeText() {
        String nameCode;
        if (codeBus == 0) {
            nameCode = "Load Bus";
        } else if (codeBus == 1) {
            nameCode = "Slack Bus";
        } else {
            nameCode = "Regulated Bus";
        }
        return nameCode;
    }

    public int getIncidentLinesNumber() {
        return incidentLinesNumber;
    }

    public void setIncidentLinesNumber(int incidentLinesNumber) {
        this.incidentLinesNumber = incidentLinesNumber;

        ListIterator li = listeners.listIterator();
        // Recorremos la lista para ejecutar el metodo NombreCambiado de cada manejador almacenado
        while (li.hasNext()) {
            /*
             // Convertimos (CAST) de nuestro objeto
             BusEventListener listener = (BusEventListener) li.next();

             // Creamos el objeto que tiene la información del evento
             BusEventObject busEvObj = new BusEventObject(this, this);
             // Ejecutamos el metodo manejador del evento con los parametros necesarios
             (listener).changeIncidentLines(busEvObj);
             */
            ((BusEventListener) li.next()).changeIncidentLines(new BusEventObject(this, this));

        }
    }

    public void addBusEventListener(BusEventListener listener) {
        listeners.add(listener);
    }

    public void addIncidentLine(Edge incidentLine) {
        incidentLines.add(incidentLine);
    }

    public void removeIncidentLine(Edge incidentLine) {
        incidentLines.remove(incidentLine);
        //System.out.println("tamaño incidentLines: " + incidentLines.size());
    }

    public ArrayList getIncidentLines() {
        return incidentLines;
    }

    public void startSimulation() {
        ListIterator li = listeners.listIterator();
        while (li.hasNext()) {
            ((BusEventListener) li.next()).startSimulation(new BusEventObject(this, this));
        }
    }

    public int getCountFundamentalLoopsSystem() {
        return ElectricalNetwork.getElectricalNetwork().getCountFundamentalLoops();
    }

    public int getTotalBuses() {
        return ElectricalNetwork.getElectricalNetwork().getTotalBuses();
    }

    public void changeIncidentLines(BusEventObject args) {
        //System.out.println("SE PRODUCE EL EVENTO...");
    }

    public void withoutEnergy(BusEventObject args) {
        //System.out.println("SE PRODUCE EL EVENTO ZERO...");
    }

    public void changeBusType(BusEventObject args) {
        //System.out.println("SE PRODUCE EL EVENTO CHANGE BUSTYPE...");
    }

    public void startSimulation(BusEventObject args) {
        //System.out.println("SE PRODUCE EL EVENTO START SIMULATION...");
    }

}
