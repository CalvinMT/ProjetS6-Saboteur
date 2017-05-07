package Cards;


import java.util.ArrayList;

public class PlayerAttribute extends Hand {

    private final int nbAttribute = 3;

    public PlayerAttribute(){
        arrayCard = new ArrayList<Card>();
        this.visible = true;
    }

    // se défausser
    public void removeAttribute(int n){
        if(n >= 0 && n < nbCard()){
            this.arrayCard.remove(n);
        }
    }

    public void addAttribute(Card c){
        if (c.getType() == Card.Card_t.action) {
            if (this.arrayCard.size() < this.nbAttribute){
                this.arrayCard.add(c);
            } else {
                System.err.println("Can't add more");
            }
        } else {
            System.err.println("Erreur mauvaise carte");
        }
    }

    @Override
    public String toString(){
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
}
