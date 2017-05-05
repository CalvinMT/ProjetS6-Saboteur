package Cards;

import java.util.ArrayList;
import java.util.Random;


public class DeckGalleryAction extends Deck {


    private final int nbCardAction = 27;

    private final int nbNegativPickaxe = 3;
    private final int nbNegativLantern = 3;
    private final int nbNegativWagon = 3;
    private final int nbPositivWagon = 2;
    private final int nbPositivPickaxe = 2;
    private final int nbPositivLantern = 2;
    private final int nbMap = 6;
    private final int nbCrash = 3;
    private final int nbWagonPickaxe = 1;
    private final int nbPickaxeLantern = 1;
    private final int nbWagonLantern = 1;


    private final int nbCardGallery = 40;
    private final int nbCardGallery_centered = 9;
    final int nbCardGallery_no_centered = nbCardGallery - nbCardGallery_centered;


    public DeckGalleryAction(){

        GalleryCard.Gallery_t type = GalleryCard.Gallery_t.tunnel;

        Random rand = new Random();

        this.arrayCard = new ArrayList<Card>();

        for(int i=0; i<nbCardGallery_centered; i++){

            this.arrayCard.add(new GalleryCard(type, true, rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        }

        for(int i=0; i<nbCardGallery_no_centered; i++){
            this.arrayCard.add(new GalleryCard(type, false, rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        }

        for(int i=0; i<nbCardAction; i++){
            this.arrayCard.add(new ActionCard());
        }

        this.shuffle();
        this.shuffle();

    }

    public boolean getValidity(){
        return nbCardAction == (nbNegativPickaxe + nbNegativLantern + nbNegativWagon + nbPositivWagon + nbPositivPickaxe + nbPositivLantern +
         nbMap + nbCrash + nbWagonPickaxe + nbPickaxeLantern + nbWagonLantern);

    }





}
