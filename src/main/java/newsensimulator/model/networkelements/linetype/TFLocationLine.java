/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.networkelements.linetype;

import newsensimulator.model.networkelements.Vertex;

/**
 *
 * @author Jose Muñoz Parra
 */
public class TFLocationLine {

    private String name = "";
    private int numberLine = 0;
    private Vertex vertexSource = null;
    private Vertex vertexTarget = null;
    private double faultTime = 0.1;
    private double recoveryTime = 4;
    private double handlingTime = 0;
    private Conductor conductor = null;
    private boolean lineBuilt = true;

    public TFLocationLine(String name){
        this.name = name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNumberLine(int numberLine) {
        this.numberLine = numberLine;
    }

    public int getNumberLine() {
        return numberLine;
    }

    public void setSourceVertex(Vertex source) {
        this.vertexSource = source;
    }

    public Vertex getSourceVertex() {
        return vertexSource;
    }

    public void setTargetVertex(Vertex target) {
        this.vertexTarget = target;
    }

    public Vertex getTargetVertex() {
        return vertexTarget;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setFaultTime(double seconds) {
        this.faultTime = seconds;
    }

    public double getFaultTime() {
        return faultTime;
    }

    public void setRecoveryTime(double seconds) {
        this.recoveryTime = seconds;
    }

    public double getRecoveryTime() {
        return recoveryTime;
    }

    public void setHandlingTime(double seconds) {
        this.handlingTime = seconds;
    }

    public double getHandlingTime() {
        return handlingTime;
    }

    public boolean isPresent() {
        return lineBuilt;
    }

    public void setLineBuilt(boolean lineBuilt) {
        this.lineBuilt = lineBuilt;
    }
}
