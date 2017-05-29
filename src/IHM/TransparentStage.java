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

    public void start(Stage stage, double width, double height) {
        stage.initStyle(StageStyle.TRANSPARENT);
        ImageView imageview =new ImageView("ressources/calqueaide.png");
        imageview.setFitHeight(height);
        imageview.setFitWidth(width);
        AnchorPane pane = new AnchorPane();
        Button button = new Button();
        button.setId("ButtonQuitterAide"); 
        button.setGraphic(pane);
        ImageView imageview2 =new ImageView("ressources/aideneg.png");
        imageview2.setFitHeight(width/36);
        imageview2.setFitWidth(width/36);
        button.setGraphic(imageview2);
        button.setMaxSize(width/32, width/32);
        button.setTranslateX(width/32);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                 ((Stage)button.getScene().getWindow()).close();
                }
             });
        pane.getChildren().add(button);
        pane.getChildren().add(imageview);
        final Scene scene = new Scene(pane,width, height);
        scene.setFill(null);
        stage.setScene(scene);
        stage.show();
    }
    

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}