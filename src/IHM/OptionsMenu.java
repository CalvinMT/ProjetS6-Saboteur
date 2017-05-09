import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class OptionsMenu {
	
	private File fileOptions = new File("saboteur.cfg");
	private ObservableList <String>resolutionList = FXCollections.observableArrayList("1080*720");
	
	@FXML
	private AnchorPane anchorPaneOptions;
	
	@FXML
	private ChoiceBox<String> choiceBox;
	
	@FXML
	private Text textApplied;
	
	@FXML
	private Boolean checkBoxFullscreen;

	
	// --------------- Controllers ---------------
	@FXML
    public void handleReturnMenu () throws IOException {
		AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		anchorPaneOptions.getChildren().setAll(anchorPane);
    }

	@FXML
    public void handleCheckBoxFullscreen () throws IOException {
		checkBoxFullscreen // TODO
    }
	
	@FXML
    public void handleApply () throws IOException {
		try {
		    PrintWriter writer = new PrintWriter(fileOptions);
		    writer.println("Music=" + 100); // TODO
		    writer.println("Effects=" + 100); // TODO
		    writer.println("Resolution=");
		    if (checkBoxFullscreen) {
		    	writer.println("Fullscreen=" + true);
		    }
		    else {
		    	writer.println("Fullscreen=" + false);
		    }
		    writer.close();
		    textApplied.setVisible(true);
		} catch (IOException e) {
		}
		System.out.println("pressed apply");
    }
	
	
	// --------------- ----------- ---------------
	
	@FXML
	public void initialize () {
		textApplied.setVisible(false);
		choiceBox.setItems(resolutionList);
		try {
			Scanner scanner = new Scanner(fileOptions).useDelimiter("=");
			while (scanner.hasNext()) {
				if (scanner.next().equals("Music")) {
					// TODO
				}
				else if (scanner.next().equals("Effects")) {
					// TODO
				}
				else if (scanner.next().equals("Resolution")) {
					// TODO
				}
				else if (scanner.next().equals("Fullscreen")) {
					if (scanner.next().equals(true)) {
						checkBoxFullscreen.fire();
					}
				}
			}
		} catch (Exception e) {
		}
	}
	
}
