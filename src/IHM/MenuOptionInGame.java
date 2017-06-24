package IHM;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MenuOptionInGame {

    private File fileOptions = new File("saboteur.cfg");
    private Double initialMusicSliderValue;

    @FXML
    private Slider sliderMusique;

    @FXML
    private Slider sliderEffets;

    @FXML
    void handleRetour(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MenuPause.fxml"));
        Stage stage = (Stage) sliderEffets.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void handleSliderEffect(MouseEvent event) {
        System.out.println("Modification du volume des effets");
    }

    @FXML
    void handleSliderMusique(MouseEvent event) {
        System.out.println("Modification du volume de la musique");
        MainLoader.mediaPlayerMusic.setVolume(sliderMusique.getValue()/100);
    }

    @FXML
    public void initialize () {
        String string;
        try {
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(fileOptions).useDelimiter(":");
            while (scanner.hasNext()) {
                string = scanner.next();
                if (string.equals("Music")) {
                    string = scanner.next();
                    initialMusicSliderValue = Double.parseDouble(string);
                    sliderMusique.setMin(0);
                    sliderMusique.setMax(100);
                    sliderMusique.setMajorTickUnit(10);
                    sliderMusique.setShowTickMarks(true);
                    sliderMusique.setValue(initialMusicSliderValue);
                }
                else if (string.equals("Effects")) {
                    string = scanner.next();
                    sliderEffets.setMin(0);
                    sliderEffets.setMax(100);
                    sliderEffets.setMajorTickUnit(10);
                    sliderEffets.setShowTickMarks(true);
                    sliderEffets.setValue(Double.parseDouble(string));
                }

//                else if (string.equals("Fullscreen")) {
//                    string = scanner.next();
//                    if (string.equals("true")) {
//                        checkBoxFullscreen.setSelected(true);
//                        choiceBox.setDisable(true);
//                    }
//                }
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("ERROR --> Couldn't load previous settings from 'saboteur.cfg'.");
        }
    }

}
