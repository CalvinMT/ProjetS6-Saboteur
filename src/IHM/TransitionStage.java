package IHM;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TransitionStage extends Application {

    public void start(Stage stage, double width, double height,String pseudo ) {
        AnchorPane pane = new AnchorPane();
        Button button = new Button("OK");
        button.setMaxSize(width/32, width/32);
        button.setTranslateX(width/2);
        button.setTranslateY(height/6);
        Text text = new Text(10, 50,"Au tour de " + pseudo);       
        text.setTranslateX((width/2)-50);
        System.out.println(pseudo);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                 ((Stage)button.getScene().getWindow()).close();
                }
             });
        pane.getChildren().add(button);
        pane.getChildren().add(text);
        final Scene scene = new Scene(pane,width, height/3);
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