/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.problem.restorationservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Liss
 */
public class NewFundamentalLoops {

    private List cycles;
    private List fault;
    private List noswitch;

    public NewFundamentalLoops(List cycles, List fault, List noSwitch) {
        this.cycles = cycles;
        this.fault = fault;
        this.noswitch = noSwitch;
    }
    

    

    public List runFL() {
        int counter =0;
        List newCycles = new ArrayList();
        List FL = new ArrayList();

        for (Object cycle : this.cycles) {
            if (Collections.disjoint((List) cycle, this.fault) != true) {
                FL.addAll((List) cycle);
                counter++;
            } else {                
                newCycles.add((List) cycle);
                
            }
        }
        
        FL.removeAll(this.fault);
        
        List duplicate = new ArrayList();
        duplicate = getDuplicate(FL);
        FL.removeAll(duplicate);
        
        if(counter > 1 ){
        newCycles.add(FL);
        }
        
        for (int i = 0; i < newCycles.size(); i++) {
            List cycle = (List) newCycles.get(i);
            cycle.removeAll(this.noswitch);
        }
        
        System.out.println(newCycles);
        return newCycles;
    }
    
    public static List getDuplicate(Collection list) {

    List duplicated = new ArrayList();
    Set set = new HashSet() {

    public boolean add(Object e) {
        if (contains(e)) {
            duplicated.add(e);
        }
        return super.add(e);
    }
    };
   for (Object t : list) {
        set.add(t);
    }
    return duplicated;
}

}
