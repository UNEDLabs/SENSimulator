/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package newsensimulator.model.networkelements.vertextype;

import newsensimulator.model.networkelements.Edge;


/**
 *
 * @author Jose Muñoz Parra
 */
public class Fault{
    
    private String name;
    private int faultNumber;
    private int faultType;
    private Edge line;

    public Fault(String name) {
        this.name = name;
    }

    /*
    public Fault(String name, int number) {
        this.name = name;
        this.faultNumber = number;
    }
    */
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setFaultNumber(int faultNumber){
        this.faultNumber = faultNumber;
    }
    
    public int getFaultNumber(){
        return faultNumber;
    }
/*
    public String toString() {
        return name;
    }
  */  
    public void setLocation(Edge line){
        this.line = line;
        line.getEdgeAsSimpleLine().setInFalut(true);
    }
    
    public Edge getLocation(){
        return line;
    }

    public void setFaultType(int faultType){
        this.faultType = faultType;
    }
    
    public int getFaultType() {
        return faultType;
    }
}
