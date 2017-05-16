package Player;

import Board.Couple;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class IATest {
    IA ia = new IA();
    @Test
    public void getHeuristic() throws Exception {
        System.out.println("TODO : test getHeuristic()");
        System.out.println(ia.getHeuristic(new Couple(0, 8), new Couple(0, 7)));
        assertTrue(ia.getHeuristic(new Couple(0, 8), new Couple(0, 7)) == 1.0);
        assertTrue(ia.getHeuristic(new Couple(-2, 8), new Couple(1, 3)) == 8.0);
    }

    // TODO
    @Test
    public void choosePosition() throws Exception {
        System.out.println("TODO : test choosePosition()");
    }

    @Test
    public void ignoreGoal() throws Exception {
        System.out.println("TODO : test ignoreGoal()");
    }

    @Test
    public void addGoldGoal() throws Exception {
        System.out.println("TODO : test addGoldGoal()");
    }

    @Test
    public void changeDiffulty() throws Exception {
        System.out.println("TODO : test changeDiffulty()");
    }

}