package Cards;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;


public class DeckGalleryActionTest {

    @Test
    public void TestValidityAction(){
        DeckGalleryAction d = new DeckGalleryAction();
        Assert.assertTrue(d.getValidity());
    }
}