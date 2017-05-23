package IHM;

import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class BandeauPlayerFinGame {	
	
    private ImageView imageViewAvatar;
    private Text textPseudo;
    private Text textRole;
    private Text textOr;
    
    
	public BandeauPlayerFinGame (TableView<BandeauPlayerFinGame> tableView, ImageView avatar, String pseudo, String or, String role) {
		this.imageViewAvatar = avatar;
		this.textPseudo = new Text(pseudo);
		this.textRole = new Text(role);	
                this.textOr = new Text(or);
			
		
	}
	

	public ImageView getAvatar () {
		return imageViewAvatar;
	}
	
	public String getPseudo () {
		return textPseudo.getText();
	}
        
        public String getOr () {
		return textOr.getText();
	}
	
        public String getRole () {
		return textRole.getText();
	}
	

	

}
