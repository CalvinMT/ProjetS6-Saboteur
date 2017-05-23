package Cards;

import Board.Couple;
import org.junit.Assert;
import org.junit.Test;

import static Cards.GalleryCard.Gallery_t.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GalleryCardTest {

    @Test
    public void setResist() throws Exception {
        int res;
        GalleryCard c = new GalleryCard();
        assertTrue(c.getResist() == 4);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    for (int l = 0; l < 2; l++) {
                        for (int m = 0; m < 2; m++) {
                            c = new GalleryCard((i==1), (j==1), (k==1), (l==1), (m==1));
                            if (i == 0) { // Center : false
                                assertTrue(c.getResist() == 0);
                            }
                            else {
                                res = j+k+l+m;
                                assertTrue(c.getResist() == res);
                            }
                        }
                    }
                }
            }
        }
    }


    @Test
    public void getterSetterX() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.getLine() == 0);
        c.setLine(2);
        assertTrue(c.getLine() == 2);
    }

    @Test
    public void getterSetterY() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.getColumn() == 0);
        c.setColumn(2);
        assertTrue(c.getColumn() == 2);
    }

    @Test
    public void getGalleryType() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.getGalleryType() == start);
        c = new GalleryCard(tunnel, 2, 2, true, true, true, true, true);
        assertTrue(c.getGalleryType() == tunnel);
        c = new GoalCard(new Couple(2, 2), true, true, true, true, true);
        assertTrue(c.getGalleryType() == but);
    }

    @Test
    public void hasNorth() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.canHasNorth());
        c = new GalleryCard(tunnel, 0, 0, true, false, true, true, true);
        assertFalse(c.canHasNorth());
    }

    @Test
    public void hasSouth() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.canHasSouth());
        c = new GalleryCard(tunnel, 0, 0, true, true, false, true, true);
        assertFalse(c.canHasSouth());
    }

    @Test
    public void hasEast() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.canHasEast());
        c = new GalleryCard(tunnel, 0, 0, true, true, true, false, true);
        assertFalse(c.canHasEast());
    }

    @Test
    public void hasWest() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.canHasWest());
        c = new GalleryCard(tunnel, 0, 0, true, true, true, true, false);
        assertFalse(c.canHasWest());
    }

    @Test
    public void hasCenter() throws Exception {
        GalleryCard c = new GalleryCard();
        assertTrue(c.canHasCenter());
        c = new GalleryCard(tunnel, 0, 0, false, true, true, true, true);
        assertFalse(c.canHasCenter());
    }

    @Test
    public void equals() throws Exception {
        GalleryCard c1 = new GalleryCard();
        GalleryCard c2 = new GalleryCard(tunnel, 1, 2, false, false, true, false, true);

        assertFalse(c1.equals(c2));
        c1 = new GalleryCard(tunnel, 1, 2, false, false, true, false, true);
        assertTrue(c1.equals(c2));
    }

    @Test
    public void rotate() throws Exception {
        GalleryCard c2, c = new GalleryCard(tunnel, 0, 0, true, false, true, false, true);

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

        c = new GalleryCard(tunnel, 0, 0, true, true, true, false, true);

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
        int res;
        GalleryCard c;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    for (int l = 0; l < 2; l++) {
                        for (int m = 0; m < 2; m++) {
                            res = 0;
                            c = new GalleryCard(tunnel, 0, 0, (i==1), (j==1), (k==1), (l==1), (m==1));
                            if (i==1) {
                                res += 0b10000;
                            }
                            if (j==1) {
                                res += 0b1000;
                            }
                            if (k==1) {
                                res += 0b100;
                            }
                            if (l==1) {
                                res += 0b10;
                            }
                            if (m==1) {
                                res += 0b1;
                            }
                            assertTrue(c.getConfig() == res);
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testPossible1(){
        boolean center = true;
        boolean north = true;
        boolean south = false;
        boolean east = false;
        boolean west = false;

        GalleryCard c = new GalleryCard(GalleryCard.Gallery_t.tunnel, center, north, south, east, west);
        Assert.assertFalse(c.possible());
    }

    @Test
    public void testPossible2(){
        boolean center = false;
        boolean north = true;
        boolean south = false;
        boolean east = false;
        boolean west = false;

        GalleryCard c = new GalleryCard(GalleryCard.Gallery_t.tunnel, center, north, south, east, west);
        Assert.assertTrue(c.possible());
    }

    @Test
    public void testPossible3(){
        boolean center = true;
        boolean north = false;
        boolean south = false;
        boolean east = false;
        boolean west = false;

        GalleryCard c = new GalleryCard(GalleryCard.Gallery_t.tunnel, center, north, south, east, west);
        Assert.assertFalse(c.possible());
    }

    @Test
    public void testPossible4(){
        boolean center = true;
        boolean north = true;
        boolean south = true;
        boolean east = true;
        boolean west = true;

        GalleryCard c = new GalleryCard(GalleryCard.Gallery_t.tunnel, center, north, south, east, west);
        Assert.assertTrue(c.possible());
    }
}