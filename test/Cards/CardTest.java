package Cards;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CardTest {
    @Test
    void getType() {
        Card cAction = new Card(action, 3, 3);
        Card cPlayer = new Card(player, 3, 3);
        Card cGallery = new Card(gallery, 3, 3);
        Assertions.assertTrue(cAction.getType() = Card.Type.action);
        Assertions.assertTrue(cPlayer.getType() = Card.Type.player);
        Assertions.assertTrue(cGallery.getType() = Card.Type.gallery);
    }

    @Test
    int getX() {
        Card c = new Card(action, 1, 1);
        Assertions.assertTrue(c.getX() = 1);
    }
    @Test
    int getY() {
        Card c = new Card(action, 1, 1);
        Assertions.assertTrue(c.getY() = 1);
    }



}