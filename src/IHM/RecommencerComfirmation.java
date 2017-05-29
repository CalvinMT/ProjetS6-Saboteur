package IHM;

import Saboteur.Saboteur;
import Saboteur.Moteur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RecommencerComfirmation {

    @FXML
    private Button buttonNon;

    @FXML
    void handleButtonNon(ActionEvent event) {
        ((Stage)buttonNon.getScene().getWindow()).close();
    }

    @FXML
    void handleButtonOui(ActionEvent event) throws IOException {
        System.out.println("Réinitialisation de la partie");

        Saboteur.setMoteur(new Moteur(Saboteur.getMoteur().getAllPlayers(), ""));

        Scene scene = GameBoard.gridPaneBoard.getScene(); //buttonNon.getScene().getWindow().getScene().getWindow().getScene();
        ((Stage)buttonNon.getScene().getWindow()).close();
        ((Stage)((Stage) buttonNon.getScene().getWindow()).getOwner()).close();
        BorderPane borderPaneGameLoader = (BorderPane) scene.lookup("#borderPaneGameLoader");
        BorderPane borderPaneChoixRole = FXMLLoader.load(getClass().getResource("ChoixRole.fxml"));
        borderPaneGameLoader.getChildren().setAll(borderPaneChoixRole);
    }

}
