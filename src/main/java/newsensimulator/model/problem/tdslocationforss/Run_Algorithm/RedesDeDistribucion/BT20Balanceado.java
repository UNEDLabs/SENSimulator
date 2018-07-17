/*
                 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|                               
                 |xxxxxxx|               DATOS DE LA RED DE DISTRIBUCIÓN              |xxxxxxx|                               
                 |xxxxxxx|                         (20 Nodos)                         |xxxxxxx|                               
                 |xxxxxxx|                            2014                            |xxxxxxx|                               
                 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|    
 */
package newsensimulator.model.problem.tdslocationforss.Run_Algorithm.RedesDeDistribucion;

/*
 *@author Andreuw Madrid Carreño
 */
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class BT20Balanceado {

//       xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
//         >> Definición de Variables <<  
//       xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    private int nl = 27;                              // Número de líneas......................
    private int nn = 21;                              // Número de nodos.......................
    private int ng = 1;                               // Número de barras generadoras..........
    private int nc = 20;                              // Número de cargas......................
    private double Vb = 0.2193931;                    // (kV) de la red de B.T.................
    private int Vbm = 12;                             // (kV) de la red de M.T.................
    private int MVAb = 1;                             // (MVA).................................
    private int fe = 1;                               // Factor de energía.....................
    private double InT = 0.15;                        // Tasa de interes.......................
    private double InF = 0.18;                        // Tasa de descuento.....................
    private double Fc = 0.5;                          // Factor de carga.......................
    private double Fp = 0.325;                        // Fractor de pérdida....................

//     xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
//       >> Matriz de Líneas de la Red <<  
//     xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    private double[][] M_LineasT
            = //NL   Nombre   n1     n2    Cond     L(m)   Tfalla   Trep   Tman
            {{1, 0, 1, 2, 2, 100, 0.1, 4, 0},
            {2, 0, 2, 3, 2, 100, 0.1, 4, 0},
            {3, 0, 2, 7, 2, 100, 0.1, 4, 0},
            {4, 0, 3, 4, 2, 200, 0.2, 4, 0},
            {5, 0, 3, 8, 2, 100, 0.1, 4, 0},
            {6, 0, 4, 5, 2, 100, 0.1, 4, 0},
            {7, 0, 4, 10, 2, 100, 0.1, 4, 0},
            {8, 0, 6, 7, 2, 100, 0.1, 4, 0},
            {9, 0, 6, 15, 2, 200, 0.2, 4, 0},
            {10, 0, 7, 8, 2, 100, 0.1, 4, 0},
            {11, 0, 7, 11, 2, 100, 0.1, 4, 0},
            {12, 0, 8, 9, 2, 100, 0.1, 4, 0},
            {13, 0, 8, 12, 2, 100, 0.1, 4, 0},
            {14, 0, 9, 10, 2, 100, 0.1, 4, 0},
            {15, 0, 9, 18, 2, 200, 0.2, 4, 0},
            {16, 0, 10, 13, 2, 100, 0.1, 4, 0},
            {17, 0, 11, 12, 2, 100, 0.1, 4, 0},
            {18, 0, 11, 16, 2, 100, 0.1, 4, 0},
            {19, 0, 12, 17, 2, 100, 0.1, 4, 0},
            {20, 0, 13, 14, 2, 100, 0.1, 4, 0},
            {21, 0, 13, 19, 2, 100, 0.1, 4, 0},
            {22, 0, 15, 16, 2, 100, 0.1, 4, 0},
            {23, 0, 16, 17, 2, 100, 0.1, 4, 0},
            {24, 0, 17, 18, 2, 100, 0.1, 4, 0},
            {25, 0, 18, 19, 2, 100, 0.1, 4, 0},
            {26, 0, 19, 20, 2, 100, 0.1, 4, 0},
            {27, 0, 10, 21, 2, 100, 0.1, 4, 0}};
    
    private RealMatrix M_Lineas = new Array2DRowRealMatrix(M_LineasT);

//     xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
//       >> Matriz de Conductores <<
//     xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    private double[][] M_CondT = {
        // Cond    Imax     rl      xl      F       N       rn      xn           
        {1.0000, 0.0960, 1.0100, 0.0950, 3.0000, 1.0000, 0.7440, 0.0910},
        {2.0000, 0.1170, 0.7440, 0.0931, 3.0000, 1.0000, 0.7440, 0.0910},
        {3.0000, 1.0420, 0.1010, 0.3280, 3.0000, 1.0000, 0.7440, 0.0910}};

//     Imáx (kA), Rl (ohms/Km), Xl (ohms/Km), Rn (ohms/Km), Xn (ohms/Km)
//     Tabla de conductores para tensiones de 0,4 (kV)
//     M_CondT esta traspuesta por tanto hay que trasponerla para dejarla normal
    private RealMatrix M_Cond = new Array2DRowRealMatrix(M_CondT);

//      xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
//       >> Matriz de Distancias entre Conductores <<   
//      xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    private double[][] M_Dist1 = {
        //       N          A      B       C  
        {0.0000, 0.2000, 0.4000, 0.6000}, // N

        {0.2000, 0.0000, 0.2000, 0.4000}, // A

        {0.4000, 0.2000, 0.0000, 0.2000}, // B

        {0.6000, 0.4000, 0.2000, 0.0000}};// C

    //   Distancia entre conductores de 20 (cm)
    
    RealMatrix M_Dist = new Array2DRowRealMatrix(M_Dist1);
    
//        xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
//          >> Matriz de Cargas de la Red <<  
//        xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    
    private double[][] M_CargasT = {
        //  NB  Pa(pu)   Pb(pu)   Pc(pu)   Qa(pu)   Qb(pu)   Qc(pu)  NCli  Coord X  Coord Y                      
        {1, 0.00024, 0.00024, 0.00024, 0.00018, 0.00018, 0.00018, 1, 100, 400},
        {2, 0.00117, 0.00117, 0.00117, 0.00088, 0.00088, 0.00088, 1, 200, 400},
        {3, 0.00229, 0.00229, 0.00229, 0.00172, 0.00172, 0.00172, 1, 300, 400},
        {4, 0.00104, 0.00104, 0.00104, 0.00078, 0.00078, 0.00078, 1, 500, 400},
        {5, 0.00067, 0.00067, 0.00067, 0.00050, 0.00050, 0.00050, 1, 600, 400},
        {6, 0.00093, 0.00093, 0.00093, 0.00070, 0.00070, 0.00070, 1, 100, 300},
        {7, 0.00197, 0.00197, 0.00197, 0.00148, 0.00148, 0.00148, 1, 200, 300},
        {8, 0.00173, 0.00173, 0.00173, 0.00130, 0.00130, 0.00130, 1, 300, 300},
        {9, 0.00248, 0.00248, 0.00248, 0.00186, 0.00186, 0.00186, 1, 400, 300},
        {10, 0.00221, 0.00221, 0.00221, 0.00166, 0.00166, 0.00166, 1, 500, 300},
        {11, 0.00123, 0.00123, 0.00123, 0.00092, 0.00092, 0.00092, 1, 200, 200},
        {12, 0.00165, 0.00165, 0.00165, 0.00124, 0.00124, 0.00124, 1, 300, 200},
        {13, 0.00013, 0.00013, 0.00013, 0.00010, 0.00010, 0.00010, 1, 500, 200},
        {14, 0.00144, 0.00144, 0.00144, 0.00108, 0.00108, 0.00108, 1, 600, 200},
        {15, 0.00120, 0.00120, 0.00120, 0.00090, 0.00090, 0.00090, 1, 100, 100},
        {16, 0.00229, 0.00229, 0.00229, 0.00172, 0.00172, 0.00172, 1, 200, 100},
        {17, 0.00227, 0.00227, 0.00227, 0.00170, 0.00170, 0.00170, 1, 300, 100},
        {18, 0.00125, 0.00125, 0.00125, 0.00094, 0.00094, 0.00094, 1, 400, 100},
        {19, 0.00208, 0.00208, 0.00208, 0.00156, 0.00156, 0.00156, 1, 500, 100},
        {20, 0.00173, 0.00173, 0.00173, 0.00130, 0.00130, 0.00130, 1, 600, 100},
        {21, 0.00173, 0.00173, 0.00173, 0.00130, 0.00130, 0.00130, 1, 600, 300}};

    private RealMatrix M_Cargas = new Array2DRowRealMatrix(M_CargasT);
    private double[][] NodosMT = {{1, 2, 3, 4, 5}};

//            | ==============================================================================================|
//            |  Archivo que contiene toda la informaciónde topológica, de cargas, de conductores y distancias|
//            |  ...................................................Informe final de Gerónimo Uribe Capitulo 4|
//            | ==============================================================================================|

//                        |===============================================================|
//                        |===============================================================|
//                        |     INCORPORACION DE MICROGENERACION A POTENCIA CONSTANTE     |
//                        |              MAXIMO DE MICROGENERACIÓN 20%                    |
//                        |===============================================================|
//                        |===============================================================|

//Definicion de variables de tipo doble en 2 dimensiones    
    
    private double[][] NodosG2 = {{0}};   //Ingrese los nodos que inyectan energia al sistema
    private double[][] ValPot;      //Ingrese los Valores de potencia a inyectar correspondiente a cada nodo

//##########[Los valores de potencia a generar en corresponde a kW]###############
    private double[][] M_generacion;
    private RealMatrix NG1 = new Array2DRowRealMatrix(NodosG2);//Se define una nueva matriz, identica a NodosG2, la cual se denomina NG1
    private int NumeroDeColumnas = NG1.getColumnDimension();   //NumeroDeColumnas=El numero de columnas de la matris NG1
    private int NumeroDeFilas = NG1.getRowDimension();         //NumeroDeFilas=El numero de filas de la matris NG1
    private int NumeroDeNodos = NumeroDeColumnas * NumeroDeFilas;//Al multiplicar las filas por las columnas de una matris, se obtiene la cantidad de elementos en ella.

    
    //Luego NumeroDeNodos, es el numero de nodos del sistema.   
    private RealMatrix M_generacion1;
    private RealVector Columna1_M_generacion1;  //Declaracion de la columna 1 de la matriz M_generacion (linea 280)
    private RealVector Columna2_M_generacion1;
    private RealVector Columna2_M_Cargas;       //Declaracion de la columna 2 de la matriz M_Cargas (linea 285)
    private RealVector Columna3_M_Cargas;
    private RealVector Columna4_M_Cargas;
    private RealVector Columna5_M_Cargas;
    private RealVector Columna6_M_Cargas;
    private RealVector Columna7_M_Cargas;
    private RealMatrix Columna1_M_Lineas;
    private RealMatrix Columna3_M_Lineas;
    private RealMatrix Columna4_M_Lineas;
    private RealMatrix Columna6_M_Lineas;
    private RealMatrix Columna9_M_Lineas;
    private RealMatrix Columna10_M_Lineas;
    private RealVector Columna5_M_generacion1;
    private RealVector Columna4_M_generacion1;
    private RealVector Columna3_M_generacion1;
    private double A11 = NG1.getEntry(0,0); 
    private RealMatrix M_Cargas1 = new Array2DRowRealMatrix(M_CargasT);
    private RealMatrix ValPot1;
    private int gd;

    public BT20Balanceado() {
       
        //Se define el tiempo en el que empieza a medir
       // double TimeOn = System.currentTimeMillis();

        //Declaracion y llenado de la matriz M_generacion, en funcion de "nn"
        M_generacion = new double[(int) nn][4];
        for (int i = 0; i < nn; i++) {

            for (int j = 0; j < 4; j++) {

                M_generacion[i][j] = 0;
            }
        }
        M_generacion1 = new Array2DRowRealMatrix(M_generacion);

        //Comienzo de las sentencias
        if (A11 > 0) {
            for (int i = 0; i < NumeroDeNodos; i++)//Como inicie la variable en "0", se uso un menor y no un menor o igual.
            {

                gd = (int) NG1.getEntry(0, i); //Por que solo extraigo un elemento no un vector o matriz
                if (M_Cargas1.getEntry(gd - 1, 2) > 0) {
                    if (M_Cargas1.getEntry(gd - 1, 3) > 0) {
                        if (M_Cargas1.getEntry(gd - 1, 4) > 0) {
                            M_generacion1.setEntry(gd - 1, 2, ValPot1.getEntry(0, i) * ((double) 1 / 3));//Lo que hace esta linea de codigo, es tomar la componente
                            M_generacion1.setEntry(gd - 1, 3, ValPot1.getEntry(0, i) * ((double) 1 / 3));//de la matriz M_generacion1, (gd-1,2) y luego, reemplazar
                            M_generacion1.setEntry(gd - 1, 4, ValPot1.getEntry(0, i) * ((double) 1 / 3));//dicho valor por la componente (0,i) de la matriz ValPot1, 
                        }                                                                                //pero ademas esta componente es dividida por 3.
                    }
                }

                if (M_Cargas1.getEntry(gd - 1, 2) > 0) {
                    if (M_Cargas1.getEntry(gd - 1, 3) > 0) {
                        if (M_Cargas1.getEntry(gd - 1, 4) == 0) {
                            M_generacion1.setEntry(gd - 1, 2, ValPot1.getEntry(0, i) * ((double) 1 / 2));
                            M_generacion1.setEntry(gd - 1, 3, ValPot1.getEntry(0, i) * ((double) 1 / 2));
                            M_generacion1.setEntry(gd - 1, 4, 0);

                        }
                    }
                }

                if (M_Cargas1.getEntry(gd - 1, 2) > 0) {
                    if (M_Cargas1.getEntry(gd - 1, 3) == 0) {
                        if (M_Cargas1.getEntry(gd - 1, 4) > 0) {
                            M_generacion1.setEntry(gd - 1, 2, ValPot1.getEntry(0, i) * ((double) 1 / 2));
                            M_generacion1.setEntry(gd - 1, 3, 0);
                            M_generacion1.setEntry(gd - 1, 4, ValPot1.getEntry(0, i) * ((double) 1 / 2));

                        }
                    }
                }
                if (M_Cargas1.getEntry(gd - 1, 2) > 0) {
                    if (M_Cargas1.getEntry(gd - 1, 3) == 0) {
                        if (M_Cargas1.getEntry(gd - 1, 4) == 0) {
                            M_generacion1.setEntry(gd - 1, 2, ValPot1.getEntry(0, i));
                            M_generacion1.setEntry(gd - 1, 3, 0);
                            M_generacion1.setEntry(gd - 1, 4, 0);
                        }
                    }
                }
                if (M_Cargas1.getEntry(gd - 1, 2) == 0) {
                    if (M_Cargas1.getEntry(gd - 1, 3) > 0) {
                        if (M_Cargas1.getEntry(gd - 1, 4) > 0) {
                            M_generacion1.setEntry(gd - 1, 2, 0);
                            M_generacion1.setEntry(gd - 1, 3, ValPot1.getEntry(0, i) * ((double) 1 / 2));
                            M_generacion1.setEntry(gd - 1, 4, ValPot1.getEntry(0, i) * ((double) 1 / 2));

                        }
                    }
                }
                if (M_Cargas1.getEntry(gd - 1, 2) == 0) {
                    if (M_Cargas1.getEntry(gd - 1, 3) > 0) {
                        if (M_Cargas1.getEntry(gd - 1, 4) == 0) {
                            M_generacion1.setEntry(gd - 1, 2, 0);
                            M_generacion1.setEntry(gd - 1, 3, ValPot1.getEntry(0, i));
                            M_generacion1.setEntry(gd - 1, 4, 0);
                        }
                    }
                }
                if (M_Cargas1.getEntry(gd - 1, 2) == 0) {
                    if (M_Cargas1.getEntry(gd - 1, 3) == 0) {
                        if (M_Cargas1.getEntry(gd - 1, 4) > 0) {
                            M_generacion1.setEntry(gd - 1, 2, 0);
                            M_generacion1.setEntry(gd - 1, 3, 0);
                            M_generacion1.setEntry(gd - 1, 4, ValPot1.getEntry(0, i));
                        }
                    }
                }

            }
        }

        M_generacion1 = M_generacion1.scalarMultiply(1 / 1000);//Los Valores de potencia quedan en MVA

        double [] Columna1_M_generacion1 = M_generacion1.getColumn(0);//Me da la primera columna de M_generacion1

        //Declaracion y llenado del Vector Columna1_M_generacion1,en funcion de "nn"
        Columna1_M_generacion1 = new double[(int) nn];
        for (int j = 0; j < nn; j++) {
            Columna1_M_generacion1[j] = 0;

        }
        // Se genera una nueva matriz carga incorporando la microgeneración a potencia constante 

        double[] Columna2_M_Cargas1 = M_Cargas.getColumn(1);//Me da la segunda columna de M_Cargas
        double[] Columna2_M_generacion11 = M_generacion1.getColumn(1);//Me da la segunda columna de M_generacion1
        double[] Columna3_M_Cargas1 = M_Cargas.getColumn(2);//Me da la tercera columna de M_generacion1
        double[] Columna3_M_generacion11 = M_generacion1.getColumn(2);//Me da la tercera columna de M_generacion1
        double[] Columna4_M_Cargas1 = M_Cargas.getColumn(3);//Me da la cuarta columna de M_generacion1
        double[] Columna4_M_generacion11 = M_generacion1.getColumn(3);//Me da la cuarta columna de M_generacion1
        double[] Columna5_M_Cargas1 = M_Cargas.getColumn(4);//Me da la cuarta columna de M_generacion1
        double[] Columna6_M_Cargas1 = M_Cargas.getColumn(5);//Me da la cuarta columna de M_generacion1
        double[] Columna7_M_Cargas1 = M_Cargas.getColumn(6);//Me da la cuarta columna de M_generacion1
        double[] Columna1_M_Lineas1 = M_Lineas.getColumn(0);
        double[] Columna3_M_Lineas1 = M_Lineas.getColumn(2);
        double[] Columna4_M_Lineas1 = M_Lineas.getColumn(3);
        double[] Columna6_M_Lineas1 = M_Lineas.getColumn(5);
        double[] Columna9_M_Lineas1 = M_Lineas.getColumn(8);


        // Algoritmo para conmutar las columnas obtenidas anteriormente por las de otra matriz
        //Se definieron los vectores a utilizar para despues conmutar las columnas necesarias
        
        Columna2_M_Cargas = new ArrayRealVector(Columna2_M_Cargas1);
        Columna2_M_generacion1 = new ArrayRealVector(Columna2_M_generacion11);
        Columna3_M_Cargas = new ArrayRealVector(Columna3_M_Cargas1);
        Columna3_M_generacion1 = new ArrayRealVector(Columna3_M_generacion11);
        Columna4_M_Cargas = new ArrayRealVector(Columna4_M_Cargas1);
        Columna4_M_generacion1 = new ArrayRealVector(Columna4_M_generacion11);
        Columna5_M_Cargas = new ArrayRealVector(Columna5_M_Cargas1);
        Columna6_M_Cargas = new ArrayRealVector(Columna6_M_Cargas1);
        Columna7_M_Cargas = new ArrayRealVector(Columna7_M_Cargas1);
        Columna1_M_Lineas = new Array2DRowRealMatrix(Columna1_M_Lineas1);
        Columna3_M_Lineas = new Array2DRowRealMatrix(Columna3_M_Lineas1);
        Columna4_M_Lineas = new Array2DRowRealMatrix(Columna4_M_Lineas1);
        Columna6_M_Lineas = new Array2DRowRealMatrix(Columna6_M_Lineas1);
        Columna9_M_Lineas = new Array2DRowRealMatrix(Columna9_M_Lineas1);
        M_Cargas.setColumn(1, Columna2_M_Cargas.subtract(Columna2_M_generacion1).toArray());//Se usó toArray para que se cumpliera el requisito!
        M_Cargas.setColumn(2, Columna3_M_Cargas.subtract(Columna3_M_generacion1).toArray());//Se usó toArray para que se cumpliera el requisito!
        M_Cargas.setColumn(3, Columna4_M_Cargas.subtract(Columna4_M_generacion1).toArray());//Se usó toArray para que se cumpliera el requisito!
        this.M_Cargas = M_Cargas;

    }

    public RealMatrix getM_Lineas() {
        return M_Lineas;
    }
    public int getng() {
        return ng;
    }
    public int getnl() {
        return nl;
    }
    public int getnc() {
        return nc;
    }
    public  RealMatrix getM_Cargas() {
        return M_Cargas;
    }
    public RealMatrix getM_Dist() {
        return M_Dist;
    }
    public RealMatrix getM_Cond() {
        return M_Cond;
    }
    public int getnn() {
        return nn;
    }
    public double getVb() {
        return Vb;
    }
    public int getVbm() {
        return Vbm;
    }
    public int getMVAb() {
        return MVAb;
    }
    public int getfe() {
        return fe;
    }
    public double getInT() {
        return InT;
    }
    public double getInF() {
        return InF;
    }
    public double getFc() {
        return Fc;
    }
    public double getFp() {
        return Fp;
    }
    public double[][] getNodosMT() {
        return NodosMT;
    }
    public RealMatrix getM_generacion1() {
        return M_generacion1;
    }

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //Métodos get para la clase Orden y sus respectivos métodos
    public RealVector getColumna2_M_Cargas() {
        return Columna2_M_Cargas;
    }
    public RealVector getColumna3_M_Cargas() {
        return Columna3_M_Cargas;
    }
    public RealVector getColumna4_M_Cargas() {
        return Columna4_M_Cargas;
    }
    public RealVector getColumna5_M_Cargas() {
        return Columna5_M_Cargas;
    }
    public RealVector getColumna6_M_Cargas() {
        return Columna6_M_Cargas;
    }
    public RealVector getColumna7_M_Cargas() {
        return Columna7_M_Cargas;
    }
    public RealMatrix getColumna1_M_Lineas1() {
        return Columna1_M_Lineas;
    }
    public RealMatrix getColumna3_M_Lineas1() {
        return Columna3_M_Lineas;
    }
    public RealMatrix getColumna4_M_Lineas1() {
        return Columna4_M_Lineas;
    }
    public RealMatrix getColumna6_M_Lineas1() {
        return Columna6_M_Lineas;
    }
    public RealMatrix getColumna9_M_Lineas1() {
        return Columna9_M_Lineas;
    }
    public RealMatrix getColumna10_M_Lineas1() {
        return Columna10_M_Lineas;
    }
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

}
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

