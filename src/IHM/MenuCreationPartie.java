package IHM;

import Saboteur.Lobby;
import Saboteur.Saboteur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MenuCreationPartie {

    private ObservableList<String> typeList = FXCollections.observableArrayList("Ordinateur", "Joueur");

    private Lobby lobby = new Lobby();

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
    private ScrollPane scrollPaneListeJoueur;

    @FXML
    private Button buttonPlay;

    @FXML
    void handleButtonAjouter(ActionEvent event) {
        String name = textFieldPseudo.getText();
        String type = comboBoxType.getValue();

        if(name == null || name.equals("")){
            if(type.equals("Ordinateur")){
                name = "Ordinateur";
            } else if(type.equals("Joueur")){
                name = "Joueur";
            }
        }
        lobby.addPlayer(name, type);

        System.out.println(lobby);

        if(this.lobby.enoughPlayer()){
            buttonPlay.setDisable(false);
        }

    }

    @FXML
    void handleButtonPlay(ActionEvent event) {

        if(this.lobby.enoughPlayer() && !this.lobby.tooMuchPlayer()){
            Saboteur.initMoteur(this.lobby.getArrayPlayer());
        }
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
