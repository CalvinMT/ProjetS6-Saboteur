package IHM;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class GameBoard {
	
	public static int cardsWidth = 118;
	public static int cardsHeight = 181;
	
	public static int gridWidth = 90;
	public static int gridHeight = 89;
	
	public static int startCardX = 6;
	public static int startCardY = 3;
	
	// See FIXMEs below
	private Stage stage;
	private double pressedX;
	private double pressedY;
	private double gridViewX;
	private double gridViewY;
	
	
	public static GridPane gridPaneBoard;
	
	@FXML
	private GridPane gridPaneGameBoard;
	
	@FXML
	private AnchorPane anchorPaneGameBoard;
	
	@FXML
	public void handleClickGrid (MouseEvent event) {
		int clickX = (int) (event.getX()/cardsWidth);
		int clickY = (int) (event.getY()/cardsHeight);
		gridPaneBoard.add(new ImageView(new Image("ressources/carte_test_118_181.png")), clickX, clickY);
	}
	
	@FXML
	public void handlePressedGrid (MouseEvent event) {
		// FIXME
		//stage = (Stage) gridPaneBoard.getScene().getWindow();
		/*pressedX = event.getSceneX();
		pressedY = event.getSceneY();
		gridViewX = gridPaneBoard.getTranslateX();//((GridView)(event.getSource())).getTranslateX();
		gridViewY = gridPaneBoard.getTranslateY();//((GridView)(event.getSource())).getTranslateY();*/
	}
	
	@FXML
	public void handleDragGrid (MouseEvent event) {
		//FIXME
		/*double mouseOffSetX = event.getSceneX() - pressedX;
		double mouseOffSetY = event.getSceneY() - pressedY;
		gridPaneBoard.setTranslateX(gridViewX + mouseOffSetX);
		gridPaneBoard.setTranslateY(gridViewY + mouseOffSetY);*/
		//gridPaneBoard.setPadding(new Insets(event.getScreenY() + pressedY, event.getScreenX() + pressedX, event.getScreenY() + pressedY, event.getScreenX() + pressedX));
	}
	
	@FXML
	public void initialize () throws IOException {
		gridPaneBoard = new GridPane();
		anchorPaneGameBoard.getChildren().clear();
		anchorPaneGameBoard.getChildren().setAll(gridPaneBoard);
		
		gridPaneBoard.setPrefWidth(10620.0);
		gridPaneBoard.setPrefHeight(10620.0);
		gridPaneBoard.setGridLinesVisible(true);
		AnchorPane.setTopAnchor(gridPaneBoard, 0.0);
		AnchorPane.setRightAnchor(gridPaneBoard, 0.0);
		AnchorPane.setBottomAnchor(gridPaneBoard, 0.0);
		AnchorPane.setLeftAnchor(gridPaneBoard, 0.0);
		// onMouseClicked="#handleClickGrid" onMouseDragged="#handleDragGrid" onMousePressed="#handlePressedGrid"
		
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
				if (i == startCardX  &&  j == startCardY) {
					ImageView viewCard = new ImageView("ressources/NSEO_C.png");
					gridPaneBoard.add(viewCard, i, j);
				}
				else if (i == 14) {
					if (j == 1) {
						ImageView viewCard = new ImageView("ressources/dos_carte_arrivee.png");
						gridPaneBoard.add(viewCard, i, j);
					}
					else if (j == 3) {
						ImageView viewCard = new ImageView("ressources/dos_carte_arrivee.png");
						gridPaneBoard.add(viewCard, i, j);
					}
					else if (j == 5) {
						ImageView viewCard = new ImageView("ressources/dos_carte_arrivee.png");
						gridPaneBoard.add(viewCard, i, j);
					}
				}
				
				// TODO - Original - places the cards in center of grid
				/*if (i == gridWidth/2  &&  j == gridHeight/2) {
					ImageView viewCard = new ImageView("ressources/carte_test_118_181.png");
					gridPaneBoard.add(viewCard, i, j);
				}
				else if (i == ((gridWidth/2)+8)) {
					if (j == ((gridHeight/2)-2)) {
						ImageView viewCard = new ImageView("ressources/carte_test_118_181.png");
						gridPaneBoard.add(viewCard, i, j);
					}
					else if (j == (gridHeight/2)) {
						ImageView viewCard = new ImageView("ressources/carte_test_118_181.png");
						gridPaneBoard.add(viewCard, i, j);
					}
					else if (j == ((gridHeight/2)+2)) {
						ImageView viewCard = new ImageView("ressources/carte_test_118_181.png");
						gridPaneBoard.add(viewCard, i, j);
					}
				}*/
			}
		}
		
		// Centers game board
		gridPaneBoard.setScaleX(0.6);
		gridPaneBoard.setScaleY(0.6);
		//gridPaneBoard.setTranslateX(gridWidth);
		//gridPaneBoard.setTranslateY(gridHeight);
		gridPaneBoard.setPadding(new Insets(15444, 0, 0, 9622));
		
	}

}
