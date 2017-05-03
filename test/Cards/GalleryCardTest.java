package Cards;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static Cards.GalleryCard.Gallery_t.*;

class GalleryCardTest {

    @Test
    void getX() {
        GalleryCard c = new GalleryCard();
        Assertions.assertTrue(c.getX() == 0);
        c = new GalleryCard(tunnel, 2, 2);
        Assertions.assertTrue(c.getX() == 2);
    }

    @Test
    void getY() {
        GalleryCard c = new GalleryCard();
        Assertions.assertTrue(c.getY() == 0);
        c = new GalleryCard(tunnel, 2, 2);
        Assertions.assertTrue(c.getY() == 2);
    }

    @Test
    void getGalleryType() {
        GalleryCard c = new GalleryCard();
        Assertions.assertTrue(c.getGalleryType() == start);
        c = new GalleryCard(tunnel, 2, 2);
        Assertions.assertTrue(c.getGalleryType() == tunnel);
        c = new GalleryCard(but, 2, 2);
        Assertions.assertTrue(c.getGalleryType() == but);
    }

    @Test
    void hasNorth() {
        GalleryCard c = new GalleryCard();
        Assertions.assertTrue(c.hasNorth());
        c = new GalleryCard(tunnel, 0, 0, true, false, true, true, true);
        Assertions.assertFalse(c.hasNorth());
    }

    @Test
    void hasSouth() {
        GalleryCard c = new GalleryCard();
        Assertions.assertTrue(c.hasSouth());
        c = new GalleryCard(tunnel, 0, 0, true, true, false, true, true);
        Assertions.assertFalse(c.hasSouth());
    }

    @Test
    void hasEast() {
        GalleryCard c = new GalleryCard();
        Assertions.assertTrue(c.hasEast());
        c = new GalleryCard(tunnel, 0, 0, true, true, true, false, true);
        Assertions.assertFalse(c.hasEast());
    }

    @Test
    void hasWest() {
        GalleryCard c = new GalleryCard();
        Assertions.assertTrue(c.hasWest());
        c = new GalleryCard(tunnel, 0, 0, true, true, true, true, false);
        Assertions.assertFalse(c.hasWest());
    }

    @Test
    void hasCenter() {
        GalleryCard c = new GalleryCard();
        Assertions.assertTrue(c.hasCenter());
        c = new GalleryCard(tunnel, 0, 0, false, true, true, true, true);
        Assertions.assertFalse(c.hasCenter());
    }

    @Test
    void rotate() {
        GalleryCard c = new GalleryCard(tunnel, 0, 0, true, false, true, false, true);
        Assertions.assertFalse(c.hasNorth());
        Assertions.assertFalse(c.hasEast());
        Assertions.assertTrue(c.hasSouth());
        Assertions.assertTrue(c.hasWest());
        c.rotate();
        Assertions.assertTrue(c.hasNorth());
        Assertions.assertTrue(c.hasEast());
        Assertions.assertFalse(c.hasSouth());
        Assertions.assertFalse(c.hasWest());
    }

}