/*
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 |xxxxxxx|                     Programa de Tesis                      |xxxxxxx|
 |xxxxxxx|         OPTIMIZACIÓN DEL ABASTECIMIENTO ELÉCTRICO          |xxxxxxx|
 |xxxxxxx|                 EN REDES DE BAJA TENSIÓN                   |xxxxxxx|
 |xxxxxxx|            CONSIDERANDO UN ANÁLISIS TRIFÁSICO              |xxxxxxx|
 |xxxxxxx|                                                            |xxxxxxx|
 |xxxxxxx|           "LOCALIZACION ÓPTIMA DE TRANSFORMADORES"         |xxxxxxx|
 |xxxxxxx|                  "EN REDES DE DISTRIBUCIÓN"                |xxxxxxx|
 |xxxxxxx|                            2014                            |xxxxxxx|
 |xxxxxxx|                                                            |xxxxxxx|
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 |xxxxxxx|               JMendoza - AMadrid - HVargas                 |xxxxxxx|
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 */
package newsensimulator.model.problem.tdslocationforss.tds_location_for_ss;

import newsensimulator.model.problem.tdslocationforss.Run_Algorithm.TDs_Location.TDs_Location;

/**
 * @author Andreuw Madrid Carreño "Escuela de Ingeniería Eléctrica - PUCV"
 */

public class TDs_Location_for_SS {
    
    public static void main(String[] args) {
    TDs_Location TDs = new TDs_Location();
    TDs.Run_TDs_Location();                 //Ejecutar Algoritmo
    
    TDs.getcostoTotal();                    //Costo total de la Red
    TDs.getTDs();                           //Posición de los TDs
    TDs.getLineasAbiertas();                //Líneas que se abrirán
    TDs.getLineasCerradas();                //L{ineas que quedar{an cerradas
    TDs.getPerdTDs();                       //Pérdidas en TDs
    TDs.getPerd_Red();                      //Pérdidas en la Red
    TDs.getTDs_Norm();                      //Valor comercial TDs
    TDs.getcantidadEnergiaNoSuministrada(); //Energía no suministrada
    TDs.getdistLBT();                       //Longitud línea de B.T
    TDs.getdistLMT();                       //Longitud línea de M.T
    TDs.getkVA();                           //Demanda real
    TDs.getkVAs();                          //Demanda proyectada
    TDs.getfaseTensionMasBaja();            //Fase de la tension mas baja
    TDs.getnodoConTensionMasBaja();         //Nodo con tension mas baja
    TDs.gettensionMasBaja();                //Regulación
    TDs.getMatrizVoltajesGrados();          //Amplitud, Fase y Ángulo de cada Voltaje. (Voltaje de Nodo) (GRADOS)
    TDs.getMatrizVoltajesRadianes();        //Amplitud, Fase y Ángulo de cada Voltaje. (Voltaje de Nodo) (RADIANES)
    TDs.getMatrizCorrientesGrados();        //Amplitud, Fase y Ángulo de cada Corriente. (Corriente de Línea) (GRADOS)
    TDs.getMatrizCorrientesRadianes();      //Amplitud, Fase y Ángulo de cada Corriente. (Corriente de Línea) (RADIANES)

    /*System.out.println("Líneas Abiertas: ");
    PrintMatrix Impresion_tdssource = new PrintMatrix(TDs.getLineasAbiertas());
    System.out.println("Líneas Abiertas: ");
    PrintMatrix Impresion_tdssodurce = new PrintMatrix(TDs.getLineasCerradas());
    System.out.println("Voltajes Grados: ");
    PrintMatrix Impresion_tdkssource = new PrintMatrix(TDs.getMatrizVoltajesGrados());
    System.out.println("Corrientes Grados: ");
    PrintMatrix Impresion_tdskksource = new PrintMatrix(TDs.getMatrizCorrientesGrados());
    */
    }
}
