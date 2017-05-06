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
public class Player {

    private String playerName;
    private Card role;
    private int goldPoints;
    private HandPlayer playableCards; // les cartes données au debut du jeu
    Card [] pauseCards; // les cartes de pause devant le joueur (maximum de 3)

    public Player(String playerName) {
        this.playerName = playerName;
        this.goldPoints = 0;      
        pauseCards = new Card [3];
        this.playableCards = new HandPlayer();
    }

    public Player() {
        this.playerName = "Joueur";
        this.goldPoints = 0;
        pauseCards = new Card [3];
        this.playableCards = new HandPlayer();
    }

    // assignation des rôles
    public void assignRole(Card c){
        if(c.getType() == Card.Card_t.role){
            this.role = c;
        } else {
            System.err.println("Fail to assign Role");
        }
    }

    // le joueur pioche
    public void drawCard(Deck d) {
        if(playableCards.nbCard() < 6){
            playableCards.addCard(d.getTopDeck());
        }
    }

    // le joueur se défausse
    public void discard(int i){
        if(i >= 0 && i < playableCards.nbCard() && playableCards.nbCard() > 0){
            playableCards.discard(i);
        }
    }

    // regarder une carte de son jeu
    public Card lookAtCard(int i){
        if(i >= 0 && i < playableCards.nbCard() && playableCards.nbCard() > 0){
            return playableCards.chooseOne_without_remove(i);
        } else {
            System.out.println("This card doesn't exist");
            return null;
        }
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

    public Hand getPlayableCards() {
        return playableCards;
    }

    public Card[] getPauseCards() {
        return pauseCards;
    }

    public int nbCardHand(){
        return this.playableCards.nbCard();
    }

    
    
    
    
}
