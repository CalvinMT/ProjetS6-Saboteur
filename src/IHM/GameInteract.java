package IHM;

import static IHM.ImageCard.getImageCard;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

import Board.Couple;
import Player.Player.Difficulty;
import Cards.ActionCard;
import Cards.GoalCard;
import Cards.Card;
import Cards.Card.Card_t;
import Cards.GalleryCard;
import Cards.GalleryCard.Gallery_t;
import Cards.Hand;
import Cards.HandPlayer;
import Cards.RepareSabotageCard;
import Cards.RepareSabotageCard.Tools;
import Player.Player;
import Saboteur.Moteur;
import Saboteur.Saboteur;
import javafx.scene.paint.*;
import javafx.animation.AnimationTimer;
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
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import Saboteur.Moteur.State;


public class GameInteract {
	
	private Moteur moteur;
	private Hand hand;
	private Card card;
    
    
	
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

//	private ArrayList<>

	
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

		textMancheCounter.setText("Manche : "+Saboteur.manche+" /3");
		textMancheCounter.setTextAlignment(TextAlignment.CENTER);
		textMancheCounter.setFill(Paint.valueOf("FFFFFF"));
		
		// Player list configuration
		numberOfPlayers = moteur.getAllPlayers().size();
		vboxPlayerList.setPrefHeight(vboxPlayerList.getPrefHeight()*numberOfPlayers);
		for (int i=0; i < numberOfPlayers; i++) {
			gridPanePlayer = new GridPane();
			gridPanePlayer.setPrefSize(hboxGameCardsInHand.getPrefWidth(), (hboxGameCardsInHand.getPrefHeight()/numberOfPlayers));
			// Avatar
			ImageView viewAvatar = new ImageView("ressources/" + moteur.getPlayer(i).getAvatar() + ".png");
			// Pseudo
			Text textPseudo = new Text(moteur.getPlayer(i).getPlayerName());
			textPseudo.setFill(Paint.valueOf("FFFFFF"));
			// Constraints
            Player player = moteur.getPlayer(i);
            ImageView viewConstraintPickaxe;
            ImageView viewConstraintLantern;
            ImageView viewConstraintWagon;
            if(player.getAttributeCards().canRepareTool(new RepareSabotageCard("Repare", Tools.Lantern))){
                viewConstraintLantern = new ImageView("ressources/lanterne_detruite.png");
            } else {
                viewConstraintLantern = new ImageView("ressources/lanterne.png");
            }
            if(player.getAttributeCards().canRepareTool(new RepareSabotageCard("Repare", Tools.Wagon))){
                viewConstraintWagon = new ImageView("ressources/wagon_detruit.png");
            } else {
                viewConstraintWagon = new ImageView("ressources/wagon.png");
            }
            if(player.getAttributeCards().canRepareTool(new RepareSabotageCard("Repare", Tools.Pickaxe))){
                viewConstraintPickaxe = new ImageView("ressources/pioche_detruite.png");
            } else {
                viewConstraintPickaxe = new ImageView("ressources/pioche.png");
            }
			// Puts everything into the grid list
			gridPanePlayer.add(viewAvatar, listAvatarPos.getColumn(), listAvatarPos.getLine()); GridPane.setColumnSpan(viewAvatar, 2); GridPane.setRowSpan(viewAvatar, 2);
			gridPanePlayer.add(textPseudo, listPseudoPos.getColumn(), listPseudoPos.getLine()); GridPane.setColumnSpan(textPseudo, 3); GridPane.setMargin(textPseudo, new Insets(5, 0, 0, 5));
			gridPanePlayer.add(viewConstraintLantern, listConstraintLanternPos.getColumn(), listConstraintLanternPos.getLine());
			gridPanePlayer.add(viewConstraintPickaxe, listConstraintPickaxePos.getColumn(), listConstraintPickaxePos.getLine());
			gridPanePlayer.add(viewConstraintWagon, listConstraintWagonPos.getColumn(), listConstraintWagonPos.getLine());
			vboxPlayerList.getChildren().add(gridPanePlayer);
		}
		
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
		GridPane.setMargin(textPlayerInfoPseudo, new Insets(50, 0, 0, 10));
		GridPane.setMargin(textPlayerInfoRole, new Insets(5, 0, 0, 10));
		GridPane.setMargin(textPlayerInfoGold, new Insets(0, 0, 0, 10));
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
		BorderPane.setMargin(stackPaneBottom, new Insets((MainLoader.scene.getHeight()-GameBoard.cardsHeight-vboxPlayerList.getPrefHeight()-hboxTop.getPrefHeight()-83), 0, 0, 0));
		StackPane.setMargin(hboxGameCardsInHand, new Insets(19, 0, 0, ((MainLoader.scene.getWidth()/2)-(numberOfCardsInHand*GameBoard.cardsWidth/2))));
		StackPane.setMargin(hboxDeckDiscard, new Insets(19, 0, 0, MainLoader.scene.getWidth()-hboxDeckDiscard.getPrefWidth()-BorderPane.getMargin(stackPaneBottom).getLeft()));
		StackPane.setMargin(vboxNumberOfCardsInDeck, new Insets(19, 0, 0, MainLoader.scene.getWidth()-(hboxDeckDiscard.getPrefWidth()/2)-(GameBoard.cardsWidth*2)-BorderPane.getMargin(stackPaneBottom).getLeft()));

		
		borderPaneInteract.setPadding(new Insets(15576, 0, 0, 9821));
		borderPaneInteract.setPickOnBounds(false);

		// mise a jour du GridPane en fonction de la mine
        GalleryCard card;
        for(int j=4; j<moteur.getBoard().getMine().size(); j++){

            card = moteur.getBoard().getMine().get(j).getCard();
            GameBoard.gridPaneBoard.add(ImageCard.getImageCard(card).getImageView(), card.getColumn()+GameBoard.startCardX, card.getLine()+GameBoard.startCardY);

        }



		moteur.setGameInteractControler(this);
	}
	
	
	
	// -------------------- ---------- --------------------
	

	
	private ArrayList<Couple> possiblePositions;
	
	private int droppedColumn;
	private int droppedLine;
	
	private boolean isDragged = false;
	private boolean isCrumbling = false;
	private boolean isDiscovering = false;
	
	private void cardsInHandEvents (ImageView viewCard, Card card, String cardName, GamePlayingCard playingCard) {
		// ---------- Mouse enters viewCard ----------
		viewCard.setOnMouseEntered(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				// Brings card forward
				viewCard.setTranslateY(viewCard.getTranslateY()-25);
				// Turns on gallery card's indications
				if (card.getType().equals(Card_t.gallery) && moteur.getCurrentPlayer().canPlayGalleryCard()) {
					possiblePositions = moteur.getBoard().getPossiblePositions((GalleryCard) card);
					possiblePositions.stream().forEach(position -> {
						ImageView viewIndication = new ImageView("ressources/carte_indication.png");
						GameBoard.gridPaneIndications.add(viewIndication, (position.getColumn() + GameBoard.startCardX), (position.getLine() + GameBoard.startCardY));
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
									Node nodeToDelete = getNodeFromGridPane(GameBoard.gridPaneIndications, droppedColumn, droppedLine);

                                    int index = hboxGameCardsInHand.getChildren().indexOf(viewCard);
                                    Card cardBefore = moteur.getCurrentPlayer().getPlayableCards().chooseOne_without_remove(index);

                                    // DEBUG INDEX
//                                    System.err.println("Index: "+index+" Card: "+((GalleryCard) cardBefore).simplified());

                                    if(!moteur.getBoard().isCompatibleWithNeighbors((GalleryCard) cardBefore, new Couple(position.getLine(), position.getColumn()))){
										cardToPut = ((GalleryCard) cardBefore).rotate();
                                    } else {
										cardToPut = (GalleryCard) cardBefore;
									}

									moteur.getBoard().putCard(cardToPut, (droppedLine-GameBoard.startCardY), (droppedColumn-GameBoard.startCardX));

									GameBoard.gridPaneIndications.getChildren().remove(nodeToDelete);
									GameBoard.gridPaneBoard.add(getImageCard(cardToPut).getImageView(), droppedColumn, droppedLine);


									// Check if a GoalCard has been reached

                                    Board.Node goal;

                                    for(int i=1; i<=3; i++){
                                        goal = moteur.getBoard().getMine().get(i);

                                        if(moteur.getBoard().nodeReached(goal) && !((GoalCard)goal.getCard()).isVisible()){

                                            Couple coupleMoteur = goal.getCard().getCoord();
                                            GalleryCard goalCard = moteur.getBoard().getNodeFromMine(coupleMoteur).getCard();
                                            Couple coupleInterface = new Couple(coupleMoteur.getLine()+GameBoard.startCardY, coupleMoteur.getColumn()+GameBoard.startCardX);

                                            if(!moteur.getBoard().isCompatibleWithNeighbors(goalCard, coupleMoteur)){
                                                moteur.getBoard().setCardInMine(i, goalCard.rotate());
                                                goal = moteur.getBoard().getMine().get(i);
                                            }


                                            // DEBUG GOAL
                                            System.out.println("Le but à "+coupleMoteur+" a ete atteint");
//                                            System.out.println(moteur.getBoard().mine());

                                            ImageView viewChosenEndCard = getImageCard(goal.getCard()).getImageView();
                                            GameBoard.gridPaneBoard.add(viewChosenEndCard, coupleInterface.getColumn(), coupleInterface.getLine());

                                            ((GoalCard)goal.getCard()).setVisible(true);

                                            if(goal.getCard().isGold()){
                                                System.out.println("Fin de manche les mineurs ont gagné !!!");
                                            }

                                        }
                                    }

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
							Couple endCardSimplePos = new Couple((endCard.getLine()-GameBoard.startCardY), (endCard.getColumn()-GameBoard.startCardX));
							if (!((GoalCard)moteur.getBoard().getNodeFromMine(endCardSimplePos).getCard()).isVisible()) {
								ImageView viewIndicationEndCard = new ImageView("ressources/carte_indication.png");
								GameBoard.gridPaneIndications.add(viewIndicationEndCard, endCard.getColumn(), endCard.getLine());
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
											Node nodeToDelete = getNodeFromGridPane(GameBoard.gridPaneIndications, droppedColumn, droppedLine);
											GameBoard.gridPaneIndications.getChildren().remove(nodeToDelete);
											ImageView viewChosenEndCard = getImageCard(moteur.getBoard().getNodeFromMine(new Couple((droppedLine-GameBoard.startCardY), (droppedColumn-GameBoard.startCardX))).getCard()).getImageView();
											GameBoard.gridPaneBoard.add(viewChosenEndCard, droppedColumn, droppedLine);
											
											Couple endCardsimplePos = new Couple((droppedLine-GameBoard.startCardY), (droppedColumn-GameBoard.startCardX));
											
											// Delai retournement de carte but
			                            	Timeline timeChosenEndCard = new Timeline(new KeyFrame(Duration.seconds(2.0), new KeyValue(viewChosenEndCard.imageProperty(), new Image("ressources/dos_carte_arrivee.png"))));
		                            		timeChosenEndCard.setOnFinished(new EventHandler <ActionEvent>() {
												@Override
												public void handle(ActionEvent event) {
													Node node = getNodeFromGridPane(GameBoard.gridPaneBoard, endCard.getColumn(), endCard.getLine());
					                                //node = getNodeFromGridPane(GameBoard.gridPaneBoard, endCard.getColumn(), endCard.getLine());
						                            GameBoard.gridPaneBoard.getChildren().remove(node);
													if (viewChosenEndCard.getRotate() != 0.0) {
														viewChosenEndCard.setRotate(0.0);
													}
													((GoalCard)moteur.getBoard().getNodeFromMine(endCardsimplePos).getCard()).setVisible(false);
												}
											});
			                            	timeChosenEndCard.play();
			                            	
			                            	((GoalCard)moteur.getBoard().getNodeFromMine(endCardsimplePos).getCard()).setVisible(true);
			                            	
											success = true;
										}
		                            	isDiscovering = success;
										dragEvent.setDropCompleted(success);
										dragEvent.consume();
									}
								});
							}
						});
					}
					// Turns on crumbling indications
					else if (((ActionCard)card).getAction().equals(ActionCard.Action.Crumbling)) {
						ImageView viewIndicationStartCard = new ImageView("ressources/carte_non_indication.png");
						GameBoard.gridPaneIndications.add(viewIndicationStartCard, GameBoard.startCardX, GameBoard.startCardY);
						GameBoard.endCards.stream().forEach(endCard -> {
							Couple endCradSimplePos = new Couple((endCard.getLine()-GameBoard.startCardY), (endCard.getColumn()-GameBoard.startCardX));
							if (!((GoalCard)moteur.getBoard().getNodeFromMine(endCradSimplePos).getCard()).isVisible()) {
								ImageView viewIndicationEndCard = new ImageView("ressources/carte_non_indication.png");
								GameBoard.gridPaneIndications.add(viewIndicationEndCard, endCard.getColumn(), endCard.getLine());
							}
						});
						
						moteur.getBoard().getMine().stream().forEach((Consumer<? super Board.Node>)node -> {
							GalleryCard galleryCardOnBoard = node.getCard();
							Couple galleryCardOnBoardPos = new Couple((galleryCardOnBoard.getLine()+GameBoard.startCardY), (galleryCardOnBoard.getColumn()+GameBoard.startCardX));
							if (!galleryCardOnBoard.getGalleryType().equals(Gallery_t.start)  &&  !galleryCardOnBoard.getGalleryType().equals(Gallery_t.but)) {
								ImageView viewIndicationCrumbling = new ImageView("ressources/carte_indication.png");
								GameBoard.gridPaneIndications.add(viewIndicationCrumbling, galleryCardOnBoardPos.getColumn(), galleryCardOnBoardPos.getLine());
								// Drag over viewIndicationCrumbling
								viewIndicationCrumbling.setOnDragOver(new EventHandler <DragEvent>() {
									@Override
									public void handle(DragEvent dragEvent) {
							            if (dragEvent.getGestureSource() != viewIndicationCrumbling  &&  dragEvent.getDragboard().hasImage()) {
							            	dragEvent.acceptTransferModes(TransferMode.MOVE);
							            }
							            dragEvent.consume();
									}
								});
								// Drag dropped viewIndicationCrumbling
								viewIndicationCrumbling.setOnDragDropped(new EventHandler <DragEvent>(){
									@Override
									public void handle(DragEvent dragEvent) {
										Dragboard dragBoard = dragEvent.getDragboard();
										boolean success = false;
										if (dragBoard.hasImage()) {
											droppedColumn = galleryCardOnBoardPos.getColumn();
											droppedLine = galleryCardOnBoardPos.getLine();
											if (getNodeFromGridPane(GameBoard.gridPaneBoard, droppedColumn, droppedLine) != null) {
												Node nodeToDelete = getNodeFromGridPane(GameBoard.gridPaneBoard, droppedColumn, droppedLine);
												GameBoard.gridPaneBoard.getChildren().remove(nodeToDelete);
						                        
												moteur.getBoard().removeCard(new Couple(droppedLine-GameBoard.startCardY, droppedColumn-GameBoard.startCardX));
												
						                        success = true;
											}
										}
										isCrumbling = success;
										dragEvent.setDropCompleted(success);
										dragEvent.consume();
									}
								});
							}
						});
					}
					// Turns on constraints indications
					else if (((ActionCard)card).getAction().equals(ActionCard.Action.Sabotage)) {
						moteur.getAllPlayers().stream().forEach(player -> {
							if (player.getAttributeCards().canBreakTool((RepareSabotageCard)card)) {
								ImageView viewIndicationConstraints = new ImageView("ressources/carte_indication.png");
								viewIndicationConstraints.setFitWidth(((GridPane)vboxPlayerList.getChildren().get(player.getNum())).getWidth());
								viewIndicationConstraints.setFitHeight(((GridPane)vboxPlayerList.getChildren().get(player.getNum())).getHeight());
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
											
                                            player.setSabotage((RepareSabotageCard) card);
											
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
								viewIndicationConstraints.setFitWidth(((GridPane)vboxPlayerList.getChildren().get(player.getNum())).getWidth());
								viewIndicationConstraints.setFitHeight(((GridPane)vboxPlayerList.getChildren().get(player.getNum())).getHeight());
								vboxPlayerListIndications.getChildren().add(player.getNum(), viewIndicationConstraints);
							}
						});
					}
					// Turns on repare indications
					else if (((ActionCard)card).getAction().equals(ActionCard.Action.Repare)) {
						moteur.getAllPlayers().stream().forEach(player -> {
							if (player.getAttributeCards().canRepareTool((RepareSabotageCard)card)) {
								ImageView viewIndicationRepare = new ImageView("ressources/carte_indication.png");
								viewIndicationRepare.setFitWidth(((GridPane)vboxPlayerList.getChildren().get(player.getNum())).getWidth());
								viewIndicationRepare.setFitHeight(((GridPane)vboxPlayerList.getChildren().get(player.getNum())).getHeight());
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
								viewIndicationRepare.setFitWidth(((GridPane)vboxPlayerList.getChildren().get(player.getNum())).getWidth());
								viewIndicationRepare.setFitHeight(((GridPane)vboxPlayerList.getChildren().get(player.getNum())).getHeight());
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

					if(moteur.getCurrentPlayer().getDifficulty() == Player.Difficulty.Player){
						
						// Turns off indications
						if (card.getType().equals(Card_t.gallery)) {
							possiblePositions.stream().forEach(position -> {
								Node node = getNodeFromGridPane(GameBoard.gridPaneIndications, (position.getColumn() + GameBoard.startCardX), (position.getLine() + GameBoard.startCardY));
								GameBoard.gridPaneIndications.getChildren().remove(node);
							});
						}
						else if (card.getType().equals(Card_t.action)) {
							// Turns off end card's indication
							if (((ActionCard)card).getAction().equals(ActionCard.Action.Map)) {
								// FIXME - bug when double click-drag
								GameBoard.endCards.stream().forEach(endCard -> {
									Couple endCardSimplePos = new Couple((endCard.getLine()-GameBoard.startCardY), (endCard.getColumn()-GameBoard.startCardX));
									if (!((GoalCard)moteur.getBoard().getNodeFromMine(endCardSimplePos).getCard()).isVisible()) {
										Node node = getNodeFromGridPane(GameBoard.gridPaneIndications, endCard.getColumn(), endCard.getLine());
										GameBoard.gridPaneIndications.getChildren().remove(node);
									}
								});
							}
							// Turns off crumbling indications
							else if (((ActionCard)card).getAction().equals(ActionCard.Action.Crumbling)) {
								// FIXME - bug when double click-drag
								moteur.getBoard().getMine().stream().forEach((Consumer<? super Board.Node>)nodeOnBoard -> {
									GalleryCard galleryCardOnBoard = nodeOnBoard.getCard();
									Couple galleryCardOnBoardSimplePos = new Couple(galleryCardOnBoard.getLine(), galleryCardOnBoard.getColumn());
									if (!moteur.getBoard().getNodeFromMine(galleryCardOnBoardSimplePos).getCard().isGoal()  ||  !((GoalCard)moteur.getBoard().getNodeFromMine(galleryCardOnBoardSimplePos).getCard()).isVisible()) {
										Couple galleryCardOnBoardPos = new Couple((galleryCardOnBoard.getLine()+GameBoard.startCardY), (galleryCardOnBoard.getColumn()+GameBoard.startCardX));
										Node node = getNodeFromGridPane(GameBoard.gridPaneIndications, galleryCardOnBoardPos.getColumn(), galleryCardOnBoardPos.getLine());
										GameBoard.gridPaneIndications.getChildren().remove(node);
									}
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
			}
		});
		// ---------- Mouse presses viewCard ----------
		viewCard.setOnMousePressed(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
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
				if (mouseEvent.isPrimaryButtonDown()) {
					isDragged = true;
					Dragboard dragBoard = viewCard.startDragAndDrop(TransferMode.MOVE);
					ClipboardContent content = new ClipboardContent();
			        content.putImage(viewCard.snapshot(null, null));
			        dragBoard.setContent(content);
			        mouseEvent.consume();
				}
			}
		});
		// ---------- Drag finished on viewCard ----------
		viewCard.setOnDragDone(new EventHandler <DragEvent>() {
			@Override
			public void handle(DragEvent dragEvent) {
				
				// Puts back card into place
				if (!System.getProperty("os.name").equals("Mac OS X")) {
					viewCard.setTranslateY(viewCard.getTranslateY()+25);
				}
				isDragged = false;

				if(moteur.getCurrentPlayer().getDifficulty() == Player.Difficulty.Player){

                    // Turns off gallery card's indication
                    if (card.getType().equals(Card_t.gallery)  &&  !possiblePositions.isEmpty()) {
                        possiblePositions.stream().forEach(position -> {
                            if ((position.getColumn() + GameBoard.startCardX) != droppedColumn  ||  (position.getLine() + GameBoard.startCardY) != droppedLine) {
                                Node node = getNodeFromGridPane(GameBoard.gridPaneIndications, (position.getColumn() + GameBoard.startCardX), (position.getLine() + GameBoard.startCardY));
                                GameBoard.gridPaneIndications.getChildren().remove(node);
                            }
                        });
                    }
                    // Turns off end card's indication
                    if (card.getType().equals(Card_t.action)  &&  ((ActionCard)card).getAction().equals(ActionCard.Action.Map)) {
                        GameBoard.endCards.stream().forEach(endCard -> {
							Couple endCardSimplePos = new Couple((endCard.getLine()-GameBoard.startCardY), (endCard.getColumn()-GameBoard.startCardX));
							if (!((GoalCard)moteur.getBoard().getNodeFromMine(endCardSimplePos).getCard()).isVisible()) {
	                            Node node = getNodeFromGridPane(GameBoard.gridPaneIndications, endCard.getColumn(), endCard.getLine());
	                            if (endCard.getColumn() != droppedColumn  ||  endCard.getLine() != droppedLine) {
	                                node = getNodeFromGridPane(GameBoard.gridPaneIndications, endCard.getColumn(), endCard.getLine());
	                            }
	                            GameBoard.gridPaneIndications.getChildren().remove(node);
                        	}
                        });
                    }
                    // Turns off crumbling indication
                    if (card.getType().equals(Card_t.action)  &&  ((ActionCard)card).getAction().equals(ActionCard.Action.Crumbling)) {
						// FIXME - bug when double click-drag
						moteur.getBoard().getMine().stream().forEach((Consumer<? super Board.Node>)nodeOnBoard -> {
							GalleryCard galleryCardOnBoard = nodeOnBoard.getCard();
							Couple galleryCardOnBoardPos = new Couple((galleryCardOnBoard.getLine()+GameBoard.startCardY), (galleryCardOnBoard.getColumn()+GameBoard.startCardX));
							Node node = getNodeFromGridPane(GameBoard.gridPaneIndications, galleryCardOnBoardPos.getColumn(), galleryCardOnBoardPos.getLine());
							node = getNodeFromGridPane(GameBoard.gridPaneIndications, galleryCardOnBoardPos.getColumn(), galleryCardOnBoardPos.getLine());
							GameBoard.gridPaneIndications.getChildren().remove(node);
                        });
						if (isCrumbling) {
							Node node = getNodeFromGridPane(GameBoard.gridPaneIndications, droppedColumn, droppedLine);
							node = getNodeFromGridPane(GameBoard.gridPaneIndications, droppedColumn, droppedLine);
							GameBoard.gridPaneIndications.getChildren().remove(node);
						}
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
                        
                        waitBeforeCheck();
                    }
                } else {
                    System.out.println("Ce n'est pas ton tour");
                }


                dragEvent.consume();

			}
		});
	}

	
	
	public void waitBeforeCheck() {
		hboxGameCardsInHand.setDisable(true);
		AnimationTimer timerWaitNextTurn = new AnimationTimer() {
			boolean b = false;
			long time;
			@Override
			public void handle(long now) {
				if (!b) {
					b = true;
					time = now;
				}
				if (isDiscovering) {
					if ((now - time)/1000000000 > 2){
						checkEndGame();
						stop();
					}
				}
				else {
					if ((now - time)/500000000 > 1){
						checkEndGame();
						stop();
					}
				}
			}
		};
		
		timerWaitNextTurn.start();
    }
	
	
	
	public void checkEndGame() {
		
		hboxGameCardsInHand.setDisable(false);
		
		 // fin de manche
        if(moteur.endGame()){

            System.out.println("Fin de partie");
            try {
            	moteur.setState(State.Waiting);

                Scene scene = (Scene) GameBoard.gridPaneBoard.getScene();
                BorderPane borderPaneGameLoader = (BorderPane) scene.lookup("#borderPaneGameLoader");
                BorderPane borderPaneEndShaft = FXMLLoader.load(getClass().getResource("EndShaft.fxml"));
                borderPaneGameLoader.getChildren().setAll(borderPaneEndShaft);
            } catch (IOException e) {
                System.err.println("[GameInteract] Erreur changenement de scene");
                e.printStackTrace();
            }

        } else {
            // Draws the first card from the deck
            if(!moteur.getDeck().isEmpty()  &&  cardsInHand.size() < moteur.maxHandCard()){
                Card cardDraw = moteur.getCurrentPlayer().drawCard(moteur.getDeck());

                // DEBUT CARTE PIOCHEE
                //System.out.println("Carte piochée: "+cardDraw);


                cardsInHand.add(getImageCard(cardDraw));
                cardsInHandEvents(cardsInHand.get(cardsInHand.size()-1).getImageView(), cardDraw, cardsInHand.get(cardsInHand.size()-1).getName(), cardsInHand.get(cardsInHand.size()-1));
                hboxGameCardsInHand.getChildren().add(cardsInHand.get(cardsInHand.size()-1).getImageView());

                // DEBUG BOARD
//                System.out.println(moteur.getBoard().mine());
//                System.out.println("Bloqued: "+moteur.getBoard().goldBlocked());
            }

            boolean isIA = false;
            if(moteur.getCurrentPlayer().getDifficulty() != Difficulty.Player){
                isIA = true;
            }

            moteur.nextPlayer();
            nextPlayer();

            if(!isIA){
                // Transition
            	GameLoader.anchorPaneGameTransition.setVisible(true);
            }
            else {
            	AnimationTimer timerWaitForAIToFinish = new AnimationTimer() {
        			boolean b = false;
        			long time;
        			@Override
        			public void handle(long now) {
        				if (!b) {
        					b = true;
        					time = now;
        				}
    					if ((now - time)/500000000 > 1){
    						stop();
    					}
        			}
        		};
        		timerWaitForAIToFinish.start();
            }

            // Discard indication off
            viewDiscard.setImage(new Image("ressources/defausse.png"));

    		isDiscovering = false;
        }

	}
	
	
	// -------------------- ---------- --------------------
	
	
	
	private void deckEvents (ImageView viewDeck) {
		viewDeck.setOnMouseEntered(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				textNumberOfCardsInDeck.setText(moteur.getDeck().nbCard() + "Cartes");
				vboxNumberOfCardsInDeck.setVisible(true);
				textNumberOfCardsInDeck.setVisible(true);
				textNumberOfCardsInDeck.setOnMouseEntered(new EventHandler <MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						vboxNumberOfCardsInDeck.setVisible(true);
						textNumberOfCardsInDeck.setVisible(true);
					}
				});
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
        textPlayerInfoPseudo.setFill(Paint.valueOf("FFFFFF"));
		textPlayerInfoRole.setText(moteur.getCurrentPlayer().getRole().toString());
        textPlayerInfoRole.setFill(Paint.valueOf("FFFFFF"));
		textPlayerInfoGold.setText(new String("Or : " + Saboteur.goldByPlayer.get(moteur.currentNumPlayer())));
        textPlayerInfoGold.setFill(Paint.valueOf("FFFFFF"));
		
		// Hand configuration
		hand = moteur.getCurrentPlayer().getPlayableCards();
        numberOfCardsInHand = hand.nbCard();
		cardsInHand = new ArrayList <GamePlayingCard> ();
		hboxGameCardsInHand.getChildren().clear();
		hboxGameCardsInHand.setPrefWidth(hboxGameCardsInHand.getPrefWidth()*numberOfCardsInHand);

		if(moteur.getCurrentPlayer().getDifficulty() == Difficulty.Player){

            for (int i=0; i < numberOfCardsInHand; i++) {
                card = hand.chooseOne_without_remove(i);
                cardsInHand.add(getImageCard(card));
                cardsInHandEvents(cardsInHand.get(i).getImageView(), card, cardsInHand.get(i).getName(), cardsInHand.get(i));
                hboxGameCardsInHand.getChildren().add(cardsInHand.get(i).getImageView());
            }
        }

		// Brings forward current player in list
		moteur.getAllPlayers().stream().forEach(player -> {
			((GridPane)vboxPlayerList.getChildren().get(player.getNum())).setPrefSize(vboxPlayerList.getPrefWidth(), vboxPlayerList.getPrefHeight());
			((ImageView)((GridPane)vboxPlayerList.getChildren().get(player.getNum())).getChildren().get(0)).setFitWidth(listAvatarSize);
			((ImageView)((GridPane)vboxPlayerList.getChildren().get(player.getNum())).getChildren().get(0)).setFitHeight(listAvatarSize);
			((ImageView)((GridPane)vboxPlayerList.getChildren().get(player.getNum())).getChildren().get(2)).setFitWidth(listConstraintSize);
			((ImageView)((GridPane)vboxPlayerList.getChildren().get(player.getNum())).getChildren().get(2)).setFitHeight(listConstraintSize);
			((ImageView)((GridPane)vboxPlayerList.getChildren().get(player.getNum())).getChildren().get(3)).setFitWidth(listConstraintSize);
			((ImageView)((GridPane)vboxPlayerList.getChildren().get(player.getNum())).getChildren().get(3)).setFitHeight(listConstraintSize);
			((ImageView)((GridPane)vboxPlayerList.getChildren().get(player.getNum())).getChildren().get(4)).setFitWidth(listConstraintSize);
			((ImageView)((GridPane)vboxPlayerList.getChildren().get(player.getNum())).getChildren().get(4)).setFitHeight(listConstraintSize);
		});
		((GridPane)vboxPlayerList.getChildren().get(moteur.getCurrentPlayer().getNum())).setPrefSize(vboxPlayerList.getPrefWidth()+30, vboxPlayerList.getPrefHeight()+50);
		((ImageView)((GridPane)vboxPlayerList.getChildren().get(moteur.getCurrentPlayer().getNum())).getChildren().get(0)).setFitWidth(listAvatarSize+30);
		((ImageView)((GridPane)vboxPlayerList.getChildren().get(moteur.getCurrentPlayer().getNum())).getChildren().get(0)).setFitHeight(listAvatarSize+30);
		((ImageView)((GridPane)vboxPlayerList.getChildren().get(moteur.getCurrentPlayer().getNum())).getChildren().get(2)).setFitWidth(listConstraintSize+(20/3));
		((ImageView)((GridPane)vboxPlayerList.getChildren().get(moteur.getCurrentPlayer().getNum())).getChildren().get(2)).setFitHeight(listConstraintSize+(20/3));
		((ImageView)((GridPane)vboxPlayerList.getChildren().get(moteur.getCurrentPlayer().getNum())).getChildren().get(3)).setFitWidth(listConstraintSize+(20/3));
		((ImageView)((GridPane)vboxPlayerList.getChildren().get(moteur.getCurrentPlayer().getNum())).getChildren().get(3)).setFitHeight(listConstraintSize+(20/3));
		((ImageView)((GridPane)vboxPlayerList.getChildren().get(moteur.getCurrentPlayer().getNum())).getChildren().get(4)).setFitWidth(listConstraintSize+(20/3));
		((ImageView)((GridPane)vboxPlayerList.getChildren().get(moteur.getCurrentPlayer().getNum())).getChildren().get(4)).setFitHeight(listConstraintSize+(20/3));
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


	
	public void updateBoardWithIA(Card cardToPlay, Couple posToPlay){
		if (cardToPlay.getType().equals(Card_t.gallery)) {
			GalleryCard cardToPut = (GalleryCard) cardToPlay;
			if(!moteur.getBoard().isCompatibleWithNeighbors(cardToPut, new Couple(posToPlay.getLine(), posToPlay.getColumn()))){
				cardToPut = cardToPut.rotate();
			}
			addGalleryCard(cardToPut, posToPlay.getColumn(), posToPlay.getLine());
		}
		else if (cardToPlay.getType().equals(Card_t.action)) {
			if (((ActionCard)(cardToPlay)).getAction().equals(ActionCard.Action.Crumbling)) {
        		removeGalleryCard(posToPlay.getColumn(), posToPlay.getLine());
        	}
        	else if (((ActionCard)(cardToPlay)).getAction().equals(ActionCard.Action.Map)) {
        		// TODO - add indications so players can see what AI is doing
        	}
		}
    }
	
	
	
	public void updatePlayerListWithIA (Card cardToPlay, Player aPlayerInList) {
    	if (((ActionCard)(cardToPlay)).getAction().equals(ActionCard.Action.Repare)) {
    		if (((RepareSabotageCard)(cardToPlay)).getTool().equals(Tools.Lantern)) {
                ImageView viewConstraint = (ImageView)getNodeFromGridPane((GridPane)vboxPlayerList.getChildren().get(aPlayerInList.getNum()), listConstraintLanternPos.getColumn(), listConstraintLanternPos.getLine());
                viewConstraint.setImage(new Image("ressources/lanterne.png"));
            }
			else if (((RepareSabotageCard)(cardToPlay)).getTool().equals(Tools.Pickaxe)) {
                ImageView viewConstraint = (ImageView)getNodeFromGridPane((GridPane)vboxPlayerList.getChildren().get(aPlayerInList.getNum()), listConstraintPickaxePos.getColumn(), listConstraintPickaxePos.getLine());
                viewConstraint.setImage(new Image("ressources/pioche.png"));
            }
			else if (((RepareSabotageCard)(cardToPlay)).getTool().equals(Tools.Wagon)) {
                ImageView viewConstraint = (ImageView)getNodeFromGridPane((GridPane)vboxPlayerList.getChildren().get(aPlayerInList.getNum()), listConstraintWagonPos.getColumn(), listConstraintWagonPos.getLine());
                viewConstraint.setImage(new Image("ressources/wagon.png"));
            }
    	}
    	else if (((ActionCard)(cardToPlay)).getAction().equals(ActionCard.Action.Sabotage)) {
    		if (((RepareSabotageCard)(cardToPlay)).getTool().equals(Tools.Lantern)) {
				ImageView viewConstraint = (ImageView)getNodeFromGridPane((GridPane)vboxPlayerList.getChildren().get(aPlayerInList.getNum()), listConstraintLanternPos.getColumn(), listConstraintLanternPos.getLine());
				viewConstraint.setImage(new Image("ressources/lanterne_detruite.png"));
			}
			else if (((RepareSabotageCard)(cardToPlay)).getTool().equals(Tools.Pickaxe)) {
				ImageView viewConstraint = (ImageView)getNodeFromGridPane((GridPane)vboxPlayerList.getChildren().get(aPlayerInList.getNum()), listConstraintPickaxePos.getColumn(), listConstraintPickaxePos.getLine());
				viewConstraint.setImage(new Image("ressources/pioche_detruite.png"));
			}
			else if (((RepareSabotageCard)(cardToPlay)).getTool().equals(Tools.Wagon)) {
				ImageView viewConstraint = (ImageView)getNodeFromGridPane((GridPane)vboxPlayerList.getChildren().get(aPlayerInList.getNum()), listConstraintWagonPos.getColumn(), listConstraintWagonPos.getLine());
				viewConstraint.setImage(new Image("ressources/wagon_detruit.png"));
			}
    	}
    	
    	updateCurrentPlayerConstraints();
	}
	

	
	public void addGalleryCard(GalleryCard c, int column, int line){
		GameBoard.gridPaneBoard.add(getImageCard(c).getImageView(), (column + GameBoard.startCardX), (line + GameBoard.startCardY));
	}
	
	public void removeGalleryCard(int column, int line){
		GameBoard.gridPaneBoard.getChildren().remove(getNodeFromGridPane(GameBoard.gridPaneBoard, (column + GameBoard.startCardX), (line + GameBoard.startCardY)));
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




	@FXML
	void handleButtonAideInGame(ActionEvent event){
            Stage stage = new Stage();
            TransparentStage tspStage= new TransparentStage();
            tspStage.start(stage,borderPaneInteract.getParent().getScene().getWidth() , borderPaneInteract.getParent().getScene().getHeight());

	}

	@FXML
	void handleButtonRecommencer(ActionEvent event){
		System.out.println("Remise à zéro de la partie");
	}
}
