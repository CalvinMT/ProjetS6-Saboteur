package Cards;


import java.util.ArrayList;

public class PlayerAttribute extends Hand {

    private final int nbAttribute = 3;

    public PlayerAttribute(){
        arrayCard = new ArrayList<Card>();
        this.visible = true;
    }


    // enlever un attribut avec une carte repare
    public void removeAttribute(int i){
        if(i >= 0 && i < this.nbCard()){
            this.arrayCard.remove(i);
        }
    }

    public boolean containsTools(RepareSabotageCard.Tools t){
        for(int i=0; i<nbCard(); i++){
            if(arrayCard.get(i).containsTools(t)){
                return true;
            }
        }
        return false;
    }

    // enleve un carte de sabotage
    public void removeAttribute(RepareSabotageCard c, RepareSabotageCard.Tools t){

        if(c.getType() == Card.Card_t.action && c.getAction() == ActionCard.Action.Repare && c.nbTools() <= 2 && c.nbTools() > 0){
            RepareSabotageCard card = new RepareSabotageCard("Repare", t);
            int nbRepare = 1;
            for(int i=0; i<getNbAttribute() && nbRepare > 0; i++){
                if(arrayCard.get(i).canBeRepareBy(card)){
                    nbRepare--;
                    arrayCard.remove(i);
                }
            }
        }
    }

    /// ajoute le sabotage au joueur
    public void putSabotage(RepareSabotageCard c){
        if (c.getType() == Card.Card_t.action){
            if(c.getAction() == ActionCard.Action.Sabotage && !this.containsTools(c.getTool())) {
                if (this.arrayCard.size() < this.nbAttribute) {
                    this.arrayCard.add(c);
                }
            }
        }
    }

    // repare un outil cassé
    public void putRepare(RepareSabotageCard c, RepareSabotageCard.Tools t){
        if (c.getType() == Card.Card_t.action){
            if(c.getAction() == ActionCard.Action.Repare && t != null && c.containsTools(t)){
                removeAttribute(c, t);
            }
        }
    }

    // ajoute un attribut
    public void doActionCard(RepareSabotageCard c, RepareSabotageCard.Tools t){
        if (c.getType() == Card.Card_t.action){
            if(c.getAction() == ActionCard.Action.Sabotage && !this.containsTools(c.getTool())) {
                if (this.arrayCard.size() < this.nbAttribute) {
                    this.arrayCard.add(c);
                }
            } else if(c.getAction() == ActionCard.Action.Repare && t != null && c.containsTools(t)){
                removeAttribute(c, t);
            }
        } else {
            System.err.println("Erreur mauvaise carte");
        }
    }

    public int getNbAttribute(){
        return arrayCard.size();
    }

    @Override
    public String toString(){
        String renvoi = "Attribute : ";
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
