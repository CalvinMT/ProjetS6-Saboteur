/*
 * Morpion pÃ©dagogique
 * Copyright (C) 2016 Guillaume Huard

 * Ce programme est libre, vous pouvez le redistribuer et/ou le
 * modifier selon les termes de la Licence Publique GÃ©nÃ©rale GNU publiÃ©e par la
 * Free Software Foundation (version 2 ou bien toute autre version ultÃ©rieure
 * choisie par vous).

 * Ce programme est distribuÃ© car potentiellement utile, mais SANS
 * AUCUNE GARANTIE, ni explicite ni implicite, y compris les garanties de
 * commercialisation ou d'adaptation dans un but spÃ©cifique. Reportez-vous Ã  la
 * Licence Publique GÃ©nÃ©rale GNU pour plus de dÃ©tails.

 * Vous devez avoir reÃ§u une copie de la Licence Publique GÃ©nÃ©rale
 * GNU en mÃªme temps que ce programme ; si ce n'est pas le cas, Ã©crivez Ã  la Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * Ã‰tats-Unis.

 * Contact: Guillaume.Huard@imag.fr
 *          Laboratoire LIG
 *          700 avenue centrale
 *          Domaine universitaire
 *          38401 Saint Martin d'HÃ¨res
 */

import java.security.InvalidParameterException;
import java.util.Iterator;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

class Plateau {
    boolean enCours;
    int [][] plateau;

    Plateau() {
        plateau = new int[3][3];
        enCours = true;
        for (int i=0; i<plateau.length; i++)
            for (int j=0; j<plateau[0].length; j++)
                plateau[i][j] = -1;
    }

    boolean libre(int i, int j) {
        return plateau[i][j] == -1;
    }

    void jouer(int n, int i, int j) {
        plateau[i][j] = n;
        boolean vertical = true, horizontal=true, slash=true, antiSlash=true;
        for (int p=0; p<plateau.length; p++) {
            horizontal = horizontal && (plateau[i][p] == n);
            vertical = vertical && (plateau[p][j] == n);
            slash = slash && (plateau[p][p] == n);
            antiSlash = antiSlash && (plateau[p][plateau.length-p-1] == n);
        }
        enCours = !(horizontal || vertical || slash || antiSlash);
    }

    int valeur(int i, int j) {
        return plateau[i][j];
    }

    boolean enCours() {
        return enCours;
    }

    int largeur() {
        return plateau[0].length;
    }

    int hauteur() {
        return plateau.length;
    }
}

abstract class Joueur {
    Plateau plateau;
    int num;

    Joueur(int n, Plateau p) {
        num = n;
        plateau = p;
    }
    protected void placerCoup(int i, int j) {

    }
    int passageDeMain() {
        // MÃ©thode appelÃ©e lorsque le tour d'un joueur vient
        // renvoie un temps Ã  attendre en millisecondes ou -1 si aucune temporisation requise
        return -1;
    }
    boolean tempsEcoule() {
        // MÃ©thode appelÃ©e une fois le temps Ã©coulÃ©
        return false;
    }
    boolean jeu(int i, int j) {
        // MÃ©thode appelÃ©e lors d'un clic sur le plateau
        return false;
    }
}

class  JoueurIA extends Joueur {
    Random r;

    JoueurIA(int n, Plateau p) {
        super(n, p);
        r = new Random();
    }

    @Override
    int passageDeMain() {
        return 1000;
    }
    @Override
    boolean tempsEcoule() {
        int i, j;

        i = r.nextInt(plateau.hauteur());
        j = r.nextInt(plateau.largeur());
        while (!plateau.libre(i, j)) {
            i = r.nextInt(plateau.hauteur());
            j = r.nextInt(plateau.largeur());
        }
        plateau.jouer(num, i, j);
        return true;
    }
}

class JoueurHumain extends Joueur {
    JoueurHumain(int n, Plateau p) {
        super(n, p);
    }

    @Override
    boolean jeu(int i, int j) {
        if (plateau.libre(i, j)) {
            plateau.jouer(num, i, j);
            return true;
        } else {
            return false;
        }
    }
}

public class Morpion extends Application {
    Plateau plateau;
    Canvas c;
    Joueur [] joueurs;
    int joueurCourant;
    long echeance;

    void dessin() {
        double width = c.getWidth();
        double height = c.getHeight();
        int lignes = plateau.hauteur();
        int colonnes = plateau.largeur();
        GraphicsContext g = c.getGraphicsContext2D();

        g.clearRect(0, 0, c.getWidth(), c.getHeight());
        if (!plateau.enCours())
            g.strokeText("Fin", 20, c.getHeight()/2);
        for (int i=1; i<lignes;i++) {
            g.strokeLine(0, i*height/lignes, width, i*height/lignes);
            g.strokeLine(i*width/lignes, 0, i*width/lignes, height);
        }
        for (int i=0; i<lignes; i++)
            for (int j=0; j<colonnes; j++)
                switch (plateau.valeur(i, j)) {
                    case 0:
                        g.strokeOval(j*width/lignes, i*height/lignes, width/lignes, height/lignes);
                        break;
                    case 1:
                        g.strokeLine(j*width/lignes, i*height/lignes, (j+1)*width/lignes, (i+1)*height/lignes);
                        g.strokeLine(j*width/lignes, (i+1)*height/lignes, (j+1)*width/lignes, i*height/lignes);
                        break;
                }
    }

    void changeJoueur() {
        joueurCourant = 1 - joueurCourant;
        echeance = joueurs[joueurCourant].passageDeMain()*1000000 + System.nanoTime();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        plateau = new Plateau();
        joueurs = new Joueur[2];
        joueurCourant = 0;
        echeance = System.nanoTime();

        Iterator<String> it = getParameters().getRaw().iterator();
        for (int i=0; i<joueurs.length; i++) {
            String nature;
            if (it.hasNext())
                nature = it.next();
            else
                nature = "IA";
            switch (nature) {
                case "humain":
                    joueurs[i] = new JoueurHumain(i, plateau);
                    break;
                case "IA":
                    joueurs[i] = new JoueurIA(i, plateau);
                    break;
                default:
                    throw new InvalidParameterException(nature);
            }
        }

        primaryStage.setTitle("Morpion");
        c = new Canvas(400,400);

        // Composant de regroupement qui occupe toute la place disponible
        // Le noeud donnÃ© en paramÃ¨tre est placÃ© au centre du BorderPane
        BorderPane b = new BorderPane(c);
        Scene s;
        s = new Scene(b);
        primaryStage.setScene(s);
        primaryStage.show();
        dessin();

        c.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (plateau.enCours()) {
                    int i = (int) (e.getY()*plateau.hauteur()/c.getHeight());
                    int j = (int) (e.getX()*plateau.largeur()/c.getWidth());
                    if (joueurs[joueurCourant].jeu(i, j)) {
                        changeJoueur();
                        dessin();
                    }
                }
            }
        });

        AnimationTimer anim = new AnimationTimer() {
            @Override
            public void handle(long temps) {
                if (plateau.enCours() && (temps > echeance) && joueurs[joueurCourant].tempsEcoule()) {
                    changeJoueur();
                    dessin();
                }
            }
        };
        anim.start();
    }

    public static void main(String [] args) {
        launch(args);
    }
}