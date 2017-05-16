/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Saboteur;
import Cards.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import Player.*;
import Board.Board;
import Board.Couple;

import javax.swing.*;
import java.util.ArrayList;

/**
 *
 * @author uwalakae
 */
public class Moteur {
    private ArrayList<Player> arrayPlayer;
    private Deck pile;
    private int currentPlayer;
    private HandRole roleCards;

    //ajout du board
    Board board;

    
    
    
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
    
    public boolean load(String filename){
    	//boolean answer = false;
    	try {
    		FileReader filereader = new FileReader(filename);
    		BufferedReader br = new BufferedReader(filereader);
    		String playerName, playerType, playerRole, tempGoldPoints, temp; // playerType = Humain | IA
    		int goldPoints;
    		int nbPlayers = Integer.parseInt(br.readLine());
    		for (int i=0; i<nbPlayers; i++){
    			playerName = br.readLine();
    			
    			playerType = br.readLine();
    			if (playerType != "Humain" || playerType != "Saboteur") return false;
    			
    			playerRole = br.readLine();
    			if (playerRole != "Mineur" || playerRole != "Saboteur") return false;
    			
    			tempGoldPoints = br.readLine();
    			if (tempGoldPoints.matches("[0-9]+")) goldPoints = Integer.parseInt(tempGoldPoints);
    			else return false;
    			// write code for the Attributes property
    			temp = br.readLine();
								
				String [] handCards = temp.split("[;]");
				if (handCards.length != 6) return false;
				
				
    		}
    	}
    	catch(Exception e){
    		System.out.println(e);
    	}
    	return false;
    	
    }
    
    
    
    

    // ligne nbJoueur colonne Nb carte de 0 à 7 contenant le nombre de cartes en début de partie
    final int [] ruleNbCard = {6, 6, 6, 5, 5, 4, 4, 4};
    
    // constructeur
    public Moteur(int nbPlayer){
        arrayPlayer = new ArrayList<Player>();
        this.pile = new DeckGalleryAction();
        initArrayPlayer(nbPlayer);
        currentPlayer = 0;
        roleCards = new HandRole(nbPlayer());
        this.board = new Board();
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

    // passe au joueur suivant
    public void nextPlayer(){
        currentPlayer = (currentPlayer+1)%nbPlayer();
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

    // le joueur courant joue une carte sur le board
    public void play(GalleryCard c){
        if(c.getType() == Card.Card_t.gallery){
            // ajout dans sur le board
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
            } else if(c.getAction() == ActionCard.Action.Crumbing){

                // TODO methode crumbing
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
        if(c.getY() == 8 && (c.getX() == 0 || c.getX() == 2 || c.getX() == -2)){
            System.out.println("Taille mine: "+this.board.getMineSize());
            if(c.getX() == 2){ // B3
                return this.board.getMineElement(3).getCard();
            } else if(c.getX() == 0){ // B2
                return this.board.getMineElement(2).getCard();
            } else if(c.getX() == -2){ // B1
                return this.board.getMineElement(1).getCard();
            } else {
                return null;
            }
        } else {
            return null;
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
    
    

    public String toString(){
        String renvoi = "Moteur: \n";

        renvoi += "Joueur courant: "+this.getCurrentPlayer().getPlayerName() +"\n";
        renvoi += "Deck: "+this.pile.nbCard() +" cartes \n";
        renvoi += this.roleCards.print_without_visibility() + "\n";
        renvoi += "Joueurs [ ";
        for(int i=0; i<nbPlayer(); i++){
            renvoi += this.getPlayer(i).getPlayerName() + " ; ";
        }
        renvoi += "]\n";

        return renvoi;
    }




}
