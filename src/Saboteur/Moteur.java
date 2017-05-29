/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Saboteur;

import Cards.*;
import Cards.GalleryCard.Gallery_t;
import Cards.RepareSabotageCard.Tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;

import IHM.ChoixRole;
import IHM.GameInteract;
import Player.*;
import Board.Board;
import Board.Couple;


import java.util.ArrayList;

/**
 *
 * @author uwalakae
 */
public class Moteur {
    private ArrayList<Player> arrayPlayer;
    private Deck pile;
    private int currentPlayer = -1;
    private HandRole roleCards;
    public ArrayList<Boolean> roleTaken;
    private Board board;

    private long echeance;

    private ChoixRole choixroleControler;
    private GameInteract gameInteractControler;

    private State state;
    public enum State {
        Waiting,
        ChooseRole,
        Game,
        ChooseGold;
    }
    
    
    
    public void save(String filename) {
    	try{
			PrintWriter saveFile = new PrintWriter(filename, "UTF-8");
			saveFile.println(this.arrayPlayer.size());
			for (int i=0; i<arrayPlayer.size(); i++) {
				saveFile.println(arrayPlayer.get(i));
			}
			saveFile.println(this.arrayPlayer.get(currentPlayer).getPlayerName());
			//saveFile.println("L'état du Deck:");
			saveFile.println(this.pile);			
			saveFile.close();
		} catch (IOException e) {
			System.err.println("Erreur: Echec d'ouverture du fichier.");
		}
    }

    // ligne nbJoueur colonne Nb carte de 0 à 7 contenant le nombre de cartes en début de partie
    final int [] ruleNbCard = {6, 6, 6, 5, 5, 4, 4, 4};
    
    // constructeur
    public Moteur(int nbPlayer){
        arrayPlayer = new ArrayList<Player>();
        this.pile = new DeckGalleryAction();
        initArrayPlayer(nbPlayer);
        currentPlayer = 0;

        for(int i=0; i<nbPlayer(); i++){
            arrayPlayer.get(i).resetPlayer();
        }

        roleCards = new HandRole(nbPlayer());
        initRoleTaken();
        this.board = new Board();
        state = State.Waiting;
        setAllPlayerBoard();
        initHand();
        this.echeance = System.nanoTime();
    }

    public Moteur(ArrayList<Player> arrayPlayer, String option){
        if(arrayPlayer.size() >= 3 && arrayPlayer.size() <=10){
            this.arrayPlayer = arrayPlayer;
            if(option.equals("--debugBoard")){
                this.pile = new DeckGalleryAction(80, 60);
            } else {
                this.pile = new DeckGalleryAction();
            }
            currentPlayer = 0;


            for(int i=0; i<nbPlayer(); i++){
                arrayPlayer.get(i).resetPlayer();
            }

            roleCards = new HandRole(nbPlayer());
            initRoleTaken();
            this.board = new Board();
            state = State.Waiting;
            this.echeance = System.nanoTime();

            setAllPlayerBoard();
            initHand();

            System.out.println("Partie configurée!");


        } else {
            System.err.println("Tableau de joueur impossible");
        }
    }


    public void setChoixroleControler(ChoixRole c){
        this.choixroleControler = c;
    }
    public void setGameInteractControler(GameInteract g){
        this.gameInteractControler = g;
    }

    public void setAllPlayerBoard(){
        for(int i=0; i<nbPlayer(); i++){
            arrayPlayer.get(i).setBoard(this.board);
        }
    }


    // initialise le tableau contenant les joueurs
    private void initArrayPlayer(int n){
        int nbPlayer;

        if(n < 3 || n > 10){
            nbPlayer = 3;
        } else {
            nbPlayer = n;
        }

        for(int i=0; i<nbPlayer; i++){
            arrayPlayer.add(new PlayerHuman(i+1, this.board));
        }
    }

    public void initRoleTaken(){
        roleTaken = new ArrayList<>();

        for(int i=0; i<roleCards.nbCard(); i++){
            roleTaken.add(false);
        }
    }

    // fait piocher le bon nombre de carte a tous les joueurs
    public void initHand(){

        int nbCard = ruleNbCard[this.nbPlayer()-3];

        for(int i=0; i<nbPlayer(); i++){
            for(int j=0; j<nbCard; j++){
                arrayPlayer.get(i).drawCard(this.pile);
            }
        }

    }

    // choix des roles en début de manche
    public void chooseRole(int numPlayer, int numCard) throws Exception{

        if(!this.roleCards.isEmpty()){
            if(numCard >= 0 && numCard < this.roleCards.nbCard() && numPlayer >= 0 && numPlayer < nbPlayer()){
                Card c = this.roleCards.chooseOne_with_remove(numCard);
                this.arrayPlayer.get(numPlayer).assignRole(c);
            } else {
                throw new Exception();
            }
        } else {
            throw new Exception();
        }
    }

    // choix des roles en début de manche
    public void chooseRole(int numCard) throws Exception{

        if(!this.roleCards.isEmpty()){
            if(numCard >= 0 && numCard < this.roleCards.nbCard() && this.currentPlayer >= 0 && this.currentPlayer < nbPlayer()){
                Card c = this.roleCards.chooseOne_with_remove(numCard);

                this.arrayPlayer.get(this.currentPlayer).assignRole(c);
                nextPlayer();
            } else {
                throw new Exception();
            }
        } else {
            throw new Exception();
        }
    }

    // si tous les roles sont attribués
    public boolean allRoleAreSet(){
        for(int i=0; i<nbPlayer(); i++){
            if(this.arrayPlayer.get(i).getRole() == null){
                return false;
            }
        }
        return true;
    }

    // affiche les infos joueurs en version texte
    public void promptPlayers(){
        for(int i=0; i<nbPlayer(); i++){
            System.out.println(arrayPlayer.get(i));
            System.out.println();
        }
    }

    // affiche les infos joueurs en version texte
    public void promptPlayersRole(){
        for(int i=0; i<nbPlayer(); i++){
            System.out.println(arrayPlayer.get(i).getPlayerName()+" "+arrayPlayer.get(i).getRole());
        }
    }

    public void setCurrentPlayer(int i){
        this.currentPlayer = i;
    }


    // passe au joueur suivant
    public void nextPlayer(){
        currentPlayer = (currentPlayer+1)%nbPlayer();
        echeance = getCurrentPlayer().waitingTime()*1000000 + System.nanoTime();
    }

    // renvoie le nombre max de cartes que les joueurs peuvent avoir en main
    public int maxHandCard(){
        return ruleNbCard[nbPlayer()-3];
    }

    // renvoie le nombre de joueur
    public int nbPlayer(){
        return arrayPlayer.size();
    }

    public Player getPlayer(int i){
        if(i >= 0 && i < nbPlayer()){
            return arrayPlayer.get(i);
        } else {
            return null;
        }
    }

    // si le joueur courant a un role
    public boolean roleSet(){
        return getCurrentPlayer().getRole() != null;
    }

    // le joueur courant joue une carte sur le board
    public void play(GalleryCard c){
        if(c.getType() == Card.Card_t.gallery){

            // TODO a test
            // ajout dans sur le board
            this.board.addCard(c);
            nextPlayer();
        }
    }

    // le joueur courant joue une carte revele goal ou effondrement
    public void play(ActionCard c, Couple cou){
        if(c.getType() == Card.Card_t.action){
            if(c.getAction() == ActionCard.Action.Map){
                Card goal = this.lookGoal(cou);
                System.out.println("Carte goal: "+goal);
                nextPlayer();
            } else if(c.getAction() == ActionCard.Action.Crumbling){

                // TODO a test
                this.board.removeCard(cou);
                nextPlayer();
            }
        }
    }

    // le joueur courant joue une carte Sabotage
    public void play(RepareSabotageCard c, Player p){
        if(c.getType() == Card.Card_t.action){
            if(c.getAction() == ActionCard.Action.Sabotage){
                this.getCurrentPlayer().putSabotage(c, p);
                nextPlayer();
            }
        }
    }

    // le joueur courant joue une carte Repare sur un joueur
    public void play(RepareSabotageCard c, RepareSabotageCard.Tools t, Player p){
        if(c.getType() == Card.Card_t.action){
            if(c.getAction() == ActionCard.Action.Repare){
                this.getCurrentPlayer().putRepare(c, t, p);
                nextPlayer();
            }
        }

    }

    // regarde la carte but choisi par le joueur
    public Card lookGoal(Couple c){
        if(c.getColumn() == 8 && (c.getLine() == 0 || c.getLine() == 2 || c.getLine() == -2)){
            System.out.println("Taille mine: "+this.board.getMineSize());
            if(c.getLine() == 2){ // B3
                return this.board.getMineElement(3).getCard();
            } else if(c.getLine() == 0){ // B2
                return this.board.getMineElement(2).getCard();
            } else if(c.getLine() == -2){ // B1
                return this.board.getMineElement(1).getCard();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public void setState(State s){
        this.state = s;
    }

    public State getState(){
        return this.state;
    }

    public HandRole getRoleCards(){
        return this.roleCards;
    }

    public ArrayList<Boolean> getRoleCardsTaken(){
        return this.roleTaken;
    }

    public boolean isTaken(int i){
        if(i >= 0 && i < roleTaken.size()){
            return roleTaken.get(i);
        } else {
            return false;
        }
    }

    public void setTrueTaken(int i){
        if(i >= 0 && i < roleTaken.size()){
            this.roleTaken.set(i, true);
        }
    }

    public Card getRoleCard(int i) throws Exception{
        if(i >= 0 && i < roleTaken.size()){
            return roleCards.chooseOne_without_remove(i);
        } else {
            throw new Exception();
        }
    }

    public long getEcheance(){
        return this.echeance;
    }

    public void setEcheance(long l){
        this.echeance = l;
    }

    // si la manche est terminée
    public boolean endGame() {

        boolean emptyHand = true;

        for (int i = 0; i < arrayPlayer.size(); i++) {
            if (arrayPlayer.get(i).nbCardHand() > 0) {
                emptyHand = false;
            }
        }

        if (this.board.goldReached()) {
            return true;
        } else if (this.board.goldBlocked() && pile.nbCard() == 0 && emptyHand) {
            return true;
        } else if (!this.board.goldReached() && pile.nbCard() == 0 && emptyHand){
            return true;
        } else {
            return false;
        }
    }

    // renvoie le numero du joueur courant
    public int currentNumPlayer(){
        return this.currentPlayer;
    }

    // renvoie l'objet Player du joueur courant
    public Player getCurrentPlayer(){
        return arrayPlayer.get(this.currentPlayer);
    }

    // renvoie une arrayList de tous les joueurs dans la partie
    public ArrayList<Player> getAllPlayers(){
        return this.arrayPlayer;
    }

    public Deck getDeck(){
        return this.pile;
    }

    public int getNbRoleCards(){
        return roleCards.nbCard();
    }

    public ChoixRole getChoixroleControleur(){
        return this.choixroleControler;
    }

    public GameInteract getGameInteractControler(){
        return this.gameInteractControler;
    }

    public Board getBoard(){
        return this.board;
    }

    public String toString(){
        String renvoi = "Moteur: \n";

        renvoi += "Etat: "+this.state+"\n";
        renvoi += "Joueur courant: "+this.getCurrentPlayer().getPlayerName() + "Index : "+this.currentPlayer +"\n";
        renvoi += "Deck: "+this.pile.nbCard() +" cartes \n";
        renvoi += this.roleCards.print_without_visibility() + "\n";
        renvoi += "Joueurs: \n";
        for(int i=0; i<nbPlayer(); i++){
            renvoi += this.getPlayer(i).getPlayerName() + " "+this.getPlayer(i).getRole()+ " ; \n";
        }

        for(int i=0; i<nbPlayer(); i++){
            renvoi += this.roleTaken.get(i) + " ; ";
        }



        return renvoi;
    }


    // reset les roles
    public void resetRole(){
        for(int i=0; i<nbPlayer(); i++){
            arrayPlayer.get(i).setEmptyRole();
        }
    }


}
