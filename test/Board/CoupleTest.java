package Board;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by oloar on 09/05/2017.
 */
public class CoupleTest {
    @Test
    public void getX() throws Exception {
        Couple c = new Couple(1, 2);
        Assert.assertTrue(c.getX() == 1);
    }

    @Test
    public void getY() throws Exception {
        Couple c = new Couple(1, 2);
        Assert.assertTrue(c.getY() == 2);
    }

}