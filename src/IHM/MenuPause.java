package IHM;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MenuPause {

    @FXML
    void handleButtonOption(ActionEvent event) {
        System.out.println("Button option pressed");
    }

    @FXML
    void handleButtonReprendre(ActionEvent event) {
        System.out.println("Button reprendre pressed");
    }

    @FXML
    void handleButtonRetourMenu(ActionEvent event) {
        System.out.println("Button retour menu pressed");
    }

}
