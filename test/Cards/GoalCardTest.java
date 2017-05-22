package Cards;

import Board.Couple;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GoalCardTest {
    @Test
    public void equals() throws Exception {
        GoalCard g1 = new GoalCard();
        GoalCard g2 = new GoalCard(new Couple (0, 8), false, true, true, false, false);

        assertFalse(g1.equals(g2));
        g2 = new GoalCard();
        assertTrue(g1.equals(g2));

    }


    @Test
    public void isGold() throws Exception {
        GoalCard gNoGold = new GoalCard();
        GoalCard gGold = new GoalCard(new Couple(0, 8), false, true, true, false, true);

        Assert.assertFalse(gNoGold.isGold());
        Assert.assertTrue(gGold.isGold());
    }

    @Test
    public void getConfig() throws Exception {
        int res;
        GoalCard c;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    for (int l = 0; l < 2; l++) {
                        for (int m = 0; m < 2; m++) {
                            res = 0b10000;
                            c = new GoalCard(new Couple(0, 8), (i==1), (j==1), (k==1), (l==1), (m==1));
                            if (i==1) {
                                res += 0b1000;
                            }
                            if (j==1) {
                                res += 0b100;
                            }
                            if (k==1) {
                                res += 0b10;
                            }
                            if (l==1) {
                                res += 0b1;
                            }
                            if (m==1) {
                                res += 0b100000;
                            }
                            assertTrue(c.getConfig() == res);
                        }
                    }
                }
            }
        }
    }
}