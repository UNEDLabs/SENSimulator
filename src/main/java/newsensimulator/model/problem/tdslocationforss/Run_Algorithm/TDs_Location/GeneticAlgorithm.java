/*
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 |xxxxxxx|             TÉCNICA DE OPTIMIZACION HEURÍSTICA             |xxxxxxx|
 |xxxxxxx|                     ALGORITMO GENÉTICO                     |xxxxxxx|
 |xxxxxxx|                            2014                            |xxxxxxx|
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 |xxxxxxx|                JMendoza - AMadrid - HVargas                |xxxxxxx|
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 */



package newsensimulator.model.problem.tdslocationforss.Run_Algorithm.TDs_Location;

import java.util.Arrays;
import newsensimulator.model.problem.tdslocationforss.Run_Algorithm.GeneticAlgorithm.Cruza;
import newsensimulator.model.problem.tdslocationforss.Run_Algorithm.GeneticAlgorithm.Elitismo;
import newsensimulator.model.problem.tdslocationforss.Run_Algorithm.GeneticAlgorithm.Mutacion;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * @author Andreuw Madrid Carreño  "Escuela de Ingeniería Eléctrica - PUCV"
 */

public class GeneticAlgorithm {
   
    private RealMatrix FitnV;
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
    private RealMatrix cromosoma;
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
    private double[] vectormutacion;
    private double Pmutacion;
    private double Pcruza;
    private RealMatrix neto;
    private int gen;
    private int MAXGEN;
    private RealMatrix min;
    private double aa;
    private double bb;
    private RealMatrix Best;
    private RealMatrix ConverMatrix;
    private RealMatrix minForNextEval;
    private RealMatrix MemoryBest;
    private RealMatrix TheBestChrom;
    
public GeneticAlgorithm(int nl,int nn,int ng,int nc,double Vb,double Vbm,double MVAb,double fe,double InT,double InF,double Fc,
        double Fp,RealMatrix M_Lineas, RealMatrix M_Cond,RealMatrix M_Dist, RealMatrix M_Cargas, double[][] NodosMT,RealMatrix 
        distanciasT,double ind,RealMatrix P,RealMatrix lineas,RealMatrix posicion,RealMatrix Minfo,RealMatrix cromosoma,
        double fproy,int years,double PP,double pMT,double pLLV,int hora,double[] ValoresActuales,int fig,int indkVA,RealMatrix
        Mem,int NMem,RealMatrix evolmemoria,double Pcruza,double Pmutacion,RealMatrix neto,double[] vectorcruza,double[] 
        vectormutacion,int gen,int MAXGEN,RealMatrix Best){
    
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
    this.cromosoma = cromosoma;
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
    this.Pmutacion=Pmutacion;
    this.vectorcruza=vectorcruza;
    this.vectormutacion=vectormutacion;
    this.neto=neto;
    this.gen=gen;
    this.Best=Best;
    this.ConverMatrix = new Array2DRowRealMatrix(new double[MAXGEN][2]);
    this.SelCh = cromosoma.copy();
    this.netoSelCh = neto.copy();   
    this.MAXGEN = MAXGEN; 
}   
    

// MÉTODOS VARIOS:  

public void RunAG(){
    
    while(gen<MAXGEN){
//  ********************************************
//         >> Inicio Operador Cruza <<
//  ********************************************
    Cruza cruza = new Cruza(nl,nn,ng,nc,Vb,Vbm,MVAb,fe,InT,InF,Fc,Fp,M_Lineas,M_Cond,M_Dist,M_Cargas,NodosMT,distanciasT,
          ind,P,lineas,posicion,Minfo,SelCh,netoSelCh,fproy,years,PP,pMT,pLLV,hora,ValoresActuales,fig,indkVA,Mem,NMem,
          evolmemoria,Pcruza,vectorcruza,FitnV);
    
    cruza.RunCruza();
    SelCh=cruza.getSelCh();
    netoSelCh=cruza.getnetoSelCh();
    vectorcruza=cruza.getvectorcruza();
    Mem=cruza.getMem();
    NMem=cruza.getNMem();
    evolmemoria=cruza.getevolmemoria();
    
    
//  ********************************************
//        >> Inicio Operador Mutacion <<
//  ********************************************

    Mutacion mutacion = new Mutacion(nl,nn,ng,nc,Vb,Vbm,MVAb,fe,InT,InF,Fc,Fp,M_Lineas,M_Cond,M_Dist,M_Cargas,NodosMT,
            distanciasT,ind,P,lineas,posicion,Minfo,SelCh,netoSelCh,fproy,years,PP,pMT,pLLV,hora,ValoresActuales,fig,
            indkVA,Mem,NMem,evolmemoria,Pmutacion,vectormutacion);  
    
    mutacion.RunMutacion();
    SelCh = mutacion.getSelCh();
    netoSelCh=mutacion.getnetoSelCh();
    vectormutacion=mutacion.getvectormutacion();
    Mem=mutacion.getMem();
    NMem=mutacion.getNMem();
    evolmemoria=mutacion.getevolmemoria();
 
    
//  ********************************************
//        >> Inicio Operador Elitismo <<
//  ********************************************
    Elitismo elitismo = new Elitismo(cromosoma,SelCh,neto,netoSelCh);
    elitismo.RunElitismo();
    SelCh = elitismo.getcromosoma();
    netoSelCh = elitismo.getneto();
    
    
//  *********************************************
//          >> Matriz de Convergencia <<
//  ********************************************* 

// System.out.println("Iteración N° "+(gen+1));
// RealMatrix plot = new Array2DRowRealMatrix(new double[20][21]);
// plot.setSubMatrix(SelCh.getData(),0,1);
// plot.setColumn(0,netoSelCh.getColumn(0));

  
//  *********************************************
//  >> Incremento del Contador de Generaciones <<
//  *********************************************
    gen++;
    min = Metodos.minComplete(netoSelCh);
    aa = min.getColumnMatrix(0).getEntry(0,0);
    bb = min.getColumnMatrix(1).getEntry(0,0);
    Best.setEntry(gen,0, aa);    
    this.cromosoma=SelCh;
    this.neto=netoSelCh;
    this.Best=Best;
    
    }
    
    minForNextEval = Metodos.minComplete(netoSelCh);
    aa = min.getColumnMatrix(0).getEntry(0,0);
    bb = min.getColumnMatrix(1).getEntry(0,0);
    MemoryBest=new Array2DRowRealMatrix(new double[1][1+SelCh.getColumnDimension()]);
    MemoryBest.setEntry(0,0,aa);
    MemoryBest.setSubMatrix(SelCh.getRowMatrix((int)bb).getData(),0,1);
    TheBestChrom=SelCh.getRowMatrix((int)bb);
    this.cromosoma=SelCh;
    this.neto=netoSelCh;
    this.Best=Best;
    
}
    
private static RealMatrix Ranking(RealMatrix matrizNeto) {
        //Este método tolera como entrada un vector tipo columna
        //que sea un objeto de la clase RealMatrix.
        //Retorna un objeto RealMatrix.
        //En síntesis: Ingresa el vector con los costos netos asociados
        //al cromosoma actual, se calcula la suma del vector(de todos los elementos)
        //luego se toma la suma y se le resta el valor en cada posicion, y se genera un nuevo vector,
        //luego de ello se suma este vector nuevamente, luego se calcula el PU de dicho vector,
        //tomando como base la sumatoria de dicho vector, generando un nuevo vector 
        //con valores entre 0 y 1,dandole un peso determinado a cada solucion y su cromosoma
        //asociado.

        RealMatrix vector1 = matrizNeto.getColumnMatrix(0);
        double suma = Metodos.suma(vector1);
        RealMatrix vector2 = new Array2DRowRealMatrix(new double[vector1.getRowDimension()][vector1.getColumnDimension()]);
        vector2 = Metodos.addScalar(vector2, suma);
        vector1 = vector1.scalarMultiply(-1);
        RealMatrix vector3 = Metodos.addMatrix(vector2, vector1);
        double suma2 = 1 / Metodos.suma(vector3);
        RealMatrix vectorOut = Metodos.MultiplyScalar(vector3, suma2);
        
        //Estas últimas modificaciones, se realizan para otorgarle a las mejores 4 soluciones
        //un mayor peso probabilistico, en pocas palabras se aumenta la probabilidad de usarles en un 10%
        //a cada una de las 4 mejores, para compensar esto, se le disminuye a el resto de soluciones la 
        //probabilidad de que sean seleccionados ya sea para cruza o mutacion:
        double [] VAO=vectorOut.copy().getColumn(0);
        Arrays.sort(VAO);
        double[] aux = new double[4];
        aux[0] = VAO[VAO.length - 4];
        aux[1] = VAO[VAO.length - 3];
        aux[2] = VAO[VAO.length - 2];
        aux[3] = VAO[VAO.length - 1];
        int Dim = vectorOut.getRowDimension();
        double scalar=0.4/(Dim-4);
        for(int j=0;j<Dim;j++){
            if(vectorOut.getEntry(j,0)==aux[0]||vectorOut.getEntry(j,0)==aux[1]||vectorOut.getEntry(j,0)==aux[2]||vectorOut.getEntry(j,0)==aux[3]){
                vectorOut.setEntry(j,0,vectorOut.getEntry(j,0)+0.1);
            }
            else{
                vectorOut.setEntry(j,0,vectorOut.getEntry(j,0)-scalar);
            }
            
        }
        double uma = Metodos.suma(vectorOut.getColumnMatrix(0));//uma=1, implica que la suma de los pesos es correcto
        return vectorOut;
    }

    
    //MÉTODOS GET():
    
    public RealMatrix getBest() {
        return Best;
    }

    public RealMatrix getcromosoma() {
        return cromosoma;
    }

    public RealMatrix getneto() {
        return neto;
    }

    public RealMatrix getMemoryBest() {
        return MemoryBest;
    }

    public RealMatrix getTheBestChrom() {
        return TheBestChrom;
    }

}
