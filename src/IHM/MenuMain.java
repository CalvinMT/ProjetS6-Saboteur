package IHM;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;


public class MenuMain {
	
	@FXML
	private AnchorPane anchorPaneMenuMain;
	
	
	// --------------- Controllers ---------------
	@FXML
    public void handleLocalGame() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("MenuCreationPartie.fxml"));
        anchorPaneMenuMain.getChildren().setAll(anchorPane);
    }
	
	@FXML
    public void handleOptions() throws IOException {
		AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("MenuOptions.fxml"));
		anchorPaneMenuMain.getChildren().setAll(anchorPane);
    }
	
	@FXML
    public void handleQuitProgram() {
        Platform.exit();
    }
	
	
	// --------------- ----------- ---------------
	
	@FXML
	Button buttonLocal;
	@FXML
	Button buttonOnline;
	@FXML
	Button buttonTutorial;
	@FXML
	Button buttonOptions;
	@FXML
	Button buttonQuit;
	
	@FXML
	public void initialize () {
		buttonLocal.setPrefWidth(Double.MAX_VALUE);
		buttonOnline.setPrefWidth(Double.MAX_VALUE);
		buttonTutorial.setPrefWidth(Double.MAX_VALUE);
		buttonOptions.setPrefWidth(Double.MAX_VALUE);
		buttonQuit.setPrefWidth(Double.MAX_VALUE);
	}
	
}