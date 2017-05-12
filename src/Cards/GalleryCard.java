package Cards;

import static Cards.GalleryCard.Gallery_t.but;
import static Cards.GalleryCard.Gallery_t.start;

public class GalleryCard extends Card {
    public enum Gallery_t {start, but, tunnel};
    private Gallery_t type_g;
    private int x = 0,
                y = 0;

    private boolean center = true,
                    north = true,
                    south = true,
                    east = true,
                    west = true;
    private boolean gold = false;

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

    public GalleryCard(Gallery_t t, boolean g, boolean c, boolean n, boolean s, boolean e, boolean w){
        this.type = Card_t.gallery;
        this.type_g = t;
        this.gold = g;
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
    public GalleryCard(Gallery_t t, int x, int y, boolean g, boolean c, boolean n, boolean s, boolean e, boolean w) {
        this.type = Card_t.gallery;
        this.type_g = t;
        this.x = x;
        this.y = y;
        this.gold = g;
        this.center = c;
        this.north = n;
        this.south = s;
        this.east = e;
        this.west = w;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
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

    public boolean isGold() {
        return gold;
    }



    public boolean equals(GalleryCard c) {
        return  ((this.getGalleryType() == c.getGalleryType()) &&
                (this.getX() == c.getX()) &&
                (this.getY() == c.getY()) &&
                (this.canHasCenter() == c.canHasCenter()) &&
                (this.canHasNorth() == c.canHasNorth()) &&
                (this.canHasSouth() == c.canHasSouth()) &&
                (this.canHasEast() == c.canHasEast()) &&
                (this.canHasWest() == c.canHasWest()) &&
                (this.isGold() == c.isGold()));
    }

    public GalleryCard rotate() {
        return new GalleryCard(this.getGalleryType(), this.getX(), this.getY(), this.isGold(), this.canHasCenter(), this.canHasSouth(), this.canHasNorth(), this.canHasWest(), this.canHasEast());
    }

    public int getConfig() {
        int res = 0;
        if (this.gold) {
            res += 100000;
        }
        if (this.center) {
            res += 10000;
        }
        if (this.north) {
            res += 1000;
        }
        if (this.south) {
            res += 100;
        }
        if (this.east) {
            res += 10;
        }
        if (this.west) {
            res += 1;
        }
        return res;
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
        if (isGold() != card.isGold()) return false;
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
        result = 31 * result + (isGold() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        if (type_g == but && this.gold) return "GOLD!!";
        if (type_g == but && !this.gold) return "Stone";
        if (type_g == start) return "Start";
        return "GalleryCard{" +
                "type=" + type +
                ", x=" + x +
                ", y=" + y +
                ", gold=" + gold +
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
