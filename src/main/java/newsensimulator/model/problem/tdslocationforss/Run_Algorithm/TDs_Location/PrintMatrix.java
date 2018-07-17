/*
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 |xxxxxxx|             IMPRESION DE MATRICES y/o VECTORES             |xxxxxxx| 
 |xxxxxxx|                           2014                             |xxxxxxx|
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 |xxxxxxx|                JMendoza - AMadrid - HVargas                |xxxxxxx|
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 */

package newsensimulator.model.problem.tdslocationforss.Run_Algorithm.TDs_Location;

import flanagan.complex.ComplexMatrix;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * @author Andreuw Madrid C.
 */

public class PrintMatrix {
    // IMPRIMIR UN OBJETO DE LA CLASE REAL-MATRIX    
    public PrintMatrix(RealMatrix a) {

        System.out.println("fila: " + a.getRowDimension());
        System.out.println("columna: " + a.getColumnDimension());

        for (int i = 0; i < a.getRowDimension(); i++) {
            for (int j = 0; j < a.getColumnDimension(); j++) {
                System.out.print(a.getEntry(i, j) + "  ");
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
    // IMPRIMIR UN OBJETO DE LA CLASE COMPLEX-MATRIX
    
    public PrintMatrix(ComplexMatrix a) {

//        System.out.println("fila: " + a.getNrow());
//        System.out.println("columna: " + a.getNcol());
//        for (int capa= 0 ; capa < a.getnMatrix(); capa++){
//            System.out.println("Matriz Capa " + capa);        
//            for (int i = 0; i < a.getNrow(); i++) {
//                for (int j = 0; j < a.getNcol(); j++) {
//                    System.out.print(a.getElementCopy(i, j) + "  ");
//                }
//                System.out.println("");
//            }
//            System.out.println("");
//        }
//        System.out.println("\n\n");
        
    }
    
// IMPRIMIR UN OBJETO DE LA CLASE DOUBLE [][]
    public PrintMatrix(double[][] a) {

        System.out.println("N° Filas: " + a.length);
        System.out.println("N° columnas: " + a[a.length - 1].length);
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println(" ");
        System.out.println();
    }

// IMPRIMIR UN OBJETO DE LA CLASE DOUBLE [] 
    public PrintMatrix(double[] a) {

        for (int f = 0; f < a.length; f++) {
            System.out.print(a[f] + " ");
        }
    }
}
