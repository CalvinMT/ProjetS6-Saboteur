package Board;

import Cards.GalleryCard;
import org.junit.Assert;
import org.junit.Test;

import java.util.Hashtable;

import static Cards.GalleryCard.Gallery_t.*;

public class BoardTest {
    Board b = new Board();

    @Test
    public void testConstructor() throws Exception {
        Assert.assertTrue(b.getMineElement(0).card.getGalleryType() == start);
        Assert.assertTrue(b.getMineElement(1).card.getGalleryType() == but);
        Assert.assertTrue(b.getMineElement(2).card.getGalleryType() == but);
        Assert.assertTrue(b.getMineElement(3).card.getGalleryType() == but);
        Assert.assertTrue((b.getMineElement(1).card.isGold() && !b.getMineElement(2).card.isGold() && !b.getMineElement(3).card.isGold()) ||
                (!b.getMineElement(1).card.isGold() && b.getMineElement(2).card.isGold() && !b.getMineElement(3).card.isGold()) ||
                (!b.getMineElement(1).card.isGold() && !b.getMineElement(2).card.isGold() && b.getMineElement(3).card.isGold()));
    }
    @Test
    public void addCard() throws Exception {
        int size = b.getMineSize();;
        GalleryCard c = new GalleryCard(tunnel, 1, 0, false, true, false, true, true, false);;

        b.addCard(c);

        Assert.assertTrue(b.getMineSize() == (size + 1));
        Assert.assertTrue(b.getMineElement(b.getMineSize() - 1).card.equals(c));
        Assert.assertTrue(b.getMineElement(size).getNorth() == 0);
        Assert.assertTrue(b.getMineElement(size).getSouth() == -1);
        Assert.assertTrue(b.getMineElement(size).getEast() == -1);
        Assert.assertTrue(b.getMineElement(size).getWest() == -1);

    }

    @Test
    public void removeCard() throws Exception {
        int size;
        GalleryCard c = new GalleryCard(tunnel, 1, 0, false, true, false, true, true, false);
        b.addCard(c);
        size = b.getMineSize();
        b.removeCard(new Couple(c.getX(), c.getY()));

        Assert.assertTrue(b.getMineSize() == size - 1);
        Assert.assertFalse(b.getMineElement(b.getMineSize() - 1).card.equals(c));
    }


    @Test
    public void hashtableTest() throws Exception {
        Hashtable<String, Node> h = new Hashtable<String, Node>();

        GalleryCard c = new GalleryCard(tunnel, -1, 0, false, true, false, true, true, false);


        h.put(new Couple(c.getX(), c.getY()).toString(), new Node(c));

        Assert.assertTrue(h.get(new Couple(c.getX(), c.getY()).toString()).equals(new Node(c)));

    }

    @Test
    public void accessibleCards() throws Exception {
        Hashtable<String, Node> h;
        GalleryCard card1 = new GalleryCard(tunnel, -1, 0, false, true, false, true, true, false),
                    card2 = new GalleryCard(tunnel, -1, 1, false, true, true, true, false, true),
                    card3 = new GalleryCard(tunnel, -2, 1, false, true, true, true, false, false),
                    card4 = new GalleryCard(tunnel, 1, 1, false, true, false, false, true, true);
        Couple  cpl1 = new Couple(-1, 0),
                cpl2 = new Couple(-1, 1),
                cpl3 = new Couple(-2, 1),
                cpl4 = new Couple(1, 1);

        b.addCard(card1);
        b.addCard(card2);
        b.addCard(card3);
        b.addCard(card4);

        b.computeAccessCards();
        h = b.getAccessCard();
        Assert.assertTrue(h.containsKey(cpl1.toString()));
        Assert.assertTrue(h.containsKey(cpl2.toString()));
        Assert.assertTrue(h.containsKey(cpl3.toString()));
        Assert.assertFalse(h.containsKey(cpl4.toString()));
        Assert.assertTrue(h.get(cpl1.toString()).card.equals(card1));
        Assert.assertTrue(h.get(cpl2.toString()).card.equals(card2));
        Assert.assertTrue(h.get(cpl3.toString()).card.equals(card3));
    }

    // TODO
    @Test
    public void isCompatibleWithNeighbors() throws Exception {
        System.err.println("TODO : BoardTest.isCompatibleWithNeighbors()");
    }

    // TODO
    @Test
    public void getPossiblePositions() throws Exception {
        System.err.println("TODO : BoardTest.getPossiblePositions()");
    }

}