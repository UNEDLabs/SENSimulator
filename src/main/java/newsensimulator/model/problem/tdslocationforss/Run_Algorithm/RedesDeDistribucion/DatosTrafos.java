/*
|xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
|xxxxxxx|                      Datos:                     |xxxxxxx|
|xxxxxxx|              TIPOS Y PRECIOS DE TDs             |xxxxxxx|
|xxxxxxx|                      2011                       |xxxxxxx|
|xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|
|xxxxxxx|          David Labra Pinto (Marzo2010)          |xxxxxxx|
|xxxxxxx|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|xxxxxxx|

  kVA Valor($)
  RED DE 12 KV CGE
*/



package newsensimulator.model.problem.tdslocationforss.Run_Algorithm.RedesDeDistribucion;

//import newsensimulator.model.problem.tdslocationforss.Run_Algorithm.TDs_Location.TDs_Location;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * @author Andreuw Madrid Carreño
 */

public class DatosTrafos {
    
//public TDs_Location TDs_Location = new TDs_Location();
//double Vbm = TDs_Location.get
double inf = Double.POSITIVE_INFINITY;
double f = 1;
RealMatrix trafos;

public RealMatrix DatosTrafos(double Vbm){
    if(Vbm == 12){
        double [][] trafos1 = {
            
//   %__S(kVA)_____$______Po(kW)_______Pcu(kW)
      {    15 ,   1000000,   f*0.095,   f*0.455},
      {    30 ,   1515000,   f*0.135,   f*0.515},
      {    45 ,   1767000,   f*0.180,   f*0.710},
      {    75 ,   2176000,   f*0.265,   f*1.090},
      { 112.5 ,   2820000,   f*0.365,   f*1.540},
      {   150 ,   3500000,   f*0.450,   f*1.960},
      {   300 ,   4647000,   f*0.780,   f*4.000},
      {   inf ,   inf    ,   inf    ,      inf}
        };
      trafos = new Array2DRowRealMatrix(trafos1);      
    }
  
    if(Vbm == 15){
       
        double [][] trafos1 =  
//    %__S(kVA)_____$______Po(kW)_______Pcu(kW)
        {{   15 ,    1028000,   1.15*0.095,   1.15*0.455},
        {    30 ,    1340000,   1.15*0.135,   1.15*0.515}, 
        {    45 ,    1587000,   1.15*0.180,   1.15*0.710},
        {    75 ,    2044000,   1.15*0.265,   1.15*1.090},
        { 112.5 ,    2681000,   1.15*0.365,   1.15*1.540},
        {   300 ,    4449000,   1.15*0.450,   1.15*1.960},
        {   500 ,    7357000,   1.15*0.780,   1.15*4.000},
        {   inf ,       inf ,      inf    ,        inf}};
        
        trafos = new Array2DRowRealMatrix(trafos1);      
    }
    
    if(Vbm == 23){
       
        double [][] trafos1 =
//        %__S(kVA)_____$______Po(kW)_______Pcu(kW)
        {{   10,      955000,   1.15*0.095,   1.15*0.455},
        {    15,     1208000,   1.15*0.135,   1.15*0.515},
        {    30,     1382000,   1.15*0.180,   1.15*0.665},
        {    45,     1767000,   1.15*0.265,   1.15*0.820},
        {    75,     2152000,   1.15*0.365,   1.15*1.340},
        { 112.5,     2964000,   1.15*0.450,   1.15*1.540},
        {   150,     3691000,   1.15*0.460,   1.15*2.180},
        {   300,     4966000,   1.15*0.780,   1.15*4.000},
        {   500,     6914000,   1.15*1.080,   1.15*5.950},
        {   inf,     inf    ,   inf       ,         inf}};
        
        trafos = new Array2DRowRealMatrix(trafos1);    
    }
    return trafos;
    
}

 public RealMatrix gettrafos() {
        return trafos;
    }
}
