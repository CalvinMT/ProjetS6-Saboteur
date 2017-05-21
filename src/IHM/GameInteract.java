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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameInteract {
	
	private Moteur moteur;
	private Hand hand;
	private Card card;
	
	private int numberOfCardsInHand;
	private GamePlayingCard []cardsInHand;
	
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
		cardsInHand = new GamePlayingCard [numberOfCardsInHand];
		hboxGameCardsInHand.setPrefWidth(hboxGameCardsInHand.getPrefWidth()*numberOfCardsInHand);
		hboxGameCardsInHand.setPrefHeight(hboxGameCardsInHand.getPrefHeight()*numberOfCardsInHand);
		for (int i=0; i < numberOfCardsInHand; i++) {
			card = hand.chooseOne_without_remove(i);
			cardsInHand[i] = getImageCard(card);
			cardsInHandEvents(cardsInHand[i].getImageView(), card, cardsInHand[i].getName());
			hboxGameCardsInHand.getChildren().add(cardsInHand[i].getImageView());
		}
		
		// Player list configuration
		numberOfPlayers = moteur.getAllPlayers().size();
		vboxPlayerList.setPrefHeight(vboxPlayerList.getPrefHeight()*numberOfPlayers);
		/*for (int i=0; i < numberOfPlayers; i++) {
			// TODO
			vboxPlayerList.getChildren().add(null);
		}*/
		//vboxPlayerList.getChildren().addAll(playerList);
		
		// Center player list on center-left of the screen
		BorderPane.setMargin(vboxPlayerList, new Insets(0, 0, 0, MainLoader.scene.getWidth()-vboxPlayerList.getTranslateX()-vboxPlayerList.getPrefWidth()));
		// Center playable cards (hand) in bottom-middle of the screen
		BorderPane.setMargin(hboxGameCardsInHand, new Insets((MainLoader.scene.getHeight()-GameBoard.cardsHeight-vboxPlayerList.getPrefHeight()), 0, 0, ((MainLoader.scene.getWidth()/2)-(numberOfCardsInHand*GameBoard.cardsWidth/2))));
		
		borderPaneInteract.setPadding(new Insets(15576, 0, 0, 9821));
		borderPaneInteract.setPickOnBounds(false);
	}
	
	
	
	// -------------------- ---------- --------------------
	

	
	private ArrayList<Couple> possiblePositions;
	
	private double mouseX;
	private double mouseY;
	private double viewCardX;
	private double viewCardY;
	
	private void cardsInHandEvents (ImageView viewCard, Card card, String cardName) {
		// ---------- Mouse enters viewCard ----------
		viewCard.setOnMouseEntered(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				// Brings card forward
				//viewCard.setTranslateY(viewCard.getTranslateY()-25);
				// Turns on indications
				if (card.getType().equals(Card_t.gallery)) {
					possiblePositions = moteur.getBoard().getPossiblePositions((GalleryCard) card);
					possiblePositions.stream().forEach(position -> {
						ImageView viewIndication = new ImageView("ressources/carte_indication.png");
						GameBoard.gridPaneBoard.add(viewIndication, (position.getColumn() + GameBoard.startCardX), (position.getLine() + GameBoard.startCardY));
						// Drag over viewIndication
						viewIndication.setOnDragDropped(new EventHandler <DragEvent>(){
							@Override
							public void handle(DragEvent dragEvent) {
								System.out.println("DROPPED");
								if (dragEvent.getGestureSource() != viewIndication  &&  dragEvent.getDragboard().hasImage()) {
									dragEvent.acceptTransferModes(TransferMode.COPY);
									Node nodeToDelete = getNodeFromGridPane(GameBoard.gridPaneBoard, (position.getColumn() + GameBoard.startCardX), (position.getLine() + GameBoard.startCardY));
									GameBoard.gridPaneBoard.getChildren().remove(nodeToDelete);
									GameBoard.gridPaneBoard.add(new ImageCell().getImageView(cardName), (position.getColumn() + GameBoard.startCardX), (position.getLine() + GameBoard.startCardY));
									viewCard.setVisible(false);
								}
								dragEvent.setDropCompleted(true);
								dragEvent.consume();
							}
						});
						viewIndication.setOnMouseDragExited(new EventHandler <MouseEvent>(){
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
		/*viewCard.setOnMouseExited(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// Puts back card into place
				viewCard.setTranslateY(viewCard.getTranslateY()+25);
				// Turns off indications
				if (card.getType().equals(Card_t.gallery)) {
					possiblePositions.stream().forEach(position -> {
						Node node = getNodeFromGridPane(GameBoard.gridPaneBoard, (position.getColumn() + GameBoard.startCardX), (position.getLine() + GameBoard.startCardY));
						GameBoard.gridPaneBoard.getChildren().remove(node);
					});
				}
			}
		});*/
		// ---------- Mouse presses viewCard ----------
		viewCard.setOnMousePressed(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown()  &&  !event.isSecondaryButtonDown()) {
					/*hboxGameCardsInHand.getScene().setCursor(Cursor.CLOSED_HAND);
					mouseX = event.getSceneX();
					mouseY = event.getSceneY();
					viewCardX = ((ImageView)(event.getSource())).getTranslateX();
					viewCardY = ((ImageView)(event.getSource())).getTranslateY();*/
				}
				if (event.isSecondaryButtonDown()  &&  card.getType().equals(Card_t.gallery)) {
					viewCard.setRotate(viewCard.getRotate() + 180);
				}
			}
		});
		// ---------- Drag detected on viewCard ----------
		viewCard.setOnDragDetected(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				// Drag & Drop
				Dragboard dragBoard = viewCard.startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
		        content.putImage(viewCard.getImage());
		        dragBoard.setContent(content);
		        mouseEvent.consume();
			}
		});
		// ---------- Mouse drags viewCard ----------
		viewCard.setOnMouseDragged(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown()) {
					/*double mouseOffSetX = event.getSceneX() - mouseX;
					double mouseOffSetY = event.getSceneY() - mouseY;
					viewCard.setTranslateX(viewCardX + mouseOffSetX);
					viewCard.setTranslateY(viewCardY + mouseOffSetY);*/
					// TODO
					/*if (card.getType().equals(Card_t.gallery)  &&  card_can_go_into_grid) {
						card_sticks_to_grid
					}*/
				}
			}
		});
		// ---------- Mouse releases viewCard ----------
		viewCard.setOnMouseReleased(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// Puts back card into place
				viewCard.setTranslateY(viewCard.getTranslateY()+25);
				// Turns off indications
				if (!event.isPrimaryButtonDown()) {
					hboxGameCardsInHand.getScene().setCursor(Cursor.DEFAULT);
					if (card.getType().equals(Card_t.gallery)) {
						possiblePositions.stream().forEach(position -> {
							Node node = getNodeFromGridPane(GameBoard.gridPaneBoard, (position.getColumn() + GameBoard.startCardX), (position.getLine() + GameBoard.startCardY));
							GameBoard.gridPaneBoard.getChildren().remove(node);
						});
					}
				}
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
	
	
	
	private GamePlayingCard getImageCard (Card c) {
		GamePlayingCard playingCard = null;
        switch(c.getType()) {
            case action:
                switch(((ActionCard)c).getAction()){
                    case Sabotage:
                        switch(((RepareSabotageCard)c).getTool()){
                            case Pickaxe:
                            	playingCard = new GamePlayingCard("carte_brise_pioche");
                                break;
                            case Lantern:
                            	playingCard = new GamePlayingCard("carte_brise_lanterne");
                                break;
                            case Wagon:
                            	playingCard = new GamePlayingCard("carte_brise_chariot");
                                break;
                        }
                        break;
                    case Map:
                    	playingCard = new GamePlayingCard("carte_plan_secret");
                        break;
                    case Repare:
                        switch(((RepareSabotageCard)c).nbTools()){
                            case 1 :
                                switch(((RepareSabotageCard)c).getTool()){
                                    case Pickaxe:
                                    	playingCard = new GamePlayingCard("carte_repare_pioche");
                                        break;
                                    case Lantern:
                                    	playingCard = new GamePlayingCard("carte_repare_lanterne");
                                        break;
                                    case Wagon:
                                    	playingCard = new GamePlayingCard("carte_repare_chariot");
                                        break; 
                                }
                            break;
                            case 2 :
                                switch(((RepareSabotageCard)c).getTool()){
                                    case Pickaxe:
                                    	playingCard = new GamePlayingCard("carte_repare_pioche_lanterne");
                                        break;
                                    case Lantern:
                                    	playingCard = new GamePlayingCard("carte_repare_lanterne_chariot");
                                        break;
                                    case Wagon:
                                    	playingCard = new GamePlayingCard("carte_repare_chariot_pioche");
                                        break;
                                }
                            break;
                            default:
                            	playingCard = new GamePlayingCard("carte_test_118_181");
                        }
                        break;
                    case Crumbing:
                    	playingCard = new GamePlayingCard("carte_eboulement");
                        break;
                    default:
                    	playingCard = new GamePlayingCard("carte_test_118_181");
                }
                break;
            case gallery:
                if(((GalleryCard)c).canHasCenter()){
                    if(((GalleryCard)c).canHasNorth()){
                        if(((GalleryCard)c).canHasWest()){
                            if(((GalleryCard)c).canHasSouth()){
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("NSEO_C");
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("NSO_C");
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("NEO_C");
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("NO_C");
                                }
                            }
                        }else{//Sans West
                            if(((GalleryCard)c).canHasSouth()){
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("NSO_C");//NSE
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("NS_C");
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("NE_C");
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("carte_test_118_181");//N
                                }
                            }
                        }
                    }else{//Sans North
                        if(((GalleryCard)c).canHasWest()){
                            if(((GalleryCard)c).canHasSouth()){
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("NEO_C");//SEO
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("NE_C");//SO
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("EO_C");
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("carte_test_118_181");//O
                                }
                            }
                        }else{//Sans West
                            if(((GalleryCard)c).canHasSouth()){
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("NO_C");//SE
                                }else{//Sans East carte_test_118_181
                                	playingCard = new GamePlayingCard("carte_test_118_181");//S
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("carte_test_118_181");//E
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("carte_test_118_181");//rien
                                }
                            }
                        }
                        
                    }
                }else{//Sans Center
                    if(((GalleryCard)c).canHasNorth()){
                        if(((GalleryCard)c).canHasWest()){
                            if(((GalleryCard)c).canHasSouth()){
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("NSEO_NC");
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("NSO_NC");
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("NEO_NC");
                                }else{
                                	playingCard = new GamePlayingCard("NO_NC");
                                }
                            }
                        }else{//Sans West
                            if(((GalleryCard)c).canHasSouth()){
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("NSO_NC");//NSE
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("NS_NC");
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("SO_NC");
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("N_NC");//N
                                }
                            }
                        }
                    }else{//Sans North
                        if(((GalleryCard)c).canHasWest()){
                            if(((GalleryCard)c).canHasSouth()){
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("NEO_NC");//SEO
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("SO_NC");//SO
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("EO_NC");
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("O_NC");//O
                                }
                            }
                        }else{//Sans West
                            if(((GalleryCard)c).canHasSouth()){
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("NO_NC");//SE
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("N_NC");//S
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("O_NC");//E
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("carte_test_118_181");//rien
                                }
                            }
                        }
                    }
                }
                break;
            default:
            	playingCard = new GamePlayingCard("carte_test_118_181");
        }
        return playingCard;
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
			    //imageView.setFitWidth(70);
			    //imageView.setFitHeight(70);
			}
			return imageView;
		}
	
	}
}
