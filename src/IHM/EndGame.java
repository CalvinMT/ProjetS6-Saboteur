
package IHM;

import Cards.RoleCard;
import Player.Player;
import Player.PlayerHuman;
import Saboteur.Moteur;
import Saboteur.Saboteur;
import Saboteur.Moteur.State;
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

    private Moteur engine;
	    

    private ObservableList<BandeauPlayerFinGame> playerList = FXCollections.observableArrayList();
    
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
    
    
    
    

    @FXML
    void handleButtonMancheSuivante(ActionEvent event) throws IOException {
        Scene scene = (Scene) anchorPaneEndGame.getScene();
        BorderPane borderPaneGameLoader = (BorderPane) scene.lookup("#borderPaneMainLoader");
        BorderPane borderPaneMainLoader = FXMLLoader.load(getClass().getResource("MainLoader.fxml"));
        borderPaneGameLoader.getChildren().setAll(borderPaneMainLoader);
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("MenuMain.fxml"));
        borderPaneMainLoader.setCenter(anchorPane);
        
        
        engine.resetRole();
        Saboteur.resetMoteur(engine.getAllPlayers());

        Saboteur.getMoteur().setState(State.Waiting);
    }

    
    

    @FXML
    public void initialize(){
    	engine = Saboteur.getMoteur();
    	
        tableViewListeJoueur.setItems(playerList);
        columnAvatar.setStyle( "-fx-alignment: CENTER;");
        columnPseudo.setStyle( "-fx-alignment: CENTER-LEFT;");
        columnRole.setStyle( "-fx-alignment: CENTER-LEFT");
        columnAvatar.setCellValueFactory(new PropertyValueFactory<BandeauPlayerFinGame, ImageView>("Avatar"));
        columnPseudo.setCellValueFactory(new PropertyValueFactory<BandeauPlayerFinGame, String>("Pseudo"));
        columnOr.setCellValueFactory(new PropertyValueFactory<BandeauPlayerFinGame, String>("Or"));
        columnRole.setCellValueFactory(new PropertyValueFactory<BandeauPlayerFinGame, String>("Role"));
        TextWinner.setText("Le vainqueur à gagné");// replacer par le winner     
        Player player;
        String avatar;

        for(int i=0; i<engine.nbPlayer(); i++){
            player = engine.getAllPlayers().get(i);
            if(player == null){
                System.out.println("PLayer null");
            }
            avatar = player.getAvatar();
            playerList.add(new BandeauPlayerFinGame (tableViewListeJoueur, new ImageCell().getImageView(avatar), player.getPlayerName(), Saboteur.goldByPlayer.get(i).toString(), (player.getRole()).toString()));//Pour chaque joueur
        }
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