package IHM;

import java.io.File;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class MenuMain {
	
	@FXML
	private AnchorPane anchorPaneMenuMain;
	
	
	// --------------- Controllers ---------------
	@FXML
    public void handleLocalGame() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("MenuCreationPartie.fxml"));
        anchorPaneMenuMain.getChildren().setAll(anchorPane);
		MainLoader.autoResizeToResolution(anchorPane);
    }

    @FXML
	public void handleButtonChargerPartie(){
		// TODO chargement de partie
		Stage stageJeux = (Stage)((Stage) buttonChargerPartie.getScene().getWindow()).getOwner();

		final FileChooser fileChooser = new FileChooser();
		configureFileChooser(fileChooser);
		File file = fileChooser.showOpenDialog(stageJeux);
	}

	private static void configureFileChooser(final FileChooser fc){
		fc.setTitle("Chargement d'une partie");
		fc.setInitialDirectory(
				new File(System.getProperty("user.dir"))
		);
	}
	
	@FXML
    public void handleOptions() throws IOException {
		AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("MenuOptions.fxml"));
		anchorPaneMenuMain.getChildren().setAll(anchorPane);
		MainLoader.autoResizeToResolution(anchorPane);

    }
	
	@FXML
    public void handleQuitProgram() {
        Platform.exit();
    }
	
	
	// --------------- ----------- ---------------
	
	@FXML
	Button buttonLocal;
	@FXML
	Button buttonChargerPartie;
	@FXML
	Button buttonTutorial;
	@FXML
	Button buttonOptions;
	@FXML
	Button buttonQuit;
	
	@FXML
	public void initialize () {
		buttonLocal.setPrefWidth(Double.MAX_VALUE);
		buttonChargerPartie.setPrefWidth(Double.MAX_VALUE);
		buttonTutorial.setPrefWidth(Double.MAX_VALUE);
		buttonOptions.setPrefWidth(Double.MAX_VALUE);
		buttonQuit.setPrefWidth(Double.MAX_VALUE);
	}
	
}
