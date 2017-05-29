package IHM;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static IHM.MainLoader.anchorPaneMenuMain;
import static IHM.MainLoader.scene;

public class ExitComfirmation{  // RETOUR AU MENU

    @FXML
    private Button buttonNon;

    @FXML
    void handleButtonNon(ActionEvent event) {
        Stage stage=(Stage)buttonNon.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleButtonOui(ActionEvent event) throws Exception {

        /*Stage stage = (Stage)((Stage)((Stage) buttonNon.getScene().getWindow()).getOwner()).getOwner();         //récupérer la fenêtre correspondante au jeu
        Parent root = FXMLLoader.load(getClass().getResource("MainLoader.fxml"));
        MainLoader.anchorPaneMainLoader = (AnchorPane) root.lookup("#anchorPaneMainLoader");
        MainLoader.anchorPaneMenuMain = FXMLLoader.load(getClass().getResource("MenuMain.fxml"));
        MainLoader.autoResizeToResolution(MainLoader.anchorPaneMenuMain);
        MainLoader.anchorPaneMainLoader.getChildren().setAll(MainLoader.anchorPaneMenuMain);
        ((Stage)buttonNon.getScene().getWindow()).close();                                                      //quitter la popup de comfirmation
        ((Stage)((Stage) buttonNon.getScene().getWindow()).getOwner()).close();                                 //quitter la popup de Pause
        stage.setScene(new Scene(root));
        stage.show();*/  //OLG BUG (je laisse au cas où la nouvelle version soit pas bonne au final)

        Stage primaryStage = (Stage)((Stage)((Stage) buttonNon.getScene().getWindow()).getOwner()).getOwner();
        ((Stage)buttonNon.getScene().getWindow()).close();                                                      //quitter la popup de comfirmation
        ((Stage)((Stage) buttonNon.getScene().getWindow()).getOwner()).close();                                 //quitter la popup de Pause
        Parent parentMainMenu = FXMLLoader.load(getClass().getResource("MainLoader.fxml"));
        primaryStage.setTitle("Saboteur");
        double SCREEN_WIDTH = primaryStage.getWidth();
        double SCREEN_HEIGHT = primaryStage.getHeight();
        scene = new Scene(parentMainMenu, SCREEN_WIDTH, SCREEN_HEIGHT);
        primaryStage.setWidth(SCREEN_WIDTH);
        primaryStage.setHeight(SCREEN_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        // Load MenuMain.fxml
        MainLoader.anchorPaneMainLoader = (AnchorPane) parentMainMenu.lookup("#anchorPaneMainLoader");
        anchorPaneMenuMain = FXMLLoader.load(getClass().getResource("MenuMain.fxml"));
        MainLoader.anchorPaneMainLoader.getChildren().setAll(anchorPaneMenuMain);

        // Automatic Resizing
        MainLoader.autoResizeToResolution(anchorPaneMenuMain);

        primaryStage.show();

    }

}
