package IHM;

import java.io.IOException;
import java.util.ArrayList;
import Board.Couple;
import Cards.ActionCard;
import Cards.Card;
import Cards.Card.Card_t;
import Cards.GalleryCard;
import Cards.Hand;
import Cards.RepareSabotageCard;
import Saboteur.Moteur;
import Saboteur.Saboteur;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameInteract {
	
	private Moteur moteur;
	private Hand hand;
	private Card card;
	
	private int numberOfCardsInHand;
	private ImageView []cardsInHand;
	
	private int numberOfPlayers;
	
	@FXML
	BorderPane borderPaneInteract;
	@FXML
	HBox hboxGameCardsInHand;
	@FXML
	VBox vboxPlayerList;
	
	@FXML
	private void handleHBoxMouseEntered () {
			hboxGameCardsInHand.getScene().setCursor(Cursor.HAND);
	}
	
	@FXML
	private void handleHBoxMouseExited () {
		if (hboxGameCardsInHand.getScene().getCursor().equals(Cursor.HAND)) {
			hboxGameCardsInHand.getScene().setCursor(Cursor.DEFAULT);
		}
	}
	
	@FXML
	public void initialize () throws IOException {
		// Liaison Moteur IHM
		moteur = Saboteur.getMoteur();
		
		// Hand configuration
		hand = moteur.getCurrentPlayer().getPlayableCards();
        numberOfCardsInHand = hand.nbCard();
		cardsInHand = new ImageView [numberOfCardsInHand];
		for (int i=0; i < numberOfCardsInHand; i++) {
			card = hand.chooseOne_without_remove(i);
			cardsInHand[i] = new ImageView(Bottom.getImageCard(card));
			cardsInHandEvents(cardsInHand[i], card);
		}
		hboxGameCardsInHand.setPrefWidth(hboxGameCardsInHand.getPrefWidth()*numberOfCardsInHand);
		hboxGameCardsInHand.setPrefHeight(hboxGameCardsInHand.getPrefHeight()*numberOfCardsInHand);
		hboxGameCardsInHand.getChildren().addAll(cardsInHand);
		
		// Player list configuration
		numberOfPlayers = moteur.getAllPlayers().size();
		vboxPlayerList.setPrefHeight(vboxPlayerList.getPrefHeight()*numberOfPlayers);
		//vboxPlayerList.getChildren().addAll(playerList);
		
		// Center player list on center-left of the screen
		BorderPane.setMargin(vboxPlayerList, new Insets(0, 0, 0, MainLoader.scene.getWidth()-vboxPlayerList.getTranslateX()-vboxPlayerList.getPrefWidth()));
		// Center playable cards (hand) in bottom-middle of the screen
		BorderPane.setMargin(hboxGameCardsInHand, new Insets((MainLoader.scene.getHeight()-GameBoard.cardsHeight-vboxPlayerList.getPrefHeight()), 0, 0, ((MainLoader.scene.getWidth()/2)-(numberOfCardsInHand*GameBoard.cardsWidth/2))));
		
		
		// Get grid from GameBoard
		/*FXMLLoader loader = new FXMLLoader();
		loader.setLocation(GameInteract.class.getResource("GameBoard.fxml"));
		AnchorPane anchorPaneGameBoard = (AnchorPane) loader.load();
		gameBoard = loader.getController();
		MainLoader.primaryStage.getScene().setRoot(anchorPaneGameBoard);*/
	}
	
	
	
	// -------------------- ---------- --------------------
	

	
	private ArrayList<Couple> possiblePositions;
	
	private double mouseX;
	private double mouseY;
	private double viewCardX;
	private double viewCardY;
	
	private void cardsInHandEvents (ImageView viewCard, Card card) {
		// ---------- Mouse enters viewCard ----------
		viewCard.setOnMouseEntered(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// Brings card forward
				viewCard.setTranslateY(viewCard.getTranslateY()-25);
				// Turns on indications
				if (card.getType().equals(Card_t.gallery)) {
					possiblePositions = moteur.getBoard().getPossiblePositions((GalleryCard) card);
					possiblePositions.stream().forEach(position -> {
						ImageView imageView = new ImageView("ressources/carte_indication.png");
						GameBoard.gridPaneBoard.add(imageView, (position.getColumn() + GameBoard.startCardX), (position.getLine() + GameBoard.startCardY));
						imageView.setOnMouseDragOver(new EventHandler <MouseEvent>(){
							@Override
							public void handle(MouseEvent event) {
								GameBoard.gridPaneBoard.add(viewCard, (position.getColumn() + GameBoard.startCardX), (position.getLine() + GameBoard.startCardY));
							}
						});
						imageView.setOnMouseDragExited(new EventHandler <MouseEvent>(){
							@Override
							public void handle(MouseEvent event) {
								
							}
						});
					});
				}
				// TODO
				/*
				else if (card.getType().equals(Card_t.action)) {
					turn_on_indications_on_player_list
				}
				*/
			}
		});
		// ---------- Mouse exits viewCard ----------
		viewCard.setOnMouseExited(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// Puts back card into place
				viewCard.setTranslateY(viewCard.getTranslateY()+25);
				// Turns off indications
				possiblePositions.stream().forEach(position -> {
					Node node = getNodeFromGridPane(GameBoard.gridPaneBoard, (position.getColumn() + GameBoard.startCardX), (position.getLine() + GameBoard.startCardY));
					GameBoard.gridPaneBoard.getChildren().remove(node);
				});
			}
		});
		// ---------- Mouse presses viewCard ----------
		viewCard.setOnMousePressed(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				hboxGameCardsInHand.getScene().setCursor(Cursor.CLOSED_HAND);
				mouseX = event.getSceneX();
				mouseY = event.getSceneY();
				viewCardX = ((ImageView)(event.getSource())).getTranslateX();
				viewCardY = ((ImageView)(event.getSource())).getTranslateY();
			}
		});
		// ---------- Mouse drags viewCard ----------
		viewCard.setOnMouseDragged(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				double mouseOffSetX = event.getSceneX() - mouseX;
				double mouseOffSetY = event.getSceneY() - mouseY;
				viewCard.setTranslateX(viewCardX + mouseOffSetX);
				viewCard.setTranslateY(viewCardY + mouseOffSetY);
				// TODO
				/*if (card.getType().equals(Card_t.gallery)  &&  card_can_go_into_grid) {
					card_sticks_to_grid
				}*/
			}
		});
		// ---------- Mouse releases viewCard ----------
		viewCard.setOnMouseReleased(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				hboxGameCardsInHand.getScene().setCursor(Cursor.DEFAULT);
				// TODO
				/*if (card_can_go_into_grid) {
					card_goes_into_grid
				}
				else {
					card_returns_to_hand
				}
				turn_off_indications
				*/
			}
		});
	}
	
	
	
	private Node getNodeFromGridPane (GridPane gridPane, int col, int row) {
	    for (Node node : gridPane.getChildren()) {
	    	if (GridPane.getColumnIndex(node) != null  &&  GridPane.getRowIndex(node) != null) {
		        if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
		            return node;
		        }
	    	}
	    }
	    return null;
	}
	
	
	
	/*private Image getImageCard (Card c) {
        Image image = null;
        switch(c.getType()) {
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
    }*/
	
}
