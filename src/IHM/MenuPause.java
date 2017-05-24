package IHM;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class MenuPause {

    @FXML
    private Button buttonReprendre;

    @FXML
    void handleButtonOption(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MenuOptionInGame.fxml"));
        Stage stage = (Stage) buttonReprendre.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void handleButtonReprendre(ActionEvent event) {

        Stage stage = (Stage) buttonReprendre.getScene().getWindow();
        stage.close();

    }

    @FXML
    void handleButtonRecommencer(ActionEvent event) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("RecommencerComfirmation.fxml"));
        //Stage stage = (Stage) buttonReprendre.getScene().getWindow();
        //stage.setScene(new Scene(root));
        //stage.show();
        Stage stage = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("RecommencerComfirmation.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Recommencer");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(buttonReprendre.getScene().getWindow());
            stage.showAndWait();
        }catch(Exception e){
            System.out.println("Erreur" + e);
        }
    }

    @FXML
    void handleButtonSave(ActionEvent event){
        System.out.println("Sauvegarde");    //TODO SAUVEGARDE
    }

    @FXML
    void handleButtonRetourMenu(ActionEvent event) throws IOException {

        Stage stage = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("ExitComfirmation.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Options");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(buttonReprendre.getScene().getWindow());
            stage.showAndWait();
        }catch(Exception e){
            System.out.println("Erreur" + e);
        }

    }

    @FXML
    void handleButtonQuitter(ActionEvent event){
        Stage stage = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("QuitterComfirmation.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Options");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(buttonReprendre.getScene().getWindow());
            stage.showAndWait();
        }catch(Exception e){
            System.out.println("Erreur" + e);
        }
    }

}
