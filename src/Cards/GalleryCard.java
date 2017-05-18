package Cards;

import Board.Couple;

import static Cards.GalleryCard.Gallery_t.start;

public class GalleryCard extends Card {
    public enum Gallery_t {start, but, tunnel};
    private Gallery_t type_g;
    private Couple coord = new Couple();

    private boolean center = true,
                    north = true,
                    south = true,
                    east = true,
                    west = true;

    public GalleryCard() { // Init start card
        this.type = Card_t.gallery;
        this.type_g = start;
    }

    public GalleryCard(Gallery_t t, boolean c, boolean n, boolean s, boolean e, boolean w) {
        this.type = Card_t.gallery;
        this.type_g = t;
        this.center = c;
        this.north = n;
        this.south = s;
        this.east = e;
        this.west = w;
    }

    public GalleryCard(boolean c, boolean n, boolean s, boolean e, boolean w){
        this.type = Card_t.gallery;
        this.type_g = Gallery_t.tunnel;
        this.center = c;
        this.north = n;
        this.south = s;
        this.east = e;
        this.west = w;
    }

    // Pour debug
    public GalleryCard(Gallery_t t, int x, int y, boolean c, boolean n, boolean s, boolean e, boolean w) {
        this.type = Card_t.gallery;
        this.type_g = t;
        this.coord.setX(x);
        this.coord.setY(y);
        this.center = c;
        this.north = n;
        this.south = s;
        this.east = e;
        this.west = w;
    }

    public int getX() {
        return coord.getX();
    }

    public int getY() {
        return coord.getY();
    }

    public void setX(int x) {
        this.coord.setX(x);
    }

    public void setY(int y) {
        this.coord.setY(y);
    }

    public Gallery_t getGalleryType() {
        return type_g;
    }


    public boolean canHasNorth() {
        return this.north;
    }

    public boolean canHasSouth() {
        return this.south;
    }

    public boolean canHasEast() {
        return this.east;
    }

    public boolean canHasWest() {
        return this.west;
    }

    public boolean canHasCenter() {
        return this.center;
    }

    public void setCenter(boolean center) {
        this.center = center;
    }

    public void setNorth(boolean north) {
        this.north = north;
    }

    public void setSouth(boolean south) {
        this.south = south;
    }

    public void setEast(boolean east) {
        this.east = east;
    }

    public void setWest(boolean west) {
        this.west = west;
    }

    public void setType_g(Gallery_t type_g) {
        this.type_g = type_g;
    }

    public boolean equals(GalleryCard c) {
        return  ((this.getGalleryType() == c.getGalleryType()) &&
                (this.getX() == c.getX()) &&
                (this.getY() == c.getY()) &&
                (this.canHasCenter() == c.canHasCenter()) &&
                (this.canHasNorth() == c.canHasNorth()) &&
                (this.canHasSouth() == c.canHasSouth()) &&
                (this.canHasEast() == c.canHasEast()) &&
                (this.canHasWest() == c.canHasWest()));
    }

    public GalleryCard rotate() {
        return new GalleryCard(this.getGalleryType(), this.getX(), this.getY(), this.canHasCenter(), this.canHasSouth(), this.canHasNorth(), this.canHasWest(), this.canHasEast());
    }

    public int getConfig() {
        int res = 0;

        if (this.center) {
            res += 0b10000;
        }
        if (this.north) {
            res += 0b1000;
        }
        if (this.south) {
            res += 0b100;
        }
        if (this.east) {
            res += 0b10;
        }
        if (this.west) {
            res += 0b1;
        }
        return res;
    }

    public boolean isGold() {
        GoalCard card = (GoalCard) this;
        return card.isGold();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GalleryCard)) return false;

        GalleryCard card = (GalleryCard) o;

        if (getX() != card.getX()) return false;
        if (getY() != card.getY()) return false;
        if (center != card.center) return false;
        if (north != card.north) return false;
        if (south != card.south) return false;
        if (east != card.east) return false;
        if (west != card.west) return false;
        return getType() == card.getType();
    }

    @Override
    public int hashCode() {
        int result = getType().hashCode();
        result = 31 * result + getX();
        result = 31 * result + getY();
        result = 31 * result + (center ? 1 : 0);
        result = 31 * result + (north ? 1 : 0);
        result = 31 * result + (south ? 1 : 0);
        result = 31 * result + (east ? 1 : 0);
        result = 31 * result + (west ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        if (type_g == start) return "Start";
        return "GalleryCard{" +
                "type=" + type +
                ", x=" + coord.getX() +
                ", y=" + coord.getY() +
                ", center=" + center +
                ", north=" + north +
                ", south=" + south +
                ", east=" + east +
                ", west=" + west +
                '}';
    }
    
    @Override
	public int getGold(){
		return 0;
	}
}
