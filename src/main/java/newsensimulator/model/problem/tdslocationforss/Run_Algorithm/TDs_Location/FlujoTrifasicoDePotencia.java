/*
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 |xxxxxxx|                                                            |xxxxxxx|
 |xxxxxxx|                 FLUJO DE POTENCIA TRIFASICO                |xxxxxxx|
 |xxxxxxx|                                                            |xxxxxxx|
 |xxxxxxx|                   MÉTODO SUMA DE POTENCIAS                 |xxxxxxx|
 |xxxxxxx|                                                            |xxxxxxx|
 |xxxxxxx|                            2014                            |xxxxxxx|
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 |xxxxxxx|                JMendoza - AMadrid - HVargas                |xxxxxxx|
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 */
package newsensimulator.model.problem.tdslocationforss.Run_Algorithm.TDs_Location;

import flanagan.complex.ComplexMatrix;
import flanagan.complex.Complexx;
import java.util.Arrays;
import static newsensimulator.model.problem.tdslocationforss.Run_Algorithm.TDs_Location.Metodos.CorrientesLineas;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * @author Andreuw Madrid Carreño "Escuela de Ingeniería Eléctrica - PUCV"
 */
public class FlujoTrifasicoDePotencia {

    private double Zbase;                         //Impedancia de base..................................................
    private double Ibase;                         //Corriente de base...................................................
    private int iter_max = 50;                    //Máxima cantidad de iteraciones......................................
    private int n_elem;                          //Número de lineas....................................................
    private int n_nodos;                          //Número de nodos del sistema.........................................
    private ComplexMatrix Volt1;
    private double[][] Volt;
    private double error_max;
    private ComplexMatrix Volt_ant;
    private double Sbase;
    private double Vbase;
    private double error = 1E-8;
    private double nodo_ori;
    private double nodo_des;
    private Complexx Zaa;
    private Complexx Zab;
    private Complexx Zac;
    private Complexx Zba;
    private Complexx Zbb;
    private Complexx Zbc;
    private Complexx Zca;
    private Complexx Zcb;
    private Complexx Zcc;
    private Complexx Yaa;
    private Complexx Ybb;
    private Complexx Ycc;
    private ComplexMatrix Ilin;
    private ComplexMatrix Ilin1;
    private RealMatrix delta;
    private RealMatrix Sperd;
    private Complexx Sperd_1;
    private Complexx Sperd_2;
    private Complexx Sperd_3;
    private RealMatrix P_perdidas;
    private RealMatrix Q_perdidas;
    private ComplexMatrix Volt_int;
    private ComplexMatrix Volt_1;
    private RealMatrix P_equivalente;
    private RealMatrix Q_equivalente;

    private int nl;
    private int ng;
    private int nc;
    private double Vb;
    private double MVAb;
    private double fe;
    private RealMatrix M_Lineas;
    private RealMatrix M_Cargas;
    private RealMatrix M_Cond;
    private RealMatrix M_Dist;
    private RealMatrix Vout_aux;

// """"""SALIDAS DEL FLUJO DE POTENCIA"""""""
    private ComplexMatrix FDP;
    private RealMatrix IL;
    private RealMatrix Voltajes;
    private double ENS;
    private double Perdidas;
    private int Iter;
    private RealMatrix Corriente;
// """"""""""""""""""""""""""""""""""""""""""

    public FlujoTrifasicoDePotencia(int nl, int ng, int nc, double Vb, double MVAb, double fe, RealMatrix M_Lineas, RealMatrix M_CargasMasUno, RealMatrix aux333, RealMatrix M_Cond, RealMatrix M_Dist) {

//        this.nl = nl;
//        this.ng = ng;
//        this.nc = nc;
//        this.Vb = Vb;
//        this.MVAb = MVAb;
//        this.fe = fe;
//        this.M_Lineas = M_Lineas;
//        this.M_Cargas = M_CargasMasUno;
//        this.Vout_aux = aux333;
//        this.M_Cond = M_Cond;
//        this.M_Dist = M_Dist;
//
//        n_elem = nl;                      //Número de lineas....................................................
//        n_nodos = nc + ng;                //Número de nodos del sistema.........................................
//        Sbase = MVAb;
//        Vbase = Vb;
//        P_equivalente = new Array2DRowRealMatrix(new double[n_nodos][3]);
//        Q_equivalente = new Array2DRowRealMatrix(new double[n_nodos][3]);
//
//        Ibase = (Sbase * Math.pow(10, 6)) / (Vbase * Math.pow(10, 3));
//        Zbase = (Math.pow((Vbase * Math.pow(10, 3)), 2)) / (Sbase * Math.pow(10, 6));
//        RealMatrix n1 = new Array2DRowRealMatrix(new double[M_Lineas.getRowDimension()][1]);
//        RealMatrix n2 = new Array2DRowRealMatrix(new double[M_Lineas.getRowDimension()][1]);
//        RealMatrix cond = new Array2DRowRealMatrix(new double[M_Lineas.getRowDimension()][1]);
//        RealMatrix lon = new Array2DRowRealMatrix(new double[M_Lineas.getRowDimension()][1]);
//        n1.setColumn(0, M_Lineas.getColumn(2));
//        n2.setColumn(0, M_Lineas.getColumn(3));
//        cond.setColumn(0, M_Lineas.getColumn(4));
//        lon.setColumn(0, M_Lineas.getColumn(5));
//        int frecuencia = 50;                             //Frecuencia de la Red en [Hz]......
//        double ResistividadAl = 2.82 * Math.pow(10, -8); //Resistividad del Al (ohms·Km).....
//
////      ...............................................      
////          Función : "Eliminar Ramas del Sistema" 
////      ...............................................
//        double[] Vout1 = Vout_aux.getColumn(0);
//
//        for (int iVout1 = 0; iVout1 < Vout1.length; iVout1++) {
//            Vout1[iVout1] = Vout1[iVout1] + 1;
//        }
//
//        Arrays.sort(Vout1); //ordena el vector de menor a mayor
//        int lout;
//
//        for (int i = Vout1.length; i >= 1; i--) {
//            lout = (int) Vout1[i - 1];
//            if (lout <= nl) {
//                nl = nl - 1;
//
//                RealMatrix aux = Metodos.eliminarRama(n1, lout);
//                n1 = null;
//                n1 = aux;
//                aux = null;
//
//                RealMatrix aux1 = Metodos.eliminarRama(n2, lout);
//                n2 = null;
//                n2 = aux1;
//                aux1 = null;
//
//                RealMatrix aux2 = Metodos.eliminarRama(cond, lout);
//                cond = null;
//                cond = aux2;
//                aux2 = null;
//
//                RealMatrix aux3 = Metodos.eliminarRama(lon, lout);
//                lon = null;
//                lon = aux3;
//                aux3 = null;
//            }
//        }
//
//        RealMatrix M_Pc = new Array2DRowRealMatrix(new double[M_Cargas.getRowDimension()][3]);
//        RealMatrix M_Qc = new Array2DRowRealMatrix(new double[M_Cargas.getRowDimension()][3]);
//
//        M_Pc.setColumn(0, M_Cargas.getColumn(1));
//        M_Pc.setColumn(1, M_Cargas.getColumn(2));
//        M_Pc.setColumn(2, M_Cargas.getColumn(3));
//
//        M_Qc.setColumn(0, M_Cargas.getColumn(4));
//        M_Qc.setColumn(1, M_Cargas.getColumn(5));
//        M_Qc.setColumn(2, M_Cargas.getColumn(6));
//
////Variables aux para construir la matriz de Cargas..............................
//        //Variables aux para construir la matriz de Cargas
//        double A2[] = new double[M_Cargas.getRowDimension()];   //Vector columna generado para construir la matriz de Cargas...........
//        double B2[] = new double[M_Cargas.getRowDimension()];   //Vector columna generado para construir la matriz de Cargas...........
//        double C2[] = new double[M_Cargas.getRowDimension()];   //Vector columna generado para construir la matriz de Cargas...........
//        double D2[] = new double[M_Cargas.getRowDimension()];   //Vector columna generado para construir la matriz de Cargas...........
//
//        for (int i = 0; i < M_Cargas.getRowDimension(); i++) {
//            A2[i] = i + 1;
//            B2[i] = 2;
//            C2[i] = 1;
//            D2[i] = 0;
//        }
//        RealMatrix A1 = new Array2DRowRealMatrix(A2);
//        RealMatrix B1 = new Array2DRowRealMatrix(B2);
//        RealMatrix C1 = new Array2DRowRealMatrix(C2);
//        RealMatrix D1 = new Array2DRowRealMatrix(D2);
//
//        //DEFINIR LA MATRIZ DE CARGAS
//        RealMatrix cargas2 = new Array2DRowRealMatrix(new double[M_Cargas.getRowDimension()][A1.getColumnDimension() + B1.getColumnDimension() + C1.getColumnDimension()
//                + D1.getColumnDimension() + M_Pc.getColumnDimension() + M_Qc.getColumnDimension()]);
//        cargas2.setColumn(0, A1.getColumn(0));
//        cargas2.setColumn(1, B1.getColumn(0));
//        cargas2.setColumn(2, C1.getColumn(0));
//        cargas2.setColumn(3, D1.getColumn(0));
//        cargas2.setColumn(4, M_Pc.getColumn(0));
//        cargas2.setColumn(5, M_Pc.getColumn(1));
//        cargas2.setColumn(6, M_Pc.getColumn(2));
//        cargas2.setColumn(7, M_Qc.getColumn(0));
//        cargas2.setColumn(8, M_Qc.getColumn(1));
//        cargas2.setColumn(9, M_Qc.getColumn(2));
//        cargas2.setEntry(0, 1, 1);
//        RealMatrix cargas = ((RealMatrix) cargas2); //Matriz de Cargas..........
//
//        //Variables aux para construir la matriz de Lineas..............................
//        double[] E2 = new double[n1.getRowDimension()];
//        for (int i = 0; i < n1.getRowDimension(); i++) {
//            E2[i] = i + 1;
//        }
//        RealMatrix E = new Array2DRowRealMatrix(E2);
//
//        // CONTINUACION RUTINA   Eli_banch (Función para eliminar filas del sistema)....                                                    
//        RealMatrix lines2 = new Array2DRowRealMatrix(new double[n1.getRowDimension()][E.getColumnDimension()
//                + n1.getColumnDimension() + n2.getColumnDimension() + 2 * lon.getColumnDimension()
//                + 2 * cond.getColumnDimension()]);
//
//        lines2.setColumn(0, E.getColumn(0));
//        lines2.setColumn(1, n1.getColumn(0));
//        lines2.setColumn(2, n2.getColumn(0));
//        lines2.setColumn(3, cond.getColumn(0));
//        lines2.setColumn(4, cond.getColumn(0));
//        lines2.setColumn(5, lon.getColumn(0));
//        lines2.setColumn(6, lon.getColumn(0));
//        RealMatrix lines = ((RealMatrix) lines2);
//
//        //...................................................................................................       
//        //...................  Ordenar la matriz [M_lines] en forma radial  .................................
//        //...................................................................................................
//        int cont = 0;
//        int nodo = 1;
//        int a;
//        int b;
//        RealMatrix Mat_lines = new Array2DRowRealMatrix(new double[lines.getRowDimension()][lines.getColumnDimension()]);
//        n_elem = nl;
//        RealMatrix vector_nodos = new Array2DRowRealMatrix(new double[n_elem]);
//
//        for (int i = 0; i < n_elem; i++) {
//            if (lines.getEntry(i, 1) == nodo) {
//                vector_nodos.setEntry(cont, 0, lines.getEntry(i, 2));
//
//                for (int j = 0; j < Mat_lines.getColumnDimension(); j++) {
//                    Mat_lines.setEntry(cont, j, lines.getEntry(i, j));
//                }
//                lines.setEntry(i, 0, lines.getEntry(i, 0) * (-1));
//                cont++;
//            }
//        }
//        Mat_lines = Metodos.suprimirFilasNulas(Mat_lines);
//        vector_nodos = Metodos.suprimirFilasNulas(vector_nodos.transpose());
//        vector_nodos = vector_nodos.transpose();
//        //Inicio sentencias: 
//        int contador_vector = 1;
//        int contador0 = 0;
//        int contador1 = 0;
//        int contador2 = 0;
//        int contador3 = 0;
//
//        //---------------------------------------
//        vector_nodos = Metodos.suprimirFilasNulas(vector_nodos);
//        vector_nodos = vector_nodos.transpose();
//        //---------------------------------------
//
//        while (cont < n_elem && cont != 0) {
//            nodo = (int) vector_nodos.getEntry(0, contador_vector - 1);
//
//            for (int j = 0; j < n_elem; j++) {
//
//                if (lines.getEntry(j, 0) > 0) {
//
//                    if (lines.getEntry(j, 1) == nodo) {
//
//                        //vector_nodos=[vector_nodos,lines(i,3)];
//                        //--------------------------------------------------------------
//                        contador0++;
//                        double[] vectorAux_B = new double[vector_nodos.getColumnDimension() + contador0];
//                        RealMatrix vectorAux_A = new Array2DRowRealMatrix(vectorAux_B);
//                        vectorAux_A = vectorAux_A.transpose();
//                        vectorAux_A.setSubMatrix(vector_nodos.getData(), 0, 0);
//                        vectorAux_A.setEntry(0, vector_nodos.getColumnDimension(), lines.getEntry(j, 2));
//                        vectorAux_A = vectorAux_A.transpose();
//                        vectorAux_A = Metodos.suprimirFilasNulas(vectorAux_A);
//                        vector_nodos = vectorAux_A.transpose();
//
//                        //Mat_lines=[Mat_lines;lines(i,:)];
//                        contador1++;
//                        double[][] matrizAux1 = new double[Mat_lines.getRowDimension() + contador1][Mat_lines.getColumnDimension()];
//                        RealMatrix matrizAux11 = new Array2DRowRealMatrix(matrizAux1);
//                        matrizAux11.setSubMatrix(Mat_lines.getData(), 0, 0);
//                        RealMatrix vectorAux2 = new Array2DRowRealMatrix(lines.getRow(j));
//                        double[][] vectorAux = vectorAux2.transpose().getData();
//                        matrizAux11.setSubMatrix(vectorAux, Mat_lines.getRowDimension(), 0);
//                        matrizAux11 = Metodos.suprimirFilasNulas(matrizAux11);
//                        Mat_lines = matrizAux11.copy();
//                        //--------------------------------------------------------------   
//                        lines.setEntry(j, 0, lines.getEntry(j, 0) * (-1));
//                    }
//
//                    if (lines.getEntry(j, 2) == nodo) {
//                        //--------------------------------------------------------------
//                        contador2++;
//                        double[] vectorAux_B = new double[vector_nodos.getColumnDimension() + contador2];
//                        RealMatrix vectorAux_A = new Array2DRowRealMatrix(vectorAux_B);
//                        vectorAux_A = vectorAux_A.transpose();
//                        vectorAux_A.setSubMatrix(vector_nodos.getData(), 0, 0);
//                        vectorAux_A.setEntry(0, vector_nodos.getColumnDimension(), lines.getEntry(j, 1));
//                        vectorAux_A = vectorAux_A.transpose();
//                        vectorAux_A = Metodos.suprimirFilasNulas(vectorAux_A);
//                        vector_nodos = vectorAux_A.transpose();
//
//                        //Mat_lines=[Mat_lines;lines(i,:)];
//                        contador3++;
//                        double[][] matrizAux1 = new double[Mat_lines.getRowDimension() + contador3][Mat_lines.getColumnDimension()];
//                        RealMatrix matrizAux11 = new Array2DRowRealMatrix(matrizAux1);
//                        matrizAux11.setSubMatrix(Mat_lines.getData(), 0, 0);
//                        RealMatrix vectorAux2 = new Array2DRowRealMatrix(lines.getRow(j));
//                        double[][] vectorAux = vectorAux2.transpose().getData();
//                        matrizAux11.setSubMatrix(vectorAux, Mat_lines.getRowDimension(), 0);
//                        matrizAux11 = Metodos.suprimirFilasNulas(matrizAux11);
//                        Mat_lines = matrizAux11.copy();
//                        //--------------------------------------------------------------
//
//                        //[a,b]=size(Mat_lines);     
//                        a = Mat_lines.getRowDimension();
//                        b = Mat_lines.getColumnDimension();
//
//                        // Mat_lines(a,2)=lines(i,3);
//                        Mat_lines.setEntry(a - 1, 1, lines.getEntry(j, 2));
//
//                        //Mat_lines(a,3)=lines(i,2); 
//                        Mat_lines.setEntry(a - 1, 2, lines.getEntry(j, 1));
//
//                        //lines(i,1)=-lines(i,1);    
//                        lines.setEntry(j, 0, lines.getEntry(j, 0) * (-1));
//                        contador1++;
//                    }
//
//                }
//
//            }
//
//            if (cont == contador_vector) {
//
//                a = vector_nodos.getColumnDimension() * vector_nodos.getRowDimension();
//                if (a == cont) {
//                    cont = n_elem;
//                } else {
//                    cont = a;
//                    contador_vector++;
//                }
//
//            } else {
//                contador_vector++;
//            }
//        }
//
//        double[] columna1_lines = lines.getColumn(0);
//        for (int i = 0; i < columna1_lines.length; i++) {
//            columna1_lines[i] = columna1_lines[i] * -1;
//        }
//        lines.setColumn(0, columna1_lines);
//
//        //__________Cálculo de Impedancias propias y mutuas de las líneas________________________
//        RealMatrix tipcond = new Array2DRowRealMatrix(new double[Mat_lines.getRowDimension()]);
//        RealMatrix Longitud = new Array2DRowRealMatrix(Mat_lines.getColumn(6));
//        Longitud = Longitud.scalarMultiply(Math.pow(10, -3));
//        tipcond = Mat_lines.getColumnMatrix(3);
//
//        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
//        //>> Subrutina de Parámetros <<    
//        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
////        ComplexMatrix imped = new ComplexMatrix(3, 3, nl);
////        ComplexMatrix ImpedLD = new ComplexMatrix(3, 3, nl);
////        ComplexMatrix impedLD = new ComplexMatrix(3, 3, nl);
////        ComplexMatrix Z1 = new ComplexMatrix(4, 4, nl);
//        for (int u = 0; u < nl; u++) {
//            RealMatrix vcond = new Array2DRowRealMatrix(M_Cond.getRow((int) tipcond.getEntry(u, 0) - 1));
//            for (int i = 0; i < 4; i++) {
//                for (int j = 0; j < 4; j++) {
//                    // Z1 = new ComplexMatrix(a, b, u);
//                    if (i == j) {
//                        if (i == 3 && j == 3) {
////                            Z1.setElementThreeD(i, j, u, vcond.getEntry(6, 0), vcond.getEntry(7, 0));
////                            //Z1.setElementThreeD(i, j, u, vcond.getEntry(6), vcond.getEntry(7));
////                            //_______Corrección de Carson___________________________________________________
////                            //Z2(a,b,i)=Z1(a,b,i) + 9.88*10^(-4)*f + j*12.566*10^(-4)*f*log(658*sqrt(ro/f));
////                            //Z1[u][a][b] = impedancia;//en MATLAB Z1(a,b,u) es (fila,columna,matriz)       
////                        } else {
////                            Z1.setElementThreeD(i, j, u, vcond.getEntry(2, 0), vcond.getEntry(3, 0));
////                            //_______Corrección de Carson___________________________________________________
////                            //Z2(a,b,i)=Z1(a,b,i) + 9.88*10^(-4)*f + j*12.566*10^(-4)*f*log(658*sqrt(ro/f));
////                        }
////                    } else {
////                        Z1.setElementThreeD(i, j, u, 0, 12.566 * Math.pow(10, -4) * frecuencia * Math.log(1 / (M_Dist.getEntry(i, j))));
//
//                           //_______Corrección de Carson___________________________________________________
//                            //Z2(a,b,i)=Z1(a,b,i) + 9.88*10^(-4)*f + j*12.566*10^(-4)*f*log(658*sqrt(ro/f));
//                        }
//                    }
//                }
//                             //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
//                //  >> Reducción del Neutro <<    
//                //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
//
//                for (int m = 0; m < 3; m++) {
//                    for (int n = 0; n < 3; n++) {
//                        // imped(m,n,u)=Z1(m,n,u)-(Z1(m,4,u)*Z1(4,n,u))/Z1(4,4,u);
////                    Complexx elemento1;
////                    Complexx elemento2;
////                    Complexx elemento3;
////                    Complexx elemento4;
////                    elemento1 = Z1.getElementReferenceThreeD(m, n, u);
////                    elemento2 = Z1.getElementReferenceThreeD(m, 3, u);
////                    elemento3 = Z1.getElementReferenceThreeD(3, n, u);
////                    elemento4 = Z1.getElementReferenceThreeD(3, 3, u);
////                    imped.setElementThreeD(m, n, u, elemento1.minus(elemento2.times(elemento3).over(elemento4)));
////
////                        //ImpedLD(m,n,u)=imped(m,n,u).*longitud(u);
////                    //ImpedLD = imped.times(Longitud[u][1]);
////                    Complexx multiplicador = imped.getElementReferenceThreeD(m, n, u).times(Longitud.getEntry(u, 0));
////                    ImpedLD.setElementThreeD(m, n, u, multiplicador);
////
////                    //impedLD(m,n,u)=ImpedLD(m,n,u)/Zbase;
////                    Complexx divisor = ImpedLD.getElementReferenceThreeD(m, n, u).over(Zbase);
////                    impedLD.setElementThreeD(m, n, u, divisor);//Obtiene la impedancia en P.U
//                    }
//                }
//
//            }
//
//        //______________________________________________________________________________________    
//            //__________________________                        ____________________________________     
//            //__________________________ Conectividad de la red ____________________________________
//            //______________________________________________________________________________________     
//            double[] sol = {1};
//            RealMatrix sol1 = new Array2DRowRealMatrix(sol);
//            int contadorIf = 0;
//            double[] auxConectividadRed = new double[vector_nodos.getRowDimension() * vector_nodos.getColumnDimension()];
//
//            for (int i = 2; i < n_nodos; i++) {
//
//                int counter = 0;
//                for (int j = 0; j < vector_nodos.getColumnDimension(); j++) {
//                    if (vector_nodos.getEntry(0, j) == i) {
//                        counter++;
//                    }
//                }
//                if (counter == 0) {
//                    contadorIf++;
//                    double[] auxIf = new double[1 + contadorIf];
//                    RealMatrix auxIf1 = new Array2DRowRealMatrix(auxIf);
//                    auxIf1 = auxIf1.transpose();
//                    auxIf1.setSubMatrix(sol1.getData(), 0, 0);
//                    auxIf1.setEntry(0, sol1.getColumnDimension(), i);
//                    sol1 = auxIf1;
//                }
//            }
//
//            Volt = new double[n_nodos][3];
//
//            if (sol1.getRowDimension() * sol1.getColumnDimension() > 1) {
//                Perdidas = 1;
//                Iter = iter_max;
//
//                for (int ii = 0; ii < n_nodos; ii++) {
//                    for (int jj = 0; jj < 3; jj++) {
//                        Volt[ii][jj] = 0;
//                    }
//                }
//            } else {
//                for (int ii = 0; ii < n_nodos; ii++) {
//                    for (int jj = 0; jj < 3; jj++) {
//                        Volt[ii][jj] = 1;
//                    }
//                }
//
//                Volt1 = new ComplexMatrix(Volt);
//
//                //delta=[zeros(n_nodos,1) -2.094*ones(n_nodos,1) 2.094*ones(n_nodos,1)];    
//                double[][] delta1;
//                delta1 = new double[n_nodos][3];
//                delta = new Array2DRowRealMatrix(delta1);
//                double[] columna1delta;
//                double[] columna2delta;
//                double[] columna3delta;
//                columna1delta = new double[n_nodos];
//                columna2delta = new double[n_nodos];
//                columna3delta = new double[n_nodos];
//                for (int aa = 0; aa < n_nodos; aa++) {
//                    columna1delta[aa] = 0;
//                }
//                for (int aa = 0; aa < n_nodos; aa++) {
//                    columna2delta[aa] = -2.094;
//                }
//                for (int aa = 0; aa < n_nodos; aa++) {
//                    columna3delta[aa] = 2.094;
//                }
//                delta.setColumn(0, columna1delta);
//                delta.setColumn(1, columna2delta);
//                delta.setColumn(2, columna3delta);
//
//                Volt_ant = new ComplexMatrix(new double[Volt1.getNrow()][Volt1.getNcol()]);
//                Volt_ant = Volt1;
//                error_max = 1;
//                Iter = 0;
//
//                while (error_max > error && Iter < iter_max) {
//
//                    Iter = Iter + 1;
//
//            //______________Cálculo POTENCIAS (PROCESO AGUAS ARRIBA)_____________________
//                    // System.out.println("Calculo de Potencias 'Proceso Aguas Arriba'");
//                    P_perdidas = new Array2DRowRealMatrix(new double[n_nodos][3]);
//                    Q_perdidas = new Array2DRowRealMatrix(new double[n_nodos][3]);
//                    Ilin = new ComplexMatrix(n_elem, 3);
//
//                    for (int g = n_elem - 1; g >= 0; g--) {
//
////                    Zaa = impedLD.getElementReferenceThreeD(0, 0, g);//Complejo
////                    Zab = impedLD.getElementReferenceThreeD(0, 1, g);//Complejo
////                    Zac = impedLD.getElementReferenceThreeD(0, 2, g);//Complejo
////                    Zba = impedLD.getElementReferenceThreeD(1, 0, g);//Complejo
////                    Zbb = impedLD.getElementReferenceThreeD(1, 1, g);//Complejo
////                    Zbc = impedLD.getElementReferenceThreeD(1, 2, g);//Complejo
////                    Zca = impedLD.getElementReferenceThreeD(2, 0, g);//Complejo
////                    Zcb = impedLD.getElementReferenceThreeD(2, 1, g);//Complejo
////                    Zcc = impedLD.getElementReferenceThreeD(2, 2, g);//Complejo
//                        nodo_ori = Mat_lines.getEntry(g, 1);//Real  
//                        nodo_des = Mat_lines.getEntry(g, 2);//Real 
//
////                    Yaa = Zaa.inverse();//Complejo
////                    Ybb = Zbb.inverse();//Complejo
////                    Ycc = Zcc.inverse();//Complejo
//                    //   __________Potencias "aguas abajo" del nodo(i+1)_______________________________
//                        //   correspondiente a la suma de las cargas y pérdidas "aguas abajo" del nodo(i+1)
//                        //................FASE A...........................................................
//                        P_equivalente.setEntry((int) nodo_des - 1, 0, P_equivalente.getEntry((int) nodo_des - 1, 0) + M_Pc.getEntry((int) nodo_des - 1, 0));
//                        Q_equivalente.setEntry((int) nodo_des - 1, 0, Q_equivalente.getEntry((int) nodo_des - 1, 0) + M_Qc.getEntry((int) nodo_des - 1, 0));
//
//                        //................FASE B...........................................................
//                        P_equivalente.setEntry((int) nodo_des - 1, 1, P_equivalente.getEntry((int) nodo_des - 1, 1) + M_Pc.getEntry((int) nodo_des - 1, 1));
//                        Q_equivalente.setEntry((int) nodo_des - 1, 1, Q_equivalente.getEntry((int) nodo_des - 1, 1) + M_Qc.getEntry((int) nodo_des - 1, 1));
//
//                        //................FASE C...........................................................
//                        P_equivalente.setEntry((int) nodo_des - 1, 2, P_equivalente.getEntry((int) nodo_des - 1, 2) + M_Pc.getEntry((int) nodo_des - 1, 2));
//                        Q_equivalente.setEntry((int) nodo_des - 1, 2, Q_equivalente.getEntry((int) nodo_des - 1, 2) + M_Qc.getEntry((int) nodo_des - 1, 2));
//
//                    //____________Corriente entre el nodo(i) y el nodo(i+1)____________________________
//                    //             |  S   |*     |       P + jQ            |*    
//                        //       I  =  |------|   =  |-------------------------|  [A]
//                        //             |  V   |      | Vrms(cos(#)+ j*sin(#))  |     
////                    Complexx auxx1 = new Complexx(P_equivalente.getEntry((int) nodo_des - 1, 0), Q_equivalente.getEntry((int) nodo_des - 1, 0));
////                    Complexx auxx2 = new Complexx(Volt1.getElementCopy((int) nodo_des - 1, 0).abs() * Math.cos(delta.getEntry((int) nodo_des - 1, 0)),
////                            Volt1.getElementCopy((int) nodo_des - 1, 0).abs() * Math.sin(delta.getEntry((int) nodo_des - 1, 0)));
////
////                    Complexx auxx3 = new Complexx(P_equivalente.getEntry((int) nodo_des - 1, 1), Q_equivalente.getEntry((int) nodo_des - 1, 1));
////                    Complexx auxx4 = new Complexx(Volt1.getElementCopy((int) nodo_des - 1, 1).abs() * Math.cos(delta.getEntry((int) nodo_des - 1, 1)),
////                            Volt1.getElementCopy((int) nodo_des - 1, 1).abs() * Math.sin(delta.getEntry((int) nodo_des - 1, 1)));
////
////                    Complexx auxx5 = new Complexx(P_equivalente.getEntry((int) nodo_des - 1, 2), Q_equivalente.getEntry((int) nodo_des - 1, 2));
////                    Complexx auxx6 = new Complexx(Volt1.getElementCopy((int) nodo_des - 1, 2).abs() * Math.cos(delta.getEntry((int) nodo_des - 1, 2)),
////                            Volt1.getElementCopy((int) nodo_des - 1, 2).abs() * Math.sin(delta.getEntry((int) nodo_des - 1, 2)));
////
////                    //................FASE A...........................................................
////                    Complexx auxx7 = auxx1.over(auxx2);
////                    Ilin.setElement(g, 0, auxx7.conjugate());
////
////                    //................FASE B...........................................................
////                    Complexx auxx8 = auxx3.over(auxx4);
////                    Ilin.setElement(g, 1, auxx8.conjugate());
////
////                    //................FASE C...........................................................
////                    Complexx auxx9 = auxx5.over(auxx6);
////                    Ilin.setElement(g, 2, auxx9.conjugate());
//                    //.....................................................................
//                        //_______________________________Fase A_________________________________________________
//                        if (Volt1.getElementCopy((int) nodo_des - 1, 0).abs() != 0) {
//
//                            Complexx aux11;
//                            Complexx aux12;
//                            double aux13;
//                            Complexx aux14;
//
//                            aux11 = Zab.times(Ilin.getElementReference(g, 1)).times(Ilin.getElementReference(g, 0).conjugate());  //Zab*Ilin(g,2)
//                            aux12 = Zac.times(Ilin.getElementReference(g, 2)).times(Ilin.getElementReference(g, 0).conjugate());//Zac*Ilin(g,3))*conj(Ilin(g,1))
//                            aux13 = (Ilin.getElementReference(g, 0).pow(2)).abs();//abs((Ilin(g,1))^2)
//                            aux14 = Zaa.times(aux13);//"abs((Ilin(g,1))^2)/Yaa"   ó   "Zaa*abs((Ilin(g,1))^2)"
//
//                            Sperd_1 = (aux11.plus(aux12).plus(aux14)); //Sperd_1=(Zab*Ilin(g,2) + Zac*Ilin(g,3))*conj(Ilin(g,1)) + abs((Ilin(g,1))^2)/Yaa;
//                            double Sperd_1_Real = Sperd_1.getReal();
//                            double Sperd_1_Imag = Sperd_1.getImag();
//                            P_perdidas.setEntry((int) nodo_des - 1, 0, Sperd_1.getReal()); //P_perdidas(nodo_des,1)=real(Sperd_1);
//                            Q_perdidas.setEntry((int) nodo_des - 1, 0, Sperd_1.getImag()); //Q_perdidas(nodo_des,1)=imag(Sperd_1);
//
//                        } else {
//
//                            P_perdidas.setEntry((int) nodo_des - 1, 0, P_perdidas.getEntry((int) nodo_des - 1, 0)); //P_perdidas(nodo_des,1)=P_perdidas(nodo_des,1);
//                            Q_perdidas.setEntry((int) nodo_des - 1, 0, Q_perdidas.getEntry((int) nodo_des - 1, 0));//Q_perdidas(nodo_des,1)=Q_perdidas(nodo_des,1);
//                        }
//
//                        //___________________________________________Fase B________________________________________________________ 
//                        if (Volt1.getElementCopy((int) nodo_des - 1, 1).abs() != 0) {
//
//                            Complexx aux11;
//                            Complexx aux12;
//                            double aux13;
//                            Complexx aux14;
//
//                            aux11 = Zba.times(Ilin.getElementReference(g, 0)).times(Ilin.getElementReference(g, 1).conjugate());  //Zba*Ilin(g,1)
//                            aux12 = Zbc.times(Ilin.getElementReference(g, 2)).times(Ilin.getElementReference(g, 1).conjugate());//Zbc*Ilin(g,3))*conj(Ilin(g,2))
//                            aux13 = Ilin.getElementReference(g, 1).pow(2).abs();//abs((Ilin(g,2))^2)
//                            aux14 = Zbb.times(aux13);//"abs((Ilin(g,2))^2)/Ybb"   ó   "Zbb*abs((Ilin(g,2))^2)"
//
//                            Sperd_2 = (aux11.plus(aux12).plus(aux14)); //Sperd_1=(Zba*Ilin(g,1) + Zbc*Ilin(g,3))*conj(Ilin(g,2)) + abs((Ilin(g,2))^2)/Ybb;
//
//                            P_perdidas.setEntry((int) nodo_des - 1, 1, Sperd_2.getReal()); //P_perdidas(nodo_des,2)=real(Sperd_2);
//                            Q_perdidas.setEntry((int) nodo_des - 1, 1, Sperd_2.getImag()); //Q_perdidas(nodo_des,2)=imag(Sperd_2);
//
//                        } else {
//
//                            P_perdidas.setEntry((int) nodo_des - 1, 1, P_perdidas.getEntry((int) nodo_des - 1, 1)); //P_perdidas(nodo_des,2)=P_perdidas(nodo_des,2);
//                            Q_perdidas.setEntry((int) nodo_des - 1, 1, Q_perdidas.getEntry((int) nodo_des - 1, 1));//Q_perdidas(nodo_des,2)=Q_perdidas(nodo_des,2);
//
//                        }
//
//                        //___________________________________________Fase C________________________________________________________ 
//                        if (Volt1.getElementCopy((int) nodo_des - 1, 2).abs() != 0) {
//
//                            Complexx aux11;
//                            Complexx aux12;
//                            double aux13;
//                            Complexx aux14;
//
//                            aux11 = Zca.times(Ilin.getElementReference(g, 0)).times(Ilin.getElementReference(g, 2).conjugate());  //Zca*Ilin(g,1)*conj(Ilin(g,3))
//                            aux12 = Zcb.times(Ilin.getElementReference(g, 1)).times(Ilin.getElementReference(g, 2).conjugate());//Zcb*Ilin(g,2))*conj(Ilin(g,3))
//                            aux13 = Ilin.getElementReference(g, 2).pow(2).abs();//abs((Ilin(g,3))^2)
//                            aux14 = Zcc.times(aux13);//"abs((Ilin(g,3))^2)/Ycc"   ó   "Zcc*abs((Ilin(g,3))^2)"
//
//                            Sperd_3 = (aux11.plus(aux12).plus(aux14)); //Sperd_3=(Zca*Ilin(g,1) + Zcb*Ilin(g,2))*conj(Ilin(g,3)) + abs((Ilin(g,3))^2)/Ycc;
//
//                            P_perdidas.setEntry((int) nodo_des - 1, 2, Sperd_3.getReal()); //P_perdidas(nodo_des,3)=real(Sperd_3);
//                            Q_perdidas.setEntry((int) nodo_des - 1, 2, Sperd_3.getImag()); //Q_perdidas(nodo_des,3)=imag(Sperd_3);
//
//                        } else {
//
//                            P_perdidas.setEntry((int) nodo_des - 1, 2, P_perdidas.getEntry((int) nodo_des - 1, 2)); //P_perdidas(nodo_des,3)=P_perdidas(nodo_des,3);
//                            Q_perdidas.setEntry((int) nodo_des - 1, 2, Q_perdidas.getEntry((int) nodo_des - 1, 2));//Q_perdidas(nodo_des,3)=Q_perdidas(nodo_des,3);
//
//                        }
//
////__________________________________________Potencias equivalente en el nodo(i)____________________________________________________________
//                        //___________________Fase A_________________________________________________________________________________
//                        //P_equivalente(nodo_ori,1)=P_equivalente(nodo_ori,1)  + P_perdidas(nodo_des,1) + P_equivalente(nodo_des,1);          
//                        //Q_equivalente(nodo_ori,1)=Q_equivalente(nodo_ori,1)  + Q_perdidas(nodo_des,1) + Q_equivalente(nodo_des,1);          
//                        P_equivalente.setEntry((int) nodo_ori - 1, 0, P_equivalente.getEntry((int) nodo_ori - 1, 0)
//                                + P_perdidas.getEntry((int) nodo_des - 1, 0) + P_equivalente.getEntry((int) nodo_des - 1, 0));
//
//                        Q_equivalente.setEntry((int) nodo_ori - 1, 0, Q_equivalente.getEntry((int) nodo_ori - 1, 0)
//                                + Q_perdidas.getEntry((int) nodo_des - 1, 0) + Q_equivalente.getEntry((int) nodo_des - 1, 0));
//
//                    //__________________Fase B____________________________________________________________________________________
//                        // P_equivalente(nodo_ori,2)=P_equivalente(nodo_ori,2)  + P_perdidas(nodo_des,2) + P_equivalente(nodo_des,2);        
//                        // Q_equivalente(nodo_ori,2)=Q_equivalente(nodo_ori,2)  + Q_perdidas(nodo_des,2) + Q_equivalente(nodo_des,2);        
//                        P_equivalente.setEntry((int) nodo_ori - 1, 1, P_equivalente.getEntry((int) nodo_ori - 1, 1)
//                                + P_perdidas.getEntry((int) nodo_des - 1, 1) + P_equivalente.getEntry((int) nodo_des - 1, 1));
//
//                        Q_equivalente.setEntry((int) nodo_ori - 1, 1, Q_equivalente.getEntry((int) nodo_ori - 1, 1)
//                                + Q_perdidas.getEntry((int) nodo_des - 1, 1) + Q_equivalente.getEntry((int) nodo_des - 1, 1));
//
//                    //__________________Fase C___________________________________________________________________________________
//                        //P_equivalente(nodo_ori,3)=P_equivalente(nodo_ori,3)  + P_perdidas(nodo_des,3) + P_equivalente(nodo_des,3);         
//                        //Q_equivalente(nodo_ori,3)=Q_equivalente(nodo_ori,3)  + Q_perdidas(nodo_des,3) + Q_equivalente(nodo_des,3);         
//                        P_equivalente.setEntry((int) nodo_ori - 1, 2, P_equivalente.getEntry((int) nodo_ori - 1, 2)
//                                + P_perdidas.getEntry((int) nodo_des - 1, 2) + P_equivalente.getEntry((int) nodo_des - 1, 2));
//
//                        Q_equivalente.setEntry((int) nodo_ori - 1, 2, Q_equivalente.getEntry((int) nodo_ori - 1, 2)
//                                + Q_perdidas.getEntry((int) nodo_des - 1, 2) + Q_equivalente.getEntry((int) nodo_des - 1, 2));
//
//                    }
//
//                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::                
//                    //::::::::::::::::::::::::::::::::::::::::::----------------------------------------------:::::::::::::::::::::::::::::::::::::::::::                
//                    //__________________________________________| Cálculo de TENSIONES (proceso aguas abajo) |___________________________________________                
//                    //::::::::::::::::::::::::::::::::::::::::::----------------------------------------------:::::::::::::::::::::::::::::::::::::::::::                
//                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::                
//                    Volt_int = new ComplexMatrix(n_nodos, 3);
//
//                    for (int g = 0; g < n_elem; g++) {
//
////                    Zaa = impedLD.getElementReferenceThreeD(0, 0, g);//MATRIZ
////                    Zab = impedLD.getElementReferenceThreeD(0, 1, g);//MATRIZ
////                    Zac = impedLD.getElementReferenceThreeD(0, 2, g);//MATRIZ
////                    Zba = impedLD.getElementReferenceThreeD(1, 0, g);//MATRIZ
////                    Zbb = impedLD.getElementReferenceThreeD(1, 1, g);//MATRIZ
////                    Zbc = impedLD.getElementReferenceThreeD(1, 2, g);//MATRIZ
////                    Zca = impedLD.getElementReferenceThreeD(2, 0, g);//MATRIZ
////                    Zcb = impedLD.getElementReferenceThreeD(2, 1, g);//MATRIZ
////                    Zcc = impedLD.getElementReferenceThreeD(2, 2, g);//MATRIZ
//                        nodo_ori = Mat_lines.getEntry(g, 1);
//                        nodo_des = Mat_lines.getEntry(g, 2);
//                        Yaa = Zaa.inverse();//MATRIZ
//                        Ybb = Zbb.inverse();//MATRIZ
//                        Ycc = Zcc.inverse();//MATRIZ
//
//            //________________________________Tensiones Intermedias________________________________________________       
//                        //_______________________________________FASE A__________________________________     
//                        Complexx vo;
//                        Complexx Volt_int1;
//                        double scalar = -1;
//                        vo = new Complexx(Volt1.getElementCopy((int) nodo_ori - 1, 0));//Volt(nodo_ori,1)                    
//                        Complexx v1 = Zab.times(Ilin.getElementReference(g, 1));
//                        Complexx v2 = Zac.times(Ilin.getElementReference(g, 2));
//                        Volt_int1 = vo.plus(v1.times(scalar).plus(v2.times(scalar)));
//
//                        Volt_int.setElement((int) nodo_des - 1, 0, Volt_int1);
//
//                        //_______________________________________FASE B_________________________________      
//                        Complexx voo;
//                        Complexx Volt_int11;
//                        voo = new Complexx(Volt1.getElementCopy((int) nodo_ori - 1, 1));//Volt(nodo_ori,1)                    
//                        Complexx v11 = Zba.times(Ilin.getElementReference(g, 0));
//                        Complexx v21 = Zbc.times(Ilin.getElementReference(g, 2));
//                        Volt_int11 = vo.plus(v11.times(scalar).plus(v21.times(scalar)));
//
//                        Volt_int.setElement((int) nodo_des - 1, 1, Volt_int11);
//
//                        //_______________________________________FASE C_________________________________       
//                        Complexx vooo;
//                        Complexx Volt_int111;
//                        vooo = new Complexx(Volt1.getElementCopy((int) nodo_ori - 1, 2));//Volt(nodo_ori,1)
//                        Complexx v111 = Zca.times(Ilin.getElementReference(g, 0));
//                        Complexx v211 = Zcb.times(Ilin.getElementReference(g, 1));
//                        Volt_int111 = vo.plus(v111.times(scalar).plus(v211.times(scalar)));
//
//                        Volt_int.setElement((int) nodo_des - 1, 2, Volt_int111);
//
//                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::        
//                        //_________________________________Cálculo de coeficientes____________________________________________:        
//                        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//                        //_______________________________________Fase A________________________________________
//                        //A1=(real(Yaa))^2 + (imag(Yaa))^2; 
//                        double A11 = Yaa.squareAbs(); //Math.pow(Yaa.getReal(),2) + Math.pow(Yaa.getImag(),2);
//
//                        //B1=2*P_equivalente(nodo_des,1)*real(Yaa) - 2*Q_equivalente(nodo_des,1)*imag(Yaa) - abs(Volt_int(nodo_des,1))^2*abs(Yaa)^2;
//                        double B11;
//                        double elemento1; //2*P_equivalente(nodo_des,1)*real(Yaa) 
//                        double elemento2; //2*Q_equivalente(nodo_des,1)*imag(Yaa) 
//                        double elemento3; //abs(Volt_int(nodo_des,1))^2*abs(Yaa)^2
//                        elemento1 = 2 * P_equivalente.getEntry((int) nodo_des - 1, 0) * Yaa.getReal();
//                        elemento2 = 2 * Q_equivalente.getEntry((int) nodo_des - 1, 0) * Yaa.getImag();
//                        elemento3 = Math.pow((Volt_int.getElementReference((int) nodo_des - 1, 0).abs()), 2) * Math.pow(Yaa.abs(), 2);
//                        B11 = elemento1 - elemento2 - elemento3;
//
//                        //C1=P_equivalente(nodo_des,1)^2 + Q_equivalente(nodo_des,1)^2;
//                        double C11 = Math.pow(P_equivalente.getEntry((int) nodo_des - 1, 0), 2) + Math.pow(Q_equivalente.getEntry((int) nodo_des - 1, 0), 2);
//
//                        //Volt(nodo_des,1)=sqrt((-B1 + sqrt(B1^2 - 4*A1*C1))/(2*A1));
//                        Volt1.setElement((int) nodo_des - 1, 0, Math.sqrt((-B11 + Math.sqrt(Math.pow(B11, 2) - 4 * A11 * C11)) / (2 * A11)), 0);
//
//                        //numerador= Q_equivalente(nodo_des,1) - abs(Volt(nodo_des,1))^2*real(Yaa);
//                        double numerador;
//                        numerador = Q_equivalente.getEntry((int) nodo_des - 1, 0) - Math.pow((Volt_int.getElementReference((int) nodo_des - 1, 0).abs()), 2)
//                                * Yaa.getReal();
//
//                        //denominador=P_equivalente(nodo_des,1) + abs(Volt(nodo_des,1))^2*imag(Yaa);
//                        double denominador;
//                        denominador = P_equivalente.getEntry((int) nodo_des - 1, 0) + Math.pow((Volt_int.getElementReference((int) nodo_des - 1, 0).abs()), 2)
//                                * Yaa.getImag();
//
//                   //if denominador~=0
//                        //     delta(nodo_des,1)=angle(Volt_int(nodo_des,1)) + angle(Yaa) + atan(numerador/denominador);
//                        if (denominador != 0) {
//                            delta.setEntry((int) nodo_des - 1, 0,
//                                    (Math.atan(Volt_int.getElementCopy((int) nodo_des - 1, 0).getImag() / Volt_int.getElementCopy((int) nodo_des - 1, 0).getReal()))
//                                    + (Math.atan(Yaa.getImag() / Yaa.getReal()))
//                                    + (Math.atan(numerador / denominador)));
//                        } else {
//                            //delta(nodo_des,1)=0;
//                            delta.setEntry((int) nodo_des - 1, 0, 0);
//                        }
//
//                   //_______________________________________Fase B_________________________________________________
//                        //A2=(real(Ybb))^2 + (imag(Ybb))^2;
//                        double A22 = Ybb.squareAbs();
//
//                        //B2=2*P_equivalente(nodo_des,2)*real(Ybb) - 2*Q_equivalente(nodo_des,2)*imag(Ybb) - abs(Volt_int(nodo_des,2))^2*abs(Ybb)^2;
//                        double B22;
//                        double elemento11; //2*P_equivalente(nodo_des,2)*real(Ybb) 
//                        double elemento22; //2*Q_equivalente(nodo_des,2)*imag(Ybb) 
//                        double elemento33; //abs(Volt_int(nodo_des,2))^2*abs(Ybb)^2
//                        elemento11 = 2 * P_equivalente.getEntry((int) nodo_des - 1, 1) * Ybb.getReal();
//                        elemento22 = 2 * Q_equivalente.getEntry((int) nodo_des - 1, 1) * Ybb.getImag();
//                        elemento33 = Math.pow((Volt_int.getElementReference((int) nodo_des - 1, 1).abs()), 2) * Math.pow(Ybb.abs(), 2);
//                        B22 = elemento11 - elemento22 - elemento33;
//
//                        //C2=P_equivalente(nodo_des,2)^2 + Q_equivalente(nodo_des,2)^2;
//                        double C22 = Math.pow(P_equivalente.getEntry((int) nodo_des - 1, 1), 2) + Math.pow(Q_equivalente.getEntry((int) nodo_des - 1, 1), 2);
//
//                        //Volt(nodo_des,2)=sqrt((-B2 + sqrt(B2^2 - 4*A2*C2))/(2*A2));
//                        Volt1.setElement((int) nodo_des - 1, 1, Math.sqrt((-B22 + Math.sqrt(Math.pow(B22, 2) - 4 * A22 * C22)) / (2 * A22)), 0);
//
//                        //numerador=Q_equivalente(nodo_des,2) - abs(Volt(nodo_des,2))^2*real(Ybb);
//                        numerador = Q_equivalente.getEntry((int) nodo_des - 1, 1) - Math.pow((Volt_int.getElementReference((int) nodo_des - 1, 1).abs()), 2)
//                                * Ybb.getReal();
//
//                        //denominador=P_equivalente(nodo_des,2) + abs(Volt(nodo_des,2))^2*imag(Ybb);
//                        denominador = P_equivalente.getEntry((int) nodo_des - 1, 1) + Math.pow((Volt_int.getElementReference((int) nodo_des - 1, 1).abs()), 2)
//                                * Ybb.getImag();
//
//                        if (denominador != 0) {//if denominador~=0
//                            delta.setEntry((int) nodo_des - 1, 1, //delta(nodo_des,2)
//                                    (Math.atan(Volt_int.getElementCopy((int) nodo_des - 1, 1).getImag() / Volt_int.getElementCopy((int) nodo_des - 1, 1).getReal())) + //angle(Volt_int(nodo_des,2))
//                                    (Math.atan(Ybb.getImag() / Ybb.getReal())) + //angle(Ybb)
//                                    (Math.atan(numerador / denominador))); //atan(numerador/denominador
//                        } else {
//
//                            delta.setEntry((int) nodo_des - 1, 1, 0); //delta(nodo_des,2)=0;
//                        }
//
//                   //_______________________________________Fase C_________________________________________________ 
//                        //A3=(real(Ycc))^2 + (imag(Ycc))^2;
//                        double A33 = Ycc.squareAbs();
//
//                        //B3=2*P_equivalente(nodo_des,3)*real(Ycc) - 2*Q_equivalente(nodo_des,3)*imag(Ycc) - abs(Volt_int(nodo_des,3))^2*abs(Ycc)^2;
//                        double B33;
//                        double elemento111; //2*P_equivalente(nodo_des,3)*real(Ycc) 
//                        double elemento222; //2*Q_equivalente(nodo_des,3)*imag(Ycc) 
//                        double elemento333; //abs(Volt_int(nodo_des,3))^2*abs(Ycc)^2
//                        elemento111 = 2 * P_equivalente.getEntry((int) nodo_des - 1, 2) * Ycc.getReal();
//                        elemento222 = 2 * Q_equivalente.getEntry((int) nodo_des - 1, 2) * Ybb.getImag();
//                        elemento333 = Math.pow((Volt_int.getElementReference((int) nodo_des - 1, 2).abs()), 2) * Math.pow(Ycc.abs(), 2);
//                        B33 = elemento111 - elemento222 - elemento333;
//
//                        //C3=P_equivalente(nodo_des,3)^2 + Q_equivalente(nodo_des,3)^2;
//                        double C33 = Math.pow(P_equivalente.getEntry((int) nodo_des - 1, 2), 2) + Math.pow(Q_equivalente.getEntry((int) nodo_des - 1, 2), 2);
//
//                        //Volt(nodo_des,3)=sqrt((-B3 + sqrt(B3^2 - 4*A3*C3))/(2*A3));
//                        Volt1.setElement((int) nodo_des - 1, 2, Math.sqrt((-B33 + Math.sqrt(Math.pow(B33, 2) - 4 * A33 * C33)) / (2 * A33)), 0);
//
//                        //numerador=Q_equivalente(nodo_des,3) - abs(Volt(nodo_des,3))^2*real(Ycc);
//                        numerador = Q_equivalente.getEntry((int) nodo_des - 1, 2) - Math.pow((Volt_int.getElementReference((int) nodo_des - 1, 2).abs()), 2)
//                                * Ycc.getReal();
//
//                        //denominador=P_equivalente(nodo_des,3) + abs(Volt(nodo_des,3))^2*imag(Ycc);
//                        denominador = P_equivalente.getEntry((int) nodo_des - 1, 2) + Math.pow((Volt_int.getElementReference((int) nodo_des - 1, 2).abs()), 2)
//                                * Ycc.getImag();
//
//                   //if denominador~=0
//                        //   delta(nodo_des,3)=angle(Volt_int(nodo_des,3)) + angle(Ycc) + atan(numerador/denominador);
//                        if (denominador != 0) {
//                            delta.setEntry((int) nodo_des - 1, 2,
//                                    (Math.atan(Volt_int.getElementCopy((int) nodo_des - 1, 2).getImag() / Volt_int.getElementCopy((int) nodo_des - 1, 2).getReal()))
//                                    + (Math.atan(Ycc.getImag() / Ycc.getReal()))
//                                    + (Math.atan(numerador / denominador)));
//                        } else {
//                            //delta(nodo_des,3)=0;
//                            delta.setEntry((int) nodo_des - 1, 2, 0);
//                        }
//
//                    }
//
//                // _____________________________________________________________Errores_____________________________________________________________         
//                    // _________________________________________________Fase A____________________________________________________
//                    // Volt_1(:,1)=Volt(:,1).*cos(-delta(:,1)) + j*Volt(:,1).*sin(-delta(:,1));                                   
//                    // error_1= abs(abs(Volt_1(1,1)) - abs(Volt_ant(1,1)));                                                       
//                    Volt_1 = new ComplexMatrix(Volt1.getNrow(), 3); // 3 Columnas
//                    double error_1;
//
//                    for (int condd = 0; condd < Volt1.getNrow(); condd++) {
//                        Volt_1.setElement(condd, 0, Volt1.getElementCopy(condd, 0).abs() * Math.cos(-1 * delta.getEntry(condd, 0)),
//                                Volt1.getElementCopy(condd, 0).abs() * Math.sin(-1 * delta.getEntry(condd, 0)));
//                    }
//
//                    error_1 = Math.abs(Volt_1.getElementCopy(0, 0).abs() - Volt_ant.getElementCopy(0, 0).abs());
//
//                // _________________________________________________Fase B____________________________________________________
//                    // Volt_1(:,1)=Volt(:,2).*cos(-delta(:,2)) + j*Volt(:,2).*sin(-delta(:,2));                                   
//                    // error_2= abs(abs(Volt_1(1,2)) - abs(Volt_ant(1,2)));                                                       
//                    double error_2;
//
//                    for (int condd = 0; condd < Volt1.getNrow(); condd++) {
//                        Volt_1.setElement(condd, 1, Volt1.getElementCopy(condd, 1).abs() * Math.cos(-1 * delta.getEntry(condd, 1)),
//                                Volt1.getElementCopy(condd, 1).abs() * Math.sin(-1 * delta.getEntry(condd, 1)));
//                    }
//
//                    error_2 = Math.abs(Volt_1.getElementCopy(0, 1).abs() - Volt_ant.getElementCopy(0, 1).abs());
//
//                // _________________________________________________Fase C____________________________________________________
//                    // Volt_1(:,1)=Volt(:,2).*cos(-delta(:,2)) + j*Volt(:,2).*sin(-delta(:,2));                                   
//                    // error_2= abs(abs(Volt_1(1,2)) - abs(Volt_ant(1,2)));                                                       
//                    double error_3;
//
//                    for (int condd = 0; condd < Volt1.getNrow(); condd++) {
//                        Volt_1.setElement(condd, 2, Volt1.getElementCopy(condd, 2).abs() * Math.cos(-1 * delta.getEntry(condd, 2)),
//                                Volt1.getElementCopy(condd, 2).abs() * Math.sin(-1 * delta.getEntry(condd, 2)));
//                    }
//
//                    error_3 = Math.abs(Volt_1.getElementCopy(0, 2).abs() - Volt_ant.getElementCopy(0, 2).abs());
//
//                    //v_errores1=[error_1, error_2, error_3];
//                    double[] v_errores2 = new double[3];
//                    error_max = Math.max(Math.max(error_1, error_2), error_3);
//
//                    double error_aux1;
//                    double error_aux2;
//                    double error_aux3;
//                    double error_aux;
//
//                    for (int k = 0; k < n_nodos; k++) {
//                        error_aux1 = Math.abs(Volt_1.getElementCopy(k, 0).abs() - Volt_ant.getElementCopy(k, 0).abs()); //error_aux1=abs(abs(Volt_1(k,1)) - abs(Volt_ant(k,1))); 
//                        error_aux2 = Math.abs(Volt_1.getElementCopy(k, 1).abs() - Volt_ant.getElementCopy(k, 1).abs()); //error_aux2=abs(abs(Volt_1(k,2)) - abs(Volt_ant(k,2))); 
//                        error_aux3 = Math.abs(Volt_1.getElementCopy(k, 2).abs() - Volt_ant.getElementCopy(k, 2).abs()); //error_aux3=abs(abs(Volt_1(k,3)) - abs(Volt_ant(k,3))); 
//                        error_aux = Math.max(Math.max(error_aux1, error_aux2), error_aux3);                      //error_aux=max(v_errores2);
//
//                        if (error_aux > error_max) {
//                            error_max = error_aux;
//                        }
//                    }
//
//                    Volt_ant = Volt_1;
//                }
//
////......................................................................................................................................            
////_____________________________________Corrientes y perdidas de líneas de la red_________________________________________________________________________
//                //Se generó un arreglo de n_elem x 4, con ceros 
//                int contador_para_FDP = 0;
//
//                for (int w = 0; w < n_elem; w++) {
//                    nodo_ori = Mat_lines.getEntry(w, 1);// nodo_ori=Mat_lines(w,2); 
//                    nodo_des = Mat_lines.getEntry(w, 2);// nodo_des=Mat_lines(w,3); 
//
//                    //_______Corriente entre el nodo(i) y el nodo(i+1)_______________________________
//                    Complexx numerador1 = new Complexx(P_equivalente.getEntry((int) nodo_des - 1, 0), Q_equivalente.getEntry((int) nodo_des - 1, 0));
//                    Complexx denominador1 = new Complexx((Volt1.getElementCopy((int) nodo_des - 1, 0)).abs() * (Math.cos(delta.getEntry((int) nodo_des - 1, 0))),
//                            (Volt1.getElementCopy((int) nodo_des - 1, 0)).abs() * (Math.sin(delta.getEntry((int) nodo_des - 1, 0))));
//
//                    Complexx numerador2 = new Complexx(P_equivalente.getEntry((int) nodo_des - 1, 1), Q_equivalente.getEntry((int) nodo_des - 1, 1));
//                    Complexx denominador2 = new Complexx((Volt1.getElementCopy((int) nodo_des - 1, 1)).abs() * (Math.cos(delta.getEntry((int) nodo_des - 1, 1))),
//                            (Volt1.getElementCopy((int) nodo_des - 1, 1)).abs() * (Math.sin(delta.getEntry((int) nodo_des - 1, 1))));
//
//                    Complexx numerador3 = new Complexx(P_equivalente.getEntry((int) nodo_des - 1, 2), Q_equivalente.getEntry((int) nodo_des - 1, 2));
//                    Complexx denominador3 = new Complexx((Volt1.getElementCopy((int) nodo_des - 1, 2)).abs() * (Math.cos(delta.getEntry((int) nodo_des - 1, 2))),
//                            (Volt1.getElementCopy((int) nodo_des - 1, 2)).abs() * (Math.sin(delta.getEntry((int) nodo_des - 1, 2))));
//
//                    Ilin.setElement(w, 0, (numerador1.over(denominador1)).conjugate());
//                    Ilin.setElement(w, 1, (numerador2.over(denominador2)).conjugate());
//                    Ilin.setElement(w, 2, (numerador3.over(denominador3)).conjugate());
//
//                    if (Mat_lines.getEntry(w, 1) == 1) {
//                        //FDP=[FDP;nodo_ori, nodo_des, aux33, abs(aux33)];
//                        contador_para_FDP++;
//                        ComplexMatrix FDP1 = new ComplexMatrix(contador_para_FDP, 4);
//                        if (contador_para_FDP != 1) {
//                            FDP1.setSubMatrix(0, 0, FDP.getArray());
//                            Complexx aux33 = new Complexx((Volt1.getElementCopy((int) nodo_des - 1, 0)).times(Ilin.getElementCopy(w, 0).conjugate()).plus(
//                                    (Volt1.getElementCopy((int) nodo_des - 1, 1)).times(Ilin.getElementCopy(w, 1).conjugate()).plus(
//                                            (Volt1.getElementCopy((int) nodo_des - 1, 2)).times(Ilin.getElementCopy(w, 2).conjugate()))));
//
//                            FDP1.setElement(w, 0, nodo_ori - 1, 0);
//                            FDP1.setElement(w, 1, nodo_des - 1, 0);
//                            FDP1.setElement(w, 2, aux33);
//                            FDP1.setElement(w, 3, aux33.abs(), 0);
//
//                        } else {
//                            Complexx aux33 = new Complexx((Volt1.getElementCopy((int) nodo_des - 1, 0)).times(Ilin.getElementCopy(w, 0).conjugate()).plus(
//                                    (Volt1.getElementCopy((int) nodo_des - 1, 1)).times(Ilin.getElementCopy(w, 1).conjugate()).plus(
//                                            (Volt1.getElementCopy((int) nodo_des - 1, 2)).times(Ilin.getElementCopy(w, 2).conjugate()))));
//
//                            FDP1.setElement(w, 0, nodo_ori - 1, 0);
//                            FDP1.setElement(w, 1, nodo_des - 1, 0);
//                            FDP1.setElement(w, 2, aux33);
//                            FDP1.setElement(w, 3, aux33.abs(), 0);
//                        }
//                        FDP = FDP1;
//                    }
//
//                }
//
//                //Ilin tiene 3 columnas que corresponden a las corrientes de linea complejas (a+jb)
//                RealMatrix IL1 = new Array2DRowRealMatrix(new double[Ilin.getNrow()][Ilin.getNcol()]);
//                RealMatrix PL = new Array2DRowRealMatrix(new double[n_elem][3]);
//                for (int h = 0; h < n_elem; h++) {
//
//                    // IL=abs(Ilin);
//                    for (int filasIL1 = 0; filasIL1 < IL1.getRowDimension(); filasIL1++) {
//                        for (int columnasIL1 = 0; columnasIL1 < IL1.getColumnDimension(); columnasIL1++) {
//                            IL1.setEntry(filasIL1, columnasIL1, Ilin.getElementCopy(filasIL1, columnasIL1).abs());
//                        }
//                    }
//
////                //_____________________FASE  A___________________________________
////                PL.setEntry(h, 0, Math.pow(IL1.getEntry(h, 0), 2) * impedLD.getElementReferenceThreeD(0, 0, h).getReal()); //PL(h,1)=(IL(h,1)^2)*real(impedLD(1,1,h));
////
////                //_____________________FASE  B___________________________________
////                PL.setEntry(h, 1, Math.pow(IL1.getEntry(h, 1), 2) * impedLD.getElementReferenceThreeD(1, 1, h).getReal()); //PL(h,2)=(IL(h,2)^2)*real(impedLD(2,2,h));
////
////                //_____________________FASE  C___________________________________
////                PL.setEntry(h, 2, Math.pow(IL1.getEntry(h, 2), 2) * impedLD.getElementReferenceThreeD(2, 2, h).getReal()); //PL(h,3)=(IL(h,3)^2)*real(impedLD(3,3,h));
//                }
//
//                //IL=[n11,n22,IL,PL];
//                IL = new Array2DRowRealMatrix(new double[Mat_lines.getRowDimension()][1 + IL1.getColumnDimension() + 1 + PL.getColumnDimension()]);
//                IL.setColumn(0, Mat_lines.getColumn(1));
//                IL.setColumn(1, Mat_lines.getColumn(2));
//                IL.setColumn(2, IL1.getColumn(0));
//                IL.setColumn(3, IL1.getColumn(1));
//                IL.setColumn(4, IL1.getColumn(2));
//                IL.setColumn(5, PL.getColumn(0));
//                IL.setColumn(6, PL.getColumn(1));
//                IL.setColumn(7, PL.getColumn(2));
//
//                //perd1=sum(PL(:,1));
//                double perd1 = 0;
//                for (int suma = 0; suma < PL.getRowDimension(); suma++) {
//                    perd1 += PL.getEntry(suma, 0);
//                }
//                //perd2=sum(PL(:,2));
//                double perd2 = 0;
//                for (int suma = 0; suma < PL.getRowDimension(); suma++) {
//                    perd2 += PL.getEntry(suma, 1);
//                }
//                //perd3=sum(PL(:,3));
//                double perd3 = 0;
//                for (int suma = 0; suma < PL.getRowDimension(); suma++) {
//                    perd3 += PL.getEntry(suma, 2);
//                }
//                Perdidas = (perd1 + perd2 + perd3);
//
//                //________________________Energia No Suministrada_____________(ENS)______________________
//                double indisp = 0.1 * 4;
//
//                //Pred=M_Cargas(:,2) + M_Cargas(:,3) + M_Cargas(:,4);
//                double[][] Pred1 = new double[M_Cargas.getRowDimension()][1];
//                RealMatrix Pred = new Array2DRowRealMatrix(Pred1);
//                Pred = M_Cargas.getColumnMatrix(1).add(M_Cargas.getColumnMatrix(2).add(M_Cargas.getColumnMatrix(3)));
//
//                //Qred=M_Cargas(:,5) + M_Cargas(:,6) + M_Cargas(:,7);
//                double[][] Qred1 = new double[M_Cargas.getRowDimension()][1];
//                RealMatrix Qred = new Array2DRowRealMatrix(Qred1);
//                Qred = M_Cargas.getColumnMatrix(4).add(M_Cargas.getColumnMatrix(5).add(M_Cargas.getColumnMatrix(6)));
//
//                //Sred=sqrt(Pred.^2 + Qred.^2);
//                double[][] Sred1 = new double[M_Cargas.getRowDimension()][1];
//                RealMatrix Sred = new Array2DRowRealMatrix(Sred1);
//                double[][] PredCuadrado1 = new double[M_Cargas.getRowDimension()][1];
//                RealMatrix PredCuadrado = new Array2DRowRealMatrix(PredCuadrado1);
//                double[][] QredCuadrado1 = new double[M_Cargas.getRowDimension()][1];
//                RealMatrix QredCuadrado = new Array2DRowRealMatrix(QredCuadrado1);
//                double[][] ArgumentoRaiz1 = new double[M_Cargas.getRowDimension()][1];
//                RealMatrix ArgumentoRaiz = new Array2DRowRealMatrix(ArgumentoRaiz1);
//
//                for (int contador4 = 0; contador4 < M_Cargas.getRowDimension(); contador4++) {
//                    PredCuadrado.setEntry(contador4, 0, Math.pow(Pred.getEntry(contador4, 0), 2));
//                    QredCuadrado.setEntry(contador4, 0, Math.pow(Qred.getEntry(contador4, 0), 2));
//                }
//                ArgumentoRaiz = PredCuadrado.add(QredCuadrado);
//                for (int contador5 = 0; contador5 < M_Cargas.getRowDimension(); contador5++) {
//                    Sred.setEntry(contador5, 0, Math.sqrt(ArgumentoRaiz.getEntry(contador5, 0)));
//                }
//
//                double ENS1 = 0;
//                for (int contador4 = 0; contador4 < M_Cargas.getRowDimension(); contador4++) {
//                    ENS1 += Sred.getEntry(contador4, 0);
//                }
//                ENS = indisp * ENS1;
//
//                //Voltajes=[abs(Volt), delta.*180./pi];
//                RealMatrix VoltAbs = new Array2DRowRealMatrix(Volt);
//                for (int contador6 = 0; contador6 < VoltAbs.getRowDimension(); contador6++) {
//                    VoltAbs.setEntry(contador6, 0, (Volt1.getElementCopy(contador6, 0)).abs());
//                    VoltAbs.setEntry(contador6, 1, (Volt1.getElementCopy(contador6, 1)).abs());
//                    VoltAbs.setEntry(contador6, 2, (Volt1.getElementCopy(contador6, 2)).abs());
//                }
//
//                RealMatrix deltaGrados = new Array2DRowRealMatrix(new double[delta.getRowDimension()][delta.getColumnDimension()]);
//                for (int contador6 = 0; contador6 < deltaGrados.getRowDimension(); contador6++) {
//                    deltaGrados.setEntry(contador6, 0, delta.getEntry(contador6, 0) * (180 / Math.PI));
//                    deltaGrados.setEntry(contador6, 1, delta.getEntry(contador6, 1) * (180 / Math.PI));
//                    deltaGrados.setEntry(contador6, 2, delta.getEntry(contador6, 2) * (180 / Math.PI));
//                }
//
//                Voltajes = new Array2DRowRealMatrix(new double[VoltAbs.getRowDimension()][VoltAbs.getColumnDimension() + deltaGrados.getColumnDimension()]);
//                //Voltajes.setColumnMatrix(0,VoltAbs);
//                Voltajes.setColumn(0, VoltAbs.getColumn(0));
//                Voltajes.setColumn(1, VoltAbs.getColumn(1));
//                Voltajes.setColumn(2, VoltAbs.getColumn(2));
//                Voltajes.setColumn(3, deltaGrados.getColumn(0));
//                Voltajes.setColumn(4, deltaGrados.getColumn(1));
//                Voltajes.setColumn(5, deltaGrados.getColumn(2));
//
//                Corriente = CorrientesLineas(IL, Ilin);
//            }
//        }
        //  Métodos get():     
    }

    public RealMatrix getCorriente() {
        return Corriente;
    }

    public ComplexMatrix getFDP() {
        return FDP;
    }

    public RealMatrix getVoltajes() {
        return Voltajes;
    }

    public RealMatrix getIL() {
        return IL;
    }

    public double getENS() {
        return ENS;
    }

    public double getPerdidas() {
        return Perdidas;
    }

}
