package IHM;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class BandeauPlayer {

    @FXML
    private ImageView imageViewAvatar;

    @FXML
    private Text textPseudo;

    @FXML
    private Button buttonSupprimer;

    @FXML
    void handleButtonSupprimer(ActionEvent event) {
        System.out.println("Supprimer pressed");
    }

}
