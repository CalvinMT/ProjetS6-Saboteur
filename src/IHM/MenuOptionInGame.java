package IHM;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

public class MenuOptionInGame {

    @FXML
    private Slider sliderMusique;

    @FXML
    private Slider sliderEffets;

    @FXML
    void handleRetour(ActionEvent event) {
        System.out.println("Retour pressed");
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
