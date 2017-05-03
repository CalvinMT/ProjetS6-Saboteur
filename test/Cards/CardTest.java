package Cards;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static Cards.Card.Card_t.*;

class CardTest {
    @Test
    void getType() {
        Card cAction = new Card(action);
        Card cPlayer = new Card(player);
        Card cGallery = new Card(gallery);
        Assertions.assertTrue(cAction.getType() == action);
        Assertions.assertTrue(cPlayer.getType() == player);
        Assertions.assertTrue(cGallery.getType() == gallery);
    }
}