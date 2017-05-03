package Cards;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static Cards.Card.Card_t.*;

class CardTest {
    @Test
    void getType() {
        Card cAction = new Card(action, 3, 3);
        Card cPlayer = new Card(player, 3, 3);
        Card cGallery = new Card(gallery, 3, 3);
        Assertions.assertTrue(cAction.getCardt() == action);
        Assertions.assertTrue(cPlayer.getCardt() == player);
        Assertions.assertTrue(cGallery.getCardt() == gallery);
    }
}