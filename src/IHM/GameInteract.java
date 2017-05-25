package IHM;

import java.io.IOException;
import java.util.ArrayList;
import Board.Couple;
import Cards.*;
import Cards.Card.Card_t;
import Cards.RepareSabotageCard.Tools;
import Player.Player;
import Saboteur.Moteur;
import Saboteur.Saboteur;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

public class GameInteract {
	
	private Moteur moteur;
	private Hand hand;
	protected Card card;
    
    
	
	private int numberOfCardsInHand;
	private ArrayList <GamePlayingCard> cardsInHand;

	private GridPane gridPanePlayer;
	private int numberOfPlayers;
	private final int listAvatarSize = 75;
	private final int listConstraintSize = 40;
	private final Couple listAvatarPos = new Couple(0, 0);
	private final Couple listPseudoPos = new Couple(0, 2);
	private final Couple listConstraintLanternPos = new Couple(1, 2);
	private final Couple listConstraintPickaxePos = new Couple(1, 3);
	private final Couple listConstraintWagonPos = new Couple(1, 4);
	
	private ImageView viewDeck;
	private ImageView viewDiscard;
	
	private GridPane gridPanePlayerInfos;
	private ImageView viewPlayerInfoConstraintLantern;
	private ImageView viewPlayerInfoConstraintPickaxe;
	private ImageView viewPlayerInfoConstraintWagon;
	private ImageView viewPlayerInfoAvatar;
	private Text textPlayerInfoPseudo;
	private Text textPlayerInfoRole;
	private Text textPlayerInfoGold;
	private final Couple playerInfoConstraintLanternPos = new Couple(0, 0);
	private final Couple playerInfoConstraintPickaxePos = new Couple(0, 1);
	private final Couple playerInfoConstraintWagonPos = new Couple(0, 2);
	private final Couple playerInfoAvatarPos = new Couple(1, 0);
	private final Couple playerInfoPseudoPos = new Couple(1, 3);
	private final Couple playerInfoRolePos = new Couple(2, 3);
	private final Couple playerInfoGoldPos = new Couple(3, 3);
	
	@FXML
	BorderPane borderPaneInteract;
	@FXML
	HBox hboxGameCardsInHand;
	@FXML
	VBox vboxPlayerList;
	@FXML
	VBox vboxPlayerListIndications;
	@FXML
	HBox hboxTop;
	@FXML
	HBox hboxDeckDiscard;
	@FXML
	VBox vboxNumberOfCardsInDeck;
	@FXML
	Text textNumberOfCardsInDeck;
	@FXML
	StackPane stackPaneBottom;
	@FXML
	HBox hboxPlayerInfos;
	
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

		
		// Player list configuration
		numberOfPlayers = moteur.getAllPlayers().size();
		vboxPlayerList.setPrefHeight(vboxPlayerList.getPrefHeight()*numberOfPlayers);
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
			gridPanePlayer.add(viewAvatar, listAvatarPos.getColumn(), listAvatarPos.getLine()); GridPane.setColumnSpan(viewAvatar, 2); GridPane.setRowSpan(viewAvatar, 2);
			gridPanePlayer.add(textPseudo, listPseudoPos.getColumn(), listPseudoPos.getLine()); GridPane.setColumnSpan(textPseudo, 3); GridPane.setMargin(textPseudo, new Insets(5, 0, 0, 5));
			gridPanePlayer.add(viewConstraintLantern, listConstraintLanternPos.getColumn(), listConstraintLanternPos.getLine());
			gridPanePlayer.add(viewConstraintPickaxe, listConstraintPickaxePos.getColumn(), listConstraintPickaxePos.getLine());
			gridPanePlayer.add(viewConstraintWagon, listConstraintWagonPos.getColumn(), listConstraintWagonPos.getLine());
			vboxPlayerList.getChildren().add(gridPanePlayer);
		}
		// TODO - bring forward first player
		
		// Deck and Discard configuration
		viewDeck = new ImageView("ressources/dos_carte_jeu.png");
		hboxDeckDiscard.getChildren().add(viewDeck);
		deckEvents(viewDeck);
		viewDiscard = new ImageView("ressources/defausse.png");
		hboxDeckDiscard.getChildren().add(viewDiscard);
		
		// Player's info
		hboxPlayerInfos.setPrefSize(300.0, (150.0+(150.0/3.0))); // avatar 150*150  ;  constraints (150/3)*(150/3)  ;  texts 150*?
		gridPanePlayerInfos = new GridPane();
		// Player's info constraints
		viewPlayerInfoConstraintLantern = new ImageView();
		viewPlayerInfoConstraintPickaxe = new ImageView();
		viewPlayerInfoConstraintWagon = new ImageView();
		viewPlayerInfoConstraintLantern.setFitWidth(150.0 / 3.0);
		viewPlayerInfoConstraintLantern.setFitHeight(150.0 / 3.0);
		viewPlayerInfoConstraintPickaxe.setFitWidth(150.0 / 3.0);
		viewPlayerInfoConstraintPickaxe.setFitHeight(150.0 / 3.0);
		viewPlayerInfoConstraintWagon.setFitWidth(150.0 / 3.0);
		viewPlayerInfoConstraintWagon.setFitHeight(150.0 / 3.0);
		// Player's info avatar
		viewPlayerInfoAvatar = new ImageView();
		viewPlayerInfoAvatar.setFitWidth(150.0);
		viewPlayerInfoAvatar.setFitHeight(150.0);
		// Player's info texts
		textPlayerInfoPseudo = new Text();
		textPlayerInfoRole = new Text();
		textPlayerInfoGold = new Text();
		// Puts everything into the player info grid
		gridPanePlayerInfos.setPrefSize(hboxPlayerInfos.getPrefWidth(), hboxPlayerInfos.getPrefHeight());
		gridPanePlayerInfos.add(viewPlayerInfoConstraintLantern, playerInfoConstraintLanternPos.getColumn(), playerInfoConstraintLanternPos.getLine());
		gridPanePlayerInfos.add(viewPlayerInfoConstraintPickaxe, playerInfoConstraintPickaxePos.getColumn(), playerInfoConstraintPickaxePos.getLine());
		gridPanePlayerInfos.add(viewPlayerInfoConstraintWagon, playerInfoConstraintWagonPos.getColumn(), playerInfoConstraintWagonPos.getLine());
		gridPanePlayerInfos.add(viewPlayerInfoAvatar, playerInfoAvatarPos.getColumn(), playerInfoAvatarPos.getLine()); GridPane.setColumnSpan(viewPlayerInfoAvatar, 3); GridPane.setRowSpan(viewPlayerInfoAvatar, 3);
		gridPanePlayerInfos.add(textPlayerInfoPseudo, playerInfoPseudoPos.getColumn(), playerInfoPseudoPos.getLine());
		gridPanePlayerInfos.add(textPlayerInfoRole, playerInfoRolePos.getColumn(), playerInfoRolePos.getLine());
		gridPanePlayerInfos.add(textPlayerInfoGold, playerInfoGoldPos.getColumn(), playerInfoGoldPos.getLine());
		hboxPlayerInfos.getChildren().add(gridPanePlayerInfos);
		
		
		nextPlayer();
		
		
		// Center player list on center-left of the screen
		BorderPane.setMargin(vboxPlayerList, new Insets(0, 0, 0, MainLoader.scene.getWidth()-vboxPlayerList.getTranslateX()-vboxPlayerList.getPrefWidth()));
		// Center hand in bottom-middle of the screen, and deck and discard zone in bottom-right of the screen, and player's infos
		BorderPane.setMargin(stackPaneBottom, new Insets((MainLoader.scene.getHeight()-GameBoard.cardsHeight-vboxPlayerList.getPrefHeight()-hboxTop.getPrefHeight()), 0, 0, 0));
		StackPane.setMargin(hboxGameCardsInHand, new Insets(0, 0, 0, ((MainLoader.scene.getWidth()/2)-(numberOfCardsInHand*GameBoard.cardsWidth/2))));
		StackPane.setMargin(hboxDeckDiscard, new Insets(0, 0, 0, MainLoader.scene.getWidth()-hboxDeckDiscard.getPrefWidth()-BorderPane.getMargin(stackPaneBottom).getLeft()));
		StackPane.setMargin(vboxNumberOfCardsInDeck, new Insets(0, 0, 0, MainLoader.scene.getWidth()-(hboxDeckDiscard.getPrefWidth()/2)-(GameBoard.cardsWidth*2)-BorderPane.getMargin(stackPaneBottom).getLeft()));

		
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
				if (card.getType().equals(Card_t.gallery)  &&  moteur.getCurrentPlayer().canPlayGalleryCard()) {
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

                                    int index = hboxGameCardsInHand.getChildren().indexOf(viewCard);
                                    Card cardBefore = moteur.getCurrentPlayer().getPlayableCards().chooseOne_without_remove(index);

                                    System.err.println("Index: "+index+" Card: "+((GalleryCard) cardBefore).simplified());

                                    if(!moteur.getBoard().isCompatibleWithNeighbors((GalleryCard) cardBefore, new Couple(position.getLine(), position.getColumn()))){
										cardToPut = ((GalleryCard) cardBefore).rotate();
                                    } else {
										cardToPut = (GalleryCard) cardBefore;
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
						            	dragEvent.acceptTransferModes(TransferMode.MOVE);
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
										ImageView viewChosenEndCard = getImageCard(moteur.getBoard().getNodeFromMine(new Couple((droppedLine-GameBoard.startCardY), (droppedColumn-GameBoard.startCardX))).getCard()).getImageView();
										GameBoard.gridPaneBoard.add(viewChosenEndCard, droppedColumn, droppedLine);
										
										// Delai retournement de carte but
		                            	Timeline timeChosenEndCard = new Timeline(new KeyFrame(Duration.seconds(3.0), new KeyValue(viewChosenEndCard.imageProperty(), new Image("ressources/dos_carte_arrivee.png"))));
		                            	if (viewChosenEndCard.getRotate() != 0.0) {
		                            		timeChosenEndCard.setOnFinished(new EventHandler <ActionEvent>() {
												@Override
												public void handle(ActionEvent event) {
													viewChosenEndCard.setRotate(0.0);
												}
											});
		                            	}
		                            	timeChosenEndCard.play();
		                            	
										success = true;
									}
									dragEvent.setDropCompleted(success);
									dragEvent.consume();
								}
							});
						});
					}
					// Turns on crumbling indications
					else if (((ActionCard)card).getAction().equals(ActionCard.Action.Crumbing)) {
						ImageView viewIndicationStartCard = new ImageView("ressources/carte_non_indication.png");
						GameBoard.gridPaneBoard.add(viewIndicationStartCard, GameBoard.startCardX, GameBoard.startCardY);
						GameBoard.endCards.stream().forEach(endCard -> {
							ImageView viewIndicationEndCard = new ImageView("ressources/carte_non_indication.png");
							GameBoard.gridPaneBoard.add(viewIndicationEndCard, endCard.getColumn(), endCard.getLine());
						});
					}
					// Turns on constraints indications
					else if (((ActionCard)card).getAction().equals(ActionCard.Action.Sabotage)) {
						moteur.getAllPlayers().stream().forEach(player -> {
							if (player.getAttributeCards().canBreakTool((RepareSabotageCard)card)) {
								ImageView viewIndicationConstraints = new ImageView("ressources/carte_indication.png");
								viewIndicationConstraints.setFitWidth(vboxPlayerList.getPrefWidth());
								viewIndicationConstraints.setFitHeight(vboxPlayerList.getPrefHeight()/numberOfPlayers);
								vboxPlayerListIndications.getChildren().add(player.getNum(), viewIndicationConstraints);
								// Drag over viewIndicationConstraints
								viewIndicationConstraints.setOnDragOver(new EventHandler <DragEvent>() {
									@Override
									public void handle(DragEvent dragEvent) {
							            if (dragEvent.getGestureSource() != viewIndicationConstraints  &&  dragEvent.getDragboard().hasImage()) {
							            	dragEvent.acceptTransferModes(TransferMode.MOVE);
							            }
							            dragEvent.consume();
									}
								});
								// Drag dropped viewIndicationConstraints
								viewIndicationConstraints.setOnDragDropped(new EventHandler <DragEvent>(){
									@Override
									public void handle(DragEvent dragEvent) {
										Dragboard dragBoard = dragEvent.getDragboard();
										boolean success = false;
										if (dragBoard.hasImage()) {
											if (((RepareSabotageCard)card).getTool().equals(Tools.Lantern)) {
												ImageView viewConstraint = (ImageView)getNodeFromGridPane((GridPane)vboxPlayerList.getChildren().get(player.getNum()), listConstraintLanternPos.getColumn(), listConstraintLanternPos.getLine());
												viewConstraint.setImage(new Image("ressources/lanterne_detruite.png"));
											}
											else if (((RepareSabotageCard)card).getTool().equals(Tools.Pickaxe)) {
												ImageView viewConstraint = (ImageView)getNodeFromGridPane((GridPane)vboxPlayerList.getChildren().get(player.getNum()), listConstraintPickaxePos.getColumn(), listConstraintPickaxePos.getLine());
												viewConstraint.setImage(new Image("ressources/pioche_detruite.png"));
											}
											else if (((RepareSabotageCard)card).getTool().equals(Tools.Wagon)) {
												ImageView viewConstraint = (ImageView)getNodeFromGridPane((GridPane)vboxPlayerList.getChildren().get(player.getNum()), listConstraintWagonPos.getColumn(), listConstraintWagonPos.getLine());
												viewConstraint.setImage(new Image("ressources/wagon_detruit.png"));
											}

                                            // maj moteur Sabotage
                                            player.setSabotage((RepareSabotageCard) card);
                                            System.out.println(player.debugString());
											
											updateCurrentPlayerConstraints();

                                            success = true;
										}
										dragEvent.setDropCompleted(success);
										dragEvent.consume();
									}
								});
							}
							else {
								ImageView viewIndicationConstraints = new ImageView("ressources/carte_non_indication.png");
								viewIndicationConstraints.setFitWidth(vboxPlayerList.getPrefWidth());
								viewIndicationConstraints.setFitHeight(vboxPlayerList.getPrefHeight()/numberOfPlayers);
								vboxPlayerListIndications.getChildren().add(player.getNum(), viewIndicationConstraints);
							}
						});
					}
					// Turns on repare indications
					else if (((ActionCard)card).getAction().equals(ActionCard.Action.Repare)) {
						moteur.getAllPlayers().stream().forEach(player -> {
							if (player.getAttributeCards().canRepareTool((RepareSabotageCard)card)) {
								ImageView viewIndicationRepare = new ImageView("ressources/carte_indication.png");
								viewIndicationRepare.setFitWidth(vboxPlayerList.getPrefWidth());
								viewIndicationRepare.setFitHeight(vboxPlayerList.getPrefHeight()/numberOfPlayers);
								vboxPlayerListIndications.getChildren().add(player.getNum(), viewIndicationRepare);
								// Drag over viewIndicationRepare
								viewIndicationRepare.setOnDragOver(new EventHandler <DragEvent>() {
									@Override
									public void handle(DragEvent dragEvent) {
							            if (dragEvent.getGestureSource() != viewIndicationRepare  &&  dragEvent.getDragboard().hasImage()) {
							            	dragEvent.acceptTransferModes(TransferMode.MOVE);
							            }
							            dragEvent.consume();
									}
								});
								// Drag dropped viewIndicationConstraints
								viewIndicationRepare.setOnDragDropped(new EventHandler <DragEvent>(){
									@Override
									public void handle(DragEvent dragEvent) {
										Dragboard dragBoard = dragEvent.getDragboard();
										boolean success = false;
										if (dragBoard.hasImage()) {
                                            Tools tool = player.setRepare((RepareSabotageCard) card);
                                            if (tool.equals(Tools.Lantern)) {
                                                ImageView viewConstraint = (ImageView)getNodeFromGridPane((GridPane)vboxPlayerList.getChildren().get(player.getNum()), listConstraintLanternPos.getColumn(), listConstraintLanternPos.getLine());
                                                viewConstraint.setImage(new Image("ressources/lanterne.png"));
                                            }
											else if (tool.equals(Tools.Pickaxe)) {
                                                ImageView viewConstraint = (ImageView)getNodeFromGridPane((GridPane)vboxPlayerList.getChildren().get(player.getNum()), listConstraintPickaxePos.getColumn(), listConstraintPickaxePos.getLine());
                                                viewConstraint.setImage(new Image("ressources/pioche.png"));
                                            }
											else if (tool.equals(Tools.Wagon)) {
                                                ImageView viewConstraint = (ImageView)getNodeFromGridPane((GridPane)vboxPlayerList.getChildren().get(player.getNum()), listConstraintWagonPos.getColumn(), listConstraintWagonPos.getLine());
                                                viewConstraint.setImage(new Image("ressources/wagon.png"));
                                            }

											updateCurrentPlayerConstraints();
											
											success = true;
										}
										dragEvent.setDropCompleted(success);
										dragEvent.consume();
									}
								});
							}
							else {
								ImageView viewIndicationRepare = new ImageView("ressources/carte_non_indication.png");
								viewIndicationRepare.setFitWidth(vboxPlayerList.getPrefWidth());
								viewIndicationRepare.setFitHeight(vboxPlayerList.getPrefHeight()/numberOfPlayers);
								vboxPlayerListIndications.getChildren().add(player.getNum(), viewIndicationRepare);
							}
						});
					}
				}
				// Turns on discard indications
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
							// FIXME - bug when double click-drag
							GameBoard.endCards.stream().forEach(endCard -> {
								Node node = getNodeFromGridPane(GameBoard.gridPaneBoard, endCard.getColumn(), endCard.getLine());
								node.toFront();
								node = getNodeFromGridPane(GameBoard.gridPaneBoard, endCard.getColumn(), endCard.getLine());
								GameBoard.gridPaneBoard.getChildren().remove(node);
							});
						}
						// Turns off crumbling indications
						else if (((ActionCard)card).getAction().equals(ActionCard.Action.Crumbing)) {
							// FIXME - bug when double click-drag
							Node nodeStart = getNodeFromGridPane(GameBoard.gridPaneBoard, GameBoard.startCardX, GameBoard.startCardY);
							nodeStart.toFront();
							nodeStart = getNodeFromGridPane(GameBoard.gridPaneBoard, GameBoard.startCardX, GameBoard.startCardY);
							GameBoard.gridPaneBoard.getChildren().remove(nodeStart);
							GameBoard.endCards.stream().forEach(endCard -> {
								Node node = getNodeFromGridPane(GameBoard.gridPaneBoard, endCard.getColumn(), endCard.getLine());
								node.toFront();
								node = getNodeFromGridPane(GameBoard.gridPaneBoard, endCard.getColumn(), endCard.getLine());
								GameBoard.gridPaneBoard.getChildren().remove(node);
							});
						}
						// Turns off constraints indications
						else if (((ActionCard)card).getAction().equals(ActionCard.Action.Sabotage)) {
							vboxPlayerListIndications.getChildren().clear();
						}
						// Turns off repare indications
						else if (((ActionCard)card).getAction().equals(ActionCard.Action.Repare)) {
							vboxPlayerListIndications.getChildren().clear();
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
					viewCard.setRotate(viewCard.getRotate() + 180);


                    int index = hboxGameCardsInHand.getChildren().indexOf(viewCard);
                    Card cardBefore = moteur.getCurrentPlayer().getPlayableCards().chooseOne_without_remove(index);
                    GalleryCard cardChanged = ((GalleryCard) cardBefore).rotate();

                    ((HandPlayer)moteur.getCurrentPlayer().getPlayableCards()).setGalleryCard(index, cardChanged);

				}
			}
		});
		// ---------- Drag detected on viewCard ----------
		viewCard.setOnDragDetected(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				// Drag & Drop
				isDragged = true;
				Dragboard dragBoard = viewCard.startDragAndDrop(TransferMode.MOVE);
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

				if(moteur.getCurrentPlayer().getDifficulty() == Player.Difficulty.Player){

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
                    // Turns off crumbling indication
                    if (card.getType().equals(Card_t.action)  &&  ((ActionCard)card).getAction().equals(ActionCard.Action.Crumbing)) {
						// FIXME - bug when double click-drag
						Node nodeStart = getNodeFromGridPane(GameBoard.gridPaneBoard, GameBoard.startCardX, GameBoard.startCardY);
						nodeStart.toFront();
						nodeStart = getNodeFromGridPane(GameBoard.gridPaneBoard, GameBoard.startCardX, GameBoard.startCardY);
						GameBoard.gridPaneBoard.getChildren().remove(nodeStart);
						GameBoard.endCards.stream().forEach(endCard -> {
							Node node = getNodeFromGridPane(GameBoard.gridPaneBoard, endCard.getColumn(), endCard.getLine());
							node.toFront();
							node = getNodeFromGridPane(GameBoard.gridPaneBoard, endCard.getColumn(), endCard.getLine());
							GameBoard.gridPaneBoard.getChildren().remove(node);
						});
                    }
                    // Turns off constraints indications
                    if (card.getType().equals(Card_t.action)  &&  ((ActionCard)card).getAction().equals(ActionCard.Action.Sabotage)) {
                    	vboxPlayerListIndications.getChildren().clear();
                    }
                    // Turns off repare indications
                    if (card.getType().equals(Card_t.action)  &&  ((ActionCard)card).getAction().equals(ActionCard.Action.Repare)) {
                    	vboxPlayerListIndications.getChildren().clear();
                    }
                    
                    
                    // Removes card from hand
                    if (dragEvent.getTransferMode() == TransferMode.MOVE) {
                        cardsInHand.remove(playingCard);
                        int index = hboxGameCardsInHand.getChildren().indexOf(viewCard);
                        moteur.getCurrentPlayer().getPlayableCards().chooseOne_with_remove(index);
                        hboxGameCardsInHand.getChildren().remove(viewCard);
                    }
                    
                    
                    // Draws the first card from the deck
                    if(!moteur.getDeck().isEmpty()  &&  cardsInHand.size() < moteur.maxHandCard()){
                        Card cardDraw = moteur.getCurrentPlayer().drawCard(moteur.getDeck());
                        System.out.println("Carte piochée: "+cardDraw);
                        cardsInHand.add(getImageCard(cardDraw));
                        cardsInHandEvents(cardsInHand.get(cardsInHand.size()-1).getImageView(), cardDraw, cardsInHand.get(cardsInHand.size()-1).getName(), cardsInHand.get(cardsInHand.size()-1));
                        hboxGameCardsInHand.getChildren().add(cardsInHand.get(cardsInHand.size()-1).getImageView());
                    }
                    // Discard indication off
                    viewDiscard.setImage(new Image("ressources/defausse.png"));
                } else {
                    System.out.println("Ce n'est pas ton tour");
                }

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
					// todo
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
		viewDeck.setOnMouseEntered(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				textNumberOfCardsInDeck.setText(moteur.getDeck().nbCard() + "Cartes");
				vboxNumberOfCardsInDeck.setVisible(true);
				textNumberOfCardsInDeck.setVisible(true);
			}
		});
		viewDeck.setOnMouseExited(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				vboxNumberOfCardsInDeck.setVisible(false);
				textNumberOfCardsInDeck.setVisible(false);
			}
		});
	}
	
	
	
	// -------------------- ---------- --------------------
	
	
	
	// Next player
	private void nextPlayer () {
		// Player's info constraints
		updateCurrentPlayerConstraints();
		// Player's info avatar
		viewPlayerInfoAvatar.setImage(new Image("ressources/" + moteur.getCurrentPlayer().getAvatar() + ".png"));
		// Player's info texts
		textPlayerInfoPseudo.setText(moteur.getCurrentPlayer().getPlayerName());
		textPlayerInfoRole.setText(moteur.getCurrentPlayer().getRole().toString());
		textPlayerInfoGold.setText(new String("Or : " + moteur.getCurrentPlayer().getGoldPoints()));
		
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
		
		
	}
	
	
	
	private void updateCurrentPlayerConstraints () {
		if (!moteur.getCurrentPlayer().getAttributeCards().containsTools(Tools.Lantern))
			viewPlayerInfoConstraintLantern.setImage(new Image("ressources/lanterne.png"));
		else
			viewPlayerInfoConstraintLantern.setImage(new Image("ressources/lanterne_detruite.png"));
		if (!moteur.getCurrentPlayer().getAttributeCards().containsTools(Tools.Pickaxe))
			viewPlayerInfoConstraintPickaxe.setImage(new Image("ressources/pioche.png"));
		else
			viewPlayerInfoConstraintPickaxe.setImage(new Image("ressources/pioche_detruite.png"));
		if (!moteur.getCurrentPlayer().getAttributeCards().containsTools(Tools.Wagon))
			viewPlayerInfoConstraintWagon.setImage(new Image("ressources/wagon.png"));
		else
			viewPlayerInfoConstraintWagon.setImage(new Image("ressources/wagon_detruit.png"));
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

            /// VERIFIED BY THESPYGEEK

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
                                    playingCard.getImageView().setRotate(180);
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
                                }
                            }else{//Sans South
                                if(((GalleryCard)c).canHasEast()){
                                	playingCard = new GamePlayingCard("EO_NC");
                                }else{//Sans East
                                	playingCard = new GamePlayingCard("O_NC");
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
