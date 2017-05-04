package Cards;

import java.util.ArrayList;

public class Deck {

    ArrayList<Card> arrayCard;


    public void shuffle(){
        // a faire
    }

    public Card getTopDeck(){
        if(this.arrayCard.size() > 0){
            return this.arrayCard.remove(0);
        } else {
            return null;
        }
    }

    public String toString(){
        String renvoi = "";
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
