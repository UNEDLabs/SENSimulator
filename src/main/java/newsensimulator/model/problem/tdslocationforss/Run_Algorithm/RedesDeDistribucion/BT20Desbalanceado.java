/*
                 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|                               
                 |xxxxxxx|               DATOS DE LA RED DE DISTRIBUCIÓN              |xxxxxxx|                               
                 |xxxxxxx|                         (20 Nodos)                         |xxxxxxx|
                 |xxxxxxx|                        Desbalanceado                       |xxxxxxx|       
                 |xxxxxxx|                            2014                            |xxxxxxx|                               
                 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|    
 */


package newsensimulator.model.problem.tdslocationforss.Run_Algorithm.RedesDeDistribucion;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

/**
 *
 * @author Andreuw Madrid Carreño
 */


public class BT20Desbalanceado {
    
    private int nl = 26;                              // Número de líneas......................
    private int nn = 20;                              // Número de nodos.......................
    private int ng = 1;                               // Número de barras generadoras..........
    private int nc = 19;                              // Número de cargas......................
    private double Vb = 0.2193931;                    // (kV) de la red de B.T.................
    private int Vbm = 12;                             // (kV) de la red de M.T.................
    private int MVAb = 1;                             // (MVA).................................
    private int fe = 1;                               // Factor de energía.....................
    private double InT = 0.15;                        // Tasa de interes.......................
    private double InF = 0.18;                        // Tasa de descuento.....................
    private double Fc = 0.5;                          // Factor de carga.......................
    private double Fp = 0.325;                        // Fractor de pérdida....................    
    
    double [][] M_LineasT = 
    {{1,0,1,2,2,100,0.1,4,0},
    {2,0,2,3,2,100,0.1,4,0},
    {3,0,2,7,2,100,0.1,4,0},
    {4,0,3,4,2,200,0.2,4,0},
    {5,0,3,8,2,100,0.1,4,0},
    {6,0,4,5,2,100,0.1,4,0},
    {7,0,4,10,2,100,0.1,4,0},
    {8,0,6,7,2,100,0.1,4,0},
    {9,0,6,15,2,200,0.2,4,0},
    {10,0,7,8,2,100,0.1,4,0},
    {11,0,7,11,2,100,0.1,4,0},
    {12,0,8,9,2,100,0.1,4,0},
    {13,0,8,12,2,100,0.1,4,0},
    {14,0,9,10,2,100,0.1,4,0},
    {15,0,9,18,2,200,0.2,4,0},
    {16,0,10,13,2,100,0.1,4,0},
    {17,0,11,12,2,100,0.1,4,0},
    {18,0,11,16,2,100,0.1,4,0},
    {19,0,12,17,2,100,0.1,4,0},
    {20,0,13,14,2,100,0.1,4,0},
    {21,0,13,19,2,100,0.1,4,0},
    {22,0,15,16,2,100,0.1,4,0},
    {23,0,16,17,2,100,0.1,4,0},
    {24,0,17,18,2,100,0.1,4,0},
    {25,0,18,19,2,100,0.1,4,0},
    {26,0,19,20,2,100,0.1,4,0}};
    RealMatrix M_Lineas = new Array2DRowRealMatrix(M_LineasT);
    
    double [][] M_CondT = 
   {{1, 0.096, 1.01, 0.095, 3, 1, 0.744, 0.091},
   {2, 0.117, 0.744, 0.0931, 3, 1, 0.744, 0.091},
   {3, 1.042, 0.101, 0.328, 3, 1, 0.744, 0.091}};
    RealMatrix M_Cond = new Array2DRowRealMatrix(M_CondT);
        
    double[][] M_DistT
            = {{0, 0.2, 0.4, 0.6},
            {0.2, 0, 0.2, 0.4},
            {0.4, 0.2, 0, 0.2},
            {0.6, 0.4, 0.2, 0}};
    RealMatrix M_Dist = new Array2DRowRealMatrix(M_DistT);     
    
    double[][] M_CargasT=
        {{1,0.00072,0,0,0.00054,0,0,1,100,400},
        {2,0.00351,0,0,0.00264,0,0,1,200,400},
        {3,0.00687,0,0,0.00516,0,0,1,300,400},
        {4,0.00312,0,0,0.00234,0,0,1,500,400},
        {5,0.001005,0.001005,0,0.00075,0.00075,0,1,600,400},
        {6,0.001395,0.001395,0,0.00105,0.00105,0,1,100,300},
        {7,0.002955,0.002955,0,0.00222,0.00222,0,1,200,300},
        {8,0.002595,0.002595,0,0.00195,0.00195,0,1,300,300},
        {9,0.00372,0.00372,0,0.00279,0.00279,0,1,400,300},
        {10,0,0,0.00663,0,0,0.00498,1,500,300},
        {11,0,0.00369,0,0,0.00276,0,1,200,200},
        {12,0,0.00495,0,0,0.00372,0,1,300,200},
        {13,0,0.00039,0,0,0.0003,0,1,500,200},
        {14,0,0.00432,0,0,0.00324,0,1,600,200},
        {15,0,0,-0.0114,0,0,0.0027,1,100,100},
        {16,0,0,0.00687,0,0,0.00516,1,200,100},
        {17,0,0,0.00681,0,0,0.0051,1,300,100},
        {18,0,0,0.00375,0,0,0.00282,1,400,100},
        {19,0,0,0.00624,0,0,0.00468,1,500,100},
        {20,0,0,0.00519,0,0,0.0039,1,600,100}};
    
    RealMatrix M_Cargas = new Array2DRowRealMatrix(M_CargasT);
    
    double[][] NodosMT = {{1, 2, 3, 4, 5}};  


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
}

