/*
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 |xxxxxxx|                        (EVALUADOR)                         |xxxxxxx|
 |xxxxxxx|          OPTIMIZACIÓN DEL ABASTECIMIENTO ELÉCTRICO         |xxxxxxx|
 |xxxxxxx|                   EN REDES DE DISTRIBUCIÓN                 |xxxxxxx|
 |xxxxxxx|                                                            |xxxxxxx|
 |xxxxxxx|             CONSIDERANDO UN ANÁLISIS TRIFÁSICO             |xxxxxxx|
 |xxxxxxx|                            2014                            |xxxxxxx|
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 |xxxxxxx|                JMendoza - AMadrid - HVargas                |xxxxxxx|
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 */
package newsensimulator.model.problem.tdslocationforss.Run_Algorithm.TDs_Location;

import flanagan.complex.ComplexMatrix;
import newsensimulator.model.problem.tdslocationforss.Run_Algorithm.RedesDeDistribucion.DatosTrafos;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;


/**
 * @author Andreuw Madrid Carreño  "Escuela de Ingeniería Eléctrica - PUCV"
 */


public class Evaluador {
    
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
    private DatosTrafos datostrafos = new DatosTrafos();
    private RealMatrix Chrom;
    private RealMatrix auxlineas;
    private RealMatrix TDs;
    private int nTDs;
    private RealMatrix auxiliar3;
    private RealMatrix aux33;
    private RealMatrix aux44;
    private RealMatrix aux99;
    private RealMatrix aux333;
    private RealMatrix TDs_Norm;
    private double Factor_utilidad;
    private double Inf = Double.POSITIVE_INFINITY;
    private double NaN = Double.NaN;
    private RealMatrix distLMT;
    private RealMatrix conexMT;
    private RealMatrix auxLLV;
    private double LLVc;
    private RealMatrix neto;
    private double [] ValoresActuales;
    private RealMatrix distancia;
    private RealMatrix asignacion;
    private int e =0;
    private int s;
    private int d;
    private double b;
    private RealMatrix radial;
    private RealMatrix radial1;
    private double C;
    private double CT = 0;
    private RealMatrix auxlineas12;
    private RealMatrix asignacionL1;
    private RealMatrix asignacionL;
    private RealMatrix M_Lineas_aux;
    private RealMatrix Columna_1_Voltajes;
    private RealMatrix Minimos;
    private RealMatrix Minimos1;
    private double regulacion;
    private int nodo; 
    private int fase;
    private RealMatrix X;
    private RealMatrix Min_Aux;
    private RealMatrix Mem;
    private RealMatrix evolmemoria;
    private int NMem;
    private int contador_memoria;
    private RealMatrix Mem_copy;
    private RealMatrix kVA;
    private RealMatrix kVAs;
    private double Perd_Red;
    private double PerdTDs;
    private double ENSkWh;
    private double eENSc;
    private int nl1;
    private int nn1;
    private int nc1;
    private double Vb1;
    private double Vbm1;
    private double MVAb1;
    private double fe1;
    private double InT1;
    private double InF1;
    private double Fc1;
    private double Fp1;
    private RealMatrix M_Lineas1;
    private RealMatrix M_Cargas1;
    private RealMatrix M_Dist1;
    private RealMatrix M_Cond1;
    private double[][] NodosMT2;
    private RealMatrix Mem1;
    private int NMem1;
    private RealMatrix aux1;
    private RealMatrix M_Lineas_paraFlujo;
    private RealMatrix AsignacionLineas;//----------> Matriz que contandrá las asignaciones radiales
    private int NF=0;
    private RealMatrix Voltajes;
    private RealMatrix IL;
    private RealMatrix Corrientes;

    public  Evaluador(int nl,int nn,int ng,int nc,double Vb,double Vbm,double MVAb,double fe,double InT,double InF,double Fc,double Fp,RealMatrix M_Lineas, RealMatrix M_Cond,
             RealMatrix M_Dist, RealMatrix M_Cargas, double[][] NodosMT,RealMatrix distanciasT,double ind,RealMatrix P,RealMatrix lineas,RealMatrix posicion,RealMatrix
             Minfo,RealMatrix cromosoma,double fproy,int years,double PP,double pMT,double pLLV,int hora,double[] ValoresActuales,int fig,int indkVA,RealMatrix Mem,int 
             NMem,RealMatrix evolmemoria){
        
    
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
        this.P=P;
        this.lineas=lineas;
        this.posicion=posicion;
        this.Minfo= Minfo;
        this.cromosoma= cromosoma;
        this.fproy = fproy;
        this.years = years;
        this.PP = PP;
        this.pMT = pMT;
        this.pLLV = pLLV;
        this.hora = hora;
        this.ValoresActuales=ValoresActuales;
        this.fig = fig;
        this.indkVA = indkVA;
        this.Mem = Mem;
        this.evolmemoria=evolmemoria;
        this.NMem=NMem;
             
        
        
        /*
        VA = ValoresActuales[0];
        VAC = ValoresActuales[1];
        VAC2 = ValoresActuales[2];
        */
        VA=3.127171020941989;
        VAC=3.477981396740861;
        VAC2=4.125009815951134;
        
//     ************************************************************************************    
//     >> Almacenar Variables Nativas del Sistema, Para el siguiente Candidato <<
//     ************************************************************************************
        nl1 = nl;
        nn1 = nn;
        nc1 = nc;
        Vb1 = Vb;
        Vbm1 = Vbm;
        MVAb1 = MVAb;
        fe1 = fe;
        InT1 = InT;
        InF1 = InF;
        Fc1 = Fc;
        Fp1 = Fp;
        M_Lineas1 = M_Lineas.copy();
        M_Cargas1 = M_Cargas.copy();
        M_Dist1 = M_Dist.copy();
        M_Cond1 = M_Cond.copy();
        NodosMT2 = NodosMT;
//     ***** End (Almacenar Variables Nativas del Sistema, Para el siguiente Candidato)*****        

        
        int fC = cromosoma.getRowDimension();
        int cC = cromosoma.getColumnDimension();
        neto = new Array2DRowRealMatrix(new double[fC][1]);
        contador_memoria =1;
        
   
//     *************************************************************************************************      
//     >> Loop Principal con el cual se hace variar la lectura de candidato <<
//     *************************************************************************************************            
        for (int i = 0; i < fC; i++) {
            
        
            
            
//        **********************************************************************************************      
//         >> Definir candidato a evaluar <<
//        **********************************************************************************************                  
            Chrom = new Array2DRowRealMatrix(cromosoma.getRow(i));
//        ************************End (Definir candidato a evaluar)*************************************
           
       
            
            
            
//        **********************************************************************************************      
//         >> Definir Posiciones de los TDs en el candidato actual <<
//        **********************************************************************************************   
            TDs = Metodos.find(Chrom);                                       //Posicion de los TDs en el sistema
            nTDs = TDs.getColumnDimension() * TDs.getRowDimension();         //Número de TDs en el sistema
            nn = ng + nc;                                                    //Número de nodos del sistema  
//        ***********End (Definir Posiciones de los TDs en el candidato actual)*************************
            
            
            
            
            
            
            
//        **********************************************************************************************              
//         >> Repartir la Carga por Distancias << (RepartoJM2)
//        **********************************************************************************************              
            int ns = TDs.getColumnDimension() * TDs.getRowDimension();   //ns:numero de TDs a instalar              
            int fn = distanciasT.getRowDimension();
            int cn = distanciasT.getColumnDimension();
            distancia = new Array2DRowRealMatrix(new double[fn][ns]);
            for (int col = 0; col < TDs.getColumnDimension(); col++) {
                distancia.setColumn(col, distanciasT.getColumn((int) TDs.getEntry(0, col)));
            }
//          >> Asignación <<
            RealMatrix m = Metodos.OrdenSortMatlab2(distancia);
            m = Metodos.addScalar(m,1);//se le sumó un uno a cada posicion, para poder comparar con Matlab
            //En las siguientes lineas, se le asigna el valor nulo a la posicion del TDs, por lo que si se deja
            //que la posicion "0" de Java o sea "1" de Matlab exista, se confundirá lo buscado!
            
            double[] nulo = new double[m.getColumnDimension()];
            for (int k = 0; k < TDs.getColumnDimension(); k++) {
                m.setRow((int) TDs.getEntry(0, k), nulo);
            }
            
            asignacion = new Array2DRowRealMatrix(new double[fn][ns]);

            for (int n = 0; n < ns; n++) {
                RealMatrix aux = Metodos.findModificado(m.getColumnMatrix(0), n+1);
                if (aux.getEntry(0, 0) != Inf) {
                    for (int h = 0; h < aux.getColumnDimension(); h++) {
                        asignacion.setEntry((int) aux.getEntry(0, h), n, 1);
                    }

                    asignacion.setEntry((int) TDs.getEntry(0, n), n, 1);
                }
            }
//        **********************End (Repartir la Carga por Distancias)***********************************                        

            
            
            
            
            
            
//        **********************************************************************************************            
//                              >> Generar Rutas Radiales (Asignacion)<< 
//        **********************************************************************************************            
            int auxiliar4;
            asignacionL = new Array2DRowRealMatrix(nl, nTDs + 1); 
            for (int j = 0; j < nTDs; j++) {
                auxiliar3 = new Array2DRowRealMatrix(asignacion.getColumn(j));
                auxiliar4 = Metodos.find(auxiliar3).getRow(0).length;
                if (auxiliar4 > 1) {
                   
                    //******************Star Prim*********************************************
                    RealMatrix Minfo1 = Minfo.copy();
                    int l = Minfo.getRowDimension();
                    int cc = Minfo.getColumnDimension();
                    RealMatrix nfuera = Metodos.findModificado(auxiliar3, 0);
                    int lnf = nfuera.getColumnDimension() * nfuera.getRowDimension(); 
                    if(nfuera.getEntry(0,0)== Inf){//Protección, en caso que nfuera o sea void
                       lnf =0;
                    }
                    RealMatrix vectorInfF = new Array2DRowRealMatrix(new double[l]);
                    vectorInfF = vectorInfF.transpose();
                    vectorInfF = Metodos.addScalar(vectorInfF,Inf) ;
                    
                    RealMatrix vectorInfC = new Array2DRowRealMatrix(new double[cc]);
                    vectorInfC = Metodos.addScalar(vectorInfC,Inf) ;
                    
                    RealMatrix kk = new Array2DRowRealMatrix(new double[l]); 
                    kk=Metodos.vectorIncrementalFila(kk,l);
                   
                    RealMatrix listV = new Array2DRowRealMatrix(new double[l]);
                    listV = listV.transpose();
                    RealMatrix source = new Array2DRowRealMatrix(new double[l-1]);
                    source = source.transpose();
                    source.setSubMatrix(TDs.getData(),0,0);
                    RealMatrix distance = new Array2DRowRealMatrix(new double[l-1]);
                    distance = distance.transpose();
                    RealMatrix destination = new Array2DRowRealMatrix(new double[l-1]);
                    destination = destination.transpose(); 
  
                    if (nfuera.getEntry(0, 0) != Inf) {
                        for (int k = 0; k < lnf; k++) {
                            Minfo1.setRow((int) nfuera.getEntry(0, k), vectorInfF.getRow(0));
                            Minfo1.setColumn((int) nfuera.getEntry(0, k), vectorInfC.getColumn(0));
                        }
                    }
                    for (int ñ = 0; ñ < TDs.getColumnDimension(); ñ++) {
                        listV.setEntry(0, (int) TDs.getEntry(0, ñ), 1);
                    }

                    e=0;
                    while (e < l-1) {
                        double min = Inf;
                        for (int ik = 0; ik < l; ik++) {
                            if (listV.getEntry(0, ik) == 1) {
                                for (int jk = 0; jk < l; jk++) {
                                    if (listV.getEntry(0, jk) == 0) {
                                        if (ind == 1) {
                                            if (min >(Minfo1.getEntry(ik, jk) * Math.pow(P.getEntry(0, jk), 2))) {
                                                min = Minfo1.getEntry(ik, jk) * Math.pow(P.getEntry(0, jk), 2);
                                                b = Minfo1.getEntry(ik, jk) * Math.pow(P.getEntry(0, jk), 2);
                                                s = ik;
                                                d = jk;
                                            }
                                        } 
                                        else {
                                            if (min > Minfo1.getEntry(ik, jk)) {
                                                min = Minfo1.getEntry(ik, jk);
                                                b = Minfo1.getEntry(ik, jk);
                                                s = ik;
                                                d = jk;
                                            }
                                        }

                                    }
                                }
                            }
                        }
                        
                        
                            listV.setEntry(0, d, 1);
                            distance.setEntry(0, e, b);
                            source.setEntry(0, e, s);
                            destination.setEntry(0, e, d);
                            e++;       
                    }
                    CT=0;
                    radial = new Array2DRowRealMatrix(new double[source.getColumnDimension()][source.getRowDimension() + destination.getRowDimension() + distance.getRowDimension()]);
                    radial.setColumn(0, source.transpose().getColumn(0));
                    radial.setColumn(1, destination.transpose().getColumn(0));
                    radial.setColumn(2, distance.transpose().getColumn(0));
                    RealMatrix radial1 = new Array2DRowRealMatrix(new double[l-lnf][radial.getColumnDimension()]);
                    radial1=radial.getSubMatrix(0,radial1.getRowDimension()-2,0,radial1.getColumnDimension()-1);
                    radial = radial1;
                    RealMatrix auxSuma = radial.getSubMatrix(0,l - lnf - 2, 2, 2);
                    C = Metodos.verificarSuma(auxSuma.getColumn(0));
//               ********************End (Prim)*********************************************************                    
                    CT = CT + C;
//         ********************End (Generar Rutas Radiales)*********************************************************

                    
                    
                    
                    
                    
                    
                    
                    
                    
//        **********************************************************************************************            
//                          >> Generar Radial para Lineas Abiertas (Asignacion)<< 
//        **********************************************************************************************                    
                    if(NF ==0){
                        AsignacionLineas = new Array2DRowRealMatrix(new double[radial.getRowDimension()][3]);
                        AsignacionLineas.setSubMatrix(radial.getData(),0,0);
                        NF++;
                    }
                    else{
                        RealMatrix auxLineasAbiertas = new Array2DRowRealMatrix(new double [
                        AsignacionLineas.getRowDimension()+radial.getRowDimension()][3]);
                        auxLineasAbiertas.setSubMatrix(AsignacionLineas.getData(),0,0);
                        auxLineasAbiertas.setSubMatrix(radial.getData(),AsignacionLineas.getRowDimension(),0);
                        AsignacionLineas = auxLineasAbiertas.copy();
                    }
//         ********************End (Generar Radial para Lineas Abiertas)********************************
                    
                    
                    
                    
                    
                    
                    
                    
                    
//         **********************************************************************************************                    
//                               >> Crear Matriz de Asignación de Líneas <<
//         **********************************************************************************************                    
                   
                    int fr = radial.getRowDimension();
                    int cr = radial.getColumnDimension();
                    int contador = 1;
                    radial.setColumn(0,Metodos.addScalar(radial.getColumnMatrix(0),1).getColumn(0));
                    radial.setColumn(1,Metodos.addScalar(radial.getColumnMatrix(1),1).getColumn(0));
                    for (int jk = 0; jk < fr; jk++) {
                        aux1 = Metodos.findMatlab(new Array2DRowRealMatrix(M_Lineas.getColumn(2)), radial.getEntry(jk,0)).transpose();
                        int aux3 = 0;
                        if (aux1.getEntry(0, 0) != Inf) {//if isempty(aux1)==0

                            RealMatrix aux22 = new Array2DRowRealMatrix(new double[aux1.getRowDimension()][1]);
                            for (int Din3 = 0; Din3 < aux22.getRowDimension(); Din3++) {
                                aux22.setEntry(Din3, 0, M_Lineas.getEntry((int) aux1.getEntry(Din3, 0), 3));
                            }              
                            
                            RealMatrix aux2 = Metodos.findMatlab(aux22, radial.getEntry(jk,1)).transpose(); 
                            if (aux2.getEntry(0, 0) != Inf) {//if isempty(aux2)==0
                                if (contador == 1) {
                                    RealMatrix auxlineas1 = new Array2DRowRealMatrix(new double[aux2.getRowDimension()][contador]);
                                    RealMatrix colAux = new Array2DRowRealMatrix(new double[aux2.getRowDimension()][1]);
                                    for (int Din3 = 0; Din3 < aux2.getRowDimension(); Din3++) {
                                        colAux.setEntry(Din3, 0, aux1.getEntry((int) aux2.getEntry(Din3, 0), 0));
                                    }
                                    auxlineas1.setColumnMatrix(0, colAux);
                                    auxlineas12 = auxlineas1.copy();
                                    contador++;
                                }
                                
                                else {
                                    RealMatrix auxlineas1 = new Array2DRowRealMatrix(new double[auxlineas12.getRowDimension()][contador]);
                                    RealMatrix colAux = new Array2DRowRealMatrix(new double[auxlineas12.getRowDimension()][1]);
                                    for (int Din3 = 0; Din3 < aux2.getRowDimension(); Din3++) {
                                        colAux.setEntry(Din3, 0, aux1.getEntry((int) aux2.getEntry(Din3, 0), 0));
                                    }
                                    auxlineas1.setSubMatrix(auxlineas12.getData(), 0, 0);
                                    auxlineas1.setColumnMatrix(auxlineas12.getColumnDimension(), colAux);
                                    auxlineas12 = auxlineas1.copy();
                                    contador++;
                                    
                                }
                                aux3 = 1;
                            }

                        } 
                        if (aux3 == 0) {
                            aux1 = Metodos.findMatlab(new Array2DRowRealMatrix(M_Lineas.getColumn(3)), radial.getEntry(jk, 0)).transpose();
                            if (aux1.getEntry(0, 0) != Inf) {
                                RealMatrix aux222 = new Array2DRowRealMatrix(new double[aux1.getRowDimension()][1]);
                                for (int Din3 = 0; Din3 < aux222.getRowDimension(); Din3++) {
                                    aux222.setEntry(Din3, 0, M_Lineas.getEntry((int) aux1.getEntry(Din3, 0), 2));
                                }
                                RealMatrix aux2 = Metodos.findMatlab(aux222, radial.getEntry(jk, 1)).transpose();
                                if (aux2.getEntry(0, 0) != Inf) {
                                    if (contador == 1) {
                                 
                                        RealMatrix auxlineas1 = new Array2DRowRealMatrix(new double[aux2.getRowDimension()][contador]);
                                        RealMatrix colAux = new Array2DRowRealMatrix(new double[aux2.getRowDimension()][1]);
                                        for (int Din3 = 0; Din3 < aux2.getRowDimension(); Din3++) {
                                            colAux.setEntry(Din3, 0, aux1.getEntry((int) aux2.getEntry(Din3, 0), 0));
                                        }
                                        auxlineas1.setColumnMatrix(0, colAux);
                                        auxlineas12 = auxlineas1.copy();
                                        contador++;
                                    } else {
                                        RealMatrix auxlineas1 = new Array2DRowRealMatrix(new double[auxlineas12.getRowDimension()][contador]);
                                        RealMatrix colAux = new Array2DRowRealMatrix(new double[auxlineas12.getRowDimension()][1]);
                                        for (int Din3 = 0; Din3 < aux2.getRowDimension(); Din3++) {
                                            colAux.setEntry(Din3, 0, aux1.getEntry((int) aux2.getEntry(Din3, 0), 0));
                                        }
                                        auxlineas1.setSubMatrix(auxlineas12.getData(), 0, 0);
                                        auxlineas1.setColumnMatrix(auxlineas12.getColumnDimension(), colAux);
                                        auxlineas12 = auxlineas1.copy();
                                        contador++;
                                    }
                                    
                                }
                            }
                        }
                    }
                        auxlineas = auxlineas12.copy();
                        for (int iAsig = 0; iAsig < auxlineas.getColumnDimension(); iAsig++) {
                            asignacionL.setEntry((int) auxlineas.getEntry(0, iAsig), j, 1);
                            asignacionL.setEntry((int) auxlineas.getEntry(0, iAsig), nTDs, 1);
                        }   
                }
                
            }
//        *****************************End (Asignación de Líneas)*******************************            

            
                    
            
            
            //....................................................................
            //Calculo de las tasas de fallas de cada Feeder
             double  ENS = 0;
            //El profesor señaló que no era necesario, si queda tiempo terminar!!!
            //....................................................................
             
             
             
             
             
             
//        **************************************************************************************              
//        >> Modificación del Sistema para Cálculos de Pérdidas (Flujo de Potencia Trifásico) <<
//        **************************************************************************************             
            nc = nc+1; //Aumentar el numero de nodos en 1
            RealMatrix M_CargasMasUno = new Array2DRowRealMatrix(new double[M_Cargas.getRowDimension()+1][M_Cargas.getColumnDimension()]); //
            M_CargasMasUno.setSubMatrix(M_Cargas.getData(),0,0);
            M_CargasMasUno.setRow(nc,M_Cargas.getRow(0)); 
            M_CargasMasUno.setEntry(nc,0, nc+1);
            M_CargasMasUno.setRow(0,new double [] {1,0,0,0,0,0,0,1,0,0});
            M_Lineas_paraFlujo = M_Lineas.copy();
            if (Metodos.findMatlab(M_Lineas_paraFlujo.getColumnMatrix(3), 1).getEntry(0, 0) != Inf) {
                for (int ii = 0; ii < Metodos.findMatlab(M_Lineas_paraFlujo.getColumnMatrix(3), 1).getRowDimension(); ii++) {   
                    M_Lineas_paraFlujo.setEntry((int) Metodos.findMatlab(M_Lineas_paraFlujo.getColumnMatrix(3), 1).getEntry(ii, 0), 3, nc+1);    
                }
            }
            if (Metodos.findMatlab(M_Lineas_paraFlujo.getColumnMatrix(2), 1).getEntry(0, 0) != Inf) {
                for (int ii = 0; ii < Metodos.findMatlab(M_Lineas_paraFlujo.getColumnMatrix(2), 1).getRowDimension(); ii++) {   
                    M_Lineas_paraFlujo.setEntry((int) Metodos.findMatlab(M_Lineas_paraFlujo.getColumnMatrix(2), 1).getEntry(ii, 0), 2, nc+1);    
                }
            }
            int nl_Flujo=nl;
            for(int jj=0; jj<nTDs ; jj++){
                nl_Flujo++;
                M_Lineas_aux = new Array2DRowRealMatrix(new double[nl_Flujo][M_Lineas_paraFlujo.getColumnDimension()]);
                M_Lineas_aux.setSubMatrix(M_Lineas_paraFlujo.getData(),0,0);
                
                if(TDs.getEntry(0,jj) != 0){
                    M_Lineas_aux.setRow(nl_Flujo-1,new double [] {nl_Flujo, 0, 1, TDs.getEntry(0,jj)+1 ,1, 0.001, 0, 0, 0});
                }
                else{
                    M_Lineas_aux.setRow(nl_Flujo-1,new  double [] {nl_Flujo, 0, 1, nc+1 ,1, 0.001, 0, 0, 0});
                }
                M_Lineas_paraFlujo = M_Lineas_aux.copy();
            }
            
            aux333 = Metodos.findMatlab(asignacionL.getColumnMatrix(nTDs),0).transpose();
//         **********************END Modificacion del sistema************************************
            
             
//        **************************************************************************************              
//         >> Cálculo de las Pérdidas del sistema y Monto ($) (Flujo de Potencias Trifásico) <<
//        **************************************************************************************                  
            FlujoTrifasicoDePotencia flujoPotencia = new FlujoTrifasicoDePotencia(nl_Flujo, ng, nc, Vb, MVAb, fe, M_Lineas_paraFlujo, M_CargasMasUno, aux333, M_Cond, M_Dist);
            ComplexMatrix FDP = flujoPotencia.getFDP();
            
            IL = flujoPotencia.getIL();
            
            Voltajes = flujoPotencia.getVoltajes();
            
            Corrientes = flujoPotencia.getCorriente();
            
            ENS = flujoPotencia.getENS();
            double Perdidas = flujoPotencia.getPerdidas();
            
            Perd_Red = Perdidas*MVAb*1000;
            
//        (AUMENTADAS 10% POR EFECTO DE ARMONICOS)
            double eLLVc = 8760*Perdidas*MVAb*(1e6/1e3)*PP*VAC; //Costo pérdidas con VAC
            
//        *********************END (Flujo de Potencias Trifásico)******************************
            
            
            
            
//        **************************************************************************************            
//         >> Indice de Confiabilidad <<
//        **************************************************************************************
            ENS = 0 ; //El profesor lo dejó así
            ENSkWh = ENS*MVAb*(1e6/1e3);
            eENSc  = 3*ENSkWh*PP*VAC;    //Costo de la energía (el tres es por la multa)  
//        **************************End (Indice de Confiabilidad)*******************************
            
                

            
//        **************************************************************************************            
//         >> Cálculo del Costo de los Transformadores (Inversión) << 
//        **************************************************************************************            
            kVA = new Array2DRowRealMatrix(new double[FDP.getNrow()][1]);
            for (int ii = 0; ii < FDP.getNrow(); ii++) {
                kVA.setEntry(ii, 0, FDP.getElementCopy(ii, 3).getReal() * MVAb * (1e6 / 1e3));
            }
            kVAs = kVA.scalarMultiply(fproy);
            RealMatrix trafos = datostrafos.DatosTrafos(Vbm);//--> Valores de trasformadores 
            double DTc = 0;
            double CPerdPo = 0;
            double CPerdPcu = 0;
            double Pvacio = 0;
            double Pcobre = 0;
            PerdTDs = 0;
            RealMatrix isNaNaux = Metodos.isNaN(kVAs);
            double sumaIsNaN = 0;
            sumaIsNaN = Metodos.suma(isNaNaux);
              
                if (sumaIsNaN == 0) {
                    TDs_Norm = new Array2DRowRealMatrix(new double[nTDs][1]);
                    for (int kkk = 0; kkk < nTDs; kkk++) {
                        aux33 = Metodos.findMatlabCaso(trafos.getColumnMatrix(0), ">=", kVAs.getEntry(kkk, 0));
                        DTc = DTc + trafos.getEntry((int) aux33.getEntry(0, 0), 1); // ---> Costo compra de transformadores
                        TDs_Norm.setEntry(kkk, 0, trafos.getEntry((int) aux33.getEntry(0, 0), 0)); //--> Valores comerciales  
                        if (indkVA == 1) {
                            cromosoma.setEntry(i, (int) TDs.getEntry(0, kkk), kVAs.getEntry(kkk, 0));
                        }

                    }                      
//                >> Pérdidas en los Trasnformadores <<                      
                    for (int z = 0; z < nTDs; z++) {
                        aux44 = Metodos.findMatlabCaso(trafos.getColumnMatrix(0), ">=", kVAs.getEntry(z, 0));
                        Factor_utilidad = kVA.getEntry(z, 0) / TDs_Norm.getEntry(z, 0);

                        //            >> Pérdidas en TDs <<
                        Pvacio = Pvacio + trafos.getEntry((int) aux44.getEntry(0, 0), 2);
                        Pcobre = Pcobre + Factor_utilidad * trafos.getEntry((int) aux44.getEntry(0, 0), 3);

                        //            >> Costo Pérdidas en los Transformadores <<
                        CPerdPo = CPerdPo + 8760 * PP * VA * trafos.getEntry((int) aux44.getEntry(0, 0), 2);
                        CPerdPcu = CPerdPcu + 8760 * PP * VAC2 * Factor_utilidad * trafos.getEntry((int) aux44.getEntry(0, 0), 3);
                    }

                    PerdTDs = Pvacio + Pcobre;
                }
                else {
                    DTc = Inf;
                }
            
//        ******************End (Cálculo del Costo de los Transformadores)*********************                     
            
            
            
            
            
//        **************************************************************************************                      
//         >> Cálculo Costo en Líneas de MT (costoMT) <<
//        **************************************************************************************                      
            double LMVc = 0;
            RealMatrix NodosMT1 = new Array2DRowRealMatrix(NodosMT);
            NodosMT1 = NodosMT1.transpose();
            NodosMT1 = Metodos.addScalar(NodosMT1,-1);
            RealMatrix dMT = new Array2DRowRealMatrix(new double[NodosMT1.getRowDimension()][distancia.getColumnDimension()]);
            distLMT = new Array2DRowRealMatrix(new double[dMT.getRowDimension()][1]);
            conexMT = new Array2DRowRealMatrix(new double[dMT.getRowDimension()][1]);
            if (Metodos.isempty(NodosMT1) != true) {
                for (int jj = 0; jj < NodosMT1.getRowDimension(); jj++) {
                    dMT.setRow(jj, distancia.getRow((int) NodosMT1.getEntry(jj, 0)));
                }
                aux99 = Metodos.findMatlab(Metodos.convMatrixToVectorCol(dMT), Inf);
                if (aux99.getEntry(0, 0) != Inf) {
                    for (int jjj = 0; jjj < aux99.getColumnDimension(); jjj++) {
                        dMT=Metodos.recorreColumna(dMT,(int)aux99.getEntry(0,jjj),0);
                    }
                } 
                
                if (dMT.getRowDimension() == 1) {
                    distLMT = dMT.copy();
                    conexMT = Metodos.addScalar(conexMT, 1);  
                } 
                else {
                    Min_Aux = Metodos.minComplete(dMT);
                    distLMT = Min_Aux.getColumnMatrix(0);
                    conexMT = Min_Aux.getColumnMatrix(1);
                    
                }
                LMVc = Metodos.suma(distLMT) * pMT; // --->  Costo de construcción de líneas de MT 

            }
//        ***********************End (Cálculo Costo en Líneas de MT)**************************                      
        
            
            
            
//        ************************************************************************************            
//         >> Costos de Líneas de Baja Tensión LLVc <<
//        ************************************************************************************            
            auxLLV = new Array2DRowRealMatrix(new double[M_Lineas_paraFlujo.getRowDimension()][1]);
            auxLLV = M_Lineas_paraFlujo.getColumnMatrix(5);
            for (int zz = 0; zz < aux333.getRowDimension(); zz++) {
                auxLLV.setEntry((int) aux333.getEntry(zz, 0), 0, 0);
            }
            LLVc = Metodos.suma(auxLLV) * pLLV;  //--> Costo de construcción de líneas de BT
//        ******************End (Costos de Líneas de Baja Tensión)****************************
            
            
            
            
            
            
//        ************************************************************************************            
//         >> Suma Final, Costos Totales <<  
//        ************************************************************************************            
            double eLDTc = CPerdPo + CPerdPcu;
            neto.setEntry(i, 0, (DTc + LMVc + LLVc + eLLVc + eLDTc + eENSc) / (1e6));
            if (neto.getEntry(i, 0) == NaN) {
                neto.setEntry(i, 0, Inf);
            }
//        *************************End (Suma Final, Costos Totales)***************************            
            if (fig == 1) {
                Minimos = Metodos.minForSomeColumn(Voltajes, new double[]{0, 1, 2});
                regulacion = Minimos.getEntry(0,0);
                nodo = (int)Minimos.getEntry(1,0);
                fase = (int)Minimos.getEntry(2,0);
            }

            Mem = new Array2DRowRealMatrix(new double[contador_memoria][Chrom.getRowDimension() + 1]);
            if (contador_memoria == 1) {
                Mem.setSubMatrix(Chrom.transpose().getData(), contador_memoria - 1, 1);
                Mem.setEntry(contador_memoria - 1, 0, neto.getEntry(i, 0));
                Mem_copy = Mem.copy();
                contador_memoria++;
            } else {
                Mem.setSubMatrix(Mem_copy.getData(), 0, 0);
                Mem.setSubMatrix(Chrom.transpose().getData(), contador_memoria - 1, 1);
                Mem.setEntry(contador_memoria - 1, 0, neto.getEntry(i, 0));
                Mem_copy = Mem.copy();
                contador_memoria++;
            }

            NMem = NMem+1;
            
            
            
//        ************************************************************************************            
//         >> Restablecer Variables Nativas del Sistema, Para el siguiente Candidato <<  
//        ************************************************************************************  
            nl = nl1;
            nn = nn1;
            nc = nc1;
            Vb = Vb1;
            Vbm = Vbm1;
            MVAb = MVAb1;
            fe = fe1;
            InT = InT1;
            InF = InF1;
            Fc = Fc1;
            Fp = Fp1;
            M_Lineas = M_Lineas1.copy();
            M_Cargas = M_Cargas1.copy();
            M_Dist = M_Dist1.copy();
            M_Cond = M_Cond1.copy();
            NodosMT = NodosMT2;
//        *****End (Restablecer Variables Nativas del Sistema, Para el siguiente Candidato)*****    
           
     
        }
//     *********************************End (Fin del Bucle Principal)*****************************************    
        
        Mem1=Mem.copy();
        NMem1=NMem;
        
        
    }

    
    //Métodos get():
    public int getnl() {
        return nl;
    }

    public int getng() {
        return ng;
    }

    public int getnc() {
        return nc;
    }

    public int getnn() {
        return nn;
    }

    public RealMatrix getM_Lineas() {
        /*
        if (M_Lineas == null) {
            Evaluador();
        }
        */
        return M_Lineas;
    }
    
    public RealMatrix getVoltajes(){
        return Voltajes;
    }
    
    public RealMatrix getIL(){
        return IL;
    }
    
    public RealMatrix getCorrientes(){
        return Corrientes;
    }

    public RealMatrix getM_Cargas() {
        /*if (M_Cargas == null) {
            Evaluador();
        }*/
        return M_Cargas;
    }

    public RealMatrix getaux333() {
        /*if (aux333 == null) {
            Evaluador();
        }*/
        return aux333;
    }
    
    public RealMatrix getTDs() {
        /*f (TDs == null) {
            Evaluador();
        }*/
        return TDs;
    }
    
    public RealMatrix getneto(){
        return neto;
        
    }
    
    public RealMatrix getcromosoma(){
        return cromosoma;
    }

    public RealMatrix getMem(){
        return Mem1;
    }
    
    public int getNMem(){
        return NMem1;
    }
    
    public RealMatrix getevolmemoria(){
        return evolmemoria;
    }
    
    public RealMatrix getkVA(){
        return kVA;
        
    }
    
    public RealMatrix getkVAs(){
        return kVAs;
    }
    
    public RealMatrix getTDs_Norm(){
        return TDs_Norm;
}
    
    public double getPerd_Red(){
        return Perd_Red;
        
    }
    
    public double getPerTDs(){
        return PerdTDs;
    }
    
    public double getdistLMT(){
        double sumaDistLMT=Metodos.suma(distLMT);
        return sumaDistLMT;
    }
    
    public double getauxLLV(){
        double sumaDistLBT=Metodos.suma(auxLLV);
        return sumaDistLBT;
    }
    
    public int getnodo(){
        return nodo;
    }
    
    public int getfase(){
        return fase;
    }
    
    public double getregulacion(){
        return regulacion;
    }
    
    public double getENSkWh(){
        return ENSkWh;
    }
    
    public RealMatrix getasignacionL(){
        return asignacionL;
    }
    
    public RealMatrix getradial(){
        return radial;
    }
    
    public RealMatrix getasignacion(){
        return asignacion;
    }
    
    public RealMatrix getAsignacionLineas(){
        return AsignacionLineas;
    }
    
}
