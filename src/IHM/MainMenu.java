import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;


public class MainMenu {
	
	@FXML
	private AnchorPane anchorPaneMainMenu;
	
	// --------------- Controllers ---------------
	@FXML
    public void handleLocalGame() {
        System.out.println("pressed local game");
    }
	
	@FXML
    public void handleOptions() throws IOException {
		AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("OptionsMenu.fxml"));
		anchorPaneMainMenu.getChildren().setAll(anchorPane);
    }
	
	@FXML
    public void handleQuitProgram() {
        Platform.exit();
    }
	
	
	// --------------- ----------- ---------------
	
}
