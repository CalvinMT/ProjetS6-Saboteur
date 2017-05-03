package Cards;

public abstract class Card {
    private enum Type {action, player, gallery};
    private final Type type;
    private int x, y;

    Card(type t, int x, int y) {
        this.type = t;
        this.x = x;
        this.y = y;
    }

    public Type getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
