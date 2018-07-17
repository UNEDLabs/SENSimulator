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

package newsensimulator.model.problem.tdslocationforss.Run_Algorithm.TDs_Location;

import newsensimulator.model.problem.tdslocationforss.Run_Algorithm.RedesDeDistribucion.BT20Balanceado;
import static newsensimulator.model.problem.tdslocationforss.Run_Algorithm.TDs_Location.Metodos.CorrientesLineasGrados;
import static newsensimulator.model.problem.tdslocationforss.Run_Algorithm.TDs_Location.Metodos.VoltajesNodosRadianes;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;

/**
 * @author Andreuw Madrid Carreño  "Escuela de Ingeniería Eléctrica - PUCV"
 */
public class TDs_Location {
    
    private int years;
    private double Crec_Dem;
    private double g;
    private RealMatrix distanciasT;
    private RealMatrix TDs;
    private RealMatrix auxiliar3;
    private double ind = 0;
    private double NaN = Double.NaN;
    private double Inf = Double.POSITIVE_INFINITY;
    private RealMatrix Minfo;
    private RealMatrix cromosoma;
    private double fproy;
    private double PP;
    private double pMT;
    private double pLLV;
    private int hora;
    private int fig=0;
    private RealMatrix P;
    private int indkVA=0;
    private double [] ValoresActuales;
    private RealMatrix Best;
    private RealMatrix Mem;
    private RealMatrix evolmemoria;       
    private RealMatrix ListaVectCruza;
    private RealMatrix ListaVectMutacion;
    private int NMem;
    private RealMatrix neto;
    private RealMatrix posicion;
    private RealMatrix lineas;
    private RealMatrix TheBestChrom;
    private RealMatrix BestMemory;
    private int nl;
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
    private int gen;
    private int nn;
    private double r;
    private double FL;
    private double FOL;
    private double vmcc;
    private int NIND;
    private int MAXGEN;
    private double Pcruza;
    private double Pmutacion;
    private double TimeOn;
    private double [] vectorcruza= {0.3333,0.3333,0.3334};
    private double [] vectormutacion ={0.3333,0.3333,0.3334};
    private double [] LineasAbiertas;
    private double [] LineasCerradas;
    
    private double costoTotal;
    private RealMatrix kVAs;
    private RealMatrix kVA;
    private RealMatrix TDs_Norm;
    private double Perd_Red;    
    private double PerdTDs;
    private double distLMT;
    private double distLBT;
    private int nodoConTensionMasBaja;
    private double tensionMasBaja;
    private int faseTensionMasBaja;
    private double cantidadEnergiaNoSuministrada;
    
    private RealMatrix MatrizVoltajesGrados;
    private RealMatrix MatrizVoltajesRadianes;
    private RealMatrix MatrizCorrientesRadianes;
    private RealMatrix MatrizCorrientesGrados;
    
    
    //Valores por defecto, empleando red de prueba BT20Balanceado
    public TDs_Location (){
        BT20Balanceado BT20Balanceado = new BT20Balanceado();                   //Instanciar al Sistema..........................
        nl = BT20Balanceado.getnl();                                            //Número de líneas del Sistema...................
        ng = BT20Balanceado.getng();                                            //Número de generadores en el Sistema............
        nc = BT20Balanceado.getnc();                                            //Número de cargas del Sistema...................
        Vb = BT20Balanceado.getVb();                                            //(kV) de la red de M.T..........................
        Vbm = BT20Balanceado.getVbm();                                          //(kV) de la red de B.T..........................
        MVAb= BT20Balanceado.getMVAb();                                         //MVA............................................
        fe = BT20Balanceado.getfe();                                            //Factor de Energía..............................
        InT = BT20Balanceado.getInT();                                          //Tasa de interés................................
        InF = BT20Balanceado.getInF();                                          //Tasa de descuento..............................
        Fc = BT20Balanceado.getFc();                                            //Factor de carga................................
        Fp = BT20Balanceado.getFp();                                            //Factor de pérdida..............................
        M_Lineas = BT20Balanceado.getM_Lineas();                                //Matriz de Líneas...............................
        M_Cond = BT20Balanceado.getM_Cond();                                    //Matriz de Conductores..........................
        M_Dist = BT20Balanceado.getM_Dist();                                    //Matriz de Distancias en conductores............
        M_Cargas = BT20Balanceado.getM_Cargas();                                //Matriz de Cargas...............................
        NodosMT = BT20Balanceado.getNodosMT();                                  //Nodos de M.T...................................
        gen =0;                                                                 //Contador de generaciones.......................
        NMem = 0;                                                               //Contador de Memoria............................        
        nn = ng+nc;                                                             //Número de Nodos................................
        years = 5;                                                              //Años para la recuperacion......................
        Crec_Dem = 4;                                                           //Creciemiento de la demanda ....................
        g = Crec_Dem / 100;                                                     //Creciemiento de la demanda (%).................
        r = 0.18;                                                               //Tasa de descuento..............................
        FL = 0.5;                                                               //Factor de carga de la red...................... 
        FOL = 1.2;                                                              //Factor de sobrecarga del transformador.........
        fproy = Math.pow((1 + g), (years)) / (FL * FOL);                        //Factor de proyección antes (1.5)............... 
        PP = 63.1;                                                              //Precio de la energía de pérdidas real CGE ($).. 
        pMT = 17733;                                                            //Hora para el análisis horario..................
        pLLV = 8000;                                                            //Precio de construcción de línea BT ($/mts)(2011)
        vmcc = 0.28;                                                            //Valor mínimo de la curva de carga..............
        hora = 25;                                                              //Hora para el analisis horario.................. 
        NIND = 20;                                                              //(n° de cromosomas o candidatos a solución).....
        MAXGEN = 150;                                                             //Número máximo de generaciones (iteraciones)....
        Pcruza = 0.9;                                                           //Probabilidad de cruza .........................      
        Pmutacion = 0.4;                                                        //Probabilidad de mutación.......................                                            
        //vectorcruza = {0.3333,0.333,0.3334};                          //Optimización de la Cruza.......................    
        //vectormutacion = {0.3333,0.3333,0.3334};                      //Optimización de la Mutación....................
        TimeOn = System.currentTimeMillis();                                    //Inicio contador de tiempo......................
     }
 
    
    //Constructor que permite cargar datos de la Red
    public TDs_Location (int N_Source,double Vb, double Vbm,double MVAb,double fe,double InT,double InF,double FC,double fp,double[][] 
              matrizLineas,double[][] matrizConductores,double[][] matrizDistancias,double [][] matrizCargas,double[][] matrizNodosMT){
       
        this.M_Lineas = new Array2DRowRealMatrix(matrizLineas);                 //Matriz de lineas...............................
        this.nl = M_Lineas.getRowDimension();                                   //N° de lineas de la Red.........................
        this.M_Cargas = new Array2DRowRealMatrix(matrizCargas);                 //Matriz de Cargas...............................
        this.nc = M_Cargas.getRowDimension();                                   //N° de cargas de la Red.........................
        this.ng = N_Source;                                                     //N° de generadores..............................
        this.nn=nc+ng;                                                          //N° de nodos de la Red..........................
        this.Vb = Vb;                                                           //[kV] Red de B.T................................
        this.Vbm = Vbm;                                                         //[kV] Red de M.T................................
        this.MVAb = MVAb;                                                       //[MVA] Base.....................................
        this.fe = fe;                                                           //Factor de energía..............................
        this.InT = InT;                                                         //Tasa de interes................................
        this.InF = InF;                                                         //Tasa de descuento..............................
        this.Fc = FC;                                                           //Factor de Carga................................
        this.Fp = fp;                                                           //Factor de pérdidas.............................
        this.M_Cond = new Array2DRowRealMatrix(matrizConductores);              //Matriz de Conductores..........................
        this.M_Dist = new Array2DRowRealMatrix(matrizDistancias);               //Matriz de Distancias...........................
        this.NodosMT = matrizNodosMT;                                           //Matriz nodos M.T...............................
        this.gen =0;                                                            //Contador de Iteraciones........................
        this.NMem = 0;                                                          //Contador de Memoria............................
        TimeOn = System.currentTimeMillis();                                    //Inicio contador de tiempo......................
    }
    
    
    //Constructor que Permite Modificar Datos de la Red & Aspectos Económicos 
    public TDs_Location (int N_Source,double Vb, double Vbm,double MVAb,double fe,double InT,double InF,double FC,double fp,double[][] 
              matrizLineas,double[][] matrizConductores,double[][] matrizDistancias,double [][] matrizCargas,double[][] matrizNodosMT,
              int años,double cre_dem,double r,double FL,double FOL,double pp,double pMT,double pLLV,double vmcc, int hora){
       
        this.M_Lineas = new Array2DRowRealMatrix(matrizLineas);                 //Matriz de lineas...............................
        this.nl = M_Lineas.getRowDimension();                                   //N° de lineas de la Red.........................
        this.M_Cargas = new Array2DRowRealMatrix(matrizCargas);                 //Matriz de Cargas...............................
        this.nc = M_Cargas.getRowDimension();                                   //N° de cargas de la Red.........................
        this.ng = N_Source;                                                     //N° de generadores..............................
        this.nn=nc+ng;                                                          //N° de nodos de la Red..........................
        this.Vb = Vb;                                                           //[kV] Red de B.T................................
        this.Vbm = Vbm;                                                         //[kV] Red de M.T................................
        this.MVAb = MVAb;                                                       //[MVA] Base.....................................
        this.fe = fe;                                                           //Factor de energía..............................
        this.InT = InT;                                                         //Tasa de interes................................
        this.InF = InF;                                                         //Tasa de descuento..............................
        this.Fc = FC;                                                           //Factor de Carga de la Red......................
        this.Fp = fp;                                                           //Factor de pérdidas.............................
        this.M_Cond = new Array2DRowRealMatrix(matrizConductores);              //Matriz de Conductores..........................
        this.M_Dist = new Array2DRowRealMatrix(matrizDistancias);               //Matriz de Distancias...........................
        this.NodosMT = matrizNodosMT;                                           //Matriz nodos M.T...............................
        this.gen =0;                                                            //Contador de Iteraciones........................
        this.NMem = 0;                                                          //Contador de Memoria............................
        this.years = años;                                                      //Años recuperación..............................
        this.Crec_Dem = cre_dem;                                                //Crecimiento de la demanda......................
        this.r = r;                                                             //Tasa de Descuento..............................
        this.FL = FL;                                                           //Factor de Carga................................
        this.FOL = FOL;                                                         //Factor de sobrecarga del Transformador.........
        this.PP = pp;                                                           //Precio de la energía de pérdidas reales........
        this.pMT = pMT;                                                         //Precio Construcción líneas de M.T ($/mts)......
        this.pLLV = pLLV;                                                       //Precio Construcción líneas de B.T ($/mts)......
        this.vmcc = vmcc;                                                       //Valor mínimo de la curva de la carga...........
        this.hora = hora;                                                       //Hora para el análisis horario..................
        TimeOn = System.currentTimeMillis();                                    //Inicio contador de tiempo......................
    }
    
    
    //Constructor que Permite Modificar Datos de la Red, Aspectos Económicos & Algunos Aspectos del AG (N°Individuos & N°Iteraciones) 
    public TDs_Location (int N_Source,double Vb, double Vbm,double MVAb,double fe,double InT,double InF,double FC,double fp,double[][] 
              matrizLineas,double[][] matrizConductores,double[][] matrizDistancias,double [][] matrizCargas,double[][] matrizNodosMT,
              int años,double cre_dem,double r,double FL,double FOL,double pp,double pMT,double pLLV,double vmcc, int hora,int NIND,int MAXGEN){
       
        this.M_Lineas = new Array2DRowRealMatrix(matrizLineas);                 //Matriz de lineas...............................
        this.nl = M_Lineas.getRowDimension();                                   //N° de lineas de la Red.........................
        this.M_Cargas = new Array2DRowRealMatrix(matrizCargas);                 //Matriz de Cargas...............................
        this.nc = M_Cargas.getRowDimension();                                   //N° de cargas de la Red.........................
        this.ng = N_Source;                                                     //N° de generadores..............................
        this.nn=nc+ng;                                                          //N° de nodos de la Red..........................
        this.Vb = Vb;                                                           //[kV] Red de B.T................................
        this.Vbm = Vbm;                                                         //[kV] Red de M.T................................
        this.MVAb = MVAb;                                                       //[MVA] Base.....................................
        this.fe = fe;                                                           //Factor de energía..............................
        this.InT = InT;                                                         //Tasa de interes................................
        this.InF = InF;                                                         //Tasa de descuento..............................
        this.Fc = FC;                                                           //Factor de Carga de la Red......................
        this.Fp = fp;                                                           //Factor de pérdidas.............................
        this.M_Cond = new Array2DRowRealMatrix(matrizConductores);              //Matriz de Conductores..........................
        this.M_Dist = new Array2DRowRealMatrix(matrizDistancias);               //Matriz de Distancias...........................
        this.NodosMT = matrizNodosMT;                                           //Matriz nodos M.T...............................
        this.gen =0;                                                            //Contador de Iteraciones........................
        this.NMem = 0;                                                          //Contador de Memoria............................
        this.years = años;                                                      //Años recuperación..............................
        this.Crec_Dem = cre_dem;                                                //Crecimiento de la demanda......................
        this.r = r;                                                             //Tasa de Descuento..............................
        this.FL = FL;                                                           //Factor de Carga................................
        this.FOL = FOL;                                                         //Factor de sobrecarga del Transformador.........
        this.PP = pp;                                                           //Precio de la energía de pérdidas reales........
        this.pMT = pMT;                                                         //Precio Construcción líneas de M.T ($/mts)......
        this.pLLV = pLLV;                                                       //Precio Construcción líneas de B.T ($/mts)......
        this.vmcc = vmcc;                                                       //Valor mínimo de la curva de la carga...........
        this.hora = hora;                                                       //Hora para el análisis horario..................
        this.NIND = NIND;                                                       //Número de Individuos Población Inicial.........
        this.MAXGEN = MAXGEN;                                                   //Número de Iteraciones..........................
        TimeOn = System.currentTimeMillis();                                    //Inicio contador de tiempo......................
    }
    
    
    //Constructor que Permite Modificar Datos de la Red, Aspectos Económicos & Algunos Aspectos del AG(N°Individuos;N°Iteraciones
    //Probabilidad de Cruza; Probabilidad de mutacion)
    public TDs_Location (int N_Source,double Vb, double Vbm,double MVAb,double fe,double InT,double InF,double FC,double fp,double[][] 
              matrizLineas,double[][] matrizConductores,double[][] matrizDistancias,double [][] matrizCargas,double[][] matrizNodosMT,
              int años,double cre_dem,double r,double FL,double FOL,double pp,double pMT,double pLLV,double vmcc, int hora,int NIND,
              int MAXGEN,double Pcruza,double Pmutacion){
       
        this.M_Lineas = new Array2DRowRealMatrix(matrizLineas);                 //Matriz de lineas...............................
        this.nl = M_Lineas.getRowDimension();                                   //N° de lineas de la Red.........................
        this.M_Cargas = new Array2DRowRealMatrix(matrizCargas);                 //Matriz de Cargas...............................
        this.nc = M_Cargas.getRowDimension();                                   //N° de cargas de la Red.........................
        this.ng = N_Source;                                                     //N° de generadores..............................
        this.nn=nc+ng;                                                          //N° de nodos de la Red..........................
        this.Vb = Vb;                                                           //[kV] Red de B.T................................
        this.Vbm = Vbm;                                                         //[kV] Red de M.T................................
        this.MVAb = MVAb;                                                       //[MVA] Base.....................................
        this.fe = fe;                                                           //Factor de energía..............................
        this.InT = InT;                                                         //Tasa de interes................................
        this.InF = InF;                                                         //Tasa de descuento..............................
        this.Fc = FC;                                                           //Factor de Carga de la Red......................
        this.Fp = fp;                                                           //Factor de pérdidas.............................
        this.M_Cond = new Array2DRowRealMatrix(matrizConductores);              //Matriz de Conductores..........................
        this.M_Dist = new Array2DRowRealMatrix(matrizDistancias);               //Matriz de Distancias...........................
        this.NodosMT = matrizNodosMT;                                           //Matriz nodos M.T...............................
        this.gen =0;                                                            //Contador de Iteraciones........................
        this.NMem = 0;                                                          //Contador de Memoria............................
        this.years = años;                                                      //Años recuperación..............................
        this.Crec_Dem = cre_dem;                                                //Crecimiento de la demanda......................
        this.r = r;                                                             //Tasa de Descuento..............................
        this.FL = FL;                                                           //Factor de Carga................................
        this.FOL = FOL;                                                         //Factor de sobrecarga del Transformador.........
        this.PP = pp;                                                           //Precio de la energía de pérdidas reales........
        this.pMT = pMT;                                                         //Precio Construcción líneas de M.T ($/mts)......
        this.pLLV = pLLV;                                                       //Precio Construcción líneas de B.T ($/mts)......
        this.vmcc = vmcc;                                                       //Valor mínimo de la curva de la carga...........
        this.hora = hora;                                                       //Hora para el análisis horario..................
        this.NIND = NIND;                                                       //Número de Individuos Población Inicial.........
        this.MAXGEN = MAXGEN;                                                   //Número de Iteraciones..........................
        this.Pcruza = Pcruza;                                                   //Probabilidad de Cruza..........................
        this.Pmutacion = Pmutacion;                                             //Probabilidad de Mutación.......................
        TimeOn = System.currentTimeMillis();                                    //Inicio contador de tiempo......................
        g = Crec_Dem / 100;
        fproy = Math.pow(1+g, años)/(FL*FOL);
    }
    
    
    
    //EJECUTAR ALGORITMO:
    public void Run_TDs_Location(){ 
        
        cromosoma = Metodos.creacionCromosoma(nc, ng, nc,0.2);                  //Inicializar Población (Memoria)................
        Best = Metodos.Best(MAXGEN+1);                                          //Restablecer Contadores.........................   
        Metodos Metodos = new Metodos(M_Cargas,M_Lineas,years,g);               //Ordenar Datos del Sistema......................
        Minfo = Metodos.crearMatrizDeInformacion(nn-1,Metodos.getLineas());     //Crear Matriz de Información....................
        distanciasT = Metodos.Dijkstra(Minfo);                                  //Cálculo de Todas las Distancias................
        ValoresActuales = Metodos.VA_VAC(years,g,r);                            //Cálculo del (VA) y (VAC).......................
        Evaluador eval = new Evaluador(nl, nn-1, ng, nc-1, Vb, Vbm, MVAb, fe, InT, InF, Fc, Fp, M_Lineas, M_Cond, M_Dist, M_Cargas, NodosMT,distanciasT, ind,Metodos.getP(),Metodos.getLineas(),Metodos.getPosicion(), Minfo, cromosoma, fproy, years, PP, pMT, pLLV, hora, ValoresActuales, 0, 0, Mem, NMem, evolmemoria);
        GeneticAlgorithm AG = new GeneticAlgorithm(nl,nn-1,ng,nc-1,Vb,Vbm,MVAb,fe,InT,InF,Fc,Fp,M_Lineas,M_Cond,M_Dist,M_Cargas,NodosMT,distanciasT,ind,Metodos.getP(),Metodos.getLineas(),Metodos.getPosicion(),Minfo,eval.getcromosoma(),fproy,years,PP,pMT,pLLV,hora,ValoresActuales,fig,indkVA,eval.getMem(),eval.getNMem(),evolmemoria,Pcruza,Pmutacion,eval.getneto(),vectorcruza,vectormutacion,gen,MAXGEN,Best);
        AG.RunAG();
        Evaluador eval_exit = new Evaluador(nl, nn-1, ng, nc-1, Vb, Vbm, MVAb, fe, InT, InF, Fc, Fp, M_Lineas, M_Cond, M_Dist, M_Cargas, NodosMT,distanciasT, ind,Metodos.getP(),Metodos.getLineas(),Metodos.getPosicion(), Minfo,AG.getTheBestChrom(), fproy, years, PP, pMT, pLLV, hora, ValoresActuales, 1, 1,eval.getMem(), NMem, evolmemoria);
        
        this.LineasAbiertas = Metodos.lineasAbiertas(eval_exit.getAsignacionLineas(),M_Lineas);
        this.LineasCerradas = Metodos.lineasCerradas(eval_exit.getAsignacionLineas(),M_Lineas);
        
        Metodos.display(AG.getMemoryBest(),eval_exit.getTDs(),eval_exit.getkVA(),eval_exit.getkVAs(),eval_exit.getTDs_Norm(),eval_exit.getPerd_Red(),eval_exit.getPerTDs(),eval_exit.getdistLMT(),eval_exit.getauxLLV(),eval_exit.getfase(),eval_exit.getregulacion(),eval_exit.getENSkWh(),eval_exit.getnodo(),TimeOn);
        
        this.costoTotal = AG.getMemoryBest().getEntry(0,0);
        this.TDs = eval_exit.getTDs();
        this.kVA = eval_exit.getkVA();
        this.kVAs = eval_exit.getkVAs();
        this.TDs_Norm = eval_exit.getTDs_Norm();
        this.Perd_Red = eval_exit.getPerd_Red();
        this.PerdTDs =  eval_exit.getPerTDs();
        this.distLMT =  eval_exit.getdistLMT();
        this.distLBT = eval_exit.getauxLLV();
        this.nodoConTensionMasBaja = eval_exit.getnodo();
        this.tensionMasBaja = eval_exit.getregulacion();
        this.faseTensionMasBaja = eval_exit.getfase();
        this.cantidadEnergiaNoSuministrada = eval_exit.getENSkWh();
        this.MatrizVoltajesGrados = eval_exit.getVoltajes(); //GRADOS
        this.MatrizVoltajesRadianes = VoltajesNodosRadianes(eval_exit.getVoltajes());//RADIANES
        this.MatrizCorrientesRadianes = eval_exit.getCorrientes();//RADIANES
        this.MatrizCorrientesGrados = CorrientesLineasGrados(eval_exit.getCorrientes());//GRADOS
    }
    
    
    //DIAGRAMA DE FLUJO DEL PROGRAMA:
    private void diagramaFlujo(){
        
 /* @author Andreuw Madrid Carreño  "Escuela de Ingeniería Eléctrica - PUCV"
 *                  
 * 
 *                   |------------|
 *                   |   INICIO   |
 *                   |------------|
 *                         |
 *          |------------------------------|
 *          |*Cargar Variables del sistema |
 *          |*Cargar variables elementales |
 *          |*Creación Población Inicial   |
 *          |*Ordenar Datos del Sistema    |
 *          |*Generar Matriz de Información|
 *          |------------------------------|
 *                         |  
 *         |--------------------------------|
 *         |*Cálculo de todas las distancias|
 *         |           (Dijktra)            | 
 *         |*   Cálculo del (VA) y (VAC)    |
 *         |--------------------------------|
 *                         |    
 *          |-----------------------------|
 *          |   Evaluar población inical  |
 *          |-----------------------------|         
 *                         |
 *                         |  <--------------------|
 *                 |--------------|                | 
 *                 |   RANKING    |                |
 *                 |--------------|                | 
 *                         |                       |
 *              |---------------------|            | 
 *              |   OPERADOR CRUZA    |            |
 *              |---------------------|            |
 *              |*Selecionar Padre 1  |            | 
 *              |*Selecionar Padre 2  |            |
 *              |*Generar hijos 1 & 2 |            |
 *              |*Evaluar hijos 1 & 2 |            |
 *              |*Intercambiar hijos  |            |
 *              | por padres de ser   |            |
 *              | mejores.            |            |
 *              |---------------------|            |
 *                        |                        |
 *              |---------------------|            |
 *              |  OPERADOR MUTACIÓN  |            |
 *              |---------------------|            |
 *              |*Selecionar Padre 1  |            |
 *              |*Selecionar Padre 2  |            |
 *              |*Generar hijos 1 & 2 |            |
 *              |*Evaluar hijos 1 & 2 |            |
 *              |*Intercambiar hijos  |            |
 *              | por padres de ser   |            | 
 *              | mejores.            |            | 
 *              |---------------------|            |
 *                        |                        |
 *                 |--------------|                |
 *                 |   ELITISMO   |                |
 *                 |--------------|                |
 *                        |                        |
 *                  _____/ \____                   |
 *                 /            \                  |
 *                <| gen<MAXGEN |>------>----------|
 *                 \_____   ____/
 *                       \ /
 *                        |
 *          |----------------------------|
 *          |   Evaluar Mejor Candidato  |
 *          |----------------------------| 
 *                        |
 *                   |---------|                
 *                   |   FIN   |                
 *                   |---------|  
 * 
 **/    
    }
      
    
    //MÉTODOS GET():
    
    public double[][] getMatrizVoltajesGrados(){
        return MatrizVoltajesGrados.getData();
    }
    
    public double [][] getMatrizVoltajesRadianes(){
        return MatrizVoltajesRadianes.getData();
    }
    
    public double[][] getMatrizCorrientesRadianes(){
        return MatrizCorrientesRadianes.getData();
    }
    
    public double [][] getMatrizCorrientesGrados (){
        return MatrizCorrientesGrados.getData();
    }
    
    public int getyears() {
        return years;
    }

    public double getg() {
        return g;
    }

    public RealMatrix getTDs() {
        return TDs;
    }

    public RealMatrix getauxiliar3() {
        return auxiliar3;
    }

    public RealMatrix getMinfo() {
        return Minfo;
    }

    public RealMatrix getcromosoma() {
        return cromosoma;
    }

    public double getind() {
        return ind;
    }

    public double getfproy() {
        return fproy;
    }

    public double getPP() {
        return PP;
    }

    public double getpMT() {
        return pMT;
    }

    public double[] getValoresActuales() {
        return ValoresActuales;
    }

    public double getpLLV() {
        return pLLV;
    }

    public double gethora() {
        return hora;
    }

    public double getfig() {
        return fig;
    }

    public double[] getLineasAbiertas() {
        return LineasAbiertas;
    }
    
    public double[] getLineasCerradas() {
        return LineasCerradas;
    }

    public double getcostoTotal() {
        return costoTotal;
    }

    public double getPerd_Red() {
        return Perd_Red;
    }

    public double getPerdTDs() {
        return PerdTDs;
    }

    public double getdistLMT() {
        return distLMT;
    }

    public double getdistLBT() {
        return distLBT;
    }

    public int getnodoConTensionMasBaja() {
        return nodoConTensionMasBaja;
    }

    public double gettensionMasBaja() {
        return tensionMasBaja;
    }

    public int getfaseTensionMasBaja() {
        return faseTensionMasBaja;
    }

    public double getcantidadEnergiaNoSuministrada() {
        return cantidadEnergiaNoSuministrada;
    }

    public RealMatrix getkVA() {
        return kVA;
    }

    public RealMatrix getkVAs() {
        return kVAs;
    }

    public RealMatrix getTDs_Norm() {
        return TDs_Norm;
    }
    
    public double Vbm(){
        return Vbm;
    }
}

