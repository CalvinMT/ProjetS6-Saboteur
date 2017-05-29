package IHM;

import Cards.RoleCard;
import Player.Player;
import Player.PlayerHuman;
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
import javafx.scene.text.Text;


public class MenuFinManche {
	    

    private ObservableList<BandeauPlayerFin> playerList = FXCollections.observableArrayList();

    //tests
    Player joueur = new PlayerHuman(1);
    Player joueur2 = new PlayerHuman(2);
    Player joueur3 = new PlayerHuman(3);
    Player joueur4 = new PlayerHuman(4);
    Player joueur5 = new PlayerHuman(5);
    Player joueur6 = new PlayerHuman(6);
    RoleCard mine = new RoleCard("Mineur");
    RoleCard sabo = new RoleCard("Saboteur");
    
    @FXML
    private AnchorPane anchorPaneMenuFinManche;
    @FXML
    private TableView<BandeauPlayerFin> tableViewListeJoueur;
     @FXML
    private Text TextWinners;
    @FXML
    private Button ButtonMancheSuivante;
    @FXML
    private TableColumn<BandeauPlayerFin, ImageView> columnAvatar;
    @FXML
    private TableColumn<BandeauPlayerFin, String> columnPseudo;
    @FXML
    private TableColumn<BandeauPlayerFin, String> columnRole;
    
    public MenuFinManche(){//tests
        joueur.setAvatar("avatar_test");
        joueur.assignRole(mine);
        joueur2.setAvatar("avatar_test");
        joueur2.assignRole(mine);
        joueur3.setAvatar("avatar_test");
        joueur3.assignRole(sabo);
        joueur4.setAvatar("avatar_test");
        joueur4.assignRole(sabo);
        joueur5.setAvatar("avatar_test");
        joueur5.assignRole(mine);
        joueur6.setAvatar("avatar_test");
        joueur6.assignRole(mine);
    }
    
    
    
    

    @FXML
    void handleButtonMancheSuivante(ActionEvent event) throws IOException {
        Scene scene = (Scene) anchorPaneMenuFinManche.getScene();
        BorderPane borderPaneMainLoader = (BorderPane) scene.lookup("#borderPaneMainLoader");
        BorderPane borderPaneGameLoader = FXMLLoader.load(getClass().getResource("GameLoader.fxml"));
        borderPaneMainLoader.getChildren().setAll(borderPaneGameLoader);
    
    }

    
    

    @FXML
    public void initialize(){
        tableViewListeJoueur.setItems(playerList);
        columnAvatar.setStyle( "-fx-alignment: CENTER;");
        columnPseudo.setStyle( "-fx-alignment: CENTER-LEFT;");
        columnRole.setStyle( "-fx-alignment: CENTER-LEFT");
        columnAvatar.setCellValueFactory(new PropertyValueFactory<BandeauPlayerFin, ImageView>("Avatar"));
        columnPseudo.setCellValueFactory(new PropertyValueFactory<BandeauPlayerFin, String>("Pseudo"));
        columnRole.setCellValueFactory(new PropertyValueFactory<BandeauPlayerFin, String>("Role"));
        TextWinners.setText("Les " + "Saboteurs" + "ont gagné");
        playerList.add(new BandeauPlayerFin (tableViewListeJoueur, new ImageCell().getImageView(joueur.getAvatar()), joueur.getPlayerName(), (joueur.getRole()).toString()));
     
        
        

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