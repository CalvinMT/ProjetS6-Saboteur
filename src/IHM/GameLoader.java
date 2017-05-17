package IHM;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class GameLoader {
	
	@FXML
	private BorderPane borderPaneGameLoader;
	
	@FXML
	private AnchorPane anchorPaneGameLoaderBoard;
	@FXML
	private AnchorPane anchorPaneGameLoaderTop;
	@FXML
	private AnchorPane anchorPaneGameLoaderBottom;
	@FXML
	private AnchorPane anchorPaneGameLoaderRight;
	
	@FXML
	public void initialize() throws IOException {
		// Game Board
		anchorPaneGameLoaderBoard = (AnchorPane) anchorPaneGameLoaderBoard.lookup("#anchorPaneGameLoaderBoard");
		AnchorPane anchorPaneGameBoard = FXMLLoader.load(getClass().getResource("GameBoard.fxml"));
		
		// Game Cards
		// TODO
		
		// Game Infos
		/*anchorPaneGameLoaderInfos = (AnchorPane) anchorPaneGameLoaderInfos.lookup("#anchorPaneGameLoaderInfos");
		AnchorPane anchorPaneGameInfos = FXMLLoader.load(getClass().getResource("GameInfos.fxml"));*/
		
		
		
		
		// Game Top
		/*anchorPaneGameLoaderTop = (AnchorPane) anchorPaneGameLoaderTop.lookup("#anchorPaneGameLoaderTop");
		AnchorPane anchorPaneGameTop = FXMLLoader.load(getClass().getResource("Top.fxml"));*/
		
		// Game Bottom
		/*anchorPaneGameLoaderBottom = (AnchorPane) anchorPaneGameLoaderBottom.lookup("#anchorPaneGameLoaderBottom");
		AnchorPane anchorPaneGameBottom = FXMLLoader.load(getClass().getResource("Bottom.fxml"));*/
		
		// Game Right
		/*anchorPaneGameLoaderRight = (AnchorPane) anchorPaneGameLoaderRight.lookup("#anchorPaneGameLoaderRight");
		AnchorPane anchorPaneGameRight = FXMLLoader.load(getClass().getResource("Right.fxml"));*/
		
		
		
		
		borderPaneGameLoader.getChildren().setAll(anchorPaneGameBoard);
	}
	
}
