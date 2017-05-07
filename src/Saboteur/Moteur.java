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
    // attributs (autres à ajouter par la suite)
    ArrayList<Player> arrayPlayer;
    
    // constructeur
    Moteur(int n){
        arrayPlayer = new ArrayList<Player>();
        initArrayPlayer(n);
    }


    // thespygeek
    private void initArrayPlayer(int n){
        int nbPlayer;

        if(n < 3 && n > 10){
            nbPlayer = 3;
        } else {
            nbPlayer = n;
        }

        for(int i=0; i<nbPlayer; i++){
            arrayPlayer.add(new Player(i+1));
        }
    }

    public int nbPlayer(){
        return arrayPlayer.size();
    }

    public void promptPlayers(){
        for(int i=0; i<nbPlayer(); i++){
            System.out.println(arrayPlayer.get(i));
            System.out.println();
        }
    }

    // renvoie le nombre max de cartes que les joueurs peuvent avoir en main
    public int maxHandCard(){
        return 6;
    }


    /// EMEZ


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
