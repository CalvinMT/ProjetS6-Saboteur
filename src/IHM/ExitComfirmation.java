package IHM;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ExitComfirmation{

    @FXML
    private Button buttonNon;

    @FXML
    void handleButtonNon(ActionEvent event) {
        Stage stage=(Stage)buttonNon.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleButtonOui(ActionEvent event) throws IOException { //FIXME - Fonctionne pour le menu de création de partie mais pas in game
        Stage stage=(Stage)buttonNon.getScene().getWindow();
        stage.close();
        stage = (Stage)((Stage) stage.getOwner()).getOwner();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("MenuMain.fxml"));
        MainLoader.anchorPaneMainLoader.getChildren().setAll(anchorPane);
    }

}
