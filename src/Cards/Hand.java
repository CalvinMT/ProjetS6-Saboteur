package Cards;


/// Carte que le joueur a dans la main


import java.util.ArrayList;

public class Hand extends Deck {

    protected boolean visible = false;

    // si la main est visible
    public boolean visible(){
        return this.visible;
    }

    // on renvoi juste la carte
    public Card chooseOne_without_remove(int i){
        if(i > 0 && i < this.nbCard()) {
            return this.arrayCard.get(i);
        } else {
            return this.arrayCard.get(0);
        }
    }

    // on prend une carte et on l'enlève
    public Card chooseOne_with_remove(int i){
        if(i > 0 && i < this.nbCard()) {
            return this.arrayCard.remove(i);
        } else {
            return this.arrayCard.remove(0);
        }
    }

    public void removeCard(Card c){
        this.arrayCard.remove(c);
    }


    public void setVisibility(boolean b){
        this.visible = b;
    }

    @Override
    public String toString(){
        //String renvoi = "Hand : ";
        //renvoi += "[ ";
    	String renvoi = "";
        if(this.visible()){
            for(int i=0; i<this.arrayCard.size(); i++){
                renvoi += this.arrayCard.get(i).toString();

                if(i<this.arrayCard.size()-1){
                    renvoi += ";";
                }
            }
        } else {
            for(int i=0; i<this.arrayCard.size(); i++){
                renvoi += " X ";

                if(i<this.arrayCard.size()-1){
                    renvoi += " ; ";
                }
            }
        }
        //renvoi += " ]";
        return renvoi;
    }

    public String print_without_visibility(){
        String renvoi = "Hand : ";
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


    // Tests
    public ArrayList<Card> getArrayCard() {
        return this.arrayCard;
    }

}
