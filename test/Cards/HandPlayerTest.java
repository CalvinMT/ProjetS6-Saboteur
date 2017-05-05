package Cards;

import org.junit.Assert;
import org.junit.Test;


public class HandPlayerTest {

    @Test
    public void TestCons() {
        HandPlayer h = new HandPlayer();
        Assert.assertTrue(h.nbCard() >= 0);
    }

    @Test
    public void TestDiscard(){
        HandPlayer h = new HandPlayer();
        h.discard(1);
        Assert.assertTrue(h.nbCard() >= 0);
    }

    @Test
    public void TestDiscard2(){
        HandPlayer h = new HandPlayer();
        h.discard(0);
        Assert.assertTrue(h.nbCard() >= 0);
    }

    @Test
    public void TestDiscard3(){
        HandPlayer h = new HandPlayer(3);
        int size = h.nbCard();
        h.discard(h.nbCard());
        Assert.assertTrue(h.nbCard() == size);
    }

    @Test
    public void Testadd1(){
        HandPlayer h = new HandPlayer();
        int size = h.nbCard();
        h.addCard(new ActionCard());
        Assert.assertTrue(h.nbCard() == size+1);
    }

    @Test
    public void Testadd2(){
        HandPlayer h = new HandPlayer();
        int size = h.nbCard();
        h.addCard(new RoleCard("Mineur"));
        Assert.assertTrue(h.nbCard() == size);
    }

    @Test
    public void Testchoose1(){
        HandPlayer h = new HandPlayer(4);
        Assert.assertTrue(h.nbCard() > 0);
        int size = h.nbCard();
        h.chooseOne_with_remove(3);
        Assert.assertTrue(h.nbCard() == size-1);
    }

    // MORE TEST
}