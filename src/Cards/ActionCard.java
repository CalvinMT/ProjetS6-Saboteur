package Cards;


import static Cards.ActionCard.Action.*;

public class ActionCard extends Card {

    Action action;

    enum Action {
        Sabotage,
        Rescue,
        DoubleRescue,
        Map,
        Crumbing;

    }

    ActionCard(){
        this.action = Rescue;
    }

    ActionCard(String a){
        switch(a){
            case "Sabotage":
                this.action = Sabotage;
                break;
            case "Rescue":
                this.action = Rescue;
                break;
            case "DoubleRescue":
                this.action = DoubleRescue;
                break;
            case "Map":
                this.action = Map;
                break;
            case "Crumbing":
                this.action = Crumbing;
                break;
            default :
                this.action = Rescue;
                break;
        }
    }

    public String toString(){
        String renvoi = "Action: ";
        switch(this.action){
            case Sabotage:
                renvoi += "Sabotage";
                break;
            case Rescue:
                renvoi += "Rescue";
                break;
            case DoubleRescue:
                renvoi += "DoubleRescue";
                break;
            case Map:
                renvoi += "Map";
                break;
            case Crumbing:
                renvoi += "Crumbing";
                break;
            default :
                renvoi += "Error";
                break;
        }
        return renvoi;
    }



}
