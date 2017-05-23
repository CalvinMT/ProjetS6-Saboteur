package Board;

import Cards.GalleryCard;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

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
                    card4 = new GalleryCard(tunnel, 1, 1, true, false, false, true, true)/*,
                    card5 = new GalleryCard(tunnel, -3, 1, true, false, true, true, false),
                    card6 = new GalleryCard(tunnel, -3, 2, true, false, true, false, true),
                    card7 = new GalleryCard(tunnel, -2, 2, true, true, true, false, true),
                    card8 = new GalleryCard(tunnel, -2, 1, true, true, true, true, false)*/;
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
        /*
        b.removeCard(new Couple(-2, 1));
        b.addCard(card8);
        b.addCard(card5);
        b.addCard(card6);
        b.addCard(card7);
        b.computeAccessCards();
        h = b.getAccessCard();
        //System.out.println(b.getMine());
        //System.out.println(h);*/
    }

    @Test
    public void isCompatibleWithNeighbors() throws Exception {
        Hashtable<String, Node> h;
        GalleryCard card1 = new GalleryCard(tunnel, -1, 0, true, false, true, true, false),
                card2 = new GalleryCard(tunnel, -1, 1, true, true, true, false, true),
                card3 = new GalleryCard(tunnel, -2, 1, true, true, true, false, false),
                card4 = new GalleryCard(tunnel, 1, 1, true, false, false, true, true),
                cardTest = new GalleryCard(tunnel, 1, 1, true, true, true, false, false);
        /*
        Couple  cpl1 = new Couple(-1, 0),
                cpl2 = new Couple(-1, 1),
                cpl3 = new Couple(-2, 1),
                cpl4 = new Couple(1, 1);
        */

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

    @Test
    public void computePathRes() throws Exception {
        int res;
        //LinkedList<Node> path = new LinkedList<Node>();
        GalleryCard card1 = new GalleryCard(tunnel, -1, 0, true, false, true, true, false),
                    card2 = new GalleryCard(tunnel, -1, 1, true, true, true, false, true),
                    card3 = new GalleryCard(tunnel, -2, 1, true, true, true, true, false),
                    card4 = new GalleryCard(tunnel, 1, 1, true, false, false, true, true),
                    card5 = new GalleryCard(tunnel, -3, 1, true, false, true, true, false),
                    card6 = new GalleryCard(tunnel, -3, 2, true, false, true, false, true),
                    card7 = new GalleryCard(tunnel, -2, 2, true, true, true, false, true);

        // start            // 0
        // but1             // 1
        // but2             // 2
        // but3             // 3
        b.addCard(card1);   // 4
        b.addCard(card2);   // 5
        b.addCard(card3);   // 6
        b.addCard(card4);   // 7
        b.addCard(card5);   // 8
        b.addCard(card6);   // 9
        b.addCard(card7);   //10

        b.computeAccessCards();

        b.computePathRes(b.getMineElement(0), new LinkedList<Node>(), 0);

        res = b.getMineElement(0).getPathRes();
        if (res != 4) System.out.println("\nExpected 4 found " + res);
        Assert.assertTrue(res == 4);
        res = b.getMineElement(0).getPathLength();
        if (res != 1) System.out.println("\nExpected 1 found " + res);
        Assert.assertTrue(res == 1);

        res = b.getMineElement(4).getPathRes();
        if (res != 6) System.out.println("\nExpected 6 found " + res);
        Assert.assertTrue( res == 6);
        res = b.getMineElement(4).getPathLength();
        if (res != 2) System.out.println("\nExpected 2 found " + res);
        Assert.assertTrue(res == 2);

        res = b.getMineElement(5).getPathRes();
        if (res != 9) System.out.println("\nExpected 9 found " + res);
        Assert.assertTrue( res == 9);
        res = b.getMineElement(5).getPathLength();
        if (res != 3) System.out.println("\nExpected 3 found " + res);
        Assert.assertTrue(res == 3);

        res = b.getMineElement(6).getPathRes();
        if (res != 12) System.out.println("\nExpected 12 found " + res);
        Assert.assertTrue( res == 12);
        res = b.getMineElement(6).getPathLength();
        if (res != 4) System.out.println("\nExpected 4 found " + res);
        Assert.assertTrue(res == 4);

        res = b.getMineElement(7).getPathRes();
        if (res != 2) System.out.println("\nExpected 2 found " + res);
        Assert.assertTrue( res == 2);
        res = b.getMineElement(7).getPathLength();
        if (res != 1) System.out.println("\nExpected 4 found " + res);
        Assert.assertTrue(res == 1);

        res = b.getMineElement(8).getPathRes();
        if (res != 14) System.out.println("\nExpected 14 found " + res);
        Assert.assertTrue( res == 14);
        res = b.getMineElement(8).getPathLength();
        if (res != 5) System.out.println("\nExpected 5 found " + res);
        Assert.assertTrue(res == 5);

        res = b.getMineElement(9).getPathRes();
        if (res != 16) System.out.println("\nExpected 16 found " + res);
        Assert.assertTrue( res == 16);
        res = b.getMineElement(9).getPathLength();
        if (res != 6) System.out.println("\nExpected 6 found " + res);
        Assert.assertTrue(res == 6);

        res = b.getMineElement(10).getPathRes();
        if (res != 19) System.out.println("\nExpected 19 found " + res);
        Assert.assertTrue( res == 19);
        res = b.getMineElement(10).getPathLength();
        if (res != 7) System.out.println("\nExpected 7 found " + res);
        Assert.assertTrue(res == 7);
    }
}