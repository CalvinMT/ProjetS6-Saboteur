package IHM;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;


public class MenuMain {
	
	@FXML
	private AnchorPane anchorPaneMainMenu;
	
	// --------------- Controllers ---------------
	@FXML
    public void handleLocalGame() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("MenuCreationPartie.fxml"));
        anchorPaneMainMenu.getChildren().setAll(anchorPane);
    }
	
	@FXML
    public void handleOptions() throws IOException {
		AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("MenuOptions.fxml"));
		anchorPaneMainMenu.getChildren().setAll(anchorPane);
    }
	
	@FXML
    public void handleQuitProgram() {
        Platform.exit();
    }
	
	
	// --------------- ----------- ---------------
	
}
