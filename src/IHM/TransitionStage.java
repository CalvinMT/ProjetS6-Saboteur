package IHM;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TransitionStage extends Application {

    public void start(Stage stage, double width, double height,String pseudo ) {
        stage.initStyle(StageStyle.TRANSPARENT);
        AnchorPane pane = new AnchorPane();
        Button button = new Button("OK");
        button.setPrefSize(width/8, height/16);
        button.setTranslateX(width/2 - width/12);
        button.setTranslateY(height/2);
        button.setStyle("-fx-font: bold italic 22pt \"Arial\";\n" + "-fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 );");
        Text text = new Text(30, 100,"C'est au tour de " + pseudo);
        text.setTranslateX((width/2 - 280));
        text.setTranslateY((height/4));
        text.setStyle("-fx-font: bold italic 22pt \"Arial\";\n");
        System.out.println(pseudo);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                 ((Stage)button.getScene().getWindow()).close();
                }
             });
        pane.getChildren().add(button);
        pane.getChildren().add(text);
        pane.setStyle("-fx-background-image: url('ressources/parchemin.jpg');  ");
        final Scene scene = new Scene(pane,width, height+30);        
        stage.initModality(Modality.APPLICATION_MODAL);        
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