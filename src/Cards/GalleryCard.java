package Cards;

import Board.Couple;
import static Cards.GalleryCard.Gallery_t.start;
import static Cards.GalleryCard.Gallery_t.tunnel;

public class GalleryCard extends Card {
    public enum Gallery_t {start, but, tunnel};
    private Gallery_t type_g;
    private Couple coord = new Couple();
    private int resist;

    private boolean center,
                    north,
                    south,
                    east,
                    west;


    public GalleryCard() { // Init start card
        this(start, 0, 0, true, true, true, true, true);
    }

    public GalleryCard(Gallery_t t, boolean c, boolean n, boolean s, boolean e, boolean w) {
        this(t, 0, 0, c, n, s, e, w);
    }
    
    public GalleryCard(boolean c, boolean n, boolean s, boolean e, boolean w){
        this(tunnel, 0, 0, c, n, s, e, w);
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
        setResist();
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

    public void setResist() {
        int res = 0;
        if (this.center) {
            if (this.north) {
                res += 1;
            }
            if (this.south) {
                res += 1;
            }
            if (this.east) {
                res += 1;
            }
            if (this.west) {
                res += 1;
            }
        }
        // Valeur négative pour les saboteurs ?
        /*else {
            if (this.north) {
                res -= 1;
            }
            if (this.south) {
                res -= 1;
            }
            if (this.east) {
                res -= 1;
            }
            if (this.west) {
                res -= 1;
            }
        }*/

        this.resist = res - 1;
    }

    public int getResist() {
        return resist;
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

    // renvoi faux si une carte n'est pas possible
    public boolean possible(){
        int nb = 0;
        if(this.canHasNorth()){
            nb++;
        }
        if(this.canHasSouth()){
            nb++;
        }
        if(this.canHasEast()){
            nb++;
        }
        if(this.canHasWest()){
            nb++;
        }
        if(nb == 0){
            return false;
        } else if(nb == 1 && this.canHasCenter()) {
            return true;
        } else {
            return nb > 1;
        }
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

    public String debugString() {
        String renvoi = "GalleryCard: {";
        if(this.canHasNorth()){
            renvoi += "N";
        }
        if(this.canHasSouth()){
            renvoi += "S";
        }
        if(this.canHasEast()){
            renvoi += "E";
        }
        if(this.canHasWest()){
            renvoi += "W";
        }
        renvoi += "} ";
        if(this.canHasCenter()){
            renvoi += "bloqued";
        }

        return renvoi;
    }
    
    @Override
	public int getGold(){
		return 0;
	}
}
