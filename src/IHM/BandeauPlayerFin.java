package IHM;

import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class BandeauPlayerFin {	
	
    private ImageView imageViewAvatar;
    private Text textPseudo;
    private Text textRole;
    
    
	public BandeauPlayerFin (TableView<BandeauPlayerFin> tableView, ImageView avatar, String pseudo, String role) {
		this.imageViewAvatar = avatar;
		this.textPseudo = new Text(pseudo);
		this.textRole = new Text(role);		
			
		
	}
	

	public ImageView getAvatar () {
		return imageViewAvatar;
	}
	
	public String getPseudo () {
		return textPseudo.getText();
	}
	
        public String getRole () {
		return textRole.getText();
	}
	

	

}
