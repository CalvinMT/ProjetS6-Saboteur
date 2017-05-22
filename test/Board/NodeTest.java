package Board;

import org.junit.Test;

import static org.junit.Assert.*;


public class NodeTest {
    Node n = new Node();
    @Test
    public void getterSettterNorth() throws Exception {
        assertTrue(n.getNorth() == -1);
        n.setNorth(4);
        assertFalse(n.getNorth() == -1);
        assertTrue(n.getNorth() == 4);

    }

    @Test
    public void getterSetterSouth() throws Exception {
        assertTrue(n.getSouth() == -1);
        n.setSouth(4);
        assertFalse(n.getSouth() == -1);
        assertTrue(n.getSouth() == 4);
    }

    @Test
    public void getterSetterEast() throws Exception {
        assertTrue(n.getEast() == -1);
        n.setEast(4);
        assertFalse(n.getEast() == -1);
        assertTrue(n.getEast() == 4);
    }

    @Test
    public void getterSetterWest() throws Exception {
        assertTrue(n.getWest() == -1);
        n.setWest(4);
        assertFalse(n.getWest() == -1);
        assertTrue(n.getWest() == 4);
    }

}