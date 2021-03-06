package Cards;

import Board.Couple;

public class GoalCard extends GalleryCard {
    private boolean gold = false;

    private boolean visible = false;

    public GoalCard() {
        super(Gallery_t.but, 0, 8, true, true, true, true, true);
    }

    public GoalCard(Couple c) {
        super(Gallery_t.but, c.getLine(), c.getColumn(), true, true, true, true, true);
        this.gold = true;
    }

    public GoalCard(Couple c, boolean n, boolean s, boolean e, boolean w, boolean g) {
        super(Gallery_t.but, c.getLine(), c.getColumn(), true, n, s, e, w);
        this.gold = g;
    }

    @Override
    public boolean isGoal(){
        return true;
    }



    @Override
    public int getConfig() {
        if (this.gold) {
            return 0b100000 + super.getConfig();
        }
        else {
            return super.getConfig();
        }
    }

    @Override
    public boolean isGold() {
        return this.gold;
    }

    @Override
    public GoalCard rotate(){
        boolean tmp;

        tmp = this.north;
        this.north = this.south;
        this.south = tmp;

        tmp = this.east;
        this.east = this.west;
        this.west = tmp;

        return this;
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
        return "GoalCard{" +
                "gold=" + gold +
                "} " + super.toString();
    }

    public void setVisible(boolean b){
        this.visible = b;
    }

    public boolean isVisible(){
        return this.visible;
    }
}
