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
        assertTrue(c.canHasNorth());
        c = new GalleryCard(tunnel, 0, 0, false, true, false, true, true, true);
        assertFalse(c.canHasNorth());
    }

    @Test
    public void hasSouth() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.canHasSouth());
        c = new GalleryCard(tunnel, 0, 0, false, true, true, false, true, true);
        assertFalse(c.canHasSouth());
    }

    @Test
    public void hasEast() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.canHasEast());
        c = new GalleryCard(tunnel, 0, 0, false, true, true, true, false, true);
        assertFalse(c.canHasEast());
    }

    @Test
    public void hasWest() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.canHasWest());
        c = new GalleryCard(tunnel, 0, 0, false, true, true, true, true, false);
        assertFalse(c.canHasWest());
    }

    @Test
    public void hasCenter() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.canHasCenter());
        c = new GalleryCard(tunnel, 0, 0, false, false, true, true, true, true);
        assertFalse(c.canHasCenter());
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
        c1 = new GalleryCard(tunnel, 1, 2, true, false, false, true, false, true);
        assertTrue(c1.equals(c2));
    }

    @Test
    public void rotate() throws Exception {
        GalleryCard c2, c = new GalleryCard(tunnel, 0, 0, false, true, false, true, false, true);

        assertFalse(c.canHasNorth());
        assertFalse(c.canHasEast());
        assertTrue(c.canHasSouth());
        assertTrue(c.canHasWest());
        c2 = c.rotate();
        assertTrue(c2.canHasNorth());
        assertTrue(c2.canHasEast());
        assertFalse(c2.canHasSouth());
        assertFalse(c2.canHasWest());
        assertTrue(c.equals(c2.rotate()));

        c = new GalleryCard(tunnel, 0, 0, false, true, true, true, false, true);

        assertTrue(c.canHasNorth());
        assertFalse(c.canHasEast());
        assertTrue(c.canHasSouth());
        assertTrue(c.canHasWest());
        c2 = c.rotate();
        assertTrue(c2.canHasNorth());
        assertTrue(c2.canHasEast());
        assertTrue(c2.canHasSouth());
        assertFalse(c2.canHasWest());
        assertTrue(c.equals(c2.rotate()));
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