package Cards;

public abstract class Card {
    public enum Type {action, player, gallery};
    private final Type type;

    Card() {
        this.type = Type.player;
    }
    Card(Type t) {
        this.type = t;
    }

    public Type getType() {
        return type;
    }
}
