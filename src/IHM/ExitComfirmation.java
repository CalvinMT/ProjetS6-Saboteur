package IHM;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ExitComfirmation{  // RETOUR AU MENU

    @FXML
    private Button buttonNon;

    @FXML
    void handleButtonNon(ActionEvent event) {
        Stage stage=(Stage)buttonNon.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleButtonOui(ActionEvent event) throws Exception { //FIXME - Fonctionne pour le menu de création de partie mais pas in game

        Stage stage = (Stage)((Stage)((Stage) buttonNon.getScene().getWindow()).getOwner()).getOwner();         //récupérer la fenêtre correspondante au jeu
        Parent root = FXMLLoader.load(getClass().getResource("MainLoader.fxml"));
        MainLoader.anchorPaneMainLoader = (AnchorPane) root.lookup("#anchorPaneMainLoader");
        MainLoader.anchorPaneMenuMain = FXMLLoader.load(getClass().getResource("MenuMain.fxml"));
        MainLoader.autoResizeToResolution(MainLoader.anchorPaneMenuMain);
        MainLoader.anchorPaneMainLoader.getChildren().setAll(MainLoader.anchorPaneMenuMain);
        ((Stage)buttonNon.getScene().getWindow()).close();                                                      //quitter la popup de comfirmation
        ((Stage)((Stage) buttonNon.getScene().getWindow()).getOwner()).close();                                 //quitter la popup de Pause
        stage.setScene(new Scene(root));
        stage.show();

    }

}
