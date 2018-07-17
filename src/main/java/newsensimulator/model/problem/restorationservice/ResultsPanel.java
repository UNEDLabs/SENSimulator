package newsensimulator.model.problem.restorationservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Liss
 */
public class ResultsPanel {

    private static List solution;

    public ResultsPanel(List solucion) {
        ResultsPanel.solution = solucion;
    }

    public static void initAndShowGUI() {
        JFrame frame = new JFrame("Results Panel");
        JFXPanel fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setSize(620, 400);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
        });
    }

    private static void initFX(JFXPanel fxPanel) {

        Scene scene = createScene();
        fxPanel.setScene(scene);

    }


    private static Scene createScene() {
      
        GridPane grid = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(34);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(66);
        grid.getColumnConstraints().addAll(col1, col2);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(grid, 620, 400);
        List<Double> losses = (List) ResultsPanel.solution.get(3);
        List infinity = new ArrayList();
        infinity.add(Double.POSITIVE_INFINITY);
        losses.removeAll(infinity);
        System.out.println(losses);
        /*Se definen los ejes*/
        final NumberAxis xAxis = new NumberAxis();
       final NumberAxis yAxis = new NumberAxis((double)Collections.min(losses)-0.05,(double)Collections.max(losses)+0.05,0.01);
        xAxis.setLabel("Evolución");
        yAxis.setLabel("Pérdidas[MW]");
        /*Se crea el gráfico*/
        final LineChart<Number, Number> lineChart
                = new LineChart<Number, Number>(xAxis, yAxis);

        String titulo = "Evolución de las Soluciones";
        lineChart.setTitle(titulo);

        /*Se define una serie*/
        XYChart.Series series = new XYChart.Series();
       

        /*Se llena la serie con datos*/
        for (int i = 0; i < losses.size(); i++) {
            series.getData().add(new XYChart.Data(i + 1, losses.get(i)));
        }
        lineChart.setLegendVisible(false);
        lineChart.getData().add(series);

        scene.getStylesheets().add("newsensimulator/model/problem/restorationservice/chart.css");
        VBox vbox = new VBox();
        VBox vbox2 = new VBox();
        vbox.getStyleClass().add("vbox");
//        vbox.setPadding(new Insets(5));
//        vbox.setSpacing(5);
        vbox2.getChildren().add(lineChart);
        vbox2.getStyleClass().add("vbox");

        List open = (List) ResultsPanel.solution.get(0);
        List close = (List) ResultsPanel.solution.get(1);
        int conmutation = open.size() + close.size();
        String loss = String.format("%.3f", (double) ResultsPanel.solution.get(2));

        String texto0 = "Resumen de resultados";
        String texto1 = "Ubicación de las fallas: " + ResultsPanel.solution.get(5);
        String texto2 = "Aislar Falla abriendo líneas: \n" + ResultsPanel.solution.get(4);
        String texto3 = "Configuración de Algoritmo Genético: \n";

        String texto4 = "  Cantidad de evoluciones: " + ResultsPanel.solution.get(6) + " \n"
                + "  Tamaño de población: " + ResultsPanel.solution.get(7) + "\n" + "  Porcentaje de Cruza: " + ResultsPanel.solution.get(8) + "\n" + "  Porcentaje de Mutación: " + ResultsPanel.solution.get(9) + "\n";

        String texto5 = "  Líneas a abrir: " + open + "\n" + "  Líneas a cerrar: " + close + "\n" + "  Operaciones de Conmutación: " + conmutation + "\n" + "  Pérdidas del Sistema:" + loss + " MW \n";

        Label text0 = new Label(texto0);
        Label text = new Label(texto1);
        Label text2 = new Label(texto2);
        Label text3 = new Label(texto3);
        Label text4 = new Label(texto4);
        Label text5 = new Label("Restauración de Servicio:");
        Label text6 = new Label(texto5);

        text.setPadding(new Insets(5, 5, 5, 5));
        text.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
        text.setWrapText(true);
        text0.setPadding(new Insets(15, 5, 15, 5));
        text0.setStyle("-fx-font-size: 17; -fx-text-fill: #380146; -fx-font-weight:bold;");
        text0.setWrapText(true);
        text2.setPadding(new Insets(5, 5, 5, 5));
        text2.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
        text2.setWrapText(true);
        text3.setPadding(new Insets(5, 5, 5, 5));
        text3.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
        text3.setWrapText(true);
        text4.setPadding(new Insets(5, 5, 5, 5));
        text4.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        text4.setWrapText(true);
        text5.setPadding(new Insets(5, 5, 5, 5));
        text5.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
        text5.setWrapText(true);
        text6.setPadding(new Insets(5, 5, 5, 5));
        text6.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        text6.setWrapText(true);

        vbox.getChildren().addAll(text0, text, text2, text3, text4, text5, text6);
        grid.add(vbox, 0, 0);
        grid.add(vbox2, 1, 0);
        return scene;
    }

 public void runresults() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initAndShowGUI();
            }
        });

    }
    

}
