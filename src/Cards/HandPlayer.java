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

    // fait la rotation de la carte a la ième position
    public void rotateCard(int i){
        if(i >= 0 && i <this.nbCard()){
            Card c = arrayCard.get(i);
            if(c.getType() == Card.Card_t.gallery){
                arrayCard.set(i, ((GalleryCard) c).rotate());
            }
        }
    }

    public void addCard(Card c){
        if (c.getType() == Card.Card_t.gallery || c.getType() == Card.Card_t.action){
            this.arrayCard.add(c);
        } else {
            System.err.println("Erreur mauvaise carte");
        }
    }

    @Override
    public String toString(){
        String renvoi = "Hand : ";
        renvoi += "[ ";
        for(int i=0; i<this.arrayCard.size(); i++){
            if(this.arrayCard.get(i).getType() == Card.Card_t.gallery){

                renvoi += ((GalleryCard) this.arrayCard.get(i)).debugString();
            } else {
                renvoi += this.arrayCard.get(i).toString();
            }

            if(i<this.arrayCard.size()-1){
                renvoi += " ; ";
            }
        }

        renvoi += " ]";
        return renvoi;
    }

    public int nbCard(){
        return arrayCard.size();
    }

}
