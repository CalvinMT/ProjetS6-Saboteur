package IHM;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MenuOptions {
	
	private File fileOptions = new File("saboteur.cfg");
	private ObservableList <String>resolutionList = FXCollections.observableArrayList("1280*720","1366*768","1600*900","1920*1080");
	
	private Double initialMusicSliderValue;
	
	@FXML
	public AnchorPane anchorPaneOptions;
	
	@FXML
	private Slider sliderMusic;
	@FXML
	private Slider sliderEffects;
	@FXML
	private ChoiceBox<String> choiceBox;
	@FXML
	private Text textApplied;
	@FXML
	private CheckBox checkBoxFullscreen;

	
	// --------------- Controllers ---------------
	@FXML
    public void handleReturnMenu () throws IOException {
		MainLoader.mediaPlayerMusic.setVolume(initialMusicSliderValue/100);
		AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("MenuMain.fxml"));
		MainLoader.autoResizeToResolution(anchorPane);
		anchorPaneOptions.getChildren().setAll(anchorPane);
    }
	
	@FXML
	public void handleSliderMusic () {
		MainLoader.mediaPlayerMusic.setVolume(sliderMusic.getValue()/100);
	}

	@FXML
    public void handleCheckBoxFullscreen () throws IOException {
		if (checkBoxFullscreen.isSelected()) {
			choiceBox.setDisable(true);
		}
		else {
			choiceBox.setDisable(false);
		}
    }
	
	@FXML
    public void handleApply () throws IOException {
		try {
			initialMusicSliderValue = sliderMusic.getValue();
		    PrintWriter writer = new PrintWriter(fileOptions);
		    writer.println(":Music:" + sliderMusic.getValue() + ":");
		    writer.println(":Effects:" + sliderEffects.getValue() + ":");
		    writer.println(":Resolution:" + choiceBox.getValue() + ":");
		    if (checkBoxFullscreen.isSelected()) {
		    	MainLoader.primaryStage.setFullScreen(true);
		    	writer.println(":Fullscreen:" + true + ":");
		    }
		    else {
		    	MainLoader.primaryStage.setFullScreen(false);
		    	String[] stringList = choiceBox.getValue().split("\\*");
		    	double newScreenWidth = Double.parseDouble(stringList[0]);
		    	double newScreenHeight = Double.parseDouble(stringList[1]);
			    MainLoader.primaryStage.setWidth(newScreenWidth);
			    MainLoader.primaryStage.setHeight(newScreenHeight);
			    MainLoader.primaryStage.centerOnScreen();
		    	writer.println(":Fullscreen:" + false + ":");
		    	MainLoader.autoResizeToResolution(anchorPaneOptions);
		    }
		    writer.close();
			textApplied.setVisible(true);
		    FadeTransition fadeTextApplied = new FadeTransition(Duration.millis(5000), textApplied);
		    fadeTextApplied.setFromValue(1.0);
		    fadeTextApplied.setToValue(0.0);
		    fadeTextApplied.setCycleCount(1);
		    fadeTextApplied.play();

		} catch (IOException e) {
			System.out.println("ERROR --> Couldn't apply changes.");
		}
    }
	
	
	// --------------- ----------- ---------------
	
	@FXML
	public void initialize () {
		String string;
		textApplied.setVisible(false);
		choiceBox.setItems(resolutionList);
		try {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(fileOptions).useDelimiter(":");
			while (scanner.hasNext()) {
				string = scanner.next();
				if (string.equals("Music")) {
					string = scanner.next();
					initialMusicSliderValue = Double.parseDouble(string);
					sliderMusic.setMin(0);
					sliderMusic.setMax(100);
					sliderMusic.setMajorTickUnit(10);
					sliderMusic.setShowTickMarks(true);
					sliderMusic.setValue(initialMusicSliderValue);
				}
				else if (string.equals("Effects")) {
					string = scanner.next();
					sliderEffects.setMin(0);
					sliderEffects.setMax(100);
					sliderEffects.setMajorTickUnit(10);
					sliderEffects.setShowTickMarks(true);
					sliderEffects.setValue(Double.parseDouble(string));
				}
				else if (string.equals("Resolution")) {
					string = scanner.next();
					choiceBox.setValue(string);
				}
				else if (string.equals("Fullscreen")) {
					string = scanner.next();
					if (string.equals("true")) {
						checkBoxFullscreen.setSelected(true);
						choiceBox.setDisable(true);
					}
				}
			}
			scanner.close();
		} catch (Exception e) {
			System.out.println("ERROR --> Couldn't load previous settings from 'saboteur.cfg'.");
		}
	}
	
}
