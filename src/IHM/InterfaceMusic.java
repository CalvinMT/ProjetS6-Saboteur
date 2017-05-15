package IHM;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public interface InterfaceMusic {
	
	Media sound = new Media(new File("bin/ressources/pull-up-a-chair.mp3").toURI().toString());
	MediaPlayer mediaPlayerMusic = new MediaPlayer(sound);
	
}
