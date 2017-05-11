/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ihm;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 *
 * @author villermt
 */
public class MainInterfaceController implements Initializable {
    
    @FXML
    private Label label;
        
    @FXML
    private ImageView buttonMenu;

    @FXML
    private Button buttonAide;

    @FXML
    void handlerButtonAide(ActionEvent event) {//Je ne sais pas si ça va marcher, vu que j'insere des FXML dans le main FXML

    }

    @FXML
    void handlerButtonMenu(ActionEvent event) {

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
