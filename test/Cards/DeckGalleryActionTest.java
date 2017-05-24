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

    @Test
    public void TestNbBloqued(){

        Card c;
        int nb = 0;

        DeckGalleryAction deck = new DeckGalleryAction();

        for(int i=0; i<deck.nbCard(); i++){
            c = deck.arrayCard.get(i);
            if(c.getType() == Card.Card_t.gallery){
                if(((GalleryCard) c).canHasCenter()){
                    nb++;
                }
            }
        }
        Assert.assertTrue(nb == deck.getNbBloqued());
    }

    @Test
    public void testValidityGallery(){
        DeckGalleryAction deck = new DeckGalleryAction();
        Card c;

        for(int i=0; i<deck.nbCard(); i++){
            c = deck.arrayCard.get(i);
            if(c.getType() == Card.Card_t.gallery){
                Assert.assertTrue(((GalleryCard) c).possible());
            }
        }
    }
}