package IHM;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class BandeauPlayer {
	
    private ImageView imageViewAvatar;
    private Text textPseudo;
    private Button buttonDelete;
    
	public BandeauPlayer (ImageView avatar, String pseudo) {
		this.imageViewAvatar = avatar;
		this.textPseudo = new Text(pseudo);
	}
	
	
	
	void handleButtonDelete(ActionEvent event) {
	    System.out.println("Supprimer pressed");
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
