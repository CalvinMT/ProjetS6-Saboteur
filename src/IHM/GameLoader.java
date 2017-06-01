package IHM;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class GameLoader {
	
	@FXML
	private BorderPane borderPaneGameLoader;
	
	@FXML
	private StackPane stackPaneGameLoader;
	
	public static AnchorPane anchorPaneGameBoard;
	public static AnchorPane anchorPaneGameInteract;
	public static AnchorPane anchorPaneGameTransition;
	
	
	
	@FXML
	public void initialize() throws IOException {
		// Game Board
		anchorPaneGameBoard = FXMLLoader.load(getClass().getResource("GameBoard.fxml"));
		
		// Game Interact
		anchorPaneGameInteract = FXMLLoader.load(getClass().getResource("GameInteract.fxml"));
		anchorPaneGameInteract.setPickOnBounds(false);
		
		// Game Transition
		anchorPaneGameTransition = FXMLLoader.load(getClass().getResource("GameTransition.fxml"));
		anchorPaneGameTransition.setVisible(false);
		
		
		stackPaneGameLoader.getChildren().setAll(anchorPaneGameBoard, anchorPaneGameInteract, anchorPaneGameTransition);
	}
	
}
