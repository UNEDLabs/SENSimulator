package newsensimulator.model.networkelements.linetype;

import newsensimulator.model.networkelements.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Jose Muñoz Parra
 */
public class SimpleLine {

    private String name;
    private int lineaNo;
    private double resistance = 0.1;
    private double reactance = 0.1;
    private double maxCurrent = 1.0;
    private Vertex origen;
    private Vertex destino;
    private int switchCode = 222;
    private int switchStatus = 11;
    private Color colorLinea = Color.BLACK;
    private boolean lineState = true;
    private static float dash[] = {1000000000000.0f};
    private Stroke estiloStroke = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 1.0f);
    private Complex current = new Complex(0, 0);
    private double magCurrent = 0;

    private String labelState;
    
    private int[] swSource;
    private int[] swDest;
    private boolean inFault = false;

    public SimpleLine(String name) {
        this.name = name;
    }

    public int getLineNumber() {
     return lineaNo;
    }

    public void setLineNumber(int numero) {
        this.lineaNo = numero;
    }

    public double getResistance() {
        return resistance;
    }

    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    public double getReactance() {
        return reactance;
    }

    public void setReactance(double reactance) {
        this.reactance = reactance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxCurrent() {
        return maxCurrent;
    }

    public void setMaxCurrent(double maxCurrent) {
        this.maxCurrent = maxCurrent;
    }

    public void setOrigen(Vertex origen) {
        this.origen = origen;
    }

    public Vertex getOrigen() {
        return origen;
    }

    public void setDestino(Vertex destino) {
        this.destino = destino;
    }

    public Vertex getDestino() {
        return destino;
    }

    public void setSwitchCode(int switchCode) {
        this.switchCode = switchCode;
    }

    public int getSwitchCode() {
        return switchCode;
    }

    public void setSwitchStatus(int switchStatus) {
        this.switchStatus = switchStatus;
    }

    public int getSwitchStatus() {
        return switchStatus;
    }

    public void setColorLinea(Color color) {
        this.colorLinea = color;
    }

    public Color getColorLinea() {
        return colorLinea;
    }

    public void setEstiloLinea(boolean estilo) {
        if (estilo) {
            float dash[] = {5.0f};
            estiloStroke = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 1.0f);
            lineState = estilo;
            setColorLinea(Color.BLACK);
        } else {
            float dash[] = {5.0f};
            estiloStroke = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 1.0f);
            lineState = estilo;
            setColorLinea(Color.GRAY);
        }
    }

    public boolean getEstiloLinea() {
        float dashT[] = {5.0f};
        if (estiloStroke == new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dashT, 1.0f)) {
            return lineState;
        } else {
            return lineState;
        }
    }

    public void setEstiloStroke(Stroke stroke) {
        this.estiloStroke = stroke;
    }

    public Stroke getEstiloStroke() {
        return estiloStroke;
    }

    public void setMagCurrent(double current) {
        this.magCurrent = current;
    }

    public double getMagCurrent() {
        return magCurrent;
    }

    public void setCurrent(Complex current) {
        this.current = current;
    }

    public Complex getCurrent() {
        return current;
    }

    public void setLabelState(String labelState) {
        this.labelState = labelState;
    }
    
    public int[] getSwSource(){
        return this.swSource;
    }
    
    public void setSwSource(int[] swSource){
        this.swSource=swSource;
    }
    
    public int[] getSwDest(){
        return this.swDest;
    }
    
    public void setSwDest(int[] swDest){
        this.swDest=swDest;
    }
    
    public String getLabel() {
        String label = null;
        if (!this.getName().equals("")) {
            switch (this.getSwitchStatus()) {
                case 00:
                    labelState = "(0 - 0)";
                    break;
                case 11:
                    labelState = "(X - X)";
                    break;
                case 01:
                    labelState = "(0 - X)";
                    break;
                case 10:
                    labelState = "(X - 0)";
                    break;
                default:
                    labelState = "( N/A )";
                    break;

            }
            label = name + " " + labelState;
        }
        return label;
    }
    
    public void setInFalut(boolean inFault){
        this.inFault = inFault;
    }
    
    public boolean getInFault(){
        return inFault;
    }
    
    public String toString(){
        return name;
    }
}
