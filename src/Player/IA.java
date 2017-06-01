package Player;

import Board.Couple;
import Cards.*;
import Cards.Card.Card_t;
import Cards.RepareSabotageCard.Tools;
import Saboteur.Lobby;

import java.util.ArrayList;
import java.util.Random;
import static Cards.Card.Card_t.gallery;
import static java.lang.Math.abs;


public class IA extends Player{
	
    private ArrayList<Couple> goalsToTest;

    private Card cardToPlay;
    private Couple posToPlay;
    private ArrayList<Player> allPlayers = new ArrayList<>();
    
    private Player aPlayerInList = null;
    private Tools toolOfAPlayerInList = null;

    public IA(int index) {
        this(index, "IA", Difficulty.Easy, Lobby.getArrayPlayer());
    }

    public IA(int index, String name) {
        this(index, name, Difficulty.Easy, Lobby.getArrayPlayer());
    }

    public IA(int index, String name, Difficulty d){
        this(index, name, d, Lobby.getArrayPlayer());
    }

    public IA(int index, String name, Difficulty d, ArrayList<Player> p){
        this.playerName = name;
        this.num = index;
        this.difficulty = d;
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
        this.avatar = "robot_miner";
        this.allPlayers = p;
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
        int range = r.nextInt(nbCardHand());
        range--;
        if (range < 1) {
            range = 1;
        }
        this.cardToPlay = lookAtCard(r.nextInt(range));
        if (cardToPlay.getType() == gallery  &&  attributeCards.getNbAttribute() <= 0) {
            this.board.computeAccessCards();
            p = this.board.getPossiblePositions((GalleryCard) cardToPlay);
            range = r.nextInt(p.size() - 1);
            if (range < 1) {
                range = 1;
            }
            this.posToPlay = p.get(r.nextInt(range));
        }
        else if (cardToPlay.getType() == Card_t.action) {
        	if (((ActionCard)cardToPlay).getAction().equals(ActionCard.Action.Crumbling)) {
        		range = r.nextInt(this.board.getMineSize()-4);
                if (range < 1) {
                    range = 1;
                }
                this.posToPlay = this.board.getMineElement(r.nextInt(range)+4).getCard().getCoord();
        		System.out.println(this.playerName + " used CRUMBLING on " + this.posToPlay);
        	}
        	else if (((ActionCard)cardToPlay).getAction().equals(ActionCard.Action.Map)) {
        		range = r.nextInt(goalsToTest.size());
                //range--;
                System.out.println(this.playerName + " used MAP on " + goalsToTest.get(range));
        	}
        	else if (((ActionCard)cardToPlay).getAction().equals(ActionCard.Action.Repare)) {
        		range = r.nextInt(this.allPlayers.size());
        		//range--;
        		this.aPlayerInList = this.allPlayers.get(range);
        		if (aPlayerInList.getAttributeCards().canRepareTool((RepareSabotageCard) cardToPlay)) {
        			this.toolOfAPlayerInList = ((RepareSabotageCard) cardToPlay).getTool();
        			posToPlay = null;
        			System.out.println(this.playerName + " used REPARE " + this.toolOfAPlayerInList.name() + " on " + aPlayerInList.getPlayerName());
        		}
        		else {
        			// TODO - defausse
        		}
        	}
        	else if (((ActionCard)cardToPlay).getAction().equals(ActionCard.Action.Sabotage)) {
        		range = r.nextInt(this.allPlayers.size());
        		//range--;
        		this.aPlayerInList = this.allPlayers.get(range);
        		if (aPlayerInList.getAttributeCards().canBreakTool((RepareSabotageCard) cardToPlay)) {
        			this.toolOfAPlayerInList = ((RepareSabotageCard) cardToPlay).getTool();
        			posToPlay = null;
        			System.out.println(this.playerName + " used SABOTAGE " + this.toolOfAPlayerInList.name() + " on " + aPlayerInList.getPlayerName());
        		}
        		else {
        			// TODO - defausse
        		}
        	}
        }
    }


    // Computing

    // Renvoie vrai si une carte à été posée dans une zone de 2 cases autour d'un des buts
    // Faux sinon
    public boolean isInSwitchZone() {
        for (int c = 6; c < 11; c++) {
            for (int l = -4; l < 5; l++) {
                if (c != 8 || (l != -2 && l != 0 && l != 2)) {
                    if (null != board.getNodeFromMine(new Couple(l, c))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Calcul de l'heuristique (distance)
    // Soit p une position
    // h(p) = max ( distance(p, but1), distance(p, but2), distance(p, but3) )
    // avec distance(p, but) = |(but.x - p.x)| + |(but.y - p.y)|
    public int getDistanceToGoal(Couple goal, Couple cpl) {
        return abs(goal.getLine() - cpl.getLine()) + abs(goal.getColumn() - cpl.getColumn());
    }

    // TODO : Tests
    // TODO : Choisir les cartes
    // Determine la position la plus proche d'un but et met à jour ses coordonnées
    public void choosePosition() {
        int h, hMin = -1;
        Card c, bestCard;
        GalleryCard currCard;
        Couple bestCpl = new Couple(0, 0);
        ArrayList<Couple> p;

        bestCard = lookAtCard(0);
        for (int cardIdx = 0; cardIdx < nbCardHand(); cardIdx++) { // Parcours des cartes en main
            c = lookAtCard(cardIdx);
            if (c.getType() == gallery) { // Si la carte est une gallerie
                currCard = (GalleryCard) c;
                p = this.board.getPossiblePositions(currCard); // On calcule les positions possibles pour cette carte

                for (Couple currCpl : p) { // Pour chaque position possible
                    for (Couple goal : goalsToTest) { // Et pour chaque but
                        h = getDistanceToGoal(goal, currCpl); // On calcul l'heuristique (distance position <-> but)
                        //System.out.printf("Goal : (%2d,%2d) Pos : (%2d,%2d) \n\t BEST :\n\t\t Pos : (%2d,%2d) \n\t\t Card : {(%2d,%2d) %5s} \n\tCURRENT :\n\t\t {(%2d,%2d) %5s} -> %2d : %2d", goal.getLine(), goal.getColumn(), currCpl.getLine(), currCpl.getColumn(), bestCpl.getLine(), bestCpl.getColumn(), ( (GalleryCard) bestCard).getLine(), ( (GalleryCard) bestCard).getColumn(), Integer.toBinaryString(( (GalleryCard) bestCard).getConfig()), currCard.getLine(), currCard.getColumn(), Integer.toBinaryString(currCard.getConfig()), h, hMin);

                        // TODO : Verifier si on peut finir le chemin
                        // TODO : Si égalité favoriser la carte la plus résistante si mineur (et inversement)

                        if (h < hMin || hMin == -1) { // Si l'heuristique est minimale
                            //System.out.printf(" True");
                            hMin = h; // On met à jour l'heuristique max
                            bestCpl = currCpl; // On garde la position
                            bestCard = currCard; // et la carte associée
                        }
                        //System.out.printf("\n");
                    }
                }
            }
        }
        this.posToPlay = bestCpl;
        this.cardToPlay = bestCard;
    }


    public void mediumPlay() {
            System.out.println("TODO : IA Medium");
    }


    // Utils

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

    // Retroune la carte à jouer
    public Card getCardToPlay() {
        return cardToPlay;
    }

    // Retourne la position où jouer la carte
    // getPosPlay n'est affectée/mise à jour que si la carte à jouer est une gallerie
    public Couple getPosToPlay() {
        return posToPlay;
    }
    
    public Player getAPlayerInList () {
    	return this.aPlayerInList;
    }
    
    public Tools getToolOfAPlayerInList () {
    	return toolOfAPlayerInList;
    }

    @Override
    public boolean iaPlayCard() {
        switch (this.difficulty) {
            case Easy:
                randomPlay();
                break;
            case Medium:
                //playMedium();
                break;
            case Player:
                System.err.println("Pas une IA");
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


    public String debugString(){
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
        if (this.cardToPlay != null) renvoi += this.cardToPlay + "\n";
        if (this.posToPlay != null) renvoi += this.posToPlay + "\n";

        return renvoi;
    }
}
