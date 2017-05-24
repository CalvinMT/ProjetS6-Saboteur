package IHM;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TransparentStage extends Application {

    @Override
    public void start(Stage stage) {
        stage.initStyle(StageStyle.TRANSPARENT);
        ImageView imageview =new ImageView("ressources/calqueaide.png");
        AnchorPane pane = new AnchorPane();
        Button button = new Button();
        button.setId("ButtonQuitterAide"); 
        button.setGraphic(pane);
        button.setGraphic(new ImageView("ressources/aideneg.png"));
        button.setMaxSize(25.0, 25.0);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                 ((Stage)button.getScene().getWindow()).close();
                }
             });
        pane.getChildren().add(button);
        //pane.getChildren().add(imageview);
        final Scene scene = new Scene(pane,1920, 1080);
        scene.setFill(null);
        stage.setScene(scene);
        stage.show();
    }
    
    public void handlerQuitterAide() throws IOException {
    }

    public static void main(String[] args) {
        launch(args);
    }
}