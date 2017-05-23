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

public class RecommencerComfirmation {

    @FXML
    private Button buttonNon;

    @FXML
    void handleButtonNon(ActionEvent event) {
        ((Stage)buttonNon.getScene().getWindow()).close();
    }

    @FXML
    void handleButtonOui(ActionEvent event) {
        System.out.println("Réinitialisation de la partie");

        Saboteur.setMoteur(new Moteur(Saboteur.getMoteur().getAllPlayers()));

        // TODO Renvoyer sur le choix des roles
    }

}
