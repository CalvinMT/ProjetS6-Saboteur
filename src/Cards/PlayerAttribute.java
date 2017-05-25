package Cards;


import Saboteur.Saboteur;
import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;

public class PlayerAttribute extends Hand {

    private final int nbAttribute = 3;

    public PlayerAttribute(){
        arrayCard = new ArrayList<Card>();
        this.visible = true;
    }



    public boolean containsTools(RepareSabotageCard.Tools t){
        for(int i=0; i<nbCard(); i++){
            if(arrayCard.get(i).containsTools(t)){
                return true;
            }
        }
        return false;
    }

    // si on peut casser l'outil du joueur
    public boolean canBreakTool(RepareSabotageCard t){
        boolean breakTool = true;
        if(t.getAction() == ActionCard.Action.Sabotage){
            for(int i=0; i<this.nbCard(); i++){
                if(containsTools(t.getTool())){
                    breakTool = false;
                }
            }
            return breakTool;
        } else {
            return false;
        }
    }

    // enlever un attribut avec une carte repare
    public void removeAttribute(int i){
        if(i >= 0 && i < this.nbCard()){
            this.arrayCard.remove(i);
        }
    }


    // enleve un carte de sabotage
    public RepareSabotageCard.Tools removeAttribute(RepareSabotageCard c){

        if(c.getType() == Card.Card_t.action && c.getAction() == ActionCard.Action.Repare && c.nbTools() <= 2 && c.nbTools() > 0){

            RepareSabotageCard.Tools re = null;
            ArrayList<RepareSabotageCard.Tools> tools = c.getAlltools();

            int nbRepare = 1;

            for(int j=0; j<tools.size(); j++){

                RepareSabotageCard card = new RepareSabotageCard("Repare", tools.get(j));
                for(int i=0; i<getNbAttribute() && nbRepare > 0; i++){
                    if(arrayCard.get(i).canBeRepareBy(card)){
                        nbRepare--;
                        re = ((RepareSabotageCard)arrayCard.get(i)).getTool();
                        arrayCard.remove(i);
                    }
                }
            }

            return re;
        } else {
            return null;
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
                removeAttribute(c);
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
                removeAttribute(c);
            }
        } else {
            System.err.println("Erreur mauvaise carte");
        }
    }

    // si une carte peut réparer des outils présents dans le playerAttribute
    public boolean canRepareTool(RepareSabotageCard card){

        if(card.getAction() == ActionCard.Action.Repare){

            for(int i=0; i<this.nbCard(); i++){
                if(this.arrayCard.get(i).canBeRepareBy(card)){
                    return true;
                }
            }
            return false;
        } else {
            System.err.println("[PlayerAttribute] Ceci n'est pas une carte Repare");
            return false;
        }

    }

    public int getNbAttribute(){
        return arrayCard.size();
    }

    @Override
    public String toString(){
        String renvoi = "";
        //renvoi += "{";
        for(int i=0; i<this.arrayCard.size(); i++){
            renvoi += this.arrayCard.get(i).toString();
            if(i<this.arrayCard.size()-1){
                renvoi += ";";
            }
        }

        //renvoi += "}";
        return renvoi;
    }
}
