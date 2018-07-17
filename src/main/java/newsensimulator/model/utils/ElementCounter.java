/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.utils;

import java.util.ArrayList;

/**
 *
 * @author Jose Muñoz Parra
 */
public class ElementCounter {

    private static ElementCounter elementCounter;

    private ArrayList<Integer> busCounter;
    private ArrayList<Integer> loadCounter;
    private ArrayList<Integer> faultCounter;
    private ArrayList<Integer> generatorCounter;
    private ArrayList<Integer> EVCounter;
    private ArrayList<Integer> swCounter;
    private ArrayList<Integer> lineCounter;
    private ArrayList<Integer> batteryCounter;

    private int busCount = 0;
    private int loadCount = 0;
    private int faultCount = 0;
    private int generatorCount = 0;
    private int EVCount = 0;
    private int swCount = 0;
    private int lineCount = 0;
    private int batteryCount = 0;

    public static ElementCounter getElementCounter() {
        if (elementCounter == null) {
            elementCounter = new ElementCounter();
        }
        return elementCounter;
    }

    public ElementCounter() {
        busCounter = new ArrayList();
        loadCounter = new ArrayList();
        faultCounter = new ArrayList();
        generatorCounter = new ArrayList();
        EVCounter = new ArrayList();
        swCounter = new ArrayList();
        lineCounter = new ArrayList();
        batteryCounter = new ArrayList();

        /*
         System.out.println("tamaño inicial: " + busCounter.size());
         System.out.println("tamaño inicial: " + loadCounter.size());
         System.out.println("tamaño inicial: " + faultCounter.size());
         System.out.println("tamaño inicial: " + generatorCounter.size());
         System.out.println("tamaño inicial: " + EVCounter.size());
         System.out.println("tamaño inicial: " + swCounter.size());
         System.out.println("tamaño inicial: " + lineCounter.size());
         */
    }

    public int getCountFundamentalLoops() {
        int totalBuses = busCounter.size() + 1;
        int totalLines = lineCounter.size() + 1;

        return totalLines - totalBuses + 1;
    }

    public int getTotalBuses() {
        return busCounter.size() + 1;
    }

    public int getBusNumberAvailable() {
        int number = -1;
        System.out.println("el tamano es:" + busCounter.size());
        for (int i = 1; i <= busCounter.size(); i++) {
            if (busCounter.indexOf(i) == -1) {
                number = i;
                busCounter.add(number);
                //System.out.println("aca saca nuemro");
                break;
            }
        }
        if (number == -1) {
            number = busCounter.size() + 1;
            busCounter.add(number);
            //System.out.println("aca saca nuevo numero");
        }
        System.out.println("solicito el numero " + number);
        return number;
    }

    public void setBusNumberAvailable(int num) {
        busCounter.add(num);
    }

    public int getLoadNumberAvailable() {
        int number = -1;
        for (int i = 1; i <= loadCounter.size(); i++) {
            if (loadCounter.indexOf(i) == -1) {
                number = i;
                loadCounter.add(number);
                //System.out.println("aca saca nuemro");
                break;
            }
        }
        if (number == -1) {
            number = loadCounter.size() + 1;
            loadCounter.add(number);
        }
        return number;
    }

    public int getFautNumberAvailable() {
        int number = -1;
        for (int i = 1; i <= faultCounter.size(); i++) {
            if (faultCounter.indexOf(i) == -1) {
                number = i;
                faultCounter.add(number);
                break;
            }
        }
        if (number == -1) {
            number = faultCounter.size() + 1;
            faultCounter.add(number);
        }
        return number;
    }

    public int getGeneratorNumberAvailable() {
        int number = -1;
        for (int i = 1; i <= generatorCounter.size(); i++) {
            if (generatorCounter.indexOf(i) == -1) {
                number = i;
                generatorCounter.add(number);
                break;
            }
        }
        if (number == -1) {
            number = generatorCounter.size() + 1;
            generatorCounter.add(number);
        }
        return number;
    }

    public int getElectricVehicleNumberAvailable() {
        int number = -1;
        for (int i = 1; i <= EVCounter.size(); i++) {
            if (EVCounter.indexOf(i) == -1) {
                number = i;
                EVCounter.add(number);
                break;
            }
        }
        if (number == -1) {
            number = EVCounter.size() + 1;
            EVCounter.add(number);
        }
        return number;
    }

    public int getBatteryNumberAvailable() {
        int number = -1;
        for (int i = 1; i <= batteryCounter.size(); i++) {
            if (batteryCounter.indexOf(i) == -1) {
                number = i;
                batteryCounter.add(number);
                break;
            }
        }
        if (number == -1) {
            number = batteryCounter.size() + 1;
            batteryCounter.add(number);
        }
        return number;
    }

    public int getSWNumberAvailable() {
        int number = -1;
        for (int i = 1; i <= swCounter.size(); i++) {
            if (swCounter.indexOf(i) == -1) {
                number = i;
                swCounter.add(number);
                break;
            }
        }
        if (number == -1) {
            number = swCounter.size() + 1;
            swCounter.add(number);
        }
        return number;
    }

    public int getLineNumberAvailable() {
        int number = -1;
        System.out.println("tamano de lineas: " + lineCounter.size());
        for (int i = 1; i <= lineCounter.size(); i++) {
            System.out.println("numero---> " + lineCounter.get(i - 1));
            if (lineCounter.indexOf(i) == -1) {
                number = i;
                lineCounter.add(number);
                break;
            }
        }
        if (number == -1) {
            number = lineCounter.size() + 1;
            lineCounter.add(number);
        }
        return number;
    }

    public void removeBusNumber(int number) {
        busCounter.remove(busCounter.indexOf(number));
    }

    public void removeLoadNumber(int number) {
        loadCounter.remove(loadCounter.indexOf(number));
    }

    public void removeFaultNumber(int number) {
        faultCounter.remove(faultCounter.indexOf(number));
    }

    public void removeGeneratorNumber(int number) {
        generatorCounter.remove(generatorCounter.indexOf(number));
    }

    public void removeElectricVehicleNumber(int number) {
        EVCounter.remove(EVCounter.indexOf(number));
    }

    public void removeBatteryNumber(int number) {
        batteryCounter.remove(batteryCounter.indexOf(number));
    }

    public void removeSWNumber(int number) {
        swCounter.remove(swCounter.indexOf(number));
    }

    public void removeLineNumber(int number) {
        for (int i = 0; i < lineCounter.size(); i++) {
            System.out.println("lineCounter index: " + lineCounter.get(i));

        }
        System.out.println("el que quiero eliminar: " + number);
        lineCounter.remove(lineCounter.indexOf(number));
    }

    public void resetCounters() {
        elementCounter = new ElementCounter();
    }

    public void setFautNumberAvailable(int numeroFalla) {
        faultCounter.add(numeroFalla);
    }

    public void setLoadNumberAvailable(int numeroCarga) {
        loadCounter.add(numeroCarga);
    }

    public void setGeneratorNumberAvailable(int numeroGenerador) {
        generatorCounter.add(numeroGenerador);
    }

    public void setElectricVehicleNumberAvailable(int numeroVehiculoElectrico) {
        EVCounter.add(numeroVehiculoElectrico);
    }

    public void setBatteryNumberAvailable(int numeroBateria) {
        batteryCounter.add(numeroBateria);
    }

    public void setLineNumberAvailable(int numberLine) {
        if (!lineCounter.contains(numberLine)) {
            this.lineCounter.add(numberLine);
        }
    }
}
