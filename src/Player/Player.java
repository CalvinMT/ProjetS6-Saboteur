package Player;


import Cards.*;
import com.sun.org.apache.regexp.internal.RE;
import Board.Board;
import Board.Couple;

import javax.tools.Tool;

public abstract class Player {

    public enum Difficulty {
        Easy,
        Medium,
        Hard
    }

    protected String playerName;
    protected Card role;
    protected int goldPoints;
    protected HandPlayer playableCards; // les cartes données au debut du jeu
    protected PlayerAttribute attributeCards; // les cartes d'attribut devant le joueur (maximum de 3)
    protected Board board; // plateau de jeu


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
        if(playableCards.nbCard() < 6 && !d.isEmpty()){
            playableCards.addCard(d.getTopDeck());
        }
    }

    // le joueur ajoute une carte donnée à sa main
    public void drawCard(Card c) {
        if(playableCards.nbCard() < 6 && (c.getType() == Card.Card_t.action || c.getType() == Card.Card_t.gallery)){
            playableCards.addCard(c);
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

    public void setBoard(Board b){
        this.board = b;
    }

    // ajout d'une carte Repare
    public void setRepare(RepareSabotageCard c, RepareSabotageCard.Tools t){
        this.attributeCards.putRepare(c, t);
    }

    // ajout d'une carte Sabotage
    public void setSabotage(RepareSabotageCard c){
        this.attributeCards.putSabotage(c);
    }

    // met une carte malus a au joueur p
    public void putRepare(RepareSabotageCard c, RepareSabotageCard.Tools t, Player p){
        p.setRepare(c, t);
    }

    // met une carte malus a au joueur p
    public void putSabotage(RepareSabotageCard c, Player p){
        p.setSabotage(c);
    }

    public void removeAttribute(RepareSabotageCard c, RepareSabotageCard.Tools t){
        if(c.containsTools(t)){
            this.attributeCards.removeAttribute(c, t);
        }
    }

    // uniquement pour les tests
    public void removeAttribute(int n){
        this.attributeCards.removeAttribute(n);
    }


    //// GETTEUR
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

    public PlayerAttribute getAttributeCards() {
        return attributeCards;
    }

    public int nbCardHand(){
        return this.playableCards.nbCard();
    }

    public abstract String toString();
    public abstract void changeDiffulty(Difficulty d);

}