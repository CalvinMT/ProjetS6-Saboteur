package IHM;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MenuCreationPartie {

    private ObservableList<String> typeList = FXCollections.observableArrayList("Ordinateur", "Joueur");

    @FXML
    private AnchorPane anchorPaneMenuCreationPartie;

    @FXML
    private ComboBox<ImageView> comboBoxAvatar;

    @FXML
    private TextField textFieldPseudo;

    @FXML
    private ComboBox<String> comboBoxType;

    @FXML
    private Button buttonAjouter;

    @FXML
    private ListView listViewListeJoueur;

    @FXML
    private Button buttonPlay;

    @FXML
    void handleButtonAjouter(ActionEvent event) {
        System.out.println("Ajouter pressed");
    }

    @FXML
    void handleButtonPlay(ActionEvent event) {
        System.out.println("Boutton Jouer pressed");
    }

    @FXML
    void handleButtonRetourMenu(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        anchorPaneMenuCreationPartie.getChildren().setAll(anchorPane);
    }

    @FXML
    void handleComboBoxAvatar(ActionEvent event) {
        System.out.println("Combo Box avatar used");
    }

    @FXML
    void handleComboBoxType(ActionEvent event) {
        System.out.println("Combo Box type used");
    }

    @FXML
    public void initialize(){
        buttonPlay.setDisable(true);
        comboBoxType.setItems(typeList);
    }

}
