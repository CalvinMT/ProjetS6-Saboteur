/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Saboteur;
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
    private PlayerAttribute attributeCards; // les cartes d'attribut devant le joueur (maximum de 3)

    public Player(String playerName) {
        this.playerName = playerName;
        this.goldPoints = 0;      
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
    }

    public Player() {
        this.playerName = "Joueur";
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
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

    // ajout d'une carte attribut
    public void setAttributeCards(Card c){
        attributeCards.addAttribute(c);
    }

    // met une carte malus a au joueur p
    public void putAttribute(Card c, Player p){
        if(!p.equals(this)){
            p.setAttributeCards(c);
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

    public PlayerAttribute getPauseCards() {
        return attributeCards;
    }

    public int nbCardHand(){
        return this.playableCards.nbCard();
    }

    
    
    
    
}
