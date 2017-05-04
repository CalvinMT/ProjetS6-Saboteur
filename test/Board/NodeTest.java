package Board;

import Cards.GalleryCard;
import org.junit.Test;
import static org.junit.Assert.*;


public class NodeTest {
    Node n = new Node();
    @Test
    public void setNorth() throws Exception {
        GalleryCard c = new GalleryCard(GalleryCard.Gallery_t.tunnel);
        assertNull(n.getNorth());
        n.setNorth(new Node(c));
        assertNotNull(n.getNorth());
        assertTrue(n.getNorth().card.equals(c));

    }

    @Test
    public void setSouth() throws Exception {
        GalleryCard c = new GalleryCard(GalleryCard.Gallery_t.tunnel);
        assertNull(n.getSouth());
        n.setSouth(new Node(c));
        assertNotNull(n.getSouth());
        assertTrue(n.getSouth().card.equals(c));
    }

    @Test
    public void setEast() throws Exception {
        GalleryCard c = new GalleryCard(GalleryCard.Gallery_t.tunnel);
        assertNull(n.getEast());
        n.setEast(new Node(c));
        assertNotNull(n.getEast());
        assertTrue(n.getEast().card.equals(c));
    }

    @Test
    public void setWest() throws Exception {
        GalleryCard c = new GalleryCard(GalleryCard.Gallery_t.tunnel);
        assertNull(n.getWest());
        n.setWest(new Node(c));
        assertNotNull(n.getWest());
        assertTrue(n.getWest().card.equals(c));
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