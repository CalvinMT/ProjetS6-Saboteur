package Cards;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class HandGoldTest {

    @Test
    public void TestValidityNbCard(){
        HandGold h = new HandGold();
        Assert.assertTrue(h.nbCard_true());
    }
}