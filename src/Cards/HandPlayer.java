package Cards;

// cartes que le joueur a en main


import java.util.ArrayList;

public class HandPlayer extends Hand {

    public HandPlayer(){
        this.visible = true;
        this.arrayCard = new ArrayList<Card>();
    }

    public HandPlayer(int n){
        this.visible = true;
        this.arrayCard = new ArrayList<Card>();
        if(n > 0){
            for(int i=0; i<n; i++){
                arrayCard.add(new RoleCard("Mineur"));
            }
        }
    }

    // se défausser
    public void discard(int n){
        if(n >= 0 && n < nbCard()){
            this.arrayCard.remove(n);
        }
    }

    public void addCard(Card c){
        if (c.getType() == Card.Card_t.gallery || c.getType() == Card.Card_t.action){
            this.arrayCard.add(c);
        } else {
            System.err.println("Erreur mauvaise carte");
        }
    }

    public int nbCard(){
        return arrayCard.size();
    }

}
