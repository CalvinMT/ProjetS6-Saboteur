package IHM;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ExitComfirmation{

    @FXML
    void handleButtonNon(ActionEvent event) {
        System.out.println("Non pressed");
    }

    @FXML
    void handleButtonOui(ActionEvent event) {
        System.out.println("oui pressed");
    }

}
