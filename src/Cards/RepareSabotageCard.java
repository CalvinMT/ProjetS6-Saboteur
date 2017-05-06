package Cards;


import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;

public class RepareSabotageCard extends ActionCard{

    private ArrayList<Tools> arrayTools;

    public enum Tools {
        Pickaxe,
        Lantern,
        Wagon
    }



    public RepareSabotageCard(String type, Tools t){
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

    public boolean canBeRepareBy(RepareSabotageCard c){
        if(c.action == Action.Repare && this.action == Action.Sabotage){
            if(this.arrayTools.size() > 0){
                return c.containsTools(this.arrayTools.get(0));
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean containsTools(Tools t){
        return arrayTools.contains(t);
    }

    public int nbTools(){
        return arrayTools.size();
    }


}
