package IHM;

import java.io.File;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


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
	public void handleButtonChargerPartie() throws IOException {
		// TODO chargement de partie
		AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("ChargementPartie.fxml"));
		anchorPaneMenuMain.getChildren().setAll(anchorPane);
		MainLoader.autoResizeToResolution(anchorPane);
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
	public void handleButtonTutorial() throws IOException {
		/*AnchorPane anchorpane = FXMLLoader.load(getClass().getResource("Regles.fxml"));
		anchorPaneMenuMain.getChildren().setAll(anchorpane);
		MainLoader.autoResizeToResolution((anchorpane));*/

		AnchorPane anchorPaneRegles = FXMLLoader.load(getClass().getResource("Regles.fxml"));
		Scene scene = new Scene(anchorPaneRegles);
		Stage stage = (Stage) anchorPaneMenuMain.getScene().getWindow();
		stage.hide();
		stage.setScene(scene);
		stage.show();

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
	Button buttonOptions;
	@FXML
	Button buttonQuit;
	
	@FXML
	public void initialize () {
		buttonLocal.setPrefWidth(Double.MAX_VALUE);
		buttonChargerPartie.setPrefWidth(Double.MAX_VALUE);
		buttonOptions.setPrefWidth(Double.MAX_VALUE);
		buttonQuit.setPrefWidth(Double.MAX_VALUE);
	}
	
}
