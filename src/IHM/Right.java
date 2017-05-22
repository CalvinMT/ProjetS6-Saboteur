package IHM;

import Cards.*;
import static Cards.RepareSabotageCard.Tools.Lantern;
import static Cards.RepareSabotageCard.Tools.Pickaxe;
import static Cards.RepareSabotageCard.Tools.Wagon;
import Player.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

public class Right{
    //Joueur test
    
    Player joueur = new PlayerHuman(1);
    RoleCard rc = new RoleCard("Mineur");
    RepareSabotageCard Sabo = new RepareSabotageCard("Sabotage", Lantern);
    Deck deck = new DeckGalleryAction();
    
    
    public Right(){
        joueur.assignRole(rc);
        for(int i=0; i<6;i++){
            joueur.drawCard(deck);
        }        
        joueur.putSabotage(Sabo, joueur);
    }
    
    
    
   

    @FXML
    private ImageView imageViewplayerPick;

    @FXML
    private ImageView imageViewplayerLantern;

    @FXML
    private ImageView imageViewplayerKart;

    @FXML
    private ImageView imageViewPlayerAvatar;

    @FXML
    private TextArea textFieldPlayerGold;

    @FXML
    private TextArea textFieldPlayerRole;

    @FXML
    private TextArea textFieldPlayerPseudo;

    @FXML
    private ImageView imageViewplayerCard1;

    @FXML
    private ImageView imageViewplayerCard2;

    @FXML
    private ImageView imageViewplayerCard3;

    @FXML
    private ImageView imageViewplayerCard4;

    @FXML
    private ImageView imageViewplayerCard5;

    @FXML
    private ImageView imageViewplayerCard6;
    
    @FXML
    private ImageView imageViewDraw;

    @FXML
    private ImageView imageViewDefausse;

    @FXML
    void handleDragCard(MouseEvent event) {

    }

    @FXML
    void handleDropCard(DragEvent event) {

    }
    
     @FXML
    public void initialize(){
        
       
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