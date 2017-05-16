package IHM;

import Player.Player.Difficulty;
import Player.Player;
import Saboteur.Lobby;
import Saboteur.Saboteur;
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
	
    private ObservableList<String> avatarList = FXCollections.observableArrayList("avatar_anonyme", "avatar_test");

    private ObservableList<String> difficulteList = FXCollections.observableArrayList("Facile", "Moyen", "Difficile");

    private ObservableList<BandeauPlayer> playerList = FXCollections.observableArrayList();

    private Lobby lobby = new Lobby();

    @FXML
    private AnchorPane anchorPaneMenuCreationPartie;

    @FXML
    private ComboBox<String> comboBoxAvatar;
    @FXML
    private ComboBox<String> comboBoxDifficulteIA;
    @FXML
    private TextField textFieldPseudo;
    @FXML
    private Button buttonAjouterPlayer;
    @FXML
    private Button buttonAjouterIA;
    @FXML
    private Button buttonPlay;

    @FXML
    private TableView<BandeauPlayer> tableViewListeJoueur;
    @FXML
    private TableColumn<BandeauPlayer, ImageView> columnAvatar;
    @FXML
    private TableColumn<BandeauPlayer, String> columnPseudo;
    @FXML
    private TableColumn<BandeauPlayer, String> columnType;
    @FXML
    private TableColumn<BandeauPlayer, ComboBox<String>> columnDifficulte;
    @FXML
    private TableColumn<BandeauPlayer, Button> columnDelete;


    @FXML
    void handleButtonAjouterIA(ActionEvent event){
        String difficulty = comboBoxDifficulteIA.getValue();
        String pseudo = "IA " + difficulty;
        String type = "Ordinateur";
        String avatar = "robot_miner";

        lobby.addPlayer(playerList.size(), pseudo, Difficulty.stringToDiff(difficulty));

        playerList.add(new BandeauPlayer(tableViewListeJoueur, new ImageCell().getImageView(avatar), pseudo, type, difficulty, buttonPlay, buttonAjouterPlayer, buttonAjouterIA, lobby));

        if (playerList.size() >= 3) {
            buttonPlay.setDisable(false);
        }
        if(playerList.size()==10){
            buttonAjouterPlayer.setDisable(true);
            buttonAjouterIA.setDisable(true);
        }

        System.out.println(lobby);
    }

    @FXML
    void handleComboBoxDifficulteIA(ActionEvent event){
        System.out.println("Difficulté de l'ia");
    }

    @FXML


    void handleButtonAjouterPlayer(ActionEvent event) {
		String pseudo = textFieldPseudo.getText();
		String type = "Joueur";
		String avatar = comboBoxAvatar.getValue();

		if(pseudo == null || pseudo.equals("")){
		    pseudo = "Joueur";
        }


        lobby.addPlayer(playerList.size(), pseudo, avatar);

        playerList.add(new BandeauPlayer(tableViewListeJoueur, new ImageCell().getImageView(avatar), pseudo, type, buttonPlay, buttonAjouterPlayer, buttonAjouterIA, lobby));


		if (playerList.size() >= 3) {
			buttonPlay.setDisable(false);
		}
		if(playerList.size()>10){
		    buttonAjouterPlayer.setDisable(true);
		    buttonAjouterIA.setDisable(true);
        }

        System.out.println(lobby);

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
    public void initialize(){
        buttonPlay.setDisable(true);
        comboBoxDifficulteIA.setValue(difficulteList.get(0));
        comboBoxDifficulteIA.setItems(difficulteList);
        comboBoxAvatar.setValue(avatarList.get(0));
        comboBoxAvatar.setItems(avatarList);
        comboBoxAvatar.setCellFactory(listview -> new ImageCell());
        comboBoxAvatar.setButtonCell(new ImageCell());
        tableViewListeJoueur.setItems(playerList);
        columnAvatar.setStyle( "-fx-alignment: CENTER;");
        columnPseudo.setStyle( "-fx-alignment: CENTER-LEFT;");
        columnType.setStyle( "-fx-alignment: CENTER-LEFT");
        columnDifficulte.setStyle( "-fx-alignment: CENTER-LEFT");
        columnDelete.setStyle( "-fx-alignment: CENTER;");
        columnAvatar.setCellValueFactory(new PropertyValueFactory<BandeauPlayer, ImageView>("Avatar"));
        columnPseudo.setCellValueFactory(new PropertyValueFactory<BandeauPlayer, String>("Pseudo"));
        columnType.setCellValueFactory(new PropertyValueFactory<BandeauPlayer, String>("Type"));
        columnDifficulte.setCellValueFactory(new PropertyValueFactory<BandeauPlayer, ComboBox<String>>("Difficulte"));
        columnDelete.setCellValueFactory(new PropertyValueFactory<BandeauPlayer, Button>("ButtonDelete"));
    }

	// A custom ListCell that displays an ImageView
	static class ImageCell extends ListCell<String> {
		Label label;
		@Override
		protected void updateItem (String item, boolean empty) {
			super.updateItem(item, empty);
			if (item == null || empty) {
				setItem(null);
				setGraphic(null);
			}
			else {
				ImageView image = getImageView(item);
				label = new Label("",image);
				setGraphic(label);
			}
		}

		private ImageView getImageView(String imageName) {
			ImageView imageView = null;
            imageView = new ImageView(new Image("ressources/" + imageName + ".png"));
			if (!imageView.equals(null)) {
			    imageView.setFitWidth(70);
			    imageView.setFitHeight(70);
			}
			return imageView;
		}
	
	}
    
}
