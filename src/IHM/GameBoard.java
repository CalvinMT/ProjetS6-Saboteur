package IHM;

import java.io.IOException;
import java.util.ArrayList;
import Board.Couple;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;


public class GameBoard {
	
	public static int cardsWidth = 118;
	public static int cardsHeight = 181;
	
	public static int gridWidth = 90;
	public static int gridHeight = 89;
	
	public static int startCardX = (gridWidth / 2);
	public static int startCardY = (gridHeight / 2);
	
	public static ArrayList <Couple>endCards;
	private int endCardX = ((gridWidth/2)+8);
	private int endTopCardY = ((gridHeight/2)-2);
	private int endMiddleCardY = (gridHeight/2);
	private int endBottomCardY = ((gridHeight/2)+2);
	
	private double pressedX;
	private double pressedY;
	private double gridViewX;
	private double gridViewY;
	
	
	public static GridPane gridPaneBoard;
	public static GridPane gridPaneIndications;
	
	@FXML
	private GridPane gridPaneGameBoard;
	
	@FXML
	private AnchorPane anchorPaneGameBoard;
	
	
	
	@FXML
	public void initialize () throws IOException {
		gridPaneBoard = new GridPane();
		anchorPaneGameBoard.getChildren().clear();
		anchorPaneGameBoard.getChildren().setAll(gridPaneBoard);
		
		// Grid initialization
		gridPaneBoard.setPrefWidth(10620.0);
		gridPaneBoard.setPrefHeight(10620.0);
		gridPaneBoard.setGridLinesVisible(true);
		AnchorPane.setTopAnchor(gridPaneBoard, 0.0);
		AnchorPane.setRightAnchor(gridPaneBoard, 0.0);
		AnchorPane.setBottomAnchor(gridPaneBoard, 0.0);
		AnchorPane.setLeftAnchor(gridPaneBoard, 0.0);
		
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
					ImageView viewCard = new ImageView("ressources/carte_depart.png");
					gridPaneBoard.add(viewCard, i, j);
				}
			}
		}
		
		// End cards initialization
		endCards = new ArrayList<>();
		endCards.add(new Couple(endTopCardY, endCardX));
		endCards.add(new Couple(endMiddleCardY, endCardX));
		endCards.add(new Couple(endBottomCardY, endCardX));
		endCards.stream().forEach(endCard -> {
			ImageView viewCard = new ImageView("ressources/dos_carte_arrivee.png");
			gridPaneBoard.add(viewCard, endCard.getColumn(), endCard.getLine());
		});
		
		// Centers game board
		gridPaneBoard.setScaleX(0.6);
		gridPaneBoard.setScaleY(0.6);
		gridPaneBoard.setPadding(new Insets(15444, 0, 0, 9622));
		gridPaneBoard.setTranslateX(-2721.0);
		gridPaneBoard.setTranslateY(-4466.0);
		
		gridPaneBoardEvents();
	}
	
	
	
	// gridPaneBoard's events
	public void gridPaneBoardEvents () {
		gridPaneBoard.setOnMousePressed(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown()) {
					anchorPaneGameBoard.getScene().setCursor(Cursor.CLOSED_HAND);
					pressedX = event.getSceneX();
					pressedY = event.getSceneY();
					gridViewX = ((GridPane)(event.getSource())).getTranslateX();
					gridViewY = ((GridPane)(event.getSource())).getTranslateY();
				}
			}
		});
		gridPaneBoard.setOnMouseDragged(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown()) {
					double mouseOffSetX = event.getSceneX() - pressedX;
					double mouseOffSetY = event.getSceneY() - pressedY;
					gridPaneBoard.setTranslateX(gridViewX + mouseOffSetX);
					gridPaneBoard.setTranslateY(gridViewY + mouseOffSetY);
				}
			}
		});
		gridPaneBoard.setOnMouseReleased(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				anchorPaneGameBoard.getScene().setCursor(Cursor.DEFAULT);
			}
		});
	}
}
