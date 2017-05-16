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
        GoalCard c;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    for (int l = 0; l < 2; l++) {
                        for (int m = 0; m < 2; m++) {
                            for (int n = 0; n < 2; n++) {
                                c = new GoalCard(new Couple(0, 8), (i==1), (j==1), (k==1), (l==1), (m==1));
                                assertTrue(c.getConfig() == 10000 + (i * 1000) + (j * 100) + (k * 10) + (l * 1) + (m * 100000));
                            }
                        }
                    }
                }
            }
        }
    }
}