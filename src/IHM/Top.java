package IHM;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Top {

	@FXML
	private Button buttonMenuIngame;

	@FXML
	private Button buttonAideInGame;

	@FXML
	private Label labelNumManche;

	@FXML
	void handleButtonMenuInGame(ActionEvent event){
		Stage stage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("MenuPause.fxml"));
			stage.setScene(new Scene(root));
			stage.setTitle("Pause");
			stage.initStyle(StageStyle.UNDECORATED);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(buttonMenuIngame.getScene().getWindow());
			stage.showAndWait();
		}catch(Exception e){
			System.out.println("Erreur" + e);
		}
	}

	@FXML
	void handleButtonAideInGame(ActionEvent event){
		System.out.println("Tu veux de l'aide?");
	}
    
}