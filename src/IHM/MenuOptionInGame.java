package IHM;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuOptionInGame {

    @FXML
    private Slider sliderMusique;

    @FXML
    private Slider sliderEffets;

    @FXML
    void handleRetour(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MenuPause.fxml"));
        Stage stage = (Stage) sliderEffets.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void handleSliderEffect(MouseEvent event) {
        System.out.println("Modification du volume des effets");
    }

    @FXML
    void handleSliderMusique(MouseEvent event) {
        System.out.println("Modification du volume de la musique");
    }

}
