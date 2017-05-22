package IHM;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GamePlayingCard {

	private String name;
	private ImageView imageView;
	
	public GamePlayingCard(String name) {
		this.name = name;
		this.imageView = new ImageView(new Image("ressources/" + name + ".png"));
	}
	
	
	public String getName () {
		return this.name;
	}
	
	public ImageView getImageView () {
		return this.imageView;
	}
}
