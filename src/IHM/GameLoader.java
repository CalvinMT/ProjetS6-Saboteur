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
	
	/*@FXML
	private AnchorPane anchorPaneGameLoaderBoard;
	@FXML
	private AnchorPane anchorPaneGameLoaderInteract;*/
	
	@FXML
	public void initialize() throws IOException {
		// Game Board
		//anchorPaneGameLoaderBoard = (AnchorPane) anchorPaneGameLoaderBoard.lookup("#anchorPaneGameLoaderBoard");
		AnchorPane anchorPaneGameBoard = FXMLLoader.load(getClass().getResource("GameBoard.fxml"));
		
		// Game Interact
		//anchorPaneGameLoaderInteract = (AnchorPane) anchorPaneGameLoaderInteract.lookup("#anchorPaneGameLoaderInteract");
		AnchorPane anchorPaneGameInteract = FXMLLoader.load(getClass().getResource("GameInteract.fxml"));
		anchorPaneGameInteract.setPickOnBounds(false);
		

		stackPaneGameLoader.getChildren().setAll(anchorPaneGameBoard, anchorPaneGameInteract);
	}
	
}
