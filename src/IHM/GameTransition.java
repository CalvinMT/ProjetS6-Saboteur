package IHM;

import Saboteur.Moteur;
import Saboteur.Saboteur;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameTransition {
	
	Moteur moteur = Saboteur.getMoteur();
	
	@FXML
	AnchorPane anchorPaneGameTransition;
	
	@FXML
	VBox vboxTransition;
	
	@FXML
	Text textTransition;
	@FXML
	Button buttonTransition;
	
	
	
	@FXML
	public void handleButtonTransitionAction () {
		anchorPaneGameTransition.setVisible(false);
	}
	
	
	
	@FXML
	public void initialize () {
		anchorPaneGameTransition.setTranslateY(MainLoader.scene.getHeight()-anchorPaneGameTransition.getPrefHeight());
		anchorPaneGameTransition.setPrefWidth(MainLoader.scene.getWidth());
		
		textTransition.setText("C'est au tour de " + moteur.getCurrentPlayer().getPlayerName() + ".");

		moteur.setGameTransition(this);
		
		vboxTransition.setPrefWidth(textTransition.getLayoutBounds().getWidth());
		vboxTransition.setTranslateX((anchorPaneGameTransition.getPrefWidth()/2)-(vboxTransition.getPrefWidth()/2));
	}

	public void updatePseudo(){
        textTransition.setText("C'est au tour de " + moteur.getCurrentPlayer().getPlayerName() + ".");
    }
	
}
