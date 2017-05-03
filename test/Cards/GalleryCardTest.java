package Cards;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static Cards.GalleryCard.Gallery_t.*;

class GalleryCardTest {
    @Test
    void getX() {
        GalleryCard c = new GalleryCard();
        Assertions.assertTrue(c.getX() == 0);
        c = new GalleryCard(tunnel, 2, 2);
        Assertions.assertTrue(c.getX() = 2);
    }

    @Test
    void getY() {
        GalleryCard c = new GalleryCard();
        Assertions.assertTrue(c.getY() = 0);
        c = new GalleryCard(GalleryCard.Gallery_t.tunnel, 2, 2);
        Assertions.assertTrue(c.getY() == 2);
    }

    @Test
    void getGalleryType() {
        GalleryCard c = new GalleryCard();
        Assertions.assertTrue(c.getGalleryType() == start);
        c = new GalleryCard(tunnel, 2, 2);
        Assertions.assertTrue(c.getGalleryType() == tunnel);
    }

}