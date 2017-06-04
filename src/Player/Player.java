package Player;


import Board.Board;
import Cards.*;

public abstract class Player {

    public enum Difficulty {
        Player,
        Easy,
        Medium,
        Hard;

        static public Difficulty stringToDiff(String diff){
            switch(diff){
                case "Player":
                    return Player;
                case "Easy":
                case "Facile":
                    return Easy;
                case "Medium":
                case "Moyen":
                    return Medium;
                case "Hard":
                case "Difficile":
                    return Hard;
                default:
                    return Player;
            }
        }
    }

    protected String playerName;
    protected int num; // pour le retrouver
    protected Difficulty difficulty;
    protected Card role;
    protected int goldPoints;
    protected HandPlayer playableCards; // les cartes données au debut du jeu
    protected PlayerAttribute attributeCards; // les cartes d'attribut devant le joueur (maximum de 3)
    protected Board board; // plateau de jeu
    protected String avatar;


    // assignation des rôles
    public void assignRole(Card c){
        if(c.getType() == Card.Card_t.role){
            this.role = c;
        } else {
            System.err.println("Fail to assign Role");
        }
    }

    // reset playerAttribute et HandCard
    public void resetPlayer(){
        this.playableCards = new HandPlayer();
        this.attributeCards = new PlayerAttribute();
        this.role = null;
    }

    // le joueur pioche
    public Card drawCard(Deck d) {
        if(playableCards.nbCard() < 6 && !d.isEmpty()){
            Card c = d.getTopDeck();
            playableCards.addCard(c);
            return c;
        } else {
            return null;
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

    // choix des cartes roles
    public boolean chooseRoleCard(HandRole cards){
        return false;
    }

    // assigne un nouveau pseudo
    public void setPlayerName(String name){
        this.playerName = name;
    }

    // si le joueur n'a aucune attribut de sabotage sur lui
    public boolean canPlayGalleryCard(){
        return this.attributeCards.nbCard() == 0;
    }

    // assigne un avatar
    public void setAvatar(String jpg){
        this.avatar = jpg;
    }

    //assign une difficulté
    public void setDifficulty(Difficulty d){
        this.difficulty = d;
    }

    //assigne un numéro
    public void setNum(int i){
        this.num = i;
    }

    // lui assigne un tableau de jeu
    public void setBoard(Board b){
        this.board = b;
    }

    // ajout d'une carte Repare
    public void setRepare(RepareSabotageCard c, RepareSabotageCard.Tools t){
        this.attributeCards.putRepare(c, t);
    }

    // ajout d'une carte Repare
    public RepareSabotageCard.Tools setRepare(RepareSabotageCard c){
        return this.attributeCards.removeAttribute(c);
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
            this.attributeCards.removeAttribute(c);
        }
    }

    // uniquement pour les tests
    public void removeAttribute(int n){
        this.attributeCards.removeAttribute(n);
    }

    // alternance IA Joueur


    public int waitingTime() {
        return -1;
    }

    // Méthode appelée une fois le temps écoulé
    public boolean pastTime() {
        return false;
    }

    // clic sur le plateau
    public boolean play(){
        return false;
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

    public int getNum(){
        return this.num;
    }

    public String getAvatar(){
        return this.avatar;
    }

    public Difficulty getDifficulty(){
        return this.difficulty;
    }

    public PlayerAttribute getAttributeCards() {
        return attributeCards;
    }

    public int nbCardHand(){
        return this.playableCards.nbCard();
    }
    
    public abstract void setGoldPoints(int gp);

    public void setEmptyRole(){
        role = null;
    }

    public boolean iaPlayCard() {
        return false;
    }

    public String toString(){

        String renvoi = "";

        renvoi += "Pseudo: "+this.playerName + "\n";
        renvoi += "Difficulté: "+this.difficulty+"\n";
        if(this.role == null){
            renvoi += "Aucun role pour l'instant\n";
        } else {
            renvoi += "Role: "+ this.role + "\n";
        }
        renvoi += "Point Or: "+this.goldPoints + "\n";
        renvoi += "Cartes attributs: "+this.attributeCards + "\n";
        renvoi += "Cartes en main: "+this.playableCards + "\n";

        return renvoi;
    }


    public String toFile(){

        String renvoi = "";

        renvoi += this.playerName + "\n";
        renvoi += this.difficulty+"\n";
        if(this.role == null){
            renvoi += "Aucun role pour l'instant\n";
        } else {
            renvoi += this.role + "\n";
        }
        renvoi += this.goldPoints + "\n";
        renvoi += this.attributeCards + "\n";
        renvoi += this.playableCards + "\n";

        return renvoi;
    }

}
