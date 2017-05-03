package Cards;

public class GalleryCard extends Card {
    public enum Gallery_t {start, but, tunnel};
    private Gallery_t type;
    private int x, y;

    @Override
    GalleryCard() {
        this.x = 0;
        this.y = 0;
        this.type = Gallery_t.start;
    }

    GalleryCard(Gallery_t t, int x, int y) {
        this.type = t;
        this.x = x;
        this.y = y;
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
}
