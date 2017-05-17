package IHM;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainLoader extends Application {
	
	public static Stage primaryStage; // XXX - Not good looking.
	public static Scene scene; // XXX - Not good looking.
	public static MediaPlayer mediaPlayerMusic; // XXX - Not good looking.
	public static AnchorPane anchorPaneMainLoader; // XXX - Not good looking.
	public static AnchorPane anchorPaneMenuMain; // XXX - Not good looking.
	
	private double SCREEN_WIDTH;
	private double SCREEN_HEIGHT;
	
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
	
	
	public static void autoResizeToResolution (double width, double height) {
		if (anchorPaneMainLoader != null) {
			anchorPaneMainLoader.setPrefWidth(width-(width/3));
			anchorPaneMainLoader.setPrefHeight(height-217); // FIXME
			if (anchorPaneMenuMain != null) {
		        anchorPaneMenuMain.setPrefWidth(anchorPaneMainLoader.getPrefWidth());
		        anchorPaneMenuMain.setPrefHeight(anchorPaneMainLoader.getPrefHeight());
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
			Media music = new Media(new File("bin/ressources/pull-up-a-chair.mp3").toURI().toString());
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
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		
		// Load MenuMain.fxml
		anchorPaneMainLoader = (AnchorPane) parentMainMenu.lookup("#anchorPaneMainLoader");
        anchorPaneMenuMain = FXMLLoader.load(getClass().getResource("MenuMain.fxml"));
        anchorPaneMainLoader.getChildren().setAll(anchorPaneMenuMain);
        
        // Automatic Resizing
        autoResizeToResolution(SCREEN_WIDTH, SCREEN_HEIGHT);
        
        
		primaryStage.show();
	}

    public static void main (String[] args) {
        launch(args);
    }
    
}
