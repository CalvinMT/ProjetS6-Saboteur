package Cards;

public class GalleryCard extends Card {
    public enum Gallery_t {start, but, tunnel};
    private Gallery_t type;
    private int x, y;
    private boolean center, north, south, east, west;

    GalleryCard() {
        this.x = 0;
        this.y = 0;
        this.type = Gallery_t.start;
        this.center = true;
        this.north = true;
        this.south = true;
        this.east = true;
        this.west = true;
    }
    // Pour debug
    GalleryCard(Gallery_t t, int x, int y){
        this.type = t;
        this.x = x;
        this.y = y;
        this.center = false;
        this.north = false;
        this.south = false;
        this.east = false;
        this.west = false;
    }

    GalleryCard(Gallery_t t, int x, int y, boolean c, boolean n, boolean s, boolean e, boolean w) {
        this.type = t;
        this.x = x;
        this.y = y;
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

    public Gallery_t getGalleryType() {
        return type;
    }

    public boolean hasNorth() {
        return this.north;
    }

    public boolean hasSouth() {
        return this.south;
    }

    public boolean hasEast() {
        return this.east;
    }

    public boolean hasWest() {
        return this.west;
    }

    public boolean hasCenter() {
        return this.center;
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
}
