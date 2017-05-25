package Player;

import Board.Couple;
import Cards.Card;

public class Move {
    private Card card; // la carte à placer
    private int sabotageTarget; // index du joueur a saboter
    private Couple positionTarget; // Position où placer la carte (gallery/action)
    private int value;

    public Move() {
        this(new Card, 0, new Couple(0,0), 0);
    }

    public Move(Card card, Couple positionTarget, int value) {
        this(card, 0, positionTarget, value);
    }

    public Move(Card card, int sabotageTarget, int value) {
        this(card, sabotageTarget, new Couple(0,0), value);
    }

    public Move(Card card, int sabotageTarget, Couple positionTarget, int value) {
        this.card = card;
        this.sabotageTarget = sabotageTarget;
        this.positionTarget = positionTarget;
        this.value = value;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setSabotageTarget(int sabotageTarget) {
        this.sabotageTarget = sabotageTarget;
    }

    public void setPositionTarget(Couple positionTarget) {
        this.positionTarget = positionTarget;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Card getCard() {
        return card;
    }

    public int getSabotageTarget() {
        return sabotageTarget;
    }

    public Couple getPositionTarget() {
        return positionTarget;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Move{" +
            "card=" + card +
            ", sabotageTarget=" + sabotageTarget +
            ", positionTarget=" + positionTarget +
            ", value=" + value +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;

        Move move = (Move) o;

        if (getSabotageTarget() != move.getSabotageTarget()) return false;
        if (getValue() != move.getValue()) return false;
        if (!getCard().equals(move.getCard())) return false;
        return getPositionTarget() != null ? getPositionTarget().equals(move.getPositionTarget()) : move.getPositionTarget() == null;
    }

    @Override
    public int hashCode() {
        int result = getCard().hashCode();
        result = 31 * result + getSabotageTarget();
        result = 31 * result + (getPositionTarget() != null ? getPositionTarget().hashCode() : 0);
        result = 31 * result + getValue();
        return result;
    }
}
