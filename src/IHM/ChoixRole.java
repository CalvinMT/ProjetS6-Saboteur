package IHM;

import Saboteur.Moteur.State;
import Cards.Card;
import Cards.HandRole;
import Player.Player;
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
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import Cards.RoleCard;
import java.util.ArrayList;

public class ChoixRole {

    static private Moteur m;
    private int nbPlayer;
    private HandRole roles;
    private ArrayList<ImageView> cartesRole;
    private ArrayList<Text> infoEmplacement;
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
    private Text textRolePris1;
    @FXML
    private Text textRolePris2;
    @FXML
    private Text textRolePris3;
    @FXML
    private Text textRolePris4;
    @FXML
    private Text textRolePris5;
    @FXML
    private Text textRolePris6;
    @FXML
    private Text textRolePris7;
    @FXML
    private Text textRolePris8;
    @FXML
    private Text textRolePris9;
    @FXML
    private Text textRolePris10;
    @FXML
    private Text textRolePris11;
    @FXML
    private Text textRolePris12;


    @FXML private Text textJoueurCourant;

    @FXML
    private Button buttonJoueurSuivant;
    @FXML
    private GridPane gridPaneChoixRole;
    
    @FXML
    private GridPane mainGridPane;
    
    @FXML
    private BorderPane borderPaneChoixRole;

    @FXML
    private void handleButtonJoueurSuivant() throws Exception {
        if(m.allRoleAreSet()){

            // Lancement de la partie
            System.out.println("Lancement de la partie");
            m.nextPlayer();
//            m.setState(State.Game);

            m.setState(State.Game);

            Scene scene = (Scene) borderPaneChoixRole.getScene();
            borderPaneChoixRole = (BorderPane) scene.lookup("#borderPaneChoixRole");
            BorderPane borderPaneGameLoader = FXMLLoader.load(getClass().getResource("GameLoader.fxml"));
            borderPaneChoixRole.getChildren().setAll(borderPaneGameLoader);

        } else if(m.roleSet()){

            System.out.println(roles.print_without_visibility());

            System.out.println("Joueur suivant");
            buttonJoueurSuivant.setDisable(true);
            cartesRole.get(this.num).setVisible(false);
            infoEmplacement.get(this.num).setText(m.getCurrentPlayer().getPlayerName());
            infoEmplacement.get(this.num).setFill(Paint.valueOf("FFFFFF"));
            m.nextPlayer();
            updateText();

        }
    }

    @FXML
    private void handleMouseClickedImageChoixRole(MouseEvent event) {


        if(!m.roleSet() && m.getCurrentPlayer().getDifficulty() == Player.Difficulty.Player){

            int i=0;

            while(i<nbPlayer && i<cartesRole.size() && !event.getSource().equals(cartesRole.get(i))){ //On cherche quelle carte a été selectionnée
                i++;
            }

            if(event.getSource().equals(cartesRole.get(i)) && !m.isTaken(i)){                            //Si on l'a trouvée on la retourne
                assignRoleToPlayer(i);
                m.setTrueTaken(i);
            } else {
                System.err.println("Carte deja prise!");
            }
        }

    }


    public void assignRoleToPlayer(int i){
        Card carteCourante = roles.chooseOne_without_remove(i);
        if(carteCourante.getType() == Card.Card_t.role){
            if(((RoleCard)carteCourante).isMiner()){
                cartesRole.get(i).setImage(new Image("ressources/carte_role_mineur.png"));
            }else if(((RoleCard)carteCourante).isSaboteur()){
                cartesRole.get(i).setImage(new Image("ressources/carte_role_saboteur.png"));
            }
//                    cartesRole.remove(i);
            this.num = i;

            m.getCurrentPlayer().assignRole(carteCourante);
//            m.promptPlayersRole();

        }

        configEndChoose();
    }

    public void updateGraphic(int i){
        String pseudo = m.getCurrentPlayer().getPlayerName();
        cartesRole.get(i).setVisible(false);
        infoEmplacement.get(i).setText(pseudo);
        infoEmplacement.get(i).setFill(Paint.valueOf("FFFFFF"));
    }

    public void updateText(){
        textJoueurCourant.setText("Au tour de " +m.getCurrentPlayer().getPlayerName());
    }

    public void configEndChoose(){
        if(m.allRoleAreSet()){   //Si c'était la dernière carte

            textJoueurCourant.setText("Commencer la partie");
            buttonJoueurSuivant.setDisable(false);
            buttonJoueurSuivant.setText("Commencer partie!!!");

        } else {
            buttonJoueurSuivant.setDisable(false);
        }
    }


    @FXML
    public void initialize(){
        m = Saboteur.getMoteur();
        nbPlayer = m.nbPlayer();
        roles = m.getRoleCards(); // tableau de cartes roles
        cartesRole = new ArrayList<ImageView>(12);
        infoEmplacement = new ArrayList<Text>(12);
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
        infoEmplacement.add(0, textRolePris1);
        infoEmplacement.add(1, textRolePris2);
        infoEmplacement.add(2, textRolePris3);
        infoEmplacement.add(3, textRolePris4);
        infoEmplacement.add(4, textRolePris5);
        infoEmplacement.add(5, textRolePris6);
        infoEmplacement.add(6, textRolePris7);
        infoEmplacement.add(7, textRolePris8);
        infoEmplacement.add(8, textRolePris9);
        infoEmplacement.add(9, textRolePris10);
        infoEmplacement.add(10, textRolePris11);
        infoEmplacement.add(11, textRolePris12);

        textJoueurCourant.setText("Au tour de " + m.getCurrentPlayer().getPlayerName());


        for(int i=0; i<nbPlayer+1; i++){
            cartesRole.get(i).setImage(new Image("ressources/dos_carte_role.png"));
        }

//        System.out.println(roles.print_without_visibility());

        m.setState(State.ChooseRole);
        m.setChoixroleControler(this);

        // reset boolean
        for(int i=0; i<m.getRoleCards().nbCard(); i++){
            m.roleTaken.set(i, false);
        }
    }

    // getImageCard(Card c) gameInteractive
    //m.getCurrentPlayer().getPlayerName();

}
