package IHM;

import Saboteur.Moteur;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class GameInteract {
	
	private Moteur moteur = Saboteur.Saboteur.getMoteur();
	
	private int numberOfCardsInHand;
	private ImageView []cardsInHand;
	
	@FXML
	public void initialize () {
		numberOfCardsInHand = moteur.getCurrentPlayer().nbCardHand();
		for (int i=0; i < numberOfCardsInHand; i++) {
			
		}
	}
	
}
