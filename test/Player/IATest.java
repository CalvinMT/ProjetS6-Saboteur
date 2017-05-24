package Player;

import Board.Board;
import Board.Couple;
import Cards.ActionCard;
import Cards.GalleryCard;
import Cards.Hand;
import Cards.RoleCard;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static Cards.Card.Card_t.gallery;
import static Player.Player.Difficulty.Easy;
import static org.junit.Assert.assertTrue;

public class IATest {
    IA ia = new IA(0);

    @Test
    public void getDistanceToGoal() throws Exception {
        assertTrue(ia.getDistanceToGoal(new Couple(0, 8), new Couple(0, 7)) == 1.0);
        assertTrue(ia.getDistanceToGoal(new Couple(-2, 8), new Couple(1, 3)) == 8.0);
        assertTrue(ia.getDistanceToGoal(new Couple(2, 8), new Couple(1, 12)) == 5.0);

    }

    // TODO
    @Test
    public void choosePosition() throws Exception {
        System.out.println("TODO : IATest.java choosePosition()");
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

    @Test
    public void changeDiffulty() throws Exception {
        System.out.println("TODO : IATest.java changeDiffulty()");
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
        System.out.println("randomPlay()");
        for (int i = 1; i < 101; i++) {
            System.out.format("\r\tTest n°%3d/100\n", i);
            randomPlayTests();
        }
    }

    @Test
    public void getGoalsToTest() throws Exception {
        System.out.println("TODO : IATest.java getGoalsToTest()");
    }

    @Test
    public void iaPlayCard() throws Exception {
        System.out.println("TODO : IATest.java iaPlay()");
    }
}