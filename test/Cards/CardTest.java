package Cards;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CardTest {
    @Test
    void getType() {
        Card cAction = new Card(Card.Type.action, 3, 3);
        Card cPlayer = new Card(Card.Type.player, 3, 3);
        Card cGallery = new Card(Card.Type.gallery, 3, 3);
        Assertions.assertTrue(cAction.getType() = Card.Type.action);
        Assertions.assertTrue(cPlayer.getType() = Card.Type.player);
        Assertions.assertTrue(cGallery.getType() = Card.Type.gallery);
    }
}