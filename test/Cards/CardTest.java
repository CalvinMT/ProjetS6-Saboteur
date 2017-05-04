package Cards;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static Cards.Card.Card_t.*;

public class CardTest {

    @Test
    public void getType() throws Exception {
        Card cAction = new Card(action);
        Card cPlayer = new Card(player);
        Card cGallery = new Card(gallery);

        assertTrue(cAction.getType() == action);
        assertTrue(cPlayer.getType() == player);
        assertTrue(cGallery.getType() == gallery);
    }

}