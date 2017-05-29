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
	
	
	
	@FXML
	public void initialize() throws IOException {
		// Game Board
		AnchorPane anchorPaneGameBoard = FXMLLoader.load(getClass().getResource("GameBoard.fxml"));
		
		// Game Interact
		AnchorPane anchorPaneGameInteract = FXMLLoader.load(getClass().getResource("GameInteract.fxml"));
		anchorPaneGameInteract.setPickOnBounds(false);


		

		stackPaneGameLoader.getChildren().setAll(anchorPaneGameBoard, anchorPaneGameInteract);
	}
	
}
