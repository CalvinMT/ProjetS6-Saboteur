package Player;

import Board.Couple;
import Cards.Card;
import Cards.GalleryCard;
import Cards.HandPlayer;
import Cards.PlayerAttribute;

import java.util.ArrayList;
import java.util.Random;

import static Cards.Card.Card_t.gallery;
import static java.lang.Math.abs;

/**
 * Created by thespygeek on 11/05/17.
 */
public class IA extends Player{



    private ArrayList<Couple> goalsToTest;

    private Card cardToPlay;
    private Couple posToPlay;

    public IA(int index){
        this.playerName = "IA " + index;
        this.num = index;
        this.difficulty = Difficulty.Easy;
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
        this.avatar = "robot_miner";
        setUpGoals();
    }

    public IA(int index, String name){
        this.playerName = name;
        this.num = index;
        this.difficulty = Difficulty.Easy;
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
        this.avatar = "robot_miner";
        setUpGoals();
    }

    public IA(int index, String name, Difficulty d){
        this.playerName = name;
        this.num = index;
        this.difficulty = d;
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
        this.avatar = "robot_miner";
        setUpGoals();
    }

    private void setUpGoals() {
        this.goalsToTest = new ArrayList<Couple>();
        this.goalsToTest.add(new Couple(-2, 8));
        this.goalsToTest.add(new Couple(0, 8));
        this.goalsToTest.add(new Couple(2, 8));
    }



    // IA Random
    public void randomPlay() {
        ArrayList<Couple> p;
        Random r = new Random();
        int range = r.nextInt(nbCardHand() - 1);
        if (range < 1) {
            range = 1;
        }
        this.cardToPlay = lookAtCard(r.nextInt(range));
        if (cardToPlay.getType() == gallery) {
            this.board.computeAccessCards();
            p = this.board.getPossiblePositions((GalleryCard) cardToPlay);
            range = r.nextInt(p.size() - 1);
            if (range < 1) {
                range = 1;
            }
            this.posToPlay = p.get(r.nextInt(range));
        }
    }


    // IA Medium + Hard

    // Calcul de l'heuristique
    // Soit p une position
    // h(p) = max ( distance(p, but1), distance(p, but2), distance(p, but3) )
    // avec distance(p, but) = |(but.x - p.x)| + |(but.y - p.y)|
    public float getDistanceToGoal(Couple goal, Couple cpl) {
        return abs(goal.getLine() - cpl.getLine()) + abs(goal.getColumn() - cpl.getColumn());
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

                if(p.size() > 0){

                    bestCpl = p.get(0);
                    for (Couple currCpl : p) { // Pour chaque position possible
                        for (Couple goal : goalsToTest) { // Et pour chaque but
                            h = getDistanceToGoal(goal, currCpl); // On calcul l'heuristique (distance position <-> but)

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
        }
        this.posToPlay = bestCpl;
        this.cardToPlay = bestCard;
    }

    // Supprime un but des buts à tester
    // cpl : couple de coordonnée du but
    // /!\ Si cpl n'existe pas  dans goalsToTest ne fait rien
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

    public Card getCardToPlay() {
        return cardToPlay;
    }

    public Couple getPosToPlay() {
        return posToPlay;
    }



    @Override
    public boolean iaPlayCard() {
        switch (this.difficulty) {
            case Easy:
                randomPlay();
                break;
            default:
                System.out.println("TODO : IA " + this.difficulty);
        }

        return true;
    }
    
    @Override
    public void setGoldPoints(int gp){
    	if (gp >= 0)
    		this.goldPoints += gp;
    }

}
