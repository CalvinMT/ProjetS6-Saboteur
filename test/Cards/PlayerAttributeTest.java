package Cards;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;


public class PlayerAttributeTest {


    @Test
    public void removeAttribute() throws Exception {
        PlayerAttribute p = new PlayerAttribute();
        p.removeAttribute(0);
        Assert.assertTrue(p.nbCard() >= 0);
    }

    @Test
    public void addAttribute() throws Exception {
        PlayerAttribute p = new PlayerAttribute();
        p.doActionCard(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Lantern), null);
        Assert.assertTrue(p.nbCard() == 1);
    }

    @Test
    public void test1(){
        PlayerAttribute p = new PlayerAttribute();
        p.doActionCard(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Lantern), null);
        Assert.assertTrue(p.nbCard() == 1);
        p.doActionCard(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Wagon), null);
        Assert.assertTrue(p.nbCard() == 2);
        p.doActionCard(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Pickaxe), null);
        Assert.assertTrue(p.nbCard() == 3);
        p.doActionCard(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Pickaxe), null);
        Assert.assertTrue(p.nbCard() == 3);
    }

    @Test
    public void test2(){
        PlayerAttribute p = new PlayerAttribute();
        p.doActionCard(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Lantern), null);
        Assert.assertTrue(p.nbCard() == 1);
        p.doActionCard(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Wagon), RepareSabotageCard.Tools.Wagon);
        Assert.assertTrue(p.nbCard() == 2);
        p.removeAttribute(0);
        Assert.assertTrue(p.nbCard() == 1);
        p.removeAttribute(1);
        Assert.assertTrue(p.nbCard() == 1);
    }


    @Test
    public void test3(){
        PlayerAttribute p = new PlayerAttribute();
        Assert.assertTrue(p.nbCard() >= 0);
    }

}