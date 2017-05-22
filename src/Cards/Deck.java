package Cards;

import java.util.ArrayList;
import java.util.Random;


public class Deck {

    protected ArrayList<Card> arrayCard;

    /// Mélanger le paquet
    public void shuffle(){

        Card a, b;
        int j, n = this.nbCard();

        Random rand = new Random();

        for(int i=0; i<n; i++){
            j = rand.nextInt(n);

            a = this.arrayCard.get(i);
            b = this.arrayCard.get(j);

            this.arrayCard.set(j, a);
            this.arrayCard.set(i, b);
        }

    }

    // renvoie la carte en haut du paquet
    public Card getTopDeck(){
        if(!this.isEmpty()){
            return this.arrayCard.remove(0);
        } else {
            System.err.println("");
            return null;
        }
    }

    // renvoie le nombre de cartes dans le paquet
    public int nbCard(){
        return this.arrayCard.size();
    }

    // si le paquet est vide
    public boolean isEmpty(){
        return (this.nbCard() <= 0);
    }

    public String toString(){
        String renvoi = "Deck : ";
        renvoi += "[ ";
        for(int i=0; i<this.arrayCard.size(); i++){
            renvoi += this.arrayCard.get(i).toString();

            if(i<this.arrayCard.size()-1){
                renvoi += " ; ";
            }
        }
        renvoi += " ]";
        return renvoi;
    }


}
