package IHM;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainLoader extends Application {
	
	private static double SCREEN_WIDTH = 1080;
	private static double SCREEN_HEIGHT = 720;
	
	private File fileOptions = new File("saboteur.cfg");
	
	private static String musicFile = "../ressources/pull-up-a-chair.mp3";

	
	private void initGameConfig () {
		try {
		    PrintWriter writer = new PrintWriter(fileOptions);
		    writer.println(":Music:" + "1" + ":");
		    writer.println(":Effects:" + "1" + ":");
		    writer.println(":Resolution:" + "1080*720" + ":");
	    	writer.println(":Fullscreen:" + "true" + ":");
		    writer.close();
		} catch (IOException e) {
			System.out.println("ERROR --> Couldn't initialize 'saboteur.cfg'.");
		}
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent parentMainMenu = FXMLLoader.load(getClass().getResource("MainLoader.fxml"));

		// Initialize saboteur.cfg
		initGameConfig();
		
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
	}

    public static void main (String[] args) {
        launch(args);
    }
    
}
