package Cards;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static Cards.Card.Card_t.*;

public class CardTest {

    @Test
    public void getType() throws Exception {
        Card cAction = new ActionCard("Map");
        Card cPlayer = new RoleCard("Mineur");
        Card cGallery = new GalleryCard();

        assertTrue(cAction.getType() == action);
        assertTrue(cPlayer.getType() == role);
        assertTrue(cGallery.getType() == gallery);
    }

    @Test
    public void equals() throws Exception {
        Card c1 = new RoleCard("Mineur");
        Card c2 = new RoleCard("Mineur");
        Card c3 = new RoleCard("Saboteur");

        assertTrue(c1.equals(c2));
        assertTrue(c1.equals(c3));
        assertTrue(c2.equals(c3));
        c2 = new GalleryCard();
        assertFalse(c1.equals(c2));
    }

}