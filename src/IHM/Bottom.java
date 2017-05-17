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
    
    Player joueur = new PlayerHuman(1);
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
        imageViewPlayerAvatar.setImage(new Image("ressources/avatar_anonyme.png"));
        
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
    
    /*
    private ImageView getImageCard(Card c){
        ImageView imageView = null;
        switch(c.getType()){
            case action:
                switch(((ActionCard)c).getAction()){
                    case Sabotage:
                        switch(((RepareSabotageCard)c).nbTools()){
                            case Sabotage:
                        break;
                    case Map:
                        imageView = new ImageView(new Image("ressources/carte_plan_secret.jpg"));
                        break;
                    case Repare:
                        break;
                        
                    case Crumbing:
                        imageView = new ImageView(new Image("ressources/carte_eboulement.jpgg"));
                        break;
                    default:
                        imageView = null;
                }
                break;
            case gallery:
                imageView = new ImageView(new Image("ressources/" + imageName + ".png"));
                break;
            default:
                imageView = null;
		
        }
        
        return imageView;
    }
    */
	
}