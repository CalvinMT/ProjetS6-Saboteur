package IHM;

import java.io.IOException;
import java.util.ArrayList;
import Board.Couple;
import Cards.*;
import Cards.Card.Card_t;
import Saboteur.Moteur;
import Saboteur.Saboteur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameInteract {
	
	private Moteur moteur;
	private Hand hand;
	private Card card;
    
    
	
	private int numberOfCardsInHand;
	private ArrayList <GamePlayingCard> cardsInHand;
	
	private int numberOfPlayers;
	private int listAvatarSize = 75;
	private int listConstraintSize = 40;
	
	private int numberOfCardsInDeck;
	private ImageView viewDeck;
	private ImageView viewDiscard;
	
	@FXML
	BorderPane borderPaneInteract;
	@FXML
	HBox hboxGameCardsInHand;
	@FXML
	VBox vboxPlayerList;
	@FXML
	HBox hboxTop;
	@FXML
	HBox hboxDeckDiscard;
	@FXML
	StackPane stackPaneBottom;
	
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
		moteur.getBoard().computeAccessCards();
		
		// Hand configuration
		hand = moteur.getCurrentPlayer().getPlayableCards();
        numberOfCardsInHand = hand.nbCard();
		cardsInHand = new ArrayList <GamePlayingCard> ();
		hboxGameCardsInHand.setPrefWidth(hboxGameCardsInHand.getPrefWidth()*numberOfCardsInHand);
		//hboxGameCardsInHand.setPrefHeight(hboxGameCardsInHand.getPrefHeight()*numberOfCardsInHand);
		for (int i=0; i < numberOfCardsInHand; i++) {
			card = hand.chooseOne_without_remove(i);
			cardsInHand.add(getImageCard(card));
			cardsInHandEvents(cardsInHand.get(i).getImageView(), card, cardsInHand.get(i).getName(), cardsInHand.get(i));
			hboxGameCardsInHand.getChildren().add(cardsInHand.get(i).getImageView());
		}

		
		// Player list configuration
		numberOfPlayers = moteur.getAllPlayers().size();
		vboxPlayerList.setPrefHeight(vboxPlayerList.getPrefHeight()*numberOfPlayers);
		GridPane gridPanePlayer;
		for (int i=0; i < numberOfPlayers; i++) {
			gridPanePlayer = new GridPane();
			gridPanePlayer.setPrefSize(hboxGameCardsInHand.getPrefWidth(), (hboxGameCardsInHand.getPrefHeight()/numberOfPlayers));
			// Avatar
			ImageView viewAvatar = new ImageView("ressources/" + moteur.getPlayer(i).getAvatar() + ".png");
			viewAvatar.setFitWidth(listAvatarSize);
			viewAvatar.setFitHeight(listAvatarSize);
			// Pseudo
			Text textPseudo = new Text(moteur.getPlayer(i).getPlayerName());
			// Constraints
			ImageView viewConstraintLantern = new ImageView("ressources/lanterne.png");
			ImageView viewConstraintPickaxe = new ImageView("ressources/pioche.png");
			ImageView viewConstraintWagon = new ImageView("ressources/wagon.png");
			viewConstraintLantern.setFitWidth(listConstraintSize);
			viewConstraintLantern.setFitHeight(listConstraintSize);
			viewConstraintPickaxe.setFitWidth(listConstraintSize);
			viewConstraintPickaxe.setFitHeight(listConstraintSize);
			viewConstraintWagon.setFitWidth(listConstraintSize);
			viewConstraintWagon.setFitHeight(listConstraintSize);
			// Puts everything into the grid list
			gridPanePlayer.add(viewAvatar, 0, 0); GridPane.setColumnSpan(viewAvatar, 2); GridPane.setRowSpan(viewAvatar, 2);
			gridPanePlayer.add(textPseudo, 2, 0); GridPane.setColumnSpan(textPseudo, 3); GridPane.setMargin(textPseudo, new Insets(5, 0, 0, 5));
			gridPanePlayer.add(viewConstraintLantern, 2, 1);
			gridPanePlayer.add(viewConstraintPickaxe, 3, 1);
			gridPanePlayer.add(viewConstraintWagon, 4, 1);
			vboxPlayerList.getChildren().add(gridPanePlayer);
		}
		// TODO - bring forward first player
		
		// Deck and Discard configuration
		numberOfCardsInDeck = moteur.getDeck().nbCard();
		viewDeck = new ImageView("ressources/dos_carte_jeu.png");
		hboxDeckDiscard.getChildren().add(viewDeck);
		deckEvents(viewDeck);
		viewDiscard = new ImageView("ressources/defausse.png");
		hboxDeckDiscard.getChildren().add(viewDiscard);
		
		// Player's info
		// TODO - show avatar, pseudo, role and gold
		
		// Center player list on center-left of the screen
		BorderPane.setMargin(vboxPlayerList, new Insets(0, 0, 0, MainLoader.scene.getWidth()-vboxPlayerList.getTranslateX()-vboxPlayerList.getPrefWidth()));
		// Center hand in bottom-middle of the screen, and deck and discard zone in bottom-right of the screen
		BorderPane.setMargin(stackPaneBottom, new Insets((MainLoader.scene.getHeight()-GameBoard.cardsHeight-vboxPlayerList.getPrefHeight()-hboxTop.getPrefHeight()), 0, 0, ((MainLoader.scene.getWidth()/2)-(numberOfCardsInHand*GameBoard.cardsWidth/2))));
		StackPane.setMargin(hboxDeckDiscard, new Insets(0, 0, 0, MainLoader.scene.getWidth()-hboxDeckDiscard.getPrefWidth()-BorderPane.getMargin(stackPaneBottom).getLeft()));
		
		borderPaneInteract.setPadding(new Insets(15576, 0, 0, 9821));
		borderPaneInteract.setPickOnBounds(false);


		moteur.setGameInteractControler(this);
	}
	
	
	
	// -------------------- ---------- --------------------
	

	
	private ArrayList<Couple> possiblePositions;
	
	private int droppedColumn;
	private int droppedLine;
	
	private boolean isDragged = false;
	
	private double mouseX;
	private double mouseY;
	private double viewCardX;
	private double viewCardY;
	
	private void cardsInHandEvents (ImageView viewCard, Card card, String cardName, GamePlayingCard playingCard) {
		// ---------- Mouse enters viewCard ----------
		viewCard.setOnMouseEntered(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				// Brings card forward
				viewCard.setTranslateY(viewCard.getTranslateY()-25);
				// Turns on gallery card's indications
				if (card.getType().equals(Card_t.gallery)) {
					possiblePositions = moteur.getBoard().getPossiblePositions((GalleryCard) card);
					possiblePositions.stream().forEach(position -> {
						ImageView viewIndication = new ImageView("ressources/carte_indication.png");
						GameBoard.gridPaneBoard.add(viewIndication, (position.getColumn() + GameBoard.startCardX), (position.getLine() + GameBoard.startCardY));
						// Drag enters viewIndication
						/*viewIndication.setOnDragEntered(new EventHandler <DragEvent>() {
							@Override
							public void handle(DragEvent dragEvent) {
						        if (dragEvent.getGestureSource() != viewIndication  &&  dragEvent.getDragboard().hasImage()) {
									Node nodeToDelete = getNodeFromGridPane(GameBoard.gridPaneBoard, (position.getColumn() + GameBoard.startCardX), (position.getLine() + GameBoard.startCardY));
									GameBoard.gridPaneBoard.getChildren().remove(nodeToDelete);
									GameBoard.gridPaneBoard.add(new ImageCell().getImageView(cardName), (position.getColumn() + GameBoard.startCardX), (position.getLine() + GameBoard.startCardY));
									viewCard.setVisible(false);
						        }
					            dragEvent.consume();
							}
						});
						// Drag exits viewIndication
						viewIndication.setOnDragExited(new EventHandler <DragEvent>() {
							@Override
							public void handle(DragEvent dragEvent) {
								Node nodeToDelete = getNodeFromGridPane(GameBoard.gridPaneBoard, (position.getColumn() + GameBoard.startCardX), (position.getLine() + GameBoard.startCardY));
								GameBoard.gridPaneBoard.getChildren().remove(nodeToDelete);
								GameBoard.gridPaneBoard.add(viewIndication, (position.getColumn() + GameBoard.startCardX), (position.getLine() + GameBoard.startCardY));
								viewCard.setVisible(true);
					            dragEvent.consume();
							}
						});*/
						// Drag over viewIndication
						viewIndication.setOnDragOver(new EventHandler <DragEvent>() {
							@Override
							public void handle(DragEvent dragEvent) {
					            if (dragEvent.getGestureSource() != viewIndication  &&  dragEvent.getDragboard().hasImage()) {
					            	dragEvent.acceptTransferModes(TransferMode.MOVE);
					            }
					            dragEvent.consume();
							}
						});
						// Drag dropped viewIndication
						viewIndication.setOnDragDropped(new EventHandler <DragEvent>(){
							@Override
							public void handle(DragEvent dragEvent) {
								Dragboard dragBoard = dragEvent.getDragboard();
								boolean success = false;
								if (dragBoard.hasImage()) {
									GalleryCard cardToPut;
									droppedColumn = (position.getColumn() + GameBoard.startCardX);
									droppedLine = (position.getLine() + GameBoard.startCardY);
									Node nodeToDelete = getNodeFromGridPane(GameBoard.gridPaneBoard, droppedColumn, droppedLine);

									if(!moteur.getBoard().isCompatibleWithNeighbors((GalleryCard) card, new Couple(position.getLine(), position.getColumn()))){
										cardToPut = ((GalleryCard) card).rotate();
									} else {
										cardToPut = (GalleryCard) card;
									}

									moteur.getBoard().putCard(cardToPut, (droppedLine-GameBoard.startCardY), (droppedColumn-GameBoard.startCardX));

									GameBoard.gridPaneBoard.getChildren().remove(nodeToDelete);
									GameBoard.gridPaneBoard.add(getImageCard(cardToPut).getImageView(), droppedColumn, droppedLine);
									
									success = true;
								}
								dragEvent.setDropCompleted(success);
								dragEvent.consume();
							}
						});
					});
				}
				else if (card.getType().equals(Card_t.action)) {
					// Turns on end card's indications
					if (((ActionCard)card).getAction().equals(ActionCard.Action.Map)) {
						GameBoard.endCards.stream().forEach(endCard -> {
							ImageView viewIndicationEndCard = new ImageView("ressources/carte_indication.png");
							GameBoard.gridPaneBoard.add(viewIndicationEndCard, endCard.getColumn(), endCard.getLine());
							// Drag over viewIndicationEndCard
							viewIndicationEndCard.setOnDragOver(new EventHandler <DragEvent>() {
								@Override
								public void handle(DragEvent dragEvent) {
						            if (dragEvent.getGestureSource() != viewIndicationEndCard  &&  dragEvent.getDragboard().hasImage()) {
						            	dragEvent.acceptTransferModes(TransferMode.COPY);
						            }
						            dragEvent.consume();
								}
							});
							// Drag dropped viewIndicationEndCard
							viewIndicationEndCard.setOnDragDropped(new EventHandler <DragEvent>(){
								@Override
								public void handle(DragEvent dragEvent) {
									Dragboard dragBoard = dragEvent.getDragboard();
									boolean success = false;
									if (dragBoard.hasImage()) {
										droppedColumn = endCard.getColumn();
										droppedLine = endCard.getLine();
										Node nodeToDelete = getNodeFromGridPane(GameBoard.gridPaneBoard, droppedColumn, droppedLine);
										GameBoard.gridPaneBoard.getChildren().remove(nodeToDelete);
										GameBoard.gridPaneBoard.add(getImageCard(moteur.getBoard().getNodeFromMine(new Couple((droppedLine-GameBoard.startCardY), (droppedColumn-GameBoard.startCardX))).getCard()).getImageView(), droppedColumn, droppedLine);
										success = true;
									}
									dragEvent.setDropCompleted(success);
									dragEvent.consume();
								}
							});
						});
					}
					else if (((ActionCard)card).getAction().equals(ActionCard.Action.Crumbing)) {
						// TODO
						/*turn_on_indications_on_all_tunnel_cards
						*/
					}
					else if (((ActionCard)card).getAction().equals(ActionCard.Action.Sabotage)) {
						// TODO
						/*turn_on_indications_on_player_list
						*/
					}
					else if (((ActionCard)card).getAction().equals(ActionCard.Action.Repare)) {
						// TODO
						/*turn_on_indications_on_player_list
						*/
					}
				}
				// Discard indication on
				viewDiscard.setImage(new Image("ressources/defausse_indication.png"));
				viewDiscard.setOnDragOver(new EventHandler <DragEvent>() {
					@Override
					public void handle(DragEvent dragEvent) {
						if (dragEvent.getGestureSource() != viewDiscard  &&  dragEvent.getDragboard().hasImage()) {
			            	dragEvent.acceptTransferModes(TransferMode.MOVE);
			            }
			            dragEvent.consume();
					}
				});
				viewDiscard.setOnDragDropped(new EventHandler <DragEvent>(){
					@Override
					public void handle(DragEvent dragEvent) {
						Dragboard dragBoard = dragEvent.getDragboard();
						boolean success = false;
						if (dragBoard.hasImage()) {
							success = true;
						}
						dragEvent.setDropCompleted(success);
						dragEvent.consume();
					}
				});
			}
		});
		// ---------- Mouse exits viewCard ----------
		viewCard.setOnMouseExited(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (!isDragged) {
					// Puts back card into place
					viewCard.setTranslateY(viewCard.getTranslateY()+25);
					// Turns off indications
					if (card.getType().equals(Card_t.gallery)) {
						possiblePositions.stream().forEach(position -> {
							Node node = getNodeFromGridPane(GameBoard.gridPaneBoard, (position.getColumn() + GameBoard.startCardX), (position.getLine() + GameBoard.startCardY));
							GameBoard.gridPaneBoard.getChildren().remove(node);
						});
					}
					else if (card.getType().equals(Card_t.action)) {
						if (((ActionCard)card).getAction().equals(ActionCard.Action.Map)) {
							GameBoard.endCards.stream().forEach(endCard -> {
								Node node = getNodeFromGridPane(GameBoard.gridPaneBoard, endCard.getColumn(), endCard.getLine());
								node.toFront();
								node = getNodeFromGridPane(GameBoard.gridPaneBoard, endCard.getColumn(), endCard.getLine());
								GameBoard.gridPaneBoard.getChildren().remove(node);
							});
						}
						else if (((ActionCard)card).getAction().equals(ActionCard.Action.Crumbing)) {
							// TODO
							/*turn_off_indications_on_all_tunnel_cards
							*/
						}
						else if (((ActionCard)card).getAction().equals(ActionCard.Action.Sabotage)) {
							// TODO
							/*turn_off_indications_on_player_list
							*/
						}
						else if (((ActionCard)card).getAction().equals(ActionCard.Action.Repare)) {
							// TODO
							/*turn_off_indications_on_player_list
							*/
						}
					}
					// Discard indication off
					viewDiscard.setImage(new Image("ressources/defausse.png"));
				}
			}
		});
		// ---------- Mouse presses viewCard ----------
		viewCard.setOnMousePressed(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				/*if (event.isPrimaryButtonDown()  &&  !event.isSecondaryButtonDown()) {
					hboxGameCardsInHand.getScene().setCursor(Cursor.CLOSED_HAND);
					mouseX = event.getSceneX();
					mouseY = event.getSceneY();
					viewCardX = ((ImageView)(event.getSource())).getTranslateX();
					viewCardY = ((ImageView)(event.getSource())).getTranslateY();
				}*/

				if (event.isSecondaryButtonDown()  &&  card.getType().equals(Card_t.gallery)) {
					System.out.println(moteur.getCurrentPlayer());
					viewCard.setRotate(viewCard.getRotate() + 180);

					GalleryCard cardChanged = ((GalleryCard) card).rotate();

					int index = hboxGameCardsInHand.getChildren().indexOf(viewCard);
					((HandPlayer)moteur.getCurrentPlayer().getPlayableCards()).setGalleryCard(index, cardChanged);

					System.out.println(moteur.getCurrentPlayer());
				}
			}
		});
		// ---------- Drag detected on viewCard ----------
		viewCard.setOnDragDetected(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				// Drag & Drop
				isDragged = true;
				Dragboard dragBoard = viewCard.startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
		        content.putImage(viewCard.snapshot(null, null));
		        dragBoard.setContent(content);
		        mouseEvent.consume();
			}
		});
		// ---------- Drag finished on viewCard ----------
		viewCard.setOnDragDone(new EventHandler <DragEvent>() {
			@Override
			public void handle(DragEvent dragEvent) {
				// Puts back card into place
				viewCard.setTranslateY(viewCard.getTranslateY()+25);
				isDragged = false;
				// Turns off gallery card's indication
				if (card.getType().equals(Card_t.gallery)  &&  !possiblePositions.isEmpty()) {
		            possiblePositions.stream().forEach(position -> {
		            	if ((position.getColumn() + GameBoard.startCardX) != droppedColumn  ||  (position.getLine() + GameBoard.startCardY) != droppedLine) {
							Node node = getNodeFromGridPane(GameBoard.gridPaneBoard, (position.getColumn() + GameBoard.startCardX), (position.getLine() + GameBoard.startCardY));
							GameBoard.gridPaneBoard.getChildren().remove(node);
		            	}
		            });

				}
				// Turns off end card's indication
				if (card.getType().equals(Card_t.action)  &&  ((ActionCard)card).getAction().equals(ActionCard.Action.Map)) {
					GameBoard.endCards.stream().forEach(endCard -> {
						Node node = getNodeFromGridPane(GameBoard.gridPaneBoard, endCard.getColumn(), endCard.getLine());
						if (endCard.getColumn() != droppedColumn  ||  endCard.getLine() != droppedLine) {
							node.toFront();
							node = getNodeFromGridPane(GameBoard.gridPaneBoard, endCard.getColumn(), endCard.getLine());
						}
						GameBoard.gridPaneBoard.getChildren().remove(node);
					});
				}
				if (dragEvent.getTransferMode() == TransferMode.MOVE) {
					cardsInHand.remove(playingCard);
		            moteur.getCurrentPlayer().getPlayableCards().removeCard(card);
		            hboxGameCardsInHand.getChildren().remove(viewCard);
		        }
				else if (dragEvent.getTransferMode() == TransferMode.COPY) {
		            cardsInHand.remove(playingCard);
		            moteur.getCurrentPlayer().getPlayableCards().removeCard(card);
		            hboxGameCardsInHand.getChildren().remove(viewCard);
		            // TODO
		        }
	            // Draws the first card from the deck
	            if(!moteur.getDeck().isEmpty()  &&  cardsInHand.size() < moteur.maxHandCard()){
	            	moteur.getCurrentPlayer().drawCard(moteur.getDeck());
					Card cardDraw = hand.chooseOne_without_remove(cardsInHand.size()-1);
					cardsInHand.add(getImageCard(cardDraw));
					cardsInHandEvents(cardsInHand.get(cardsInHand.size()-1).getImageView(), cardDraw, cardsInHand.get(cardsInHand.size()-1).getName(), cardsInHand.get(cardsInHand.size()-1));
					hboxGameCardsInHand.getChildren().add(cardsInHand.get(cardsInHand.size()-1).getImageView());
				}
				// Discard indication off
				viewDiscard.setImage(new Image("ressources/defausse.png"));
				dragEvent.consume();
			}
		});
		// ---------- Mouse drags viewCard ----------
		/*viewCard.setOnMouseDragged(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown()) {
					double mouseOffSetX = event.getSceneX() - mouseX;
					double mouseOffSetY = event.getSceneY() - mouseY;
					viewCard.setTranslateX(viewCardX + mouseOffSetX);
					viewCard.setTranslateY(viewCardY + mouseOffSetY);
					// TODO
					if (card.getType().equals(Card_t.gallery)  &&  card_can_go_into_grid) {
						card_sticks_to_grid
					}
				}
		}
		});*/
		// ---------- Mouse releases viewCard ----------
		/*viewCard.setOnMouseReleased(new EventHandler <MouseEvent>() {
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
		});*/
	}
	
	
	
	// -------------------- ---------- --------------------
	
	
	
	private void deckEvents (ImageView viewDeck) {
		// TODO - Deck with numberOfCardsInDeck when mouse hovers
	}
	
	
	
	// -------------------- ---------- --------------------
	
	
	
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
	
	// TODO FACTORISER @Copyright G - Huard 2017
	// TODO getConfig
	
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
                                	playingCard.getImageView().setRotate(180);
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("NS_C");
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("NE_C");
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("carte_test_118_181");//N
                                	playingCard.getImageView().setRotate(180);
                                }
                            }
                        }
                    }else{//Sans North
                        if(((GalleryCard)c).canHasWest()){
                            if(((GalleryCard)c).canHasSouth()){
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("NEO_C");//SEO
                                	playingCard.getImageView().setRotate(180);
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("NE_C");//SO
                                	playingCard.getImageView().setRotate(180);
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("EO_C");
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("carte_test_118_181");//O
                                	playingCard.getImageView().setRotate(180);
                                }
                            }
                        }else{//Sans West
                            if(((GalleryCard)c).canHasSouth()){
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("NO_C");//SE
                                	playingCard.getImageView().setRotate(180);
                                }else{//Sans East carte_test_118_181
                                	playingCard = new GamePlayingCard("carte_test_118_181");//S
                                	playingCard.getImageView().setRotate(180);
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("carte_test_118_181");//E
                                	playingCard.getImageView().setRotate(180);
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("carte_test_118_181");//rien
                                	playingCard.getImageView().setRotate(180);
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
                                	playingCard.getImageView().setRotate(180);
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("NS_NC");
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("SO_NC");
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("N_NC");//N
                                	playingCard.getImageView().setRotate(180);
                                }
                            }
                        }
                    }else{//Sans North
                        if(((GalleryCard)c).canHasWest()){
                            if(((GalleryCard)c).canHasSouth()){
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("NEO_NC");//SEO
                                	playingCard.getImageView().setRotate(180);
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("SO_NC");//SO
                                	playingCard.getImageView().setRotate(180);
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("EO_NC");
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("O_NC");//O
                                	playingCard.getImageView().setRotate(180);
                                }
                            }
                        }else{//Sans West
                            if(((GalleryCard)c).canHasSouth()){
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("NO_NC");//SE
                                	playingCard.getImageView().setRotate(180);
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("N_NC");//S
                                	playingCard.getImageView().setRotate(180);
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("O_NC");//E
                                	playingCard.getImageView().setRotate(180);
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("carte_test_118_181");//rien
                                	playingCard.getImageView().setRotate(180);
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

	// ---------------------------------------------------------------------------------------------------------------

	@FXML
	private Button buttonMenuIngame;

	@FXML
	private Button buttonAideInGame;

	@FXML
	private Button buttonRecommencer;

	@FXML
	private Text textMancheCounter;

	@FXML
	void handleButtonMenuInGame(ActionEvent event) {
		Stage stage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("MenuPause.fxml"));
			stage.setScene(new Scene(root));
			stage.setTitle("Pause");
			stage.initStyle(StageStyle.UNDECORATED);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(buttonMenuIngame.getScene().getWindow());
			stage.showAndWait();
		}catch(Exception e){
			System.out.println("Erreur " + e);
		}
	}

	public void addGalleryCard(GalleryCard c, int line, int column){

		GameBoard.gridPaneBoard.add(getImageCard(c).getImageView(), (column + GameBoard.startCardX), (line + GameBoard.startCardY));
	}




	@FXML
	void handleButtonAideInGame(ActionEvent event){
            Stage stage = new Stage();
            TransparentStage tspStage= new TransparentStage();
            tspStage.start(stage);
	}

	@FXML
	void handleButtonRecommencer(ActionEvent event){
		System.out.println("Remise à zéro de la partie");
	}
}
