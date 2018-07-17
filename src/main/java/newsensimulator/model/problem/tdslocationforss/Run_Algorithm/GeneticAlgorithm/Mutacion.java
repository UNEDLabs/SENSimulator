/*
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 |xxxxxxx|             TÉCNICA DE OPTIMIZACION HEURÍSTICA             |xxxxxxx|
 |xxxxxxx|                    ALGORITMO GENÉTICO                      |xxxxxxx|
 |xxxxxxx|                   (OPERADOR  MUTACION)                     |xxxxxxx| 
 |xxxxxxx|                           2014                             |xxxxxxx|
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 |xxxxxxx|                JMendoza - AMadrid - HVargas                |xxxxxxx|
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 */
package newsensimulator.model.problem.tdslocationforss.Run_Algorithm.GeneticAlgorithm;

import static java.lang.Math.round;
import newsensimulator.model.problem.tdslocationforss.Run_Algorithm.TDs_Location.Evaluador;
import newsensimulator.model.problem.tdslocationforss.Run_Algorithm.TDs_Location.Metodos;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

/**
 *
 * @author Andreuw Madrid Carreño  "Escuela de Ingeniería Eléctrica - PUCV"
 */
public class Mutacion {
    
    private RealMatrix SelCh;
    private RealMatrix netoSelCh;
    private int nl;
    private int nn;
    private int ng;
    private int nc;
    private double Vb;
    private double Vbm;
    private double MVAb;
    private double fe;
    private double InT;
    private double InF;
    private double Fc;
    private double Fp;
    private RealMatrix M_Lineas;
    private RealMatrix M_Cond;
    private RealMatrix M_Dist;
    private RealMatrix M_Cargas;
    private double [][] NodosMT;
    private RealMatrix distanciasT;
    private double ind;
    private RealMatrix P;
    private RealMatrix lineas;
    private RealMatrix posicion;
    private RealMatrix Minfo;
    private double fproy;
    private int years;
    private double PP;
    private double pMT;
    private double pLLV;
    private int hora;
    private double VA;
    private double VAC;
    private double VAC2;
    private int fig;
    private int indkVA;
    private double [] ValoresActuales;
    private RealMatrix Mem;
    private RealMatrix evolmemoria;
    private int NMem;
    private double[] vectormutacion;
    private double Pmutacion; 
    private RealMatrix chrom2;
    private int NIND;
    private int NLL;
    private int N;
    private RealMatrix Evalfun2;
    private int indci;
    private RealMatrix hijo1;
    private double Evalfunp1;
    private int indmutacion; 
    private int indmh1;
    private double netoh1;
    private RealMatrix chromh1;
    
public Mutacion(int nl,int nn,int ng,int nc,double Vb,double Vbm,double MVAb,double fe,double InT,double InF,double Fc,
        double Fp,RealMatrix M_Lineas, RealMatrix M_Cond,RealMatrix M_Dist, RealMatrix M_Cargas, double[][] NodosMT,RealMatrix 
        distanciasT,double ind,RealMatrix P,RealMatrix lineas,RealMatrix posicion,RealMatrix Minfo,RealMatrix SelCh,RealMatrix netoSelCh,
        double fproy,int years,double PP,double pMT,double pLLV,int hora,double[] ValoresActuales,int fig,int indkVA,RealMatrix
        Mem,int NMem,RealMatrix evolmemoria,double Pmutacion,double[] vectormutacion){
    
    this.nl = nl;
    this.nn = nn;
    this.ng = ng;
    this.nc = nc;
    this.Vb = Vb;
    this.Vbm = Vbm;
    this.MVAb = MVAb;
    this.fe = fe;
    this.InT = InT;
    this.InF = InF;
    this.Fc = Fc;
    this.Fp = Fp;
    this.M_Lineas = M_Lineas;
    this.M_Cond = M_Cond;
    this.M_Dist = M_Dist;
    this.M_Cargas = M_Cargas;
    this.NodosMT = NodosMT;
    this.distanciasT = distanciasT;
    this.ind = ind;
    this.P = P;
    this.lineas = lineas;
    this.posicion = posicion;
    this.Minfo = Minfo;
    this.SelCh = SelCh;
    this.fproy = fproy;
    this.years = years;
    this.PP = PP;
    this.pMT = pMT;
    this.pLLV = pLLV;
    this.hora = hora;
    this.ValoresActuales = ValoresActuales;
    this.fig = fig;
    this.indkVA = indkVA;
    this.Mem = Mem;
    this.evolmemoria = evolmemoria;
    this.NMem = NMem;
    this.Pmutacion=Pmutacion;
    this.vectormutacion=vectormutacion;
    this.netoSelCh=netoSelCh;
    this.NIND = SelCh.getRowDimension();
    this.NLL = SelCh.getColumnDimension();
    this.N = (int) round(NIND * Pmutacion);
}

public void RunMutacion(){
    
    
    chrom2 = SelCh.copy();
    Evalfun2 = netoSelCh.copy();
    
    for (int i = 0; i < N; i++) {
        indci = Metodos.randInt(0, NIND - 1);
        hijo1 = SelCh.getRowMatrix(indci).transpose();
        Evalfunp1 = netoSelCh.getEntry(indci, 0);
        indmutacion = Metodos.randInt(0, vectormutacion.length - 0.0001);

        //MUTACION AUMENTA EL NUMERO:
        if (indmutacion == 0) {
            indmh1 = Metodos.randInt(0, NLL - 1);
            hijo1.setEntry(indmh1, 0, 1);
        }
        
        //MUTACION DISMINUYE EL NUMERO:
        if (indmutacion == 1) {
            RealMatrix auxII = Metodos.findMatlab(hijo1, 1).transpose();
            for (int ii = 0; ii < auxII.getRowDimension(); ii++) {
                indmh1 = Metodos.randInt(0, auxII.getEntry(ii, 0));
                hijo1.setEntry(indmh1, 0, 0);
            }
        }
        
        //MUTACION MANTIENE EL N° Y SOLO INTERCAMBIA POSICIONES:
        if (indmutacion == 2) {
            RealMatrix auxII = Metodos.findMatlab(hijo1, 1).transpose();
            for (int ii = 0; ii < auxII.getRowDimension(); ii++) {
                indmh1 = Metodos.randInt(0, auxII.getEntry(ii, 0));
                hijo1.setEntry(indmh1, 0, 0);
            }
            indmh1 = Metodos.randInt(0, NLL - 1);
            hijo1.setEntry(indmh1, 0, 1);
        }
        
        hijo1=Metodos.newChrom(hijo1).transpose();
        
        Evaluador eval_hijo1 = new Evaluador(nl, nn, ng, nc, Vb, Vbm, MVAb, fe, InT, InF, Fc, Fp, M_Lineas, M_Cond, M_Dist, M_Cargas, NodosMT,
        distanciasT, ind,P,lineas,posicion, Minfo,hijo1, fproy, years, PP, pMT, pLLV, hora, ValoresActuales, 0, 0, Mem, NMem, evolmemoria);
        netoh1 = eval_hijo1.getneto().getEntry(0,0);
        chromh1 = eval_hijo1.getcromosoma();
 
        if (netoh1 < Evalfunp1) {
            SelCh.setRowMatrix(indci, chromh1);
            netoSelCh.setEntry(indci, 0, netoh1);
            vectormutacion[indmutacion] = vectormutacion[indmutacion] + 0.005;
            for (int j = 0; j < vectormutacion.length; j++) {
                vectormutacion[j] = vectormutacion[j] / Metodos.suma(new Array2DRowRealMatrix(vectormutacion));
            }

        }
        this.SelCh = SelCh;
        this.netoSelCh=netoSelCh;
    }

}

    //MÉTODOS GET():
    
    public RealMatrix getSelCh(){
        return SelCh;
        
    }
    
    public RealMatrix getnetoSelCh(){
        return netoSelCh;
        
    }
    
    public double[] getvectormutacion(){
        return vectormutacion;
    }
    
    public RealMatrix getMem(){
        return Mem;
    }
    
    public int getNMem(){
        return NMem;
    }
    
    public RealMatrix getevolmemoria(){
        return evolmemoria;
    }
    
}
