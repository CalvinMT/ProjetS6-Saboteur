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

import javafx.scene.paint.Paint;
import javafx.scene.text.Text;


public class EndShaft {

    private Moteur engine;
	    

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
    private AnchorPane anchorPaneEndShaft;
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
    
    
    

    @FXML
    void handleButtonMancheSuivante(ActionEvent event) throws IOException {
        Scene scene = (Scene) anchorPaneEndShaft.getScene();
        BorderPane borderPaneMainLoader = (BorderPane) scene.lookup("#borderPaneMainLoader");
        BorderPane borderPaneGameLoader = FXMLLoader.load(getClass().getResource("GameLoader.fxml"));
        borderPaneMainLoader.getChildren().setAll(borderPaneGameLoader);

        engine.setState(State.Game);



        // @TheSpyGeek TODO - réinitialisation du moteur pour la nouvelle manche
    }

    
    

    @FXML
    public void initialize(){

        engine = Saboteur.getMoteur();

        tableViewListeJoueur.setItems(playerList);
        columnAvatar.setStyle( "-fx-alignment: CENTER;");
        columnPseudo.setStyle( "-fx-alignment: CENTER-LEFT;");
        columnRole.setStyle( "-fx-alignment: CENTER-LEFT");
        columnAvatar.setCellValueFactory(new PropertyValueFactory<BandeauPlayerFin, ImageView>("Avatar"));
        columnPseudo.setCellValueFactory(new PropertyValueFactory<BandeauPlayerFin, String>("Pseudo"));
        columnRole.setCellValueFactory(new PropertyValueFactory<BandeauPlayerFin, String>("Role"));

        if(engine.getBoard().goldReached()){
            TextWinners.setText("Les Mineurs ont gagné");
        } else {
            TextWinners.setText("Les Saboteurs ont gagné");
        }
        TextWinners.setFill(Paint.valueOf("FFFFFF"));


        for(int i=0; i<engine.nbPlayer(); i++){

            playerList.add(new BandeauPlayerFin (tableViewListeJoueur, new ImageCell().getImageView(engine.getAllPlayers().get(i).getAvatar()), engine.getAllPlayers().get(i).getPlayerName(), (engine.getAllPlayers().get(i).getRole()).toString()));//Pour chaque joueur
        }

        /*playerList.add(new BandeauPlayerFin (tableViewListeJoueur, new ImageCell().getImageView(joueur2.getAvatar()), joueur2.getPlayerName(), (joueur2.getRole()).toString()));
        playerList.add(new BandeauPlayerFin (tableViewListeJoueur, new ImageCell().getImageView(joueur3.getAvatar()), joueur3.getPlayerName(), (joueur3.getRole()).toString()));
        playerList.add(new BandeauPlayerFin (tableViewListeJoueur, new ImageCell().getImageView(joueur4.getAvatar()), joueur4.getPlayerName(), (joueur4.getRole()).toString()));
        playerList.add(new BandeauPlayerFin (tableViewListeJoueur, new ImageCell().getImageView(joueur5.getAvatar()), joueur5.getPlayerName(), (joueur5.getRole()).toString()));
        playerList.add(new BandeauPlayerFin (tableViewListeJoueur, new ImageCell().getImageView(joueur6.getAvatar()), joueur6.getPlayerName(), (joueur6.getRole()).toString()));
     
        */
        

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