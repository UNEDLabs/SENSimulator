/*
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 |xxxxxxx|             TÉCNICA DE OPTIMIZACION HEURÍSTICA             |xxxxxxx|
 |xxxxxxx|                    ALGORITMO GENÉTICO                      |xxxxxxx|
 |xxxxxxx|                     (OPERADOR CRUZA)                       |xxxxxxx| 
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

public class Cruza {
    
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
    private double[] vectorcruza;
    private double Pcruza;
    private RealMatrix FitnV;
    private RealMatrix Evalfun2;
    private RealMatrix cromosoma2;
    private int NIND;
    private int NLL;
    private RealMatrix padre1;
    private RealMatrix padre2;
    private RealMatrix hijo1;
    private RealMatrix hijo2;
    private RealMatrix Evalfunp1;
    private RealMatrix Evalfunp2;
    private RealMatrix impares;
    private RealMatrix pares;
    private RealMatrix vect1;
    private RealMatrix vect2;
    private double netoh1;
    private double netoh2;
    private RealMatrix chromh1;
    private RealMatrix chromh2;
    
    
    
public Cruza(int nl,int nn,int ng,int nc,double Vb,double Vbm,double MVAb,double fe,double InT,double InF,double Fc,
        double Fp,RealMatrix M_Lineas, RealMatrix M_Cond,RealMatrix M_Dist, RealMatrix M_Cargas, double[][] NodosMT,
        RealMatrix distanciasT,double ind,RealMatrix P,RealMatrix lineas,RealMatrix posicion,RealMatrix Minfo,RealMatrix
        SelCh,RealMatrix netoSelCh,double fproy,int years,double PP,double pMT,double pLLV,int hora,double[] ValoresActuales,
        int fig,int indkVA,RealMatrix Mem,int NMem,RealMatrix evolmemoria,double Pcruza,double[] vectorcruza,RealMatrix FitnV){
    
    
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
    this.Pcruza=Pcruza;
    this.vectorcruza=vectorcruza;
    this.FitnV=FitnV;
    this.netoSelCh=netoSelCh;
    this.NIND = SelCh.getRowDimension();
    this.NLL =  SelCh.getColumnDimension();
}   


public void RunCruza(){
    
    double N = round(NIND*Pcruza/2);
    cromosoma2 = SelCh.copy();
    Evalfun2 = netoSelCh;
    int indci;
    int indcj;
    int indc;
    int indcruza;
    int indc1;
    int indc2;
    
    for(int i=0; i<(int)N ; i++){
        
        indci=Metodos.randInt(0,NIND-1);
        indcj=Metodos.randInt(0,NIND-1);
        padre1 = SelCh.getRowMatrix(indci).transpose();
        padre2 = SelCh.getRowMatrix(indcj).transpose();
        hijo1 = new Array2DRowRealMatrix(new double[padre1.getRowDimension()]);
        hijo2 = new Array2DRowRealMatrix(new double[padre1.getRowDimension()]);
        Evalfunp1=netoSelCh.getRowMatrix(indci);
        Evalfunp2=netoSelCh.getRowMatrix(indcj);
        
        indcruza=Metodos.randInt(0,vectorcruza.length-0.0001);
  
        if(indcruza == 0){
        //CRUZA EN UN PUNTO:    
           indc=Metodos.randInt(0,NLL-2);
           hijo1.setSubMatrix(padre1.getSubMatrix(0,indc,0,0).getData(), 0, 0);
           hijo1.setSubMatrix(padre2.getSubMatrix(indc+1,NLL-1,0,0).getData(),indc,0);
           hijo2.setSubMatrix(padre2.getSubMatrix(0,indc,0,0).getData(), 0, 0);
           hijo2.setSubMatrix(padre1.getSubMatrix(indc+1,NLL-1,0,0).getData(),indc,0);    
        }
        if(indcruza == 1){
        // CRUZA EN DOS PUNTO:    
           indc1=Metodos.randInt(0,NLL-5);
           indc2=Metodos.randInt(indc1+1,NLL-2);//rnd.nextDouble()*num_b+num_a
           hijo1.setSubMatrix(padre1.getSubMatrix(0,indc1,0,0).getData(), 0, 0);
           hijo1.setSubMatrix(padre2.getSubMatrix(indc1+1,indc2,0,0).getData(),indc1,0);
           hijo1.setSubMatrix(padre1.getSubMatrix(indc2+1,NLL-1,0,0).getData(),indc2,0);
           
           hijo2.setSubMatrix(padre2.getSubMatrix(0,indc1,0,0).getData(), 0, 0);
           hijo2.setSubMatrix(padre1.getSubMatrix(indc1+1,indc2,0,0).getData(),indc1,0);
           hijo2.setSubMatrix(padre2.getSubMatrix(indc2+1,NLL-1,0,0).getData(),indc2,0);   
        }
        if(indcruza == 2){
        // CRUZA UNIFORME
           impares = Metodos.unosEnPar(NLL);
           pares = Metodos.unosEnImpar(NLL);
           hijo1=Metodos.multiplyElement(padre1,pares).add(Metodos.multiplyElement(padre2,impares));
           hijo2=Metodos.multiplyElement(padre2,pares).add(Metodos.multiplyElement(padre1,impares));
        }
        
        hijo1=Metodos.newChrom(hijo1).transpose();
        hijo2=Metodos.newChrom(hijo2).transpose();
        
        Evaluador eval_hijo1 = new Evaluador(nl, nn, ng, nc, Vb, Vbm, MVAb, fe, InT, InF, Fc, Fp, M_Lineas, M_Cond, M_Dist, M_Cargas, NodosMT,
        distanciasT, ind,P,lineas,posicion, Minfo,hijo1, fproy, years, PP, pMT, pLLV, hora, ValoresActuales, 0, 0, Mem, NMem, evolmemoria);
        netoh1 = eval_hijo1.getneto().getEntry(0,0);
        chromh1 = eval_hijo1.getcromosoma();
        
        Evaluador eval_hijo2 = new Evaluador(nl, nn, ng, nc, Vb, Vbm, MVAb, fe, InT, InF, Fc, Fp, M_Lineas, M_Cond, M_Dist, M_Cargas, NodosMT,
        distanciasT, ind,P,lineas,posicion, Minfo,hijo2, fproy, years, PP, pMT, pLLV, hora, ValoresActuales, 0, 0, Mem, NMem, evolmemoria);
        netoh2 = eval_hijo2.getneto().getEntry(0,0);
        chromh2 = eval_hijo2.getcromosoma();
        
        //sería mejor tomar neto(Evalfunp1 y comparar todos los valores con netoh1)
        
          if (netoh1 < Evalfunp1.getEntry(0, 0)) {
            SelCh.setRowMatrix(indci, chromh1);//pienso que deberia reemplazar a el cromosoma con neto=Evalfunp1
            netoSelCh.setEntry(indci, 0, netoh1);
            vectorcruza[indcruza] = vectorcruza[indcruza] + 0.005;
            for (int j = 0; j < vectorcruza.length; j++) {
                vectorcruza[j] = vectorcruza[j] / Metodos.suma(new Array2DRowRealMatrix(vectorcruza));
            }
        } 
          else {
            SelCh.setRowMatrix(indci, padre1.transpose());
            netoSelCh.setEntry(indci, 0, Evalfunp1.getEntry(0, 0));

        }
          
          
          if (netoh2 < Evalfunp2.getEntry(0, 0)) {
            SelCh.setRowMatrix(indcj, chromh2);
            netoSelCh.setEntry(indcj, 0, netoh2);
            vectorcruza[indcruza] = vectorcruza[indcruza] + 0.005;
            for (int j = 0; j < vectorcruza.length; j++) {
                vectorcruza[j] = vectorcruza[j] / Metodos.suma(new Array2DRowRealMatrix(vectorcruza));
            }
        } 
        else {
            SelCh.setRowMatrix(indcj, padre2.transpose());
            netoSelCh.setEntry(indcj, 0, Evalfunp2.getEntry(0, 0));

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
    
    public double[] getvectorcruza(){
        return vectorcruza;
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
