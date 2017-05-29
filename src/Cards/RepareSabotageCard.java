package Cards;



import java.util.ArrayList;

public class RepareSabotageCard extends ActionCard{

    private ArrayList<Tools> arrayTools;

    public enum Tools {
        Pickaxe,
        Lantern,
        Wagon;
    }



    public RepareSabotageCard(String type, Tools t){
        switch(type){
            case "Repare":
                this.type = Card.Card_t.action;
                this.action = Action.Repare;
                break;
            case "Sabotage":
                this.type = Card.Card_t.action;
                this.action = Action.Sabotage;
                break;
            default:
                this.type = Card.Card_t.action;
                this.action = Action.Map;
                break;
        }

        if(type == "Repare" || type == "Sabotage") {
            arrayTools = new ArrayList<Tools>();
            arrayTools.add(t);
        }
    }

    public RepareSabotageCard(String type, Tools t1, Tools t2){
        switch(type){
            case "Repare":
                this.action = Action.Repare;
                break;
            case "Sabotage":
                this.action = Action.Sabotage;
                break;
            default:
                this.action = Action.Map;
                break;
        }
        if(type == "Repare" || type == "Sabotage") {
            arrayTools = new ArrayList<Tools>();
            arrayTools.add(t1);
            if(t1 != t2 && type == "Repare"){
                arrayTools.add(t2);
            }
        }
    }

    // la carte courante (this) peut être réparé par celle donnée en argument
    @Override
    public boolean canBeRepareBy(RepareSabotageCard c){
        if(c.action == Action.Repare && this.action == Action.Sabotage){

            for(int i=0; i<arrayTools.size(); i++){
                if( c.containsTools(this.arrayTools.get(i))){
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean containsTools(Tools t){
        return arrayTools.contains(t);
    }

    public int nbTools(){
        return arrayTools.size();
    }

    public Tools getTool(){
        return this.arrayTools.get(0);
    }

    public ArrayList<Tools> getAlltools(){
        return this.arrayTools;
    }

    @Override
    public String toString(){
        String renvoi = "Action:";

        if(this.action == Action.Sabotage){
            renvoi += "Sabotage{";
            if(this.nbTools() > 0){
                renvoi += this.arrayTools.get(0);
                renvoi += "}";
            } else {
                renvoi += "Error Tools}";
            }
        } else if(this.action == Action.Repare){
            renvoi += "Repare{";
            if(this.nbTools() == 1){
                renvoi += this.arrayTools.get(0);
                renvoi += "}";
            } else if(this.nbTools() == 2){
                renvoi += this.arrayTools.get(0) + ",";
                renvoi += this.arrayTools.get(1);
                renvoi += "}";
            } else {
                renvoi += "Error Tools}";
            }
        } else {
            renvoi += "action type Error";
        }


        return renvoi;
    }


}
