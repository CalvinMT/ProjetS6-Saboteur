package Cards;

import org.junit.Test;

import static Cards.GalleryCard.Gallery_t.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GalleryCardTest {

    @Test
    public void getX() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.getX() == 0);
        c = new GalleryCard(tunnel, 2, 2);
        assertTrue(c.getX() == 2);
    }

    @Test
    public void getY() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.getY() == 0);
        c = new GalleryCard(tunnel, 2, 2);
        assertTrue(c.getY() == 2);
    }

    @Test
    public void getGalleryType() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.getGalleryType() == start);
        c = new GalleryCard(tunnel, 2, 2);
        assertTrue(c.getGalleryType() == tunnel);
        c = new GalleryCard(but, 2, 2);
        assertTrue(c.getGalleryType() == but);
    }

    @Test
    public void hasNorth() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.hasNorth());
        c = new GalleryCard(tunnel, 0, 0, true, false, true, true, true);
        assertFalse(c.hasNorth());
    }

    @Test
    public void hasSouth() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.hasSouth());
        c = new GalleryCard(tunnel, 0, 0, true, true, false, true, true);
        assertFalse(c.hasSouth());
    }

    @Test
    public void hasEast() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.hasEast());
        c = new GalleryCard(tunnel, 0, 0, true, true, true, false, true);
        assertFalse(c.hasEast());
    }

    @Test
    public void hasWest() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.hasWest());
        c = new GalleryCard(tunnel, 0, 0, true, true, true, true, false);
        assertFalse(c.hasWest());
    }

    @Test
    public void hasCenter() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.hasCenter());
        c = new GalleryCard(tunnel, 0, 0, false, true, true, true, true);
        assertFalse(c.hasCenter());
    }

    @Test
    public void isGold() throws Exception {
        GalleryCard c = new GalleryCard();
        assertFalse(c.isGold());
        c = new GalleryCard(tunnel, 0, 0, true, false, true, true, true, true);
        assertTrue(c.isGold());
    }

    @Test
    public void rotate() throws Exception {
        GalleryCard c = new GalleryCard(tunnel, 0, 0, true, false, true, false, true);
        assertFalse(c.hasNorth());
        assertFalse(c.hasEast());
        assertTrue(c.hasSouth());
        assertTrue(c.hasWest());
        c.rotate();
        assertTrue(c.hasNorth());
        assertTrue(c.hasEast());
        assertFalse(c.hasSouth());
        assertFalse(c.hasWest());
    }

}