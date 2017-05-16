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

    @Test
    public void setX() throws Exception {
        Couple c = new Couple(0, 2);
        Assert.assertTrue(c.getX() == 0);
        c.setX(1);
        Assert.assertTrue(c.getX() == 1);
    }

    @Test
    public void setY() throws Exception {
        Couple c = new Couple(2, 0);
        Assert.assertTrue(c.getY() == 0);
        c.setY(1);
        Assert.assertTrue(c.getY() == 1);
    }

    @Test
    public void equals() throws Exception {
        Couple c1 = new Couple(0, 1);
        Couple c2 = new Couple(2, 3);

        Assert.assertFalse(c1.equals(c2));
        Assert.assertFalse(c2.equals(c1));
        c2 = new Couple(0, 1);
        Assert.assertTrue(c1.equals(c2));
        Assert.assertTrue(c2.equals(c1));
    }
}