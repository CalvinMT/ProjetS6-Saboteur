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

public class Bottom {
    //Joueur test
    
    Player joueur = new PlayerHuman(1);
    RoleCard rc = new RoleCard("Mineur");
    RepareSabotageCard Sabo = new RepareSabotageCard("Sabotage", Lantern);
    Deck deck = new DeckGalleryAction();
    
    
    public Bottom(){
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
        textFieldPlayerGold.setText("Or : " + Integer.toString(joueur.getGoldPoints()));
        textFieldPlayerRole.setText(joueur.toString());
        textFieldPlayerPseudo.setText(joueur.getPlayerName());
        imageViewPlayerAvatar.setImage(new Image("ressources/avatar_anonyme.png"));
        if((joueur.getAttributeCards()).containsTools(Pickaxe)){
            imageViewplayerPick.setImage(new Image("ressources/pickaxe_detruite.png"));
        }else{
            imageViewplayerPick.setImage(new Image("ressources/pickaxe.png"));
        }
        if((joueur.getAttributeCards()).containsTools(Lantern)){
            imageViewplayerLantern.setImage(new Image("ressources/lanterne_detruite.png"));
        }else{
            imageViewplayerLantern.setImage(new Image("ressources/lanterne.png"));
        }
        if((joueur.getAttributeCards()).containsTools(Wagon)){
            imageViewplayerKart.setImage(new Image("ressources/wagon_detruite.png"));
        }else{
            imageViewplayerKart.setImage(new Image("ressources/wagon.png"));
        }
        System.out.println(joueur.lookAtCard(0).toString());
        System.out.println(joueur.lookAtCard(1).toString());
        System.out.println(joueur.lookAtCard(2).toString());
        System.out.println(joueur.lookAtCard(3).toString());
        System.out.println(joueur.lookAtCard(4).toString());
        System.out.println(joueur.lookAtCard(5).toString());
        imageViewplayerCard1.setImage(getImageCard(joueur.lookAtCard(0)));
        imageViewplayerCard2.setImage(getImageCard(joueur.lookAtCard(1)));
        imageViewplayerCard3.setImage(getImageCard(joueur.lookAtCard(2)));
        imageViewplayerCard4.setImage(getImageCard(joueur.lookAtCard(3)));
        imageViewplayerCard5.setImage(getImageCard(joueur.lookAtCard(4)));
        imageViewplayerCard6.setImage(getImageCard(joueur.lookAtCard(5)));
        imageViewDraw.setImage(new Image("ressources/dos_carte_action.jpg"));
        imageViewDefausse.setImage(new Image("ressources/defausse.jpg"));
        
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
    
    
    static public Image getImageCard(Card c){
        Image image = null;
        switch(c.getType()){
            case action:
                switch(((ActionCard)c).getAction()){
                    case Sabotage:                        
                        switch(((RepareSabotageCard)c).getTool()){
                            case Pickaxe:
                                image = (new Image("ressources/carte_brise_pioche.jpg"));
                                break;
                            case Lantern:
                                image = (new Image("ressources/carte_brise_lanterne.jpg"));
                                break;
                            case Wagon:
                                image = (new Image("ressources/carte_brise_chariot.jpg"));
                                break;
                        }
                        break;                          
                    case Map:
                        image = (new Image("ressources/carte_plan_secret.jpg"));
                        break;
                    case Repare:
                        switch(((RepareSabotageCard)c).nbTools()){
                            case 1 :
                                switch(((RepareSabotageCard)c).getTool()){
                                    case Pickaxe:
                                        image = (new Image("ressources/carte_repare_pioche.jpg"));
                                        break;
                                    case Lantern:
                                        image = (new Image("ressources/carte_repare_lanterne.jpg"));
                                        break;
                                    case Wagon:
                                        image = (new Image("ressources/carte_repare_chariot.jpg"));
                                        break;                           
                                }
                            break;
                            case 2 :
                                switch(((RepareSabotageCard)c).getTool()){
                                    case Pickaxe:
                                        image = (new Image("ressources/carte_repare_pioche_lanterne.jpg"));
                                        break;
                                    case Lantern:
                                        image = (new Image("ressources/carte_repare_lanterne_chariot.jpg"));
                                        break;
                                    case Wagon:
                                        image = (new Image("ressources/carte_repare_chariot_pioche.jpg"));
                                        break;
                                }
                            break;
                            default:
                                image = null;
                        }
                        break;
                    case Crumbing:
                        image = (new Image("ressources/carte_eboulement.jpg"));
                        break;
                    default:
                        image = null;
                }
                break;
            case gallery:
                if(((GalleryCard)c).canHasCenter()){
                    if(((GalleryCard)c).canHasNorth()){
                        if(((GalleryCard)c).canHasWest()){
                            if(((GalleryCard)c).canHasSouth()){
                                if(((GalleryCard)c).canHasEast()){
                                    image = (new Image("ressources/NSEO_C.png"));
                                }else{//Sans East
                                    image = (new Image("ressources/NSO_C.png"));
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                    image = (new Image("ressources/NEO_C.png"));
                                }else{//Sans East
                                    image = (new Image("ressources/NO_C.png"));
                                }
                            }
                        }else{//Sans West
                            if(((GalleryCard)c).canHasSouth()){
                                if(((GalleryCard)c).canHasEast()){
                                    image = (new Image("ressources/NSO_C.png"));//NSE
                                }else{//Sans East
                                    image = (new Image("ressources/NS_C.png"));
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                    image = (new Image("ressources/NE_C.png"));
                                }else{//Sans East
                                    image = null;//N
                                }
                            }
                        }
                    }else{//Sans North
                        if(((GalleryCard)c).canHasWest()){
                            if(((GalleryCard)c).canHasSouth()){
                                if(((GalleryCard)c).canHasEast()){
                                    image = (new Image("ressources/NEO_C.png"));//SEO
                                }else{//Sans East
                                    image = (new Image("ressources/NE_C.png"));//SO
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                    image = (new Image("ressources/EO_C.png"));
                                }else{//Sans East
                                    image = null;//O
                                }
                            }
                        }else{//Sans West
                            if(((GalleryCard)c).canHasSouth()){
                                if(((GalleryCard)c).canHasEast()){
                                    image = (new Image("ressources/NO_C.png"));//SE
                                }else{//Sans East
                                    image = null;//S
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                    image = null;//E
                                }else{//Sans East
                                    image = null;//rien
                                }
                            }
                        }
                        
                    }
                }else{//Sans Center
                    if(((GalleryCard)c).canHasNorth()){
                        if(((GalleryCard)c).canHasWest()){
                            if(((GalleryCard)c).canHasSouth()){
                                if(((GalleryCard)c).canHasEast()){
                                    image = (new Image("ressources/NSEO_NC.png"));
                                }else{//Sans East
                                    image = (new Image("ressources/NSO_NC.png"));
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                    image = (new Image("ressources/NEO_NC.png"));
                                }else{
                                    image = (new Image("ressources/NO_NC.png"));
                                }
                            }
                        }else{//Sans West
                            if(((GalleryCard)c).canHasSouth()){
                                if(((GalleryCard)c).canHasEast()){
                                    image = (new Image("ressources/NSO_NC.png"));//NSE
                                }else{//Sans East
                                    image = (new Image("ressources/NS_NC.png"));
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                    image = (new Image("ressources/SO_NC.png"));
                                }else{//Sans East
                                    image = (new Image("ressources/N_NC.png"));//N
                                }
                            }
                        }
                    }else{//Sans North
                        if(((GalleryCard)c).canHasWest()){
                            if(((GalleryCard)c).canHasSouth()){
                                if(((GalleryCard)c).canHasEast()){
                                    image = (new Image("ressources/NEO_NC.png"));//SEO
                                }else{//Sans East
                                    image = (new Image("ressources/SO_NC.png"));//SO
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                    image = (new Image("ressources/EO_NC.png"));
                                }else{//Sans East
                                    image = (new Image("ressources/O_NC.png"));//O
                                }
                            }
                        }else{//Sans West
                            if(((GalleryCard)c).canHasSouth()){
                                if(((GalleryCard)c).canHasEast()){
                                    image = (new Image("ressources/NO_NC.png"));//SE
                                }else{//Sans East
                                    image = (new Image("ressources/N_NC.png"));//S
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                    image = (new Image("ressources/O_NC.png"));//E
                                }else{//Sans East
                                    image = null;//rien
                                }
                            }
                        }
                    }
                }
            break;
            default:
                image = null;
        }
        return image;
    }
    
	
}