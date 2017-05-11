/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ihm;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL ;
import java.util.ResourceBundle;



/**
 *
 * @author villermt
 */
public class IHM extends Application {    
   
    @Override
  public void start(final Stage primaryStage) {
    try {
        // Localisation du fichier FXML.
        final URL url = getClass().getResource("MainInterface.fxml");
        // Chargement du bundle:
        ResourceBundle bundle = ResourceBundle.getBundle("ihm/strings");
        // Creation du loader.
        FXMLLoader fxmlLoader = new FXMLLoader(url, bundle);
        // Chargement du FXML.
        AnchorPane root = (AnchorPane) fxmlLoader.load();
        // Accès au contrôleur.
        MainInterfaceController controller = (MainInterfaceController) fxmlLoader.getController();
        // Création de la scène.
        final Scene scene = new Scene(root, 1920, 1020);
        primaryStage.setScene(scene);
      } catch (IOException ex) {
        System.err.println("Erreur au chargement: " + ex);
      }
      primaryStage.setTitle("Saboteur le jeu vidéo");
      primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
