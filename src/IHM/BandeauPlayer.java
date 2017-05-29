package IHM;

import Saboteur.Lobby;
import Player.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class BandeauPlayer {

	private ObservableList<String> difficulteList = FXCollections.observableArrayList("Facile", "Moyen", "Difficile");
	
    private ImageView imageViewAvatar;
    private Text textPseudo;
    private Text textType;
    private ComboBox<String> comboBoxDifficulte;
    private Button buttonDelete;

	public BandeauPlayer (TableView<BandeauPlayer> tableView, ImageView avatar, String pseudo, String type, String difficulte, Button buttonJouer, Button buttonAjouterPlayer, Button buttonAjouterIA, Lobby lobby) {
		this.imageViewAvatar = avatar;
		this.textPseudo = new Text(pseudo);
		this.textType = new Text(type);

		if(!difficulte.equals("")){

			this.comboBoxDifficulte = new ComboBox<String>(difficulteList);

			switch(difficulte){
				case "Facile":
					this.comboBoxDifficulte.setValue(difficulteList.get(0));
					break;
				case "Moyen":
					this.comboBoxDifficulte.setValue(difficulteList.get(1));
					break;
				case "Difficile":
					this.comboBoxDifficulte.setValue(difficulteList.get(2));
					break;
				default:
					this.comboBoxDifficulte.setValue(difficulteList.get(0));
					break;
			}
			this.comboBoxDifficulte.setOnAction(new EventHandler <ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {

					// update dans le lobby
					int num = tableView.getItems().indexOf(BandeauPlayer.this);
					String diff = comboBoxDifficulte.getValue();
					System.out.println("Difficulté: "+Player.Difficulty.stringToDiff(diff));
					lobby.updateDifficulty(num, Player.Difficulty.stringToDiff(diff));

					System.out.println(lobby);
				}
			});
		}

		ImageView imageViewCross = new ImageView(new Image("ressources/cross.png"));
		imageViewCross.setFitWidth(20);
		imageViewCross.setFitHeight(20);
		this.buttonDelete = new Button("", imageViewCross);
		this.buttonDelete.setOnAction(new EventHandler <ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				int num = tableView.getItems().indexOf(BandeauPlayer.this);

				lobby.deletePlayer(num);
				tableView.getItems().remove(BandeauPlayer.this);


				// mise a jour des bandeau
				for(int i=num; i<tableView.getItems().size(); i++){

					String name = lobby.getArrayPlayer().get(i).getPlayerName();
					tableView.getItems().get(i).setTextPseudo(name);
				}
				
				MenuCreationPartie.numberOfPlayersLeftPlusOne();

				if(tableView.getItems().size()<3)
					buttonJouer.setDisable(true);
					MenuCreationPartie.textNumberOfPlayersLeftVisibleOn();
				if(tableView.getItems().size()<10){
					buttonAjouterPlayer.setDisable((false));
					buttonAjouterIA.setDisable((false));
				}
			}
		});
	}

	public BandeauPlayer (TableView<BandeauPlayer> tableView, ImageView avatar, String pseudo, String type, Button buttonJouer, Button buttonAjouterPlayer, Button buttonAjouterIA, Lobby lobby) {
		this(tableView, avatar, pseudo, type, "", buttonJouer, buttonAjouterPlayer, buttonAjouterIA, lobby);
	}

	public void setTextPseudo(String name){
		this.textPseudo.setText(name);
	}

	public ImageView getAvatar () {
		return imageViewAvatar;
	}
	
	public String getPseudo () {
		return textPseudo.getText();
	}
	
	public String getType () {return this.textType.getText();}

	public ComboBox<String> getDifficulte () {return this.comboBoxDifficulte;}
	
	public Button getButtonDelete () {
		return buttonDelete;
	}

}
