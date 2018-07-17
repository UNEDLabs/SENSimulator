/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.problem.esslocationsize;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Liss
 */
public class RunnerFX extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {

//        Parent root = FXMLLoader.load(getClass().getResource("FXMLDailyClass.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("FXMLESSPanel.fxml"));

//        root.getChildren().add(btn);
        
        Scene scene = new Scene(root);        
//        primaryStage.setTitle("Daily Load Configuration");
        primaryStage.setTitle("ESS Location & Size Algorithm");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public void run (){
        
        
        
        launch();
    }
//    public void runner() {
//		super.runner();
//		try {
//                        // Because we need to init the JavaFX toolkit - which usually Application.launch does
//                        // I'm not sure if this way of launching has any effect on anything
//			new JFXPanel();
//
//			Platform.runLater(new Runnable() {
//				@Override
//				public void run() {
//					// Your class that extends Application
//					new RunnerFX.start(new Stage());
//				}
//			});
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
    
}
