package IHM;

import Cards.*;
import Player.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

public class Bottom {
    //Joueur test
    
    Player joueur = new PlayerHuman("Test");
    RoleCard rc = new RoleCard("Mineur");
    Deck deck = new DeckGalleryAction();
    
    public Bottom(){
        joueur.assignRole(rc);
        joueur.drawCard(deck);
        
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
    private ImageView ImageViewDefausse;

    @FXML
    void handleDragCard(MouseEvent event) {

    }

    @FXML
    void handleDropCard(DragEvent event) {

    }
    
     @FXML
    public void initialize(){
        textFieldPlayerGold.setText("Or : " + Integer.toString(joueur.getGoldPoints()));
        textFieldPlayerRole.setText(joueur.toString());
        textFieldPlayerPseudo.setText(joueur.getPlayerName());
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