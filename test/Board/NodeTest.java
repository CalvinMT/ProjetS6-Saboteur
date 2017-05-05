package Board;

import org.junit.Test;

import static org.junit.Assert.*;


public class NodeTest {
    Node n = new Node();
    @Test
    public void setNorth() throws Exception {
        assertTrue(n.getNorth() == -1);
        n.setNorth(4);
        assertFalse(n.getNorth() == -1);
        assertTrue(n.getNorth() == 4);

    }

    @Test
    public void setSouth() throws Exception {
        assertTrue(n.getSouth() == -1);
        n.setSouth(4);
        assertFalse(n.getSouth() == -1);
        assertTrue(n.getSouth() == 4);
    }

    @Test
    public void setEast() throws Exception {
        assertTrue(n.getEast() == -1);
        n.setEast(4);
        assertFalse(n.getEast() == -1);
        assertTrue(n.getEast() == 4);
    }

    @Test
    public void setWest() throws Exception {
        assertTrue(n.getWest() == -1);
        n.setWest(4);
        assertFalse(n.getWest() == -1);
        assertTrue(n.getWest() == 4);
    }

    //TODO
    @Test
    public void getNorth() throws Exception {
    }

    @Test
    public void getSouth() throws Exception {
    }

    @Test
    public void getEast() throws Exception {
    }

    @Test
    public void getWest() throws Exception {
    }

}