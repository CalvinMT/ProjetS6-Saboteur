package Player;

import Board.*;
import Board.Couple;
import Cards.*;
import Cards.ActionCard.Action;

import java.util.ArrayList;
import java.util.Random;

import static Cards.Card.Card_t.gallery;
import static java.lang.Math.abs;

/**
 * Created by thespygeek on 11/05/17.
 */
public class IA extends Player {
    //final int SABOTAGE_VALUE;
    //final int GALLERY_VALUE;
    //final int ACTION_VALUE;

    private final int MAXDEPTH = 4;

    private Card cardToPlay;
    private Couple posToPlay;
    private ArrayList<Move> moves;
    private ArrayList<Player> allPlayers;
    private ArrayList<Couple> goalsToTest;
    private Player playerToRepare;
    private Player playerToSabotage;

    public IA(int index) {
        this(index, "IA", Difficulty.Easy, new ArrayList<>());
    }

    public IA(int index, String name) {
        this(index, name, Difficulty.Easy, new ArrayList<>());
    }

    public IA(int index, String name, Difficulty d){
        this(index, name, d, new ArrayList<>());
    }

    public IA(int index, String name, Difficulty d, ArrayList<Player> p){
        this.num = index;
        this.difficulty = d;
        this.goldPoints = 0;
        this.allPlayers = p;
        this.playerName = name;
        this.avatar = "robot_miner";
        moves = new ArrayList<Move>();
        this.playableCards = new HandPlayer();
        attributeCards = new PlayerAttribute();
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
        int range = 1;
        if (nbCardHand() > 1)
            range = r.nextInt(nbCardHand() - 1);
        else if (nbCardHand() == 1)
            range = r.nextInt(1);
        if (range < 1) {
            range = 1;
        }
        this.cardToPlay = lookAtCard(r.nextInt(range));
        if (cardToPlay.getType() == gallery) {
            this.board.computeAccessCards();
            p = this.board.getPossiblePositions((GalleryCard) cardToPlay);
            if (p.size() > 1) {
                range = r.nextInt(p.size() - 1);
                if (range < 1)
                    range = 1;
                this.posToPlay = p.get(r.nextInt(range));
            }
            else if (p.size() == 1) {
                range = 1;
                this.posToPlay = p.get(r.nextInt(range));
            }
            else
                System.out.println("Il n'y a aucune case ou placer la carte.");
        }
        else if (cardToPlay.getType() == Card.Card_t.action ){
            ActionCard actioncard = (ActionCard) cardToPlay;
            if (actioncard.getAction() == Action.Map) {
                //to do: get goals to test, check for goal, and then return
            	int n, pos = 0;
            	Random random = new Random();
            	n = this.goalsToTest.size();
            	if (n > 1) {
            		pos = random.nextInt(n);
            		Couple cpl = this.goalsToTest.get(pos);
            		Node tocheck = board.getNodeFromMine(cpl);
            		if (tocheck.getCard().isGold()) 
            			this.addGoldGoal(cpl);
            		else
            			this.ignoreGoal(cpl);
            	}
            	
            }
            else if (actioncard.getAction() == Action.Crumbing) {
                // TODO
            	this.chooseWhereToCrumb();
            }
            else if (actioncard.getAction() == Action.Repare) {
                // TODO: pick a player on the other side
            	playerToRepare = this.choosePlayerToRepair((RepareSabotageCard)actioncard, (RoleCard)this.getRole());          	
            	
            }
            else if (actioncard.getAction() == Action.Sabotage) {
                // TODO:
            	playerToSabotage = this.choosePlayerToSabotage((RepareSabotageCard)actioncard, (RoleCard)this.getRole());
            }
        }
    }

    // Computing
    public TreeNode genConfigTree(int playerIdx,Board b, int depth) { // int maxDepth ?
        Player p = allPlayers.get(playerIdx % allPlayers.size());
        TreeNode t  = new TreeNode(b, p.getRole().equals(new RoleCard("Saboteur")));
        for (Card c : p.getPlayableCards().getArrayCard()) {
            if (c.getType() == gallery) {
                GalleryCard galleryCard = (GalleryCard) c;
                for (Couple pos : b.getPossiblePositions(galleryCard)) {
                    t.board = b;
                    galleryCard.setLine(pos.getLine());
                    galleryCard.setColumn(pos.getColumn());
                    t.board.addCard(galleryCard);
                    if (depth == 0 /*TODO Si fin de jeu*/) { // Si on est au dernier tour
                        t.addToNext(new TreeNode(t.board, p.getRole().equals(new RoleCard("Saboteur")))); // Ajout des feuilles
                    } else {
                        t.addToNext(genConfigTree(playerIdx++, t.board, depth--)); // Sinon ajout d'un nouveau noeud
                    }
                }
            }
            // TODO cartes action
        }
        return t;
    }

    // choisit une position à mettre la carte d'effondrement
    public void chooseWhereToCrumb(){
    	Couple position = null; // couple qui sera attribué à posToPlay
    	int neighbours = 0, maxNeighbours = 0; 
    	Node currNode;
    	for (int i=0; i<board.getMineSize(); i++) { // parcours de mine
    		currNode = board.getMineElement(i);
    		// test pour assurer que currNode n'est ni Carte Start ou parmi les cartes but
    		if (!currNode.getCard().equals(new GalleryCard()) && !this.goalsToTest.contains(currNode.getCard().getCoord())){    			
    			neighbours = 0;
    			if (currNode.getNorth() != -1) neighbours++;
    			if (currNode.getEast() != -1) neighbours++;
    			if (currNode.getSouth() != -1) neighbours++;
    			if (currNode.getWest() != -1) neighbours++;
    			
    			if (neighbours > maxNeighbours) {
    				maxNeighbours = neighbours;
    				position = currNode.getCard().getCoord();
    			}
    			
    		}    		
    	}
    	posToPlay = position;
    }


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

    public Move whereToPlaceCard(GalleryCard card) {
        int h, hMin = -1;
        ArrayList<Couple> p;

        p = this.board.getPossiblePositions(card); // On calcule les positions possibles pour cette carte

        for (Couple currCpl : p) { // Pour chaque position possible
            for (Couple goal : goalsToTest) { // Et pour chaque but
                h = getDistanceToGoal(goal, currCpl); // On calcul l'heuristique (distance position <-> but)

                //System.out.printf("Goal : (%2d,%2d) Pos : (%2d,%2d) \n\t BEST :\n\t\t Pos : (%2d,%2d) \n\t\t Card : {(%2d,%2d) %5s} \n\tCURRENT :\n\t\t {(%2d,%2d) %5s} -> %2d : %2d\n", goal.getLine(), goal.getColumn(), currCpl.getLine(), currCpl.getColumn(), bestCpl.getLine(), bestCpl.getColumn(), ( (GalleryCard) bestCard).getLine(), ( (GalleryCard) bestCard).getColumn(), Integer.toBinaryString(( (GalleryCard) bestCard).getConfig()), currCard.getLine(), currCard.getColumn(), Integer.toBinaryString(currCard.getConfig()), h, hMin);

                // TODO : Verifier si on peut finir le chemin
                // TODO : Si égalité favoriser la carte la plus résistante si mineur (et inversement)

                if (h < hMin || hMin == -1) { // Si l'heuristique est minimale
                    hMin = h; // On met à jour l'heuristique max
                    card.setLine(currCpl.getLine());
                    card.setColumn(currCpl.getColumn());
                }
            }
        }
        return new Move(card, card.getCoord(), hMin);
    }

    // TODO : Tests
    // TODO : Choisir les cartes
    // Determine la position la plus proche d'un but et retourne ses coordonnées
    public void getGalleryMoves() {
        Card c;
        //GalleryCard currCard;
        //Couple bestCpl = new Couple(0, 0);
        //ArrayList<Move> m;

        //bestCard = lookAtCard(0);
        for (int cardIdx = 0; cardIdx < nbCardHand(); cardIdx++) { // Parcours des cartes en main
            c = lookAtCard(cardIdx);
            if (c.getType() == gallery) { // Si la carte est une gallerie
                moves.add(whereToPlaceCard((GalleryCard) c));
            }
        }

        getBestValueMove();
    }

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

    private Move getBestValueMove() {
        int idx = 0,
            vMax = 0;
        for (int i = 0; i < moves.size(); i++) {
            if(moves.get(i).getValue() > vMax) {
                vMax = moves.get(i).getValue();
                idx = i;
            }
        }
        return moves.get(idx);
    }

    public void genMoves() {
        getGalleryMoves();
        // TODO : getActionsMoves();
    }

    public void computeMovesValue() {
        int v;
        Move m;
        genMoves();
        if (((RoleCard) this.getRole()).isSaboteur()) {
            if (isInSwitchZone()) {
                // TODO : bloquer la progression des mineurs / saboter
                for (int i = 0; i < moves.size(); i++) {
                    m = moves.get(i);
                    if (m.getCard().getType() == gallery) {
                        v = m.getValue();
                        // m.setValue(v / (TODO: Get max neighbors resistance) );
                    }
                }
            }
            else {
                // TODO : se rapprocher des buts avec des cartes de res faible
            }
        }
        else {
            // TODO : plus on est proche des but plus il est important de placer des cartes forte
            // Aussi Saboter les saboteur
        }
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
    
    // Retourne un joueur quelconque, différent de lui-meme & ayant un role opposé, au quel l'IA mettra un malus
    public Player choosePlayerToSabotage(RepareSabotageCard card, RoleCard roleIA){
        Player currentplayer, p = null;
        if (roleIA.isSaboteur()) {
            for (int i=0; i<this.allPlayers.size(); i++) {
                currentplayer = this.allPlayers.get(i);
                if (currentplayer != this && currentplayer.getRole().equals(new RoleCard("Mineur")) && !currentplayer.getAttributeCards().containsTools(card.getTool())) {
                    p = this.allPlayers.get(i);
                }
            }
        }
        else if (roleIA.isMiner()) {
            for (int i=0; i<this.allPlayers.size(); i++) {
                currentplayer = this.allPlayers.get(i);
                if (currentplayer != this && currentplayer.getRole().equals(new RoleCard("Saboteur")) && !currentplayer.getAttributeCards().containsTools(card.getTool()) ) {
                    p = this.allPlayers.get(i);
                }
            }
        }
        else
            System.err.println("Role incorrecte.");
        return p;
    }
    
    // Retourne un joueur quelconque, lui-meme inclus & ayant le meme role, au quel l'IA mettra une carte pour enlever un malus
    public Player choosePlayerToRepair(RepareSabotageCard card, RoleCard roleIA){
        Player currentplayer, p = null;
        if (roleIA.isMiner()){
            for (int i=0; i<this.allPlayers.size(); i++) {
                currentplayer = this.allPlayers.get(i);
                if (currentplayer.getRole().equals(new RoleCard("Mineur")) && currentplayer.getAttributeCards().containsTools(card.getTool()) ) {
                    p = currentplayer;
                }
            }
        }
        else if (roleIA.isSaboteur()){
            for (int i=0; i<this.allPlayers.size(); i++) {
                currentplayer = this.allPlayers.get(i);
                if (currentplayer.getRole().equals(new RoleCard("Saboteur")) && currentplayer.getAttributeCards().containsTools(card.getTool())  ) {
                    p = this.allPlayers.get(i);
                }
            }
        }
        else
            System.err.println("Role incorrecte.");
        return p;
    }

    public float minimax(TreeNode t, int depth, float min, float max) {
        float v, vPrim;

        if (t.isLeaf() || depth == 0) return t.evaluate(); // Fin de l'arbre ou profondeur max;
        if (t.isMaxNode()) {
            v = min;
            for (TreeNode n : t.getNext()) {
                vPrim = minimax(n, depth - 1, v, max); // Calcul recursif
                if (vPrim > v) v = vPrim; // la meilleur branche
                if (v > max) return max; // AB pruning
            }
            return v;
        }
        else {
            v = max;
            for (TreeNode n : t.getNext()) {
                vPrim = minimax(n, depth - 1, min, v);
                if (vPrim < v) v = vPrim;
                if (v < min) return min;
            }
            return v;
        }
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
        if (this.cardToPlay != null) renvoi += this.cardToPlay + "\n";
        if (this.posToPlay != null) renvoi += this.posToPlay + "\n";

        return renvoi;
    }
}
