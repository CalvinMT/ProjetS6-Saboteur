package Board;

import Cards.GalleryCard;
import org.junit.Assert;
import org.junit.Test;

import static Cards.GalleryCard.Gallery_t.*;

public class BoardTest {
    Board b = new Board();

    @Test
    public void testConstructor() throws Exception {
        Assert.assertTrue(b.getElement(0).card.getGalleryType() == start);
        Assert.assertTrue(b.getElement(1).card.getGalleryType() == but);
        Assert.assertTrue(b.getElement(2).card.getGalleryType() == but);
        Assert.assertTrue(b.getElement(3).card.getGalleryType() == but);
        Assert.assertTrue((b.getElement(1).card.isGold() && !b.getElement(2).card.isGold() && !b.getElement(3).card.isGold()) ||
                (!b.getElement(1).card.isGold() && b.getElement(2).card.isGold() && !b.getElement(3).card.isGold()) ||
                (!b.getElement(1).card.isGold() && !b.getElement(2).card.isGold() && b.getElement(3).card.isGold()));
    }
    @Test
    public void addCard() throws Exception {
        int size = b.getMineSize();;
        GalleryCard c = new GalleryCard(tunnel, 1, 0, false, true, false, true, true, false);;

        b.addCard(c);

        Assert.assertTrue(b.getMineSize() == (size + 1));
        Assert.assertTrue(b.getElement(b.getMineSize() - 1).card.equals(c));
        Assert.assertTrue(b.getElement(size).getNorth() == 0);
        Assert.assertTrue(b.getElement(size).getSouth() == -1);
        Assert.assertTrue(b.getElement(size).getEast() == -1);
        Assert.assertTrue(b.getElement(size).getWest() == -1);

    }

    @Test
    public void removeCard() throws Exception {
        int size;
        GalleryCard c = new GalleryCard(tunnel, 1, 0, false, true, false, true, true, false);
        b.addCard(c);
        size = b.getMineSize();
        b.removeCard(new Couple(c.getX(), c.getY()));

        Assert.assertTrue(b.getMineSize() == size - 1);
        Assert.assertFalse(b.getElement(b.getMineSize() - 1).card.equals(c));
    }

    // TODO
    @Test
    public void getAccessibleCards() throws Exception {
        System.err.println("============================================");
        System.err.println("TODO : BoardTest.getAccessibleCards()");
        System.err.println("============================================");
    }

    // TODO
    @Test
    public void isCompatibleWithNeighbors() throws Exception {
        System.err.println("============================================");
        System.err.println("TODO : BoardTest.isCompatibleWithNeighbors()");
        System.err.println("============================================");
    }

    // TODO
    @Test
    public void getPossiblePositions() throws Exception {
        System.err.println("============================================");
        System.err.println("TODO : BoardTest.getPossiblePositions()");
        System.err.println("============================================");
    }

}