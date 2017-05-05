package Cards;

public class GalleryCard extends Card {
    public enum Gallery_t {start, but, tunnel};
    private Gallery_t type;
    private int x = 0,
                y = 0;
    private boolean center = true,
                    north = true,
                    south = true,
                    east = true,
                    west = true;
    private boolean gold = false;

    public GalleryCard() { // Init start card
        this.type = Gallery_t.start;
    }

    public GalleryCard(Gallery_t t){
        this.type = t;
    }

    // Pour debug
    public GalleryCard(Gallery_t t, int x, int y, boolean g, boolean c, boolean n, boolean s, boolean e, boolean w) {
        this.type = t;
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
        return type;
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
        return  this.getGalleryType() == c.getGalleryType() &&
                this.getX() == c.getY() &&
                this.getY() == c.getY() &&

                this.canHasCenter() == c.canHasCenter() &&
                this.canHasNorth() == c.canHasNorth() &&
                this.canHasSouth() == c.canHasSouth() &&
                this.canHasEast() == c.canHasEast() &&
                this.canHasWest() == c.canHasWest() &&
                this.isGold() == c.isGold();
    }

    public void rotate() {
        boolean tmp;

        tmp = this.north;
        this.north = this.south;
        this.south = tmp;

        tmp = this.east;
        this.east = this.west;
        this.west = tmp;
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
}
