package IHM;

import Saboteur.Saboteur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ChargementPartie {

    @FXML
    private AnchorPane anchorPaneMenuMain;


    @FXML
    private Button buttonChargement1;

    @FXML
    private Button buttonChargement2;

    @FXML
    private Button buttonChargement3;

    @FXML
    private Button buttonChargement4;

    @FXML
    private Button buttonChargement5;

    @FXML
    void handleButtonChargement1() throws IOException {
        System.out.println("Chargement de la partie 1");

        Saboteur.game1();

        Scene scene = (Scene) anchorPaneMenuMain.getScene();
        BorderPane borderPaneChargement = (BorderPane) scene.lookup("#borderPaneMainLoader");
        BorderPane borderPaneGameLoader = FXMLLoader.load(getClass().getResource("GameLoader.fxml"));
        borderPaneChargement.getChildren().setAll(borderPaneGameLoader);

    }

    @FXML
    void handleButtonChargement2() {
        System.out.println("Chargement de la partie 2");
    }

    @FXML
    void handleButtonChargement3() {
        System.out.println("Chargement de la partie 3");
    }

    @FXML
    void handleButtonChargement4() {
        System.out.println("Chargement de la partie 4");
    }

    @FXML
    void handleButtonChargement5() {
        System.out.println("Chargement de la partie 5");
    }

    @FXML
    void handleButtonRetourMenu() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("MenuMain.fxml"));
        MainLoader.autoResizeToResolution(anchorPane);
        MainLoader.anchorPaneMainLoader.getChildren().setAll(anchorPane);
    }

    @FXML
    void initialize(){
        buttonChargement1.setDisable(false);
        buttonChargement2.setDisable(true);
        buttonChargement3.setDisable(true);
        buttonChargement4.setDisable(true);
        buttonChargement5.setDisable(true);
    }

}
