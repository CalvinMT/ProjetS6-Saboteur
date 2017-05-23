package IHM;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
        System.out.println("Réinitialisation de la partie");    // TODO RECOMMENCER
    }

}
