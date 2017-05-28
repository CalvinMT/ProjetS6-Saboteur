package IHM;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Regles {

    @FXML
    private Text textRegles;
    @FXML
    private Button buttonRetourMenu;
    @FXML
    private AnchorPane anchorPaneRegles;
    @FXML
    private ImageView imageViewCarteButOr;
    @FXML
    private ImageView imageViewCarteButCoal;
    @FXML
    private ImageView imageViewCarteButCoal1;
    @FXML
    private ImageView imageViewCarteRoleMineur;
    @FXML
    private ImageView imageViewCarteRoleSaboteur;
    @FXML
    private ImageView imageViewCarteGallery;
    @FXML
    private ImageView imageViewCarteOutilBriseWagon;
    @FXML
    private ImageView imageViewCarteOutilBriseLanterne;
    @FXML
    private ImageView imageViewCarteOutilBrisePioche;
    @FXML
    private ImageView imageViewCarteRepareWagon;
    @FXML
    private ImageView imageViewCarteReparePioche;
    @FXML
    private ImageView imageViewCarteRepareLanterne;
    @FXML
    private ImageView imageViewCarteReparationMultiple;
    @FXML
    private ImageView imageViewCartePlanSecret;
    @FXML
    private ImageView imageViewCarteEboulement;

    @FXML
    public void handleButtonRetourMenu() throws IOException { //FIXME -- même bug que le retour menu in game : Après si on lance une partie, on ne peut pas jouer de cartes
        Stage stage = (Stage) buttonRetourMenu.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("MainLoader.fxml"));
        MainLoader.anchorPaneMainLoader = (AnchorPane) root.lookup("#anchorPaneMainLoader");
        AnchorPane anchorPaneMenuMain = FXMLLoader.load(getClass().getResource("MenuMain.fxml"));
        MainLoader.anchorPaneMainLoader.getChildren().setAll(anchorPaneMenuMain);
        MainLoader.autoResizeToResolution(MainLoader.anchorPaneMenuMain);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void initialize(){
        textRegles.setText("But du jeu :\n" + "" +
                "Le jeu se déroule tour par tour et en trois manches.\n" +
                "Les gagnants d'une manche tirent au sort une quantité d'or gagné.\n" +
                "Au début de chaque manches, on pioche une carte rôle définissant l'objectif de chacun pour la manche en cours.\n" +
                "On est soit Mineur soit saboteur.\n" +
                "- Le mineur doit atteindre les trois cartes but en creusant des galleries, pour trouver la carte or.\n" +
                "- Le saboteur doit empecher le mineur d'atteindre les buts. Pour que le/les saboteur(s) gagne(nt) il faut que la pioche soit vide et que plus personne n'est de cartes en mains.\n" +
                "\n\n\fDéroulement d'une partie :\n" +
                "Chacun à tour de rôle, les joueurs peuvent effectuer une action parmis les suivantes (s'il dispose de la carte concernée : \n" +
                "- Poser une gallerie (cartes galleries)\n" +
                "- Casser/réparer un ou plusieurs outils d'un joueur quel qu'il soit (cartes outil brisé)\n" +
                "- Faire effondrer une gallerie (carte éboulement)\n" +
                "- Regarder une des cartes but (carte plan secret)\n" +
                "- Se défausser d'une carte\n" +
                "\n\nOn joue tant qu'il reste des cartes dans la pioches ou tant que l'or n'a pas été découvert");
        Tooltip.install(imageViewCarteButOr, new Tooltip("Carte but or :\nAtteindre cette carte en tracant une gallerie du départ jusqu'à elle pour gagner en tant que mineur"));
        Tooltip.install(imageViewCarteButCoal, new Tooltip("Mauvaise carte but"));
        Tooltip.install(imageViewCarteButCoal1, new Tooltip("Mauvaise carte but"));
        Tooltip.install(imageViewCarteRoleMineur, new Tooltip("Carte role mineur"));
        Tooltip.install(imageViewCarteRoleSaboteur, new Tooltip("Carte role saboteur"));
        Tooltip.install(imageViewCarteGallery, new Tooltip("Exemple de carte gallerie"));
        Tooltip.install(imageViewCarteOutilBriseWagon, new Tooltip("Carte outil brisé : Wagon"));
        Tooltip.install(imageViewCarteOutilBriseLanterne, new Tooltip("Carte outil brisé : Lanterne"));
        Tooltip.install(imageViewCarteOutilBrisePioche, new Tooltip("Carte outil brisé : Pioche"));
        Tooltip.install(imageViewCarteRepareWagon, new Tooltip("Carte Réparation : Wagon"));
        Tooltip.install(imageViewCarteRepareLanterne, new Tooltip("Carte réparation : Lanterne"));
        Tooltip.install(imageViewCarteReparePioche, new Tooltip("Carte réparation : Pioche"));
        Tooltip.install(imageViewCarteReparationMultiple, new Tooltip("Exemple de carte de réparation de plusieurs outils (un à choisir)"));
        Tooltip.install(imageViewCartePlanSecret, new Tooltip("Plan secret :\nCarte permettant de réveler une des cartes but pour le joueur qui la joue"));
        Tooltip.install(imageViewCarteEboulement, new Tooltip("Eboulement :\nCarte permettant de détruire une gallerie"));

    }

}
