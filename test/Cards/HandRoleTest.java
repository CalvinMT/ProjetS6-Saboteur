package Cards;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;


public class HandRoleTest {

    @Test
    public void TestCons1(){
        Hand h = new HandRole(1);
        Assert.assertTrue(h.nbCard() > 3);
    }

    @Test
    public void TestCons2(){
        Hand h = new HandRole(20);
        Assert.assertTrue(h.nbCard() <= 11);
    }

}