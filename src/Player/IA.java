package Player;

import Board.*;
import Board.Couple;
import Cards.*;
import Cards.ActionCard.Action;

import java.util.ArrayList;
import java.util.Random;

import static Cards.ActionCard.Action.*;
import static Cards.Card.Card_t.action;
import static Cards.Card.Card_t.gallery;
import static java.lang.Math.abs;





public class IA extends Player {
    //final int SABOTAGE_VALUE;
    //final int GALLERY_VALUE;
    //final int ACTION_VALUE;

	//private final int MAXDEPTH = 4;

    private Card cardToPlay;
    private Couple posToPlay;
    private ArrayList<Move> moves;
    private ArrayList<Player> allPlayers;
    private ArrayList<Couple> goalsToTest;
    private Player playerToRepare;
    private Player playerToSabotage;

    public IA() {
        this(0, "IA", Difficulty.Easy, new ArrayList<>(), new ArrayList<>(), new Board());
    }

    public IA(int index) {
        this(index, "IA", Difficulty.Easy, new ArrayList<>(), new ArrayList<>(), new Board());
    }

    public IA(int index, String name) {
        this(index, name, Difficulty.Easy, new ArrayList<>(), new ArrayList<>(), new Board());
    }

    public IA(int index, String name, Difficulty d){
        this(index, name, d, new ArrayList<>(), new ArrayList<>(), new Board());
    }

    public IA(int index, String name, Difficulty d, ArrayList<Player> p){
        this(index, name, d, p, new ArrayList<>(), new Board());
    }

    public IA(int index, String name, Difficulty d, ArrayList<Player> p, ArrayList<Couple> goals) {
        this(index, name, d, p, goals, new Board());
    }

    public IA(int index, String name, Difficulty d, ArrayList<Player> p, ArrayList<Couple> goals, Board board) {
        this.num = index;
        this.difficulty = d;
        this.goldPoints = 0;
        this.allPlayers = p;
        this.playerName = name;
        this.board = board;
        this.avatar = "robot_miner";
        moves = new ArrayList<Move>();
        this.goalsToTest = goals;
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

    public IA clone() {
        return new IA(this.num, this.getPlayerName(), this.difficulty, this.allPlayers, this.goalsToTest, this.board);
    }

    // IA Random
    public Move randomPlay() {
        ArrayList<Couple> p;
        Move currentMove = null;
        Random r = new Random();
        int range = 1, index;
        if (nbCardHand() > 1)
            range = r.nextInt(nbCardHand() - 1);
        else if (nbCardHand() == 1)
            range = r.nextInt(1);
        if (range < 1) {
            range = 1;
        }
        index = r.nextInt(range);
        this.cardToPlay = lookAtCard(index);
        if (cardToPlay.getType() == gallery) {
        	GalleryCard galCardToPlay = (GalleryCard) cardToPlay;
        	if (galCardToPlay.canHasCenter() && ((RoleCard)this.getRole()).isMiner()) {
        		currentMove = new Move(cardToPlay, true);
        	}
        	else {
        		this.board.computeAccessCards();
                p = this.board.getPossiblePositions((GalleryCard) cardToPlay);
                if (p.size() > 1) {
                    range = r.nextInt(p.size() - 1);
                    if (range < 1)
                        range = 1;
                    this.posToPlay = p.get(r.nextInt(range));
                    currentMove = new Move(cardToPlay, posToPlay);
                }
                else if (p.size() == 1) {
                    range = 1;
                    this.posToPlay = p.get(r.nextInt(range));
                    currentMove = new Move(cardToPlay, posToPlay);
                }
                else
                    System.out.println("Il n'y a aucune case ou placer la carte.");
        	}
            
        }
        else if (cardToPlay.getType() == action ){
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
            		currentMove = new Move(cardToPlay, cpl);
            	}
            	//
            	else {
            		currentMove = new Move(cardToPlay, true);
            	}
            	
            }

            else if (actioncard.getAction() == Action.Crumbling) {
            	this.chooseWhereToCrumb();
            	currentMove = new Move(cardToPlay, posToPlay);
            }
            else if (actioncard.getAction() == Action.Repare) {
            	int playerIndex = choosePlayerToRepair((RepareSabotageCard)actioncard, (RoleCard)this.getRole());  
            	if (playerIndex > -1)
            		currentMove = new Move(cardToPlay, playerIndex);
            	else
            		System.err.println("Indice du Joueur incorrecte.");
            }
            else if (actioncard.getAction() == Action.Sabotage) {
            	int playerIndex = choosePlayerToSabotage((RepareSabotageCard)actioncard, (RoleCard)this.getRole());
            	if (playerIndex > -1)
            		currentMove = new Move(cardToPlay, playerIndex);
            	else
            		System.err.println("Indice du Joueur incorrecte.");
            }
        }
        return currentMove;
    }

    // Computing
    public TreeNode genConfigTree(int playerIdx, int depth, IA ia) { // int maxDepth ?
        Player p = ia.allPlayers.get(playerIdx % allPlayers.size());
        TreeNode t  = new TreeNode(p.getRole().equals(new RoleCard("Saboteur")),
                                    new IA(this.num, this.getPlayerName(), this.difficulty, ia.allPlayers, ia.goalsToTest));
        IA nextIA;

        if (p.getAttributeCards().getNbAttribute() != 0) { // Le joueur ne peut pas jouer
            t.setBoard(ia.board);
            t.addToNext(genConfigTree(playerIdx++, depth--, ia));
        }
        for (int cardIdx = 0; cardIdx < p.getPlayableCards().getArrayCard().size(); cardIdx++) {
            Card c = p.getPlayableCards().getArrayCard().get(cardIdx);
            switch (c.getType()) {
                case gallery :
                    GalleryCard galleryCard = (GalleryCard) c;
                    for (Couple pos : ia.board.getPossiblePositions(galleryCard)) {
                        nextIA = ia.clone();
                        t.setBoard(ia.board);
                        galleryCard.setLine(pos.getLine());
                        galleryCard.setColumn(pos.getColumn());
                        t.setMove(new Move(galleryCard, pos));
                        nextIA.board.addCard(galleryCard);
                        if (depth == 0 /* TODO : Si fin de jeu */) { // Si on est au dernier tour
                            t.addToNext(new TreeNode(p.getRole().equals(new RoleCard("Saboteur")), nextIA)); // Ajout des feuilles
                        } else {
                            t.addToNext(genConfigTree(playerIdx++, depth--, nextIA)); // Sinon ajout d'un nouveau noeud
                        }
                    }
                    break;
                case action :
                    ActionCard actionCard = (ActionCard) c;
                    if (actionCard.getAction() == Action.Map) {
                        for (int i = 0; i < ia.goalsToTest.size(); i++) { // TODO : Ajout goalsToTest dans TreeNode
                            Couple g = ia.goalsToTest.get(i);
                            nextIA = ia.clone();
                            if (this.board.getNodeFromMine(g).getCard().isGold()) {
                                nextIA.addGoldGoal(g);
                            }
                            else {
                                nextIA.ignoreGoal(g);
                            }
                            t.setMove(new Move(actionCard, i));
                            if (depth == 0 /* TODO : Si fin de jeu */) { // Si on est au dernier tour
                                t.addToNext(new TreeNode(p.getRole().equals(new RoleCard("Saboteur")), nextIA)); // Ajout des feuilles
                            } else {
                                t.addToNext(genConfigTree(playerIdx++, depth--, nextIA)); // Sinon ajout d'un nouveau noeud
                            }
                        }
                    }

                    else if (actionCard.getAction() == Crumbling) {

                        for (int i = 0; i < ia.board.getMineSize(); i++ ) {
                            nextIA = ia.clone();
                            Couple cpl = this.board.getMineElement(i).getCard().getCoord();
                            t.setBoard(ia.board);
                            t.setMove(new Move(actionCard, cpl));
                            nextIA.board.removeCard(cpl);
                            if (depth == 0 /* TODO : Si fin de jeu */) { // Si on est au dernier tour
                                t.addToNext(new TreeNode(p.getRole().equals(new RoleCard("Saboteur")), nextIA));// Ajout des feuilles
                            } else {
                                t.addToNext(genConfigTree(playerIdx++, depth--, ia)); // Sinon ajout d'un nouveau noeud
                            }
                        }
                    }
                    else if (actionCard.getAction() == Repare) {
                        for (int i = 0; i < ia.allPlayers.size(); i++) {
                            Player currPlayer = ia.allPlayers.get(i);
                            nextIA = ia.clone();
                            if (currPlayer.getAttributeCards().containsTools(((RepareSabotageCard) actionCard).getTool())) {
                                currPlayer.setRepare((RepareSabotageCard) actionCard);
                                t.setMove(new Move(actionCard, i));
                            }
                            if (depth == 0 /* TODO : Si fin de jeu */) { // Si on est au dernier tour
                                t.addToNext(new TreeNode(p.getRole().equals(new RoleCard("Saboteur")), nextIA)); // Ajout des feuilles
                            } else {
                                t.addToNext(genConfigTree(playerIdx++, depth--, nextIA)); // Sinon ajout d'un nouveau noeud
                            }
                        }
                    }
                    else if (actionCard.getAction() == Sabotage) {
                        for (int i = 0; i < ia.allPlayers.size(); i++){
                            Player currPlayer = ia.allPlayers.get(i);
                            nextIA = ia.clone();
                            if (!currPlayer.getAttributeCards().containsTools(((RepareSabotageCard) actionCard).getTool())) {
                                currPlayer.setSabotage((RepareSabotageCard) actionCard);
                                t.setMove(new Move(actionCard, i));
                            }
                            if (depth == 0 /* TODO : Si fin de jeu */) { // Si on est au dernier tour
                                t.addToNext(new TreeNode(p.getRole().equals(new RoleCard("Saboteur")), nextIA)); // Ajout des feuilles
                            } else {
                                t.addToNext(genConfigTree(playerIdx++, depth--, nextIA)); // Sinon ajout d'un nouveau noeud
                            }
                        }
                    }
                    break;
                default:
                    System.out.println(c.getType() + " not implemented.");
                    break;
            }
            // Defausse
            nextIA = ia.clone();
            t.setMove(new Move(c, true));
            nextIA.allPlayers.get(this.num).discard(cardIdx);
            if (depth == 0 /* TODO : Si fin de jeu */) { // Si on est au dernier tour
                t.addToNext(new TreeNode(p.getRole().equals(new RoleCard("Saboteur")), nextIA)); // Ajout des feuilles
            } else {
                t.addToNext(genConfigTree(playerIdx++, depth--, nextIA)); // Sinon ajout d'un nouveau noeud
            }

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

        for (int cardIdx = 0; cardIdx < nbCardHand(); cardIdx++) { // Parcours des cartes en main
            c = lookAtCard(cardIdx);
            if (c.getType() == gallery) { // Si la carte est une gallerie
                moves.add(whereToPlaceCard((GalleryCard) c));
            }
        }

        getBestValueMove();
    }

    public boolean choosePosition() {
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
                        if (h < hMin || hMin == -1) { // Si l'heuristique est minimale
                            hMin = h; // On met à jour l'heuristique max
                            bestCpl = currCpl; // On garde la position
                            bestCard = currCard; // et la carte associée
                        }
                    }
                }
            }
        }
        this.posToPlay = bestCpl;
        this.cardToPlay = bestCard;
        return posToPlay.equals(new Couple(0, 0));
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

    public void execMove(Move m) {
        int i = 0;
        if (m.getDiscard()) {

            while (i < this.nbCardHand() && !this.getPlayableCards().getArrayCard().get(i).equals(m.getCard())){ i++;}
            this.discard(i);
        }
        switch(m.getCard().getType()) {
            case gallery :
                GalleryCard galleryCard = (GalleryCard) m.getCard();
                galleryCard.setLine(m.getPositionTarget().getLine());
                galleryCard.setColumn(m.getPositionTarget().getColumn());
                this.board.addCard(galleryCard);
                break;
            case action :
                ActionCard actionCard = (ActionCard) m.getCard();
                switch (actionCard.getAction()) {
                    case Sabotage:
                        this.allPlayers.get(m.getTargetIdx()).setSabotage((RepareSabotageCard) m.getCard());
                        break;
                    case Repare:
                        this.allPlayers.get(m.getTargetIdx()).setRepare((RepareSabotageCard) m.getCard());
                        break;
                    case Crumbling:
                        this.board.removeCard(m.getPositionTarget());
                        break;
                }
                break;
            /*case default:
                break;*/
        }
    }

    public Move mediumPlay() {
        Move move = null;
        TreeNode tree;
        float nodeValue;
        Random r = new Random();


        System.out.println("TODO : IA Medium");
        if (!isInSwitchZone()) {
            if (choosePosition()) { // Se rapproche des buts
                GalleryCard c = (GalleryCard) this.cardToPlay;
                System.out.println("choosePos");
                c.setLine(this.posToPlay.getLine());
                c.setColumn(this.posToPlay.getColumn());
                move = new Move(c, this.posToPlay);
            }
            else { // defausse
                return new Move(this.getPlayableCards().getArrayCard().get(r.nextInt(this.nbCardHand())), false);
            }
        }
        else {
            System.out.println("minimax");
            tree = genConfigTree(this.getNum(), 2, this);
            nodeValue = minimax(tree, 2, -9999, 9999);
            for (TreeNode n : tree.getNext()) {
                if (n.getValue() == nodeValue) {
                    move =  n.getMove();
                }
            }
        }
        this.execMove(move);
        return move;
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
    public int choosePlayerToSabotage(RepareSabotageCard card, RoleCard roleIA){
        Player currentplayer;
        int playerIndex = -1;
        if (roleIA.isSaboteur()) {
            for (int i=0; i<this.allPlayers.size(); i++) {
                currentplayer = this.allPlayers.get(i);
                if (currentplayer != this && currentplayer.getRole().equals(new RoleCard("Mineur")) && !currentplayer.getAttributeCards().containsTools(card.getTool())) {
                    playerIndex = i;
                }
            }
        }
        else if (roleIA.isMiner()) {
            for (int i=0; i<this.allPlayers.size(); i++) {
                currentplayer = this.allPlayers.get(i);
                if (currentplayer != this && currentplayer.getRole().equals(new RoleCard("Saboteur")) && !currentplayer.getAttributeCards().containsTools(card.getTool()) ) {
                	playerIndex = i;
                }
            }
        }
        else
            System.err.println("Role incorrecte.");
        return playerIndex;
    }
    
    // Retourne un joueur quelconque, lui-meme inclus & ayant le meme role, au quel l'IA mettra une carte pour enlever un malus
    public int choosePlayerToRepair(RepareSabotageCard card, RoleCard roleIA){
        Player currentplayer;
        int playerIndex = -1;
        if (roleIA.isMiner()){
            for (int i=0; i<this.allPlayers.size(); i++) {
                currentplayer = this.allPlayers.get(i);
                if (currentplayer.getRole().equals(new RoleCard("Mineur")) && currentplayer.getAttributeCards().containsTools(card.getTool()) ) {
                    playerIndex = i;
                }
            }
        }
        else if (roleIA.isSaboteur()){
            for (int i=0; i<this.allPlayers.size(); i++) {
                currentplayer = this.allPlayers.get(i);
                if (currentplayer.getRole().equals(new RoleCard("Saboteur")) && currentplayer.getAttributeCards().containsTools(card.getTool())  ) {
                	playerIndex = i;
                }
            }
        }
        else
            System.err.println("Role incorrecte.");
        
        return playerIndex;
    }

    public float minimax(TreeNode t, int depth, float min, float max) {
        float v, vPrim;

        if (t.isLeaf() || depth == 0) return t.evaluate(); // Fin de l'arbre ou profondeur max;
        if (t.isMaxNode()) {
            v = min;
            for (TreeNode n : t.getNext()) {
                vPrim = minimax(n, depth - 1, v, max); // Calcul reccursif
                if (vPrim > v) v = vPrim; // la meilleur branche
                n.setValue(v);
                if (v > max) {
                    return max; // AB pruning
                }
            }
            return v;
        }
        else {
            v = max;
            for (TreeNode n : t.getNext()) {
                vPrim = minimax(n, depth - 1, min, v);
                if (vPrim < v) v = vPrim;
                n.setValue(v);
                if (v < min) return min;
            }
            return v;
        }
    }


    @Override
    public Move iaPlayCard() {
        switch (this.difficulty) {
            case Easy:
                return randomPlay();
            case Medium:
                return mediumPlay();
            case Player:
                System.err.println("Pas une IA");
            default:
                System.out.println("TODO : IA " + this.difficulty);
        }

        return new Move();
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

    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public void setGoalsToTest(ArrayList<Couple> goalsToTest) {
        this.goalsToTest = goalsToTest;
    }

    public void setAllPlayers(ArrayList<Player> allPlayers) {
        this.allPlayers = allPlayers;
    }
    
    public Player getPlayerToRepare() {
    	return playerToRepare;
    }
    
    public Player getPlayerToSabotage() {
    	return playerToSabotage;
    }
}
