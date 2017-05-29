package Cards;


import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.List;

import static Cards.ActionCard.Action.*;

public class ActionCard extends Card {

    protected Action action;

    public enum Action {
        Sabotage,
        Repare,
        Map,
        Crumbling;

        private static final List<Action> val = Collections.unmodifiableList(Arrays.asList(values()));
        private static final int size = val.size();


        static public Action random_Action(){
            Random rand = new Random();
            return val.get(rand.nextInt(size));
        }
    }

    public ActionCard(){
        this.action = Action.random_Action();
        this.type = Card.Card_t.action;
    }

    public ActionCard(String a){
        this.type = Card.Card_t.action;
        switch(a){
            case "Map":
                this.action = Map;
                break;
            case "Crumbling":
                this.action = Crumbling;
                break;
            default :
                this.action = Map;
                break;
        }
    }

    public String toString(){
        String renvoi = "Action:";
        switch(this.action){
            case Map:
                renvoi += "Map";
                break;
            case Crumbling:
                renvoi += "Crumbling";
                break;
            default :
                renvoi += "Error";
                break;
        }
        return renvoi;
    }

    public Action getAction(){
        return this.action;
    }
    
    @Override
	public int getGold(){
		return 0;
	}


}
