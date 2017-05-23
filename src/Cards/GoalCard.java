package Cards;

import Board.Couple;

public class GoalCard extends GalleryCard {
    private boolean gold = false;

    public GoalCard() {
        super(Gallery_t.but, 0, 8, true, true, true, true, true);
    }

    public GoalCard(Couple c) {
        super(Gallery_t.but, c.getX(), c.getY(), true, true, true, true, true);
        this.gold = true;
    }

    public GoalCard(Couple c, boolean n, boolean s, boolean e, boolean w, boolean g) {
        super(Gallery_t.but, c.getX(), c.getY(), true, n, s, e, w);
        this.gold = g;
    }
    
    
    @Override
    public int getConfig() {
        int i = 0;
        if (this.gold) i = 1;

        return (i * 100000) + super.getConfig();
    }

    @Override
    public boolean isGold() {
        return this.gold;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoalCard)) return false;
        if (!super.equals(o)) return false;

        GoalCard goalCard = (GoalCard) o;

        return isGold() == goalCard.isGold();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (isGold() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GoalCard:" +gold +
                ":" + super.toString();
    }
}
