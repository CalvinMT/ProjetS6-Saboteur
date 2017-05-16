/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IHM;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL ;
import javafx.scene.Parent;



/**
 *
 * @author villermt
 */
public class GameInterface extends Application {    
   
    private double SCREEN_WIDTH =1920.0;
    private double SCREEN_HEIGHT =1080.0 ;
    
    
    
    @Override
  public void start(final Stage primaryStage) throws IOException {
    
        // Localisation du fichier FXML.
        final URL url = getClass().getResource("MainInterface.fxml");
        ;
        // Creation du loader.
        
        Parent parentMainGame = FXMLLoader.load(getClass().getResource("MainInterface.fxml"));
        
       
        // Création de la scène.
        primaryStage.setTitle("Saboteur");
        primaryStage.setScene(new Scene(parentMainGame, SCREEN_WIDTH, SCREEN_HEIGHT));
	primaryStage.setResizable(true);
	primaryStage.show();
      
      AnchorPane anchorPaneMainTop = (AnchorPane) parentMainGame.lookup("#anchorPaneTop");
      AnchorPane anchorPaneTop = FXMLLoader.load(getClass().getResource("Top.fxml"));
      anchorPaneMainTop.getChildren().setAll(anchorPaneTop);
      AnchorPane anchorPaneMainRight = (AnchorPane) parentMainGame.lookup("#anchorPaneRight");
      AnchorPane anchorPaneRight = FXMLLoader.load(getClass().getResource("Right.fxml"));
      anchorPaneMainRight.getChildren().setAll(anchorPaneRight);
      AnchorPane anchorPaneMainBottom = (AnchorPane) parentMainGame.lookup("#anchorPaneBottom");
      AnchorPane anchorPaneBottom = FXMLLoader.load(getClass().getResource("Bottom.fxml"));
      anchorPaneMainBottom.getChildren().setAll(anchorPaneBottom);
      
  }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
