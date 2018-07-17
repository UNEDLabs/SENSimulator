/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.problem.esslocationsize;

import java.awt.geom.Point2D;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import newsensimulator.control.Controller;
import newsensimulator.model.ElectricalNetwork;
import newsensimulator.model.networkelements.Edge;
import newsensimulator.model.networkelements.EdgeFactory;
import newsensimulator.model.networkelements.Vertex;
import newsensimulator.view.AreaSimulacion;
import newsensimulator.view.MainInterface;
import org.uma.jmetal.solution.IntegerDoubleSolution;

/**
 * FXML Controller class
 *
 * @author Liss
 */
public class FXMLESSPanelController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField numEvol;

    @FXML
    private TextField planAños;
    @FXML
    private ScatterChart<?, ?> grCostVolt;

    @FXML
    private ScatterChart<CategoryAxis, NumberAxis> grCostPerd;

    @FXML
    private TableColumn<Integer, String> columnLocation;

    @FXML
    private TableView<Integer> resultsTable;

    @FXML
    private TextField cantBess;

    @FXML
    private TableColumn<Integer, String> columnType;

    @FXML
    private CheckBox checkPlan;

    @FXML
    private CheckBox checkcantBESS;

    @FXML
    private ChoiceBox<String> metaBox;

    @FXML
    private TableColumn<Integer, String> columnSize;

    @FXML
    private TableColumn<Integer, String> columnObjetive;

    @FXML
    private TextField tamPob;

    @FXML
    private LineChart<?, ?> grLoad;

    @FXML
    private LineChart<?, ?> grLosses;

    @FXML
    private Button initButton;
    @FXML
    private Button selectedButton;

    @FXML
    private NumberAxis resultLossAxis;

    @FXML
    private NumberAxis resultVoltAxis;
    @FXML
    private CategoryAxis resultsCostAxis;
    @FXML
    private ProgressBar progressBar;

    private List keyPower;
    private List nodes;
    private int evol;

    //    final ObservableList<BESSSolution> data = FXCollections.observableArrayList(new BESSSolution("asdf"), new BESSSolution("asdf"));
    public List getKeyPower() {
        return keyPower;
    }

    public List getNodes() {
        return nodes;
    }

    public void setEvol(int evol) {
        this.evol = evol;
    }

    public int getEvol() {
        return evol;
    }

    private static FXMLESSPanelController instance;

    public static FXMLESSPanelController getInstance() {
        if (instance == null) {
            instance = new FXMLESSPanelController();
        }
        return instance;
    }

    @FXML
    void initAlgorithm(ActionEvent event) throws InterruptedException {

        progressBar.setProgress(0.5);

        resultsTable.getItems().clear();
        grCostPerd.getData().clear();

        NSGAII_main3 runNSGA = new NSGAII_main3();
        int evol = Integer.parseInt(numEvol.getText());
        int pob = Integer.parseInt(tamPob.getText());
        double años;
//        if (!checkPlan.isSelected()) {
            años = Double.parseDouble(planAños.getText());
//        } else {
//           años = 0;
//        }

        if (checkcantBESS.isSelected()) {
            runNSGA.setQuantity(true);
            int cantbess = Integer.parseInt(cantBess.getText());
            runNSGA.setBESS_AMOUNT(cantbess);
        } else {
            int cantbess = Integer.parseInt(cantBess.getText());
            runNSGA.setBESS_AMOUNT(cantbess);
        }

        setEvol(evol);

        runNSGA.setEvolutions(evol);
        runNSGA.setPopulationSize(pob);
        runNSGA.setPeriod(años);

        runNSGA.start();

        progressBar.setProgress(1.0);

        List<IntegerDoubleSolution> solution = runNSGA.getPopulation();

        TreeMap finalSolutions = new TreeMap();
        for (int i = 0; i < solution.size(); i++) {
            int variables = solution.get(i).getNumberOfVariables();
            finalSolutions.put(solution.get(i).getVariableValue(variables - 1), solution.get(i));    //Aquí estan las soluciones y el key es el tamaño de bess       
        }

        keyPower = new ArrayList(finalSolutions.keySet());

        List objetive1 = new ArrayList();
        List objetive2 = new ArrayList();
        List objetive3 = new ArrayList();
        nodes = new ArrayList();
        List typeBess = new ArrayList();
        List cantBess = new ArrayList();

        for (int i = 0; i < keyPower.size(); i++) {
            IntegerDoubleSolution sol = (IntegerDoubleSolution) finalSolutions.get(keyPower.get(i));
            objetive1.add(sol.getObjective(0));
            objetive2.add(sol.getObjective(1));
            objetive3.add(sol.getObjective(2));
            cantBess.add(sol.getNumberOfVariables() - 2);
            int[] nod = new int[sol.getNumberOfVariables() - 2];
            for (int j = 0; j < nod.length; j++) {
                nod[j] = (int) sol.getVariableValue(j);
            }
            nodes.add(nod);
            int typeIndex = sol.getNumberOfVariables() - 2;
            switch ((int) sol.getVariableValue(typeIndex) - 1) {
                case 0:
                    typeBess.add("NaS");
                case 1:
                    typeBess.add("VRB");
                case 2:
                    typeBess.add("ZnBr");
                case 3:
                    typeBess.add("Li-Ion");
                case 4:
                    typeBess.add("Lead Acid");
                case 5:
                    typeBess.add("FeCr Flow");

//                case 0:
//                    typeBess.add("advancedLA");
//                case 1:
//                    typeBess.add("znbFlow");
//                case 2:
//                    typeBess.add("vanadiumRedox");
//                case 3:
//                    typeBess.add("fecrFlow ");
//                case 4:
//                    typeBess.add("liIon");
            }
        }
//        System.out.println(solution);
//        System.out.println(keyPower);
//        System.out.println(objetive1);
//        System.out.println(objetive2);
//        System.out.println(nodes);
//        System.out.println(typeBess);

        TreeMap objetives12 = new TreeMap();
        TreeMap objetives13 = new TreeMap();
        for (int i = 0; i < objetive1.size(); i++) {
            objetives12.put(round((double) objetive1.get(i) * 100, 2), round((double) objetive2.get(i) * 100, 2));
        }
        for (int i = 0; i < objetive1.size(); i++) {
            objetives13.put(round((double) objetive1.get(i) * 100, 2), round((double) objetive3.get(i) * 100, 2));
        }

//        System.out.println(objetives12);
        objetive1.clear();
        objetive1.addAll(objetives12.keySet());
        objetive2.clear();
        objetive2.addAll(objetives12.values());
        objetive3.clear();
        objetive3.addAll(objetives13.values());
//        List objetive2 = new ArrayList(objetives12.values());

        resultLossAxis.setAutoRanging(false);
        resultLossAxis.setTickUnit(0.05);
        resultLossAxis.setUpperBound((double) Collections.max(objetive2) + 0.2);
        resultLossAxis.setLowerBound((double) Collections.min(objetive2) - 0.2);
//
        XYChart.Series serie = new XYChart.Series();
        for (int i = 0; i < objetive1.size(); i++) {
            serie.getData().add(new XYChart.Data(String.valueOf((double) objetive1.get(i)), ((double) objetive2.get(i))));

        }
        grCostPerd.getData().addAll(serie);

        resultVoltAxis.setAutoRanging(false);
        resultVoltAxis.setTickUnit(0.005);
        resultVoltAxis.setUpperBound((double) Collections.max(objetive3) + 0.05);
        resultVoltAxis.setLowerBound((double) Collections.min(objetive3) - 0.05);

        XYChart.Series serie2 = new XYChart.Series();
        for (int i = 0; i < objetive1.size(); i++) {
            serie2.getData().add(new XYChart.Data(String.valueOf((double) objetive1.get(i)), ((double) objetive3.get(i))));

        }
        grCostVolt.getData().addAll(serie2);
//
//
////        serie.getData().add(new XYChart.Data(String.valueOf(solution.get(i).getObjective(0) * 100), solution.get(i).getObjective(1) * 100));
//        System.out.println(objetive1);
//        System.out.println(objetive2);
////        Thread.sleep(2000);
//

        List powerFinale = new ArrayList();

        for (int i = 0; i < keyPower.size(); i++) {
            double power = (double) keyPower.get(i) / (int) cantBess.get(i);
            power = round(power, 4);
            powerFinale.add(power);
        }

        for (int i = 0; i < objetive1.size(); i++) {
            resultsTable.getItems().add(i);
        }
        columnType.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper((String) typeBess.get(rowIndex));
        });

        columnSize.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            double p = (double) powerFinale.get(rowIndex);
            double e = round((p * 4 / 0.8), 4);
            return new ReadOnlyStringWrapper(String.valueOf(p + " - " + e));
        });

        columnObjetive.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();

            double value1 = (double) objetive1.get(rowIndex);
            double value2 = (double) objetive2.get(rowIndex);
            double value3 = (double) objetive3.get(rowIndex);
            return new ReadOnlyStringWrapper(String.valueOf(value1 + ", " + value2 + ", " + value3));
        });

        columnLocation.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper(Arrays.toString((int[]) nodes.get(rowIndex)));
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progressBar.setProgress(0.0);
        ObservableList<String> metaList = FXCollections.observableArrayList("NSGAII", "other");
        metaBox.setValue("NSGAII");
        metaBox.setItems(metaList);
    }

    @FXML
    void testSolution(ActionEvent event) {

        int[] nod = (int[]) nodes.get(resultsTable.getSelectionModel().getSelectedItem());
        double power = (double) keyPower.get(resultsTable.getSelectionModel().getSelectedItem());

        List results = DailyLoad.getInstance().powerFlowWithBattery(nod, power);
        //perdidas originales, demanda original, perdidas bess, demanda con bess
        List originalLoss = (List) results.get(0);
        List originalLoad = (List) results.get(1);
        List evaluateLoss = (List) results.get(2);
        List evaluateLoad = (List) results.get(3);

        XYChart.Series serieLoad1 = new XYChart.Series();
        XYChart.Series serieLoad2 = new XYChart.Series();
        for (int i = 0; i < originalLoad.size(); i++) {
            serieLoad1.getData().add(new XYChart.Data(String.valueOf(i + 1), originalLoad.get(i)));
        }
        for (int i = 0; i < evaluateLoad.size(); i++) {
            serieLoad2.getData().add(new XYChart.Data(String.valueOf(i + 1), evaluateLoad.get(i)));
        }

        grLoad.setLegendVisible(false);
        grLoad.setCreateSymbols(false);
        grLoad.getData().addAll(serieLoad1);
        grLoad.getData().addAll(serieLoad2);

        XYChart.Series serieLoss1 = new XYChart.Series();
        XYChart.Series serieLoss2 = new XYChart.Series();
        for (int i = 0; i < originalLoss.size(); i++) {
            serieLoss1.getData().add(new XYChart.Data(String.valueOf(i + 1), originalLoss.get(i)));
        }
        for (int i = 0; i < evaluateLoss.size(); i++) {
            serieLoss2.getData().add(new XYChart.Data(String.valueOf(i + 1), evaluateLoss.get(i)));
        }
        grLosses.setLegendVisible(false);
        grLosses.setCreateSymbols(false);
        grLosses.getData().addAll(serieLoss1);
        grLosses.getData().addAll(serieLoss2);

// System.out.println(columnLocation.getCellData(resultsTable.getSelectionModel().getSelectedItem())); 
    }

    @FXML
    void createBattery(ActionEvent event) {

        int[] nod = (int[]) nodes.get(resultsTable.getSelectionModel().getSelectedItem());
        double power = (double) keyPower.get(resultsTable.getSelectionModel().getSelectedItem());
        power = power / nod.length;

        for (int i = 0; i < nod.length; i++) {
            String bus = "B" + nod[i];
            System.out.println(bus);
            Vertex v = ElectricalNetwork.getElectricalNetwork().getBusByName(bus);
            Point2D puntoBus = AreaSimulacion.getAreaSimulacion().getVertexPoint(v);
            System.out.println("punto bus " + puntoBus);
            Point2D puntoBat = new Point2D.Double(puntoBus.getX() + 70, puntoBus.getY() + 40);
            Vertex bat = Controller.getController().crearNodo(puntoBat, "Battery");
            bat.getVertexAsBattery().setBatteryMW(round(power, 4));
            bat.getVertexAsBattery().setBatteryMWh(round(power * 4 / 0.8, 4));
            bat.getVertexAsBattery().setEfficiency(70);

            MainInterface.getMainInterface().actualizar();
            System.out.println("pasó por aqui!! ");
            Edge l = new Edge("SimpleLine", v, bat);
            l.getEdgeAsSimpleLine().setName("");
            l.getEdgeAsSimpleLine().setEstiloLinea(true);
            ElectricalNetwork.getElectricalNetwork().createLine(l);
            MainInterface.getMainInterface().actualizar();

        }

        Stage stage = (Stage) selectedButton.getScene().getWindow();
        stage.close();

    }

    public static double round(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

}
