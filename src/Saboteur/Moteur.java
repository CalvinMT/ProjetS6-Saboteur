/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Saboteur;
import Cards.*;

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
    //Board board;



    // ligne nbJoueur colonne Nb carte de 0 à 7 contenant le nombre de cartes en début de partie
    final int [] ruleNbCard = {6, 6, 6, 5, 5, 4, 4, 4};
    
    // constructeur
    public Moteur(int nbPlayer){
        arrayPlayer = new ArrayList<Player>();
        this.pile = new DeckGalleryAction();
        initArrayPlayer(nbPlayer);
        currentPlayer = 0;
        roleCards = new HandRole(nbPlayer());
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
            arrayPlayer.add(new Player(i+1));
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

    // affiche les infos joueurs en version texte
    public void promptPlayers(){
        for(int i=0; i<nbPlayer(); i++){
            System.out.println(arrayPlayer.get(i));
            System.out.println();
        }
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


    /// EMEZ
    /// A voir avec lui pour comprendre ce qu'il voulait faire


	// verifie que cardToPlay peut prolonger la gallerie en respectant les regles si mis adjacent 
	// à cardToAppendTo
	public boolean verifyAppendToGallery(Player player, Card cardToPlay, Card cardToAppendTo) {
		return false;   
    }
	
	// role.playableCards sera modifié du coup; la gallerie aussi
    public void appendToGallery(Player player, Card cardToPlay, Card cardToAppendTo) {
        // à implementer
    	if (isGameOver()) {
    		
    	}
    }
    
    
    // verification que cardToPlay peut detruire un cul de sac, puis que x & y sont des indices 
    // valides de la gallerie et que (x,y) contient une carte de type cul de sac
    public boolean verifyDestroyCulDeSac(Player player, Card cardToPlay, int x, int y) {
    	return false;
    }
    
    // enleve une carte de type cul de sac, modifie la gallerie, et 
    // modifie le tableau role.playableCards
    public void destroyCulDeSac(Player player, Card cardToPlay, int x, int y) {
        
    }
    
    //verifie le type de cardToPlay et que ça pourrait "soigner" pausedPlayer (si pausedPlayer a besoin)
    public boolean verifyUnpausePlayer(Player player, Card cardToPlay, Player pausedPlayer) {
    	return false;
    }
    
    // enleve une carte de pause correspondant au type de Carte c devant le Player j
    // pourrait l'utiliser pour lui meme
    // modifie le tableau role.playableCards et pausedPlayer.pauseCards
    public void unpausePlayer(Player player, Card cardToPlay, Player pausedPlayer) {
        
    }
    
    
    public boolean verifyPausePlayer(Player player, Card cardToPlay, Player targetedPlayer) {
    	return false;
    }
    
    public void pausePlayer(Player player, Card cardToPlay, Player targetedPlayer) {
        
    }
    
    // verifie que role possede une carte de type Map,
    // verifie (x,y), puis lui renvoie le type de la carte sur la case (x,y). 
    // role.playableCards sera modifié et aussi role.treasureCardsChecked
    public boolean verifyPeep(Player player, Card cardToPlay, Card possibleTreasure) {
    	return false;
    }
    
    public void peepCard(Player player, Card cardToPlay) {
        
    }
       
    public boolean isGameOver() {
    	return false;
    }
    
    // si les mineurs gagnent : 
    public void shareGoldAmongMiners(Player [] players){
        
    }
    
 // si les saboteurs gagnent : 
    public void shareGoldAmongSaboteurs(Player [] players){
        
    }


    

}
