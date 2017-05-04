package Cards;

import org.junit.Test;

import static Cards.GalleryCard.Gallery_t.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GalleryCardTest {

    @Test
    public void getterSetterX() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.getX() == 0);
        c.setX(2);
        assertTrue(c.getX() == 2);
    }

    @Test
    public void getterSetterY() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.getY() == 0);
        c.setY(2);
        assertTrue(c.getY() == 2);
    }

    @Test
    public void getGalleryType() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.getGalleryType() == start);
        c = new GalleryCard(tunnel, 2, 2, false, true, true, true, true, true);
        assertTrue(c.getGalleryType() == tunnel);
        c = new GalleryCard(but, 2, 2, false, true, true, true, true, true);
        assertTrue(c.getGalleryType() == but);
    }

    @Test
    public void hasNorth() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.hasNorth());
        c = new GalleryCard(tunnel, 0, 0, false, true, false, true, true, true);
        assertFalse(c.hasNorth());
    }

    @Test
    public void hasSouth() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.hasSouth());
        c = new GalleryCard(tunnel, 0, 0, false, true, true, false, true, true);
        assertFalse(c.hasSouth());
    }

    @Test
    public void hasEast() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.hasEast());
        c = new GalleryCard(tunnel, 0, 0, false, true, true, true, false, true);
        assertFalse(c.hasEast());
    }

    @Test
    public void hasWest() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.hasWest());
        c = new GalleryCard(tunnel, 0, 0, false, true, true, true, true, false);
        assertFalse(c.hasWest());
    }

    @Test
    public void hasCenter() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.hasCenter());
        c = new GalleryCard(tunnel, 0, 0, false, false, true, true, true, true);
        assertFalse(c.hasCenter());
    }

    @Test
    public void isGold() throws Exception {
        GalleryCard c = new GalleryCard();
        assertFalse(c.isGold());
        c = new GalleryCard(tunnel, 0, 0, true, true, true, true, true, true);
        assertTrue(c.isGold());
    }

    @Test
    public void equals() throws Exception {
        GalleryCard c1 = new GalleryCard();
        GalleryCard c2 = new GalleryCard(tunnel, 1, 2, true, false, false, true, false, true);

        assertFalse(c1.equals(c2));
        c2 = new GalleryCard();
        assertTrue(c1.equals(c2));
    }

    @Test
    public void rotate() throws Exception {
        GalleryCard c = new GalleryCard(tunnel, 0, 0, false, true, false, true, false, true);
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

    @Test
    public void getConfig() throws Exception {
        GalleryCard c;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    for (int l = 0; l < 2; l++) {
                        for (int m = 0; m < 2; m++) {
                            for (int n = 0; n < 2; n++) {
                                c = new GalleryCard(tunnel, 0, 0, (i==1), (j==1), (k==1), (l==1), (m==1), (n==1));
                                assertTrue(c.getConfig() == (i * 100000) + (j * 10000) + (k * 1000) + (l * 100) + (m * 10) + (n * 1));
                            }
                        }
                    }
                }
            }
        }
    }
}