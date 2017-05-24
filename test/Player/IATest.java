package Player;

import Board.Board;
import Board.Couple;
import Cards.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static Cards.Card.Card_t.gallery;
import static Cards.GalleryCard.Gallery_t.tunnel;
import static Player.Player.Difficulty.Easy;
import static Player.Player.Difficulty.Medium;
import static org.junit.Assert.assertTrue;

public class IATest {
    IA ia = new IA(0);

    public String dispCard(GalleryCard c) {
        String res = "{(";

        if (c.getLine() >= 0) {
            res+= " " + c.getLine();
        }
        else {
            res+=c.getLine();
        }

        if (c.getColumn() >= 0) {
            res+= " " + c.getColumn();
        }
        else {
            res+=c.getColumn();
        }
        res+=Integer.toBinaryString(c.getConfig());
        return res;
    }

    @Test
    public void getDistanceToGoal() throws Exception {
        assertTrue(ia.getDistanceToGoal(new Couple(0, 8), new Couple(0, 7)) == 1);
        assertTrue(ia.getDistanceToGoal(new Couple(-2, 8), new Couple(1, 3)) == 8);
        assertTrue(ia.getDistanceToGoal(new Couple(2, 8), new Couple(1, 12)) == 5);
        assertTrue(ia.getDistanceToGoal(new Couple(2, 8), new Couple(1, 12)) == 5);
        assertTrue(ia.getDistanceToGoal(new Couple(0, 8), new Couple(1, 0)) == 9);
        assertTrue(ia.getDistanceToGoal(new Couple(-2, 8), new Couple(-1, 2)) == 7);
        assertTrue(ia.getDistanceToGoal(new Couple(0, 8), new Couple(0, 1)) == 7);

    }

    // TODO
    @Test
    public void choosePosition() throws Exception {
        IA ia = new IA(0, "IA", Medium);
        Couple cpl, expected;
        Board b = new Board();
        GalleryCard card1 = new GalleryCard(tunnel, -1, 0, true, false, true, true, false),
                card2 = new GalleryCard(tunnel, -1, 1, true, true, true, false, true),
                card3 = new GalleryCard(tunnel, -2, 1, true, true, true, true, false),
                card4 = new GalleryCard(tunnel, 1, 1, true, false, false, true, true),
                card5 = new GalleryCard(tunnel, -3, 1, true, false, true, true, false),
                card6 = new GalleryCard(tunnel, -3, 2, true, false, true, false, true),
                card7 = new GalleryCard(tunnel, -2, 2, true, true, true, false, true),
                handCard1 = new GalleryCard(tunnel, 0, 0, true, true, false, true, false),
                handCard2 = new GalleryCard(tunnel, 0, 0, true, true, false, true, true),
                handCard3 = new GalleryCard(tunnel, 0, 0, true, false, true, true, true),
                handCard4 = new GalleryCard(tunnel, 0, 0, true, false, false, true, true),
                res, cardTest;

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

        ia.setBoard(b);
        ia.drawCard(handCard1);
        ia.drawCard(handCard2);
        ia.drawCard(handCard3);
        ia.drawCard(handCard4);

        ia.choosePosition();

        res = (GalleryCard) ia.getCardToPlay();
        if (!(res.equals(handCard1)) && !(res.equals(handCard2))) {
            System.out.printf("Expected %s or %s found %s", dispCard(handCard1), dispCard(handCard2), dispCard(res));
        }
        Assert.assertTrue((res.equals(handCard1)) || (res.equals(handCard2)));

        if (res.equals(handCard1)) {
            cpl = ia.getPosToPlay();
            expected = new Couple(-1, 2);
            if (!cpl.equals(expected)) {
                System.out.printf("Expected (%2d, %2d) found (%2d, %2d)", expected.getLine(), expected.getColumn(), cpl.getLine(), cpl.getColumn());
            }
            Assert.assertTrue(cpl.equals(expected));
        }

        if (res.equals(handCard2)) {
            cpl = ia.getPosToPlay();
            expected = new Couple(0, 1);
            if (!cpl.equals(expected)) {
                System.out.printf("Expected (%2d, %2d) found (%2d, %2d)", expected.getLine(), expected.getColumn(), cpl.getLine(), cpl.getColumn());
            }
            Assert.assertTrue(cpl.equals(expected));
        }


        // Exemple jeu IA
        ia.getPlayableCards().getArrayCard().remove(ia.getCardToPlay());
        cardTest = (GalleryCard) ia.getCardToPlay();
        cardTest.setLine(ia.getPosToPlay().getLine());
        cardTest.setColumn(ia.getPosToPlay().getColumn());
        ia.board.addCard(cardTest);

        ia.choosePosition();

        if (res.equals(handCard1)) {
            res = (GalleryCard) ia.getCardToPlay();
            if (!res.equals(handCard2) && !res.equals(handCard3) && !res.equals(handCard4)) {
                System.out.printf("Expected %s or %s or %s found %s", dispCard(handCard2), dispCard(handCard3), dispCard(handCard4), dispCard(res));
            }
            Assert.assertTrue((ia.getCardToPlay().equals(handCard2)) || (ia.getCardToPlay().equals(handCard3)) || (ia.getCardToPlay().equals(handCard4)));

            cpl = ia.getPosToPlay();
            expected = new Couple(-1, 3);
            if (!cpl.equals(expected)) {
                System.out.printf("Expected (%2d, %2d) found (%2d, %2d)", expected.getLine(), expected.getColumn(), cpl.getLine(), cpl.getColumn());
            }
            Assert.assertTrue(cpl.equals(expected));
        }
        else {
            res = (GalleryCard) ia.getCardToPlay();
            if (!res.equals(handCard2) && !res.equals(handCard3) && !res.equals(handCard4)) {
                System.out.printf("Expected %s or %s or %s found %s", dispCard(handCard1), dispCard(handCard3), dispCard(handCard4), dispCard(res));
            }
            Assert.assertTrue((res.equals(handCard1)) || (res.equals(handCard3)) || (res.equals(handCard4)));

            cpl = ia.getPosToPlay();
            if (!cpl.equals(new Couple(0, 2))) {
                System.out.printf("Expected (%2d, %2d) found (%2d, %2d)", 0, 2, cpl.getLine(), cpl.getColumn());
            }
            Assert.assertTrue(cpl.equals(new Couple(0, 2)));
        }

    }

    @Test
    public void goalsAction() throws Exception {
        ArrayList<Couple> goals = ia.getGoalsToTest();

        Assert.assertTrue(goals.size() == 3);

        ia.ignoreGoal(new Couple(0, 8));
        goals = ia.getGoalsToTest();
        Assert.assertTrue(goals.size() == 2);
        Assert.assertFalse(goals.contains(new Couple(0, 8)));

        ia.ignoreGoal(new Couple(0, 8));
        goals = ia.getGoalsToTest();
        Assert.assertTrue(goals.size() == 2);
        Assert.assertFalse(goals.contains(new Couple(0, 8)));
        Assert.assertTrue(goals.contains(new Couple(-2, 8)));
        Assert.assertTrue(goals.contains(new Couple(2, 8)));

        ia = new IA(0);
        goals = ia.getGoalsToTest();
        Assert.assertTrue(goals.size() == 3);
        Assert.assertTrue(goals.contains(new Couple(0, 8)));
        Assert.assertTrue(goals.contains(new Couple(2, 8)));
        Assert.assertTrue(goals.contains(new Couple(-2, 8)));

        ia.addGoldGoal(new Couple(-2, 8));
        goals = ia.getGoalsToTest();

        Assert.assertTrue(goals.size() == 1);
        Assert.assertTrue(goals.contains(new Couple(-2, 8)));
        Assert.assertFalse(goals.contains(new Couple(2, 8)));
        Assert.assertFalse(goals.contains(new Couple(0, 8)));
    }

    // Test randomPlay 
    public void randomPlayTest(IA ia) throws Exception {
        ArrayList<Couple> p;
        Hand hand;

        hand = ia.getPlayableCards();
        ia.randomPlay();
        Assert.assertTrue(hand.getArrayCard().contains(ia.getCardToPlay()));
        if (ia.getCardToPlay().getType() == gallery) {
            p = ia.board.getPossiblePositions((GalleryCard) ia.getCardToPlay());
            Assert.assertTrue(ia.getPosToPlay() != null);
            Assert.assertTrue(p.contains(ia.getPosToPlay()));
        } else {
            Assert.assertTrue(ia.getPosToPlay() == null);
        }

        for (int i = 0; i < ia.nbCardHand(); i++) {
            ia.discard(i);
        }
    }

    public void randomPlayTests() throws Exception {
        IA ia = new IA(0, "IA", Easy);
        ia.setBoard(new Board());

        ia.drawCard(new ActionCard("Map"));
        ia.drawCard(new ActionCard("Map"));
        ia.drawCard(new GalleryCard(true, true, true, true, true));
        ia.drawCard(new RoleCard("Mineur"));
        randomPlayTest(ia);

        ia.drawCard(new GalleryCard(true, false, true, true, true));
        ia.drawCard(new GalleryCard(true, true, false, true, true));
        ia.drawCard(new GalleryCard(true, true, true, false, true));
        ia.drawCard(new GalleryCard(true, true, true, true, false));
        randomPlayTest(ia);

        ia.drawCard(new GalleryCard(true, true, true, true, false));
        randomPlayTest(ia);
    }

    @Test
    public void randomPlay() throws Exception {
        //System.out.println("randomPlay()");
        for (int i = 1; i < 101; i++) {
            //System.out.format("\r\tTest n°%3d/100\n", i);
            randomPlayTests();
        }
    }


    @Test
    public void iaPlayCard() throws Exception {
        System.out.println("TODO : IATest.java iaPlayCard()");
    }

    @Test
    public void isInSwitchZone() throws Exception {
        IA ia = new IA(0, "IA", Medium);
        ia.setBoard(new Board());
        Assert.assertFalse(ia.isInSwitchZone());
        ia.board.addCard(new GalleryCard(tunnel, 4, 6, true, false, true, true, true));
        Assert.assertTrue(ia.isInSwitchZone());
    }
}
