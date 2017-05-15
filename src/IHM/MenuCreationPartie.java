package IHM;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;


public class MenuCreationPartie {
	
    private ObservableList<String> avatarList = FXCollections.observableArrayList("avatar_anonyme", "avatar_test");

    private ObservableList<String> typeList = FXCollections.observableArrayList("Joueur", "Ordinateur");

    private ObservableList<BandeauPlayer> playerList = FXCollections.observableArrayList();

    @FXML
    private AnchorPane anchorPaneMenuCreationPartie;

    @FXML
    private ComboBox<String> comboBoxAvatar;

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
		String avatar = comboBoxAvatar.getValue();
		playerList.add(new BandeauPlayer(tableViewListeJoueur, new ImageCell().getImageView(avatar), pseudo));
		
		if (playerList.size() >= 3) {
			buttonPlay.setDisable(false);
		}
    }

    @FXML
    void handleButtonPlay(ActionEvent event) throws IOException {
    	Scene scene = (Scene) anchorPaneMenuCreationPartie.getScene();
		BorderPane borderPaneMainLoader = (BorderPane) scene.lookup("#borderPaneMainLoader");
		BorderPane borderPaneGameLoader = FXMLLoader.load(getClass().getResource("GameLoader.fxml"));
        borderPaneMainLoader.getChildren().setAll(borderPaneGameLoader);
    }

    @FXML
    void handleButtonRetourMenu(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("MenuMain.fxml"));
        anchorPaneMenuCreationPartie.getChildren().setAll(anchorPane);
    }

    @FXML
    public void initialize(){
        buttonPlay.setDisable(true);
        comboBoxAvatar.setValue(avatarList.get(0));
        comboBoxAvatar.setItems(avatarList);
        comboBoxAvatar.setCellFactory(listview -> new ImageCell());
        comboBoxAvatar.setButtonCell(new ImageCell());
        comboBoxType.setValue(typeList.get(0));
        comboBoxType.setItems(typeList);
        tableViewListeJoueur.setItems(playerList);
        columnAvatar.setStyle( "-fx-alignment: CENTER;");
        columnPseudo.setStyle( "-fx-alignment: CENTER-LEFT;");
        columnDelete.setStyle( "-fx-alignment: CENTER;");
        columnAvatar.setCellValueFactory(new PropertyValueFactory<BandeauPlayer, ImageView>("Avatar"));
        columnPseudo.setCellValueFactory(new PropertyValueFactory<BandeauPlayer, String>("Pseudo"));
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
			switch (imageName) {
				case "avatar_anonyme":
					imageView = new ImageView(new Image("ressources/" + imageName + ".png"));
					break;
				case "avatar_test":
					imageView = new ImageView(new Image("ressources/" + imageName + ".png"));
					break;
				default:
					imageName = null;
			}
			if (!imageView.equals(null)) {
			imageView.setFitWidth(70);
			imageView.setFitHeight(70);
			}
			return imageView;
		}
	
	}
    
}
