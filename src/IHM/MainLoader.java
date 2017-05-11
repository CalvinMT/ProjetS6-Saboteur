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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainLoader extends Application {
	
	private double SCREEN_WIDTH;
	private double SCREEN_HEIGHT;
	
	private File fileOptions = new File("saboteur.cfg");
	
	private static String musicFile = "../ressources/pull-up-a-chair.mp3";

	
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
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
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
				if (string.equals("Resolution")) {
					string = scanner.next();
					String[] stringList = string.split("\\*");
					SCREEN_WIDTH = Double.parseDouble(stringList[0]);
					SCREEN_HEIGHT = Double.parseDouble(stringList[1]);
				}
				else if (string.equals("Fullscreen")) {
					string = scanner.next();
					if (string.equals("true")) {
						primaryStage.setFullScreen(true);
					}
				}
			}
			scanner.close();
		} catch (Exception e) {
				System.out.println("ERROR --> Couldn't retrieve resolution from file 'saboteur.cfg'.");
		}
			
		// Music played in background
		try {
			Media sound = new Media(getClass().getResource(musicFile).toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.setStartTime(new Duration(14600));
			//mediaPlayer.setStopTime(new Duration(135700));
			mediaPlayer.play();
			Timeline timeline = new Timeline(new KeyFrame(new Duration(140000), new KeyValue(mediaPlayer.volumeProperty(), 0)));
			mediaPlayer.setOnEndOfMedia(new Runnable(){
				@Override
				public void run() {
					mediaPlayer.stop();
					timeline.stop();
					mediaPlayer.setStartTime(new Duration(14600));
					mediaPlayer.setVolume(100);
					mediaPlayer.play();
					timeline.play();
				}
			});
			timeline.play();
		} catch (Exception e) {
			System.out.println("ERROR --> Couldn't create MediaPlayer.");
		}
		
		primaryStage.setTitle("Saboteur");
		primaryStage.setScene(new Scene(parentMainMenu, SCREEN_WIDTH, SCREEN_HEIGHT));
		primaryStage.setResizable(false);
		primaryStage.show();
		
		AnchorPane anchorPaneMainLoader = (AnchorPane) parentMainMenu.lookup("#anchorPaneMainLoader");
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        anchorPaneMainLoader.getChildren().setAll(anchorPane);
	}

    public static void main (String[] args) {
        launch(args);
    }
    
}
