package IHM;

import Cards.ActionCard;
import Cards.Card;
import Cards.GalleryCard;
import Cards.Hand;
import Cards.RepareSabotageCard;
import Saboteur.Moteur;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class GameInteract {
	
	private Moteur moteur;
	
	private Hand hand;
	
	private Card card;
	
	private int numberOfCardsInHand;
	private ImageView []cardsInHand;
	
	@FXML
	HBox hboxGameCardsInHand;
	
	@FXML
	public void initialize () {
		moteur = Saboteur.Saboteur.getMoteur();
		System.out.println(moteur);
		hand = moteur.getCurrentPlayer().getPlayableCards();
		numberOfCardsInHand = hand.nbCard();
		for (int i=0; i < numberOfCardsInHand; i++) {
			card = hand.chooseOne_without_remove(i);
			cardsInHand[i] = new ImageView(getImageCard(card));
		}
		hboxGameCardsInHand.setPrefWidth(hboxGameCardsInHand.getPrefWidth()*numberOfCardsInHand);
		hboxGameCardsInHand.setPrefHeight(hboxGameCardsInHand.getPrefHeight()*numberOfCardsInHand);
		hboxGameCardsInHand.getChildren().addAll(cardsInHand);
	}
	
	
	
	private Image getImageCard(Card c){
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
