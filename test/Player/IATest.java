package Player;

import Board.Couple;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class IATest {
    IA ia = new IA();
    @Test
    public void getHeuristic() throws Exception {
        assertTrue(ia.getHeuristic(new Couple(0, 8), new Couple(0, 7)) == 1.0);
        assertTrue(ia.getHeuristic(new Couple(-2, 8), new Couple(1, 3)) == 8.0);
        assertTrue(ia.getHeuristic(new Couple(2, 8), new Couple(1, 12)) == 5.0);

    }

    // TODO
    @Test
    public void choosePosition() throws Exception {
        System.out.println("TODO : test choosePosition()");
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

        ia = new IA();
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
        System.out.println("TODO : test changeDiffulty()");
    }

}