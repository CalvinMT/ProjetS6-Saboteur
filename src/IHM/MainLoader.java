package IHM;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import Board.Couple;
import Cards.Card;
import Cards.GalleryCard;
import Player.Player;
import Player.IA;
import Saboteur.Moteur;
import Saboteur.Saboteur;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import Player.Move;


public class MainLoader extends Application {
	
	public static Stage primaryStage; // XXX - Not good looking.
	public static Scene scene; // XXX - Not good looking.
	public static MediaPlayer mediaPlayerMusic; // XXX - Not good looking.
	public static AnchorPane anchorPaneMainLoader; // XXX - Not good looking.
	public static AnchorPane anchorPaneMenuMain; // XXX - Not good looking.

	private double SCREEN_WIDTH;
	private double SCREEN_HEIGHT;

	static public final long shortWaitingTime = 1000;

	private double volumeMusic;
	// TOTO - private double volumeEffects;
	
	private File fileOptions = new File("saboteur.cfg");

	
	private void initGameConfig () {
		try {
		    PrintWriter writer = new PrintWriter(fileOptions);
		    writer.println(":Music:" + "100.0" + ":");
		    writer.println(":Effects:" + "100.0" + ":");
		    writer.println(":Resolution:" + "1280*720" + ":");
	    	writer.println(":Fullscreen:" + "false" + ":");
		    writer.close();
		} catch (IOException e) {
			System.out.println("ERROR --> Couldn't initialize 'saboteur.cfg'.");
		}
	}

	
	private void setToFullscreen (Stage stage) {
		try {
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
			stage.setX(primaryScreenBounds.getMinX());
			stage.setY(primaryScreenBounds.getMinY());
			stage.setWidth(primaryScreenBounds.getWidth());
			stage.setHeight(primaryScreenBounds.getHeight());
		} catch (Exception e) {
			System.out.println("ERROR --> Couldn't set to fullscreen.");
		}
	}
	
	
	public static void autoResizeToResolution (AnchorPane anchorPaneMenu) {
		double width = MainLoader.primaryStage.getWidth();
		double height = MainLoader.primaryStage.getHeight();
		if (anchorPaneMainLoader != null) {
			anchorPaneMainLoader.setPrefWidth(width-(width/3));
			anchorPaneMainLoader.setPrefHeight(height-300); // XXX - 217
			if (anchorPaneMenu != null) {
				anchorPaneMenu.setPrefWidth(anchorPaneMainLoader.getPrefWidth());
				anchorPaneMenu.setPrefHeight(anchorPaneMainLoader.getPrefHeight());
			}
		}
	}
	
	
	@Override
	public void start(Stage stage) throws Exception {
		primaryStage = stage;
		
		Parent parentMainMenu = FXMLLoader.load(getClass().getResource("MainLoader.fxml"));

		// Initialize saboteur.cfg
		if (!fileOptions.exists()) {
			initGameConfig();
		}
		
		// Getting screen resolution
		try {
			String string;
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(fileOptions).useDelimiter(":");
			while (scanner.hasNext()) {
				string = scanner.next();
				if (string.equals("Music")) {
					string = scanner.next();
					volumeMusic = Double.parseDouble(string);
				}
				else if (string.equals("Effects")) {
					string = scanner.next();
					// TODO - volumeEffects = Double.parseDouble(string);
				}
				else if (string.equals("Resolution")) {
					string = scanner.next();
					String[] stringList = string.split("\\*");
					SCREEN_WIDTH = Double.parseDouble(stringList[0]);
					SCREEN_HEIGHT = Double.parseDouble(stringList[1]);
				}
				else if (string.equals("Fullscreen")) {
					string = scanner.next();
					if (string.equals("true")) {
						setToFullscreen(primaryStage);
						primaryStage.setFullScreen(true);
					}
				}
			}
			scanner.close();
		} catch (Exception e) {
				System.out.println("ERROR --> Couldn't retrieve resolution from file 'saboteur.cfg'.");
		}
		
		// Music & Effects played in background
		try {
			Media music = new Media(new File("ressources/pull-up-a-chair.mp3").toURI().toString());
			mediaPlayerMusic = new MediaPlayer(music);
			mediaPlayerMusic.setVolume(volumeMusic/100);
			mediaPlayerMusic.setStartTime(new Duration(14600));
			//mediaPlayerMusic.setStopTime(new Duration(135700));
			mediaPlayerMusic.play();
			Timeline timeline = new Timeline(new KeyFrame(new Duration(140000), new KeyValue(mediaPlayerMusic.volumeProperty(), 0)));
			mediaPlayerMusic.setOnEndOfMedia(new Runnable(){
				@Override
				public void run() {
					mediaPlayerMusic.stop();
					timeline.stop();
					mediaPlayerMusic.setStartTime(new Duration(14600));
					mediaPlayerMusic.play();
					timeline.play();
				}
			});
			timeline.play();
		} catch (Exception e) {
			System.out.println("ERROR --> Couldn't create MediaPlayer.");
		}
		
		primaryStage.setTitle("Saboteur");
		scene = new Scene(parentMainMenu, SCREEN_WIDTH, SCREEN_HEIGHT);
		primaryStage.setWidth(SCREEN_WIDTH);
		primaryStage.setHeight(SCREEN_HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);

		// Load MenuMain.fxml
		anchorPaneMainLoader = (AnchorPane) parentMainMenu.lookup("#anchorPaneMainLoader");
		anchorPaneMenuMain = FXMLLoader.load(getClass().getResource("MenuMain.fxml"));
		anchorPaneMainLoader.getChildren().setAll(anchorPaneMenuMain);
		
		// Automatic Resizing
		autoResizeToResolution(anchorPaneMenuMain);
		
		
		primaryStage.show();

        ///// POUR FAIRE JOUER L'IA


		AnimationTimer game = new AnimationTimer() {

			Random rand = new Random();

			@Override
			public void handle(long temps) {

				Moteur engine = Saboteur.getMoteur();

				if(engine != null){
					Player player = engine.getCurrentPlayer();

					if(player.getDifficulty() != Player.Difficulty.Player){

						switch(engine.getState()){
							// choix des roles
							case ChooseRole:
//						if((temps > engine.getEcheance()) && engine.getCurrentPlayer().pastTime()){
								if(!engine.roleSet()){


									int numRoleCards;

									do {
										numRoleCards = rand.nextInt(engine.getNbRoleCards());
									} while(engine.isTaken(numRoleCards));

									try {

//									FXMLLoader loader =  new FXMLLoader();
//									BorderPane border = loader.load(getClass().getResource("ChoixRole.fxml").openStream());
//									ChoixRole choixroleControler = loader.<ChoixRole>getController();


										Card c = engine.getRoleCard(numRoleCards);
										engine.setTrueTaken(numRoleCards);
										engine.getCurrentPlayer().assignRole(c);



										engine.getChoixroleControleur().updateGraphic(numRoleCards);
										engine.nextPlayer();
										engine.getChoixroleControleur().updateText();




										try {

											Thread.sleep(shortWaitingTime);
										} catch (Exception ex){
											System.err.println("Erreur sleep");
										}

									} catch (Exception ex){
										System.err.println("Erreur lors du choix des roles");
										ex.printStackTrace();
									}

//								engine.promptPlayersRole();

									if(engine.allRoleAreSet()){
										engine.getChoixroleControleur().configEndChoose();
									}
								}

								break;

							// déroulement d'une manche
							case Game:

								Move moveIA = null;

								Card.Card_t type = Card.Card_t.gallery;

//								Card.Card_t type = moveIA.getCard().getType();

								switch (type){

									case gallery:
										System.out.println("Carte Tunnel");

										Card cardToPlay = moveIA.getCard();
										Couple posToPlay = moveIA.getPositionTarget();
										GalleryCard cardToPut;


										if(!engine.getBoard().isCompatibleWithNeighbors((GalleryCard) cardToPlay, new Couple(posToPlay.getLine(), posToPlay.getColumn()))){
											cardToPut = ((GalleryCard) cardToPlay).rotate();
										} else {
											cardToPut = (GalleryCard) cardToPlay;
										}

										engine.getGameInteractControler().updateBoardWithIA(cardToPut, posToPlay);

										engine.getBoard().putCard((GalleryCard) cardToPut, posToPlay.getLine(), posToPlay.getColumn());

										player.getPlayableCards().removeCard(cardToPlay);
										engine.getGameInteractControler().checkEndGame();
//										System.out.println(player);



										try {
											Thread.sleep(shortWaitingTime);
										} catch (Exception ex){
											System.err.println("Erreur sleep");
										}

//										System.out.println(engine.getBoard().mine());



										break;

									case action:
										System.out.println("Carte Action");
										break;

									default:
										System.err.println("Erreur move Impossible");
										break;

								}


								break;

							// choix de l'or
							case ChooseGold:
								//TODO chooseCard
								// not implemented yet
								break;
							default:
								break;
						}
					}

				}



			}
		};

		game.start();
	}

    public static void main (String[] args) {
        launch(args);
    }
    
}
