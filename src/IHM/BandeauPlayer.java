package IHM;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class BandeauPlayer {
	
    private ImageView imageViewAvatar;
    private Text textPseudo;
    private Button buttonDelete;
    
	public BandeauPlayer (TableView<BandeauPlayer> tableView, ImageView avatar, String pseudo) {
		this.imageViewAvatar = avatar;
		this.textPseudo = new Text(pseudo);
		
		ImageView imageViewCross = new ImageView(new Image("ressources/cross.png"));
		imageViewCross.setFitWidth(20);
		imageViewCross.setFitHeight(20);
		this.buttonDelete = new Button("", imageViewCross);
		this.buttonDelete.setOnAction(new EventHandler <ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				tableView.getItems().remove(BandeauPlayer.this);
			}
		});
	}
	
	

	public ImageView getAvatar () {
		return imageViewAvatar;
	}
	
	public String getPseudo () {
		return textPseudo.getText();
	}
	
	/*public ??? getType () {
		return ???;
	}*/
	
	public Button getButtonDelete () {
		return buttonDelete;
	}

}
