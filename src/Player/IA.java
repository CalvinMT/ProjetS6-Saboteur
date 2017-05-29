package Player;

import Board.Board;
import Board.Couple;
import Cards.*;
import Cards.ActionCard.Action;

import java.util.ArrayList;
import java.util.Random;

import static Cards.Card.Card_t.action;
import static Cards.Card.Card_t.gallery;
import static java.lang.Math.abs;
import static java.lang.Math.nextAfter;

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
        else if (cardToPlay.getType() == action ){
            ActionCard actioncard = (ActionCard) cardToPlay;
            if (actioncard.getAction() == Action.Map) {
                // TODO
            }
            else if (actioncard.getAction() == Action.Crumbing) {
                // TODO
            }
            else if (actioncard.getAction() == Action.Repare) {
                // TODO
            }
            else if (actioncard.getAction() == Action.Sabotage) {
                // TODO
            }
        }
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
        for (Card c : p.getPlayableCards().getArrayCard()) { // TODO : ajout move dans TreeNode
            switch (c.getType()) {
                case gallery :
                    GalleryCard galleryCard = (GalleryCard) c;
                    for (Couple pos : ia.board.getPossiblePositions(galleryCard)) {
                        nextIA = ia.clone();
                        t.setBoard(ia.board);

                        galleryCard.setLine(pos.getLine());
                        galleryCard.setColumn(pos.getColumn());

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
                        for (Couple g : ia.goalsToTest) { // TODO : Ajout goalsToTest dans TreeNode
                            nextIA = ia.clone();
                            if (this.board.getNodeFromMine(g).getCard().isGold()) {
                                nextIA.addGoldGoal(g);
                            }
                            else {
                                nextIA.ignoreGoal(g);
                            }
                            if (depth == 0 /* TODO : Si fin de jeu */) { // Si on est au dernier tour
                                t.addToNext(new TreeNode(p.getRole().equals(new RoleCard("Saboteur")), nextIA)); // Ajout des feuilles
                            } else {
                                t.addToNext(genConfigTree(playerIdx++, depth--, nextIA)); // Sinon ajout d'un nouveau noeud
                            }
                        }
                    }
                    else if (actionCard.getAction() == Action.Crumbing) {
                        for (int i = 0; i < ia.board.getMineSize(); i++ ) {
                            nextIA = ia.clone();
                            Couple cpl = this.board.getMineElement(i).getCard().getCoord();
                            t.setBoard(ia.board);
                            nextIA.board.removeCard(cpl);
                            if (depth == 0 /* TODO : Si fin de jeu */) { // Si on est au dernier tour
                                t.addToNext(new TreeNode(p.getRole().equals(new RoleCard("Saboteur")), nextIA));// Ajout des feuilles
                            } else {
                                t.addToNext(genConfigTree(playerIdx++, depth--, ia)); // Sinon ajout d'un nouveau noeud
                            }
                        }
                    }
                    else if (actionCard.getAction() == Action.Repare) {
                        for (Player currPlayer : ia.allPlayers) {
                            nextIA = ia.clone();
                            if (currPlayer.getAttributeCards().containsTools(((RepareSabotageCard) actionCard).getTool())) {
                                currPlayer.setRepare((RepareSabotageCard) actionCard, ((RepareSabotageCard) actionCard).getTool());
                            }
                            if (depth == 0 /* TODO : Si fin de jeu */) { // Si on est au dernier tour
                                t.addToNext(new TreeNode(p.getRole().equals(new RoleCard("Saboteur")), nextIA)); // Ajout des feuilles
                            } else {
                                t.addToNext(genConfigTree(playerIdx++, depth--, nextIA)); // Sinon ajout d'un nouveau noeud
                            }
                        }
                    }
                    else if (actionCard.getAction() == Action.Sabotage) {
                        // TODO
                        for (Player currPlayer : ia.allPlayers) {
                            nextIA = ia.clone();
                            if (!currPlayer.getAttributeCards().containsTools(((RepareSabotageCard) actionCard).getTool())) {
                                currPlayer.setSabotage((RepareSabotageCard) actionCard);
                            }
                            if (depth == 0 /* TODO : Si fin de jeu */) { // Si on est au dernier tour
                                t.addToNext(new TreeNode(p.getRole().equals(new RoleCard("Saboteur")), nextIA)); // Ajout des feuilles
                            } else {
                                t.addToNext(genConfigTree(playerIdx++, depth--, nextIA)); // Sinon ajout d'un nouveau noeud
                            }
                        }

                    }
                    if (depth == 0 /* TODO : Si fin de jeu */) { // Si on est au dernier tour
                        t.addToNext(new TreeNode(p.getRole().equals(new RoleCard("Saboteur")), ia)); // Ajout des feuilles
                    } else {
                        t.addToNext(genConfigTree(playerIdx++, depth--, ia)); // Sinon ajout d'un nouveau noeud
                    }
                    break;
                default:
                    System.out.println(c.getType() + " not implemented.");
                    break;
            }

        }
        return t;
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

    /* USELESS
    public void genMoves() {
        getGalleryMoves();
        // TODO : getActionsMoves();
    }
    */

    /*
    public void computeMovesValue() {
        int v;
        Move m;
        //genMoves();
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
    */

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

    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public void setGoalsToTest(ArrayList<Couple> goalsToTest) {
        this.goalsToTest = goalsToTest;
    }

    public void setAllPlayers(ArrayList<Player> allPlayers) {
        this.allPlayers = allPlayers;
    }
}
