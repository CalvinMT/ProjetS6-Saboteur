package Cards;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;



public class GoldCardTest {

    @Test
    public void testCons1(){
        GoldCard c = new GoldCard(4);

        Assert.assertTrue(c.getGold() == 0);
    }

    @Test
    public void testCons2(){
        GoldCard c = new GoldCard(1);

        Assert.assertTrue(c.getGold() == 1);
    }

    @Test
    public void testCons3(){
        GoldCard c = new GoldCard(2);

        Assert.assertTrue(c.getGold() == 2);
    }
    @Test
    public void testCons4(){
        GoldCard c = new GoldCard(3);

        Assert.assertTrue(c.getGold() == 3);
    }

}