package IHM;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class QuitterComfirmation {

    @FXML
    private Button buttonNon;

    @FXML
    void handleButtonNon() {
        Stage stage=(Stage)buttonNon.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleButtonOui() {
        Stage stage=(Stage)buttonNon.getScene().getWindow();
        stage.close();
        ((Stage)stage.getOwner()).close();
        Platform.exit();
    }

}