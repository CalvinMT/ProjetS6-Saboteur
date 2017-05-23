package IHM;

import javafx.application.Application;
import javafx.scene.Scene;
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
        pane.getChildren().add(imageview);
        final Scene scene = new Scene(pane,1920, 1080);
        scene.setFill(null);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}