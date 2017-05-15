package IHM;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class GameLoader {
	
	@FXML
	private AnchorPane anchorPaneGameLoader;
	
	@FXML
	public void initialize() throws IOException {
		anchorPaneGameLoader = (AnchorPane) anchorPaneGameLoader.lookup("#anchorPaneGameLoader");
		AnchorPane anchorPaneGameBoard = FXMLLoader.load(getClass().getResource("GameBoard.fxml"));
		anchorPaneGameLoader.getChildren().setAll(anchorPaneGameBoard);
	}
	
}
