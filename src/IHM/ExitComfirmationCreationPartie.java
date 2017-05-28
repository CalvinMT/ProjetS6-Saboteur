package IHM;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ExitComfirmationCreationPartie{  // RETOUR AU MENU

    @FXML
    private Button buttonNon;

    @FXML
    void handleButtonNon(ActionEvent event) {
        Stage stage=(Stage)buttonNon.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleButtonOui(ActionEvent event) throws Exception {
    	MenuCreationPartie.textNumberOfPlayersInit();

        Stage stage=(Stage)buttonNon.getScene().getWindow();
        stage.close();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("MenuMain.fxml"));
        MainLoader.autoResizeToResolution(anchorPane);
        MainLoader.anchorPaneMainLoader.getChildren().setAll(anchorPane);

    }

}
