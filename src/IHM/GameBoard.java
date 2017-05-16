package IHM;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class GameBoard {
	
	private int cardsWidth = 10;
	private int cardsHeight = 10;
	
	private int gridWidth = 90;
	private int gridHeight = 89;
	
	private Stage stage;
	private double pressedX;
	private double pressedY;
	
	@FXML
	GridPane gridPaneBoard;
	
	@FXML
	public void handleClickGrid (MouseEvent event) {
		int clickX = (int) (event.getX()/cardsWidth);
		int clickY = (int) (event.getY()/cardsHeight);
		gridPaneBoard.add(new ImageView(new Image("ressources/carte_test_10_10.png")), clickX, clickY);
	}
	
	@FXML
	public void handlePressedGrid (MouseEvent event) {
		stage = (Stage) gridPaneBoard.getScene().getWindow();
		pressedX = stage.getX() - event.getScreenX();
		pressedY = stage.getY() - event.getScreenY();
	}
	
	@FXML
	public void handleDragGrid (MouseEvent event) {
		gridPaneBoard.setPadding(new Insets(event.getScreenY() + pressedY, event.getScreenX() + pressedX, event.getScreenY() + pressedY, event.getScreenX() + pressedX));
		
		/*stage.setX(event.getScreenX() + pressedX);
		stage.setY(event.getScreenY() + pressedY);*/
	}
	
	@FXML
	public void initialize () {
        for (int i = 0 ; i < gridWidth ; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints(cardsWidth);
            colConstraints.setHgrow(Priority.NEVER);
            gridPaneBoard.getColumnConstraints().add(colConstraints);
        }
        for (int i = 0 ; i < gridHeight ; i++) {
            RowConstraints rowConstraints = new RowConstraints(cardsHeight);
            rowConstraints.setVgrow(Priority.NEVER);
            gridPaneBoard.getRowConstraints().add(rowConstraints);
        }
		for (int i=0; i < gridWidth; i++) {
			for (int j=0; j < gridHeight; j++) {
				if (i == gridWidth/2  &&  j == gridHeight/2) {
					ImageView viewCard = new ImageView("ressources/carte_test_10_10.png");
					gridPaneBoard.add(viewCard, i, j);
				}
				else if (i == ((gridWidth/2)+8)) {
					if (j == ((gridHeight/2)-2)) {
						ImageView viewCard = new ImageView("ressources/carte_test_10_10.png");
						gridPaneBoard.add(viewCard, i, j);
					}
					else if (j == (gridHeight/2)) {
						ImageView viewCard = new ImageView("ressources/carte_test_10_10.png");
						gridPaneBoard.add(viewCard, i, j);
					}
					else if (j == ((gridHeight/2)+2)) {
						ImageView viewCard = new ImageView("ressources/carte_test_10_10.png");
						gridPaneBoard.add(viewCard, i, j);
					}
				}
			}
		}
		
		gridPaneBoard.setScaleX(10);
		gridPaneBoard.setScaleY(10);
	}

}
