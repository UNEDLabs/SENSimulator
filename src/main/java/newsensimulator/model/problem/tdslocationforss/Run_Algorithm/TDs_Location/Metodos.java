/*
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 |xxxxxxx|                      MÉTODOS VARIOS                        |xxxxxxx| 
 |xxxxxxx|                           2014                             |xxxxxxx|
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
 |xxxxxxx|                JMendoza - AMadrid - HVargas                |xxxxxxx|
 |xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|

*/

package newsensimulator.model.problem.tdslocationforss.Run_Algorithm.TDs_Location;

/**
 * @author Andreuw Madrid Carreño 'Escuela de Ingeniería Eléctrica'
 */

import flanagan.complex.ComplexMatrix;
import static java.lang.Math.round;
import java.util.Arrays;
import java.util.Random;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;


public class Metodos {

    private double PTotal[];
    private double QTotal[];
    private double P1[];
    private double PTotal1[];
    private double QTotal1[];
    private double PTotal11[];
    private double QTotal11[];
    private double ArgumentoRaiz[];
    private double RaizMatriz[];
    private double RaizVector1[];
    private double Traspuesta[];
    private int Row[];
    private int Row1[];
    private int Column[];
    private RealMatrix Lineas;
    private RealMatrix Posicion;
    private int NB;
    private int NL;
    private double[] n1;
    private double[] n2;
    private double[] cond1;
    private double[] lon1;
    private RealMatrix M_Pc;
    private RealMatrix M_Qc;
    private RealMatrix M_Cond;
    private double[] NCli;
    private double[] coordx;
    private double[] coordy;
    private double[] Tfalla;
    private double[] Trep;
    private double[] Tman;
    private int[][] tap;
    private int[][] Plmin;
    private int[][] Plmax;
    private int[][] sh;
    private int[][] Pgmin;
    private int[][] Pgmax;
    private int[][] Pg;
    private int[][] Qg;
    private int ng ;
    private int nl ;
    private int nc ;
    private RealMatrix M_Lineas ;
    private RealMatrix M_Cargas ;
    private RealMatrix Columna3_M_Lineas ;
    private double[] Columna4_M_Lineas ;
    private double[] Columna5_M_Lineas ;
    private double[] Columna6_M_Lineas ; 
    private double[] Columna8_M_Cargas ;
    private double[] Columna9_M_Cargas ;
    private double[] Columna10_M_Cargas;
    private double[] Columna7_M_Lineas ;
    private double[] Columna8_M_Lineas ;
    private double[] Columna9_M_Lineas ;
    private RealVector Columna2_M_Cargas;
    private RealVector Columna3_M_Cargas;
    private RealVector Columna4_M_Cargas;
    private RealVector Columna5_M_Cargas; 
    private RealVector Columna6_M_Cargas;
    private RealVector Columna7_M_Cargas;
    private RealMatrix Columna1_M_Lineas;
    private RealMatrix asignacion;
    private RealMatrix distancia;
    private static double Inf = Double.POSITIVE_INFINITY; 
    private static double NaN = Double.NaN;
    private double ind;
    private int s;
    private int d;
    private int e =1;
    private double b;
    private RealMatrix radial;
    private RealMatrix radial1;
    private double C;
    private RealMatrix p;
    private RealMatrix Minfo;
    private static RealMatrix queue;
    private static RealMatrix path;
    private static double cost;
    private static RealMatrix distanciasT;
    private static RealMatrix AuxQueue;
    private RealMatrix distanciaT1;
    private RealMatrix P;
    private int years;
    private double g;
    
    
    public void FormatoDatos() {
         
        Columna2_M_Cargas = M_Cargas.getColumnVector(1);
        Columna3_M_Cargas = M_Cargas.getColumnVector(2);
        Columna4_M_Cargas = M_Cargas.getColumnVector(3);
        Columna5_M_Cargas = M_Cargas.getColumnVector(4);
        Columna6_M_Cargas = M_Cargas.getColumnVector(5);
        Columna7_M_Cargas = M_Cargas.getColumnVector(6);
        RealVector PTotal = new ArrayRealVector(Columna2_M_Cargas.add(Columna3_M_Cargas.add(Columna4_M_Cargas)));
        RealVector QTotal = new ArrayRealVector(Columna5_M_Cargas.add(Columna6_M_Cargas.add(Columna7_M_Cargas)));
        double cte = (1 + (g) * years);
        PTotal1 = new double[PTotal.getDimension()];
        QTotal1 = new double[QTotal.getDimension()];
        //Ruitina para elevar al cuadrado cada elemento de los dos vectores:
        for (int i = 0; i < PTotal.getDimension(); i++) {
            PTotal1[i] = Math.pow(PTotal.toArray()[i], 2);
        }
        for (int i = 0; i < QTotal.getDimension(); i++) {
            QTotal1[i] = Math.pow(QTotal.toArray()[i], 2);
        }
        //Ruitina para sumar dos vectores:
        RealVector PTotal11 = new ArrayRealVector(PTotal1);
        RealVector QTotal11 = new ArrayRealVector(QTotal1);
        RealVector ArgumentoRaiz = new ArrayRealVector(PTotal11.add(QTotal11));
        RaizVector1 = new double[ArgumentoRaiz.getDimension()];
        P1 = new double[ArgumentoRaiz.getDimension()];
        //Ruitina para calcular raiz cuadrada cada elemento de un vector:
        for (int i = 0; i < ArgumentoRaiz.getDimension(); i++) {
            RaizVector1[i] = Math.sqrt(ArgumentoRaiz.toArray()[i]);
        }
        RealMatrix RaizMatriz = new Array2DRowRealMatrix(RaizVector1);
        //Rutina para calcular la traspuesta del vector:
        //RealMatrix Traspuesta = RaizMatriz.transpose();
        //Finalmente hay que multiplicarla por la cte, para obtener la potencia:
        p = new Array2DRowRealMatrix(P1);
        p = RaizMatriz.scalarMultiply(cte);
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        Row = new int[M_Lineas.getRowDimension()];
        Row1 = new int[M_Cargas.getRowDimension()];
        RealMatrix Lineas = new Array2DRowRealMatrix(new double[M_Lineas.getRowDimension()][3]);
        RealMatrix Posicion = new Array2DRowRealMatrix(new double[M_Lineas.getRowDimension()][3]);
        for (int i = 0; i < M_Lineas.getRowDimension(); i++) {
            Row[i] = i;
        }
        for (int i = 0; i < M_Cargas.getRowDimension(); i++) {
            Row1[i] = i;
        }
        int[] ColumnsL = {2, 3, 5};
        int[] ColumnsP = {0, 8, 9};
        Lineas = M_Lineas.getSubMatrix(Row, ColumnsL);    //Líneas nodo de entrada, salida y distancia, aquí asociada a la resistencia
        Posicion = M_Cargas.getSubMatrix(Row1, ColumnsP); //Posición correlativo posicion eje x y eje Y
        this.Lineas = Lineas;
        this.Posicion = Posicion;
        this.p = p;
        
    }
    
    public Metodos(RealMatrix M_Cargas,RealMatrix M_Lineas,int years, double g){
       
        this.M_Cargas = M_Cargas;
        this.M_Lineas = M_Lineas;
        this.years = years;        
        this.g = g;       
        FormatoDatos();
        
    }
    
    public RealMatrix Dijkstra(RealMatrix minfo){
        
    System.out.println("minfo" + minfo);
        
    this.Minfo = minfo;
    
    distanciasT = addScalar(new Array2DRowRealMatrix(new double[Minfo.getRowDimension()][Minfo.getRowDimension()]), Inf);
    
    for(int from=0; from<Minfo.getRowDimension(); from++){
        for(int to=from+1; to<Minfo.getRowDimension(); to++){
            
        double count = Math.min(Minfo.getRowDimension(),Minfo.getColumnDimension());
        RealMatrix parent = new Array2DRowRealMatrix(new double[(int)count][1]);
        RealMatrix loss = addScalar(new Array2DRowRealMatrix(new double[(int)count][1]),Inf);                
        RealMatrix index = isinf(Minfo.getRowMatrix((int)from),false).transpose();
        RealMatrix indexNum = findModificado(index,1).transpose();
        
        if (indexNum.getEntry(0, 0) != Inf){
            for (int i = 0 ; i < indexNum.getRowDimension(); i++){
                loss.setEntry((int)indexNum.getEntry(i, 0), 0, Minfo.getEntry((int)from, (int)indexNum.getEntry(i, 0)));
                parent.setEntry((int)indexNum.getEntry(i, 0), 0, from+1);
            }
            queue = indexNum.copy();
            
        }
        
            while (indexNum.getEntry(0, 0) != Inf) {

                double k = queue.getEntry(0, 0);
                RealMatrix distance = addScalar(Minfo.getRowMatrix((int) k), loss.getEntry((int) k, 0)).transpose();
                index = CompararMatrices(distance, "<", loss);

                if (verificarSuma(index.getColumn(0)) != 0) {
                    RealMatrix indexNum2 = findModificado(index, 1).transpose();
                    for (int i = 0; i < indexNum2.getRowDimension(); i++) {
                        loss.setEntry((int) indexNum2.getEntry(i, 0), 0, distance.getEntry((int) indexNum2.getEntry(i, 0), 0));
                        parent.setEntry((int) indexNum2.getEntry(i, 0), 0, k + 1);
                    }

                    if (queue.getRowDimension() > 1) {
                        AuxQueue = new Array2DRowRealMatrix(new double[queue.getRowDimension() + (int) findModificado(index, 1).getColumnDimension() - 1][1]);
                        AuxQueue.setSubMatrix(queue.getSubMatrix(1, queue.getRowDimension() - 1, 0, 0).getData(), 0, 0);
                        AuxQueue.setSubMatrix(findModificado(index, 1).transpose().getData(), queue.getRowDimension() - 1, 0);
                    } else {
                        AuxQueue = new Array2DRowRealMatrix(new double[(int) findModificado(index, 1).getColumnDimension()][1]);
                        AuxQueue.setSubMatrix(findModificado(index, 1).transpose().getData(), 0, 0);
                    }
                    
                    queue = AuxQueue.copy();

                } else {
                    int DimensionQueue = queue.getRowDimension();
                    if ((DimensionQueue - 1) == 0) {
                        break;
                    } else {
                        RealMatrix AuxQueue2 = new Array2DRowRealMatrix(queue.getSubMatrix(1, queue.getRowDimension() - 1, 0, 0).getData());
                        queue = AuxQueue2.copy();
                    }
                }

            }
        
            path = new Array2DRowRealMatrix(new double[parent.getRowDimension()][1]);
            int aux = 0;
            int n = to + 1;
            for (int g = parent.getRowDimension()-1; g >= 0; g--) {
                if ((eqInt(from, n - 1)) || eqInt(n - 1, -1)) { //se cambio 0 por -1
                    break;
                }
                path.setEntry(g, 0, n);//(Matriz, posicion, valor a insertar)
                System.out.println("val:" +n);
                n = ((int) parent.getEntry(n-1, 0));
                aux=g;
            }
            aux--;   
            
            if(eqInt(from,n - 1)){
                path.setEntry(aux, 0, n);//(Matriz, posicion, valor a insertar)
                
                RealMatrix aaa = new Array2DRowRealMatrix(new double[path.getColumnDimension()*path.getRowDimension()-aux][1]);
                
                for(int j = 0; j <  aaa.getColumnDimension()*aaa.getRowDimension() ; j++){                    
                    aaa.setEntry(j,0,path.getEntry(aux,0));
                    aux++;
                }
                path = aaa.copy();
            }//path{i, 1} = path{i, 1}(k:end);
                
            else {
                path = null;
            }
            cost = loss.getEntry((int) to, 0);        
            distanciasT.setEntry(to, from, cost);
            distanciasT.setEntry(from, to, cost);            
            }
        }               
        return distanciasT;    
    }
       
    public void OrdenDatos() {

        NB = ng + nc;
        NL = nl;
        n2 = Columna4_M_Lineas;
        cond1 = Columna5_M_Lineas;
        lon1 = Columna6_M_Lineas;
        M_Pc = M_Cargas.getSubMatrix(0,M_Cargas.getRowDimension()-1, 1, 3);//Se define M_Pc como la matrix compuesta de las columnas 2,3 y 4 de la matris M_Cargas
        M_Qc = M_Cargas.getSubMatrix(0,M_Cargas.getRowDimension()-1, 4, 6);//Se define M_Qc como la matrix compuesta de las columnas 5,6 y 7 de la matris M_Cargas
        NCli = Columna8_M_Cargas;
        coordx = Columna9_M_Cargas;
        coordy = Columna10_M_Cargas;
        Tfalla = Columna7_M_Lineas;
        Trep = Columna8_M_Lineas;
        Tman = Columna9_M_Lineas;

        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        //       >> Nodos Slack <<       
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        //Se define una matriz nula "tap"
        tap = new int[1][nl];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < nl; j++) {
                tap[i][j] = 0;
            }
        }
        // Se define una matriz nula "Plmin"

        Plmin = new int[1][nl];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < nl; j++) {
                Plmin[i][j] = 0;
            }
        }
        //Se define un vector unitario "Plmax"

        Plmax = new int[1][nl];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < nl; j++) {
                Plmax[i][j] = 1;
            }
        }
        //Se define la matriz nula "sh"

        sh = new int[1][NB];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < NB; j++) {
                sh[i][j] = 0;
            }
        }
        //Se define la matriz nula "Pgmin"

        Pgmin = new int[1][NB];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < NB; j++) {
                Pgmin[i][j] = 0;
            }
        }
        //Se define la matriz nula "Pgmax"

        Pgmax = new int[1][NB];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < NB; j++) {
                Pgmax[i][j] = 0;
            }
        }
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                Pgmin[i][j] = 1;
            }
        }

        //Se define la matriz nula "Pg"
        Pg = new int[1][NB];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < NB; j++) {
                Pg[i][j] = 0;
            }
        }
        //Se define la matriz nula "Qg"

        Qg = new int[1][NB];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < NB; j++) {
                Qg[i][j] = 0;
            }
        }
        /*%Subrutina que ordena los datos de ingreso, que tiene como objetivo facilitar el procesamiento de
         % los mismo en las subrutinas restantes. 
         */
    }
 
    public  static double suma(RealMatrix datos) {
        //Este método suma los elementos de un vector
        //se asume que el vector es tipo columna
        //retornando dicha suma como un "double"
        double suma = 0;
        for (int i = 0; i < datos.getRowDimension(); i++) {
            suma += datos.getEntry(i, 0);
        }
        return suma;
    }

    public  static RealMatrix addMatrix(RealMatrix matriz1, RealMatrix matriz2) {
        //Este método suma elemento a elemento los valores de 2 matrices
        //retornando una tercera matriz con la suma.
        //Dado que lo hace elemento a elemento, ambas matrices deben tener 
        //necesariamente la misma dimension.
        RealMatrix matriz3 = new Array2DRowRealMatrix(new double[matriz1.getRowDimension()][matriz1.getColumnDimension()]);
        for (int i = 0; i < matriz3.getRowDimension(); i++) {
            for (int j = 0; j < matriz3.getColumnDimension(); j++) {
                matriz3.setEntry(i, j, matriz1.getEntry(i, j) + matriz2.getEntry(i, j));
            }
        }
        return matriz3;
    }

    public static RealMatrix CorrientesLineas (RealMatrix matriz1,ComplexMatrix matriz2){
        //matriz1 = IL    &   matriz2 = Ilin
        RealMatrix Corriente = new Array2DRowRealMatrix(new double[matriz1.getRowDimension()][8]);
        RealMatrix auxiliar = new Array2DRowRealMatrix(new double[matriz2.getNrow()][3]);
        Corriente.setColumn(0,matriz1.getColumn(0));
        Corriente.setColumn(1,matriz1.getColumn(1));
        Corriente.setColumn(2,matriz1.getColumn(2));
        Corriente.setColumn(3,matriz1.getColumn(3));
        Corriente.setColumn(4,matriz1.getColumn(4));
        for(int i=0; i<3 ; i++){
            for(int j=0; j<matriz2.getNrow();j++){
                double angulo = Math.atan((matriz2.getElementCopy(j, i).getImag())/(matriz2.getElementCopy(j, i).getReal()));//matriz2.getElementCopy(j, i)
                auxiliar.setEntry(j, i, angulo);
            }
        Corriente.setColumn(5,auxiliar.getColumn(0));
        Corriente.setColumn(6,auxiliar.getColumn(1));
        Corriente.setColumn(7,auxiliar.getColumn(2));
        }
        return Corriente;
    }
    
    public static RealMatrix CorrientesLineasGrados (RealMatrix matriz1){
        RealMatrix CorrienteGrados = matriz1.copy();//Se genera copia del original
        for(int i=5 ; i<8 ; i++){
            for(int j=0 ; j<CorrienteGrados.getRowDimension();j++){
                CorrienteGrados.setEntry(j, i, CorrienteGrados.getEntry(j, i)*(57.2957795130823209));//Conversión Radianes-Grados 
            }
        }
        return CorrienteGrados;
    }
    
    public static RealMatrix VoltajesNodosRadianes(RealMatrix matriz1){
        RealMatrix Voltajes = matriz1.copy();
        for(int i=3 ; i<6 ; i++){
            for(int j=0 ; j<Voltajes.getRowDimension();j++){
                Voltajes.setEntry(j, i, Voltajes.getEntry(j, i)*(0.017453292519943));//Conversión Radianes-Grados 
            }
        }
        return Voltajes;
    }
    
    public  static RealMatrix MultiplyScalar(RealMatrix matriz1, double escalar) {
        RealMatrix matriz3 = new Array2DRowRealMatrix(new double[matriz1.getRowDimension()][matriz1.getColumnDimension()]);
        for (int i = 0; i < matriz3.getRowDimension(); i++) {
            for (int j = 0; j < matriz3.getColumnDimension(); j++) {
                matriz3.setEntry(i, j, matriz1.getEntry(i, j) * escalar);
            }
        }
        return matriz3;
    }

    public  static RealMatrix eliminarFilaDeUn2DArray(RealMatrix array, int row) {

        int UltimaFila = array.getRowDimension() - 1;

        int columnas = array.getColumnDimension();
        int filas = array.getRowDimension();
        double[][] MSF = new double[filas - 1][columnas];
        RealMatrix MatrizSinFila = new Array2DRowRealMatrix(MSF);

        if (row == UltimaFila) {
            MatrizSinFila = array.getSubMatrix(0, filas - 2, 0, columnas - 1);
        } else {
            row++;
            double[][] AUX = new double[filas - row][columnas];
            double[][] AUX2 = new double[filas - 1][columnas];

            RealMatrix aux = new Array2DRowRealMatrix(AUX);
            aux = array.getSubMatrix(row, filas - 1, 0, columnas - 1); // AUX=A(NoFila+1:end,:);

            array.setSubMatrix(aux.getData(), row - 1, 0);             //A(NoFila:end-1,:)=AUX; 

            RealMatrix aux2 = new Array2DRowRealMatrix(AUX2);
            aux2 = array.getSubMatrix(0, filas - 2, 0, columnas - 1);  //A=A(1:end-1,:);        

            MatrizSinFila = aux2;
        }
        return MatrizSinFila;
    }

    public  static RealMatrix OrdenSort(RealMatrix MatrizSinOrden) {
        RealMatrix PosMatrizConOrden = MatrizSinOrden.copy();
        for (int i = 0; i < MatrizSinOrden.getRowDimension(); i++) {

            double[] FilaSinOrden = MatrizSinOrden.getRow(i);
            double[] FilaConOrden = MatrizSinOrden.getRow(i);
            Arrays.sort(FilaConOrden);
            double[] PosPorFilas = FilaConOrden;

            for (int j = 0; j < FilaConOrden.length; j++) {
                Busqueda:
                for (int k = 0; k < FilaSinOrden.length; k++) {
                    if (FilaConOrden[j] == FilaSinOrden[k]) {
                        PosPorFilas[j] = k;
                        break Busqueda;
                    }
                }
            }
            PosMatrizConOrden.setRow(i, PosPorFilas);
        }
        return PosMatrizConOrden;
    }

    public  static double recorreObtener(RealMatrix MatrizRecorrer,int k){
        //Declarar variables:
        int Nfila = MatrizRecorrer.getRowDimension();
        int Ncol = MatrizRecorrer.getColumnDimension();
        RealMatrix MatrixExit = new Array2DRowRealMatrix(new double[Nfila][Ncol]);
        RealMatrix vector = new Array2DRowRealMatrix(new double[Nfila * Ncol][1]);

        //Generar el vector conformado de columnas de matrizRecorrer:
        int cont = 0;
        for (int j = 0; j < Ncol * Nfila; j = cont * Nfila) {
            if (cont < Ncol) {
                vector.setSubMatrix(MatrizRecorrer.getColumnMatrix(cont).getData(), j, 0);
            }
            cont++;
        }
        double out = vector.getEntry(k, 0);
        return out;
    }
    
    public  static RealMatrix eqRealMatrix(RealMatrix Matriz1,RealMatrix Matriz2){
        RealMatrix MatrizOut = new Array2DRowRealMatrix(new double[Matriz1.getRowDimension()][Matriz1.getColumnDimension()]);
        for(int i=0; i<Matriz1.getRowDimension(); i++){
            for(int j=0; j<Matriz1.getColumnDimension(); j++){
                if(Matriz1.getEntry(i, j) == Matriz2.getEntry(i, j)){
                   MatrizOut.setEntry(i, j, 1);
                }
                else{
                    MatrizOut.setEntry(i, j, 0);
                }
            }
        }
        return MatrizOut;
        
    }
    
    public  static boolean eqDouble(double num1, double num2) {
        boolean num;
        if (num1 == num2) {
            num = true;
        } else {
            num = false;
        }
        return num;
    }
    
    public  static boolean eqInt(int num1, int num2){
        boolean num;
        if (num1 == num2) {
            num = true;
        } else {
            num = false;
        }
        return num;
    }
        
    public  static RealMatrix isinf(RealMatrix Matrix, boolean caso) {
        for (int i = 0; i < Matrix.getRowDimension(); i++) {
            for (int j = 0; j < Matrix.getColumnDimension(); j++) {
                if (Matrix.getEntry(i, j) == Double.POSITIVE_INFINITY) {
                    if (caso) {
                        Matrix.setEntry(i, j, 1);
                    } else {
                        Matrix.setEntry(i, j, 0);
                    }
                }
                else{
                    if (caso) {
                        Matrix.setEntry(i, j, 0);
                    } else {
                        Matrix.setEntry(i, j, 1);
                    }                    
                }
            }
        }
        return Matrix;
    }   
    
    public  static RealMatrix findModificado(RealMatrix Matriz1,int i){
        
            Matriz1 = Matriz1.transpose();
            RealMatrix vector_Aux1 = new Array2DRowRealMatrix(new double[1]);
            vector_Aux1 = vector_Aux1.transpose();
            vector_Aux1.setEntry(0, 0, Double.POSITIVE_INFINITY); //Retorna Inf si todos los casos son false
            int cont = 0;            
            for(int j=0; j < Matriz1.getColumnDimension();j++ ){
                double auxFind = Matriz1.getEntry(0, j);
                if(auxFind == i){
                    cont++;
                    if(cont == 1){
                       vector_Aux1.setEntry(0,0, j);
                    }
                    else{ 
                      RealMatrix vector_Aux2 = new Array2DRowRealMatrix(new double[1][vector_Aux1.getColumnDimension()+1]);  
                      vector_Aux2.setSubMatrix(vector_Aux1.getData(),0,0);
                      vector_Aux2.setEntry(0,vector_Aux1.getColumnDimension(),j);
                      vector_Aux1 = vector_Aux2;
                    }   
                }
            }            
            return vector_Aux1;
    }
    
    public  static RealMatrix addScalar(RealMatrix Matriz,double escalar){
        //Este método le suma un valor fijo "escalar", a cada uno
        //de los elementos de un objeto tipo RealMatrix
        for(int i=0; i<Matriz.getRowDimension(); i++){
            for(int j=0; j<Matriz.getColumnDimension(); j++){
                Matriz.setEntry(i, j, escalar + Matriz.getEntry(i, j));
            }           
        }
        return Matriz;
    }
    
    public  static RealMatrix CompararMatrices(RealMatrix Matriz1, String caso, RealMatrix Matriz2) {
        RealMatrix Matriz3 = new Array2DRowRealMatrix(new double[Matriz1.getRowDimension()][Matriz1.getColumnDimension()]);
        if (Matriz1.getColumnDimension() == Matriz2.getColumnDimension() && Matriz1.getRowDimension() == Matriz2.getRowDimension()) {
            switch (caso) {
                case "<":
                    for (int i = 0; i < Matriz1.getRowDimension(); i++) {
                        for (int j = 0; j < Matriz1.getColumnDimension(); j++) {
                            if (Matriz1.getEntry(i, j) < Matriz2.getEntry(i, j)) {
                                Matriz3.setEntry(i, j, 1);
                            } else {
                                Matriz3.setEntry(i, j, 0);
                            }
                        }
                    }
                    break;
                case ">":
                    for (int i = 0; i < Matriz1.getRowDimension(); i++) {
                        for (int j = 0; j < Matriz1.getColumnDimension(); j++) {
                            if (Matriz1.getEntry(i, j) > Matriz2.getEntry(i, j)) {
                                Matriz3.setEntry(i, j, 1);
                            } else {
                                Matriz3.setEntry(i, j, 0);
                            }
                        }
                    }
                    break;
                case "<=":
                    for (int i = 0; i < Matriz1.getRowDimension(); i++) {
                        for (int j = 0; j < Matriz1.getColumnDimension(); j++) {
                            if (Matriz1.getEntry(i, j) <= Matriz2.getEntry(i, j)) {
                                Matriz3.setEntry(i, j, 1);
                            } else {
                                Matriz3.setEntry(i, j, 0);
                            }
                        }
                    }
                    break;
                case ">=":
                    for (int i = 0; i < Matriz1.getRowDimension(); i++) {
                        for (int j = 0; j < Matriz1.getColumnDimension(); j++) {
                            if (Matriz1.getEntry(i, j) >= Matriz2.getEntry(i, j)) {
                                Matriz3.setEntry(i, j, 1);
                            } else {
                                Matriz3.setEntry(i, j, 0);
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("No son de la misma dimension!!!");
        }
        return Matriz3;
    }

    public  static double verificarSuma(double[] vector) {
        double suma = 0;
        for (int i = 0; i < vector.length; i++) { //recorre todas las fila
            suma += vector[i];
        }
        return suma;
    }
    
    public static RealMatrix addScalarToMatrix(RealMatrix matriz1,double escalar){
        RealMatrix matrizOut = matriz1.copy();
        for(int i=0;i<matrizOut.getRowDimension();i++){
            for(int j=0; j<matrizOut.getColumnDimension();j++){
                matrizOut.setEntry(i, j,matrizOut.getEntry(i, j)+escalar);
            }
        }
        return matrizOut;
    }
    
    public  static void display(RealMatrix aa,RealMatrix a,RealMatrix b,RealMatrix c,RealMatrix d,double e,double f,double g,double h,int i,double j,double k,int l,double m){
       //*******************************************************
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("Costo Mínimo[M] : $"+aa.getEntry(0,0));
        System.out.println(" ");
        System.out.println("Posición de TDs: ");
        PrintMatrix Impresion_tdssource = new PrintMatrix(addScalarToMatrix(a,1));
        System.out.println("TDs Demanda Media Real [kVA]: ");
        PrintMatrix Impresion_kva = new PrintMatrix(b.transpose());
        System.out.println("TDs Demanda Máxima Proyectada [kVA]: ");
        PrintMatrix Impresion_kVAs = new PrintMatrix(c.transpose());
        System.out.println("TDs Comerciales [kVA]: ");
        PrintMatrix Impresion_TDs_Norm = new PrintMatrix(d.transpose());
        System.out.println("Pérdidas Red B.T : "+e+" [kW]");
        System.out.println(" ");
        System.out.println("Pérdidas TDs     : "+f+" [kW]");
        System.out.println(" ");
        System.out.println("Pérdidas Totales : "+(e+f)+" [kW]");
        System.out.println(" ");
        System.out.println("Longitud de Líneas de MT : "+(int)g+"[mts]");
        System.out.println(" ");
        System.out.println("Longitud de Líneas de BT : "+(int)h+"[mts]");
        System.out.println(" ");
        System.out.println("Tensión más Baja en Nodo "+j +"  nodo:"+l+ "  Fase: "+ i);
        System.out.println(" ");
        System.out.println("ENSkWh: "+k);
        System.out.println(" ");
        double TimeOff = System.currentTimeMillis();
        double TotalTime = TimeOff - m;
        System.out.println("Tiempo de ejecucion: "+TotalTime+"[ms]");
        //*******************************************************
        
        
    }
    
    public  static RealMatrix Best(int num){
        RealMatrix Best_out = new Array2DRowRealMatrix(new double[num][1]);
        for(int i=0; i<Best_out.getRowDimension() ;i++){
            Best_out.setEntry(i,0,NaN);}
        return Best_out;
        
    }
    
    public  static double[] VA_VAC(int num,double num1,double num2){
        double aux1=0;
        double aux2=0;
        double aux3=0;
        double [] Out_VAN_VAC = new double[3];
        
        for (int j = 0; j < num; j++) {
            aux1 = aux1 + 1 / Math.pow(1 + num2, j);
            aux2 = aux2 + Math.pow((1 + num1) / (1 + num2), j);
            aux3 = aux2 + Math.pow(Math.pow(1 + num1, 2) / (1 + num2), j);
        }
        
        Out_VAN_VAC[0]=aux1;
        Out_VAN_VAC[1]=aux2;
        Out_VAN_VAC[2]=aux3;
        return Out_VAN_VAC;
        
    }
    
    public  static RealMatrix crearMatrizDeInformacion(int num,RealMatrix matrix){
        //num = nn &  matrix = lineas
        RealMatrix Minfo1_Out = new Array2DRowRealMatrix(new double[num][num]);
        for (int i = 0; i < Minfo1_Out.getRowDimension(); i++) {
            for (int j = 0; j < Minfo1_Out.getColumnDimension(); j++) {
                Minfo1_Out.setEntry(i, j, Inf);
            }
        }
        int fl = matrix.getRowDimension();
        int cl = matrix.getColumnDimension();
        for(int i = 0; i<fl ; i++){
            Minfo1_Out.setEntry((int)matrix.getEntry(i,0)-1,(int)matrix.getEntry(i,1)-1,matrix.getEntry(i,2));
            Minfo1_Out.setEntry((int)matrix.getEntry(i,1)-1,(int)matrix.getEntry(i,0)-1,matrix.getEntry(i,2));
        }
        return Minfo1_Out;
    }
          
    public  static RealMatrix creacionCromosoma(int numeroIndividuosPoblacion, int numeroBarrasGeneradoras, int numeroCargas, double densidad) {
        
        double [][] cromosoma1 =sprandWithRound(numeroIndividuosPoblacion,numeroCargas,densidad);
       
        //SENTENCIA QUE CHEQUEA EXISTENCIA DE UNOS
        for (int i = 0; i < cromosoma1.length; i++) { //recorre todas las filas
            for (int k = 0; k < cromosoma1[i].length; k++) { //recorre todos los elementos de una fila
                if (cromosoma1[i][k] == 1) { //condicion para verificar si algun elemento de la fila es = a 1
                    break; //si encuentra un elemento = a 1, se sale
                }
                if (k == cromosoma1[i].length - 1) { //si es que no encontro ningun uno en la fila,se genera un "1" aleatorio en alguna posicion aleatoria
                    float c = new Random().nextFloat();//genera el n° aleatorio entre 0 y 1
                    int y = Math.round((cromosoma1[i].length - 1) * c); //lo lleva a intervalo 0-largo
                    cromosoma1[i][y] = 1; //asigna en la posicion aleatoria el valor "1";

                }
            }
        }
        RealMatrix cromosomaRealMatrix = new Array2DRowRealMatrix(cromosoma1);
        return cromosomaRealMatrix;

    }

    public  static double[][] sprandWithRound(int m, int n, double density) {
        //Este Método genera una matriz dispersa, con valores ceros y unos 
        //el método requiere, que se le especifique las dimensiones del
        //arreglo, ademas de la densidad en si de valores no nulos (0)
        //Retorna un arreglo (matriz dispersa) tipo double[][].
        double[] Vm = new double[m];
        double[] Vn = new double[n];
        double[][] MatrizDispersa = new double[m][n];
        for (int im = 0; im < m; im++) {
            Vm[im] = im;
        }
        for (int in = 0; in < m; in++) {
            Vn[in] = in;
        }
        double NoNulos = new Random().nextDouble();
        NoNulos = (m * n * density * 0.1 * NoNulos) + (m * n * density * 0.9);
        for (int NN = 0; NN < NoNulos; NN++) {
            double RX = new Random().nextDouble();
            int Rm = (int) ((Vm.length - 1) * RX);
            int Rn = (int) ((Vn.length - 1) * RX);
            Rm = (int) Vm[Rm];
            Rn = (int) Vn[Rn];
            RealMatrix VM = eliminarRama(new Array2DRowRealMatrix(Vm), Rm+1);
            RealMatrix VN = eliminarRama(new Array2DRowRealMatrix(Vn), Rn+1);
            MatrizDispersa[Rm][Rn] = round(new Random().nextDouble());
        }
        
        return MatrizDispersa;
    }
    
    public  static RealMatrix eliminarRama(RealMatrix variable, int fila) {
        double[] aux1 = new double[variable.getRowDimension() - 1];
        RealMatrix aux = new Array2DRowRealMatrix(aux1);
        int contador = 0;
        for (int j = 0; j < variable.getRowDimension(); j++) {
            if (j != (fila - 1)) {
                aux.setEntry(contador, 0, variable.getEntry(j, 0));
                contador++;
            }
        }
        return aux;
    }

    public  static RealMatrix multiplyElement(RealMatrix matriz1,RealMatrix matriz2){
        RealMatrix matrizOut = new Array2DRowRealMatrix(new double[matriz1.getRowDimension()][matriz1.getColumnDimension()]);
        
        for(int i=0; i<matriz1.getRowDimension();i++){
            for(int j=0; j<matriz1.getColumnDimension();j++){
                matrizOut.setEntry(i, j,matriz1.getEntry(i, j)*matriz2.getEntry(i, j));
            }
        }
        return matrizOut;
    }

    public  static int randInt(double num_a,double num_b){    
    //Este método entrega un valor aleatorio en el intervalo
    //[num_a,num_b],con una distribucion uniforme:
        Random rnd = new Random();
        int out = (int)(rnd.nextDouble()*num_b+num_a);
        while(out>num_b){
            out = (int)(rnd.nextDouble()*num_b+num_a);    
        }
        return out;

    }
    
    public  static int findDouble(RealMatrix Matriz0, double valorBuscado) {
        //Este método busca en un vector tipo columna un valor
        //en especifico, y funciona si y solo si, se sabe que
        //dicho valor efectivamente se encuentra en el vector
        //retorna la posicion en la cual se encuentra.
        Matriz0 = Matriz0.transpose();
        int valorExit = 0;
        for (int j = 0; j < Matriz0.getColumnDimension(); j++) {
            double auxFind = Matriz0.getEntry(0, j);
            if (auxFind == valorBuscado) {
                valorExit = j;
                break;
            }
        }
        return valorExit;
    }

    public  static RealMatrix findMatlab(RealMatrix Matriz0, double valorBuscado) {
        // Entrega un vector con un valor Infinity si y solo si, no encontró 
        // el numero buscado.
        // El vector de salida es tipo FILA    
        Matriz0 = Matriz0.transpose();
        RealMatrix vector_Aux1 = new Array2DRowRealMatrix(new double[1]);
        vector_Aux1.setEntry(0, 0, Double.POSITIVE_INFINITY);
        vector_Aux1 = vector_Aux1.transpose();
        int cont = 0;
        for (int j = 0; j < Matriz0.getColumnDimension(); j++) {
            double auxFind = Matriz0.getEntry(0, j);
            if (auxFind == valorBuscado) {
                cont++;
                if (cont == 1) {
                    vector_Aux1.setEntry(0, 0, j);
                } else {
                    RealMatrix vector_Aux2 = new Array2DRowRealMatrix(new double[1][vector_Aux1.getColumnDimension() + 1]);
                    vector_Aux2.setSubMatrix(vector_Aux1.getData(), 0, 0);
                    vector_Aux2.setEntry(0, vector_Aux1.getColumnDimension(), j);
                    vector_Aux1 = vector_Aux2.copy();
                }
            }
        }
        return vector_Aux1;
    }

    public  static RealMatrix findMatlabCaso(RealMatrix Matriz0, String caso, double valorBuscado) {
        //Este método tolera la entrada de vectores no matrices
        //Descripcion: Este método, busca en un vector un valor en especifico, y entregará un vector
        //en el cual se señalan las posiciones donde se cumplio la condicion de entrada, ya sea si
        //se buscaban los valores '=' o '<' o '>' o '<=' o '>='
        Matriz0 = Matriz0.transpose();
        RealMatrix vector_Aux1 = new Array2DRowRealMatrix(new double[1]);
        vector_Aux1.setEntry(0, 0, Double.POSITIVE_INFINITY);
        vector_Aux1 = vector_Aux1.transpose();
        int cont = 0;
        switch (caso) {

            case "=":
                for (int j = 0; j < Matriz0.getColumnDimension(); j++) {
                    double auxFind = Matriz0.getEntry(0, j);
                    if (auxFind == valorBuscado) {
                        cont++;
                        if (cont == 1) {
                            vector_Aux1.setEntry(0, 0, j);
                        } else {
                            RealMatrix vector_Aux2 = new Array2DRowRealMatrix(new double[1][vector_Aux1.getColumnDimension() + 1]);
                            vector_Aux2.setSubMatrix(vector_Aux1.getData(), 0, 0);
                            vector_Aux2.setEntry(0, vector_Aux1.getColumnDimension(), j);
                            vector_Aux1 = vector_Aux2.copy();
                        }
                    }
                }
                break;

            case ">":
                for (int j = 0; j < Matriz0.getColumnDimension(); j++) {
                    double auxFind = Matriz0.getEntry(0, j);
                    if (auxFind > valorBuscado) {
                        cont++;
                        if (cont == 1) {
                            vector_Aux1.setEntry(0, 0, j);
                        } else {
                            RealMatrix vector_Aux2 = new Array2DRowRealMatrix(new double[1][vector_Aux1.getColumnDimension() + 1]);
                            vector_Aux2.setSubMatrix(vector_Aux1.getData(), 0, 0);
                            vector_Aux2.setEntry(0, vector_Aux1.getColumnDimension(), j);
                            vector_Aux1 = vector_Aux2.copy();
                        }
                    }
                }
                break;

            case "<":
                for (int j = 0; j < Matriz0.getColumnDimension(); j++) {
                    double auxFind = Matriz0.getEntry(0, j);
                    if (auxFind < valorBuscado) {
                        cont++;
                        if (cont == 1) {
                            vector_Aux1.setEntry(0, 0, j);
                        } else {
                            RealMatrix vector_Aux2 = new Array2DRowRealMatrix(new double[1][vector_Aux1.getColumnDimension() + 1]);
                            vector_Aux2.setSubMatrix(vector_Aux1.getData(), 0, 0);
                            vector_Aux2.setEntry(0, vector_Aux1.getColumnDimension(), j);
                            vector_Aux1 = vector_Aux2.copy();
                        }
                    }
                }
                break;

            case ">=":
                for (int j = 0; j < Matriz0.getColumnDimension(); j++) {
                    double auxFind = Matriz0.getEntry(0, j);
                    if (auxFind >= valorBuscado) {
                        cont++;
                        if (cont == 1) {
                            vector_Aux1.setEntry(0, 0, j);
                        } else {
                            RealMatrix vector_Aux2 = new Array2DRowRealMatrix(new double[1][vector_Aux1.getColumnDimension() + 1]);
                            vector_Aux2.setSubMatrix(vector_Aux1.getData(), 0, 0);
                            vector_Aux2.setEntry(0, vector_Aux1.getColumnDimension(), j);
                            vector_Aux1 = vector_Aux2.copy();
                        }
                    }
                }
                break;

            case "<=":
                for (int j = 0; j < Matriz0.getColumnDimension(); j++) {
                    double auxFind = Matriz0.getEntry(0, j);
                    if (auxFind <= valorBuscado) {
                        cont++;
                        if (cont == 1) {
                            vector_Aux1.setEntry(0, 0, j);
                        } else {
                            RealMatrix vector_Aux2 = new Array2DRowRealMatrix(new double[1][vector_Aux1.getColumnDimension() + 1]);
                            vector_Aux2.setSubMatrix(vector_Aux1.getData(), 0, 0);
                            vector_Aux2.setEntry(0, vector_Aux1.getColumnDimension(), j);
                            vector_Aux1 = vector_Aux2.copy();
                        }
                    }
                }
                break;
        }

        return vector_Aux1;

    }

    public  static boolean isempty(RealMatrix matriz1) {
        //El método permite que entre un vector tipo columna
        boolean caso;
        if (matriz1.getRowDimension() >= 1) {
            caso = false;
        } else {
            caso = true;
        }
        return caso;
    }

    public  static RealMatrix find(RealMatrix Matriz1) {

        Matriz1 = Matriz1.transpose();
        RealMatrix vector_Aux1 = new Array2DRowRealMatrix(new double[1]);
        vector_Aux1 = vector_Aux1.transpose();
        int cont = 0;
        for (int j = 0; j < Matriz1.getColumnDimension(); j++) {
            double auxFind = Matriz1.getEntry(0, j);
            if (auxFind == 1) {
                cont++;
                if (cont == 1) {
                    vector_Aux1.setEntry(0, 0, j);
                } else {
                    RealMatrix vector_Aux2 = new Array2DRowRealMatrix(new double[1][vector_Aux1.getColumnDimension() + 1]);
                    vector_Aux2.setSubMatrix(vector_Aux1.getData(), 0, 0);
                    vector_Aux2.setEntry(0, vector_Aux1.getColumnDimension(), j);
                    vector_Aux1 = vector_Aux2.copy();
                }
            }
        }
        return vector_Aux1;
    }

    public  static double[] posicionVectorFila(RealMatrix vector1, int iteracion) {
        double vector[] = vector1.getRow(0);
        double vector2[] = new double[vector.length];
        for (int d = 0; d < vector2.length; d++) {
            vector2[d] = 0;
        }
        int valor = 0;
        for (int posicion = 0; posicion < vector.length; posicion++) {
            if (vector[posicion] == iteracion) {
                vector2[valor] = posicion;
                valor++;
            }
        }
        return vector2;
    }

    public  static RealMatrix suprimirFilasNulas(RealMatrix a) {
        int FilaNula = 0;
        RealMatrix matrizAux111 = new Array2DRowRealMatrix(a.getData());
        matrizAux111 = a;
        NoMasFilasNulas:
        while (true) {
            BuscarFilaNula:
            for (int fila = 0; fila < matrizAux111.getRowDimension(); fila++) {
                double[] Vector = matrizAux111.getRow(fila);
                double SumaVector = verificarSuma(Vector);//Metodo que sume todos los elementos de un vector                
                if (SumaVector == 0) {
                    FilaNula = fila;
                    break BuscarFilaNula;
                } else if (fila == matrizAux111.getRowDimension() - 1) {
                    break NoMasFilasNulas;
                }
            }
            RealMatrix asdf = eliminarFilaDeUn2DArray(matrizAux111, FilaNula);
            matrizAux111 = asdf;
        }
        return matrizAux111;
    }

    public  static RealMatrix isNaN(RealMatrix matriz) {

        double NaN = Double.NaN;
        RealMatrix matriz1 = new Array2DRowRealMatrix(new double[matriz.getRowDimension()][matriz.getColumnDimension()]);
        RealMatrix MatrixOut = new Array2DRowRealMatrix(new double[matriz.getRowDimension()][matriz.getColumnDimension()]);
        matriz1 = addScalar(matriz1, NaN);
        MatrixOut = eqRealMatrix(matriz, matriz1);
        return MatrixOut;

    }

    public  static RealMatrix OrdenSortMatlab2(RealMatrix MatrizSinOrden) {
        //  Equivalente Matlab:
        // [n,PosMatrizConOrden]=sort(MatrizSinOrden,2);

        RealMatrix PosMatrizConOrden = MatrizSinOrden.copy();
        for (int i = 0; i < MatrizSinOrden.getRowDimension(); i++) {

            double[] FilaSinOrden = MatrizSinOrden.getRow(i);
            double[] FilaConOrden = MatrizSinOrden.getRow(i);
            Arrays.sort(FilaConOrden);
            double[] PosPorFilas = FilaConOrden;

            for (int j = 0; j < FilaConOrden.length; j++) {
                Busqueda:
                for (int k = 0; k < FilaSinOrden.length; k++) {
                    if (FilaConOrden[j] == FilaSinOrden[k]) {
                        PosPorFilas[j] = k;
                        break Busqueda;
                    }
                }
            }
            PosMatrizConOrden.setRow(i, PosPorFilas);
        }
        double[] fila_iesima = new double[PosMatrizConOrden.getColumnDimension()];
        for (int i = 0; i < PosMatrizConOrden.getRowDimension(); i++) {
            fila_iesima = PosMatrizConOrden.getRow(i);
            for (int j = 0; j < PosMatrizConOrden.getColumnDimension(); j++) {
                double a = fila_iesima[j];
                if (j < PosMatrizConOrden.getColumnDimension() - 1) {
                    for (int k = j + 1; k < PosMatrizConOrden.getColumnDimension(); k++) {
                        if (a == fila_iesima[k]) {
                            fila_iesima[k] = fila_iesima[k] + 1;
                        }

                    }

                }
            }
            PosMatrizConOrden.setRow(i, fila_iesima);
        }
        return PosMatrizConOrden;
    }

    public  static RealMatrix findModificado2(RealMatrix Matriz1, int i) {

        Matriz1 = Matriz1.transpose();
        RealMatrix vector_Aux1 = new Array2DRowRealMatrix(new double[1]);
        vector_Aux1 = vector_Aux1.transpose();
        vector_Aux1.setEntry(0, 0, Double.POSITIVE_INFINITY); //Retorna Inf si todos los casos son false
        int cont = 0;
        for (int j = 0; j < Matriz1.getColumnDimension(); j++) {
            double auxFind = Matriz1.getEntry(0, j);
            if (auxFind == i) {
                cont++;
                if (cont == 1) {
                    vector_Aux1.setEntry(0, 0, j);
                } else {
                    RealMatrix vector_Aux2 = new Array2DRowRealMatrix(new double[1][vector_Aux1.getColumnDimension() + 1]);
                    vector_Aux2.setSubMatrix(vector_Aux1.getData(), 0, 0);
                    vector_Aux2.setEntry(0, vector_Aux1.getColumnDimension(), j);
                    vector_Aux1 = vector_Aux2;
                }
            }
        }
        return vector_Aux1;
    }

    public  static RealMatrix vectorIncrementalFila(RealMatrix vector, int numElement) {
        //El vector de entrada debe ser tipo fila
        //retorna un vector tipo RealMatrix
        vector = vector.transpose();
        RealMatrix vector1 = vector.copy();
        for (int jk = 0; jk < numElement; jk++) {
            vector1.setEntry(0, jk, jk);
        }
        return vector1;
    }

    public  static RealMatrix vectorIncrementalColumnaMod(int numElement,int star) {
        //Genera un vector tipo columna incremental, comienza en el valor star
        //su tasa de incremento es "1", y tendra tantos elementos como el 
        //usuario especifique; Retorna un vector tipo RealMatrix
        //numElement:numero de elementos del vector
        //star: comenzar a incrementar a partir del numero "star"
        RealMatrix vector1 = new Array2DRowRealMatrix(new double[numElement]);
        int contador = star;
        for (int jk = 0; jk < numElement; jk++) {
            vector1.setEntry(jk, 0, star);
            contador++;
        }
        return vector1;
    }

    public  static RealMatrix minComplete(RealMatrix matriz) {
        //Este método tolera el ingreso de una Matriz 
        //ademas debe ser un objeto RealMatrix()
        //Entrega como retorno una Matriz de 2 columnas y tantas filas
        //como lumnas tenga la matriz de ingreso, en la primera columna
        //estará el menor valor de la columna, mientras que en la segunda columna
        //estará la posicion de dicho valor en el vector columna de la matriz
        //Ingreso:
        RealMatrix matriz_salida = new Array2DRowRealMatrix(new double[matriz.getColumnDimension()][2]);
        for (int Ncol = 0; Ncol < matriz.getColumnDimension(); Ncol++) {
            double[] VectorOrden_dMT = matriz.getColumn(Ncol);
            Arrays.sort(VectorOrden_dMT);
            matriz_salida.setEntry(Ncol, 1, findDouble(matriz.getColumnMatrix(Ncol), VectorOrden_dMT[0]));
            matriz_salida.setEntry(Ncol, 0, VectorOrden_dMT[0]);
        }
        return matriz_salida;
    }

    public  static RealMatrix minForSomeColumn(RealMatrix matriz, double[] vectorDeCol) {
        //Este método tolera el ingreso de una Matriz, que sea un Objeto RealMatrix()
        //además, necesita el ingreso de un vector tipo "double[]", en el cual se le ingresaran
        //las filas que se desean analizar (sacar el minimo valor y su posicion correspondiente)
        //se comporta de la misma forma que el método "minComplete"
        //Ingreso:
        RealMatrix matriz_salida = new Array2DRowRealMatrix(new double[vectorDeCol.length][3]);
        for (int Ncol = 0; Ncol < vectorDeCol.length; Ncol++) {
            int col = (int) vectorDeCol[Ncol];
            double[] VectorOrden_dMT = matriz.getColumn(col);
            Arrays.sort(VectorOrden_dMT);
            matriz_salida.setEntry(Ncol, 0, VectorOrden_dMT[0]);
            matriz_salida.setEntry(Ncol, 1, findDouble(matriz.getColumnMatrix(col), VectorOrden_dMT[0])+1);
            matriz_salida.setEntry(Ncol, 2,Ncol+1);
        }
        double [] aux_Volt = matriz_salida.getColumn(0);
        Arrays.sort(aux_Volt);
        int nodo_aux = findDouble(matriz_salida.getColumnMatrix(0),aux_Volt[0]);
        RealMatrix vector_Regulacion = new Array2DRowRealMatrix(new double[3][1]);
        vector_Regulacion.setEntry(0,0,matriz_salida.getEntry(nodo_aux, 0));
        vector_Regulacion.setEntry(1,0,matriz_salida.getEntry(nodo_aux, 1));
        vector_Regulacion.setEntry(2,0,matriz_salida.getEntry(nodo_aux, 2));
        return vector_Regulacion;
    }

    public  static RealMatrix recorreColumna(RealMatrix MatrizRecorrer, int k, double escalar) {
        //Este método recorre una matriz como si fuese un vector, tal como lo 
        //hace Matlab, cuando uno programa expresiones como esta:
        //Matriz(k)=0;, busca el elemento k-esimo de la matriz y 
        //le otorga el valor nulo.
        //Este método recorre columna por columna
        //Una vez que recorre la primera columna y llega al último elemento
        //sigue contando, pero en el primer elemento de la segunda 
        //columna.
        //Como ingreso este método requiere, la matriz que se desea recorrer
        //pero que esta sea un objeto RealMatrix, ademas de la posicion que se busca
        //y el valor que se desea colocar en dicha posicion.
        //El método, retorna la misma matriz pero con el elemento indicado
        //cambiado por el valor que el usuario señaló:

        //Declarar variables:
        int Nfila = MatrizRecorrer.getRowDimension();
        int Ncol = MatrizRecorrer.getColumnDimension();
        RealMatrix MatrixExit = new Array2DRowRealMatrix(new double[Nfila][Ncol]);
        RealMatrix vector = new Array2DRowRealMatrix(new double[Nfila * Ncol][1]);

        //Generar el vector conformado de columnas de matrizRecorrer:
        int cont = 0;
        for (int j = 0; j < Ncol * Nfila; j = cont * Nfila) {
            if (cont < Ncol) {
                vector.setSubMatrix(MatrizRecorrer.getColumnMatrix(cont).getData(), j, 0);
            }
            cont++;
        }

        //ingresar el "escalar" en la posicion "k" deseada:
        vector.setEntry(k, 0, escalar);

        //volver a configurar la matriz:
        int conteo = 0;
        for (int i = 0; i < Ncol; i++) {
            RealMatrix bottle = new Array2DRowRealMatrix(new double[Nfila]);
            for (int j = 0; j < Nfila; j++) {
                bottle.setEntry(j, 0, vector.getEntry(conteo, 0));
                conteo++;
            }
            MatrixExit.setColumn(i, bottle.getColumn(0));
        }
        return MatrixExit;
    }
    
    public  static RealMatrix convMatrixToVectorCol(RealMatrix MatrizRecorrer) {
        //Este método convierte una Matriz (objeto RealMatrix), en un vector
        //tipo columna, acoplando las columnas de la matriz en una sola columna
        //Ej: sea la matriz = |a b|
        //                    |c d|
        //vector de salida será: vector=[a b c d], obviamente traspuesto,
        //dado que la salida es un vector columna, y además es un objeto tipo RealMatrix.

        //Declarar variables:
        int Nfila = MatrizRecorrer.getRowDimension();
        int Ncol = MatrizRecorrer.getColumnDimension();
        RealMatrix MatrixExit = new Array2DRowRealMatrix(new double[Nfila][Ncol]);
        RealMatrix vector = new Array2DRowRealMatrix(new double[Nfila * Ncol][1]);

        //Generar el vector conformado de columnas de matrizRecorrer:
        int cont = 0;
        for (int j = 0; j < Ncol * Nfila; j = cont * Nfila) {
            if (cont < Ncol) {
                vector.setSubMatrix(MatrizRecorrer.getColumnMatrix(cont).getData(), j, 0);
            }
            cont++;
        }
        return vector;
    }
       
    public  static RealMatrix unosEnImpar(int Nelement) {
    // Este método genera un vector tipo columna de largo
    // "Nelement", en el cual coloca un "1" en donde la posicion 
    // es Impar, y en las pares, coloca un "0", retornando un 
    // Objeto tipo RealMatrix de unos y ceros:    
        RealMatrix vector = new Array2DRowRealMatrix(new double[Nelement]);
        for (int i = 0; i < vector.getRowDimension(); i++) {
            if (i % 2 == 0) {
                vector.setEntry(i, 0, 0);
            } else {
                vector.setEntry(i, 0, 1);
            }
        }
        return vector;
    }

    public  static RealMatrix unosEnPar(int Nelement) {
    // Este método genera un vector tipo columna de largo
    // "Nelement", en el cual coloca un "0" en donde la posicion 
    // es Impar, y en las pares, coloca un "1", retornando un 
    // Objeto tipo RealMatrix de unos y ceros:    
        RealMatrix vector = new Array2DRowRealMatrix(new double[Nelement]);
        for (int i = 0; i < vector.getRowDimension(); i++) {
            if (i % 2 == 0) {
                vector.setEntry(i, 0, 1);
            } else {
                vector.setEntry(i, 0, 0);
            }
        }
        return vector;
    }
    
    public  static RealMatrix newChrom(RealMatrix matriz) {
        //Este método asegura la existencia de al menos un TDs:
        //Si no existe ningun TDs en el cromosoma(hijo), se le colocaran
        //a lo mas dos "1" y a lo menos un "1" aleatoriamente, con una 
        //distribucion uniforme respecto a la eleccion de la posicion
        //de dicho(os) TDs a colocar.
        int f = matriz.getRowDimension();
        int c = matriz.getColumnDimension();
        int aux;
        int aux2;
        if(suma(matriz)==0){
            aux2=randInt(0, matriz.getRowDimension()-1);
            aux=randInt(0, matriz.getRowDimension()-1);
            matriz.setEntry(aux2,0,1);
            matriz.setEntry(aux,0,1);
        }
        return matriz;

    }
    
    public  static RealMatrix vectorIncrementalColumna(RealMatrix vector, int numElement) {
        //El vector de entrada debe ser tipo columna
        //retorna un vector tipo RealMatrix
        RealMatrix vector1 = vector.copy();
        for (int jk = 0; jk < numElement; jk++) {
            vector1.setEntry(jk, 0, jk);
        }
        return vector1;
    }

    public  static RealMatrix maxComplete(RealMatrix matriz) {
        //Este método tolera el ingreso de una Matriz 
        //ademas debe ser un objeto RealMatrix()
        //Entrega como retorno una Matriz de 2 columnas y tantas filas
        //como columnas tenga la matriz de ingreso, en la primera columna
        //estará el menor valor de la columna, mientras que en la segunda columna
        //estará la posicion de dicho valor en el vector columna de la matriz de
        //Ingreso:
        RealMatrix matriz_salida = new Array2DRowRealMatrix(new double[matriz.getColumnDimension()][2]);
        for (int Ncol = 0; Ncol < matriz.getColumnDimension(); Ncol++) {
            double[] VectorOrden_dMT = matriz.getColumn(Ncol);
            Arrays.sort(VectorOrden_dMT);
            VectorOrden_dMT = invPosVector(VectorOrden_dMT);
            matriz_salida.setEntry(Ncol, 1, findDouble(matriz.getColumnMatrix(Ncol), VectorOrden_dMT[0]));
            matriz_salida.setEntry(Ncol, 0, VectorOrden_dMT[0]);
        }
        return matriz_salida;
    }
    
    public  static double[]  invPosVector(double[] vectSort){
        double [] aux2 = new double[vectSort.length];
        int cont=0;
        for(int i=vectSort.length-1;i>=0;i--){
            aux2[cont]= vectSort[i];
            cont++;
        }
        
       return aux2;
        
    }
    
    public  static double[][] sprand(int m,int n,double density){
        //Este Método genera una matriz dispersa, con valores entre 0 y 1
        //el método requiere, que se le especifique las dimensiones del
        //arreglo, ademas de la densidad en si de valores no nulos (0)
        //Retorna un arreglo (matriz dispersa) tipo double[][].
        double[] Vm = new double[m];
        double[] Vn = new double[n];
        double[][] MatrizDispersa = new double[m][n];
        for(int im=0; im<m; im++){
            Vm[im] = im;
        }
        for(int in=0; in<m; in++){
            Vn[in] = in;
        }
        double NoNulos = new Random().nextDouble();
        NoNulos = (m*n*density*0.1*NoNulos)+(m*n*density*0.9);
        for (int NN = 0;  NN<NoNulos; NN++){
            double RX = new Random().nextDouble();
            int Rm =  (int)((Vm.length-1)*RX);
            int Rn = (int)((Vn.length-1)*RX);
            Rm = (int)Vm[Rm];            
            Rn = (int)Vn[Rn];
            RealMatrix VM = eliminarRama(new Array2DRowRealMatrix(Vm), Rm);
            RealMatrix VN = eliminarRama(new Array2DRowRealMatrix(Vn), Rn);
           // Vm = VM.getColumn(0);
            //Vn = VN.getColumn(0);
            MatrizDispersa[Rm][Rn] = new Random().nextDouble();            
        }
        return MatrizDispersa;        
    }
    
    public  static RealMatrix Ranking(RealMatrix matrizNeto) {
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
        double suma = suma(vector1);
        RealMatrix vector2 = new Array2DRowRealMatrix(new double[vector1.getRowDimension()][vector1.getColumnDimension()]);
        vector2 = addScalar(vector2, suma);
        vector1 = vector1.scalarMultiply(-1);
        RealMatrix vector3 = addMatrix(vector2, vector1);
        double suma2 = 1 / suma(vector3);
        RealMatrix vectorOut = MultiplyScalar(vector3, suma2);
        
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
        double uma = suma(vectorOut.getColumnMatrix(0));//uma=1, implica que la suma de los pesos es correcto
        return vectorOut;
    }
    
    public  static RealMatrix paraPrint(RealMatrix max){
        double [] a = max.getColumn(0);
        Arrays.sort(a);
        int pos=0;
        for(int i=0;i<a.length;i++){
            double b=a[0];
            if(b==  max.getColumn(0)[i]){
                pos=i;
            }
        }
        RealMatrix crom = max.getRowMatrix(pos);
        return crom;
    }   
    
    public double [] lineasAbiertas(RealMatrix radial1,RealMatrix M_Lineas1){

        RealMatrix colNumLineas = vectorIncrementalFila(new Array2DRowRealMatrix(new double[M_Lineas1.getRowDimension()][1]), M_Lineas1.getRowDimension()).transpose();
        colNumLineas = addScalarToMatrix(colNumLineas, 1);
        RealMatrix lineas = new Array2DRowRealMatrix(new double[M_Lineas1.getRowDimension()][3]);
        lineas.setColumn(0, colNumLineas.getColumn(0));
        lineas.setColumn(1, M_Lineas1.getColumn(2));
        lineas.setColumn(2, M_Lineas1.getColumn(3));
        RealMatrix lineasRadial = new Array2DRowRealMatrix(new double[radial1.getRowDimension()][2]);
        lineasRadial.setColumn(0, addScalarToMatrix(radial1.getColumnMatrix(0), 1).getColumn(0));
        lineasRadial.setColumn(1, addScalarToMatrix(radial1.getColumnMatrix(1), 1).getColumn(0));
        int cont = 0;
        double[] LineasAbiertas = new double[lineas.getRowDimension() - lineasRadial.getRowDimension()];//retorno!
        double[] LineasCerradas = new double[lineasRadial.getRowDimension()];//Líneas que están cerradas
        int contador = 0;
        int contador1 = 0;
        for (int i = 0; i < lineasRadial.getRowDimension(); i++) {
            double[] fila = lineasRadial.getRow(i);
            int an1 = (int) fila[0];
            int an2 = (int) fila[1];
            for (int j = 0; j < lineas.getRowDimension(); j++) {
                double[] fila1 = lineas.getRow(j);
                int bn1 = (int) fila1[1];
                int bn2 = (int) fila1[2];
                if ((((an1 == bn1) && (an2 == bn2)) || ((an1 == bn2) && (an2 == bn1)))) {
                    LineasCerradas[contador] = lineas.getEntry(j, 0);
                    contador++;
                    break;
                }
            }
        }
        //findMatlabCaso(RealMatrix Matriz0, String caso, double valorBuscado)
        Arrays.sort(LineasCerradas);//Ordenar líneas cerradas para comparar
        RealMatrix LineasCerradas1 = new Array2DRowRealMatrix(LineasCerradas);
        RealMatrix LineasTotales = lineas.getColumnMatrix(0);//Todas las líneas iniciales
        int contador2 = 0;
        for (int i = 0; i < LineasTotales.getRowDimension(); i++) {
            int num = (int) findMatlab(LineasCerradas1, LineasTotales.getEntry(i, 0)).getEntry(0, 0) + 1;
            contador2++;
            if (num >= 2 * 1e9 || num <= -2 * 1e9) {
                LineasAbiertas[contador1] = contador2;
                contador1++;
            }
        }
        return LineasAbiertas;
    }
    
    
    public double [] lineasCerradas(RealMatrix radial1,RealMatrix M_Lineas1){

        RealMatrix colNumLineas = vectorIncrementalFila(new Array2DRowRealMatrix(new double[M_Lineas1.getRowDimension()][1]), M_Lineas1.getRowDimension()).transpose();
        colNumLineas = addScalarToMatrix(colNumLineas, 1);
        RealMatrix lineas = new Array2DRowRealMatrix(new double[M_Lineas1.getRowDimension()][3]);
        lineas.setColumn(0, colNumLineas.getColumn(0));
        lineas.setColumn(1, M_Lineas1.getColumn(2));
        lineas.setColumn(2, M_Lineas1.getColumn(3));
        RealMatrix lineasRadial = new Array2DRowRealMatrix(new double[radial1.getRowDimension()][2]);
        lineasRadial.setColumn(0, addScalarToMatrix(radial1.getColumnMatrix(0), 1).getColumn(0));
        lineasRadial.setColumn(1, addScalarToMatrix(radial1.getColumnMatrix(1), 1).getColumn(0));
        int cont = 0;
        double[] LineasAbiertas = new double[lineas.getRowDimension() - lineasRadial.getRowDimension()];//retorno!
        double[] LineasCerradas = new double[lineasRadial.getRowDimension()];//Líneas que están cerradas
        int contador = 0;
        int contador1 = 0;
        for (int i = 0; i < lineasRadial.getRowDimension(); i++) {
            double[] fila = lineasRadial.getRow(i);
            int an1 = (int) fila[0];
            int an2 = (int) fila[1];
            for (int j = 0; j < lineas.getRowDimension(); j++) {
                double[] fila1 = lineas.getRow(j);
                int bn1 = (int) fila1[1];
                int bn2 = (int) fila1[2];
                if ((((an1 == bn1) && (an2 == bn2)) || ((an1 == bn2) && (an2 == bn1)))) {
                    LineasCerradas[contador] = lineas.getEntry(j, 0);
                    contador++;
                    break;
                }
            }
        }
        
        Arrays.sort(LineasCerradas);//Ordenar líneas cerradas para comparar
        return LineasCerradas;
    }
    

    
    
    //MÉTODOS GET():
    public RealMatrix getradial() {
 
        return radial;
    } 
    
    public double getC() {
 
        return C;
    }
    
    public RealMatrix getasignacion() {
 
        return asignacion;
    }
    
    public RealMatrix getdistancia() {
 
        return distancia;
    }
    
    public RealMatrix getLineas() {
       
        return Lineas;
    }

    public RealMatrix getdistanciasT() {
        return distanciasT;
    }
     
    public RealMatrix getPosicion() {

        return Posicion;
    }

    public RealMatrix getp() {

        return p;
    }

    public int getNB() {
        return NB;
    }

    public int getNL() {
        return NL;
    }

    public double[] getn1() {

        return n1;
    }

    public double[] getn2() {
 
        return n2;
    }

    public double[] getcond1() {
  
        return cond1;
    }

    public double[] getlon1() {
    
        return lon1;
    }

    public RealMatrix getM_Pc() {
     
        return M_Pc;
    }

    public RealMatrix getM_Qc() {
    
        return M_Qc;
    }

    public double[] getNCli() {
     
        return NCli;
    }

    public double[] getcoordx() {
    
        return coordx;
    }

    public double[] getcoordy() {
       
        return coordy;
    }

    public double[] getTfalla() {
      
        return Tfalla;
    }

    public double[] getTrep() {
       
        return Trep;
    }

    public double[] getTman() {
      
        return Tman;
    }

    public int[][] gettap() {
      
        return tap;
    }

    public int[][] getPlmin() {
      
        return Plmin;
    }

    public int[][] getPlmax() {
   
        return Plmax;
    }

    public int[][] getsh() {
    
        return sh;
    }

    public int[][] getPgmin() {
      
        return Pgmin;
    }

    public int[][] getPgmax() {
    
        return Pgmax;
    }

    public int[][] getPg() {
        
        return Pg;
    }

    public int[][] getQg() {

        return Qg;
    }

    public RealMatrix getP() {

        return p;
    }

       
}
