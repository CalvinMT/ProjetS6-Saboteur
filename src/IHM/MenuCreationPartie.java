package IHM;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;


public class MenuCreationPartie {
	
    private ObservableList<ImageView> avatarList = FXCollections.observableArrayList(new ImageView(new Image("ressources/avatar_anonyme.jpg")), new ImageView(new Image("ressources/avatar_test.jpg")));

    private ObservableList<String> typeList = FXCollections.observableArrayList("Joueur", "Ordinateur");

    private ObservableList<BandeauPlayer> playerList = FXCollections.observableArrayList();

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
    private TableView<BandeauPlayer> tableViewListeJoueur;
    @FXML
    private TableColumn<BandeauPlayer, ImageView> columnAvatar;
    @FXML
    private TableColumn<BandeauPlayer, String> columnPseudo;
    @FXML
    private TableColumn<BandeauPlayer, ?> columnType;
    @FXML
    private TableColumn<BandeauPlayer, Button> columnDelete;

    @FXML
    private Button buttonPlay;

    @FXML
    void handleButtonAjouter(ActionEvent event) {
		String pseudo = textFieldPseudo.getText();
		ImageView avatar = comboBoxAvatar.getValue();
		playerList.add(new BandeauPlayer(avatar, pseudo));
		
		if (playerList.size() >= 3) {
			buttonPlay.setDisable(false);
		}
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
    }

    @FXML
    void handleComboBoxType(ActionEvent event) {
    }

    @FXML
    public void initialize(){
        buttonPlay.setDisable(true);
        for (int index = 0; index<avatarList.size(); index++) {
            avatarList.get(index).setFitWidth(70);
            avatarList.get(index).setFitHeight(70);
        }
        comboBoxAvatar.setValue(avatarList.get(0));
        comboBoxAvatar.setItems(avatarList);
        comboBoxType.setValue(typeList.get(0));
        comboBoxType.setItems(typeList);
        tableViewListeJoueur.setItems(playerList);
        columnAvatar.setCellValueFactory(new PropertyValueFactory<BandeauPlayer, ImageView>("Avatar"));
        columnPseudo.setCellValueFactory(new PropertyValueFactory<BandeauPlayer, String>("Pseudo"));
    }

}
