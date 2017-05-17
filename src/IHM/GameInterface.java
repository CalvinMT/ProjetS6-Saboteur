/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IHM;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Parent;



/**
 *
 * @author villermt
 */
public class GameInterface extends Application {    
   
    private double SCREEN_WIDTH =1080.0;
    private double SCREEN_HEIGHT =720.0 ;
    
    
    
    @Override
  public void start(final Stage primaryStage) throws IOException {
    
               
        // Creation du loader.
        
        Parent parentMainGame = FXMLLoader.load(getClass().getResource("MainInterface.fxml"));
        
       
        // Création de la scène.
        primaryStage.setTitle("Saboteur");
        primaryStage.setScene(new Scene(parentMainGame, SCREEN_WIDTH, SCREEN_HEIGHT));
	primaryStage.setResizable(true);
	primaryStage.show();
        
        
      AnchorPane anchorPaneMainBottom = (AnchorPane) parentMainGame.lookup("#anchorPaneBottom");
      AnchorPane anchorPane2Bottom = FXMLLoader.load(getClass().getResource("EndShaft.fxml"));
      anchorPaneMainBottom.getChildren().setAll(anchorPane2Bottom);
      /*
      AnchorPane anchorPaneMainTop = (AnchorPane) parentMainGame.lookup("#anchorPaneTop");
      AnchorPane anchorPane2Top = FXMLLoader.load(getClass().getResource("Top.fxml"));
      anchorPaneMainTop.getChildren().setAll(anchorPane2Top);
      AnchorPane anchorPaneMainRight = (AnchorPane) parentMainGame.lookup("#anchorPaneRight");
      AnchorPane anchorPane2Right = FXMLLoader.load(getClass().getResource("Right.fxml"));
      anchorPaneMainRight.getChildren().setAll(anchorPane2Right);
      */
      
  }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
