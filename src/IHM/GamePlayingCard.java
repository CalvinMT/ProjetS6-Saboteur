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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GamePlayingCard)) return false;

		GamePlayingCard that = (GamePlayingCard) o;

		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		return imageView != null ? imageView.equals(that.imageView) : that.imageView == null;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (imageView != null ? imageView.hashCode() : 0);
		return result;
	}
}
