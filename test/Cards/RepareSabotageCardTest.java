package Cards;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by thespygeek on 06/05/17.
 */
public class RepareSabotageCardTest {

    @Test
    public void TestCons1(){
        ActionCard c = new RepareSabotageCard("Blabla", RepareSabotageCard.Tools.Lantern);
        Assert.assertTrue(c.getAction() == ActionCard.Action.Map);
    }

    @Test
    public void TestCons2(){
        RepareSabotageCard c = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Lantern);
        Assert.assertTrue(c.action == ActionCard.Action.Repare);
        Assert.assertTrue(c.nbTools() == 1);
    }

    @Test
    public void TestCons3(){
        RepareSabotageCard c = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Lantern, RepareSabotageCard.Tools.Wagon);
        Assert.assertTrue(c.action == ActionCard.Action.Repare);
        Assert.assertTrue(c.nbTools() == 2);
    }

    @Test
    public void TestCons4(){
        RepareSabotageCard c = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Lantern, RepareSabotageCard.Tools.Lantern);
        Assert.assertTrue(c.action == ActionCard.Action.Repare);
        Assert.assertTrue(c.nbTools() == 1);
    }

    @Test
    public void TestCons5(){
        RepareSabotageCard c = new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Lantern, RepareSabotageCard.Tools.Lantern);
        Assert.assertTrue(c.action == ActionCard.Action.Sabotage);
        Assert.assertTrue(c.nbTools() == 1);
    }

    @Test
    public void TestCons6(){
        RepareSabotageCard c = new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Lantern);
        Assert.assertTrue(c.action == ActionCard.Action.Sabotage);
        Assert.assertTrue(c.nbTools() == 1);
    }

    @Test
    public void TestcanRepare1() throws Exception {
        RepareSabotageCard breakCard = new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Lantern);
        RepareSabotageCard repareCard = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Lantern);
        Assert.assertTrue(breakCard.canBeRepareBy(repareCard));
    }

    @Test
    public void TestcanRepare2() throws Exception {
        RepareSabotageCard breakCard = new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Lantern);
        RepareSabotageCard repareCard = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Wagon, RepareSabotageCard.Tools.Lantern);
        Assert.assertTrue(breakCard.canBeRepareBy(repareCard));
    }

    @Test
    public void TestcanRepare3() throws Exception {
        RepareSabotageCard breakCard = new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Lantern);
        RepareSabotageCard repareCard = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Wagon, RepareSabotageCard.Tools.Pickaxe);
        Assert.assertFalse(breakCard.canBeRepareBy(repareCard));
    }

    @Test
    public void TestcanRepare4() throws Exception {
        RepareSabotageCard breakCard = new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Pickaxe);
        RepareSabotageCard repareCard = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Pickaxe);
        Assert.assertTrue(breakCard.canBeRepareBy(repareCard));
    }

    @Test
    public void TestcanRepare5() throws Exception {
        RepareSabotageCard breakCard = new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Pickaxe);
        RepareSabotageCard repareCard = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Wagon);
        Assert.assertFalse(breakCard.canBeRepareBy(repareCard));
    }

    @Test
    public void TestcanRepare6() throws Exception {
        RepareSabotageCard breakCard = new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Pickaxe);
        RepareSabotageCard repareCard = new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Wagon);
        Assert.assertFalse(breakCard.canBeRepareBy(repareCard));
    }

    @Test
    public void TestcanRepare7() throws Exception {
        RepareSabotageCard breakCard = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Pickaxe);
        RepareSabotageCard repareCard = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Wagon);
        Assert.assertFalse(breakCard.canBeRepareBy(repareCard));
    }

}