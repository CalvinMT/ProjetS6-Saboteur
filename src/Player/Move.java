package Player;

import Board.Couple;
import Cards.Card;
import Cards.GalleryCard;


// TODO : ajout boolean defausse

public class Move {
    private Card card; // la carte à placer
    private int targetIdx; // index du joueur a saboter
    private Couple positionTarget; // Position où placer la carte (gallery/action)
    private int value;
    private boolean isDiscard;

    public Move() {
        this(new GalleryCard(), -1, new Couple(0,0), 0, false);
    }

    // Défausse
    public Move(Card card, boolean isDiscard) {
        this(card, -1, new Couple(0, 0), 0, isDiscard);
    }

    // carte gallery / crumbling
    public Move(Card card, Couple positionTarget) {
        this(card, -1, positionTarget, 0, false);
    }

    // Carte sabotage / repare / map
    public Move(Card card, int targetIdx) {
        this(card, targetIdx, new Couple(0, 0), 0, false);
    }

    // DO NOT USE
    public Move(Card card, Couple positionTarget, int value) {
        this(card, 0, positionTarget, value, false);
    }

    // DO NOT USE
    public Move(Card card, int targetIdx, int value) {
        this(card, targetIdx, new Couple(0,0), value, false);
    }

    // Default
    public Move(Card card, int targetIdx, Couple positionTarget, int value, boolean isDiscard) {
        this.card = card;
        this.targetIdx = targetIdx;
        this.positionTarget = positionTarget;
        this.value = value;
        this.isDiscard = isDiscard;
        System.out.println(this);
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setTargetIdx(int targetIdx) {
        this.targetIdx = targetIdx;
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

    public int getTargetIdx() {
        return targetIdx;
    }

    public Couple getPositionTarget() {
        return positionTarget;
    }

    public int getValue() {
        return value;
    }

    public boolean getDiscard(){
        return this.isDiscard;
    }

    @Override
    public String toString() {
        return "Move{" +
            "card=" + card +
            ", targetIdx=" + targetIdx +
            ", positionTarget=" + positionTarget +
            ", value=" + value +
            ", isDiscard=" + isDiscard +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;

        Move move = (Move) o;

        if (getTargetIdx() != move.getTargetIdx()) return false;
        if (getValue() != move.getValue()) return false;
        if (!getCard().equals(move.getCard())) return false;
        return getPositionTarget() != null ? getPositionTarget().equals(move.getPositionTarget()) : move.getPositionTarget() == null;
    }

    @Override
    public int hashCode() {
        int result = getCard().hashCode();
        result = 31 * result + getTargetIdx();
        result = 31 * result + (getPositionTarget() != null ? getPositionTarget().hashCode() : 0);
        result = 31 * result + getValue();
        return result;
    }
}
