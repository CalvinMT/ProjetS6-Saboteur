package IHM;

import Saboteur.Lobby;
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
    private Text textType;
    private Button buttonDelete;
    private Lobby lobby;
    
	public BandeauPlayer (TableView<BandeauPlayer> tableView, ImageView avatar, String pseudo, String type, Button buttonJouer, Button buttonAjouterPlayer, Button buttonAjouterIA, Lobby lobby) {
		this.imageViewAvatar = avatar;
		this.textPseudo = new Text(pseudo);
		this.textType = new Text(type);
		this.lobby = lobby;
		
		ImageView imageViewCross = new ImageView(new Image("ressources/cross.png"));
		imageViewCross.setFitWidth(20);
		imageViewCross.setFitHeight(20);
		this.buttonDelete = new Button("", imageViewCross);
		this.buttonDelete.setOnAction(new EventHandler <ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				int num =  tableView.getItems().indexOf(BandeauPlayer.this);

				lobby.deletePlayer(num);

				System.out.println(lobby);


				tableView.getItems().remove(BandeauPlayer.this);
				if(tableView.getItems().size()<=3)
					buttonJouer.setDisable(true);
				if(tableView.getItems().size()<10){
					buttonAjouterPlayer.setDisable((false));
					buttonAjouterIA.setDisable((false));
				}
			}
		});
	}
	
	

	public ImageView getAvatar () {
		return imageViewAvatar;
	}
	
	public String getPseudo () {
		return textPseudo.getText();
	}
	
	public String getType () {return this.textType.getText();}
	
	public Button getButtonDelete () {
		return buttonDelete;
	}

}
