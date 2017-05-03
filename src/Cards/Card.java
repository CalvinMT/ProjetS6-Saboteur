package Cards;

public class Card {
    public enum Card_t {action, player, gallery};
    private final Card_t cardt;

    Card() {
        this.cardt = Card_t.player;
    }
    Card(Card_t t) {
        this.cardt = t;
    }

    public Card_t getType() {
        return cardt;
    }
}
