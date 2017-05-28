package IHM;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChargementPartie {

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
    void handleButtonChargement1() {
        System.out.println("Chargement de la partie 1");
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
        buttonChargement1.setDisable(true);
        buttonChargement2.setDisable(true);
        buttonChargement3.setDisable(true);
        buttonChargement4.setDisable(true);
        buttonChargement5.setDisable(true);
    }

}