package Cards;

public abstract class Card {
    public enum Card_t {action, role, gallery};
    protected Card_t type;

    Card() {
        this.type = Card_t.role;
    }
    public Card(Card_t t) {
        this.type = t;
    }

    public Card_t getType() {
        return type;
    }

    public boolean equals(Card c) {
        return this.getType() == c.getType();
    }

    public boolean canBeRepareBy(RepareSabotageCard c){
        return false;
    }
    public boolean containsTools(RepareSabotageCard.Tools t){
        return false;
    }
    
    public abstract int getGold();
    
    public int getconfig(){
    	return -1;
    }
}
