package Player;

import Board.Couple;
import Cards.HandPlayer;
import Cards.PlayerAttribute;

import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * Created by thespygeek on 11/05/17.
 */
public class IA extends Player{

    Difficulty difficulty;

    public IA(){
        this.playerName = "Joueur";
        this.difficulty = Difficulty.Easy;
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
    }

    public IA(String name){
        this.playerName = name;
        this.difficulty = Difficulty.Easy;
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
    }

    public IA(String name, Difficulty d){
        this.playerName = name;
        this.difficulty = d;
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
    }

    float max(float a, float b) {
        if (a > b) return a;
        return b;
    }

    // Premier jet. Ne prend pas en compte si un but a ou non de l'or.
    // TODO
    // À implémenter : Si on sait qu'un but a de l'or, faire les calculs en fonctions de lui
    //                 Si on sait qu'un but n'a pas d'or, l'ignorer
    public Couple choosePosition(ArrayList<Couple> p) {
        int y;
        float h, hMax = 0;
        Couple bestCpl = p.get(0),
               currentCpl;

        // TODO : faire le calcul pour toutes les cartes de la main
        for (int i = 0; i < p.size(); i++) {
            currentCpl = p.get(i);
            y = abs(8 - currentCpl.getY());

            // Calcule de l'heuristique
            // Soit p une position
            // h(p) = max ( distance(p, but1), distance(p, but2), distance(p, but3) )
            // avec distance(p, but) = |(but.x - p.x)| / |(but.y - p.y)|
            h = max( max(abs(2 - currentCpl.getX()) / y, abs(-2 - currentCpl.getX()) / y), abs(0 - currentCpl.getX()) / y);

            if (h > hMax) { // On garde le couple qui a la meilleure heuristique
                hMax = h;
                bestCpl = currentCpl;
            }
        }

        return bestCpl;
    }

    @Override
    public void changeDiffulty(Difficulty d){
        this.difficulty = d;
    }

    @Override
    public String toString(){
        String renvoi = "";

        renvoi += "Player: "+this.playerName + "\n";
        renvoi += "Type: IA\n";
        renvoi += "Difficulté: "+this.difficulty + "\n";
        if(this.role == null){
            renvoi += "Aucun role pour l'instant\n";
        } else {
            renvoi += this.role + "\n";
        }
        renvoi += "Nombre d'or: "+this.goldPoints + "\n";
        renvoi += this.attributeCards + "\n";
        renvoi += this.playableCards + "\n";

        return renvoi;
    }
}
