package Cards;

public class Card {
    public enum Card_t {action, player, gallery};
    protected Card_t type;

    Card() {
        this.type = Card_t.player;
    }
    Card(Card_t t) {
        this.type = t;
    }

    public Card_t getType() {
        return type;
    }

    public boolean equals(Card c) {
        return this.getType() == c.getType();
    }
}
