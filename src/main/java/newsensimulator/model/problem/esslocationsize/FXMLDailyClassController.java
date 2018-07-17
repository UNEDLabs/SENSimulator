/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.problem.esslocationsize;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Liss
 */
public class FXMLDailyClassController implements Initializable {
    @FXML
    private TextField jfIndustrial;
    @FXML
    private TextField jfCommercial;
    @FXML
    private TextField jfResidential;
    @FXML
    private LineChart<?, ?> grIndustrial;
    @FXML
    private LineChart<?, ?> grCommercial;
    @FXML
    private LineChart<?, ?> grResidential;
    @FXML
    private LineChart<?, ?> grLosses;
    @FXML
    private LineChart<?, ?> grLoad;
    
    private List <double[]> dailyLoad;
    
    private static FXMLDailyClassController instance;
    
       public static FXMLDailyClassController getInstance() {
        if (instance == null) {
            instance = new FXMLDailyClassController();
        }
        return instance;
    }     
 

    public void setDailyLoad(List<double[]> dailyLoad) {
        this.dailyLoad = dailyLoad;
    }

    public List<double[]> getDailyLoad() {
        return dailyLoad;
    }
          
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        String[] st = jfIndustrial.getText().split(", *");
        double[] num = new double[st.length];
//        int[] ints = jfIndustrial.getText().split(", *");
        XYChart.Series serie = new XYChart.Series();

        for (int i = 0; i < num.length; i++) {
            num[i] = Double.parseDouble(st[i].trim()) / 100;
            serie.getData().add(new XYChart.Data(String.valueOf(i + 1), num[i] * 100));

        }
        grIndustrial.setCreateSymbols(false);
        grIndustrial.getData().addAll(serie);

        String[] st2 = jfCommercial.getText().split(", *");
        double[] num2 = new double[st2.length];
//        int[] ints = jfIndustrial.getText().split(", *");
        XYChart.Series serie2 = new XYChart.Series();

        for (int i = 0; i < num2.length; i++) {
            num2[i] = Double.parseDouble(st2[i].trim())/100;
            serie2.getData().add(new XYChart.Data(String.valueOf(i + 1), num2[i]));

        }
        grCommercial.setCreateSymbols(false);
        grCommercial.getData().addAll(serie2);

        String[] st3 = jfResidential.getText().split(", *");
        double[] num3 = new double[st3.length];
//        int[] ints = jfIndustrial.getText().split(", *");
        XYChart.Series serie3 = new XYChart.Series();

        for (int i = 0; i < num3.length; i++) {
            num3[i] = Double.parseDouble(st3[i].trim())/100;
            serie3.getData().add(new XYChart.Data(String.valueOf(i + 1), num3[i]));

        }
        grResidential.setCreateSymbols(false);
        grResidential.getData().addAll(serie3);
                
        List loads = new ArrayList();
        loads.add(num3); // (0) Residencial
        loads.add(num2); // (1) Comercial
        loads.add(num); //(2)Industrial

        DailyLoad.getInstance().setDailyLoad(loads);        
    }
    
    @FXML
    private void runTotal(ActionEvent event) {
           
        List todo = new ArrayList();
        todo = DailyLoad.getInstance().powerFlowPerHour();
        List datanetwork = (List) todo.get(0);
                
        List load = (List) datanetwork.get(2);
        List losses = (List) todo.get(1);
        XYChart.Series serie4 = new XYChart.Series();
        for (int i = 0; i < load.size(); i++) {
            serie4.getData().add(new XYChart.Data(String.valueOf(i + 1), load.get(i)));
        }
        grLoad.setLegendVisible(false);
        grLoad.setCreateSymbols(false);
        grLoad.getData().addAll(serie4);
 
        
        XYChart.Series serie5 = new XYChart.Series();
        for (int i = 0; i < losses.size(); i++) {
            serie5.getData().add(new XYChart.Data(String.valueOf(i + 1), losses.get(i)));
        }
        grLosses.setLegendVisible(false);
        grLosses.setCreateSymbols(false);
        grLosses.getData().addAll(serie5);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
}
