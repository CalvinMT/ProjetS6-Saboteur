package IHM;

import Cards.RoleCard;
import Player.Player;
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
import java.util.ArrayList;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;


public class EndShaft {

    private Moteur engine;
	    

    private ObservableList<BandeauPlayerFin> playerList = FXCollections.observableArrayList();

    
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
        BorderPane borderPaneNext = new BorderPane();
    	if (Saboteur.manche >= 3) {
    		borderPaneNext = FXMLLoader.load(getClass().getResource("EndGame.fxml"));
    	}
    	else {
    		borderPaneNext = FXMLLoader.load(getClass().getResource("ChoixRole.fxml"));
    	}
		borderPaneMainLoader.getChildren().setAll(borderPaneNext);

        engine.resetRole();
        Saboteur.resetMoteur(engine.getAllPlayers());

        Saboteur.getMoteur().setState(State.Game);
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

            int pepite = 0;
            int currentPlayer = engine.nbPlayer()-1;
            int gold;

            ArrayList<Integer> arrayIndex = new ArrayList<>();
            
            for(int i=0; i<engine.nbPlayer(); i++){
            	if(engine.getAllPlayers().get(currentPlayer).getRole().equals(new RoleCard("Mineur"))){
            		arrayIndex.add(i);
            	}
            }
            
            currentPlayer = arrayIndex.size()-1;
            
            for(int i=0; i<engine.nbPlayer() && Saboteur.arrayGold.size() > 0; i++){
            	
            	gold = Saboteur.goldByPlayer.get(currentPlayer);
            	Saboteur.goldByPlayer.set(arrayIndex.get(currentPlayer), gold + Saboteur.arrayGold.remove(0));
            	
            	currentPlayer--;
            	if(currentPlayer < 0){
            		currentPlayer = arrayIndex.size()-1;
            	}
            	
            }
            
            

        } else {
            TextWinners.setText("Les Saboteurs ont gagné");
            int nbSaboteur = 0;
            for(int i=0; i<engine.nbPlayer(); i++){
                if(engine.getAllPlayers().get(i).getRole() == new RoleCard("Saboteur")){
                    nbSaboteur++;
                }
            }

            int nbGiveGold;

            if(nbSaboteur == 1){
                nbGiveGold = 4;
            } else if(nbSaboteur == 2 || nbSaboteur == 3){
                nbGiveGold = 3;
            } else if(nbSaboteur == 4){
                nbGiveGold = 2;
            } else {
                nbGiveGold = 0;
            }

            int gold;

            for(int i=0; i<engine.nbPlayer(); i++){
                if(engine.getAllPlayers().get(i).getRole() == new RoleCard("Saboteur")){

                    gold = Saboteur.goldByPlayer.get(i);
                    Saboteur.goldByPlayer.set(i, gold + nbGiveGold);

                }
            }
        }
        TextWinners.setFill(Paint.valueOf("FFFFFF"));

        Player player;
        String avatar;

        for(int i=0; i<engine.nbPlayer(); i++){
            player = engine.getAllPlayers().get(i);
            if(player == null){
                System.out.println("PLayer null");
            }
            avatar = player.getAvatar();
            playerList.add(new BandeauPlayerFin (tableViewListeJoueur, new ImageCell().getImageView(avatar), player.getPlayerName(), (player.getRole()).toString()));//Pour chaque joueur
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