package IHM;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class GameInteract {
	
	private int numberOfCardsInHand;
	
	private ImageView []cardsInHand;
	
	@FXML
	public void initialize () {
		numberOfCardsInHand = 4;
		for (int i=0; i < numberOfCardsInHand; i++) {
			
		}
	}
	
}
