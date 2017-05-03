/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saboteur;
import Cards.*;

/**
 *
 * @author uwalakae
 */
public class Joueur {
    String playerName;
    Card role;
    int goldPoints;
    Card [] playableCards; // les cartes données au debut du jeu
    Card [] pauseCards; // les cartes de pause devant le joueur (maximum de 3)
    Card [] treasureCardsChecked; // les cartes "trésor(s)" vues par ce joueur 

    public Joueur(String playerName, Card role, int goldPoints, Card[] startCards, Card[] checkedCards) {
        this.playerName = playerName;
        this.role = role;
        this.goldPoints = 0;
        
        int numberOfStartCards = startCards.length;
        this.playableCards = new Card [numberOfStartCards];
        for (int i=0; i<numberOfStartCards; i++) 
            this.playableCards[i] = startCards[i];
        
        pauseCards = new Card [3];
        treasureCardsChecked = new Card [3];
    }

    public String getPlayerName() {
        return playerName;
    }

    public Card getRole() {
        return role;
    }

    public int getGoldPoints() {
        return goldPoints;
    }

    public Card[] getPlayableCards() {
        return playableCards;
    }

    public Card[] getPauseCards() {
        return pauseCards;
    }

    public Card[] getTreasureCardsChecked() {
        return treasureCardsChecked;
    }
   
    public void changeACard(Card newCard, int indexToChange) {
        playableCards[indexToChange] = newCard;
        return;
    }
    
    
    
    
}
