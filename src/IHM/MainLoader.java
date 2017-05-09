import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class MainLoader extends Application {
	
	private static double SCREEN_WIDTH = 1080;
	private static double SCREEN_HEIGHT = 720;
	
	private static String musicFile = "ressources/pull-up-a-chair.mp3";

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent parentMainMenu = FXMLLoader.load(getClass().getResource("MainLoader.fxml"));

		// Music played in background
		try {
			Media sound = new Media(new File(musicFile).toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.play();
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
