/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator;

import newsensimulator.model.JADEplatform.JadeRuntime;
import newsensimulator.view.MainInterface;

/**
 *
 * @author hvargas
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //JadeRuntime.getJadeRuntime();
        MainInterface.getMainInterface().setVisible(true);
    }
}
