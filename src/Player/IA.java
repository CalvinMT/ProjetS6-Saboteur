package Player;

import Board.Couple;
import Cards.Card;
import Cards.GalleryCard;
import Cards.HandPlayer;
import Cards.PlayerAttribute;

import java.util.ArrayList;

import static Cards.Card.Card_t.gallery;
import static java.lang.Math.abs;

/**
 * Created by thespygeek on 11/05/17.
 */
public class IA extends Player{

    Difficulty difficulty;
    private Couple goldGoal = new Couple(0, 0);
    private ArrayList<Couple> goalsToTest;


    public IA(){
        this.playerName = "Joueur";
        this.difficulty = Difficulty.Easy;
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
        setUpGoals();
    }

    public IA(String name){
        this.playerName = name;
        this.difficulty = Difficulty.Easy;
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
        setUpGoals();
    }

    public IA(String name, Difficulty d){
        this.playerName = name;
        this.difficulty = d;
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
        setUpGoals();
    }

    private void setUpGoals() {
        this.goalsToTest = new ArrayList<Couple>();
        this.goalsToTest.add(new Couple(-2, 8));
        this.goalsToTest.add(new Couple(0, 8));
        this.goalsToTest.add(new Couple(2, 8));
    }

    // Calcule de l'heuristique
    // Soit p une position
    // h(p) = max ( distance(p, but1), distance(p, but2), distance(p, but3) )
    // avec distance(p, but) = |(but.x - p.x)| / |(but.y - p.y)|

    public float getHeuristic(Couple goal, Couple cpl) {
        return abs(goal.getX() - cpl.getX()) + abs(goal.getY() - cpl.getY());
    }

    // Premier jet. Ne prend pas en compte si un but a ou non de l'or.
    // TODO : À implémenter : Si on sait qu'un but a de l'or, faire les calculs en fonctions de lui
    // TODO :                 Si on sait qu'un but n'a pas d'or, l'ignorer
    public Couple choosePosition() {
        float h, hMax = 0;
        Card currCard;
        Couple bestCpl = new Couple(0, 0),
               currentCpl;
        ArrayList<Couple> p;

        for (int cardIdx = 0; cardIdx < nbCardHand(); cardIdx++) { // Parcours des cartes en main
            currCard = lookAtCard(cardIdx);
            if (currCard.getType() == gallery) { // Si la carte est une gallerie
                p = this.board.getPossiblePositions((GalleryCard) currCard); // On calcule les positions possibles pour cette carte
                bestCpl = p.get(0);
                for (int i = 0; i < p.size(); i++) { // Pour chaque position possible
                    currentCpl = p.get(i);
                    if (goldGoal.getY() == 8) { // Si on connait le but avec minerai
                        h = getHeuristic(goldGoal, currentCpl); // On calcul l'heuristique (distance position <-> but)
                        if (h > hMax){ // Si l'heuristique est maximale
                            hMax = h; // On met à jour l'heuristique max
                            bestCpl = currentCpl; // On garde la position
                        }
                    }
                    else {
                        for (int g = 0; g < goalsToTest.size(); g++) { // Et pour chaque but
                            h = getHeuristic(goalsToTest.get(g), currentCpl); // On calcul l'heuristique (distance position <-> but)
                            if (h > hMax) { // Si l'heuristique est maximale
                                hMax = h; // On met à jour l'heuristique max
                                bestCpl = currentCpl; // On garde la position
                            }
                        }
                    }
                }
            }
        }

        return bestCpl;
    }

    public void ignoreGoal(Couple cpl) {
        if (goalsToTest.contains(cpl)) this.goalsToTest.remove(cpl);
    }

    public void addGoldGoal(Couple cpl) {
        this.goldGoal = cpl;
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
