
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


public class EndGame {
	    

    private ObservableList<BandeauPlayerFinGame> playerList = FXCollections.observableArrayList();

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
    private AnchorPane anchorPaneEndGame;
    @FXML
    private TableView<BandeauPlayerFinGame> tableViewListeJoueur;
     @FXML
    private Text TextWinner;
    @FXML
    private Button ButtonRetourMenu;
    @FXML
    private TableColumn<BandeauPlayerFinGame, ImageView> columnAvatar;
    @FXML
    private TableColumn<BandeauPlayerFinGame, String> columnPseudo;
    @FXML
    private TableColumn<BandeauPlayerFinGame, String> columnOr;
    @FXML
    private TableColumn<BandeauPlayerFinGame, String> columnRole;
    
    public EndGame(){//tests à remplacer par le moteur
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
        Scene scene = (Scene) anchorPaneEndGame.getScene();
        BorderPane borderPaneMainLoader = (BorderPane) scene.lookup("#borderPaneMainLoader");
        BorderPane borderPaneGameLoader = FXMLLoader.load(getClass().getResource("MainLoader.fxml"));
        borderPaneMainLoader.getChildren().setAll(borderPaneGameLoader);
    
    }

    
    

    @FXML
    public void initialize(){
        tableViewListeJoueur.setItems(playerList);
        columnAvatar.setStyle( "-fx-alignment: CENTER;");
        columnPseudo.setStyle( "-fx-alignment: CENTER-LEFT;");
        columnRole.setStyle( "-fx-alignment: CENTER-LEFT");
        columnAvatar.setCellValueFactory(new PropertyValueFactory<BandeauPlayerFinGame, ImageView>("Avatar"));
        columnPseudo.setCellValueFactory(new PropertyValueFactory<BandeauPlayerFinGame, String>("Pseudo"));
        columnOr.setCellValueFactory(new PropertyValueFactory<BandeauPlayerFinGame, String>("Or"));
        columnRole.setCellValueFactory(new PropertyValueFactory<BandeauPlayerFinGame, String>("Role"));
        TextWinner.setText("Le vainqueur à gagné");// replacer par le winner
        //Pour chaque joueur: (Role à remplacer par leur role au cours des manches
        playerList.add(new BandeauPlayerFinGame (tableViewListeJoueur, new ImageCell().getImageView(joueur.getAvatar()), joueur.getPlayerName(), (Integer.toString(joueur.getGoldPoints()) + " pépites"), (joueur.getRole()).toString()));
        playerList.add(new BandeauPlayerFinGame (tableViewListeJoueur, new ImageCell().getImageView(joueur2.getAvatar()), joueur2.getPlayerName(), (Integer.toString(joueur.getGoldPoints()) + " pépites"), (joueur2.getRole()).toString()));
        playerList.add(new BandeauPlayerFinGame (tableViewListeJoueur, new ImageCell().getImageView(joueur3.getAvatar()), joueur3.getPlayerName(), (Integer.toString(joueur.getGoldPoints()) + " pépites"), (joueur3.getRole()).toString()));
        playerList.add(new BandeauPlayerFinGame (tableViewListeJoueur, new ImageCell().getImageView(joueur4.getAvatar()), joueur4.getPlayerName(), (Integer.toString(joueur.getGoldPoints()) + " pépites"), (joueur4.getRole()).toString()));
        playerList.add(new BandeauPlayerFinGame (tableViewListeJoueur, new ImageCell().getImageView(joueur5.getAvatar()), joueur5.getPlayerName(), (Integer.toString(joueur.getGoldPoints()) + " pépites"), (joueur5.getRole()).toString()));
        playerList.add(new BandeauPlayerFinGame (tableViewListeJoueur, new ImageCell().getImageView(joueur6.getAvatar()), joueur6.getPlayerName(), (Integer.toString(joueur.getGoldPoints()) + " pépites"), (joueur6.getRole()).toString()));
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