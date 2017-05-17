package IHM;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
		
		// Game Infos
		/*anchorPaneGameLoaderInfos = (AnchorPane) anchorPaneGameLoaderInfos.lookup("#anchorPaneGameLoaderInfos");
		AnchorPane anchorPaneGameInfos = FXMLLoader.load(getClass().getResource("GameInfos.fxml"));*/
		
		
		
		
		// Game Top
		anchorPaneGameLoaderTop = (AnchorPane) anchorPaneGameLoaderTop.lookup("#anchorPaneGameLoaderTop");
		AnchorPane anchorPaneGameTop = FXMLLoader.load(getClass().getResource("Top.fxml"));
		
		// Game Bottom
		/*anchorPaneGameLoaderBottom = (AnchorPane) anchorPaneGameLoaderBottom.lookup("#anchorPaneGameLoaderBottom");
		AnchorPane anchorPaneGameBottom = FXMLLoader.load(getClass().getResource("Bottom.fxml"));*/
		
		// Game Right
		/*anchorPaneGameLoaderRight = (AnchorPane) anchorPaneGameLoaderRight.lookup("#anchorPaneGameLoaderRight");
		AnchorPane anchorPaneGameRight = FXMLLoader.load(getClass().getResource("Right.fxml"));*/
		
		
		
		
		borderPaneGameLoader.getChildren().setAll(anchorPaneGameBoard, anchorPaneGameTop);
		//exitToMenuMain();
	}

	public void exitToMenuMain() throws IOException {
		if (borderPaneGameLoader != null)
			borderPaneGameLoader.getChildren().clear();
		if (anchorPaneGameLoaderBoard != null)
			anchorPaneGameLoaderBoard.getChildren().clear();
		if (anchorPaneGameLoaderBottom != null)
			anchorPaneGameLoaderBottom.getChildren().clear();
		if (anchorPaneGameLoaderTop != null)
			anchorPaneGameLoaderTop.getChildren().clear();
		if (anchorPaneGameLoaderRight != null)
			anchorPaneGameLoaderRight.getChildren().clear();
		AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("MenuMain.fxml"));
		MainLoader.anchorPaneMainLoader.getChildren().setAll(anchorPane);
		Stage stage = (Stage) MainLoader.anchorPaneMainLoader.getScene().getWindow();
		stage.setScene(new Scene(anchorPane));
		stage.show();
	}
	
}
