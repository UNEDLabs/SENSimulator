/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.problem.powerflow;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import newsensimulator.model.ElectricalNetwork;
import org.apache.commons.math3.complex.Complex;

public class PowerFlow {
    //VARIABLES DE LINEAS ORIGINALES (TODAS LAS LINEAS DE LA RED)

    private double[] lineNumberOr;
    private double[] nlOr;
    private double[] nrOr;
    private double[] rOr;
    private double[] xOr;
    private double[] bcOr;
    private double[] aOr;
    private double[] iMaxOr;
    //VARIABLES DE LINEAS (CONFIGURACION RADIAL)
    private double[] lineNumber;
    private double[] nl;
    private double[] nr;
    private double[] r;
    private double[] x;
    private double[] bc;
    private double[] a;
    private Complex[] Bc;
    private double[] iMax;
    private Complex[] currentLine;
    private double[] magCurrent;
    private double[] loadability;
    //VARIABLES ASOCIADAS A LAS BARRAS O BUSES DEL SISTEMA
    private double[] numBus;
    private double[] codBus;
    private double[] magVoltBus;
    private double[] angleBus;
    private double[] loadMW;
    private double[] loadMVar;
    private double[] genMWBus;
    private double[] gendMvarBus;
    private double[] genQMinBus;
    private double[] genQMaxBus;
    private double[] staticMvar;
    //VARIABLES, COMO MATRIZ, ASOCIADA A LAS BARRAS
    private double[][] dataLine;
    private double[][] dataBus;
    private int[] openLines;
    //VARIABLES PARA CALCULO DEL TIEMPO DE EJECUCION
    private double tiempoInicio;
    private double tiempoTermino;
    private double tiempoTotal = 0;
    //VARIABLES AUXILIARES Y COMUNES PARA TODO EL ARCHIVO
    private int h;
    private int i = 0;
    private int j = 0;
    private int n = 0;
    private Complex unoC;
    private Complex complejoAux;
    private Complex complejoAux2;
    //VARIABLES PRIMER ARCHIVO
    private int basemva = 100;
    private double accuracy = 0.001;
    private double accel = 1.8;
    private int maxIter = 150;
    private int nbr;
    private double nbus;
    private Complex[][] YbusC;
    private Complex[] Z;
    private Complex[] y;
    //VARIABLES SEGUNDO ARCHIVO
    private double[] kb;
    private double[] Vm;
    private double[] delta;
    private double[] pd;
    private double[] qd;
    private double[] pg;
    private double[] qg;
    private double[] qMin;
    private double[] qMax;
    private double[] Qsh;
    private double[] DV;
    private double[] P;
    private double[] Q;
    private double[] DP;
    private double[] DQ;
    private double[] deltaD;
    private double[] mLine;
    private Complex[] V;
    private Complex[] S;
    private Complex[] yLoad;
    private Complex YV;
    private Complex Sc;
    private int num = 0;
    private int acurBus = 0;
    private int converge = 1;
    private int iter = 0;
    private double maxError = 0;
    private double pgt = 0;
    private double qgt = 0;
    private double pdt = 0;
    private double qdt = 0;
    private double Qsht = 0;
    private Complex[] Vc;
    //VARIABLES TERCER ARCHIVO
    private Complex SLT;
    private Complex In;
    private Complex Ik;
    private Complex Snk;
    private Complex Skn;
    private Complex SL;
    private DecimalFormat formatoDecimal;

    /*
     * private static PowerFlow loadFlowClass;
     *
     * public static PowerFlow getLoadFlowClass(){ if (loadFlowClass == null) {
     * loadFlowClass = new PowerFlow(); } return loadFlowClass; }
     *
     */
    //----------------------------------------------------------------------------------------------------------------------------//
    //                                     TERMINOLOGIA EN BARRAS PARA EJECUCION FLUJO DE POTENCIA                                //
    //                    --------------------------------------------------------------------------------------------------------//
    //TERMINOS:           Bus      Bus        Voltage      Angle       ---Load----       ------Generator------       Static Mvar  //
    //                    N°       code         Mag.       Degree       MW    Mvar        MW  Mvar   Qmin Qmax         +Qc/-Ql    //
    //                                                                                                                            //
    //TIPO DATO          (int)     (int)      (double)    (double)    ( double x 2 )     (     double x 4     )       (double)    //
    //----------------------------------------------------------------------------------------------------------------------------//
    //----------------------------------------------------------------------------------------------------------------------------//
    //                                       TERMINOLOGIA EN LINEAS PARA EJECUCION FLUJO DE POTENCIA                              //
    //                                ----------------------------------------------------------------------                      //
    //TERMINOS:                             Bus          Bus        Z real     Z img       1/2 B      = 1                         //
    //                                 Nodo Inicial   Nodo Final    [p.u.]     [p.u.]     [p.u.]   for lines                      //
    //                                                                                                                            //
    //TIPO DATO                            (int)         (int)     (double)   (double)   (double)   (double)                      //
    //----------------------------------------------------------------------------------------------------------------------------//
    //CONSTRUCCTOR BASICO, ARRANCARÁ CON CIVANLAR, CONFIGURACION [10, 11, 19]

    public PowerFlow() {
        //                                                         INFORMACION ASOCIADA A LAS LINEAS DEL SISTEMA CIVANLAR        
        //                        L1     L2     L3    L4     L5     L6     L7     L8     L9    L10     L11     L12   L13    L14   L15    L16     L17    L18   L19    
        nlOr = new double[]{1, 1, 1, 2, 5, 5, 7, 3, 9, 9, 10, 10, 4, 14, 14, 16, 6, 11, 8};
        nrOr = new double[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 12, 15, 17};
        rOr = new double[]{0, 0, 0, 0.075, 0.080, 0.090, 0.040, 0.110, 0.080, 0.110, 0.110, 0.080, 0.110, 0.090, 0.080, 0.040, 0.040, 0.040, 0.090};
        xOr = new double[]{1e-4, 1e-4, 1e-4, 0.10, 0.11, 0.18, 0.04, 0.11, 0.11, 0.11, 0.11, 0.11, 0.11, 0.12, 0.11, 0.04, 0.04, 0.04, 0.12};
        bcOr = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        aOr = new double[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        //iMaxOr = new double[]{0.1  , 0.1  , 0.1  , 0.1  , 0.05 , 0.05 , 0.05 , 0.1  , 0.06 , 0.05 , 0.05 , 0.05 , 0.05 , 0.05 , 0.05 , 0.05 , 0.05 , 0.05 , 0.05 };

        //                                                      INFORMACION ASOCIADA A LOS NODOS DEL SISTEMA CIVANLAR        
        //                              N1     N2    N3    N4     N5     N6     N7     N8     N9     N10    N11    N12    N13    N14    N15    N16    N17    
        numBus = new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};
        codBus = new double[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        magVoltBus = new double[]{1, 1, 1, 1, 0.991, 0.988, 0.986, 0.985, 0.981, 0.973, 0.990, 0.988, 0.972, 0.992, 0.991, 0.990, 0.989};
        angleBus = new double[]{0, 0, 0, 0, -0.43, -0.66, -0.75, -0.76, -0.68, -1.31, -0.49, -0.69, -1.69, -0.34, -0.49, -0.53, -0.60};
        loadMW = new double[]{0, 0, 0, 0, 2, 3, 2, 1.5, 4, 5, 1, 0.6, 4.5, 1, 1, 1, 2.1};
        loadMVar = new double[]{0, 0, 0, 0, 1.6, 0.4, -0.4, 1.2, 2.7, 1.8, 0.9, -0.5, -1.7, 0.9, -1.1, 0.9, -0.8};
        genMWBus = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gendMvarBus = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        genQMinBus = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        genQMaxBus = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        staticMvar = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        //SE ELIMINAN LAS LINEAS PARA GENERAR LA CONFIGURACION RADIAL DE LA RED    
        openLines = new int[]{19, 11, 10};

        nl = new double[nlOr.length - openLines.length];
        nr = new double[nlOr.length - openLines.length];
        r = new double[nlOr.length - openLines.length];
        x = new double[nlOr.length - openLines.length];
        bc = new double[nlOr.length - openLines.length];
        a = new double[nlOr.length - openLines.length];
        //iMax  = new double[nlOr.length - openLines.length];

        int[] auxArray = new int[openLines.length];
        for (i = 0; i < openLines.length; i++) {
            int linea = openLines[0];
            int posicion = 0;
            for (int j = 0; j < openLines.length; j++) {
                if (linea > openLines[j]) {
                    linea = openLines[j];
                    posicion = j;
                }
            }
            auxArray[i] = linea;
            openLines[posicion] = 1000;
        }

        // Imprimiendo en pantalla para ver que tiene openLines
        h = 0;
        for (i = 0; i < auxArray.length; i++) {
            openLines[i] = auxArray[i];
        }

        for (i = 0; i < nlOr.length; i++) {
            int flag = 0;
            for (int j = 0; j < openLines.length; j++) {
                if (i == (openLines[j] - 1)) {
                    flag++;
                }
            }
            if (flag == 0) {
                nl[h] = nlOr[i];
                nr[h] = nrOr[i];
                r[h] = rOr[i];
                x[h] = xOr[i];
                bc[h] = bcOr[i];
                a[h] = aOr[i];
//                iMax[h] = iMaxOr[i];
                h++;
            }
        }
    }

    //CONSTRUCTOR INGRESO DE PARAMETROS DE RED
    public PowerFlow(double[][] dataLine, double[][] dataBus, int[] openLines) {
        this.dataLine = dataLine;
        this.dataBus = dataBus;
        this.openLines = openLines;

        //SE OBTIENEN LOS VALORES DESDE LA MATRIZ DE DATOS DE LINEA,
        //ESTA MATRIZ DEBE SER NECESARIAMENTE DE 6 COLUMNAS (para lineas), Y 11
        //COLUMNAS (en caso de barras), EN EL ORDEN DE DATOS QUE APARECE EN LA 
        //CABECERA DE ESTE ARCHIVO (cod lineas 60-78)
        nlOr = new double[this.dataLine.length];
        nrOr = new double[this.dataLine.length];
        rOr = new double[this.dataLine.length];
        xOr = new double[this.dataLine.length];
        bcOr = new double[this.dataLine.length];
        aOr = new double[this.dataLine.length];

        numBus = new double[this.dataBus.length];
        codBus = new double[this.dataBus.length];
        magVoltBus = new double[this.dataBus.length];
        angleBus = new double[this.dataBus.length];
        loadMW = new double[this.dataBus.length];
        loadMVar = new double[this.dataBus.length];
        genMWBus = new double[this.dataBus.length];
        gendMvarBus = new double[this.dataBus.length];
        genQMinBus = new double[this.dataBus.length];
        genQMaxBus = new double[this.dataBus.length];
        staticMvar = new double[this.dataBus.length];

        for (i = 0; i < dataLine.length; i++) {
            nlOr[i] = dataLine[i][0];
            nrOr[i] = dataLine[i][1];
            rOr[i] = dataLine[i][2];
            xOr[i] = dataLine[i][3];
            bcOr[i] = dataLine[i][4];
            aOr[i] = dataLine[i][5];
        }

        for (i = 0; i < dataBus.length; i++) {
            numBus[i] = dataBus[i][0];
            codBus[i] = dataBus[i][1];
            magVoltBus[i] = dataBus[i][2];
            angleBus[i] = dataBus[i][3];
            loadMW[i] = dataBus[i][4];
            loadMVar[i] = dataBus[i][5];
            genMWBus[i] = dataBus[i][6];
            gendMvarBus[i] = dataBus[i][7];
            genQMinBus[i] = dataBus[i][8];
            genQMaxBus[i] = dataBus[i][9];
            staticMvar[i] = dataBus[i][10];
        }

        //SE ELIMINAN LAS LINEAS PARA GENERAR LA CONFIGURACION RADIAL DE LA RED    
        this.openLines = openLines;

        nl = new double[nlOr.length - this.openLines.length];
        nr = new double[nlOr.length - this.openLines.length];
        r = new double[nlOr.length - this.openLines.length];
        x = new double[nlOr.length - this.openLines.length];
        bc = new double[nlOr.length - this.openLines.length];
        a = new double[nlOr.length - this.openLines.length];

        int[] auxArray = new int[this.openLines.length];
        for (i = 0; i < this.openLines.length; i++) {
            int linea = this.openLines[0];
            int posicion = 0;
            for (int j = 0; j < this.openLines.length; j++) {
                if (linea > this.openLines[j]) {
                    linea = this.openLines[j];
                    posicion = j;
                }
            }
            auxArray[i] = linea;
            this.openLines[posicion] = 1000;
        }

        // Imprimiendo en pantalla para ver que tiene openLines
        h = 0;
        for (i = 0; i < auxArray.length; i++) {
            this.openLines[i] = auxArray[i];
        }

        for (i = 0; i < nlOr.length; i++) {
            int flag = 0;
            for (int j = 0; j < this.openLines.length; j++) {
                if (i == (this.openLines[j] - 1)) {
                    flag++;
                }
            }
            if (flag == 0) {
                nl[h] = nlOr[i];
                nr[h] = nrOr[i];
                r[h] = rOr[i];
                x[h] = xOr[i];
                bc[h] = bcOr[i];
                a[h] = aOr[i];
                h++;
            }
        }
    }

    //CONSTRUCTOR INGRESO DE PARAMETROS DE RED POR TIPO
    public PowerFlow(double[] initialNodeL, double[] finalNodeL, double[] ZrL, double[] ZxL, double[] bcL, double[] aL,
            double[] numberBus, double[] codeBus, double[] magVolt, double[] angleVolt, double[] loadMW, double[] loadMVar,
            double[] genMWBus, double[] gendMvarBus, double[] genQMinBus, double[] genQMaxBus, double[] staticMvar,
            int[] openLines) {
        this.nlOr = initialNodeL;
        this.nrOr = finalNodeL;
        this.rOr = ZrL;
        this.xOr = ZxL;
        this.bcOr = bcL;
        this.aOr = aL;

        this.numBus = numberBus;
        this.codBus = codeBus;
        this.magVoltBus = magVolt;
        this.angleBus = angleVolt;
        this.loadMW = loadMW;
        this.loadMVar = loadMVar;

        this.genMWBus = genMWBus;
        this.gendMvarBus = gendMvarBus;
        this.genQMinBus = genQMinBus;
        this.genQMaxBus = genQMaxBus;
        this.staticMvar = staticMvar;

        //SE ELIMINAN LAS LINEAS PARA GENERAR LA CONFIGURACION RADIAL DE LA RED    
        this.openLines = openLines;

        nl = new double[nlOr.length - this.openLines.length];
        nr = new double[nlOr.length - this.openLines.length];
        r = new double[nlOr.length - this.openLines.length];
        x = new double[nlOr.length - this.openLines.length];
        bc = new double[nlOr.length - this.openLines.length];
        a = new double[nlOr.length - this.openLines.length];

        int[] auxArray = new int[this.openLines.length];
        for (i = 0; i < this.openLines.length; i++) {
            int linea = this.openLines[0];
            int posicion = 0;
            for (int j = 0; j < this.openLines.length; j++) {
                if (linea > this.openLines[j]) {
                    linea = this.openLines[j];
                    posicion = j;
                }
            }
            auxArray[i] = linea;
            this.openLines[posicion] = 1000;
        }

        // Imprimiendo en pantalla para ver que tiene openLines
        h = 0;
        for (i = 0; i < auxArray.length; i++) {
            this.openLines[i] = auxArray[i];
        }

        for (i = 0; i < nlOr.length; i++) {
            int flag = 0;
            for (int j = 0; j < this.openLines.length; j++) {
                if (i == (this.openLines[j] - 1)) {
                    flag++;
                }
            }
            if (flag == 0) {
                nl[h] = nlOr[i];
                nr[h] = nrOr[i];
                r[h] = rOr[i];
                x[h] = xOr[i];
                bc[h] = bcOr[i];
                a[h] = aOr[i];
                h++;
            }
        }

    }

    //ESTE ES EL QUE TENGO QUE USAR POR AHORA
    //    !!!!!!!
    //CONSTRUCTOR INGRESO DE PARAMETROS DE RED POR TIPO SIN GENERADORES
    public PowerFlow(double[] lineNo, double[] initialNodeL, double[] finalNodeL, double[] ZrL, double[] ZxL, double[] bcL, double[] aL,
            double[] numberBus, double[] codeBus, double[] magVolt, double[] angleVolt, double[] loadMW, double[] loadMVar,
            int[] openLines) {

        lineNumberOr = lineNo;
        nlOr = initialNodeL;
        nrOr = finalNodeL;
        rOr = ZrL;
        xOr = ZxL;
        bcOr = bcL;
        aOr = aL;

        this.numBus = numberBus;
        this.codBus = codeBus;
        this.magVoltBus = magVolt;
        this.angleBus = angleVolt;
        this.loadMW = loadMW;
        this.loadMVar = loadMVar;

        genMWBus = new double[this.numBus.length];
        gendMvarBus = new double[this.numBus.length];
        genQMinBus = new double[this.numBus.length];
        genQMaxBus = new double[this.numBus.length];
        staticMvar = new double[this.numBus.length];

        //System.out.println("Hemos cargado este constructor");
        for (i = 0; i < numBus.length; i++) {
            genMWBus[i] = 0;
            gendMvarBus[i] = 0;
            genQMinBus[i] = 0;
            genQMaxBus[i] = 0;
            staticMvar[i] = 0;
        }

        //SE ELIMINAN LAS LINEAS PARA GENERAR LA CONFIGURACION RADIAL DE LA RED    
        this.openLines = openLines;

        lineNumber = new double[nlOr.length - this.openLines.length];
        nl = new double[nlOr.length - this.openLines.length];
        nr = new double[nlOr.length - this.openLines.length];
        r = new double[nlOr.length - this.openLines.length];
        x = new double[nlOr.length - this.openLines.length];
        bc = new double[nlOr.length - this.openLines.length];
        a = new double[nlOr.length - this.openLines.length];

        int[] auxArray = new int[this.openLines.length];
        for (i = 0; i < this.openLines.length; i++) {
            int linea = this.openLines[0];
            int posicion = 0;
            for (int j = 0; j < this.openLines.length; j++) {
                if (linea > this.openLines[j]) {
                    linea = this.openLines[j];
                    posicion = j;
                }
            }
            auxArray[i] = linea;
            this.openLines[posicion] = 1000;
        }

        // Imprimiendo en pantalla para ver que tiene openLines
        h = 0;
        for (i = 0; i < auxArray.length; i++) {
            this.openLines[i] = auxArray[i];
        }

        for (int k = 0; k < openLines.length; k++) {
            //System.out.println("valores lineas abiertas: " + openLines[k]);
        }

        for (i = 0; i < nlOr.length; i++) {
            int flag = 0;
            for (int j = 0; j < this.openLines.length; j++) {
                if (lineNumberOr[i] == (this.openLines[j])) {
                    flag++;
                }
            }
            if (flag == 0) {
                lineNumber[h] = lineNumberOr[i];
                nl[h] = nlOr[i];
                nr[h] = nrOr[i];
                r[h] = rOr[i];
                x[h] = xOr[i];
                bc[h] = bcOr[i];
                a[h] = aOr[i];
                h++;
            }
        }
    }

    //CONSTRUCTOR INGRESO DE PARAMETROS DE RED POR TIPO SIN GENERADORES Y BC=0 ,A=1
    public PowerFlow(double[] initialNodeL, double[] finalNodeL, double[] ZrL, double[] ZxL,
            double[] numberBus, double[] codeBus, double[] magVolt, double[] angleVolt, double[] loadMW, double[] loadMVar,
            int[] openLines) {
        nlOr = initialNodeL;
        nrOr = finalNodeL;
        rOr = ZrL;
        xOr = ZxL;

        bcOr = new double[this.nlOr.length];
        aOr = new double[this.nlOr.length];

        for (i = 0; i < nlOr.length; i++) {
            bcOr[i] = 0;
            aOr[i] = 1;
        }

        this.numBus = numberBus;
        this.codBus = codeBus;
        this.magVoltBus = magVolt;
        this.angleBus = angleVolt;
        this.loadMW = loadMW;
        this.loadMVar = loadMVar;

        genMWBus = new double[this.numBus.length];
        gendMvarBus = new double[this.numBus.length];
        genQMinBus = new double[this.numBus.length];
        genQMaxBus = new double[this.numBus.length];
        staticMvar = new double[this.numBus.length];

        for (i = 0; i < numBus.length; i++) {
            genMWBus[i] = 0;
            gendMvarBus[i] = 0;
            genQMinBus[i] = 0;
            genQMaxBus[i] = 0;
            staticMvar[i] = 0;
        }

        //SE ELIMINAN LAS LINEAS PARA GENERAR LA CONFIGURACION RADIAL DE LA RED    
        this.openLines = openLines;

        nl = new double[nlOr.length - this.openLines.length];
        nr = new double[nlOr.length - this.openLines.length];
        r = new double[nlOr.length - this.openLines.length];
        x = new double[nlOr.length - this.openLines.length];
        bc = new double[nlOr.length - this.openLines.length];
        a = new double[nlOr.length - this.openLines.length];

        int[] auxArray = new int[this.openLines.length];
        for (i = 0; i < this.openLines.length; i++) {
            int linea = this.openLines[0];
            int posicion = 0;
            for (int j = 0; j < this.openLines.length; j++) {
                if (linea > this.openLines[j]) {
                    linea = this.openLines[j];
                    posicion = j;
                }
            }
            auxArray[i] = linea;
            this.openLines[posicion] = 1000;
        }

        // Imprimiendo en pantalla para ver que tiene openLines
        h = 0;
        for (i = 0; i < auxArray.length; i++) {
            this.openLines[i] = auxArray[i];
        }

        for (i = 0; i < nlOr.length; i++) {
            int flag = 0;
            for (int j = 0; j < this.openLines.length; j++) {
                if (i == (this.openLines[j] - 1)) {
                    flag++;
                }
            }
            if (flag == 0) {
                nl[h] = nlOr[i];
                nr[h] = nrOr[i];
                r[h] = rOr[i];
                x[h] = xOr[i];
                bc[h] = bcOr[i];
                a[h] = aOr[i];
                h++;
            }
        }
    }

    public void runLoadFlow() {
        basemva = ElectricalNetwork.getElectricalNetwork().getBaseMVA();
        accuracy = ElectricalNetwork.getElectricalNetwork().getAccuracy();
        accel = ElectricalNetwork.getElectricalNetwork().getAccel();
        maxIter = ElectricalNetwork.getElectricalNetwork().getMaxIter();

        boolean start = true;
        String decimales = "#.";
        double aux = accuracy;
        while (start && aux != 0) {
            aux = aux * 10;
            //System.out.println("Entra? " + aux);
            if (aux > 1) {
                start = false;
            } else {
                decimales = decimales.concat("#");
            }
        }

        //System.out.println("Decimales: " + decimales);
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        formatoDecimal = new DecimalFormat(decimales, simbolos);

        tiempoInicio = System.currentTimeMillis();
        Bc = new Complex[(int) bc.length];

        for (i = 0; i < bc.length; i++) {
            Bc[i] = new Complex(0, bc[i]);
        }

        nbr = nl.length;
        //System.out.println("largo nbr: "+nbr);

        Z = new Complex[nbr];
        y = new Complex[nbr];
        unoC = new Complex(1, 0);

        double maxNl = 0;
        double maxNr = 0;

        //Busco el nodo mayor, es decir, indice mayor
        for (i = 0; i < nl.length; i++) {
            if (maxNl < nl[i]) {
                maxNl = nl[i];
            }
            if (maxNr < nr[i]) {
                maxNr = nr[i];
            }
        }
        if (maxNr >= maxNl) {
            nbus = maxNr;
        } else {
            nbus = maxNl;
        }

        for (i = 0; i < nl.length; i++) {
            Z[i] = new Complex(r[i], x[i]);
        }

        //        // Imprimiendo en pantalla para ver que tiene Z
        //        for (i=0; i<nl.length; i++){
        //            System.out.println(Z[i]);
        //        }
        int nbusAux = (int) nbus;

        YbusC = new Complex[nbusAux][nbusAux];

        for (i = 0; i < nbusAux; i++) {
            for (int j = 0; j < nbusAux; j++) {
                YbusC[i][j] = new Complex(0, 0);
            }
        }

        for (i = 0; i < y.length; i++) {
            y[i] = unoC.divide(Z[i]);
        }

        //        System.out.println("Vamos vamos...");
        //        for (i=0; i<lData.numRows; i++){
        //            System.out.println(y[i]);
        for (i = 0; i < nbr; i++) {
            int index1 = (int) (nl[i] - 1);
            int index2 = (int) (nr[i] - 1);
            //            Ybus.set(index1, index2, Ybus.get(index1, index2));
            YbusC[index1][index2] = YbusC[index1][index2].subtract(y[i]);
            YbusC[index2][index1] = YbusC[index1][index2];
        }
        /**
         * ****
         * // Imprimiendo en pantalla para ver que tiene YbusC for (i=0;
         * i<YbusC.length; i++){ for(int j=0; j<YbusC.length; j++){
         * System.out.print(YbusC[i][j]); System.out.print(" "); if
         * (j==YbusC.length-1){ System.out.println(); } } } ****
         */
        //        System.out.println(nbusAux);
        for (i = 0; i < nbusAux; i++) {
            for (int j = 0; j < nbr; j++) {
                int index = (int) nl[j] - 1;
                if (index == i) {
                    YbusC[i][i] = YbusC[i][i].add(y[j]);   // Ybus(n,n) = Ybus(n,n)+y(k)/(a(k)^2) + Bc(k); a es 1  y Bc siempre es 0
                } else if (nr[j] - 1 == i) {
                    YbusC[i][i] = YbusC[i][i].add(y[j]);   //Ybus(n,n) = Ybus(n,n)+y(k) +Bc(k); Bc siempre es 0
                }
            }
        }

        /**
         * *
         * // Imprimiendo en pantalla para ver que tiene YbusC
         * System.out.println(); System.out.println(); for (i=0; i<YbusC.length;
         * i++){ for(int j=0; j<YbusC.length; j++){
         * System.out.print(YbusC[i][j]); System.out.print(" "); if
         * (j==YbusC.length-1){ System.out.println(); } } } *
         */
        //        System.out.println();
        //        System.out.println();
        //        nl.print();
        //        nr.print();
        // SEGUNDO ARCHIVO "LFGAUSS"
        nbus = numBus.length;
        kb = new double[(int) nbus];
        Vm = new double[(int) nbus];
        delta = new double[(int) nbus];
        pd = new double[(int) nbus];
        qd = new double[(int) nbus];
        pg = new double[(int) nbus];
        qg = new double[(int) nbus];
        qMin = new double[(int) nbus];
        qMax = new double[(int) nbus];
        Qsh = new double[(int) nbus];
        DV = new double[(int) nbus];

        V = new Complex[(int) nbus];
        S = new Complex[(int) nbus];

        P = new double[(int) nbus];
        Q = new double[(int) nbus];

        //System.out.println("limite : "+nbus);
        for (int k = 0; k < nbus; k++) {
            n = (int) numBus[k] - 1;
            kb[n] = (int) codBus[k];
            Vm[n] = magVoltBus[k];
            delta[n] = angleBus[k];
            pd[n] = loadMW[k];
            qd[n] = loadMVar[k];
            pg[n] = genMWBus[k];
            qg[n] = gendMvarBus[k];
            qMin[n] = genQMinBus[k];
            qMax[n] = genQMaxBus[k];
            Qsh[n] = staticMvar[k];

            if (Vm[n] <= 0) {
                Vm[n] = 1;
                V[n] = new Complex(1, 0);
            } else {
                delta[n] = (Math.PI / 180) * delta[n];
                complejoAux = new Complex(Math.cos(delta[n]), Math.sin(delta[n]));
                V[n] = complejoAux.multiply(Vm[n]);
                P[n] = (pg[n] - pd[n]) / basemva;
                Q[n] = (qg[n] - qd[n] + Qsh[n]) / basemva;
                complejoAux = new Complex(P[n], Q[n]);
                S[n] = complejoAux;
            }
            DV[n] = 0;
        }

        //        System.out.println("Vamos vamos...");
        //        for (i=0; i<V.length; i++){
        //            System.out.print(V[i]);
        //            System.out.print("           ");
        //            System.out.print(P[i]);
        //            System.out.print("   ");
        //            System.out.print(Q[i]);
        //            System.out.print("           ");
        //            System.out.println(S[i]);
        //        }
        num = 0;
        acurBus = 0;
        converge = 1;
        Vc = new Complex[(int) nbus];
        Sc = new Complex(0, 0);

        for (i = 0; i < nbus; i++) {
            Vc[i] = new Complex(0, 0);
        }

        mLine = new double[(int) nbr];
        for (int k = 0; k < nbr; k++) {
            mLine[k] = 1;
        }

        for (int k = 0; k < nbr; k++) {
            for (int m = k + 1; m < nbr; m++) {
                if ((nl[k] == nl[m] && (nr[k] == nr[m]))) {
                    mLine[m] = 2;
                } else if (nl[k] == nr[m] && (nr[k] == nl[m])) {
                    mLine[m] = 2;
                }
            }
        }
        /**
         * **
         * // Imprimiendo en pantalla para ver que tiene Z for (i=0;
         * i<lData.numRows; i++){ System.out.println(mLine[i]); }
         *
         ***
         */
        iter = 0;
        maxError = 10;

        DP = new double[(int) nbus];
        DQ = new double[(int) nbus];

        while (maxError >= accuracy && iter <= maxIter) {
            iter++;
            for (int n = 0; n < nbus; n++) {
                YV = new Complex(0, 0);
                for (int L = 0; L < nbr; L++) {
                    if (nl[L] - 1 == n && mLine[L] == 1) {
                        int indice = (int) nr[L] - 1;
                        YV = YV.add(YbusC[n][indice].multiply(V[indice]));
                    } else if (nr[L] - 1 == n && mLine[L] == 1) {
                        int indice = (int) nl[L] - 1;
                        YV = YV.add(YbusC[n][indice].multiply(V[indice]));
                    }
                }
                // Code MatLab:
                // Sc = conj(V(n))*(Ybus(n,n)*V(n) + YV) ;
                // Sc = conj(Sc);
                complejoAux = V[n].conjugate();
                complejoAux2 = YV.add(YbusC[n][n].multiply(V[n]));
                Sc = complejoAux.multiply(complejoAux2);
                Sc = Sc.conjugate();
                DP[n] = P[n] - Sc.getReal();
                DQ[n] = Q[n] - Sc.getImaginary();

                if (kb[n] == 1) {
                    S[n] = Sc;
                    P[n] = Sc.getReal();
                    Q[n] = Sc.getImaginary();
                    DP[n] = 0;
                    DQ[n] = 0;
                    Vc[n] = V[n];
                } else if (kb[n] == 2) {
                    Q[n] = Sc.getImaginary();
                    S[n] = new Complex(P[n], Q[n]);
                    if (qMax[n] != 0) {
                        double Qgc = Q[n] * basemva + qd[n] - Qsh[n];
                        if (Math.abs(DQ[n]) <= 0.005 && iter >= 10) {
                            if (DV[n] <= 0.045) {
                                if (Qgc < qMin[n]) {
                                    Vm[n] = Vm[n] + 0.005;
                                    DV[n] = DV[n] + 0.005;
                                } else if (Qgc > qMax[n]) {
                                    Vm[n] = Vm[n] - 0.005;
                                    DV[n] = DV[n] + 0.005;
                                }
                            }
                        }
                    }
                }
                if (kb[n] != 1) {
                    complejoAux = S[n].conjugate();
                    complejoAux2 = V[n].conjugate();
                    complejoAux = complejoAux.divide(complejoAux2).subtract(YV);
                    Vc[n] = complejoAux.divide(YbusC[n][n]);
                }
                if (kb[n] == 0) {
                    complejoAux = Vc[n].subtract(V[n]);
                    complejoAux = complejoAux.multiply(accel);
                    V[n] = V[n].add(complejoAux);
                } else if (kb[n] == 2) {
                    double VcI = Vc[n].getImaginary();
                    double VcR = Math.sqrt(Vm[n] * Vm[n] - VcI * VcI);
                    Vc[n] = new Complex(VcR, VcI);
                    complejoAux = Vc[n].subtract(V[n]);
                    complejoAux = complejoAux.multiply(accel);
                    V[n] = V[n].add(complejoAux);
                }
            }
            double maxDP = 0;
            double maxDQ = 0;
            for (i = 0; i < DP.length; i++) {
                if (Math.abs(DP[i]) > maxDP) {
                    maxDP = Math.abs(DP[i]);
                }
                if (Math.abs(DQ[i]) > maxDQ) {
                    maxDQ = Math.abs(DQ[i]);
                }
            }
            if (maxDP >= maxDQ) {
                maxError = maxDP;
            } else {
                maxError = maxDQ;
            }

            if (iter == maxIter && maxError > accuracy) {
//                System.out.println("La solucion no está convergiendo...");
//                System.out.println("Mostrando resultados...");
                converge = 0;
            }
        }
        /**
         * ****
         * System.out.println("Vamos vamos..."); for (i=0; i<V.length; i++){
         * System.out.print(V[i]); System.out.print(" ");
         * System.out.print(P[i]); System.out.print(" ");
         * System.out.print(Q[i]); System.out.print(" ");
         * System.out.println(S[i]); } ****
         */
        deltaD = new double[(int) nbus];
        yLoad = new Complex[(int) nbus];

        int k = 0;
        for (int n = 0; n < nbus; n++) {
            Vm[n] = V[n].abs();
            deltaD[n] = Math.atan(V[n].getImaginary() / V[n].getReal()) * 180 / Math.PI;
            if (kb[n] == 1) {
                S[n] = new Complex(P[n], Q[n]);
                pg[n] = P[n] * basemva + pd[n];
                qg[n] = Q[n] * basemva + qd[n] - Qsh[n];
                k++;
            } else if (kb[n] == 2) {
                k++;
                S[n] = new Complex(P[n], Q[n]);
                qg[n] = Q[n] * basemva + qd[n] - Qsh[n];
            }
            complejoAux = new Complex(pd[n], Qsh[n] - qd[n]);
            yLoad[n] = complejoAux.divide(basemva * Vm[n] * Vm[n]);
        }

        for (int sum = 0; sum < pg.length; sum++) {
            pgt = pgt + pg[sum];
            qgt = qgt + qg[sum];
            pdt = pdt + pd[sum];
            qdt = qdt + qd[sum];
            Qsht = Qsht + Qsh[sum];
        }

        for (i = 0; i < Vm.length; i++) {
            //System.out.print(Vm[i]);
            //System.out.print("    ");
            //System.out.println(deltaD[i]);
            //            System.out.print("   ");
            //            System.out.print(Q[i]);
            //            System.out.print("           ");
            //            System.out.println(S[i]);
        }

        // TERCER ARCHIVO "LINEFLOW"
        SL = new Complex(0, 0);
        SLT = new Complex(0, 0);

        for (int n = 0; n < nbus; n++) {
            int busPrt = 0;
            for (int L = 0; L < nbr; L++) {
                if (busPrt == 0) {
                    //ACA SE PUEDE IMPRIMIR EN PANTALLA LA CABECERA DE LOS DATOS A MOSTRAR
                    //Code MATLAB:
                    //       %fprintf('   \n'), fprintf('%6g', n), fprintf('      %9.3f', P(n)*basemva)
                    //       %fprintf('%9.3f', Q(n)*basemva), fprintf('%9.3f\n', abs(S(n)*basemva))
                    busPrt = 1;
                }
                if (nl[L] - 1 == n) {
                    k = (int) nr[L] - 1;
                    complejoAux = V[k].multiply((double) a[L]);
                    complejoAux = V[n].subtract(complejoAux);
                    complejoAux = y[L].multiply(complejoAux);
                    complejoAux = complejoAux.divide(a[L] * a[L]);
                    complejoAux2 = Bc[L].divide(a[L] * a[L]);
                    complejoAux2 = complejoAux2.multiply(V[n]);
                    In = complejoAux.add(complejoAux2);

                    complejoAux = V[n].divide(a[L]);
                    complejoAux = V[k].subtract(complejoAux);
                    complejoAux = complejoAux.multiply(y[L]);
                    complejoAux2 = Bc[L].multiply(V[k]);
                    Ik = complejoAux.add(complejoAux2);

                    Snk = In.conjugate().multiply(V[n].multiply(basemva));
                    Skn = Ik.conjugate().multiply(V[k].multiply(basemva));

                    SL = Snk.add(Skn);
                    SLT = SLT.add(SL);
                } else if (nr[L] - 1 == n) {
                    k = (int) nl[L] - 1;
                    complejoAux = V[k].divide(a[L]);
                    complejoAux = V[n].subtract(complejoAux);
                    complejoAux = y[L].multiply(complejoAux);
                    complejoAux2 = Bc[L].multiply(V[k]);
                    In = complejoAux.add(complejoAux2);

                    complejoAux = V[n].multiply(a[L]);
                    complejoAux = V[k].subtract(complejoAux);
                    complejoAux = y[L].multiply(complejoAux);
                    complejoAux = complejoAux.divide(a[L] * a[L]);
                    complejoAux2 = Bc[L].divide(a[L] * a[L]);
                    complejoAux2 = complejoAux2.multiply(V[k]);
                    Ik = complejoAux.add(complejoAux2);

                    Snk = In.conjugate().multiply(V[n].multiply(basemva));
                    Skn = Ik.conjugate().multiply(V[k].multiply(basemva));

                    SL = Snk.add(Skn);
                    SLT = SLT.add(SL);
                }
            }
        }
        SLT = SLT.divide(2);

        currentLine = new Complex[nl.length];

        for (i = 0; i < nl.length; i++) {
            int nodo1 = (int) nl[i];
            int nodo2 = (int) nr[i];
            currentLine[i] = YbusC[nodo1 - 1][nodo2 - 1].multiply(V[nodo1 - 1].subtract(V[nodo2 - 1]));
            //System.out.println("Corriente= " +currentLine[i]);
        }

        tiempoTermino = System.currentTimeMillis();
        tiempoTotal = tiempoTermino - tiempoInicio;
    }

    public float getTime() {
        System.out.println("Algorithm Total Time: " + this.tiempoTotal);
        return (float) tiempoTotal;
    }

    public double[] getMagVoltBus() {
        for (int i = 0; i < Vm.length; i++) {
            Vm[i] = Double.valueOf(formatoDecimal.format(Vm[i]));
        }
        return Vm;
    }

    public double[] getAngleBus() {
        for (int i = 0; i < deltaD.length; i++) {
            deltaD[i] = Double.valueOf(formatoDecimal.format(deltaD[i]));
        }
        return deltaD;
    }

    public Complex[] getLineCurrent() {
        for (int i = 0; i < currentLine.length; i++) {
            currentLine[i] = new Complex(Double.valueOf(formatoDecimal.format(currentLine[i].getReal())), Double.valueOf(formatoDecimal.format(currentLine[i].getImaginary())));
        }
        return currentLine;
    }

    public double[] getMagCurrent() {
        magCurrent = new double[nl.length];
        for (i = 0; i < nl.length; i++) {
            magCurrent[i] = Double.valueOf(formatoDecimal.format(currentLine[i].abs()));
        }

        return magCurrent;
    }

    public double getMWTotalLoss() {
//        System.out.println("Las pérdidas del sistema son: " +SLT.getReal());
        return SLT.getReal();
    }

    public double getMVarTotalLoss() {
        return SLT.getImaginary();
    }

    public double[] getLoadability(double[] maxCurrentLine) {
        iMax = new double[nlOr.length - openLines.length];
        this.iMaxOr = maxCurrentLine;

        magCurrent = new double[nl.length];
        loadability = new double[nl.length];

        for (i = 0; i < nl.length; i++) {
            magCurrent[i] = currentLine[i].abs();
        }
        for (i = 0; i < nlOr.length; i++) {
            int flag = 0;
            for (int j = 0; j < this.openLines.length; j++) {
                if (i == (this.openLines[j] - 1)) {
                    flag++;
                }
            }
            if (flag == 0) {
                iMax[h] = iMaxOr[i];
                h++;
            }
        }
        for (i = 0; i < nl.length; i++) {
            loadability[i] = magCurrent[i] / iMax[i];
        }

        for (int i = 0; i < loadability.length; i++) {
            loadability[i] = Double.valueOf(formatoDecimal.format(loadability[i]));
        }

        return loadability;
    }

    public void getFundamentalLoops() {
        //
        // ACA DEBO INCLUIR EL ALGORITMO DE HECTOR PAVEZ!!!
        // DEVOLVER ACA EL RESULTADO... QUITAR VOID!
        //
    }

    /*
    public void setNewConfiguration() {
    }
     */
    public void setOpenLines(int[] openLines) {
        this.openLines = openLines;
        int[] auxArray = new int[this.openLines.length];
        for (i = 0; i < this.openLines.length; i++) {
            int linea = this.openLines[0];
            int posicion = 0;
            for (int j = 0; j < this.openLines.length; j++) {
                if (linea > this.openLines[j]) {
                    linea = this.openLines[j];
                    posicion = j;
                }
            }
            auxArray[i] = linea;
            this.openLines[posicion] = 1000;
        }
    }

    /*
    public void setLoadConsumption() {
    }

    public void setGenerationBus() {
    }
     */
    public List getMagVoltBusAsList() {
        List Vmag = new ArrayList();
        for (int i = 0; i < Vm.length; i++) {
            if (Double.isNaN(Vm[i])) {
                Vmag.add(0.0);
            } else {
                Vmag.add(Vm[i]);
            }
        }
        return Vmag;
    } //añadido por Lisa

    public List getMagCurrentAsList() {
        List magCurrents = new ArrayList();
        for (i = 0; i < nl.length; i++) {
            magCurrents.add(Double.valueOf(formatoDecimal.format(currentLine[i].abs())));
        }
        return magCurrents;
    } //añadido por Lisa

    public void setMaxCurrent(double[] maxCurrentLine) {
        iMax = new double[nlOr.length - openLines.length];
        this.iMaxOr = maxCurrentLine;

        magCurrent = new double[nl.length];
        loadability = new double[nl.length];

        for (i = 0; i < nl.length; i++) {
            magCurrent[i] = currentLine[i].abs();
        }
        for (i = 0; i < nlOr.length; i++) {
            int flag = 0;
            for (int j = 0; j < this.openLines.length; j++) {
                if (i == (this.openLines[j] - 1)) {
                    flag++;
                }
            }
            if (flag == 0) {
                iMax[h] = iMaxOr[i];
                h++;
            }
        }
    }
    /*
    public void addLine() {
    }
     */
}
