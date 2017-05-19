package IHM;

import Cards.Card;
import Cards.HandRole;
import Saboteur.Saboteur;
import Saboteur.Moteur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import Cards.RoleCard;
import java.util.ArrayList;

public class ChoixRole {

    Moteur m;
    int nbPlayer;
    HandRole roles;
    ArrayList<ImageView> cartesRole;
    private int num;



    @FXML
    private ImageView imageCarteRole1;
    @FXML
    private ImageView imageCarteRole2;
    @FXML
    private ImageView imageCarteRole3;
    @FXML
    private ImageView imageCarteRole4;
    @FXML
    private ImageView imageCarteRole5;
    @FXML
    private ImageView imageCarteRole6;
    @FXML
    private ImageView imageCarteRole7;
    @FXML
    private ImageView imageCarteRole8;
    @FXML
    private ImageView imageCarteRole9;
    @FXML
    private ImageView imageCarteRole10;
    @FXML
    private ImageView imageCarteRole11;
    @FXML
    private ImageView imageCarteRole12;

    @FXML
    private Text textJoueurCourant;
    @FXML
    private Button buttonJoueurSuivant;
    @FXML
    private GridPane gridPaneChoixRole;

    @FXML
    private BorderPane borderPaneChoixRole;

    @FXML
    private void handleButtonJoueurSuivant() throws Exception {
        if(m.allRoleAreSet()){

            // Lancement de la partie
            System.out.println("Lancement de la partie");

            Scene scene = (Scene) borderPaneChoixRole.getScene();
            borderPaneChoixRole = (BorderPane) scene.lookup("#borderPaneChoixRole");
            BorderPane borderPaneGameLoader = FXMLLoader.load(getClass().getResource("GameLoader.fxml"));
            borderPaneChoixRole.getChildren().setAll(borderPaneGameLoader);

        } else if(m.roleSet()){

            System.out.println("Joueur suivant");
            buttonJoueurSuivant.setDisable(true);
            cartesRole.get(this.num).setVisible(false);
            m.nextPlayer();
            textJoueurCourant.setText("Au tour de " + m.getCurrentPlayer().getPlayerName());

        }
    }

    @FXML
    private void handleMouseClickedImageChoixRole(MouseEvent event) {

        int i=0;

        System.out.println("Image clicked");

        if(!m.roleSet()){


            while(i<nbPlayer && i<cartesRole.size() && !event.getSource().equals(cartesRole.get(i))){ //On cherche quelle carte a été selectionnée
                i++;
            }

            if(event.getSource().equals(cartesRole.get(i))){                            //Si on l'a trouvée on la retourne
                Card carteCourante = roles.chooseOne_with_remove(i);
                if(carteCourante.getType() == Card.Card_t.role){

                    if(((RoleCard)carteCourante).isMinor()){
                        cartesRole.get(i).setImage(new Image("ressources/carte_role_mineur.jpg"));
                    }else if(((RoleCard)carteCourante).isSaboteur()){
                        cartesRole.get(i).setImage(new Image("ressources/carte_role_saboteur.jpg"));
                    }
//                    cartesRole.remove(i);
                    this.num = i;


                    m.getCurrentPlayer().assignRole(carteCourante);
                    m.promptPlayersRole();

                }

                if(m.allRoleAreSet()){   //Si c'était la dernière carte
                    textJoueurCourant.setText("Commencer la partie");


                    //TODO changer bouton pour "Commencer partie"

                    buttonJoueurSuivant.setDisable(false);
                    buttonJoueurSuivant.setText("Commencer partie!!!");


                } else {
                    buttonJoueurSuivant.setDisable(false);
                }

            }
        }


    }

    @FXML
    public void initialize(){
        m = Saboteur.getMoteur();
        nbPlayer = m.nbPlayer();
        roles = m.getRoleCards(); // tableau de cartes roles
        cartesRole = new ArrayList<ImageView>(12);
        buttonJoueurSuivant.setDisable(true);

        cartesRole.add(0, imageCarteRole1);
        cartesRole.add(1, imageCarteRole2);
        cartesRole.add(2, imageCarteRole3);
        cartesRole.add(3, imageCarteRole4);
        cartesRole.add(4, imageCarteRole5);
        cartesRole.add(5, imageCarteRole6);
        cartesRole.add(6, imageCarteRole7);
        cartesRole.add(7, imageCarteRole8);
        cartesRole.add(8, imageCarteRole9);
        cartesRole.add(9, imageCarteRole10);
        cartesRole.add(10, imageCarteRole11);
        cartesRole.add(11, imageCarteRole12);

        for(int i=0; i<nbPlayer+1; i++){
            cartesRole.get(i).setImage(new Image("ressources/dos_carte_role.jpg"));
        }

        textJoueurCourant.setText("Au tour de " + m.getCurrentPlayer().getPlayerName());

    }
    // getImageCard(Card c) gameInteractive
    //m.getCurrentPlayer().getPlayerName();

}