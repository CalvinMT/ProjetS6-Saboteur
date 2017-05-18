package Board;

import Cards.GalleryCard;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
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
        GalleryCard c = new GalleryCard(tunnel, 1, 0, true, false, true, true, false);;
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
        GalleryCard c = new GalleryCard(tunnel, 1, 0, true, false, true, true, false);
        b.addCard(c);
        size = b.getMineSize();
        b.removeCard(new Couple(c.getX(), c.getY()));

        Assert.assertTrue(b.getMineSize() == size - 1);
        Assert.assertFalse(b.getMineElement(b.getMineSize() - 1).card.equals(c));
    }

    @Test
    public void hashtableTest() throws Exception {
        Hashtable<Couple , Node> h = new Hashtable<Couple , Node>();
        GalleryCard c = new GalleryCard(tunnel, -1, 0, true, false, true, true, false);

        h.put(new Couple(c.getX(), c.getY()), new Node(c));

        Assert.assertTrue(h.get(new Couple(c.getX(), c.getY())).equals(new Node(c)));
    }

    @Test
    public void accessibleCards() throws Exception {
        Hashtable<Couple, Node> h;
        GalleryCard card1 = new GalleryCard(tunnel, -1, 0, true, false, true, true, false),
                    card2 = new GalleryCard(tunnel, -1, 1, true, true, true, false, true),
                    card3 = new GalleryCard(tunnel, -2, 1, true, true, true, false, false),
                    card4 = new GalleryCard(tunnel, 1, 1, true, false, false, true, true);
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
        Assert.assertTrue(h.containsKey(cpl1));
        Assert.assertTrue(h.containsKey(cpl2));
        Assert.assertTrue(h.containsKey(cpl3));
        Assert.assertFalse(h.containsKey(cpl4));
        Assert.assertTrue(h.get(cpl1).card.equals(card1));
        Assert.assertTrue(h.get(cpl2).card.equals(card2));
        Assert.assertTrue(h.get(cpl3).card.equals(card3));
    }

    @Test
    public void isCompatibleWithNeighbors() throws Exception {
        Hashtable<String, Node> h;
        GalleryCard card1 = new GalleryCard(tunnel, -1, 0, true, false, true, true, false),
                card2 = new GalleryCard(tunnel, -1, 1, true, true, true, false, true),
                card3 = new GalleryCard(tunnel, -2, 1, true, true, true, false, false),
                card4 = new GalleryCard(tunnel, 1, 1, true, false, false, true, true),
                cardTest = new GalleryCard(tunnel, 1, 1, true, true, true, false, false);

        Couple  cpl1 = new Couple(-1, 0),
                cpl2 = new Couple(-1, 1),
                cpl3 = new Couple(-2, 1),
                cpl4 = new Couple(1, 1);

        b.addCard(card1);
        b.addCard(card2);
        b.addCard(card3);
        b.addCard(card4);

        Assert.assertTrue(b.isCompatibleWithNeighbors(cardTest, new Couple(-3, 1)));
        Assert.assertFalse(b.isCompatibleWithNeighbors(cardTest, new Couple(0, 1)));
        Assert.assertFalse(b.isCompatibleWithNeighbors(cardTest, new Couple(1, 0)));
        Assert.assertFalse(b.isCompatibleWithNeighbors(cardTest, new Couple(0, -1)));

        cardTest = new GalleryCard(tunnel, 2, 2, true, false, false, true, true);
        Assert.assertFalse(b.isCompatibleWithNeighbors(cardTest, new Couple(-3, 1)));
        Assert.assertFalse(b.isCompatibleWithNeighbors(cardTest, new Couple(0, 1)));
        Assert.assertFalse(b.isCompatibleWithNeighbors(cardTest, new Couple(1, 0)));
        Assert.assertTrue(b.isCompatibleWithNeighbors(cardTest, new Couple(0, -1)));

        cardTest = new GalleryCard(tunnel, 3, 3, true, true, false, false, true);
        Assert.assertFalse(b.isCompatibleWithNeighbors(cardTest, new Couple(-3, 1)));
        Assert.assertTrue(b.isCompatibleWithNeighbors(cardTest, new Couple(0, 1)));
        Assert.assertFalse(b.isCompatibleWithNeighbors(cardTest, new Couple(1, 0)));
        Assert.assertFalse(b.isCompatibleWithNeighbors(cardTest, new Couple(0, -1)));
    }

    @Test
    public void getPossiblePositions() throws Exception {
        ArrayList<Couple> p;

        GalleryCard card1 = new GalleryCard(tunnel, -1, 0, true, true, true, true, false),
                card2 = new GalleryCard(tunnel, -1, 1, true, true, true, false, true),
                card3 = new GalleryCard(tunnel, -2, 1, true, true, true, true, false),
                card4 = new GalleryCard(tunnel, 1, 1, true, false, false, true, true),
                card5 = new GalleryCard(tunnel, 0, -1, true, false, true, true, false),
                cardTest = new GalleryCard(tunnel, 1, 1, true, true, true, false, false);

        b.addCard(card1);
        b.addCard(card2);
        b.addCard(card3);
        b.addCard(card4);
        b.addCard(card5);

        p = b.getPossiblePositions(cardTest);

        Assert.assertTrue(p.contains(new Couple(1, -1)));
        Assert.assertFalse(p.contains(new Couple(1, 0)));
        Assert.assertFalse(p.contains(new Couple(0, 1)));
        Assert.assertTrue(p.contains(new Couple(-2, 0)));
        Assert.assertFalse(p.contains(new Couple(-2, 2)));
        Assert.assertTrue(p.contains(new Couple(-3, 1)));

        cardTest = new GalleryCard(tunnel, 2, 2, true, true, false, false, true);

        p = b.getPossiblePositions(cardTest);

        Assert.assertTrue(p.contains(new Couple(1, -1)));
        Assert.assertFalse(p.contains(new Couple(1, 0)));
        Assert.assertTrue(p.contains(new Couple(0, 1)));
        Assert.assertFalse(p.contains(new Couple(-2, 0)));
        Assert.assertTrue(p.contains(new Couple(-2, 2)));
        Assert.assertTrue(p.contains(new Couple(-3, 1)));
    }
}