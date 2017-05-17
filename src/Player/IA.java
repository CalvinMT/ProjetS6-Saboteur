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

    private ArrayList<Couple> goalsToTest;

    private Card cardToPlay;
    private Couple posToPlay;


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

    // Calcul de l'heuristique
    // Soit p une position
    // h(p) = max ( distance(p, but1), distance(p, but2), distance(p, but3) )
    // avec distance(p, but) = |(but.x - p.x)| + |(but.y - p.y)|
    public float getHeuristic(Couple goal, Couple cpl) {
        return abs(goal.getX() - cpl.getX()) + abs(goal.getY() - cpl.getY());
    }

    // TODO : Tests
    // TODO : Choisir les cartes
    // Determine la position la plus proche d'un but et retourne ses coordonnées
    public void choosePosition() {
        float h, hMax = 0;
        Card currCard, bestCard;
        Couple bestCpl = new Couple(0, 0);
        ArrayList<Couple> p;

        bestCard = lookAtCard(0);
        for (int cardIdx = 0; cardIdx < nbCardHand(); cardIdx++) { // Parcours des cartes en main
            currCard = lookAtCard(cardIdx);
            if (currCard.getType() == gallery) { // Si la carte est une gallerie
                p = this.board.getPossiblePositions((GalleryCard) currCard); // On calcule les positions possibles pour cette carte
                bestCpl = p.get(0);
                for (Couple currCpl : p) { // Pour chaque position possible
                    for (Couple goal : goalsToTest) { // Et pour chaque but
                        h = getHeuristic(goal, currCpl); // On calcul l'heuristique (distance position <-> but)

                        // TODO : Verifier si on peut finir le chemin
                        if (h > hMax) { // Si l'heuristique est maximale
                            hMax = h; // On met à jour l'heuristique max
                            bestCpl = currCpl; // On garde la position
                            bestCard = currCard; // et la carte associée
                        }
                    }
                }
            }
        }
        this.posToPlay = bestCpl;
        this.cardToPlay = bestCard;
    }

    // Supprime un but des buts à tester
    // cpl : couple de coordonnée du but
    public void ignoreGoal(Couple cpl) {
        if (goalsToTest.contains(cpl)) this.goalsToTest.remove(cpl);
    }

    // Definit un but comme portant de l'or
    // cpl : couple de coordonnées du but
    // Effet : Supprime les autres buts de goalsToTest
    public void addGoldGoal(Couple cpl) {
        if (goalsToTest.contains(cpl)) {
            for (int i = 0; i < goalsToTest.size(); i++) {
                if (!goalsToTest.get(i).equals(cpl)) {
                    goalsToTest.remove(i);
                    i--;
                }
            }
        }
    }

    // Retourne la liste des buts à tester
    public ArrayList<Couple> getGoalsToTest() {
        return goalsToTest;
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
