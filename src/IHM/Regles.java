package IHM;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import static IHM.MainLoader.anchorPaneMenuMain;
import static IHM.MainLoader.scene;

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
    private ImageView imageViewCarteNombreOr;

    @FXML
    public void handleButtonRetourMenu() throws IOException {
        /*Stage stage = (Stage) buttonRetourMenu.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("MainLoader.fxml"));
        MainLoader.anchorPaneMainLoader = (AnchorPane) root.lookup("#anchorPaneMainLoader");
        AnchorPane anchorPaneMenuMain = FXMLLoader.load(getClass().getResource("MenuMain.fxml"));
        MainLoader.autoResizeToResolution(MainLoader.anchorPaneMenuMain);
        MainLoader.anchorPaneMainLoader.getChildren().setAll(anchorPaneMenuMain);
        stage.setScene(new Scene(root));
        stage.show();*/ //OLD BUG (je laisse au cas où la nouvelle version soit pas bonne au final)

        Stage primaryStage = (Stage) buttonRetourMenu.getScene().getWindow();
        Parent parentMainMenu = FXMLLoader.load(getClass().getResource("MainLoader.fxml"));
        primaryStage.setTitle("Saboteur");
        double SCREEN_WIDTH = primaryStage.getWidth();
        double SCREEN_HEIGHT = primaryStage.getHeight();
		scene = new Scene(parentMainMenu, SCREEN_WIDTH, SCREEN_HEIGHT);
		primaryStage.setWidth(SCREEN_WIDTH);
		primaryStage.setHeight(SCREEN_HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);

		// Load MenuMain.fxml
		MainLoader.anchorPaneMainLoader = (AnchorPane) parentMainMenu.lookup("#anchorPaneMainLoader");
		anchorPaneMenuMain = FXMLLoader.load(getClass().getResource("MenuMain.fxml"));
		MainLoader.anchorPaneMainLoader.getChildren().setAll(anchorPaneMenuMain);

		// Automatic Resizing
		MainLoader.autoResizeToResolution(anchorPaneMenuMain);

		primaryStage.show();


    }

    @FXML
    public void initialize(){
        textRegles.setText("But du jeu :\n\n" +
                "En trois manches : amasser le plus d'or.\n" +
                "\n\n\nDéroulement d'une manche :\n\n" +
                "Au début de chaque manche, chaque joueur pioche une carte rôle définissant son objectif pour la manche.\n" +
                "Les rôles sont secrets mais les joueurs du même rôle joueront en équipe et se partageront les gains.\n" +
                "On est soit Mineur soit Saboteur.\n" +
                "- Le Mineur doit atteindre la carte but contenant l'or (seulement une sur les trois cartes but, ces cartes sont face cachées).\n" +
                "- Le Saboteur doit empecher le mineur d'atteindre les buts. Pour que le/les saboteur(s) gagne(nt) il faut que la pioche soit vide et que plus personne n'ait de cartes en mains.\n" +
                "Chaque joueur joue à tour de role.\n" +
                "On joue tant qu'aucune des deux équipes ne l'a emporté\n" +
                "L'équipe qui remporte la manche gagne de l'or. Chaque joueur de l'équipe victorieuse tire alors un carte or pour savoir combien d'or il a gagné.\n" +
                "\n\n\nDéroulement d'un tour :\n\n" +
                "Le joueur peut effectuer une action parmi les suivantes (s'il dispose de la carte concernée) puis piocher pour toujours avoir le bon nombre de cartes en main : \n" +
                "- Poser une gallerie (cartes galleries)\n" +
                "- Casser/réparer un ou plusieurs outils d'un joueur quel qu'il soit (cartes outil brisé)\n" +
                "- Faire effondrer une gallerie (carte éboulement)\n" +
                "- Regarder une des cartes but (carte plan secret)\n" +
                "- Se défausser d'une carte\n");
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
        Tooltip.install(imageViewCarteNombreOr, new Tooltip("Carte or à tirer en fin de partie lorsque l'on a gagné (3 d'or pour celle-ci, 1 et 2 pour les autres possibles)"));

    }

}
